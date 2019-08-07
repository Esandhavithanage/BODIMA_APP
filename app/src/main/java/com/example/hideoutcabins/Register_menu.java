package com.example.hideoutcabins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Register_menu extends AppCompatActivity {
Button regTraveller,regCabana;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_menu);

        regTraveller = findViewById(R.id.btntravellerReg);
        regCabana = findViewById(R.id.btncabanaReg);
    }

    public void regTravellerOnClick(View view){
        Intent intent = new Intent(Register_menu.this,Register_traveller.class);
        startActivity(intent);
    }

    public void regCabanaOnClick(View view){

    }
}
