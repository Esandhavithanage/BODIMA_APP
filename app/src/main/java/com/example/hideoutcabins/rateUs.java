package com.example.hideoutcabins;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.hideoutcabins.pojo.Rating;

import java.text.SimpleDateFormat;
import java.util.Date;

public class rateUs extends AppCompatActivity {

    Button rateus,givecomment;

    Intent intent;
    DatabaseReference reference  = FirebaseDatabase.getInstance().getReference().child("rate");
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);
        intent = getIntent();
        ratingBar = findViewById(R.id.ratingBar);
        rateus = findViewById(R.id.btnsubmit);


        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rating rating = new Rating();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currentDateandTime = sdf.format(new Date());

                rating.setcId(intent.getStringExtra("CID"));
                rating.setRating((int) ratingBar.getRating());
                rating.setDate(currentDateandTime);



               reference.push().setValue(rating);

            }
        });




    }
}
