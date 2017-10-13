package com.example.levimmartins.heartratemonitor;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TelaInicial extends AppCompatActivity {

    private String user_name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        verifyStoragePermissions(this);

        user_name = getIntent().getStringExtra("EXTRA_SESSION_ID");
    }


    public void onHRM(View view){
        Intent openHRM = new Intent(TelaInicial.this, SsensorHealthActivity.class);
        openHRM.putExtra("EXTRA_SESSION_ID", user_name);
        startActivity(openHRM);
    }

    public void onDicas(View view){
        Intent openDicas = new Intent(this, Dicas.class);
        openDicas.putExtra("EXTRA_SESSION_ID", user_name);
        startActivity(openDicas);
    }

    public void onConquistas(View view){



        String type = "pontos";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,user_name);

       // Intent openConquistas = new Intent(this, Conquistas.class);
        //openConquistas.putExtra("EXTRA_SESSION_PONTOS",backgroundWorker.global_user.toString());
       // openConquistas.putExtra("EXTRA_SESSION_ID", user_name);
       // this.startActivity(openConquistas);

    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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
