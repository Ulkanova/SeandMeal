package com.ulkanova;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap myMap;
    private List<MarkerOptions> marcadores = new ArrayList<>();
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng ubicacion, ubicacionObelisco, ubicacionUsuario;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            ubicacionUsuario= new LatLng(location.getLatitude(),location.getLongitude());
                            ubicacion = ubicacionUsuario;
                            CameraPosition cUbicacion = new CameraPosition.Builder().target(ubicacion).zoom(13.5f).build();
                            myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cUbicacion));
                        }

                        else{
//                            Coordenadas del obelisco...
                            ubicacionObelisco = new LatLng(-34.6037299182748, -58.381570184661);
                            ubicacion = ubicacionObelisco;
                            CameraPosition cUbicacion = new CameraPosition.Builder().target(ubicacion).zoom(14).build();
                            myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cUbicacion));
                        }
                    }
                });
        myMap = googleMap;
        myMap.setMyLocationEnabled(true);
        myMap.getUiSettings().setScrollGesturesEnabled(true);
        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setCompassEnabled(true);
        myMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(marcadores.size()>0) myMap.clear();
                MarkerOptions marcador = new MarkerOptions().position(latLng).draggable(false).title("Dirección de envío");
                marcadores.add(marcador);
                myMap.addMarker(marcador);
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setMessage("¿Esta es la dirección de envío?")
                        .setTitle("Ubicación")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dibujarRestaurante(latLng);
//                                enviarLocalizacion(latLng);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myMap.clear();
                                Toast.makeText(MapsActivity.this, "Intente nuevamente", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void dibujarRestaurante(LatLng latLng) {
        Random r = new Random();

// Una direccion aleatoria de 0 a 359 grados
        int direccionRandomEnGrados = r.nextInt(360);

// Una distancia aleatoria de 300 a 2000 metros
        int distanciaMinima = 300;
        int distanciaMaxima = 2000;
        int distanciaRandomEnMetros = r.nextInt(distanciaMaxima - distanciaMinima) + distanciaMinima;

        LatLng nuevaPosicion = SphericalUtil.computeOffset(
                latLng,
                distanciaRandomEnMetros,
                direccionRandomEnGrados
        );
        myMap.addMarker(new MarkerOptions().position(nuevaPosicion).title("Restaurante").draggable(false)
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        myMap.addPolyline(new PolylineOptions().add(latLng).add(nuevaPosicion).color(Color.BLUE));
        Toast.makeText(this, "Recorrido aproximado", Toast.LENGTH_SHORT).show();
        enviarLocalizacion(latLng);

    }

    public void enviarLocalizacion(LatLng latlong){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent pedido = new Intent();
                pedido.putExtra("lat", latlong.latitude);
                pedido.putExtra("lng", latlong.longitude);
                setResult(Activity.RESULT_OK, pedido);
                finish();
            }
        }, 2000); //Espera dos segundos para mostrar el recorrido aproximado del restaurante



    }
}