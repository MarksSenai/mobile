package com.example.nr12.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nr12.dominio.entidades.PontoPerigo;
import com.example.nr12.dominio.entidades.Risco;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by root on 10/10/17.
 */

public class RepositorioRiscoPontoPerigo {
    private SQLiteDatabase conn;

    public RepositorioRiscoPontoPerigo(SQLiteDatabase conn){ this.conn = conn; }
    
    private ContentValues preencheContentValues(int idRisco, long idPontoPerigo){
        ContentValues values = new ContentValues();
        values.put("RISCOID", idRisco);
        values.put("PONTOPERIGOID", idPontoPerigo);

        return values;
    }

    public void inserir(int idRisco, long idPontoPerigo){

        try{
            ContentValues values = preencheContentValues(idRisco, idPontoPerigo);
            conn.insertOrThrow("RISCOPONTOPERIGO", null, values);
        }catch(SQLException ex) {
            Log.i("INFO", "Erro ao cadastrar registro no banco: " + ex.getMessage());
        }
    }

    public List<Risco> buscaRiscoPontoPerigo(long idPontoPerigo) {
        List<Risco> riscos = new ArrayList<Risco>();
        try {
            Cursor cursor = conn.query("RISCOPONTOPERIGO", null, " PONTOPERIGOID = ? ", new String[]{String.valueOf(idPontoPerigo)}, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Risco risco = new Risco();

                    risco.setId(cursor.getInt(cursor.getColumnIndexOrThrow("RISCOID")));

                    riscos.add(risco);

                } while (cursor.moveToNext());
            }
        }catch (Exception ex){
            Log.i("INFO", "Erro ao consultar registro no banco: "+ex.getMessage());
        }
        return riscos;
    }
}
