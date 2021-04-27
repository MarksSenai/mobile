package com.example.nr12.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.nr12.database.DataBase;
import com.example.nr12.dominio.entidades.Maquina;
import com.example.nr12.dominio.entidades.Maquinas;
import com.example.nr12.dominio.repositorio.RepositorioMaquina;
import com.example.nr12.util.NetWorkUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Gui on 04/10/2017.
 */

public class ServiceMaquina {

    private Context contexto;
    private DataBase database;
    private SQLiteDatabase conn;
    private RepositorioMaquina repositorioMaquina;

    public ServiceMaquina(Context contexto){
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

    public void gravaMaquina (String strMaquina){
        Gson gson = new Gson();
        Maquinas tipos = gson.fromJson(strMaquina, Maquinas.class);
        database = new DataBase(contexto);
        conn = database.getWritableDatabase();
        repositorioMaquina = new RepositorioMaquina(conn);
        for (Maquina maquina : tipos.getListMaquina()){
            repositorioMaquina.inserirOuAlterar(maquina);
        }
    }
}


