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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.SemiColon.Hmt.elengaz.API.Service.Network;
import com.SemiColon.Hmt.elengaz.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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

public class AddPlace extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.OnConnectionFailedListener {

    SupportMapFragment mapFragment;
    Button addplace;
    Double longitude,latitude;
    private final float zoom=12f;
    private LatLng latLng;
    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private boolean PERMISSION_GRANTED = false;
    private boolean isConnected = false;
    private final int PER_REQ = 200;
    private GoogleApiClient apiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"JannaLT-Regular.ttf",true);

        apiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addOnConnectionFailedListener(this).build();
        apiClient.connect();
        addplace = findViewById(R.id.addplace);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        checkConnectivity();
        checkPermission();

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

    private void checkConnectivity() {
        if (Network.getConnection(this))
        {
            isConnected = true;
        }else
            {
                isConnected = false;
                Toast.makeText(this, getString(R.string.connection), Toast.LENGTH_SHORT).show();
            }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET)==PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET)==PackageManager.PERMISSION_GRANTED &&ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED &&ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED  )
        {
            PERMISSION_GRANTED = true;
        }else
            {
                String [] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET};
                ActivityCompat.requestPermissions(this,permissions,PER_REQ);
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PER_REQ)
        {
            if (grantResults.length>0)
            {
                for (int i =0;i<grantResults.length;i++)
                {
                    if (grantResults[i]!=PackageManager.PERMISSION_GRANTED)
                    {
                        PERMISSION_GRANTED = true;
                    }else
                        {
                            PERMISSION_GRANTED = false;
                        }
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap!=null)
        {
            if (isConnected)
            {
                if (PERMISSION_GRANTED)
                {
                    map = googleMap;

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    googleMap.setMyLocationEnabled(true);

                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(AddPlace.this);
                    Task<Location> lastLocation = fusedLocationProviderClient.getLastLocation();
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
                                            markerOptions.title(addressList.get(0).getLocality());

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

                }else
                    {
                        Toast.makeText(this, "Permission not granted ", Toast.LENGTH_SHORT).show();
                    }
            }else
                {
                    Toast.makeText(this, getString(R.string.connection), Toast.LENGTH_SHORT).show();
                }

        }


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (apiClient!=null)
        {
            if (apiClient.isConnected())
            {
                apiClient.disconnect();
            }
        }
    }
}