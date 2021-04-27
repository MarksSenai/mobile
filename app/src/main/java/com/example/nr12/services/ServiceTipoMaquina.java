package com.example.nr12.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.nr12.database.DataBase;
import com.example.nr12.dominio.entidades.TipoMaquina;
import com.example.nr12.dominio.entidades.TiposMaquinas;
import com.example.nr12.dominio.repositorio.RepositorioTipoMaquina;
import com.example.nr12.util.NetWorkUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Gui on 04/10/2017.
 */

public class ServiceTipoMaquina {

    private Context contexto;
    private DataBase database;
    private SQLiteDatabase conn;
    private RepositorioTipoMaquina repositorioTipoMaquina;

    public ServiceTipoMaquina(Context contexto){
        this.contexto = contexto;
    }

    public String getInformacao(String end, JSONObject jsonUser, String method, String token){
        String json = "";
        try {
            json = NetWorkUtils.getJSONFromAPI(end, jsonUser, method, token);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void gravaTipoMaquina (String strTipoMaquina){
        Gson gson = new Gson();
        TiposMaquinas tipos = gson.fromJson(strTipoMaquina, TiposMaquinas.class);
        database = new DataBase(contexto);
        conn = database.getWritableDatabase();
        repositorioTipoMaquina = new RepositorioTipoMaquina(conn);
        for (TipoMaquina tipoMaquina : tipos.getListTipoMaquina()){
            repositorioTipoMaquina.inserirOuAlterar(tipoMaquina);
        }
    }
}


