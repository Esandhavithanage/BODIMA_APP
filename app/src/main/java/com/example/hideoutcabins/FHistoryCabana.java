package com.example.hideoutcabins;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hideoutcabins.pojo.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FHistoryCabana.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FHistoryCabana#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FHistoryCabana extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference();

    private ArrayList<Request> requestlist = new ArrayList<Request>();
    private Request[] requeststoarray=null;
    private viewComentsTraveler.OnFragmentInteractionListener mListener;
    private ListView listView;
    private Button button;
    final Calendar myCalendar = Calendar.getInstance();
    private EditText searchtext;
    SharedPreferences UsersharedPreferences;
    String Cid;



    public FHistoryCabana() {
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



                    if (dataSnapshot1.child("date").getValue().toString().equals(searchtext.getText().toString()) && dataSnapshot1.child("cId").getValue().toString().equals(cid) ){

                        newrequest.settId(dataSnapshot1.child("tId").getValue().toString());
                        newrequest.settName(dataSnapshot1.child("tName").getValue().toString());
                        newrequest.setDate(dataSnapshot1.child("date").getValue().toString());

                        requestlist.add(newrequest);
                    }
                }


                requeststoarray = new Request[requestlist.size()];
                for (int i = 0;i<requestlist.size();i++){

                    requeststoarray[i]=requestlist.get(i);

                    Log.e("comment",requeststoarray[i].gettName());
                }

                custemAdapter custemAdapter = new custemAdapter();
                listView.setAdapter(custemAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public static FHistoryCabana newInstance(String param1, String param2) {
        FHistoryCabana fragment = new FHistoryCabana();
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

        View view = inflater.inflate(R.layout.fragment_fhistory_cabana, container, false);
        listView = view.findViewById(R.id.requestview_cabana);
        button = view.findViewById(R.id.btnsearch_cabana);
        searchtext = view.findViewById(R.id.txtsdate_cabana);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        searchtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        UsersharedPreferences = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        Cid = UsersharedPreferences.getString("ID",null);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getreqest("CB001");
            }
        });

        return view;
    }


    private void updateLabel() {
        String myFormat = "YYY-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        searchtext.setText(sdf.format(myCalendar.getTime()));
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
            view = getLayoutInflater().inflate(R.layout.historylistviewcabana,null);

            final TextView txtdate,txtcabananame,txtid;

            txtdate   = view.findViewById(R.id.txtrequestdate);
            txtcabananame = view.findViewById(R.id.txtTravelername);
            txtid = view.findViewById(R.id.txtCid);


            txtdate.setText(requestlist.get(i).getDate());
            txtcabananame.setText(requestlist.get(i).gettName());
            txtid.setText(requestlist.get(i).gettId());



            return view;
        }
    }

}
