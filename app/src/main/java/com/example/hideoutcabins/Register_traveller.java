package com.example.hideoutcabins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hideoutcabins.pojo.Traveler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register_traveller extends AppCompatActivity {
Button registerTraveler;

    private static final Pattern PASSWORD_PATTERN=Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$");
    private static final Pattern patterntelno = Pattern.compile("\\d{3}-\\d{7}");

    EditText txtfnae, txtlname, txttp, txtemail, txtnic, txtpassword;
    Button regibtn;
    Traveler traveler;
    String id;
    DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("traveler");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_traveller);

        txtfnae = findViewById(R.id.txtfnae);
        txtlname = findViewById(R.id.txtlname);
        txttp = findViewById(R.id.txttp);
        txtemail = findViewById(R.id.txtemail);
        txtnic = findViewById(R.id.txtnic);
        txtpassword = findViewById(R.id.txtpassword);

        regibtn = findViewById(R.id.regibtn);
        traveler = new Traveler();

        Long tsLong = System.currentTimeMillis() / 1000;
        id = tsLong.toString();
        Log.e("Main", "T" + id);

        regibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validatePassword()&&validateEmail()&&ValidatePhoneNumber()){
                    user_register();
                }
            }
        });

    }

    public void user_register() {

        final ProgressDialog progressDialog = new ProgressDialog(Register_traveller.this);
        progressDialog.setTitle("User Registration");
        progressDialog.setMessage("User Registration Happening... Wait!");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Log.e("Esandha","dssdsdsd");

        try {
            traveler.setFname(txtfnae.getText().toString());
            traveler.setLname(txtlname.getText().toString());
            traveler.setPnum(txttp.getText().toString());
            traveler.setEmail(txtemail.getText().toString());
            traveler.setNic(txtnic.getText().toString());
            traveler.setPassword(txtpassword.getText().toString());

            ref.child("T"+id).setValue(traveler).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressDialog.dismiss();
                    startActivity(new Intent(Register_traveller.this,Login.class));
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }





    //validate email
    public boolean validateEmail(){
        String Email=txtemail.getText().toString().trim();
        if(Email.isEmpty()){
            txtemail.setError("Email should not be empty");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            txtemail.setError("Please enter valid email");
            return false;
        }
        else{
            txtemail.setError(null);
            return true;
        }
    }


    //validate password

    public boolean validatePassword() {
        String Password = txtpassword.getText().toString().trim();

        if (Password.isEmpty()) {
            txtpassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(Password).matches()) {
            txtpassword.setError("Password too weak");
            return false;
        } else {
            txtpassword.setError(null);
            return true;
        }
    }

    //validate phone number

    public boolean ValidatePhoneNumber(){

        String sPhoneNumber = txttp.getText().toString().trim();

        if(sPhoneNumber.isEmpty()){
            txttp.setError("tel no feild can't be empty");
            return false;
        }
        else if (!patterntelno.matcher(sPhoneNumber).matches()) {
            txttp.setError("Phone Number must be in the form XXX-XXXXXXX");
            return false;
        }
        else
        {
            txttp.setError(null);
            return true;
        }
    }
}
