package com.example.hideoutcabins;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.hideoutcabins.pojo.comment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addComment extends AppCompatActivity {

    EditText txtcomment;
    Button btnSend;
    ImageView backbutn;
    DatabaseReference dbref;
    comment comment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        txtcomment = findViewById(R.id.txtcomment);
        btnSend = findViewById(R.id.btncomment);
        backbutn = findViewById(R.id.imgcomment);
        comment1 = new comment();

        dbref = FirebaseDatabase.getInstance().getReference().child("comment");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = txtcomment.getText().toString();

                comment1.setComment(comment);
                comment1.setCid("CB001");
                comment1.setTid("T001");
                comment1.setcName("kandy");
                comment1.settName("esandha");
                comment1.setDate("2019-08-12");

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
