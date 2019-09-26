package com.example.hideoutcabins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.hideoutcabins.pojo.comment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class addComment extends AppCompatActivity {

    EditText txtcomment;
    Button btnSend;
    ImageView backbutn;
    DatabaseReference dbref;
    comment comment1;

    SharedPreferences UsersharedPreferences;
    String Tid,Tname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        Intent intent = getIntent();
       final String id = intent.getStringExtra("CID");
       final  String cname = intent.getStringExtra("Cname");

        txtcomment = findViewById(R.id.txtcomment);
        btnSend = findViewById(R.id.btncomment);
        backbutn = findViewById(R.id.imgcomment);
        comment1 = new comment();

        UsersharedPreferences = this.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        Tid = UsersharedPreferences.getString("ID",null);
        Tname = UsersharedPreferences.getString("Name",null);


        dbref = FirebaseDatabase.getInstance().getReference().child("comment");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = txtcomment.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currentDateandTime = sdf.format(new Date());

                comment1.setComment(comment);
                comment1.setCid(id);
                comment1.setTid(Tid);
                comment1.setcName(cname);
                comment1.settName(Tname);
                comment1.setDate(currentDateandTime);

                dbref.push().setValue(comment1);

            }
        });

        backbutn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
