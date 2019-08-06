package com.example.hideoutcabins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class viewDetails extends AppCompatActivity {

    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        register = findViewById(R.id.btnFinish);


    }
    public void onclick(View view){
       // Intent intent = new Intent(viewDetails.this,.class);
       // startActivity(intent);
    }
}
