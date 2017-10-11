package com.example.levimmartins.heartratemonitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by levimmartins on 29/07/17.
 */

public class Conquistas extends AppCompatActivity {

    private String pontos = new String();
    private String id;
    TextView tvPontos = null;
    TextView tvLevel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conquistas);

        this.id =  getIntent().getStringExtra("EXTRA_SESSION_ID");

        if(! getIntent().getStringExtra("EXTRA_SESSION_ID").isEmpty()){
            this.pontos = getIntent().getStringExtra("EXTRA_SESSION_PONTOS");
            System.out.println(this.pontos);
        }else{

            String type = "pontos";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type,this.id);
        }
       // this.pontos = getIntent().getStringExtra("EXTRA_SESSION_PONTOS");
       // String[] pontoRetornado = pontos.split(":");




        tvPontos = (TextView) findViewById(R.id.tvPontos);
        tvLevel = (TextView) findViewById(R.id.tvLevel);



    }

}
