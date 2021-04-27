package com.example.nr12.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.nr12.database.DataBase;
import com.example.nr12.dominio.entidades.Risco;
import com.example.nr12.dominio.entidades.Riscos;
import com.example.nr12.dominio.repositorio.RepositorioRisco;
import com.example.nr12.util.NetWorkUtils;
import com.google.gson.Gson;

import org.json.JSONObject;
import java.io.IOException;

/**
 * Created by Gui on 04/10/2017.
 */

public class ServiceRisco {

    private Context contexto;
    private DataBase database;
    private SQLiteDatabase conn;
    private RepositorioRisco repositorioRisco;

    public ServiceRisco(Context contexto){
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

    public void gravaRisco (String strRisco){
        Gson gson = new Gson();
        Riscos riscos = gson.fromJson(strRisco, Riscos.class);
        database = new DataBase(contexto);
        conn = database.getWritableDatabase();
        repositorioRisco = new RepositorioRisco(conn);
        for (Risco risco : riscos.getRiscosList()){
            repositorioRisco.inserirOuAlterar(risco);
        }
    }
}


