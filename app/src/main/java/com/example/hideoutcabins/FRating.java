package com.example.hideoutcabins;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hideoutcabins.pojo.comment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FRating.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FRating#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FRating extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference();

    private ArrayList<comment> commentlist = new ArrayList<comment>();
    private viewComentsTraveler.OnFragmentInteractionListener mListener;
    RatingBar ratingBar2;
    TextView textView;
    int totalrating=0;

    SharedPreferences UsersharedPreferences;
    String Cid;



    public FRating() {
        // Required empty public constructor
    }


    public void getrate(final String cid) {
        Log.e("comment",cid);

        reference.child("rate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("comment",cid);
                int totalrate=0,coun=0;

                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {


                    if (dataSnapshot1.child("cId").getValue().toString().equals(cid) ){
                        totalrate = totalrate + Integer.parseInt(dataSnapshot1.child("rating").getValue().toString());
                        coun++;
                    }
                }

                try {
                    totalrating = totalrate/coun;

                    ratingBar2.setRating(totalrating);
                    textView.setText(String.valueOf(totalrating));
                }
                catch (ArithmeticException e){
                    Toast.makeText(getContext(),"Travelers Didn't rated ",Toast.LENGTH_LONG).show();
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public static FRating newInstance(String param1, String param2) {
        FRating fragment = new FRating();
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
        View view = inflater.inflate(R.layout.fragment_frating, container, false);

        ratingBar2 = view.findViewById(R.id.ratingBar2);
        textView = view.findViewById(R.id.txtrating);
        UsersharedPreferences = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        Cid = UsersharedPreferences.getString("ID",null);
        getrate(Cid);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
