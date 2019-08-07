package com.example.hideoutcabins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registration_Rooms extends AppCompatActivity {
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__rooms);
        register = findViewById(R.id.submitBtn);
    }

    public void onclick(View view){
        Intent intent = new Intent(Registration_Rooms.this,cabanaLocation.class);
        startActivity(intent);
    }
}
