package com.example.nr12.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.nr12.database.DataBase;
import com.example.nr12.dominio.entidades.Perigo;
import com.example.nr12.dominio.entidades.Perigos;
import com.example.nr12.dominio.entidades.Risco;
import com.example.nr12.dominio.repositorio.RepositorioPerigo;
import com.example.nr12.dominio.repositorio.RepositorioPerigoRisco;
import com.example.nr12.util.NetWorkUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gui on 04/10/2017.
 */

public class ServicePerigo {

    private Context contexto;
    private DataBase database;
    private SQLiteDatabase conn;
    private RepositorioPerigo repositorioPerigo;
    private RepositorioPerigoRisco repositorioPerigoRisco;

    public ServicePerigo(Context contexto){
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

    public void gravaPerigo (String strPerigo){
        Gson gson = new Gson();
        Perigos perigos = gson.fromJson(strPerigo, Perigos.class);
        database = new DataBase(contexto);
        conn = database.getWritableDatabase();
        repositorioPerigo = new RepositorioPerigo(conn);
        repositorioPerigoRisco = new RepositorioPerigoRisco(conn);
        for (Perigo perigo : perigos.getPerigosList()){
            repositorioPerigo.inserirOuAlterar(perigo);
            repositorioPerigoRisco.excluir(perigo.getId());
            for (Risco risco : perigo.getRiscos()){
                repositorioPerigoRisco.inserir(perigo.getId(), risco.getId());
            }
        }
    }
}


