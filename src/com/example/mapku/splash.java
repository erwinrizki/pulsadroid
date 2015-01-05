package com.example.mapku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
 
public class splash extends Activity
{
	
	
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
 
        Thread timer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(3000);
                } 
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    finish();
                    Intent m = new Intent(splash.this, menuutama.class);
                    startActivityForResult(m,0);
                }
            }
        };
        timer.start();
    }
}