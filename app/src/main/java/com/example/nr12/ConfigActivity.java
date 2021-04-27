package com.example.nr12;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nr12.app.MessageBox;
import com.example.nr12.database.DataBase;
import com.example.nr12.dominio.repositorio.RepositorioConfig;
import com.example.nr12.dominio.entidades.Config;

import java.io.UnsupportedEncodingException;

/**
 * Created by Gui on 25/09/2017.
 *
 * Alterado by Fabiano on 05/10/2017.
 */

public class ConfigActivity extends AppCompatActivity {

    public static final String PAR_USUARIO = "USUARIO";

    private EditText edtUrl;
    private EditText edtEmail;
    private EditText edtSenha;
    private TextView txtSenha;

    private DataBase dataBase;
    private SQLiteDatabase conn;

    private RepositorioConfig repositorioConfig;
    private Config config;
    private LinearLayout layoutConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        edtUrl = (EditText) findViewById(R.id.edtUrl);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        layoutConfig = (LinearLayout) findViewById(R.id.layout_config);

        criaConexao();

        config = repositorioConfig.buscaConfiguracoes();

        if(config.getId() != 0){
            preencherDados();
        }

    }

    private void criaConexao(){
        try{
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();
            Snackbar.make(layoutConfig, R.string.message_conexao_sucesso, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_ok, null).show();
            repositorioConfig = new RepositorioConfig(conn);
        }catch(SQLException ex){
            Log.i(getString(R.string.title_info), "Erro ao consultar o banco:" + ex.getMessage());
            MessageBox.show(this, getString(R.string.title_erro), "Erro ao consultar o banco:" + ex.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_salvar:
                cadastrar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isCampoVazio(String valor){
        boolean resultado = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }

    private boolean isEmailValido(String email){
        boolean resultado = (!isCampoVazio(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        return resultado;
    }

    private boolean validaCampos(){
        boolean res = false;

        String url = edtUrl.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtSenha.getText().toString();

        String base64  = null;
        try {
            base64 = Base64.encodeToString(password.getBytes("UTF-8"), Base64.NO_WRAP | Base64.URL_SAFE);
        } catch (UnsupportedEncodingException e) {
            edtSenha.requestFocus();
            e.printStackTrace();
            return true;
        }

        config.setUrl(url);
        config.setEmail(email);
        config.setSenha(base64);

        Log.i("TESTE", "Url: " +config.getUrl());
        Log.i("TESTE", "email: " +config.getEmail());
        Log.i("TESTE", "senha: " +config.getSenha());

        // Validação
        if(res = !isEmailValido(email)){
            edtEmail.requestFocus();
        }else if(res = isCampoVazio(password)){
            edtSenha.requestFocus();
        }else if(res = isCampoVazio(url)){
            edtUrl.requestFocus();
        }
        if(res){
            MessageBox.showAlert(this, getString(R.string.title_aviso), getString(R.string.message_campos_invalidos_brancos));
        }
        return res;
    }

    private void cadastrar() {
        if(validaCampos() == false){
            try {
                if (config.getId() == 0) {
                    repositorioConfig.inserir(config);
                } else {
                    repositorioConfig.alterar(config);
                }
                finish();
            }catch(SQLException ex){
                Log.i(getString(R.string.title_info), "Erro ao cadastra no banco:" + ex.getMessage());
                MessageBox.show(this, getString(R.string.title_erro), "Erro ao cadastrar no banco:" + ex.getMessage());
            }
        }
    }

    private void preencherDados() {
        edtUrl.setText(config.getUrl());
        edtEmail.setText(config.getEmail());
        edtSenha.setText(new String(Base64.decode(config.getSenha().getBytes(), Base64.DEFAULT)));
    }
}
