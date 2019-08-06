package com.example.hideoutcabins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class addPhotos extends AppCompatActivity {

    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photos);
        register = findViewById(R.id.btnFinish);
    }
    public void onclick(View view){
        Intent intent = new Intent(addPhotos.this,viewDetails.class);
        startActivity(intent);
    }
}
