package com.example.nr12.sync;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.nr12.services.ServiceConfig;
import com.example.nr12.services.ServiceSistemaSeguranca;
import com.example.nr12.util.Requisicao;

import org.json.JSONObject;

/**
 * Created by Gui on 04/10/2017.
 */

public class GetJsonSistemaSeguranca extends AsyncTask<Void, Void, String> {
    private ProgressDialog load;
    private Context contexto;

    public GetJsonSistemaSeguranca(Context contexto) {
        this.contexto = contexto;
    }


    @Override
    protected void onPreExecute(){
        load = new ProgressDialog(contexto);
        load.setMessage("Sincronizando Sistemas de Segurança");
        load.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        ServiceSistemaSeguranca serviceSistemaSeguranca = new ServiceSistemaSeguranca(contexto);
        ServiceConfig serviceConfig = new ServiceConfig(contexto);
        return serviceSistemaSeguranca.getInformacao("http://"+serviceConfig.retornaUrl()+"/sistemaseguranca", new JSONObject(), "GET", Requisicao.getToken());
    }

    @Override
    protected void onPostExecute(String result) {
        ServiceSistemaSeguranca serviceSistemaSeguranca = new ServiceSistemaSeguranca(contexto);
        serviceSistemaSeguranca.gravaSistemaSeguranca(result);
        load.dismiss();
    }
}
