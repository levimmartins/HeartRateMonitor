package com.example.levimmartins.heartratemonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by levimmartins on 29/07/17.
 */
//AppCompatActivity
public class Dicas extends Activity implements BackgroundWorker.AsyncResponse {

    private String id;
    private  Integer idDica;
    private TextView tvDica;
    String resultado;
    private  boolean setOnOff;
    private Button btnLer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicas);
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);

        tvDica = (TextView) findViewById(R.id.tvDica);
        btnLer = (Button) findViewById(R.id.btnRead);

        this.id =  getIntent().getStringExtra("EXTRA_SESSION_ID");
        setOnOff = false;
        if(setOnOff == false) {
            String type1 = "SetIdDica";
            // BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.delegate = this;
            backgroundWorker.execute(type1, this.id);

        }




    }


    public void OnMarcarLido(View view){
        Intent openCongrats = new Intent(this,  CongratulationTips.class);
        openCongrats.putExtra("EXTRA_SESSION_ID", id);
        startActivity(openCongrats);
    }

    @Override
    public void processFinish(String output) {

        if(setOnOff == false) {
            this.resultado = output;
            idDica = Integer.parseInt(resultado);
            setOnOff = true;

        }

            String type2 = "lerDica";

            BackgroundWorker backgroundWorker2 = new BackgroundWorker(this);

            backgroundWorker2.delegate = this;
            backgroundWorker2.execute(type2, id, String.valueOf(idDica+1));

            if(setOnOff == true) {
                tvDica.setText(output);

            }

            //setOnOff = false;

        //if(output.contains("Dica:")){
       //     tvDica.setText("Dica:"+output);
       /// }else {
        //    System.out.println("Saida: "+output);
          //  this.idDica = Integer.parseInt(output)+1;
      //  }//
        //System.out.println("ID DICA resultado :"+resultado);




    }
}
