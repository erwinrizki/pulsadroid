package com.example.mapku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

public class lacaktoko extends Activity{

	ListView ListView01;
	String[] nama ;
    String alamat[] ;
    String lati[] ;
    String longi[] ;
    
	@Override
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lacaktoko);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
		
		cobo();
	}

	private void cobo() {
		// TODO Auto-generated method stub
		String data;
		try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://www.akubingung.freevar.com/pulsa/jsontoko.php");
            HttpResponse response = client.execute(request);
            HttpEntity entity=response.getEntity();
            data=EntityUtils.toString(entity);
            Log.e("STRING", data);
            
            try {
		       JSONObject object=new JSONObject(data);
		       JSONArray Jarray = object.getJSONArray("data_toko");
		       
		       nama = new String[Jarray.length()];
		       alamat = new String[Jarray.length()];
		       lati = new String[Jarray.length()];
		       longi = new String[Jarray.length()];
		       
		       for(int i=0;i<Jarray.length(); i++)
		       {
		    	   JSONObject obj = Jarray.getJSONObject(i);
		    	   nama[i] = obj.getString("nama_toko");
			       alamat[i] = obj.getString("alamat_toko");
			       lati[i] = obj.getString("latitude_toko");
			       longi[i] = obj.getString("longitude_toko");
		       }    
       
		      } catch (JSONException e) {
		       // TODO Auto-generated catch block
		    	  Toast.makeText(this,"Gagal JSON: " +e.getMessage(), Toast.LENGTH_SHORT).show();
		      }
          
        } catch (ClientProtocolException e) {
            //Log.d("HTTPCLIENT", e.getLocalizedMessage());
        	Toast.makeText(this,"Gagal Client Protocol", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            //Log.d("HTTPCLIENT", e.getLocalizedMessage());
        	Toast.makeText(this,"Gagal IO ", Toast.LENGTH_SHORT).show();
        }	
		
		
		ListView01 = (ListView)findViewById(R.id.ListView01);
		ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, nama));
		ListView01.setOnItemClickListener(new OnItemClickListener() {
	    	@Override
	    	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	    		final String selection = nama[arg2];
	    		final String selection2 = alamat[arg2];
	    		final String selection3 = lati[arg2];
	    		final String selection4 = longi[arg2];
	    		Intent aa = new Intent(getApplicationContext(), lacaktoko2.class);
				aa.putExtra("nama_toko", selection);
				aa.putExtra("alamat_toko", selection2);
				aa.putExtra("latitude_toko", selection3);
				aa.putExtra("longitude_toko", selection4);
				startActivity(aa);
			}
	    });
	}
}	
	
	
	
