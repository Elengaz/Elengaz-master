package com.SemiColon.Hmt.elengaz.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import me.anwarshahriar.calligrapher.Calligrapher;

//

public class AddPlace extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    Button addplace;
    Double longitude,latitude;

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
               /* Intent i = new Intent(AddPlace.this, AddService.class);
                i.putExtra("latitude",latitude);
                i.putExtra("longitude",longitude);
                startActivity(i);*/
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

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(24.7253981, 46.2620188))
                .title("مكتب الرياض")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        googleMap.setMyLocationEnabled(true);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(24.7253981, 46.2620188), 10));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng clickCoords) {
                Toast.makeText(AddPlace.this, ""+ clickCoords.latitude + " " + clickCoords.longitude, Toast.LENGTH_SHORT).show();
              longitude=clickCoords.longitude;
              latitude=clickCoords.latitude;
                Log.e("TAG", "Found @ " + clickCoords.latitude + " " + clickCoords.longitude);
            }
        });
    }


}