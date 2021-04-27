package com.example.nr12.dominio.repositorio;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.nr12.dominio.entidades.Perigo;
import com.example.nr12.dominio.entidades.SistemaSeguranca;

/**
 * Created by Gui on 10/10/2017.
 */

public class RepositorioSistemaSeguranca {

    private SQLiteDatabase conn;

    public RepositorioSistemaSeguranca(SQLiteDatabase conn){ this.conn = conn; }

    private ContentValues preencheContentValues(SistemaSeguranca sistemaSeguranca){
        ContentValues values = new ContentValues();
        values.put(SistemaSeguranca.ID, sistemaSeguranca.getId());
        values.put(SistemaSeguranca.NOME, sistemaSeguranca.getNome());
        return values;
    }

    public void inserirOuAlterar(SistemaSeguranca sistemaSeguranca){
        try{
            ContentValues values = preencheContentValues(sistemaSeguranca);
            int u = conn.update(SistemaSeguranca.TABELA, values, " "+SistemaSeguranca.ID+" = ? ", new String[]{ String.valueOf(sistemaSeguranca.getId()) });
            if (u == 0){
                conn.insertWithOnConflict(SistemaSeguranca.TABELA, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            }
        }catch(Exception ex){
            Log.i("INFO", "Erro ao alterar registro no banco: "+ex.getMessage());
        }
    }

    public ArrayAdapter buscaTodosAdapter (Context context) {
        ArrayAdapter<SistemaSeguranca> adpSisSeg = new ArrayAdapter<SistemaSeguranca>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query(SistemaSeguranca.TABELA, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                SistemaSeguranca sisSeg = new SistemaSeguranca();

                sisSeg.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Perigo.ID)));
                sisSeg.setNome(cursor.getString(cursor.getColumnIndexOrThrow(Perigo.NOME)));

                Log.i("Perigo ID: ", "" + sisSeg.getId());
                Log.i("Perigo Nome: ", "" + sisSeg.getNome().toString());
                adpSisSeg.add(sisSeg);
            } while (cursor.moveToNext());
        }
        return adpSisSeg;
    }
}
