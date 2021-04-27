package com.example.nr12.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.nr12.database.DataBase;
import com.example.nr12.dominio.entidades.Risco;
import com.example.nr12.dominio.entidades.Riscos;
import com.example.nr12.dominio.entidades.SistemaSeguranca;
import com.example.nr12.dominio.entidades.SistemasSeguranca;
import com.example.nr12.dominio.repositorio.RepositorioRisco;
import com.example.nr12.dominio.repositorio.RepositorioSistemaSeguranca;
import com.example.nr12.util.NetWorkUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gui on 04/10/2017.
 */

public class ServiceSistemaSeguranca {

    private Context contexto;
    private DataBase database;
    private SQLiteDatabase conn;
    private RepositorioSistemaSeguranca repositorioSistemaSeguranca;

    public ServiceSistemaSeguranca(Context contexto){
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

    public void gravaSistemaSeguranca (String strSistemaSeguranca){
        Gson gson = new Gson();
        SistemasSeguranca sistemas = gson.fromJson(strSistemaSeguranca, SistemasSeguranca.class);
        database = new DataBase(contexto);
        conn = database.getWritableDatabase();
        repositorioSistemaSeguranca = new RepositorioSistemaSeguranca(conn);
        for (SistemaSeguranca sistema : sistemas.getSistemasList()){
            repositorioSistemaSeguranca.inserirOuAlterar(sistema);
        }
    }
}


