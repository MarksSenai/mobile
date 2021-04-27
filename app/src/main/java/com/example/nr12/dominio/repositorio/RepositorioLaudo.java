package com.example.nr12.dominio.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.nr12.R;
import com.example.nr12.adapter.LaudoArrayAdapter;
import com.example.nr12.adapter.MaquinaArrayAdapter;
import com.example.nr12.dominio.entidades.Cliente;
import com.example.nr12.dominio.entidades.Laudo;
import com.example.nr12.dominio.entidades.Maquina;
import com.example.nr12.dominio.entidades.PontoPerigo;
import com.example.nr12.dominio.entidades.TipoMaquina;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Fabiano on 22/11/2017.
 */

public class RepositorioLaudo {

    private SQLiteDatabase conn;

    public RepositorioLaudo(SQLiteDatabase conn){ this.conn = conn; }

    private ContentValues preencheContentValues(Laudo Laudo){

        ContentValues values = new ContentValues();
        values.put(Laudo.ID, Laudo.getId());
        values.put(Laudo.MAQUINAID, Laudo.getMaquina().getIdLocal());
        values.put(Laudo.STATUS, Laudo.getStatus());
        values.put(Laudo.DATAINICIAL, Laudo.getData_inicial().getTime());

        return values;
    }

    public void excluir(long id){
        try{
            conn.delete(Laudo.TABELA, " "+Laudo.ID+" = ? ", new String[]{ String.valueOf(id) });
        }catch(Exception ex){
            Log.i("INFO", "Erro ao excluir do banco: "+ex.getMessage());
        }
    }

    public void alterar(Laudo laudo){
        try{
            ContentValues values = preencheContentValues(laudo);
            conn.update(Laudo.TABELA, values, " "+Laudo.ID+" = ? ", new String[]{ String.valueOf(laudo.getId()) });
        }catch(Exception ex){
            Log.i("INFO", "Erro ao alterar registro no banco: "+ex.getMessage());
        }
    }

    public long inserir(Laudo laudo){
        long idGerado = 0;
        try{
            ContentValues values = preencheContentValues(laudo);
            idGerado =  conn.insert(Laudo.TABELA, null, values);
        }catch(SQLException ex){
            Log.i("INFO", "Erro ao cadastrar registro no banco: "+ex.getMessage());
        }
        return idGerado;
    }

    public LaudoArrayAdapter buscaLaudos(Context context) {
        LaudoArrayAdapter adpLaudos = new LaudoArrayAdapter(context, R.layout.item_laudo);
        try {
            Cursor cursor = conn.query(Laudo.TABELA, null, null, null, null, null, Laudo.ID+" DESC");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Laudo laudo = new Laudo();

                    laudo.setId(cursor.getLong(cursor.getColumnIndexOrThrow(Laudo.ID)));
                    laudo.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(Laudo.STATUS)));
                    laudo.setData_inicial(new Date(cursor.getLong(cursor.getColumnIndex(Laudo.DATAINICIAL))));

                    RepositorioMaquina repositorioMaquina = new RepositorioMaquina(this.conn);
                    Maquina maquina = new Maquina();
                    maquina = repositorioMaquina.buscaMaquinaId(cursor.getLong(cursor.getColumnIndexOrThrow(Laudo.MAQUINAID)));

                    RepositorioPontoPerigo repositorioPontoPerigo = new RepositorioPontoPerigo(this.conn);
                    List<PontoPerigo> listPontoPerigo = repositorioPontoPerigo.buscaPontosPorLaudo(laudo.getId());

                    laudo.setPontoPerigos(listPontoPerigo);

                    laudo.setMaquina(maquina);
                    if(maquina.getId()!=0){
                        laudo.setMaquinaId(maquina.getId());
                    }

                    adpLaudos.add(laudo);

                } while (cursor.moveToNext());
            }
        }catch (Exception ex){
            Log.i("INFO", "Erro ao consultar registro no banco: "+ex.getMessage());
        }
        return adpLaudos;
    }



}
