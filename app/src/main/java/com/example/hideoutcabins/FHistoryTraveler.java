package com.example.hideoutcabins;

import android.app.DatePickerDialog;
import android.content.Intent;
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
 * {@link FHistoryTraveler.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FHistoryTraveler#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FHistoryTraveler extends Fragment {
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
    private  EditText searchtext;
    private  TextView todate,fromdate;

  //  private OnFragmentInteractionListener mListener;


    public void getreqest(final String Tid) {
        Log.e("comment",Tid);

        reference.child("reqest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("comment",Tid);
                Request newrequest;
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {

                    newrequest = new Request();

                    Log.e("comment",dataSnapshot1.toString());
                    Log.e("comment",dataSnapshot1.child("date").getValue().toString());

                    if (dataSnapshot1.child("date").getValue().toString().equals(searchtext.getText().toString()) && dataSnapshot1.child("tId").getValue().toString().equals(Tid) ){
                        newrequest.setcId(dataSnapshot1.child("cId").getValue().toString());
                        newrequest.setcName(dataSnapshot1.child("cName").getValue().toString());
                        newrequest.setDate(dataSnapshot1.child("date").getValue().toString());
                        Log.e("comment",newrequest.getcName());
                        requestlist.add(newrequest);
                    }
                }

                Log.e("comment4",""+requestlist.size());
                requeststoarray = new Request[requestlist.size()];
                for (int i = 0;i<requestlist.size();i++){
                    requeststoarray[i]=requestlist.get(i);

                    Log.e("comment",requeststoarray[i].getcName());
                }
                custemAdapter custemAdapter = new custemAdapter();
                listView.setAdapter(custemAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public FHistoryTraveler() {
        // Required empty public constructor
    }


    public static FHistoryTraveler newInstance(String param1, String param2) {
        FHistoryTraveler fragment = new FHistoryTraveler();
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

        View view = inflater.inflate(R.layout.fragment_fhistory, container, false);
        listView = view.findViewById(R.id.requestview);
        button = view.findViewById(R.id.btnsearch);
        searchtext = view.findViewById(R.id.txtsdate);


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




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getreqest("T13344");
            }
        });



        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void updateLabel() {
        String myFormat = "YYY-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        searchtext.setText(sdf.format(myCalendar.getTime()));
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
            view = getLayoutInflater().inflate(R.layout.historylistview,null);

            final TextView txtdate,txttravelername,txtid;
            Button btnrate,btngivecoment;

            txtdate   = view.findViewById(R.id.txtdate);
            txttravelername = view.findViewById(R.id.txtcabananame);
            txtid = view.findViewById(R.id.txtid);
            btnrate = view.findViewById(R.id.btnrate);
            btngivecoment = view.findViewById(R.id.btngivecoment);

            txtdate.setText(requestlist.get(i).getDate());
            txttravelername.setText(requestlist.get(i).getcName());
            txtid.setText(requestlist.get(i).getcId());

            btnrate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),rateUs.class);
                    intent.putExtra("CID",txtid.getText().toString());
                    startActivity(intent);
                }
            });

            btngivecoment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),addComment.class);
                    intent.putExtra("CID",txtid.getText().toString());
                    startActivity(intent);
                }
            });
            return view;
        }
    }
}
