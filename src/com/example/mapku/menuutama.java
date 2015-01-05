package com.example.mapku;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class menuutama extends Activity {
	
	Button semua, lacak, keluar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menuutama);
	
		semua=(Button)findViewById(R.id.semuatoko);
		lacak=(Button)findViewById(R.id.lacaktoko);
		keluar=(Button)findViewById(R.id.keluar);
	
		semua.setOnClickListener(new Button.OnClickListener() {
	       	@Override
			public void onClick(View v) {
				semuatoko();
	       	}
	    });
		lacak.setOnClickListener(new Button.OnClickListener() {
	       	@Override
			public void onClick(View v) {
				lacaktoko();
	       	}
	    });
		keluar.setOnClickListener(new Button.OnClickListener() {
	       	@Override
			public void onClick(View v) {
	       		//android.os.Process.killProcess(android.os.Process.myPid());
	            //System.exit(1);
	       		finish();
	       	}
	    });
		
	}
	
	public void semuatoko() {
		Intent i=new Intent(this,semuatoko.class);
		startActivity(i);
	}
	
	public void lacaktoko() {
		Intent i=new Intent(this,lacaktoko.class);
		startActivity(i);
	}

}
