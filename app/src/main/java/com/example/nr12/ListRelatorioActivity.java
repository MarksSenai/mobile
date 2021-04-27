package com.example.nr12;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nr12.app.MessageBox;
import com.example.nr12.database.DataBase;
import com.example.nr12.dominio.entidades.Laudo;
import com.example.nr12.dominio.repositorio.RepositorioLaudo;
import com.example.nr12.services.ServiceConfig;
import com.example.nr12.sync.GetJsonClientes;
import com.example.nr12.sync.GetJsonToken;
import com.example.nr12.util.NetWorkUtils;

public class ListRelatorioActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private ListView lstRelatorios;
    private ArrayAdapter<Laudo> adpRelatorios;
    //private ArrayAdapter adpRelatorios;
    private RepositorioLaudo repositorioLaudo;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private CoordinatorLayout layout;

    public static final String PAR_RELATORIO = "RELATORIO";

    private final int PAR_CAD_RELATORIO = 0;
    private final int PAR_CONSULTA_CLIENTE = 1;
    private final int PAR_CONSULTA_MAQUINA = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_relatorio);

        layout = (CoordinatorLayout)findViewById(R.id.layout_list_relatorio);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        criaConexao();

        // lista relatórios
        lstRelatorios = (ListView)findViewById(R.id.lstRelatorios);

        //adpRelatorios = ArrayAdapter.createFromResource(this, R.array.relatorios, android.R.layout.simple_list_item_1);
        adpRelatorios = repositorioLaudo.buscaLaudos(this);

        lstRelatorios.setAdapter(adpRelatorios);
        lstRelatorios.setOnItemClickListener(this);

    }

    private void criaConexao(){
        try{
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();
            repositorioLaudo = new RepositorioLaudo(conn);
            Snackbar.make(layout, R.string.message_conexao_sucesso, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_ok, null).show();
        }catch(SQLException ex){
            Log.i(getString(R.string.title_info), "Erro ao consultar o banco:" + ex.getMessage());
            MessageBox.show(this, getString(R.string.title_erro), "Erro ao consultar o banco:" + ex.getMessage());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Laudo laudo = adpRelatorios.getItem(position);
        Intent intent = new Intent(this, CadRelatorioActivity.class);
        intent.putExtra(CadRelatorioActivity.PAR_LAUDO, laudo);
        startActivityForResult(intent, 0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intentConfig = new Intent(this, ConfigActivity.class);
            startActivity(intentConfig);
            return true;
        }

        if (id == R.id.action_sync){
            if (NetWorkUtils.temInternet(ListRelatorioActivity.this)){
                iniciarSinc();
            }else{
                Toast.makeText(ListRelatorioActivity.this, "Verifique sua conexão com a internet e tente novamente!",Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void iniciarSinc() {
        GetJsonToken getJsonToken = new GetJsonToken(ListRelatorioActivity.this, true);
        getJsonToken.execute();
    }


    public void cadastrar(View view) {
        Intent intent = new Intent(view.getContext(), CadRelatorioActivity.class);
        startActivityForResult(intent, PAR_CAD_RELATORIO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        adpRelatorios = repositorioLaudo.buscaLaudos(this);
        lstRelatorios.setAdapter(adpRelatorios);

    }

}
