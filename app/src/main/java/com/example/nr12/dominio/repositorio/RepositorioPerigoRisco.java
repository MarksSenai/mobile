package com.example.nr12.dominio.repositorio;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.nr12.dominio.entidades.Perigo;
import com.example.nr12.dominio.entidades.PerigoRisco;
import com.example.nr12.dominio.entidades.Risco;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gui on 11/10/17.
 */

public class RepositorioPerigoRisco {
    private SQLiteDatabase conn;

    public RepositorioPerigoRisco(SQLiteDatabase conn){ this.conn = conn; }

    private ContentValues preencheContentValues(int perigoId, int riscoId){
        ContentValues values = new ContentValues();
        values.put("PERIGO", perigoId);
        values.put("RISCO", riscoId);

        return values;
    }

    public void inserir(int perigoId, int riscoId){
        try{
            ContentValues values = preencheContentValues(perigoId, riscoId);
            conn.insertOrThrow("PERIGORISCO", null, values);
        }catch (Exception ex){
            Log.i("INFO", "Erro ao cadastrar registro no banco: "+ex.getMessage());
        }
    }
    public ArrayAdapter<PerigoRisco> buscaPerigoRiscoAdapter (Context context) {
        ArrayAdapter<PerigoRisco> adpPerigo = new ArrayAdapter<PerigoRisco>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = conn.query(PerigoRisco.TABELA, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                PerigoRisco perigoRisco = new PerigoRisco();

                perigoRisco.setPerigo(cursor.getInt(cursor.getColumnIndexOrThrow(PerigoRisco.PERIGO)));
                perigoRisco.setRisco(cursor.getInt(cursor.getColumnIndexOrThrow(PerigoRisco.RISCO)));

//                Log.i("PERIGO ID: ", "" + perigoRisco.getPerigo());
//                Log.i("RISCO ID: ", "" + perigoRisco.getRisco());
                adpPerigo.add(perigoRisco);
            } while (cursor.moveToNext());
        }
        return adpPerigo;
    }

    public void excluir(int perigoId){
        try{
            conn.delete("PERIGORISCO", " "+"PERIGO"+" = ? ", new String[]{ String.valueOf(perigoId) });
        }catch(Exception ex){
            Log.i("INFO", "Erro ao excluir do banco: "+ex.getMessage());
        }
    }


}
