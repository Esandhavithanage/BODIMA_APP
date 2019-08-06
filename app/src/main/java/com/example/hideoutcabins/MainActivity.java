package com.example.hideoutcabins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s = findViewById(R.id.button2);
    }

    public void click(View view){
        Intent usermenu = new Intent(MainActivity.this, Traveller_nav.class);
        startActivity(usermenu);
    }

}
