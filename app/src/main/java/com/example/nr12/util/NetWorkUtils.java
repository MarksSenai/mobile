package com.example.nr12.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.nr12.services.ServiceConfig;
import com.example.nr12.sync.GetJsonToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Gui on 04/10/2017.
 */

public class NetWorkUtils {

    private static HttpURLConnection conexao;
    private static NetworkInfo info;

    //Responsavel por carregar o objeto JSON
    public static String getJSONFromAPI(String url, JSONObject jsonData, String method, String token) throws IOException {
        String  retorno = "";
        try{
            URL apiEnd = new URL(url);
            int codigoResposta;
            InputStream is;
            conexao = (HttpURLConnection) apiEnd.openConnection();
            conexao.setRequestMethod(method);
            conexao.setRequestProperty("Content-Type","application/json");
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(15000);

            if ( ! token.equals("")){
                conexao.setRequestProperty("Authorization", "Bearer "+ token);
            }

            if (jsonData.length() > 0){
                OutputStreamWriter wr = new OutputStreamWriter(conexao.getOutputStream());
                wr.write(jsonData.toString());
                wr.flush();
            }else{
                conexao.connect();
            }
            codigoResposta = conexao.getResponseCode();
            if (codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST){
                is = conexao.getInputStream();
            }else{
                is = conexao.getErrorStream();
            }
            retorno = converterInputStreamToString(is);
            is.close();
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(ConnectException e){
            e.printStackTrace();
            throw e;
        }catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            conexao.disconnect();
        }
        return retorno;
    }



    @NonNull
    private static String converterInputStreamToString(InputStream is){
        StringBuffer buffer = new StringBuffer();
        try{
            BufferedReader br;
            String linha;
            br = new BufferedReader(new InputStreamReader(is));
            while ((linha = br.readLine())!=null){
                buffer.append(linha);
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static boolean temInternet(Context contexto) {
        try{
            ConnectivityManager manager = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
            info = manager.getActiveNetworkInfo();
        }catch(Exception e){
            e.printStackTrace();
        }
        return info != null && info.isConnected();
    }
}
