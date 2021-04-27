package com.example.nr12.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.nr12.database.DataBase;
import com.example.nr12.dominio.entidades.Config;
import com.example.nr12.dominio.repositorio.RepositorioConfig;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gui on 04/10/2017.
 */

public class ServiceConfig {

    private Context contexto;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioConfig repositorioConfig;

    public ServiceConfig(Context contexto){
        this.contexto = contexto;
    }

    public JSONObject retornaJsonConfig() {
        JSONObject json = new JSONObject();
        Config config = buscarConfig();
        try{
            json.put("email", config.getEmail());
            json.put("senha", config.getSenha());
        }catch(JSONException e){
            e.printStackTrace();
        }
        return json;
    }

    public boolean validaConfig() {
        Config config = buscarConfig();
        if (config.getId() > 0){
            return true;
        }else{
            return false;
        }
    }

    private Config buscarConfig(){
        dataBase = new DataBase(contexto);
        conn = dataBase.getWritableDatabase();
        repositorioConfig = new RepositorioConfig(conn);
        return repositorioConfig.buscaConfiguracoes();
    }

    public String retornaUrl(){
        dataBase = new DataBase(contexto);
        conn = dataBase.getWritableDatabase();
        repositorioConfig = new RepositorioConfig(conn);
        Config config = repositorioConfig.buscaConfiguracoes();
        return  config.getUrl();
    }
}
