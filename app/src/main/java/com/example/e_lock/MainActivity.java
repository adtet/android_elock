package com.example.e_lock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static String url = "http://156.67.221.101:8005/elock/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        koneksi();
    }

    void koneksi(){
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<responseWelcome> call = jsonPlaceHolder.getwelcome();
        call.enqueue(new Callback<responseWelcome>() {
            @Override
            public void onResponse(Call<responseWelcome> call, Response<responseWelcome> response) {
                if(response.code()==200){
                    responseWelcome responseWelcome = response.body();
                    Toast.makeText(getApplicationContext(),responseWelcome.getMessage(),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,main_menu.class));
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Lost Connection_1",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<responseWelcome> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Lost Connection",Toast.LENGTH_SHORT).show();
            }
        });
    }
}