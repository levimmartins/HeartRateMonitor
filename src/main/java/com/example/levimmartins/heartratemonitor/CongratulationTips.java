package com.example.levimmartins.heartratemonitor;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

public class CongratulationTips extends AppCompatActivity {

    public String user_name;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Activity getmContext() {
        return mContext;
    }

    public void setmContext(Activity mContext) {
        this.mContext = mContext;
    }

    public Activity mContext;

    private MediaPlayer ring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulation_tips);

        this.user_name = getIntent().getStringExtra("EXTRA_SESSION_ID");

         ring = MediaPlayer.create(this, R.raw.magicchime01);


        new CountDownTimer(2000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                    ring.start();
            }

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onFinish() {
                ring.stop();
                Intent openTelaInicio = new Intent(mContext,  TelaInicial.class);
                openTelaInicio.putExtra("EXTRA_SESSION_ID", user_name);
                startActivity(openTelaInicio);


            }
        }.start();





    }
}
