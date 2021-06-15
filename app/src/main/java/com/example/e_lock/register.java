package com.example.e_lock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class register extends AppCompatActivity implements LocationListener {
    public String url = MainActivity.url;
    TextView platnomor,nik,nama,jeniskendaraan,password;
    ImageButton signup;
    public int lebardesign = 411;
    public int tinggidesign = 823;
    public int dptinggi ;
    public int dplebar ;
    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    Location location;
    public String slng;
    public String slat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        dptinggi = (displayMetrics.heightPixels);
        dplebar = (displayMetrics.widthPixels);
        platnomor = findViewById(R.id.txtplatnomotregister);
        nik = findViewById(R.id.txtnikregister);
        nama = findViewById(R.id.txtnamaregister);
        jeniskendaraan = findViewById(R.id.txtjeniskendaraanregister);
        password = findViewById(R.id.txtpasswordregister);
        signup = findViewById(R.id.btnsignupregister);
        ViewGroup.LayoutParams btnregisterparam = signup.getLayoutParams();
        btnregisterparam.height = caltinggi(54,dptinggi);
        btnregisterparam.width = callebar(193,dplebar);
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(platnomor.getText().toString().equals("")||nik.getText().toString().equals("")||nama.getText().toString().equals("")||jeniskendaraan.getText().toString().equals("")||password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Lengkapi data",Toast.LENGTH_SHORT).show();
                }
                else{
                    daftar(platnomor.getText().toString(),nik.getText().toString(),nama.getText().toString(),password.getText().toString(),jeniskendaraan.getText().toString());
                }
            }
        });
    }


    void daftar(String plat_nomor,String nik,String nama,String password,String jenis_kendaraan){
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        postdaftar postdaftar = new postdaftar(plat_nomor, nik, nama, password, jenis_kendaraan);
        JsonPlaceHolder  jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<postdaftar> call = jsonPlaceHolder.postDaftar(postdaftar);
        call.enqueue(new Callback<com.example.e_lock.postdaftar>() {
            @Override
            public void onResponse(Call<com.example.e_lock.postdaftar> call, Response<com.example.e_lock.postdaftar> response) {
                if(response.code()==200){
                    kirim_lokasi(plat_nomor,slat,slng);
                }
                else{
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.e_lock.postdaftar> call, Throwable t) {

            }
        });

    }

    void kirim_lokasi(String plat_nomor,String lat,String lng){
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        postLokasi postLokasi = new postLokasi(plat_nomor, lat, lng);
        Call<postLokasi>call = jsonPlaceHolder.postlokasi(postLokasi);
        call.enqueue(new Callback<com.example.e_lock.postLokasi>() {
            @Override
            public void onResponse(Call<com.example.e_lock.postLokasi> call, Response<com.example.e_lock.postLokasi> response) {
                if(response.code()==200){
                    postLokasi postLokasi1 = response.body();
                    Toast.makeText(getApplicationContext(),postLokasi1.getMessage(),Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.e_lock.postLokasi> call, Throwable t) {

            }
        });
    }
    public int caltinggi(float value,int dp){
        return (int)(dp*(value/tinggidesign));
    }
    public int callebar(float value,int dp){
        return (int)(dp*(value/lebardesign));
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void fetchLastLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    location = location;
                    Toast.makeText(getApplicationContext(), location.getLatitude()+"\n"+location.getLongitude(), Toast.LENGTH_SHORT).show();
                    double lng = location.getLongitude();
                    double lat = location.getLatitude();
                    register.this.slng = String.valueOf(lng);
                    register.this.slat = String.valueOf(lat);


                }
            }
        });
    }
    @SuppressLint("MissingSuperCall")
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE :
                if(grantResults.length>0&&(grantResults[0]==PackageManager.PERMISSION_GRANTED)){
                    fetchLastLocation();
                }
                else {
                    Toast.makeText(getApplicationContext(),"silahkan enable permission gps apps ini",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    public void onBackPressed(){
        finish();
    }
}