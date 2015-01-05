package com.example.mapku;

import java.util.List;

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


public class lacaktoko2 extends android.support.v4.app.FragmentActivity implements DirectionReceivedListener {
	
	public LatLng myLocation;
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lacaktoko2);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map2))
		        .getMap();
		map.setMyLocationEnabled(true);
	    
		tempelan();
	}
	
	public void tempelan() {
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        
        if(location!=null){
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            myLocation = new LatLng(latitude, longitude);
        }
        
        String nama = getIntent().getStringExtra("nama_toko");
        String alamat = getIntent().getStringExtra("alamat_toko");
        String lati = getIntent().getStringExtra("latitude_toko");
        String longi = getIntent().getStringExtra("longitude_toko");
        
        
        Marker maku =  map.addMarker(new MarkerOptions().position(myLocation)
		        .title("Your Position"));       
        
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 12));  
        
        map.addMarker(new MarkerOptions().
        		position(new LatLng(Double.parseDouble(lati), Double.parseDouble(longi))).title(nama).snippet(alamat));	        

		new GetRotueListTask(lacaktoko2.this, myLocation, new LatLng(Double.parseDouble(lati), Double.parseDouble(longi)), 
				GMapV2Direction.MODE_DRIVING, this).execute();    
	}
	
	@Override
	public void OnDirectionListReceived(List<LatLng> mPointList) {
		// TODO Auto-generated method stub
		if (mPointList != null) {
			PolylineOptions rectLine = new PolylineOptions().width(10).color(
					Color.CYAN);
			for (int i = 0; i < mPointList.size(); i++) {
				rectLine.add(mPointList.get(i));
			}
			map.addPolyline(rectLine);
			}
			
	}
}



