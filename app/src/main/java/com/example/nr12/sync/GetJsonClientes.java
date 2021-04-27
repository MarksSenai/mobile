package com.example.nr12.sync;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.nr12.services.ServiceCliente;
import com.example.nr12.services.ServiceConfig;
import com.example.nr12.util.Requisicao;

import org.json.JSONObject;

/**
 * Created by Gui on 04/10/2017.
 */

public class GetJsonClientes extends AsyncTask<Void, Void, String> {
    private ProgressDialog load;
    private Context contexto;

    public GetJsonClientes(Context contexto) {
        this.contexto = contexto;
    }


    @Override
    protected void onPreExecute(){
        load = new ProgressDialog(contexto);
        load.setMessage("Sincronizando Clientes");
        load.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        ServiceCliente serviceCliente = new ServiceCliente(contexto);
        ServiceConfig serviceConfig = new ServiceConfig(contexto);
        return serviceCliente.getInformacao("http://"+serviceConfig.retornaUrl()+"/cliente", new JSONObject(), "GET", Requisicao.getToken());
    }

    @Override
    protected void onPostExecute(String result) {
        ServiceCliente service = new ServiceCliente(contexto);
        service.gravaClientes(result);
        load.dismiss();
        GetJsonTipoMaquina getJsonTipoMaquina = new GetJsonTipoMaquina(contexto);
        getJsonTipoMaquina.execute();

    }
}
