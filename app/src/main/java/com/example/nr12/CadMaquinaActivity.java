package com.example.nr12;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nr12.app.MessageBox;
import com.example.nr12.database.DataBase;
import com.example.nr12.dominio.entidades.TipoMaquina;
import com.example.nr12.dominio.repositorio.RepositorioMaquina;
import com.example.nr12.dominio.entidades.Cliente;
import com.example.nr12.dominio.entidades.Maquina;
import com.example.nr12.dominio.repositorio.RepositorioTipoMaquina;

public class CadMaquinaActivity extends AppCompatActivity {


    private FrameLayout layout;

    private TextView txtViewCLiente;
    private EditText edtMaquina;
    private EditText edtSetor;
    private EditText edtOperadores;
    private EditText edtModelo;
    private EditText edtFabricante;
    private EditText edtCapacidade;
    private EditText edtAno;
    private EditText edtPatromonio;
    private EditText edtNumeroSerie;

    private Spinner spnTipoMaquina;
    private ArrayAdapter<String> adpTipoMaquina;

    private Cliente cliente;
    private Maquina maquina;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioMaquina repositorioMaquina;
    private RepositorioTipoMaquina repositorioTipoMaquina;

    private final int PAR_CONSULTA_CLIENTE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_maquina);

        layout = (FrameLayout)findViewById(R.id.layout_cad_maquina);
        criaConexao();

        txtViewCLiente = (TextView)findViewById(R.id.txtViewCLiente);
        edtMaquina = (EditText)findViewById(R.id.edtMaquina);
        edtModelo = (EditText)findViewById(R.id.edtModelo);
        edtSetor = (EditText)findViewById(R.id.edtSetor);
        edtOperadores = (EditText)findViewById(R.id.edtOperadores);
        edtFabricante = (EditText)findViewById(R.id.edtFabricante);
        edtCapacidade = (EditText)findViewById(R.id.edtCapacidade);
        edtAno = (EditText)findViewById(R.id.edtAno);
        edtPatromonio = (EditText)findViewById(R.id.edtPatromonio);
        edtNumeroSerie = (EditText)findViewById(R.id.edtNumeroSerie);

        spnTipoMaquina = (Spinner)findViewById(R.id.spnTipoMaquina);
        adpTipoMaquina = repositorioTipoMaquina.buscaTodosAdapter(this);
        spnTipoMaquina.setAdapter(adpTipoMaquina);
        /*
        adpTipoMaquina = ViewHelper.createArrayAdapter(this, spnTipoMaquina);
        adpTipoMaquina.add("Injetora");
        adpTipoMaquina.add("Prensa");
        adpTipoMaquina.add("Dobradeira");
         */

        Bundle bundle = getIntent().getExtras();

        if((bundle != null) && (bundle.containsKey(CadRelatorioActivity.PAR_CLIENTE))){
            cliente = (Cliente)bundle.getSerializable(CadRelatorioActivity.PAR_CLIENTE);
            maquina = new Maquina();
            maquina.setCliente(cliente);

            txtViewCLiente.setText(cliente.getNome());
            txtViewCLiente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SelecionaClienteActivity.class);
                    startActivityForResult(intent, PAR_CONSULTA_CLIENTE);
                }
            });

            //MessageBox.showInfo(this, "Cliente", cliente.getNome());
            //preencherDados();
        }else{

            //maquina = new Maquina();
        }

    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){

        Bundle parametros = null;

        if(data != null){
            parametros = data.getExtras();
        }

        if (requestCode == PAR_CONSULTA_CLIENTE) {

            switch (resultCode){
                case RESULT_OK:
                    if(parametros != null){
                        cliente = (Cliente)parametros.getSerializable(CadRelatorioActivity.PAR_CLIENTE);
                        maquina.setCliente(cliente);
                        txtViewCLiente.setText(cliente.getNome());
                    }
                    break;

                case RESULT_CANCELED:

                    break;
            }


        }
    }

    private void criaConexao(){
        try{
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();
            Snackbar.make(layout, R.string.message_conexao_sucesso, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_ok, null).show();
            repositorioMaquina = new RepositorioMaquina(conn);
            repositorioTipoMaquina = new RepositorioTipoMaquina(conn);
        }catch(SQLException ex){
            Log.i(getString(R.string.title_info), "Erro ao consultar o banco:" + ex.getMessage());
            MessageBox.show(this, getString(R.string.title_erro), "Erro ao consultar o banco:" + ex.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(conn != null){
            conn.close();
            Log.i("INFO", "Fechando conexão!!");
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
                salvar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvar(){
        if(validar() == false){
            if(maquina.getIdLocal() == 0){
                try{
                    repositorioMaquina.inserir(maquina);
                    finish();
                }catch (SQLException ex){
                    MessageBox.showAlert(this,"Erro", ex.getMessage());
                }
            }else{
//              repositorioMaquina.alterar(maquina);
            }
        }
    }

    private boolean validar(){

        boolean res = false;

        String nome = edtMaquina.getText().toString();
        String modelo = edtModelo.getText().toString();
        String numeroSerie = edtNumeroSerie.getText().toString();
        String numeroPatrimonio = edtPatromonio.getText().toString();
        String capacidade = edtCapacidade.getText().toString();
        String fabricante = edtFabricante.getText().toString();
        String setor = edtSetor.getText().toString();
        TipoMaquina tipo = (TipoMaquina)spnTipoMaquina.getSelectedItem();
        String operadores = edtOperadores.getText().toString();
        String ano = edtAno.getText().toString();

        maquina.setNome(nome);
        maquina.setModelo(modelo);
        maquina.setNumeroSerie(numeroSerie);
        maquina.setNumeroPatrimonio(numeroPatrimonio);
        maquina.setCapacidade(capacidade);
        maquina.setFabricante(fabricante);
        maquina.setSetor(setor);
        maquina.setTipoMaquina(tipo);

        if(!isCampoVazio(operadores)){
            maquina.setOperadores(Integer.valueOf(operadores));
        }
        if(!isCampoVazio(ano)){
            maquina.setAno(Integer.valueOf(ano));
        }

        if(res = isCampoVazio(nome)){
            edtMaquina.requestFocus();
        } else
        if(res = isCampoVazio(tipo.getNome())){
            spnTipoMaquina.requestFocus();
        }

        if(res){
            MessageBox.showAlert(this, "Erro", "Há campos inválidos ou em branco.");
        }
        return res;
    }

    private boolean isCampoVazio(String valor) {
        boolean resultado  = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }
}
