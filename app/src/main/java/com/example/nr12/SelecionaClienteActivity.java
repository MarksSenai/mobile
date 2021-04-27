package com.example.nr12;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.nr12.app.MessageBox;
import com.example.nr12.database.DataBase;
import com.example.nr12.dominio.repositorio.RepositorioCliente;
import com.example.nr12.dominio.entidades.Cliente;

import java.util.List;

public class SelecionaClienteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView lstClientes;
    private LinearLayout layout;
    private ArrayAdapter<Cliente> adpClientes;
    private Toolbar toolbar;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioCliente repositorioCliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleciona_cliente);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstClientes = (ListView) findViewById(R.id.lstClientes);
        layout = (LinearLayout)findViewById(R.id.layout_cliente);

        criaConexao();

        lstClientes.setOnItemClickListener(this);
        adpClientes = repositorioCliente.buscarTodosClientesAdapter(this);
        lstClientes.setAdapter(adpClientes);
    }

    private void criaConexao(){
        try{
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();
            Snackbar.make(layout, R.string.message_conexao_sucesso, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_ok, null).show();
            repositorioCliente = new RepositorioCliente(conn);
        }catch(SQLException ex){
            Log.i(getString(R.string.title_info), "Erro ao consultar o banco:" + ex.getMessage());
            MessageBox.show(this, getString(R.string.title_erro), "Erro ao consultar o banco:" + ex.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_seleciona_cliente, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.search:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cliente cliente = adpClientes.getItem(position);
        Intent intent = new Intent();
        intent.putExtra(CadRelatorioActivity.PAR_CLIENTE, cliente);
        setResult(RESULT_OK, intent);
        finish();
    }

}
