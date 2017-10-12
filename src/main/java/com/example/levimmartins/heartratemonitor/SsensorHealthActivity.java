package com.example.levimmartins.heartratemonitor;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.List;

public class SsensorHealthActivity extends Activity {

    Ssensor ir=null;
    Ssensor red=null;
    private String id;
    ToggleButton btn_start = null;

    TextView tIR = null;
    TextView tRED = null;
    ImageView imViewPulse = null;

    //SSListenerIR mSSListenerIR = null;
    SSListenerRED mSSListenerRED = null;

    SsensorManager mSSensorManager = null;
    SsensorExtension mSsensorExtension = null;

    Activity mContext;

    //get path from sdcard
    String sdcard;
    File namefile;
    BufferedWriter out;
    StringBuffer sbDadosSensor;
    String name;
    Funcoes funcoes;
    WriteFile Arquivo;
    Vibrator v;
    private boolean mAllowVibrate = false;
    private boolean mAllowCalculating = false;
    private ImageView imageView2;

    @TargetApi(23) @Override
    protected void onCreate(Bundle savedInstanceState){
         mContext = this;
        verifyStoragePermissions(mContext);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        sdcard = Environment.getExternalStorageDirectory().getPath();


        this.id =  getIntent().getStringExtra("EXTRA_SESSION_ID");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        sbDadosSensor = new StringBuffer();
        btn_start = (ToggleButton)findViewById(R.id.btn_start);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
       // tIR = (TextView) findViewById(R.id.tIR);
        tRED = (TextView) findViewById(R.id.tRED);


        //mSSListenerIR = new SSListenerIR();
        mSSListenerRED = new SSListenerRED();


        name = sdcard+"/dadosSENSOR.txt";

        Arquivo = new WriteFile(name);

        funcoes = new Funcoes();



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

    }

    public void OnIniciar(View view){


       btn_start.setSelected(!btn_start.isSelected());


       /* if (android.os.Build.VERSION.SDK_INT >=23){
            if(checkSelfPermission(Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED){
                //SHOW EXPLANATION
                if (shouldShowRequestPermissionRationale(Manifest.permission.BODY_SENSORS)){
                    //Explai to the user why we need to read the contacts
                }

                requestPermissions(new String[] {Manifest.permission.BODY_SENSORS}, 101);

                //MY_PERMISSIONS_REQUEST_READ_CONTACTS is an app-defined int constant

                return;
            }
        }*/


        try{
            if(!btn_start.isSelected()){
                //HRM OFF
                btn_start.setText("Start");
              //  mSSensorManager.unregisterListener(mSSListenerIR, ir);
                mSSensorManager.unregisterListener(mSSListenerRED, red);
               // imageView2.setVisibility(View.INVISIBLE);
                // Arquivo.WriteWord("Teste:"+sbDadosSensor);
                //Arquivo.CloseFile();




            }else{
                imageView2.setVisibility(View.VISIBLE);
                mSsensorExtension = new SsensorExtension();

                try{
                    mSsensorExtension.initialize(mContext);
                    mSSensorManager = new SsensorManager(mContext, mSsensorExtension);

                    ir = mSSensorManager.getDefaultSensor(Ssensor.TYPE_HRM_LED_IR);
                    red = mSSensorManager.getDefaultSensor(Ssensor.TYPE_HRM_LED_RED);

                }catch(SsdkUnsupportedException e){
                   // Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                    mContext.finish();

                }catch (IllegalArgumentException e){
                  //  Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    mContext.finish();
                }catch (SecurityException e){
                  //  Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    mContext.finish();
                }

                btn_start.setText("Stop");
                btn_start.setVisibility(View.INVISIBLE);

                new CountDownTimer(20000,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {

                        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        if(mAllowVibrate){
                            v.vibrate(50);
                        }
                        tRED.setText("Seconds remaining: "+millisUntilFinished/1000);
                        //System.out.println("Seconds remaining: "+millisUntilFinished/1000);
                    }

                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onFinish() {
                        //System.out.println("Exibir BPM");
                       // mSSensorManager.cancelTriggerSensor(mSSensorManager.,red);
                        //mSSensorManager.unregisterListener(mSSListenerRED, red);
                        if(mAllowCalculating) {
                            String type = "pontuar";
                            BackgroundWorker backgroundWorker = new BackgroundWorker(mContext);
                            backgroundWorker.execute(type, id);

                            Arquivo.WriteWord("" + sbDadosSensor);
                            Arquivo.CloseFile();

                            funcoes = new Funcoes();
                            funcoes.process(0.75, 99.0);

                            List<Double> bpm = funcoes.getBpm();
                            Long roundedBpm = Math.round(bpm.get(0));
                            tRED.setText(roundedBpm.toString());


                            new CountDownTimer(3000,1000){

                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                       Intent openCongrats = new Intent(mContext,  Congratulation.class);
                                       openCongrats.putExtra("EXTRA_SESSION_ID", id);
                                       startActivity(openCongrats);
                                }
                            }.start();


                        }



                        //Arquivo.WriteWord(""+sbDadosSensor);
                       // Arquivo.CloseFile();




                    }
                }.start();



                if(mSSensorManager != null){

                    //mSSensorManager.registerListener(mSSListenerIR, ir, SensorManager.SENSOR_DELAY_NORMAL);
                    mSSensorManager.registerListener(mSSListenerRED, red, SensorManager.SENSOR_DELAY_NORMAL);
                }
            }

        }catch (IllegalArgumentException e){
            //ErrorToast(e);
        }


