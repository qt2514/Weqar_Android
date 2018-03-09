package com.weqar.weqar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class SplashActivity extends AppCompatActivity {
    ImageView IM_gif;
    private Thread mSplashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_splash);
        IM_gif=findViewById(R.id.gifImageView);
        Glide.with(SplashActivity.this).load(R.drawable.weqar_gif).
                asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).crossFade().into(IM_gif);
        final SplashActivity sPlashScreen = this;

        // The thread to wait for splash screen events
        mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        // Wait given period of time or exit on touch
                        wait(10000);
                    }
                }
                catch(InterruptedException ex){
                }

                finish();

                // Run next activity
                Intent intent = new Intent();
                intent.setClass(sPlashScreen, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };

        mSplashThread.start();




    }


}
