package com.example.nr12.services;

import com.example.nr12.util.NetWorkUtils;
import com.example.nr12.util.Requisicao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;

/**
 * Created by Gui on 04/10/2017.
 */

public class ServiceToken {

    public String getInformacao(String end, JSONObject jsonUser, String method) throws ConnectException {
        String json = "";
        try {
            json = NetWorkUtils.getJSONFromAPI(end, jsonUser, method, "");
        }catch (ConnectException e){
            throw e;
        }catch (IOException e) {
            e.printStackTrace();
        }
         return parseJson(json);
    }

    private String parseJson(String json) {
        try {

            JSONObject jsonObj = new JSONObject(json);
            Requisicao.setStatus(jsonObj.getString("status"));
            Requisicao.setMessage(jsonObj.getString("message"));
            if (Requisicao.getStatus().equals("success")){
                Requisicao.setToken(jsonObj.getString("token"));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return Requisicao.getStatus();
    }
}
