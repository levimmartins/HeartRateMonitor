package com.example.levimmartins.heartratemonitor;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by levimmartins on 11/04/17.
 */

public class login extends AppCompatActivity {

    EditText EdUsuario, EdSenha;
    TextView TvNaoPossuiCd;
    protected  void onCreate(Bundle savedInsanceState){
        super.onCreate(savedInsanceState);
        setContentView(R.layout.activity_login);

        EdUsuario = (EditText)findViewById(R.id.edUsuario);
        EdSenha = (EditText)findViewById(R.id.edSenha);
        TvNaoPossuiCd = (TextView)findViewById(R.id.tvNaoPossuiCd);


        TvNaoPossuiCd.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent abreCadastro = new Intent(login.this, TelaCadastro.class);
                startActivity(abreCadastro);
            }
        });

    }

    public void OnLogin(View view){
        String username = EdUsuario.getText().toString().trim();
        String password = EdSenha.getText().toString().trim();

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            String type = "login";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);


            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(getApplicationContext(), "Nenhum campo pode estar vazio", Toast.LENGTH_LONG).show();
            }else{
                backgroundWorker.execute(type, username, password);
            }



        }else{
            Toast.makeText(getApplicationContext(), "Nenhuma conexão foi detectada", Toast.LENGTH_LONG).show();
        }


    }


}