        //}
    }

    public void ErrorToast(IllegalArgumentException e){
       // Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        btn_start.setSelected(!btn_start.isSelected());
        btn_start.setText("Start");
        mAllowVibrate = true;
        mAllowCalculating = true;
        v.cancel();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mAllowVibrate = true;
        mAllowCalculating = true;
    }





    @Override
    protected  void onPause(){
        super.onPause();

        mAllowVibrate = false;
        mAllowCalculating = false;
        //v.cancel();
        Arquivo.WriteWord(""+sbDadosSensor);
        Arquivo.CloseFile();

        //btn_start.setVisibility(View.VISIBLE);
        btn_start.setText("Start");


        try{

          //  if(ir != null){
              //  mSSensorManager.unregisterListener(mSSListenerIR, ir);
                //tIR.setText("");

         //   }

            if(red !=null){
                mSSensorManager.unregisterListener(mSSListenerRED, red);
                tRED.setText("");

            }
        }catch (IllegalArgumentException e){
        //    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            this.finish();
        }catch (IllegalStateException e){
        //    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            this.finish();
        }
    }


  //  private  class SSListenerIR implements SsensorEventListener{

 //       @Override
   //     public void OnAccuracyChanged(Ssensor arg0, int arg1){
            //TODO
  //      }

     //   @Override
     //   public void OnSensorChanged(SsensorEvent event){

            //    Ssensor sIR = event.sensor;
            //   StringBuffer sb = new StringBuffer();
               /* sb.append("=== Sensor Information ===\n")
                        .append("Name: "+sIR.getName() + "\n")
                        .append("IR RAW DATA(HRM): "+event.values[0] + "\n");
*/
            // sb.append("IR RAW DATA(HRM): "+event.values[0] + "\n");

            // tIR.setText(sb.toString());

   //     }
  //  }


    private class SSListenerRED implements SsensorEventListener{



        @Override
        public void OnAccuracyChanged(Ssensor arg0, int arg1){

        }


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

            //tRED.setText(sb.toString());

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String date = df.format(Calendar.getInstance().getTime());
            // Date date = new Date();
            sbDadosSensor.append(sIr.getName()+","+event.values[0]+","+date+"\n");

           //Arquivo.WriteWord(""+sbDadosSensor);

            //funcoes.process(0.75,99.0); NaN


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