package com.example.nr12.sync;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.nr12.dominio.entidades.Laudo;
import com.example.nr12.services.ServiceConfig;
import com.example.nr12.services.ServiceLaudo;
import com.example.nr12.services.ServiceToken;
import com.example.nr12.util.Requisicao;

import org.json.JSONObject;

import java.net.ConnectException;

/**
 * Created by Gui on 04/10/2017.
 */

public class SetJsonLaudo extends AsyncTask<JSONObject, Void, String> {
    private ProgressDialog load;
    private Context contexto;

    public SetJsonLaudo(Context contexto) {
        this.contexto = contexto;
    }




    @Override
    protected void onPreExecute(){
        load = new ProgressDialog(contexto);
        load.setMessage("Enviando Laudo");
        load.show();
    }

    @Override
    protected String doInBackground(JSONObject... params) {
        ServiceLaudo serviceLaudo = new ServiceLaudo(contexto);
        ServiceConfig serviceConfig = new ServiceConfig(contexto);
        return serviceLaudo.setInformacao("http://"+serviceConfig.retornaUrl()+"/laudoMobile", params[0], "POST", Requisicao.getToken());
    }

    @Override
    protected void onPostExecute(String result) {
        load.dismiss();

    }
}
