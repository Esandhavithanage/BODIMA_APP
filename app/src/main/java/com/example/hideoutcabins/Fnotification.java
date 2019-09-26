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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hideoutcabins.pojo.Request;
import com.example.hideoutcabins.pojo.comment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Fnotification extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference();
    private ArrayList<com.example.hideoutcabins.pojo.Request> requestlist = new ArrayList<com.example.hideoutcabins.pojo.Request>();
    private Request[] requeststoarray=null;
    private viewComentsTraveler.OnFragmentInteractionListener mListener;
    private ListView listView;
    SharedPreferences UsersharedPreferences;
    String Cid;

    public Fnotification() {
        // Required empty public constructor
    }


    public void getreqest(final String cid) {
        Log.e("comment",cid);

        reference.child("reqest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("comment",cid);
                Request newrequest;
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {

                    newrequest = new Request();

                    Log.e("comment",dataSnapshot1.toString());
                   // Log.e("comment",dataSnapshot1.child("date").getValue().toString());

                    if (dataSnapshot1.child("cId").getValue().toString().equals(cid) && dataSnapshot1.child("status").getValue().toString().equals("PendingP") ){
                        newrequest.settId(dataSnapshot1.getKey());
                        newrequest.settName(dataSnapshot1.child("tName").getValue().toString());
                        newrequest.settNumber(dataSnapshot1.child("tNumber").getValue().toString());
                       // Log.e("comment",newrequest.gettName());
                        requestlist.add(newrequest);
                    }
                }

                Log.e("comment4",""+requestlist.size());
                requeststoarray = new Request[requestlist.size()];
                for (int i = 0;i<requestlist.size();i++){
                    requeststoarray[i]=requestlist.get(i);

                   // Log.e("comment",requeststoarray[i].gettName());
                }
                custemAdapter custemAdapter = new custemAdapter();
                listView.setAdapter(custemAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // TODO: Rename and change types and number of parameters
    public static Fnotification newInstance(String param1, String param2) {
        Fnotification fragment = new Fnotification();
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
        View view =inflater.inflate(R.layout.fragment_fnotification, container, false);
        listView = view.findViewById(R.id.requestlist);
        UsersharedPreferences = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        Cid = UsersharedPreferences.getString("ID",null);
        getreqest(Cid);
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

    class custemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return requeststoarray.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.requestcomfermlayout,null);

            final TextView txtnumber,txtnmae,txtid;
            Button btnconferm,btnreject;

            txtnmae   = view.findViewById(R.id.txtnmae);
            txtnumber = view.findViewById(R.id.txtnumber);
            txtid = view.findViewById(R.id.txtid);
            btnconferm =view.findViewById(R.id.btnconferm);
            btnreject = view.findViewById(R.id.btnreject);

            final DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("reqest");

            txtnumber.setText(requestlist.get(i).gettNumber());
            txtnmae.setText(requestlist.get(i).gettName());
            txtid.setText(requestlist.get(i).gettId());



            btnconferm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbref.child(txtid.getText().toString()).child("status").setValue("confirm");
                }
            });


            btnreject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbref.child(txtid.getText().toString()).child("status").setValue("Reject");
                }
            });

            return view;
        }
    }
}
