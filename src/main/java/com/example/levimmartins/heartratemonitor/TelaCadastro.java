package com.example.levimmartins.heartratemonitor;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;


public class TelaCadastro extends AppCompatActivity  implements  SensorEventListener{


    //private static final String TAG = MainActivity.class.getName();
    private SensorManager mSensorManager;


    EditText editNome, editIdade, editEmail, editSenha, editEmailCuidador;
    TextView tvNome, tvIdade, tvEmail, tvSenha, tvMensagem1, tvEmailCuidador;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNome = (EditText) findViewById(R.id.editNome);
        editIdade = (EditText) findViewById(R.id.editIdade);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editSenha = (EditText) findViewById(R.id.editSenha);
        editEmailCuidador = (EditText) findViewById(R.id.editEmailCuidador) ;

        tvNome = (TextView) findViewById(R.id.tvNome);
        tvIdade = (TextView) findViewById(R.id.tvIdade);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvSenha = (TextView) findViewById(R.id.tvSenha);
        tvEmailCuidador = (TextView) findViewById(R.id.tvEmailCuidador);
        tvMensagem1 = (TextView) findViewById(R.id.tvMensagem1);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);


        for (int i = 0; i < deviceSensors.size(); i++) {
            System.out.println(deviceSensors.get(i));

        }
        System.out.println("Quantidade de Sensores Identificados: " + deviceSensors.size());


        Sensor mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }


/*
    public void SaveData(View view){
        String nome = editNome.getText().toString();
        int idade = Integer.parseInt(editIdade.getText().toString());
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String emailCuidador = editEmailCuidador.getText().toString();

        BaseHelper baseHelper = new BaseHelper(this, "HRMDB", null,1);
        SQLiteDatabase db = baseHelper.getWritableDatabase();

        if(db!=null){
            ContentValues registroNovo = new ContentValues();
            registroNovo.put("nome", nome);
            registroNovo.put("idade", idade);
            registroNovo.put("email", email);
            registroNovo.put("senha", senha);
            registroNovo.put("cuidadorEmail", emailCuidador);

            long i = db.insert("user", null, registroNovo);

            if(i>0){
                Toast.makeText(this, "Registro Inserido", Toast.LENGTH_SHORT).show();
            }

        }

    }

*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.action_settings){
            return true;
        }


        if(id == R.id.action_list){
            startActivity(new Intent(this, Segunda.class));
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {


        String TAG = "tag";
        Log.i(TAG, "--------------------------");
       // Log.i(TAG, msg);
        Log.i(TAG, ""+ event.sensor.getType());

        Log.i("live","--------------");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

