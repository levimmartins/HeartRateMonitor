package com.example.levimmartins.heartratemonitor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.levimmartins.heartratemonitor.R.id.editNascim;


public class TelaCadastro extends AppCompatActivity  implements  SensorEventListener{


    //private static final String TAG = MainActivity.class.getName();
    // private SensorManager mSensorManager;


    EditText editNome, editEmail, editSenha, editEmailCuidador;
    int day, month, year;

    TextView tvNome, tvIdade, tvEmail, tvSenha, tvMensagem1, tvEmailCuidador , tvEditNascim;
    String dataNascimento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        editNome = (EditText) findViewById(R.id.editNome);
        // editNascim = (EditText) findViewById(R.id.editNascim);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editSenha = (EditText) findViewById(R.id.editSenha);
        editEmailCuidador = (EditText) findViewById(R.id.editEmailCuidador) ;

        tvNome = (TextView) findViewById(R.id.tvNome);
        tvIdade = (TextView) findViewById(R.id.tvIdade);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvSenha = (TextView) findViewById(R.id.tvSenha);
        tvEmailCuidador = (TextView) findViewById(R.id.tvEmailCuidador);
        tvMensagem1 = (TextView) findViewById(R.id.tvMensagem1);
        tvEditNascim = (TextView) findViewById(editNascim);


        tvEditNascim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(TelaCadastro.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        monthOfYear = monthOfYear+1;
                        tvEditNascim.setText(dayOfMonth+"/"+monthOfYear+"/"+year);

                        dataNascimento = year+"-"+monthOfYear+"-"+dayOfMonth;
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //  mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //  List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);


        //  for (int i = 0; i < deviceSensors.size(); i++) {
        //     System.out.println(deviceSensors.get(i));

        // }
        // System.out.println("Quantidade de Sensores Identificados: " + deviceSensors.size());


        //    Sensor mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        //   mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        //must be alpha numeric,must contain at least one symbol, and one capital letter.
        //final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z]).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public void OnReg (View view){
        String  nome = editNome.getText().toString();
        //String  dataNascimento = editNascim.getText().toString();
        String  email = editEmail.getText().toString();
        String  senha = editSenha.getText().toString();
        String emailCuidador = editEmailCuidador.getText().toString();


        if(nome.isEmpty() || dataNascimento.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha os campos obrigatórios (*) ", Toast.LENGTH_LONG).show();
        }else  if(!isValidEmail(email)) {
            Toast.makeText(getApplicationContext(), "Email inválido! ", Toast.LENGTH_LONG).show();
        }else if(isValidPassword(senha) && senha.length()>4){
            String type = "register";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, nome, dataNascimento, email, emailCuidador, senha);
        }else{
            Toast.makeText(getApplicationContext(), "Senha inválida! A senha deve ter no mínimo 5 letras, uma letra maiuscula, e um numero.", Toast.LENGTH_LONG).show();
        }

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
