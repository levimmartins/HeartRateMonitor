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

        tvPontos = (TextView) findViewById(R.id.tvPontos);
        tvLevel = (TextView) findViewById(R.id.tvLevel);
        tvLevel.setText("Level: 1");

       // this.id =  getIntent().getStringExtra("EXTRA_SESSION_ID");
        this.pontos = getIntent().getStringExtra("EXTRA_SESSION_PONTOS");



            //pontos = backgroundWorker.getGlobalPontos().toString();
           //
        //
             tvPontos.setText("Points: "+pontos);
             if(Integer.parseInt(pontos) >= 100){
                 tvLevel.setText("Level: 2");
             }
             if(Integer.parseInt(pontos) >= 200){
                 tvLevel.setText("Level: 3");
             }
             if(Integer.parseInt(pontos) >=300){
                   tvLevel.setText("Level: 4");
            }
            if(Integer.parseInt(pontos) >= 400){
                   tvLevel.setText("Level: 5");
            }
            if(Integer.parseInt(pontos) >= 500){
                   tvLevel.setText("Level: 6");
            }
            if(Integer.parseInt(pontos) >= 600){
                 tvLevel.setText("Level: 7");
             }
             if(Integer.parseInt(pontos) >= 700){
                 tvLevel.setText("Level: 8");
             }
             if(Integer.parseInt(pontos) >= 800){
                 tvLevel.setText("Level: 9");
             }
             if(Integer.parseInt(pontos) >= 900){
                 tvLevel.setText("Level: 10");
             }
             if(Integer.parseInt(pontos) >= 1000){
                 tvLevel.setText("Level: 11");
             }
             if(Integer.parseInt(pontos) >= 500){
                 tvLevel.setText("Level: 12");
             }
             if(Integer.parseInt(pontos) >= 500){
                 tvLevel.setText("Level: 13");
             }
           // this.pontos = getIntent().getStringExtra("EXTRA_SESSION_PONTOS");

       // this.pontos = getIntent().getStringExtra("EXTRA_SESSION_PONTOS");
       // String[] pontoRetornado = pontos.split(":");







    }

}
