package com.example.nr12.sync;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.nr12.services.ServiceConfig;
import com.example.nr12.services.ServiceMaquina;
import com.example.nr12.util.Requisicao;

import org.json.JSONObject;

/**
 * Created by Gui on 04/10/2017.
 */

public class GetJsonMaquina extends AsyncTask<Void, Void, String> {
    private ProgressDialog load;
    private Context contexto;

    public GetJsonMaquina(Context contexto) {
        this.contexto = contexto;
    }


    @Override
    protected void onPreExecute(){
        load = new ProgressDialog(contexto);
        load.setMessage("Sincronizando Máquinas");
        load.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        ServiceMaquina serviceMaquina = new ServiceMaquina(contexto);
        ServiceConfig serviceConfig = new ServiceConfig(contexto);
        return serviceMaquina.getInformacao("http://"+serviceConfig.retornaUrl()+"/maquina", new JSONObject(), "GET", Requisicao.getToken());
    }

    @Override
    protected void onPostExecute(String result) {
        ServiceMaquina serviceMaquina = new ServiceMaquina(contexto);
        serviceMaquina.gravaMaquina(result);
        load.dismiss();
        GetJsonRisco getJsonRisco = new GetJsonRisco(contexto);
        getJsonRisco.execute();
    }
}
