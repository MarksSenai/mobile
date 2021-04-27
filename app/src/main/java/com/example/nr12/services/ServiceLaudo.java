package com.example.nr12.services;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.nr12.database.DataBase;
import com.example.nr12.dominio.entidades.Laudo;
import com.example.nr12.dominio.entidades.PontoPerigo;
import com.example.nr12.dominio.repositorio.RepositorioLaudo;
import com.example.nr12.dominio.repositorio.RepositorioPontoPerigo;
import com.example.nr12.sync.GetJsonToken;
import com.example.nr12.sync.SetJsonLaudo;
import com.example.nr12.util.NetWorkUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gui on 11/12/2017.
 */

public class ServiceLaudo {

    private Context contexto;
    private DataBase database;
    private SQLiteDatabase conn;
    private RepositorioLaudo repositorioLaudo;
    private RepositorioPontoPerigo repositorioPontoPerigo;

    public ServiceLaudo(Context contexto){
        this.contexto = contexto;
    }

    public String setInformacao(String end, JSONObject jsonUser, String method, String token){
        String json = "";
        try {
            json = NetWorkUtils.getJSONFromAPI(end, jsonUser, method, token);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public void enviarLaudo (Laudo laudo){
        GetJsonToken getJsonToken = new GetJsonToken(contexto, false);
        getJsonToken.execute();
        laudo.setId(null);
        SetJsonLaudo setJsonLaudo = new SetJsonLaudo(contexto);
        try {
            setJsonLaudo.execute(montaJsonLaudo(laudo));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject montaJsonLaudo(Laudo laudo) throws JSONException {
        Gson gson = new Gson();
        return new JSONObject(gson.toJson(laudo));
    }
}


