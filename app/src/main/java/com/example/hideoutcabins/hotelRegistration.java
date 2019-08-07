package com.example.hideoutcabins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class hotelRegistration extends AppCompatActivity {
    Button registr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_registration);
        registr = findViewById(R.id.btnRegister);
    }

    public void  onClickRegister(View view){
       Intent intent = new Intent(hotelRegistration.this,Registration_Rooms.class);
       startActivity(intent);
    }
}
