package com.example.hideoutcabins;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hideoutcabins.pojo.Traveler;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Traveller_Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

      SharedPreferences UsersharedPreferences;
    String Tid;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button tpbtn1,tpbtn2;
    EditText tp6,tp5,tp4,tp3,tp2,tp1;
    Traveler traveler;

    private OnFragmentInteractionListener mListener;

    public Traveller_Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Traveller_Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Traveller_Profile newInstance(String param1, String param2) {
        Traveller_Profile fragment = new Traveller_Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_traveller__profile, container, false);
        UsersharedPreferences = this.getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        Tid = UsersharedPreferences.getString("ID",null);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);

        tp6 = view.findViewById(R.id.tp6);
        tp5 = view.findViewById(R.id.tp5);
        tp4 = view.findViewById(R.id.tp4);
        tp3 = view.findViewById(R.id.tp3);
        tp2 = view.findViewById(R.id.tp2);
        tp1 = view.findViewById(R.id.tp1);

        tpbtn1 = view.findViewById(R.id.tpbtn1);
        tpbtn2 = view.findViewById(R.id.tpbtn2);


        Query query = FirebaseDatabase.getInstance().getReference("traveler").child(Tid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    Traveler traveler = dataSnapshot.getValue(Traveler.class);
                    tp6.setText(traveler.getFname());
                    tp5.setText(traveler.getLname());
                    tp4.setText(traveler.getEmail());
                    tp3.setText(traveler.getNic());
                    tp2.setText(traveler.getPnum());
                    tp1.setText(traveler.getPassword());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tpbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Traveler traveler = new Traveler();
                traveler.setFname(tp6.getText().toString());
                traveler.setLname(tp5.getText().toString());
                traveler.setEmail(tp4.getText().toString());
                traveler.setNic(tp3.getText().toString());
                traveler.setPnum(tp2.getText().toString());
                traveler.setPassword(tp1.getText().toString());

                DatabaseReference up = FirebaseDatabase.getInstance().getReference("traveler");

                up.child(Tid).setValue(traveler).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(getContext(),"Updated Sucessfully!",Toast.LENGTH_SHORT).show();


                    }
                });

            }
        });

        tpbtn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                DatabaseReference delete = FirebaseDatabase.getInstance().getReference("traveler");
                progressDialog.setTitle("User Delete");
                progressDialog.setMessage("User Delete Process Happening... Wait!");
                progressDialog.show();
                delete.child(Tid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();

                        Toast.makeText(getContext(),"User Delete Sucessfully!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(),Login.class));
                    }
                });

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
