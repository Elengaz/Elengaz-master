package com.SemiColon.Hmt.elengaz.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.SemiColon.Hmt.elengaz.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

//

public class AddPlace extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    Button addplace;
    Double longitude,latitude;
    private final float zoom=13f;
    private LatLng latLng;
    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);

        addplace = findViewById(R.id.addplace);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        addplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("llllll",latitude+"\n"+longitude);
                Intent i = new Intent(AddPlace.this, AddService.class);
                i.putExtra("latitude",latitude);
                i.putExtra("longitude",longitude);
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
            map = googleMap;

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            googleMap.setMyLocationEnabled(true);

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(AddPlace.this);
            Task<Location> lastLocation = fusedLocationProviderClient.getLastLocation();
            if (lastLocation.isSuccessful())
            {
                lastLocation.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful())
                        {
                            Location location = task.getResult();


                            if (location!=null)
                            {
                                latitude = location.getLatitude();
                                longitude=location.getLongitude();

                                Log.e("locccccccc",latitude+"   "+longitude);
                                latLng = new LatLng(latitude,longitude);
                                Geocoder geocoder = new Geocoder(AddPlace.this);
                                try {
                                    List<Address> addressList = geocoder.getFromLocation(latitude,longitude,1);


                                    MarkerOptions markerOptions = new MarkerOptions();

                                    if (addressList.get(0).getLocality()!=null)
                                    {
                                        markerOptions.title(addressList.get(0).getSubLocality());
                                    }

                                    markerOptions.position(latLng);
                                    map.addMarker(markerOptions);
                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
            }









        /*googleMap.setMyLocationEnabled(true);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(24.7253981, 46.2620188), 10));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng clickCoords) {
              longitude=clickCoords.longitude;
              latitude=clickCoords.latitude;
                Log.e("TAG", "Found @ " + clickCoords.latitude + " " + clickCoords.longitude);
            }
        });*/
    }


}