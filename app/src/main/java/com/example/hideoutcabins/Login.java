package com.example.hideoutcabins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hideoutcabins.service.DBNotification;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
Button login,register;
EditText uname,pass;




    Switch aSwitch;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public  static final String USERPREFERENSES = "UserDetails";

    private static Pattern PASSWORD_PATTERN= Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = findViewById(R.id.btnlogin);
        register = findViewById(R.id.btnregister);
        uname = findViewById(R.id.edituname);
        pass = findViewById(R.id.editpassowrd);
        aSwitch = findViewById(R.id.loginswitch);

        sharedPreferences = getSharedPreferences(USERPREFERENSES, Context.MODE_PRIVATE);
         editor  = sharedPreferences.edit();

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("LOGIN",""+aSwitch.isChecked());
                if (aSwitch.isChecked()){
                    aSwitch.setText("Cabana");
                }else {
                    aSwitch.setText("Traveler");
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateEmail() && validatePassword()){
                    if (aSwitch.isChecked()){
                        Log.e("LOGIN",""+aSwitch.isChecked());
                        loginCabane();

                    }else {
                        Log.e("LOGIN",""+aSwitch.isChecked());
                        loginTraveler();
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Register_menu.class);
                startActivity(intent);
            }
        });

    }

    public  void loginCabane(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("cabana").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean statys = false;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    Log.e("loginCabane",dataSnapshot1.toString());
                    if (dataSnapshot1.child("email").getValue().toString().equals(uname.getText().toString()) && dataSnapshot1.child("pasword").getValue().toString().equals(pass.getText().toString())){
                        Log.e("loginCabane",dataSnapshot1.child("email").toString());
                        editor.putString("ID",dataSnapshot1.getKey());
                        editor.putString("name",dataSnapshot1.child("name").getValue().toString());
                        editor.putString("Type","cabana");
                        editor.putString("LoginStatus","true");
                        editor.commit();
                        statys = true;
                        break;
                    }


                }



                if(statys){
                    Log.e("LOGIN","OK");
                    Intent intent = new Intent(Login.this,Cabin_nav.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void  loginTraveler(){

        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference("traveler");
        final String un = uname.getText().toString().trim();
        final String pwd = pass.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(Login.this);
        progressDialog.setTitle("User Login");
        progressDialog.setMessage("User Login Happening... Wait!");
        progressDialog.setCancelable(false);
        progressDialog.show();


        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Log.e("loginTraveler",dataSnapshot1.toString());
                    Log.e("loginTraveler",dataSnapshot1.getKey());
                    Log.e("loginTraveler",dataSnapshot1.child("email").getValue().toString());
                    if(dataSnapshot1.child("email").getValue().toString().equals(un) && dataSnapshot1.child("password").getValue().toString().equals(pwd)){
                        Log.e("loginTraveler",dataSnapshot1.child("password").getValue().toString());
                        editor.putString("ID",dataSnapshot1.getKey());
                        editor.putString("Name",dataSnapshot1.child("fname").getValue().toString());
                        editor.putString("TP",dataSnapshot1.child("pnum").getValue().toString());
                        editor.putString("Type","Traveler");
                        editor.putString("LoginStatus","true");
                        System.out.println(editor.commit()+" sharedPreferences");
                        progressDialog.dismiss();
                        startService(new Intent(Login.this,DBNotification.class));
                        startActivity(new Intent(Login.this,Traveller_nav.class));

                        break;
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    //validate email
    public boolean validateEmail(){
        String Email=uname.getText().toString().trim();
        if(Email.isEmpty()){
            uname.setError("Email should not be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            uname.setError("Please enter valid email");
            return false;
        }
        else{
            uname.setError(null);
            return true;
        }
    }

    //validate password

    public boolean validatePassword() {
        String Password = pass.getText().toString().trim();

        if (Password.isEmpty()) {
            pass.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(Password).matches()) {
            pass.setError("Password too weak");
            return false;
        } else {
            pass.setError(null);
            return true;
        }
    }

}
