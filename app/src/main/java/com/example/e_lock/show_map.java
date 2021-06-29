package com.example.e_lock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class show_map extends AppCompatActivity implements OnMapReadyCallback {
    public String url = MainActivity.url;
    private MapView mapView;
    private Double slat;
    private Double slong;
    private String plat_nomor;
    private MapboxMap mapboxMap;
    private String DROPPED_MARKER_LAYER_ID = "DROPPED_MARKER_LAYER_ID";
    private IconFactory icon;
    AlertDialog.Builder dialog;
    LayoutInflater layoutInflater;
    View dialogview;
    ImageButton laporkan;
    Button laporan;
    EditText deskripsi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiYWRpeWF0bWEiLCJhIjoiY2twd2d4aDJvMDAyYTJ3cGVzMjRkdDAwYSJ9.dGeoVJvVHEJtdgAxgkVnkw");
        setContentView(R.layout.activity_show_map);
        slat = Double.parseDouble(getIntent().getStringExtra("Lat_from_login").toString());
        slong = Double.parseDouble(getIntent().getStringExtra("Lng_from_login").toString());
        plat_nomor = getIntent().getStringExtra("plat_nomor_from_login").toString();
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
                laporan = findViewById(R.id.select_location_button2);
                laporan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogForm2();
                    }
                });

            }
        });


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void dialogForm2(){
        dialog = new AlertDialog.Builder(show_map.this,R.style.CustomDialog);
        layoutInflater = getLayoutInflater();
        dialogview = layoutInflater.inflate(R.layout.pop_up_report,null);
        dialog.setView(dialogview);
        dialog.setCancelable(true);
        final AlertDialog alertDialog = dialog.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        laporkan = dialogview.findViewById(R.id.btnlaporakanpopuplaporan);
        deskripsi = dialogview.findViewById(R.id.txtdeskrpsipopuplaporan);

        laporkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_laporan(show_map.this.plat_nomor,deskripsi.getText().toString(),String.valueOf(show_map.this.slat),String.valueOf(show_map.this.slong));
            }
        });

        alertDialog.show();
    }



    void upload_laporan(String plat_nomor,String laporan,String latitude,String longitude){
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        postLaporan postLaporan = new postLaporan(plat_nomor, laporan, latitude, longitude);
        Call<postLaporan>call = jsonPlaceHolder.postlaporan(postLaporan);
        call.enqueue(new Callback<com.example.e_lock.postLaporan>() {
            @Override
            public void onResponse(Call<com.example.e_lock.postLaporan> call, Response<com.example.e_lock.postLaporan> response) {
                if(response.code()==200){
                    postLaporan postLaporan1 = response.body();
                    Toast.makeText(getApplicationContext(),postLaporan1.getMessage(),Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<com.example.e_lock.postLaporan> call, Throwable t) {

            }
        });

    }




}