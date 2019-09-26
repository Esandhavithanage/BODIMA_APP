package com.example.hideoutcabins;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class addPhotos extends AppCompatActivity {

    Button register;
    ImageView img1,img2,img3,img4,img5;
    private static final int IMAGE_PICK_CORD = 1000;
    private static final int PERMISSION_CORD = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photos);
        register = findViewById(R.id.btnFinish);

        img1 =findViewById(R.id.imgview1);
        img2 =findViewById(R.id.imgview2);
        img3 =findViewById(R.id.imgview3);
        img4 =findViewById(R.id.imgview4);
        img5 =findViewById(R.id.imgview5);


        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permission,PERMISSION_CORD);
                    }else {
                        pickImageFromGallery();
                    }

                }
            }
        });


    }

    public void pickImageFromGallery(){

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CORD);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CORD:{
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_DENIED){
                    pickImageFromGallery();

                }else {
                    Toast.makeText(this,"permition denide....!",Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_OK && requestCode == IMAGE_PICK_CORD){
            Log.e("photo",data.getData().toString());
            img1.setImageURI(data.getData());
        }
    }

    public void onclick(View view){
        Intent intent = new Intent(addPhotos.this,viewDetails.class);
        startActivity(intent);
    }
}
