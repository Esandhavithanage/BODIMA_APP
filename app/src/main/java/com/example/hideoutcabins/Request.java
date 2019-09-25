package com.example.hideoutcabins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Request extends AppCompatActivity {

    TextView txtcname;
    Button btncancel,btnback;
    String id;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

       final Intent intent = getIntent();
         id = intent.getStringExtra("RID");
        String cname = intent.getStringExtra("RCname");

        txtcname = findViewById(R.id.txtCname);
        btncancel = findViewById(R.id.cnacelbtn);
        btnback = findViewById(R.id.btnback);

        txtcname.setText(cname);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("reqest").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try{

                            reference  = FirebaseDatabase.getInstance().getReference().child("reqest").child(id);
                            reference.removeValue();

                        }
                        catch (NumberFormatException e){

                        }
                        catch (Exception e){

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent1 = new Intent(getBaseContext(),Traveller_nav.class);
                //startActivity(intent1);

            }
        });

    }
}
