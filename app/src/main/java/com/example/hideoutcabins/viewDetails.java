package com.example.hideoutcabins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hideoutcabins.pojo.Traveler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class viewDetails extends AppCompatActivity {

    Button btndelete,btnupdate,btnedit,btnfinish;
    EditText txtname,txtadd,txtemail,txttp,txtsprice,txtdprice;
    SharedPreferences UsersharedPreferences;
    DatabaseReference reference;
    String Cid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        btndelete = findViewById(R.id.btndelete);
        btnupdate = findViewById(R.id.btnupdate);
        btnedit = findViewById(R.id.btnedit);
        btnfinish = findViewById(R.id.btnfinish);

        txtname = findViewById(R.id.txtname);
        txtadd = findViewById(R.id.txtadd);
        txtemail = findViewById(R.id.txtemail);
        txttp = findViewById(R.id.txttp);
        txtsprice = findViewById(R.id.txtsprice);
        txtdprice = findViewById(R.id.txtdprice);

        UsersharedPreferences = this.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        Cid = UsersharedPreferences.getString("ID",null);


        Query query = FirebaseDatabase.getInstance().getReference("cabana").child(Cid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    txtname.setText(dataSnapshot.child("name").getValue().toString());
                    txtadd.setText(dataSnapshot.child("address").getValue().toString());
                    txtemail.setText(dataSnapshot.child("email").getValue().toString());
                    txttp.setText(dataSnapshot.child("tp").getValue().toString());
                    txtsprice.setText(dataSnapshot.child("room_Single_Price").getValue().toString());
                    txtdprice.setText(dataSnapshot.child("room_Double_Price").getValue().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtname.setEnabled(true);
                txtadd.setEnabled(true);
                txtemail.setEnabled(true);
                txttp.setEnabled(true);
                txtsprice.setEnabled(true);
                txtdprice.setEnabled(true);
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletecabana();
            }


        });


        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtname1,txtadd1,txtemail1,txttp1,txtsprice1,txtdprice1;
                txtname1 = txtname.getText().toString();
                txtadd1 = txtadd.getText().toString();
                txtemail1 = txtemail.getText().toString();
                txttp1 = txttp.getText().toString();
                txtsprice1 = txtsprice.getText().toString();
                txtdprice1 = txtdprice.getText().toString();
                udatecabana(txtname1,txtadd1,txtemail1,txttp1,txtsprice1,txtdprice1);

            }
        });


    }

    public void deletecabana() {
        DatabaseReference delRef = FirebaseDatabase.getInstance().getReference();
        delRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reference = FirebaseDatabase.getInstance().getReference().child("cabana").child(Cid);
                reference.removeValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void udatecabana(final String txtname1,final String txtadd1,final String txtemail1,final String txttp1,final String txtsprice1,final String txtdprice1) {

        reference = FirebaseDatabase.getInstance().getReference();


        reference.child("cabana").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(Cid)){
                    try{

                        reference  = FirebaseDatabase.getInstance().getReference().child("cabana").child(Cid);
                        reference.child("address").setValue(txtadd1);
                        reference.child("email").setValue(txtemail1);
                        reference.child("name").setValue(txtname1);
                        reference.child("room_Double_Price").setValue(txtdprice1);
                        reference.child("room_Single_Price").setValue(txtsprice1);
                        reference.child("tp").setValue(txttp1);


                    }
                    catch (NumberFormatException e){

                    }
                    catch (Exception e){

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
