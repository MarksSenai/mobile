package com.example.nr12.sync;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.nr12.services.ServiceConfig;
import com.example.nr12.services.ServiceTipoMaquina;
import com.example.nr12.util.Requisicao;

import org.json.JSONObject;

/**
 * Created by Gui on 04/10/2017.
 */

public class GetJsonTipoMaquina extends AsyncTask<Void, Void, String> {
    private ProgressDialog load;
    private Context contexto;

    public GetJsonTipoMaquina(Context contexto) {
        this.contexto = contexto;
    }


    @Override
    protected void onPreExecute(){
        load = new ProgressDialog(contexto);
        load.setMessage("Sincronizando Tipos de Máquina");
        load.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        ServiceTipoMaquina serviceTipoMaquina = new ServiceTipoMaquina(contexto);
        ServiceConfig serviceConfig = new ServiceConfig(contexto);
        return serviceTipoMaquina.getInformacao("http://"+serviceConfig.retornaUrl()+"/tipomaquina", new JSONObject(), "GET", Requisicao.getToken());
    }

    @Override
    protected void onPostExecute(String result) {
        ServiceTipoMaquina serviceTipoMaquina = new ServiceTipoMaquina(contexto);
        serviceTipoMaquina.gravaTipoMaquina(result);
        load.dismiss();
        GetJsonMaquina getJsonMaquina = new GetJsonMaquina(contexto);
        getJsonMaquina.execute();

    }
}
