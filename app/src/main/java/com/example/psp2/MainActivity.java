package com.example.psp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements Runnable {

    private static final String TAG = "tag3";

    private class HebraThread extends Thread{
        @Override
        public void run() {
            super.run();
            Log.v(TAG,this.getName() + " hebra thread");
        }
    }

    private class HebraRunnable implements Runnable{
        @Override
        public void run() {
            Log.v(TAG,Thread.currentThread().getName() + " hebra runnable");
        }
    }

    @Override
    public void run() {
        Log.v(TAG,Thread.currentThread().getName() + " hebra mainActivity");
    }

    private class Contador extends Thread{
        @Override
        public void run() {
            /*synchronized (this) {
                for (int i = 0; i < 10; i++) {
                    contador = contador + 1;
                }
                Log.v(TAG, "Contador " + contador);
            }*/
            for (int i = 0; i < 10; i++) {
                synchronized (MainActivity.this) {
                    contador = contador + 1;
                }
            }
            Log.v(TAG, "Contador " + contador);
        }

        synchronized void metodo(){
            for (int i = 0; i < 10 ; i++) {
                contador = contador + 1;
            }
            Log.v(TAG,"Contador " + contador);
        }
    }

    private int contador = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initContador();
        verResultado();
    }

    private void verResultado() {
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.v(TAG,"contador timer: " + contador);
                /*if(contador == 500){
                    timer.cancel();
                }*/
            }
        };
        timer.schedule(timerTask,1,5);
    }



    private void initContador() {
        Contador contador;
        for (int i = 0; i < 50 ; i++) {
            contador = new Contador();
            contador.start();
        }
    }

    private void init() {
        //1
        HebraThread hebraThread = new HebraThread();
        hebraThread.start();
        //2
        HebraRunnable hebraRunnable = new HebraRunnable();
        Thread thread = new Thread(hebraRunnable);
        thread.start();
        //new Thread(hebraRunnable).start();

        //3
        new Thread(this).start();
        thread = new Thread(this);
        thread.start();

        //4
        thread = new Thread(){
            @Override
            public void run() {
                super.run();
                Log.v(TAG,this.getName() + " hebra thread anonima");

            }
        };
        thread.start();

        //...

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.v(TAG,Thread.currentThread().getName() + " hebra anonima runnable");
            }
        }).start();

    }


}
