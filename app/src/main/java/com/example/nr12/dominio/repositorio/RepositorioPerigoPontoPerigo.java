package com.example.nr12.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nr12.dominio.entidades.Perigo;
import com.example.nr12.dominio.entidades.PontoPerigo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by root on 10/10/17.
 */

public class RepositorioPerigoPontoPerigo {
    private SQLiteDatabase conn;

    public RepositorioPerigoPontoPerigo(SQLiteDatabase conn){ this.conn = conn; }

    private ContentValues preencheContentValues(int idPerigo, long idPontoPerigo){
        ContentValues values = new ContentValues();
        values.put("PERIGOID", idPerigo);
        values.put("PONTOPERIGOID", idPontoPerigo);

        return values;
    }

    public void inserir(int idPerigo, long idPontoPerigo){
        try{
            ContentValues values = preencheContentValues(idPerigo, idPontoPerigo);
            conn.insertOrThrow("PERIGOPONTOPERIGO", null, values);
        }catch(SQLException ex) {
            Log.i("INFO", "Erro ao cadastrar registro no banco: " + ex.getMessage());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<Perigo> buscaPerigoPontoPerigo(long idPontoPerigo) {
        List<Perigo> perigos = new ArrayList<Perigo>();
        try {
            //Cursor cursor = conn.query(PontoPerigo.TABELA, null, null, null, null, "LAUDOID = "+ idLaudo, idLaudo+" DESC");
            Cursor cursor = conn.query("PERIGOPONTOPERIGO", null, " PONTOPERIGOID = ? ", new String[]{String.valueOf(idPontoPerigo)}, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Perigo perigo = new Perigo();

                    perigo.setId(cursor.getInt(cursor.getColumnIndexOrThrow("PERIGOID")));

                    perigos.add(perigo);

                } while (cursor.moveToNext());
            }
        }catch (Exception ex){
            Log.i("INFO", "Erro ao consultar registro no banco: "+ex.getMessage());
        }
        return perigos;
    }
}
