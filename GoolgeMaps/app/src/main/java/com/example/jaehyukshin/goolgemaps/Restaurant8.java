package com.example.jaehyukshin.goolgemaps;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static com.example.jaehyukshin.goolgemaps.R.id.map;

public class Restaurant8 extends AppCompatActivity implements OnMapReadyCallback {

    Geocoder geocoder;
    double latitude;
    double longitude;
    GoogleMap gMap;
    String restaurant = "서울 영등포구 국제금융로2길 25";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant8);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.eighthinformation);
        getSupportActionBar().hide();
        Button callButton = (Button) findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String tel = "tel:023170000";
                startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
            }
        });
        geocoder = new Geocoder(this);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(map);
        mapFragment.getMapAsync(this);

        List<Address> addressList = null;

        try{
            addressList = geocoder.getFromLocationName(restaurant, 10);

            latitude = addressList.get(0).getLatitude();        // 위도
            longitude = addressList.get(0).getLongitude();    // 경도
        }
        catch(IOException e){
            e.printStackTrace();
            Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
        }
    } // end of onCreate

    @Override
    public void onMapReady(GoogleMap map) {

        gMap = map;

        LatLng RESTAURANT = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(RESTAURANT);
        markerOptions.title("카페 콜론");
        markerOptions.snippet("여의도 증권가의 힐링 스팟");
        gMap.addMarker(markerOptions);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(RESTAURANT));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        if (ActivityCompat.checkSelfPermission(Restaurant8.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Restaurant8.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Restaurant8.this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{
            if(!gMap.isMyLocationEnabled())
                gMap.setMyLocationEnabled(true);

            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (myLocation == null) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                String provider = lm.getBestProvider(criteria, true);
                myLocation = lm.getLastKnownLocation(provider);
            }

            if(myLocation!=null){
                LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14), 1500, null);
            }
        }
    }
}
