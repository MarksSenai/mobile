package com.example.nr12.dominio.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.nr12.dominio.entidades.Perigo;


/**
 * Created by root on 10/10/17.
 */

public class RepositorioPerigo {
    private SQLiteDatabase conn;

    public RepositorioPerigo(SQLiteDatabase conn){ this.conn = conn; }

    private ContentValues preencheContentValues(Perigo perigo){
        ContentValues values = new ContentValues();
        values.put(Perigo.ID, perigo.getId());
        values.put(Perigo.NOME, perigo.getNome());

        return values;
    }



    public ArrayAdapter<Perigo> buscaTodosAdapter (Context context) {
        ArrayAdapter<Perigo> adpPerigo = new ArrayAdapter<Perigo>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query(Perigo.TABELA, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Perigo perigo = new Perigo();

                perigo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Perigo.ID)));
                perigo.setNome(cursor.getString(cursor.getColumnIndexOrThrow(Perigo.NOME)));

//                Log.i("Perigo ID: ", "" + perigo.getId());
//                Log.i("Perigo Nome: ", "" + perigo.getNome());
                adpPerigo.add(perigo);
            } while (cursor.moveToNext());
        }
        return adpPerigo;
    }


    public void inserirOuAlterar(Perigo perigo){
        try{
            ContentValues values = preencheContentValues(perigo);
            int u = conn.update(Perigo.TABELA, values, " "+Perigo.ID+" = ? ", new String[]{ String.valueOf(perigo.getId()) });
            if (u == 0){
                conn.insertWithOnConflict(Perigo.TABELA, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            }
        }catch(Exception ex){
            Log.i("INFO", "Erro ao alterar registro no banco: "+ex.getMessage());
        }

    }
}
