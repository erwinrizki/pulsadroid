package com.example.mapku;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;
import com.example.mapku.GMapV2Direction;
import com.example.mapku.GetRotueListTask;
import com.example.mapku.GMapV2Direction.DirectionReceivedListener;
import com.google.android.gms.maps.model.PolylineOptions;

import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;


public class semuatoko extends android.support.v4.app.FragmentActivity implements DirectionReceivedListener {
	
	public LatLng[] destinationPosition;
	public String[] destinationPositionTitle;
	public LatLng startPosition;
	
	public LatLng myLocation;
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.semuatoko);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		        .getMap();
		map.setMyLocationEnabled(true);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	    
		tempelan();
	}
	
	public void tempelan() {
		String data;
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        
        if(location!=null){
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            myLocation = new LatLng(latitude, longitude);
        }
        
        Marker maku =  map.addMarker(new MarkerOptions().position(myLocation)
		        .title("Your Position"));       
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 10));  
        
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
		       
		       for(int i=0;i<Jarray.length(); i++)
		       {
			        JSONObject obj = Jarray.getJSONObject(i);
			        String nama = obj.getString("nama_toko");
			        String alamat = obj.getString("alamat_toko");
			        String lati = obj.getString("latitude_toko");
			        String longi = obj.getString("longitude_toko");
			        
			        map.addMarker(new MarkerOptions().
			        		position(new LatLng(Double.parseDouble(lati), Double.parseDouble(longi))).title(nama).snippet(alamat));	        

					new GetRotueListTask(semuatoko.this, myLocation, new LatLng(Double.parseDouble(lati), Double.parseDouble(longi)), 
							GMapV2Direction.MODE_DRIVING, this).execute();
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
	}
	
	@Override
	public void OnDirectionListReceived(List<LatLng> mPointList) {
		// TODO Auto-generated method stub
		if (mPointList != null) {
			PolylineOptions rectLine = new PolylineOptions().width(10).color(
					Color.GREEN);
			for (int i = 0; i < mPointList.size(); i++) {
				rectLine.add(mPointList.get(i));
			}
			map.addPolyline(rectLine);
			}
			
	}
}
