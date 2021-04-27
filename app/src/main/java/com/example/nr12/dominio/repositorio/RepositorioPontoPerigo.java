package com.example.nr12.dominio.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.nr12.dominio.entidades.Perigo;
import com.example.nr12.dominio.entidades.PontoPerigo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by root on 10/10/17.
 */

public class RepositorioPontoPerigo {
    private SQLiteDatabase conn;

    public RepositorioPontoPerigo(SQLiteDatabase conn){ this.conn = conn; }

    private ContentValues preencheContentValues(PontoPerigo pontoPerigo){
        ContentValues values = new ContentValues();
        values.put(PontoPerigo.LAUDOID, pontoPerigo.getLaudoId());
        values.put(PontoPerigo.PONTOPERIGO, pontoPerigo.getPontoPerigo());
        values.put(PontoPerigo.FACE, pontoPerigo.getFace());
        values.put(PontoPerigo.SISTEMASEGURANCAID, pontoPerigo.getSistemaSeguranca().getId());
        values.put(PontoPerigo.ANEXO1, pontoPerigo.getAnexo1());

        return values;
    }

    public long inserir(PontoPerigo pontoPerigo){
        long idGerado = 0;
        try{
            ContentValues values = preencheContentValues(pontoPerigo);
            idGerado =  conn.insertOrThrow(PontoPerigo.TABELA, null, values);
        }catch(SQLException ex){
            Log.i("INFO", "Erro ao cadastrar registro no banco: "+ex.getMessage());
        }
        return idGerado;
    }

    public List<PontoPerigo> buscaPontosPorLaudo(long idLaudo) {
        List<PontoPerigo> pontosPerigos = new ArrayList<PontoPerigo>();
        try {
            //Cursor cursor = conn.query(PontoPerigo.TABELA, null, null, null, null, "LAUDOID = "+ idLaudo, idLaudo+" DESC");
            Cursor cursor = conn.query(PontoPerigo.TABELA, null, " LAUDOID = ? ", new String[]{String.valueOf(idLaudo)}, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    PontoPerigo ponto = new PontoPerigo();

                    ponto.setId(cursor.getLong(cursor.getColumnIndexOrThrow(PontoPerigo.ID)));
                    ponto.setLaudoId(cursor.getLong(cursor.getColumnIndexOrThrow(PontoPerigo.LAUDOID)));
                    ponto.setPontoPerigo(cursor.getString(cursor.getColumnIndexOrThrow(PontoPerigo.PONTOPERIGO)));
                    ponto.setFace(cursor.getString(cursor.getColumnIndexOrThrow(PontoPerigo.FACE)));
                    ponto.setSistemaSegurancaId(cursor.getInt(cursor.getColumnIndexOrThrow(PontoPerigo.SISTEMASEGURANCAID)));
                    ponto.setAnexo1(cursor.getString(cursor.getColumnIndexOrThrow(PontoPerigo.ANEXO1)));

                    RepositorioPerigoPontoPerigo repositorioPerigoPontoPerigo = new RepositorioPerigoPontoPerigo(this.conn);
                    ponto.setPerigos(repositorioPerigoPontoPerigo.buscaPerigoPontoPerigo(ponto.getId()));

                    RepositorioRiscoPontoPerigo repositorioRiscoPontoPerigo = new RepositorioRiscoPontoPerigo(this.conn);
                    ponto.setRiscos(repositorioRiscoPontoPerigo.buscaRiscoPontoPerigo(ponto.getId()));

                    pontosPerigos.add(ponto);

                } while (cursor.moveToNext());
            }
        }catch (Exception ex){
            Log.i("INFO", "Erro ao consultar registro no banco: "+ex.getMessage());
        }
        return pontosPerigos;
    }
}
