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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hideoutcabins.pojo.comment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link viewComentsTraveler.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link viewComentsTraveler#newInstance} factory method to
 * create an instance of this fragment.
 */
public class viewComentsTraveler extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String[] id={"1","2","3","4","5"};
    private String[] cbana={"ad","sd","we","g","tg"};
    private String[] coment={"effd","sdfd","dfd","tg","hy"};
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference();
   private ArrayList<comment>  commentlist = new ArrayList<comment>();
    private comment[] comrentstoarray=null;
    private  ArrayList<String> commentsids = new ArrayList<String>();
    private OnFragmentInteractionListener mListener;
    private  ListView listView;

    SharedPreferences UsersharedPreferences;
    String Tid;

    public void getcoments(final String Tid) {
        Log.e("comment",Tid);

        reference.child("comment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("comment",Tid);
                comment newcomment;
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {

                    newcomment = new comment();

                    Log.e("comment",dataSnapshot1.getKey());

                    if (dataSnapshot1.child("tid").getValue().toString().equals(Tid) ){
                        commentsids.add(dataSnapshot1.getKey());
                        newcomment.setCid(dataSnapshot1.child("cid").getValue().toString());
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
                custemAdapter custemAdapter = new custemAdapter();
                listView.setAdapter(custemAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public viewComentsTraveler(){
      //  getcoments();
    }

    public static viewComentsTraveler newInstance(String param1, String param2) {
        Log.e("dsfd","newInstance");
        viewComentsTraveler fragment = new viewComentsTraveler();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UsersharedPreferences = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        Tid = UsersharedPreferences.getString("ID",null);
        getcoments(Tid);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_coments, container, false);
        listView = view.findViewById(R.id.comentListview);
        return view;
    }

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

        void onFragmentInteraction(Uri uri);
    }
    class custemAdapter extends BaseAdapter{

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
            view = getLayoutInflater().inflate(R.layout.comentviewtraveler,null);

            final TextView txtdate,txtcbname,txtid;
            final EditText txtcoment;
            Button edit,delete,update;

            txtdate   = view.findViewById(R.id.txtdate);
            txtcbname = view.findViewById(R.id.txttname);
            txtcoment = view.findViewById(R.id.txtcoment);
            txtid = view.findViewById(R.id.txtid);

            edit = view.findViewById(R.id.btnedit);
            delete = view.findViewById(R.id.btndelete);
            update = view.findViewById(R.id.btnupdate);

            txtdate.setText(commentlist.get(i).getDate());
            txtcbname.setText(commentlist.get(i).getCid());
            txtcoment.setText(commentlist.get(i).getComment());
            txtid.setText(commentsids.get(i));

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    txtcoment.setEnabled(true);
                }
            });

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    commentUpdate(txtid.getText().toString(),txtcoment.getText().toString());


                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    commentDelete(txtid.getText().toString());
                }
            });

            return view;
        }
    }


    public void  commentUpdate(final String id, final String upcomment){
        reference.child("comment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(id)){
                    try{

                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String dateToStr = format.format(today);



                        reference  = FirebaseDatabase.getInstance().getReference().child("comment").child(id);
                        reference.child("comment").setValue(upcomment);
                        reference.child("date").setValue(dateToStr);


                    }
                    catch (NumberFormatException e){

                    }
                    catch (Exception e){

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void commentDelete(final String id){

        reference.child("comment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{

                    reference  = FirebaseDatabase.getInstance().getReference().child("comment").child(id);
                    reference.removeValue();

                }
                catch (NumberFormatException e){

                }
                catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
