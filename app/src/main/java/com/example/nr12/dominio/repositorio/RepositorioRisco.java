package com.example.nr12.dominio.repositorio;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.nr12.dominio.entidades.Perigo;
import com.example.nr12.dominio.entidades.PerigoRisco;
import com.example.nr12.dominio.entidades.Risco;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gui on 10/10/2017.
 */

public class RepositorioRisco {

    private SQLiteDatabase conn;

    public RepositorioRisco(SQLiteDatabase conn){ this.conn = conn; }

    private ContentValues preencheContentValues(Risco risco){
        ContentValues values = new ContentValues();
        values.put(Risco.ID, risco.getId());
        values.put(Risco.NOME, risco.getNome());
        return values;
    }

    public void excluir(long id){
        try{
            conn.delete(Risco.TABELA, " "+Risco.ID+" = ? ", new String[]{ String.valueOf(id) });
        }catch(Exception ex){
            Log.i("INFO", "Erro ao excluir do banco: "+ex.getMessage());
        }
    }

    public void inserirOuAlterar(Risco risco){
        try{
            ContentValues values = preencheContentValues(risco);
            int u = conn.update(Risco.TABELA, values, " "+Risco.ID+" = ? ", new String[]{ String.valueOf(risco.getId()) });
            if (u == 0){
                conn.insertWithOnConflict(Risco.TABELA, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            }
        }catch(Exception ex){
            Log.i("INFO", "Erro ao alterar registro no banco: "+ex.getMessage());
        }
    }

    public List<Risco> buscarTodos(){

        List<Risco> riscos= new ArrayList<Risco>();

        Cursor resultado = conn.query(Risco.TABELA, null, null, null, null, null, null);

        if(resultado.getCount() > 0){
            resultado.moveToFirst();
            do{

                Risco risco = new Risco();

                risco.setId( resultado.getInt( resultado.getColumnIndexOrThrow( Risco.ID ) ) );
                risco.setNome( resultado.getString( resultado.getColumnIndexOrThrow( Risco.NOME ) ) );

                riscos.add(risco);

            }while (resultado.moveToNext());
        }
        return riscos;
    }

    public ArrayAdapter buscaRiscoAdapter(Context context){

        ArrayAdapter<Risco> adpRisco = new ArrayAdapter<Risco>
                (context, android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query(Risco.TABELA, null, null, null, null, null, null);
        if(cursor.getCount() > 0){

            cursor.moveToFirst();
            do{
                Risco risco = new Risco();

                risco.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Risco.ID)));
                risco.setNome(cursor.getString(cursor.getColumnIndexOrThrow(Risco.NOME)));
//                Log.i("RISCO ID: ", ""+ risco.getId());
//                Log.i("RISCO Nome: ", ""+ risco.getNome());
                adpRisco.add(risco);
            }while(cursor.moveToNext());

        }

        return adpRisco;

    }

    public ArrayAdapter buscaTodosAdapter (Context context){
           ArrayAdapter<Risco> adpRisco = new ArrayAdapter<Risco>(context, android.R.layout.simple_list_item_1);
        
           Cursor cursor = conn.query(Risco.TABELA, null, null, null, null, null, null);
           if(cursor.getCount() > 0){
               cursor.moveToFirst();

                   do{
                       Risco risco = new Risco();

                       risco.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Risco.ID)));
                       risco.setNome(cursor.getString(cursor.getColumnIndexOrThrow(Risco.NOME)));
                       Log.i("Risco ID: ", ""+ risco.getId());
                       Log.i("Risco Nome: ", ""+ risco.getNome().toString());
                       adpRisco.add(risco);
                   }while(cursor.moveToNext());



           }
           return adpRisco;

    }



}
