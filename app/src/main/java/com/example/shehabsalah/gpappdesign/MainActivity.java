package com.example.shehabsalah.gpappdesign;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends Activity {
    ImageView img;
    Handler hand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ViewGroup view = (ViewGroup)findViewById(R.id.RelativeLO);
            TransitionManager.beginDelayedTransition(view);
            img.setVisibility(View.VISIBLE);

        }
    };
    Handler handl2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ViewGroup view = (ViewGroup)findViewById(R.id.RelativeLO);
            TransitionManager.beginDelayedTransition(view);
            img.setVisibility(View.INVISIBLE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView)findViewById(R.id.imageView);
        img.setImageResource(R.mipmap.logo1);
        img.setVisibility(View.INVISIBLE);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                long futureTime = System.currentTimeMillis() + 2000;
                while(System.currentTimeMillis() < futureTime) {
                    synchronized (this) {
                        try {
                            wait(futureTime - System.currentTimeMillis());
                        } catch (Exception e) {
                        }
                    }
                }
                hand.sendEmptyMessage(0);
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                long futureTime = System.currentTimeMillis() + 5000;
                while(System.currentTimeMillis() < futureTime) {
                    synchronized (this) {
                        try {
                            wait(futureTime - System.currentTimeMillis());
                        } catch (Exception e) {
                        }
                    }
                }
                handl2.sendEmptyMessage(0);
            }
        };
        Thread appThread = new Thread(r);
        appThread.start();
        Thread appThread2 = new Thread(r2);
        appThread2.start();
        int Delay = 6000;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(i);
                finish();
            }
        }, Delay);

    }
}