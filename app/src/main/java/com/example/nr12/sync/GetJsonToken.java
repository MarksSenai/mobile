package com.example.nr12.sync;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.nr12.services.ServiceToken;
import com.example.nr12.services.ServiceConfig;
import com.example.nr12.util.Requisicao;

import org.json.JSONObject;

import java.net.ConnectException;

/**
 * Created by Gui on 04/10/2017.
 */

public class GetJsonToken extends AsyncTask<Void, Void, String> {
    private ProgressDialog load;
    private Context contexto;
    //atributo usado pra saber se e pra seguir fazendo os GET ou não.
    // Necessário pq o método de login é usado no POST tambem
    private boolean GET;

    public GetJsonToken(Context contexto, boolean GET) {
        this.contexto = contexto;
        this.GET = GET;
    }


    @Override
    protected void onPreExecute(){
        load = new ProgressDialog(contexto);
        load.setMessage("Efetuando Login");
        load.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        ServiceToken serviceToken = new ServiceToken();
        ServiceConfig serviceConfig = new ServiceConfig(contexto);
        JSONObject jsonUser = null;
        jsonUser = serviceConfig.retornaJsonConfig();
        try {
            return serviceToken.getInformacao("http://"+serviceConfig.retornaUrl()+"/authenticate", jsonUser, "POST");
        }catch (ConnectException e) {
            return "Conexão Recusada";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        load.dismiss();
        if (result.equals("Conexão Recusada")){
            Toast.makeText(contexto, "Não foi possível conectar ao servidor. Verifique!",Toast.LENGTH_LONG).show();
        }else {
            if (Requisicao.getStatus().equals("error")) {
                Toast.makeText(contexto, Requisicao.getMessage(), Toast.LENGTH_LONG).show();
            } else if (GET) {
                GetJsonClientes getJsonClientes = new GetJsonClientes(contexto);
                getJsonClientes.execute();
            }
        }
    }
}
