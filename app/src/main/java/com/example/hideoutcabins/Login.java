package com.example.hideoutcabins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {
Button login,register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.btnlogin);
        register = findViewById(R.id.btnregister);
    }

    public void loginOnClick(View view){
        Intent intent = new Intent(Login.this,Traveller_nav.class);
        startActivity(intent);
    }

    public void registerOnClick(View view){
        Intent intent = new Intent(Login.this,Register_menu.class);
        startActivity(intent);
    }
}
