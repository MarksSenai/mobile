package com.example.nr12;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ListView;
import android.widget.Toast;

import com.example.nr12.app.MessageBox;
import com.example.nr12.database.DataBase;
import com.example.nr12.dominio.repositorio.RepositorioMaquina;
import com.example.nr12.dominio.entidades.Cliente;
import com.example.nr12.dominio.entidades.Maquina;

public class SelecionaMaquinaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private CoordinatorLayout layout;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioMaquina repositorioMaquina;

    private ArrayAdapter adapter;

    private ArrayAdapter<Maquina> adpMaquinas;
    private ListView lstMaquinas;

    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleciona_maquina);

        layout = (CoordinatorLayout)findViewById(R.id.layout_seleciona_maquina);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        lstMaquinas = (ListView)findViewById(R.id.lstMaquinas);
        lstMaquinas.setOnItemClickListener(this);

        criaConexao();

        Bundle bundle = getIntent().getExtras();
        if((bundle != null) && (bundle.containsKey(CadRelatorioActivity.PAR_CLIENTE))){
            cliente = (Cliente)bundle.getSerializable(CadRelatorioActivity.PAR_CLIENTE);
            adpMaquinas = repositorioMaquina.buscaMaquinas(this, cliente);
            lstMaquinas.setAdapter(adpMaquinas);

        }else{
            //contato = new Contato();
        }



        /*
        filtraDados = new FiltraDados(adpContatos);
        edtPesquisa.addTextChangedListener(filtraDados);
        */


    }

    private void criaConexao(){
        try{
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();
            Snackbar.make(layout, R.string.message_conexao_sucesso, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_ok, null).show();
            repositorioMaquina = new RepositorioMaquina(conn);
        }catch(SQLException ex){
            Log.i(getString(R.string.title_info), "Erro ao consultar o banco:" + ex.getMessage());
            MessageBox.show(this, getString(R.string.title_erro), "Erro ao consultar o banco:" + ex.getMessage());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Maquina maquina = adpMaquinas.getItem(position);
        Intent intent = new Intent();
        intent.putExtra(CadRelatorioActivity.PAR_MAQUINA, maquina);
        intent.putExtra(CadRelatorioActivity.PAR_CLIENTE, cliente);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_seleciona_maquina, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.app_bar_search:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cadastrar(View view) {
        Intent intent = new Intent(SelecionaMaquinaActivity.this, CadMaquinaActivity.class);
        intent.putExtra(CadRelatorioActivity.PAR_CLIENTE, cliente);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        adpMaquinas = repositorioMaquina.buscaMaquinas(this, cliente);
        lstMaquinas.setAdapter(adpMaquinas);
    }

}
