package com.example.nr12;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import android.widget.Spinner;
import android.widget.Toast;

import com.example.nr12.app.MessageBox;
import com.example.nr12.app.ViewHelper;
import com.example.nr12.database.DataBase;
import com.example.nr12.dominio.entidades.Perigo;
import com.example.nr12.dominio.entidades.PerigoRisco;
import com.example.nr12.dominio.entidades.PontoPerigo;
import com.example.nr12.dominio.entidades.Risco;
import com.example.nr12.dominio.entidades.SistemaSeguranca;
import com.example.nr12.dominio.repositorio.RepositorioPerigo;
import com.example.nr12.dominio.repositorio.RepositorioRisco;
import com.example.nr12.dominio.repositorio.RepositorioPerigoRisco;
import com.example.nr12.dominio.repositorio.RepositorioSistemaSeguranca;

/**
 * Created by Gui on 25/09/2017.
 */

public class PontoPerigoActivity extends AppCompatActivity {//implements OnItemSelectedListener {

    private EditText edtItem;
    private Spinner spnFace;
    private Spinner spnPerigo;
    private Spinner spnRisco;
    private Spinner spnSistemaSeguranca;
    private Spinner spnCumpreAnexo;
    private ArrayAdapter<String> adpFace;
    private ArrayAdapter<Perigo> adpSpnPerigo;//Lista de Perigos.
    private ArrayAdapter<PerigoRisco> adpPerigoRisco;//Lista de Perigos x Riscos.
    private ArrayAdapter<Risco> adpRisco;//Lista de Riscos cadastrados no banco.
    private ArrayAdapter<Risco> adpRiscoList;//Lista de Riscos gerada pelo usuário
    private ArrayAdapter<String> adpSistemaSeguranca;
    private ArrayAdapter<String> adpCumpreAnexo;

    private ImageButton btnAddPerigo;
    private ImageButton btnAddRisco;


    private RepositorioRisco repositorioRisco;
    private RepositorioPerigo repositorioPerigo;
    private RepositorioPerigoRisco repositorioPerigoRisco;
    private RepositorioSistemaSeguranca repositorioSistemaSeguranca;

    private DataBase dataBase;
    private SQLiteDatabase conn;

    //private Perigo perigoSelecionado; //Perigo selecionado
    private Risco itemRisco;//Risco selecionado
    private Risco riscoRepetido;


    private PontoPerigo pontoPerigo;
    private Perigo perigo;

    ArrayAdapter<Perigo> adapterPerigo;
    ArrayList<String> itemListString;
    private ListView listViewPerigo;
   // private ListView listV;

