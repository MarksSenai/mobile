package com.example.nr12;


import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;

import com.example.nr12.app.MessageBox;
import com.example.nr12.database.DataBase;
import com.example.nr12.dominio.entidades.Cliente;
import com.example.nr12.dominio.entidades.Laudo;
import com.example.nr12.dominio.entidades.Maquina;
import com.example.nr12.dominio.entidades.Perigo;
import com.example.nr12.dominio.entidades.PontoPerigo;
import com.example.nr12.dominio.entidades.Risco;

import com.example.nr12.dominio.repositorio.RepositorioLaudo;
import com.example.nr12.dominio.repositorio.RepositorioPontoPerigo;
import com.example.nr12.dominio.repositorio.RepositorioPerigoPontoPerigo;
import com.example.nr12.dominio.repositorio.RepositorioRiscoPontoPerigo;
import com.example.nr12.fragments.GeneralFragment;
import com.example.nr12.fragments.ItemFragment;
import com.example.nr12.fragments.OtherFragment;
import com.example.nr12.services.ServiceLaudo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CadRelatorioActivity
        extends AppCompatActivity
        implements GeneralFragment.OnFragmentInteractionListener,
        ItemFragment.OnFragmentInteractionListener {

    public static final String PAR_CLIENTE = "CLIENTE";
    public static final String PAR_MAQUINA = "MAQUINA";
    public static final String PAR_LAUDO = "LAUDO";
    public static final String PAR_PONTOPERIGO = "PONTOPERIGO";

    public static final int PAR_CONSULTA_CLIENTE = 1;
    public static final int PAR_CONSULTA_MAQUINA = 2;
    public static final int PAR_CAD_PONTO_PERIGO = 3;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton fabAddPontoPerigo;
    private Toolbar toolbar;

    private Cliente cliente;
    private Maquina maquina;
    private Laudo laudo;

    private TextView txtViewCLiente;
    private TextView txtViewMaquina;

    private DataBase dataBase;
    private SQLiteDatabase conn;

    private RepositorioLaudo repositorioLaudo;
    private RepositorioPontoPerigo repositorioPontoPerigo;
    private RepositorioPerigoPontoPerigo repositorioPerigoPontoPerigo;
    private RepositorioRiscoPontoPerigo repositorioRiscoPontoPerigo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_relatorio);

        criaConexao();

        mTabLayout          = (TabLayout) findViewById(R.id.tabs);
        mViewPager          = (ViewPager) findViewById(R.id.container);
        fabAddPontoPerigo   = (FloatingActionButton)findViewById(R.id.fab);
        toolbar             = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Botao adicionar item
        fabAddPontoPerigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PontoPerigoActivity.class);
                startActivityForResult(intent, PAR_CAD_PONTO_PERIGO);
            }
        });


        Bundle bundle = getIntent().getExtras();
        if((bundle != null) && (bundle.containsKey(CadRelatorioActivity.PAR_LAUDO))){
            laudo = (Laudo)bundle.getSerializable(CadRelatorioActivity.PAR_LAUDO);

            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Configure o ViewPager com o adaptador de seções.
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }else{
            laudo = new Laudo();
            List<PontoPerigo> pontoPer = new ArrayList<PontoPerigo>();
            laudo.setPontoPerigos(pontoPer);

            Calendar calendar = Calendar.getInstance();
            laudo.setData_inicial(calendar.getTime());
            //laudo.setHora();
            laudo.setStatus("Em Elaboração");

            Intent intent = new Intent(this, SelecionaClienteActivity.class);
            startActivityForResult(intent, PAR_CONSULTA_CLIENTE);
        }

        //ExibeDataListener listener = new ExibeDataListener();

    }

    private void criaConexao(){
        try{
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();
            /*
            Snackbar.make(layoutConfig, R.string.message_conexao_sucesso, Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_ok, null).show();
             */
            repositorioLaudo = new RepositorioLaudo(conn);
            repositorioPontoPerigo = new RepositorioPontoPerigo(conn);
            repositorioRiscoPontoPerigo = new RepositorioRiscoPontoPerigo(conn);
            repositorioPerigoPontoPerigo = new RepositorioPerigoPontoPerigo(conn);
        }catch(SQLException ex){
            Log.i(getString(R.string.title_info), "Erro ao consultar o banco:" + ex.getMessage());
            MessageBox.show(this, getString(R.string.title_erro), "Erro ao consultar o banco:" + ex.getMessage());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);

        if(laudo.getId() != null){
            menu.getItem(1).setVisible(true);
            menu.getItem(2).setVisible(true);
        }


        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_salvar:
                salvar();
                break;

            case R.id.action_enviar:
                enviar();
                finish();
                break;

            case R.id.action_excluir:
                excluir();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void enviar() {
        laudo.setPontoPerigos(repositorioPontoPerigo.buscaPontosPorLaudo(laudo.getId()));
        ServiceLaudo serviceLaudo = new ServiceLaudo(CadRelatorioActivity.this);
        serviceLaudo.enviarLaudo(laudo);
    }

    private void salvar(){
        if(validar() == false){
            if(laudo.getId() == null){
                try{
                    laudo.setId(repositorioLaudo.inserir(laudo));
                    for (PontoPerigo pontoPerigo: laudo.getPontoPerigos()) {
                        pontoPerigo.setLaudoId(laudo.getId());
                        pontoPerigo.setId(repositorioPontoPerigo.inserir(pontoPerigo));
                        for (Perigo perigo : pontoPerigo.getPerigos()){
                            repositorioPerigoPontoPerigo.inserir(perigo.getId(), pontoPerigo.getId());
                        }
                        for (Risco risco : pontoPerigo.getRiscos()){
                            repositorioRiscoPontoPerigo.inserir(risco.getId(), pontoPerigo.getId());
                        }
                    }
                    finish();
                }catch (SQLException ex){
                    MessageBox.showAlert(this,"Erro", ex.getMessage());
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                Log.i("TESTE","salvar() - Id diferente de null!!!");
                repositorioLaudo.alterar(laudo);
                finish();
            }
        }
    }

    private boolean validar(){

        boolean res = false;


        return res;
    }

    private void excluir (){
        try{
            repositorioLaudo.excluir(laudo.getId());
        }catch(Exception ex){
            Log.i("INFO", "Erro ao excluir o registro:" + ex.getMessage());
            MessageBox.show(this, "Erro", "Erro ao excluir o registro:" + ex.getMessage());
        }

    }

    private boolean isCampoVazio(String valor) {
        boolean resultado  = (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
        return resultado;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public List<PontoPerigo> getPontoPerigoList() {
        return null;
    }

    @Override
    public void addPontoPerigo(PontoPerigo pontoPerigo) {

    }

    public Laudo getLaudo() {
        return laudo;
    }

    public void setDataInicial(Date dataInicial) {
        laudo.setData_inicial(dataInicial);
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
                        //laudo.setCliente(cliente);
                        Intent intent = new Intent(this, SelecionaMaquinaActivity.class);
                        intent.putExtra(CadRelatorioActivity.PAR_CLIENTE, cliente);
                        startActivityForResult(intent, PAR_CONSULTA_MAQUINA);
                    }
                    break;

                case RESULT_CANCELED:
                    if(laudo.getMaquina() == null){
                        finish();
                    }/*else if(cliente == null){
                        break;
                    }else if(maquina == null){
                        finish();
                    }*/
                    break;
            }
        }

        if (requestCode == PAR_CONSULTA_MAQUINA) {
            switch (resultCode){
                case RESULT_OK:
                    if(parametros != null){
                        cliente = (Cliente) parametros.getSerializable(CadRelatorioActivity.PAR_CLIENTE);
                        maquina = (Maquina) parametros.getSerializable(CadRelatorioActivity.PAR_MAQUINA);
                        //laudo.setCliente(cliente);
                        laudo.setMaquina(maquina);
                        cliente = null;
                        maquina = null;

                    }
                    break;

                case RESULT_CANCELED:
                    Log.i("onActivityResult", "PAR_CONSULTA_MAQUINA - RESULT_CANCELED");
                    if(laudo.getMaquina() == null || cliente != null){
                        Intent intent = new Intent(this, SelecionaClienteActivity.class);
                        startActivityForResult(intent, PAR_CONSULTA_CLIENTE);
                    }
                    break;
            }
        }

        if (requestCode == PAR_CAD_PONTO_PERIGO) {
            switch (resultCode){
                case RESULT_OK:
                    if(parametros != null){
                        PontoPerigo pontoPerigo = (PontoPerigo) parametros.getSerializable(CadRelatorioActivity.PAR_PONTOPERIGO);
                        //if(pontoPerigo.getId() != 0){
                            laudo.getPontoPerigos().add(pontoPerigo);
                        //}
                        /*
                        cliente = (Cliente) parametros.getSerializable(CadRelatorioActivity.PAR_CLIENTE);
                        maquina = (Maquina) parametros.getSerializable(CadRelatorioActivity.PAR_MAQUINA);
                        //laudo.setCliente(cliente);
                        laudo.setMaquina(maquina);
                        cliente = null;
                        maquina = null;
                        */
                    }
                    break;

                case RESULT_CANCELED:
                    Log.i("onActivityResult", "PAR_CAD_PONTO_PERIGO - RESULT_CANCELED");
                    break;
            }
        }

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Configure o ViewPager com o adaptador de seções.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    /*
                    GeneralFragment generalFragment = new GeneralFragment();
                    Intent intent = new Intent();
                    intent.putExtra(CadRelatorioActivity.PAR_LAUDO, laudo);
                    generalFragment.setArguments(intent.getExtras());
                    return generalFragment;
                    */
                    return new GeneralFragment();
                case 1:
                    return new ItemFragment();
                case 2:
                    return new OtherFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Geral";
                case 1:
                    return "Itens";
                case 2:
                    return "Outros";
            }
            return null;
        }
    }

}

