package com.example.levimmartins.heartratemonitor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by levimmartins on 29/07/17.
 */
//AppCompatActivity
public class Dicas extends Activity implements BackgroundWorker.AsyncResponse {

    private String id;
    private static Integer idDica;
    private TextView tvDica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicas);
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        tvDica = (TextView) findViewById(R.id.tvDica);

        this.id =  getIntent().getStringExtra("EXTRA_SESSION_ID");

        idDica =1;

        String type = "lerDica";

      /*    backgroundWorker = new BackgroundWorker(this);
          backgroundWorker.context = this;
          backgroundWorker.execute(type, id, String.valueOf(idDica));
          System.out.println(backgroundWorker.dica);

         tvDica.setText("Dica:"+backgroundWorker.dica);  */
        backgroundWorker.delegate = this;
        backgroundWorker.execute(type, id, String.valueOf(idDica));


    }


    @Override
    public void processFinish(String output) {
        tvDica.setText("Dica:"+output);
    }
}
