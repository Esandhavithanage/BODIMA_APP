package com.example.hideoutcabins;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
 * {@link viewComentsCabana.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link viewComentsCabana#newInstance} factory method to
 * create an instance of this fragment.
 */
public class viewComentsCabana extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference();

    private ArrayList<comment> commentlist = new ArrayList<comment>();
    private comment[] comrentstoarray=null;
    private viewComentsTraveler.OnFragmentInteractionListener mListener;
    private  ListView listView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   // private OnFragmentInteractionListener mListener;


    public void getcoments(final String cid) {
        Log.e("comment",cid);

        reference.child("comment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("comment",cid);
                comment newcomment;
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {

                    newcomment = new comment();

                    Log.e("comment",dataSnapshot1.getKey());

                    if (dataSnapshot1.child("cid").getValue().toString().equals(cid) ){

                        newcomment.settName(dataSnapshot1.child("tName").getValue().toString());
                        newcomment.setDate(dataSnapshot1.child("date").getValue().toString());
                        newcomment.setComment(dataSnapshot1.child("comment").getValue().toString());
                        Log.e("comment",newcomment.getComment());
                        commentlist.add(newcomment);
                    }
                }

                Log.e("comment4",""+commentlist.size());
                comrentstoarray = new comment[commentlist.size()];
                for (int i = 0;i<commentlist.size();i++){
                    comrentstoarray[i]=commentlist.get(i);

                    Log.e("comment",comrentstoarray[i].getComment());
                }

                listAdapter listadapter = new listAdapter();
                listView.setAdapter(listadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public viewComentsCabana() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment viewComentsCabana.
     */
    // TODO: Rename and change types and number of parameters
    public static viewComentsCabana newInstance(String param1, String param2) {
        viewComentsCabana fragment = new viewComentsCabana();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getcoments("CB001");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_coments_cabana, container, false);
        listView = view.findViewById(R.id.comentlistcabana);
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


    class listAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return comrentstoarray.length;
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
            view = getLayoutInflater().inflate(R.layout.comentviewcabana,null);

            final TextView txtdate,txtcbname,txtcomment;

            txtdate   = view.findViewById(R.id.txtdate);
            txtcbname = view.findViewById(R.id.txttname);
            txtcomment = view.findViewById(R.id.txtcoment);

            txtdate.setText(commentlist.get(i).getDate());
            txtcbname.setText(commentlist.get(i).gettName());
            txtcomment.setText(commentlist.get(i).getComment());

            return view;
        }
    }
}
