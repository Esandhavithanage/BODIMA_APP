package com.example.hideoutcabins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class hotelRegistration extends AppCompatActivity {
    Button registr,btnRegister;
    EditText hotelRegistation,address,email,telNumber,password,confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_registration);

        hotelRegistation = (EditText)findViewById(R.id.txtname);
        address = (EditText)findViewById(R.id.txtaddres);
        email = (EditText)findViewById(R.id.txtemail);
        telNumber = (EditText)findViewById(R.id.txtTP);
        password = (EditText)findViewById(R.id.txtpassword);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  Address = address.getText().toString() ;
                String  Email = email.getText().toString();
                String  Name = hotelRegistation.getText().toString();
                String  TP = telNumber.getText().toString();
                String  pasword = password.getText().toString();

                Intent intent = new Intent(hotelRegistration.this,Registration_Rooms.class);
                intent.putExtra("hotelNae",hotelRegistation.getText().toString());
                intent.putExtra("adress",address.getText().toString());
                intent.putExtra("eMail",email.getText().toString());
                intent.putExtra("tElNumber",telNumber.getText().toString());
                intent.putExtra("paSsword",password.getText().toString());
                startActivity(intent);
            }
        });
    }

}
