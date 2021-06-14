package com.example.e_lock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login extends AppCompatActivity {
    public String url = MainActivity.url;
    public int lebardesign = 411;
    public int tinggidesign = 823;
    public int dptinggi ;
    public int dplebar ;
    EditText txtnik,txtpass;
    ImageButton btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        dptinggi = (displayMetrics.heightPixels);
        dplebar = (displayMetrics.widthPixels);
        txtnik = findViewById(R.id.txtusernamelogin);
        txtpass = findViewById(R.id.txtpasswordlogin);


        btnlogin = findViewById(R.id.btnloginlogin);
        ViewGroup.LayoutParams btnloginparam = btnlogin.getLayoutParams();
        btnloginparam.height = caltinggi(54,dptinggi);
        btnloginparam.width = callebar(193,dplebar);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtnik.getText().toString().equals("")||txtpass.getText().toString().equals("")){
                    txtnik.setText("");
                    txtpass.setText("");
                    Toast.makeText(getApplicationContext(),"Completed the form",Toast.LENGTH_SHORT).show();
                }
                else{
                    masuk(txtnik.getText().toString(),txtpass.getText().toString());
                }
            }
        });



    }

    void masuk(String nik,String password){
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        postlogin postlogin = new postlogin(nik, password);
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        Call<postlogin>call = jsonPlaceHolder.postlogin(postlogin);
        call.enqueue(new Callback<com.example.e_lock.postlogin>() {
            @Override
            public void onResponse(Call<com.example.e_lock.postlogin> call, Response<com.example.e_lock.postlogin> response) {
                if(response.code()==200){
                    postlogin postlogin1 = response.body();
                    Toast.makeText(getApplicationContext(),postlogin1.getMessage(),Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(login.this,show_map.class);
                    i.putExtra("Lat_from_login",postlogin1.getLat());
                    i.putExtra("Lng_from_login",postlogin1.getLng());
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.e_lock.postlogin> call, Throwable t) {

            }
        });
    }
    public int caltinggi(float value,int dp){
        return (int)(dp*(value/tinggidesign));
    }
    public int callebar(float value,int dp){
        return (int)(dp*(value/lebardesign));
    }



}