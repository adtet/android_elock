package com.example.e_lock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class main_menu extends AppCompatActivity {
    public int lebardesign = 411;
    public int tinggidesign = 823;
    public int dptinggi ;
    public int dplebar ;
    ImageButton btnlogin,btnregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        dptinggi = (displayMetrics.heightPixels);
        dplebar = (displayMetrics.widthPixels);

        btnlogin = findViewById(R.id.btnloginmenu);
        ViewGroup.LayoutParams btnloginparam = btnlogin.getLayoutParams();
        btnloginparam.height = caltinggi(54,dptinggi);
        btnloginparam.width = callebar(193,dplebar);

        btnregister = findViewById(R.id.btnsignupmenu);
        ViewGroup.LayoutParams btnregisterparam = btnregister.getLayoutParams();
        btnregisterparam.height = caltinggi(54,dptinggi);
        btnregisterparam.width = callebar(193,dplebar);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(main_menu.this,login.class));

            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(main_menu.this,register.class));
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