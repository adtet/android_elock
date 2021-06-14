package com.example.e_lock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.Transliterator;
import android.os.Bundle;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.light.Position;

public class show_map extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private Double slat;
    private Double slong;
    private MapboxMap mapboxMap;
    private String DROPPED_MARKER_LAYER_ID = "DROPPED_MARKER_LAYER_ID";
    private IconFactory icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiYWRpeWF0bWEiLCJhIjoiY2twd2d4aDJvMDAyYTJ3cGVzMjRkdDAwYSJ9.dGeoVJvVHEJtdgAxgkVnkw");
        setContentView(R.layout.activity_show_map);
        slat = Double.parseDouble(getIntent().getStringExtra("Lat_from_login").toString());
        slong = Double.parseDouble(getIntent().getStringExtra("Lng_from_login").toString());

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        show_map.this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                CameraPosition position = new CameraPosition.Builder().target(new LatLng(slat,slong)).zoom(15.0).tilt(20.0).build();

                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 10);
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(slat, slong))
                        .title("Posisi Anda"));

            }
        });


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}