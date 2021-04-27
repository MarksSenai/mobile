package com.example.nr12.dominio.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.nr12.dominio.entidades.TipoMaquina;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabiano on 25/09/2017.
 */

public class RepositorioTipoMaquina {

    private SQLiteDatabase conn;

    public RepositorioTipoMaquina(SQLiteDatabase conn){ this.conn = conn; }

    private ContentValues preencheContentValues(TipoMaquina tipoMaquina){

        ContentValues values = new ContentValues();
        values.put(TipoMaquina.ID, tipoMaquina.getId());
        values.put(TipoMaquina.NOME, tipoMaquina.getNome());

        return values;
    }

    public void excluir(long id){
        try{
            conn.delete(TipoMaquina.TABELA, " "+TipoMaquina.ID+" = ? ", new String[]{ String.valueOf(id) });
        }catch(Exception ex){
            Log.i("INFO", "Erro ao excluir do banco: "+ex.getMessage());
        }
    }


    public void alterar(TipoMaquina tipoMaquina){
        try{
            ContentValues values = preencheContentValues(tipoMaquina);
            conn.update(TipoMaquina.TABELA, values, " "+TipoMaquina.ID+" = ? ", new String[]{ String.valueOf(tipoMaquina.getId()) });
        }catch(Exception ex){
            Log.i("INFO", "Erro ao alterar registro no banco: "+ex.getMessage());
        }
    }

    public void inserir(TipoMaquina tipoMaquina){
        try{
            ContentValues values = preencheContentValues(tipoMaquina);
            conn.insertOrThrow(TipoMaquina.TABELA, null, values);
        }catch(Exception ex){
            Log.i("INFO", "Erro ao cadastrar registro no banco: "+ex.getMessage());
        }
    }

    public List<TipoMaquina> buscarTodos(){

        List<TipoMaquina> tipoMaquinas= new ArrayList<TipoMaquina>();
        try {
            Cursor resultado = conn.query(TipoMaquina.TABELA, null, null, null, null, null, null);

            if (resultado.getCount() > 0) {
                resultado.moveToFirst();
                do {

                    TipoMaquina tipoMaquina = new TipoMaquina();

                    tipoMaquina.setId(resultado.getInt(resultado.getColumnIndexOrThrow(TipoMaquina.ID)));
                    tipoMaquina.setNome(resultado.getString(resultado.getColumnIndexOrThrow(TipoMaquina.NOME)));

                    tipoMaquinas.add(tipoMaquina);

                } while (resultado.moveToNext());
            }
        }catch(Exception ex){
            Log.i("INFO", "Erro ao consultar registro no banco: "+ex.getMessage());
        }
        return tipoMaquinas;
    }

    public void inserirOuAlterar(TipoMaquina tipoMaquina){
        try{
            ContentValues values = preencheContentValues(tipoMaquina);
            int u = conn.update(TipoMaquina.TABELA, values, " "+TipoMaquina.ID+" = ? ", new String[]{ String.valueOf(tipoMaquina.getId()) });
            if (u == 0){
                conn.insertWithOnConflict(TipoMaquina.TABELA, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            }
        }catch(Exception ex){
            Log.i("INFO", "Erro ao alterar registro no banco: "+ex.getMessage());
        }
    }

    /*
    public TipoMaquinaArrayAdapter buscarTodosTipoMaquinasAdapter(Context context){

        TipoMaquinaArrayAdapter adpTipoMaquinas = new TipoMaquinaArrayAdapter(context, R.layout.item_tipoMaquina);

        Cursor cursor = conn.query(TipoMaquina.TABELA, null, null, null, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                TipoMaquina tipoMaquina = new TipoMaquina();

                tipoMaquina.setId(cursor.getInt(cursor.getColumnIndex(TipoMaquina.IDLOCAL)));
                tipoMaquina.setNome(cursor.getString(cursor.getColumnIndex(TipoMaquina.NOME)));

                adpTipoMaquinas.add(tipoMaquina);

            }while(cursor.moveToNext());
        }
        return adpTipoMaquinas;
    }*/

    public ArrayAdapter buscaTodosAdapter(Context context){
        ArrayAdapter<TipoMaquina> adpTipoMaquinas = new ArrayAdapter<TipoMaquina>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query(TipoMaquina.TABELA, null, null, null, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                TipoMaquina tipoMaquina = new TipoMaquina();

                tipoMaquina.setId(cursor.getInt(cursor.getColumnIndex(TipoMaquina.ID)));
                tipoMaquina.setNome(cursor.getString(cursor.getColumnIndex(TipoMaquina.NOME)));

                adpTipoMaquinas.add(tipoMaquina);

            }while(cursor.moveToNext());
        }
        return adpTipoMaquinas;
    }

}
