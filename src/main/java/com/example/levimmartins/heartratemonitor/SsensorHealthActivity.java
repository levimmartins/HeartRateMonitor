package com.example.levimmartins.heartratemonitor;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.sensorextension.Ssensor;
import com.samsung.android.sdk.sensorextension.SsensorEvent;
import com.samsung.android.sdk.sensorextension.SsensorEventListener;
import com.samsung.android.sdk.sensorextension.SsensorExtension;
import com.samsung.android.sdk.sensorextension.SsensorManager;

import java.io.BufferedWriter;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SsensorHealthActivity extends Activity {

    Ssensor ir=null;
    Ssensor red=null;
    private String id;
    //ToggleButton btn_start = null;
    ToggleButton btn_start = null;

    TextView tIR = null;
    TextView tRED = null;

    SSListenerIR mSSListenerIR = null;
    SSListenerRED mSSListenerRED = null;

    SsensorManager mSSensorManager = null;
    SsensorExtension mSsensorExtension = null;

    Activity mContext;

    //get path from sdcard
    String sdcard;
    File namefile;
    BufferedWriter out;
    StringBuffer sbDadosSensor;


    WriteFile Arquivo;

    @TargetApi(23) @Override
    protected void onCreate(Bundle savedInstanceState){
         mContext = this;
        verifyStoragePermissions(mContext);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        sdcard = Environment.getExternalStorageDirectory().getPath();

        //btn_start.setText("Iniciar");
        this.id =  getIntent().getStringExtra("EXTRA_SESSION_ID");
        System.out.println("=======USER EMAIL SESSION ID ========"+this.id);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        sbDadosSensor = new StringBuffer();
        btn_start = (ToggleButton)findViewById(R.id.btn_start);
        tIR = (TextView) findViewById(R.id.tIR);
        tRED = (TextView) findViewById(R.id.tRED);

        mSSListenerIR = new SSListenerIR();
        mSSListenerRED = new SSListenerRED();


        String name = sdcard+"/dadosSENSOR.txt";

        Arquivo = new WriteFile(name);

       /* if(btn_start !=null){




            btn_start.setOnClickListener(new View.OnClickListener() {



                        @Override
                        public void onClick(View v) {
                            btn_start.setSelected(!btn_start.isSelected());


                           // String name = sdcard+"/dadosSENSOR.txt";
                            //namefile = new File(name);
                          //  try {
                          //      out = new BufferedWriter(new FileWriter(namefile));
                       //     } catch (IOException e) {
                          //      e.printStackTrace();
                         //   }




                            try{
                                if(!btn_start.isSelected()){
                                    //HRM OFF
                                    //btn_start.setText(btn_start.getTextOff());
                                    btn_start.setText("Iniciar");
                                    mSSensorManager.unregisterListener(mSSListenerIR, ir);
                                    mSSensorManager.unregisterListener(mSSListenerRED, red);

                                   // Arquivo.WriteWord("Teste:"+sbDadosSensor);
                                   //Arquivo.CloseFile();


                                }else{



                                    mSsensorExtension = new SsensorExtension();

                                    try{
                                        mSsensorExtension.initialize(mContext);
                                        mSSensorManager = new SsensorManager(mContext, mSsensorExtension);

                                        ir = mSSensorManager.getDefaultSensor(Ssensor.TYPE_HRM_LED_IR);
                                        red = mSSensorManager.getDefaultSensor(Ssensor.TYPE_HRM_LED_RED);

                                    }catch(SsdkUnsupportedException e){
                                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                                        mContext.finish();

                                    }catch (IllegalArgumentException e){
                                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        mContext.finish();
                                    }catch (SecurityException e){
                                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        mContext.finish();
                                    }

                                    //HRM ON

                                    btn_start.setText(btn_start.getTextOn());
                                    if(mSSensorManager != null){

                                        mSSensorManager.registerListener(mSSListenerIR, ir, SensorManager.SENSOR_DELAY_NORMAL);
                                        mSSensorManager.registerListener(mSSListenerRED, red, SensorManager.SENSOR_DELAY_NORMAL);
                                    }
                                }
                            }catch (IllegalArgumentException e){
                                    ErrorToast(e);
                            }
                        }
                    });

                    if (android.os.Build.VERSION.SDK_INT >=23){
                            if(checkSelfPermission(Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED){
                                //SHOW EXPLANATION
                                if (shouldShowRequestPermissionRationale(Manifest.permission.BODY_SENSORS)){
                                    //Explai to the user why we need to read the contacts
                                }

                                requestPermissions(new String[] {Manifest.permission.BODY_SENSORS}, 101);

                                //MY_PERMISSIONS_REQUEST_READ_CONTACTS is an app-defined int constant

                                return;
                            }
                    }
        } */
    }

    public void OnIniciar(View view){
        //  if(btn_start !=null){

        // btn_start.setOnClickListener(new View.OnClickListener() {

        //   @Override
        //   public void onClick(View v) {

       btn_start.setSelected(!btn_start.isSelected());


        // String name = sdcard+"/dadosSENSOR.txt";
        //namefile = new File(name);
        //  try {
        //      out = new BufferedWriter(new FileWriter(namefile));
        //     } catch (IOException e) {
        //      e.printStackTrace();
        //   }




        try{
            if(!btn_start.isSelected()){
                //HRM OFF
                //btn_start.setText(btn_start.getTextOff());
                btn_start.setText("Iniciar");
                mSSensorManager.unregisterListener(mSSListenerIR, ir);
                mSSensorManager.unregisterListener(mSSListenerRED, red);

                // Arquivo.WriteWord("Teste:"+sbDadosSensor);
                //Arquivo.CloseFile();

                String type="pontuar";
                BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                backgroundWorker.execute(type, id);

                Intent openDicas = new Intent(this, Dicas.class);
                openDicas.putExtra("EXTRA_SESSION_ID", id);
                startActivity(openDicas);


            }else{



                mSsensorExtension = new SsensorExtension();

                try{
                    mSsensorExtension.initialize(mContext);
                    mSSensorManager = new SsensorManager(mContext, mSsensorExtension);

                    ir = mSSensorManager.getDefaultSensor(Ssensor.TYPE_HRM_LED_IR);
                    red = mSSensorManager.getDefaultSensor(Ssensor.TYPE_HRM_LED_RED);

                }catch(SsdkUnsupportedException e){
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                    mContext.finish();

                }catch (IllegalArgumentException e){
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    mContext.finish();
                }catch (SecurityException e){
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    mContext.finish();
                }

                //HRM ON

                //btn_start.setText(btn_start.getTextOn());
                btn_start.setText("Parar");

                //INSERIR PONTOS



                if(mSSensorManager != null){

                    mSSensorManager.registerListener(mSSListenerIR, ir, SensorManager.SENSOR_DELAY_NORMAL);
                    mSSensorManager.registerListener(mSSListenerRED, red, SensorManager.SENSOR_DELAY_NORMAL);
                }
            }
        }catch (IllegalArgumentException e){
            ErrorToast(e);
        }
        //   }//onClick
        // });  //setOnClick

        if (android.os.Build.VERSION.SDK_INT >=23){
            if(checkSelfPermission(Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED){
                //SHOW EXPLANATION
                if (shouldShowRequestPermissionRationale(Manifest.permission.BODY_SENSORS)){
                    //Explai to the user why we need to read the contacts
                }

                requestPermissions(new String[] {Manifest.permission.BODY_SENSORS}, 101);

                //MY_PERMISSIONS_REQUEST_READ_CONTACTS is an app-defined int constant

                return;
            }
        }
        //}
    }

    public void ErrorToast(IllegalArgumentException e){
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        btn_start.setSelected(!btn_start.isSelected());
        // btn_start.setText(btn_start.getTextOff());
        btn_start.setText("Iniciar");
    }

    @Override
    protected  void onPause(){
        super.onPause();

        Arquivo.WriteWord(""+sbDadosSensor);
        Arquivo.CloseFile();



        btn_start.setText("Iniciar");
/*
        String fileAddress = sdcard+"/dadosSENSOR.txt";
        Funcoes funcoes = new Funcoes();
        funcoes.getData(fileAddress);

            funcoes.setSensorValuesList(new ArrayList<String>( funcoes.getSensorValuesList().subList(900, 3500)));

            funcoes.rolling_mean(funcoes.getSensorValuesList(), 0.75, 99.00);
             funcoes.detect_peaks(funcoes.getSensorValuesList());
             funcoes.calc_rr(99.00);
             funcoes.calc_bpm();

*/


        try{

            if(ir != null){
                mSSensorManager.unregisterListener(mSSListenerIR, ir);
                tIR.setText("");

            }

            if(red !=null){
                mSSensorManager.unregisterListener(mSSListenerRED, red);
                tRED.setText("");

            }
        }catch (IllegalArgumentException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            this.finish();
        }catch (IllegalStateException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            this.finish();
        }
    }


    private  class SSListenerIR implements SsensorEventListener{

        @Override
        public void OnAccuracyChanged(Ssensor arg0, int arg1){
            //TODO
        }

        @Override
        public void OnSensorChanged(SsensorEvent event){

            //    Ssensor sIR = event.sensor;
            //   StringBuffer sb = new StringBuffer();
               /* sb.append("=== Sensor Information ===\n")
                        .append("Name: "+sIR.getName() + "\n")
                        .append("IR RAW DATA(HRM): "+event.values[0] + "\n");
*/
            // sb.append("IR RAW DATA(HRM): "+event.values[0] + "\n");

            // tIR.setText(sb.toString());

        }
    }


    private class SSListenerRED implements SsensorEventListener{



        @Override
        public void OnAccuracyChanged(Ssensor arg0, int arg1){

        }

        @Override
        public void OnSensorChanged(SsensorEvent event){


            //Handling SsensorEvent with SSensorEventListener.
            Ssensor sIr = event.sensor;
            StringBuffer sb = new StringBuffer();
            /*    sb.append("==== Sensor Information ====\n")
                        .append("Name:  "+sIr.getName()+ "\n")
                        .append("Vendor: "+sIr.getVendor()+ "\n")
                        .append("Type: "+sIr.getType()+ "\n")
                        .append("SDK Version: " + mSsensorExtension.getVersionName() + "\n")
                        .append("MaxRange: "+sIr.getMaxRange() + "\n")
                        .append("Resolution: "+sIr.getResolution() + "\n")
                        .append("FifoMaxEventCount: "+sIr.getFifoMaxEventCount() + "\n")
                        .append("Power: "+sIr.getPower()+ "\n")
                        .append("---------------------------------------\n")
                        .append("RED LED RAW DATA(HRM): "+event.values[0]+ "\n");
*/

            sb.append("RED LED RAW DATA(HRM): "+event.values[0]+ "\n");

            tRED.setText(sb.toString());

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String date = df.format(Calendar.getInstance().getTime());
            // Date date = new Date();
            sbDadosSensor.append(sIr.getName()+","+event.values[0]+","+date+"\n");


            //  try {
            //BufferedWriter out = new BufferedWriter(new FileWriter(new File(getFilesDir() ,"DADOS.txt")));

            // out.write(""+Float.toString(event.values[0]) + "\n");
            //out.write(Float.toString(event.values[0]));

            // out.write(Float.toString(event.values[0]) + "\n");


            // }catch (IOException e){
            // System.out.println(e);
            //}

        }
    }





    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }




}