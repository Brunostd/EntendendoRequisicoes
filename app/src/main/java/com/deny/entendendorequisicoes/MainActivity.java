package com.deny.entendendorequisicoes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button buttonRecuperar;
    private TextView textViewRecuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRecuperar     = findViewById(R.id.buttonRecuperar);
        textViewRecuperar   = findViewById(R.id.textViewRecuperar);

        buttonRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAsyncTask task = new MyAsyncTask();
                String urlApi = "https://blockchain.info/ticker";
                String cep = "62370000";
                String urlCep = "https://viacep.com.br/ws/"+cep+"/json/";
                task.execute(urlCep);
            }
        });

    }

    class MyAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String stringUrl                    = strings[0];
            InputStream inputStream             = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;

            try {
                URL url = new URL(stringUrl);
                HttpURLConnection conexao =(HttpURLConnection) url.openConnection();

                //Recupera os dados eviados em Bytes
                inputStream = conexao.getInputStream();

                //Lê os dados em Bytes e decodifica para caracteres
                inputStreamReader = new InputStreamReader(inputStream);

                //Objeto utilizado para fazer a leitura dos caracteres inputStreamReader
                BufferedReader reader = new BufferedReader(inputStreamReader);
                buffer = new StringBuffer();
                String linha="";

                while ( (linha = reader.readLine()) != null ){
                    buffer.append(linha);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textViewRecuperar.setText( s );
        }
    }
}