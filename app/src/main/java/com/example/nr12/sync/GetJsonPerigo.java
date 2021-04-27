package com.example.nr12.sync;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.nr12.services.ServiceConfig;
import com.example.nr12.services.ServicePerigo;
import com.example.nr12.util.Requisicao;

import org.json.JSONObject;

/**
 * Created by Gui on 04/10/2017.
 */

public class GetJsonPerigo extends AsyncTask<Void, Void, String> {
    private ProgressDialog load;
    private Context contexto;

    public GetJsonPerigo(Context contexto) {
        this.contexto = contexto;
    }


    @Override
    protected void onPreExecute(){
        load = new ProgressDialog(contexto);
        load.setMessage("Sincronizando Perigos");
        load.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        ServicePerigo servicePerigo = new ServicePerigo(contexto);
        ServiceConfig serviceConfig = new ServiceConfig(contexto);
        return servicePerigo.getInformacao("http://"+serviceConfig.retornaUrl()+"/perigo", new JSONObject(), "GET", Requisicao.getToken());
    }

    @Override
    protected void onPostExecute(String result) {
        ServicePerigo servicePerigo = new ServicePerigo(contexto);
        servicePerigo.gravaPerigo(result);
        load.dismiss();
        GetJsonSistemaSeguranca getJsonSistemaSeguranca = new GetJsonSistemaSeguranca(contexto);
        getJsonSistemaSeguranca.execute();
    }
}
