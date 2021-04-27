package com.example.nr12.sync;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.nr12.services.ServiceConfig;
import com.example.nr12.services.ServiceRisco;
import com.example.nr12.util.Requisicao;

import org.json.JSONObject;

/**
 * Created by Gui on 04/10/2017.
 */

public class GetJsonRisco extends AsyncTask<Void, Void, String> {
    private ProgressDialog load;
    private Context contexto;

    public GetJsonRisco(Context contexto) {
        this.contexto = contexto;
    }


    @Override
    protected void onPreExecute(){
        load = new ProgressDialog(contexto);
        load.setMessage("Sincronizando Riscos");
        load.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        ServiceRisco serviceRisco = new ServiceRisco(contexto);
        ServiceConfig serviceConfig = new ServiceConfig(contexto);
        return serviceRisco.getInformacao("http://"+serviceConfig.retornaUrl()+"/risco", new JSONObject(), "GET", Requisicao.getToken());
    }

    @Override
    protected void onPostExecute(String result) {
        ServiceRisco serviceRisco = new ServiceRisco(contexto);
        serviceRisco.gravaRisco(result);
        load.dismiss();
        GetJsonPerigo getJsonPerigo = new GetJsonPerigo(contexto);
        getJsonPerigo.execute();
    }
}
