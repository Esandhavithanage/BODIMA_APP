package com.example.hideoutcabins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hideoutcabins.service.DBNotification;

public class Login extends AppCompatActivity {
Button login,register;
EditText uname,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.btnlogin);
        register = findViewById(R.id.btnregister);

        uname = findViewById(R.id.edituname);
        pass = findViewById(R.id.editpassowrd);
    }

    public void loginOnClick(View view){
        String name=uname.getText().toString();
        String password=pass.getText().toString();

        if (name.equals("esa") && password.equals("123")){
            Intent dbservice = new Intent(Login.this, DBNotification.class);
            Intent intent = new Intent(Login.this,Traveller_nav.class);
            startService(dbservice);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(Login.this,Cabin_nav.class);
            startActivity(intent);
        }


    }

    public void registerOnClick(View view){
        Intent intent = new Intent(Login.this,Register_menu.class);
        startActivity(intent);
    }
}
