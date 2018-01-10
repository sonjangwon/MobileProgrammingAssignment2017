package com.example.jaehyukshin.goolgemaps;

import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static com.example.jaehyukshin.goolgemaps.R.id.map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView tv;
    EditText et3;
    Geocoder geocoder;
    List<Address> list = null;
    Boolean searched = true;
    double latitude;
    double longitude;
    GoogleMap gMap;

    double currentLatitude;
    double currentLongitude;


    MapView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        tv = (TextView) findViewById(R.id.textView4); // 결과창
        Button b2 = (Button)findViewById(R.id.button2);
        et3 = (EditText)findViewById(R.id.editText3);

        geocoder = new Geocoder(this);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(map);
        mapFragment.getMapAsync(this);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchLocation(et3.getText().toString());

                gMap.clear();
                try{
                    gMap.setMyLocationEnabled(true);
                    //Location location = gMap.getMyLocation();
                }
                catch (SecurityException e){

                }

                LatLng RESTAURANT = new LatLng(latitude, longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(RESTAURANT);
                markerOptions.title("맛집");
                markerOptions.snippet("This is matzip");
                gMap.addMarker(markerOptions);
                gMap.moveCamera(CameraUpdateFactory.newLatLng(RESTAURANT));
                gMap.animateCamera(CameraUpdateFactory.zoomTo(13));
            }
        });
    } // end of onCreate

    private List<Address> searchLocation(String searchStr){
        List<Address> addressList = null;

        try{
            addressList = geocoder.getFromLocationName(searchStr, 10);

            if (addressList != null) {
                if (addressList.size() == 0) {
                    tv.setText("해당되는 주소 정보는 없습니다");
                    searched = false;
                    return addressList;
                } else {
                    tv.setText(addressList.get(0).toString());
                    addressList.get(0).getCountryName();  // 국가명
                    latitude = addressList.get(0).getLatitude();        // 위도
                    longitude = addressList.get(0).getLongitude();    // 경도
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
            Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
        }
        return addressList;
    }

    @Override
    public void onMapReady(GoogleMap map) {

        gMap = map;

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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
} // end of class