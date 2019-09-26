package com.example.hideoutcabins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hideoutcabins.pojo.Cabin;

public class Registration_Rooms extends AppCompatActivity {
    Button register,submitBtn;
    EditText NumOfrooms,SingleRoomPrice,DoubleRoomPrice;
    Cabin cabin;
    String Hname, Aaddress,Emails,TeLphon,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__rooms);
        register = findViewById(R.id.submitBtn);

        register = findViewById(R.id.submitBtn);
        Intent intent = getIntent();
        Hname = intent.getStringExtra("hotelNae") ;
        Aaddress = intent.getStringExtra("adress") ;
        Emails = intent.getStringExtra("eMail") ;
        TeLphon = intent.getStringExtra("tElNumber") ;
        Password = intent.getStringExtra("paSsword") ;

        SingleRoomPrice = (EditText)findViewById(R.id.SinglePrice);
        DoubleRoomPrice =  (EditText)findViewById(R.id.doublePrice);
        submitBtn = (Button)findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // String  NUMOfrooms = NumOfrooms.getText().toString() ;
                String  SinGleRoomPrice = SingleRoomPrice.getText().toString() ;
                String  DOubleRoomPrice = DoubleRoomPrice.getText().toString() ;

                Intent intent = new Intent(Registration_Rooms.this,cabanaLocation.class);
                // intent.putExtra("Numofromms",NumOfrooms.getText().toString());
                intent.putExtra("singleRoomPr",SingleRoomPrice.getText().toString());
                intent.putExtra("DouBleRoomPr",DoubleRoomPrice.getText().toString());
                intent.putExtra("hotelNae",Hname);
                intent.putExtra("adress",Aaddress);
                intent.putExtra("eMmail",Emails);
                intent.putExtra("tElNumber",TeLphon);
                intent.putExtra("paSsword",Password);
                startActivity(intent);
            }
        });
    }

}
