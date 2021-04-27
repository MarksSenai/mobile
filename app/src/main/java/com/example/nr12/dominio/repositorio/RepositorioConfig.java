package com.example.nr12.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nr12.dominio.entidades.Config;


/**
 * Created by Fabiano on 25/09/2017.
 */

public class RepositorioConfig {

    private SQLiteDatabase conn;

    public RepositorioConfig(SQLiteDatabase conn){ this.conn = conn; }

    private ContentValues preencheContentValues(Config config){

        ContentValues values = new ContentValues();

        values.put(Config.URL, config.getUrl());
        values.put(Config.EMAIL, config.getEmail());
        values.put(Config.SENHA, config.getSenha());

        return values;
    }

    public Config buscaConfiguracoes() {
        Cursor cursor = conn.query(Config.TABELA, null, null, null, null, null, null);
        Config config = new Config();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            config.setId(cursor.getLong(cursor.getColumnIndexOrThrow(Config.ID)));
            config.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(Config.URL)));
            config.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(Config.EMAIL)));
            config.setSenha(cursor.getString(cursor.getColumnIndexOrThrow(Config.SENHA)));
        }
        return config;
    }

    public void inserir(Config config){
        try{
            ContentValues values = preencheContentValues(config);
            conn.insertOrThrow(Config.TABELA, null, values);
        }catch(Exception ex){
            Log.i("INFO", "Erro ao cadastrar registro no banco: "+ex.getMessage());
        }
    }

    public void alterar(Config config){
        try{
            ContentValues values = preencheContentValues(config);
            conn.update(Config.TABELA, values, " "+Config.ID+" = ? ", new String[]{ String.valueOf(config.getId()) });
        }catch(Exception ex){
            Log.i("INFO", "Erro ao alterar registro no banco: "+ex.getMessage());
        }
    }

}
