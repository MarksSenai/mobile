package com.example.nr12.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.nr12.database.DataBase;
import com.example.nr12.dominio.entidades.Cliente;
import com.example.nr12.dominio.entidades.Clientes;
import com.example.nr12.dominio.repositorio.RepositorioCliente;
import com.example.nr12.util.NetWorkUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Gui on 04/10/2017.
 */

public class ServiceCliente {

    private Context contexto;
    private DataBase database;
    private SQLiteDatabase conn;
    private RepositorioCliente repositorioCliente;

    public ServiceCliente (Context contexto){
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

    public void gravaClientes (String strClientes){
        Gson gson = new Gson();
        Clientes clientes = gson.fromJson(strClientes, Clientes.class);
        database = new DataBase(contexto);
        conn = database.getWritableDatabase();
        repositorioCliente = new RepositorioCliente(conn);
        for (Cliente cliente : clientes.getClientesList()){
            repositorioCliente.inserirOuAlterar(cliente);
        }
    }
}


