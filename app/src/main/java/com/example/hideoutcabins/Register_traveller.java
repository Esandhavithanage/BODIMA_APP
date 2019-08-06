package com.example.hideoutcabins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Register_traveller extends AppCompatActivity {
Button registerTraveler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_traveller);

        registerTraveler = findViewById(R.id.btntravellerregister);
    }

    public void registerOnClick(View view){
        Intent intent = new Intent(Register_traveller.this,Login.class);
        startActivity(intent);
    }
}