    private ArrayAdapter<Risco> adapterRiscos;
    private ArrayList<String> itemListRiscos;
    private ListView listVRiscos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponto_perigo);

        criaConexao();

        edtItem             = (EditText) findViewById(R.id.edtItem);
        spnFace             = (Spinner) findViewById(R.id.spnFace);
        spnSistemaSeguranca = (Spinner) findViewById(R.id.spnSistemaSeguranca);
        spnCumpreAnexo      = (Spinner) findViewById(R.id.spnCumpreAnexo);
        spnPerigo           = (Spinner) findViewById(R.id.spnPerigo);
        spnRisco            = (Spinner) findViewById(R.id.spnRisco);
        btnAddPerigo        = (ImageButton) findViewById(R.id.btnAddPerigo);
        btnAddRisco         = (ImageButton) findViewById(R.id.btnAddRisco);
        listViewPerigo      = (ListView) findViewById(R.id.lstPerigo);
        listVRiscos         = (ListView) findViewById(R.id.lstRisco);


        adpFace = ViewHelper.createArrayAdapter(this, spnFace, R.array.faces);
        spnFace.setAdapter(adpFace);

        adpSistemaSeguranca = repositorioSistemaSeguranca.buscaTodosAdapter(this);
        spnSistemaSeguranca.setAdapter(adpSistemaSeguranca);

        adpCumpreAnexo = ViewHelper.createArrayAdapter(this, spnCumpreAnexo, R.array.cumpreAnexo);
        spnCumpreAnexo.setAdapter(adpCumpreAnexo);

        adpSpnPerigo = repositorioPerigo.buscaTodosAdapter(this);
        spnPerigo.setAdapter(adpSpnPerigo);

        adpRisco = new ArrayAdapter<Risco>(this, android.R.layout.simple_list_item_1);

        //spnPerigo.setOnItemSelectedListener(this);
        //spnRisco.setOnItemSelectedListener(this);


        /**
         * Recupera parametro passados pela intent.
         */
        Bundle bundle = getIntent().getExtras();
        if((bundle != null) && (bundle.containsKey(CadRelatorioActivity.PAR_PONTOPERIGO))){
            pontoPerigo = (PontoPerigo) bundle.getSerializable(CadRelatorioActivity.PAR_PONTOPERIGO);
            //preencherDados();
        }else{
            pontoPerigo = new PontoPerigo();
            List<Perigo> perigos = new ArrayList<Perigo>();
            List<Risco> riscos = new ArrayList<Risco>();
            pontoPerigo.setPerigos(perigos);
            pontoPerigo.setRiscos(riscos);
        }


        /*****************************Lista de Perigos*****************************************/


        ImageButton btnAddPerigo = (ImageButton) findViewById(R.id.btnAddPerigo); //Botão para adição de perigos à lista.

        //String [] perigoItems = {""};

        //itemListString = new ArrayList<String>(Arrays.asList(perigoItems));
        //adapterPerigo = new ArrayAdapter<String>(this, R.layout.perigo_list, R.id.txtview, itemListString);

        adapterPerigo = new ArrayAdapter<Perigo>(this, android.R.layout.simple_list_item_1, pontoPerigo.getPerigos());
       // listV.setAdapter(adapterPerigo);

        listViewPerigo.setAdapter(adapterPerigo);

        btnAddPerigo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                boolean status = false;
                //perigoSelecionado é o perigo adicionado ao Ponto de Perigo
                Perigo perigoSelecionado = (Perigo) spnPerigo.getSelectedItem();
                pontoPerigo.getPerigos().add((Perigo)spnPerigo.getSelectedItem());
                //adapterPerigo.add(perigoSelecionado);
                adapterPerigo.notifyDataSetChanged();

                //Removido do spinner o perigo adicionado ao Ponto Perigo.
                adpSpnPerigo.remove(perigoSelecionado);
                //adpSpnPerigo.notifyDataSetChanged();

                //itemListString.add(perigoSelecionado.getNome());

                //Riscos associados à perigos.
                adpPerigoRisco = repositorioPerigoRisco.buscaPerigoRiscoAdapter(view.getContext());

                //Riscos armazenados no banco.
                adpRiscoList =  repositorioRisco.buscaRiscoAdapter(view.getContext());

                adpPerigoRisco = repositorioPerigoRisco.buscaPerigoRiscoAdapter(getApplicationContext());
                adpRiscoList =  repositorioRisco.buscaRiscoAdapter(getApplicationContext());

                for(int i = 0; i < ((Integer) adpPerigoRisco.getCount()); i++){

                    int perigo = adpPerigoRisco.getItem(i).getPerigo();//Perigo da tabela PerigoRisco
                    int risco = adpPerigoRisco.getItem(i).getRisco();//Risco da tabela PerigoRisco

                    if(perigo == perigoSelecionado.getId()){//Compara o perigo adicionado ao de PerigoRisco.

                        for(int x = 0; x < adpRiscoList.getCount(); x++){

                            if(!adpRisco.isEmpty()){

                                for(int j = 0; j < adpRisco.getCount(); j++){

                                    int riscoListId = adpRiscoList.getItem(x).getId();
                                    int riscoId = adpRisco.getItem(j).getId();
                                    /*Compara os riscos de Ponto de Perigo com os riscos às serem adicionados*/
                                    if(riscoId != riscoListId){
                                       /*Compara o risco de PerigoRisco com o riscos do banco*/
                                        if(risco == riscoListId){

                                            status = true;
                                        }
                                     }
                                    if(riscoId == riscoListId){
                                        adpRiscoList.remove(adpRiscoList.getItem(x));
                                        status = false;
                                    }
                                }
                            }
                            if(adpRisco.isEmpty()){

                                int riscoAdd = (int) adpRiscoList.getItem(x).getId();//Risco que vem do banco.

                                if(risco == riscoAdd){//Compara o Risco de PerigoRisco com o Risco à ser adicionado
                                    Risco riscoContent = new Risco();
                                    riscoContent.setId((int) adpRiscoList.getItem(x).getId());
                                    riscoContent.setNome(adpRiscoList.getItem(x).getNome());
                                    Log.i("EMPTY RISCO NAME: ", riscoContent.getNome());
                                    adpRisco.add(riscoContent);
                                }
                            }
                            if(status){
                                Risco riscoContent = new Risco();
                                riscoContent.setId((int) adpRiscoList.getItem(x).getId());
                                riscoContent.setNome(String.valueOf(adpRiscoList.getItem(x).getNome()));
                                adpRisco.add(riscoContent);
                            }
                        }
                    }
                }
                adpSpnPerigo.remove(perigoSelecionado);
                spnRisco.setAdapter(adpRisco);
            }
        });
        /*****************************Lista de Riscos*****************************************/

        /*
        String [] riscosItems = {""};
        itemListRiscos = new ArrayList<String>(Arrays.asList(riscosItems));
        adapterRiscos = new ArrayAdapter<String>(this, R.layout.risco_list, R.id.txtviewRisco, itemListRiscos);
        */
        adapterRiscos = new ArrayAdapter<Risco>(this, android.R.layout.simple_list_item_1, pontoPerigo.getRiscos());
        listVRiscos.setAdapter(adapterRiscos);

        btnAddRisco.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Risco riscoSelecionado = (Risco) spnRisco.getSelectedItem();
                pontoPerigo.getRiscos().add((Risco)spnRisco.getSelectedItem());
                //itemListRiscos.add(riscoSelecionado.getNome());
                adapterRiscos.notifyDataSetChanged();

                adpRisco.remove(riscoSelecionado);
                adpRisco.notifyDataSetChanged();
            }


        });


       // listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
       //     @Override
        //    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //    }


        /**
         * Exclusão da lista de perigos e riscos selecionados
         */

         listViewPerigo.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, long l) {

                AlertDialog.Builder perigoDialog = new AlertDialog.Builder(listViewPerigo.getContext());
                perigoDialog.setMessage("Excluir Perigo?");
                perigoDialog.setPositiveButton("Excluir", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Perigo perigo = (Perigo) parent.getItemAtPosition(position);
                        /*
                        Perigo perigo = new Perigo();
                        perigo.setId((int) parent.getItemIdAtPosition(position));
                        perigo.setNome(String.valueOf(parent.getItemAtPosition(position)));
                        */
                        pontoPerigo.getPerigos().remove(perigo);
                        //adapterPerigo.remove(perigo);
                        adapterPerigo.notifyDataSetChanged();
                        adpSpnPerigo.add(perigo);
                        adpSpnPerigo.notifyDataSetChanged();
                    }
                });

                perigoDialog.setNegativeButton("Cancelar", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(PontoPerigoActivity.this.getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                perigoDialog.show();
           }
        });

        listVRiscos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, long l) {

                AlertDialog.Builder riscoDialog = new AlertDialog.Builder(listVRiscos.getContext());
                riscoDialog.setMessage("Excluir Risco?");
                riscoDialog.setPositiveButton("Excluir", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Risco risco = (Risco) parent.getItemAtPosition(position);
                        pontoPerigo.getRiscos().remove(risco);
                        //Risco risco = new Risco();
                        //risco.setId((int) parent.getItemIdAtPosition(position));
                        //risco.setNome(String.valueOf(parent.getItemAtPosition(position)));
                        //adapterRiscos.remove(itemListRiscos.get(position));
                        adapterRiscos.notifyDataSetChanged();
                        adpRisco.add(risco);
                        adpRisco.notifyDataSetChanged();
                    }
                });

                riscoDialog.setNegativeButton("Cancelar", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(PontoPerigoActivity.this.getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                riscoDialog.show();
            }
        });
    }

    private void criaConexao(){
        try{
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();
            repositorioRisco = new RepositorioRisco(conn);
            repositorioPerigo = new RepositorioPerigo(conn);
            repositorioPerigoRisco = new RepositorioPerigoRisco(conn);
            repositorioSistemaSeguranca = new RepositorioSistemaSeguranca(conn);

        }catch(SQLException ex){
            Log.i(getString(R.string.title_info), "Erro ao consultar o banco:" + ex.getMessage());
            MessageBox.show(this, getString(R.string.title_erro), "Erro ao consultar o banco:" + ex.getMessage());
        }
    }

    /*
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        parent.getItemAtPosition(position);
        switch (parent.getId()){
            case R.id.spnPerigo:
                perigoSelecionado = (Perigo) parent.getItemAtPosition(position);
                break;
            case R.id.spnRisco:
                itemRisco = (Risco) parent.getItemAtPosition(position);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
    */

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
                cadastrarPontoPerigo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cadastrarPontoPerigo() {
        if(validar() == false){
            if(pontoPerigo.getId() == 0){
                try{
                    Intent intent = new Intent();
                    intent.putExtra(CadRelatorioActivity.PAR_PONTOPERIGO, pontoPerigo);
                    setResult(RESULT_OK, intent);
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

        String item = edtItem.getText().toString();
        String face = String.valueOf(spnFace.getSelectedItem());
        SistemaSeguranca sistemaSeguranca = (SistemaSeguranca) spnSistemaSeguranca.getSelectedItem();
        String anexo1 = String.valueOf(spnCumpreAnexo.getSelectedItem());

        pontoPerigo.setPontoPerigo(item);
        pontoPerigo.setFace(face);
        pontoPerigo.setSistemaSeguranca(sistemaSeguranca);
        pontoPerigo.setAnexo1(anexo1);
        //pontoPerigo.setPerigos();
        //pontoPerigo.setRiscos();

        if(res = isCampoVazio(item)){
            edtItem.requestFocus();
        } else
        if(res = isCampoVazio(face)){
            spnFace.requestFocus();
        }else
        if(res = isCampoVazio(anexo1)){
            spnCumpreAnexo.requestFocus();
        }else
        if(res = isCampoVazio(sistemaSeguranca.getNome())){
            spnSistemaSeguranca.requestFocus();
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
