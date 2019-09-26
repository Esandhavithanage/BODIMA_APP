package com.example.hideoutcabins;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hideoutcabins.pojo.Cabin;
import com.example.hideoutcabins.pojo.Request;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Traveller_map.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Traveller_map#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Traveller_map extends Fragment implements OnMapReadyCallback {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LinearLayout gallery ;
    LayoutInflater inflater;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView txtnmae,txtdprice,txtsprice,txtaddres,txtTp;
    Button reg;

    private  GoogleMap TgoogleMap;
    private ArrayList<Marker> locationaOfCabans;

    private OnFragmentInteractionListener mListener;
    private FusedLocationProviderClient fusedLocationClient;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference reference = firebaseDatabase.getReference();

    private HashMap<String,Cabin> cabinlist = new HashMap<String, Cabin>();

    private boolean camrastatus = true;
    private String[] beds = { "Bed type"};

    public Traveller_map() {
    }


    public static Traveller_map newInstance(String param1, String param2) {
        Traveller_map fragment = new Traveller_map();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_traveller_map, container, false);
        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

        txtnmae = (TextView) view.findViewById(R.id.txtNmae);
        txtaddres = (TextView) view.findViewById(R.id.txtaddress);
        txtdprice = (TextView) view.findViewById(R.id.txtDprice);
        txtsprice = (TextView) view.findViewById(R.id.txtSprice);
        txtTp = (TextView) view.findViewById(R.id.txttp);
        reg = (Button) view.findViewById(R.id.btnrequst);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbref  = FirebaseDatabase.getInstance().getReference().child("reqest");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currentDateandTime = sdf.format(new Date());

                Request request1 = new Request();
                Toast.makeText(getContext(),"setOnClickListener",Toast.LENGTH_LONG).show();
                request1.settId("T13344");
                request1.settName("esandha");
                request1.setcId("CB001");
                request1.setcName("Kandy");
                request1.setCheckout("Fulse");
                request1.setStatus("PendingP");
                request1.settNumber("1213854");
                request1.setDate(currentDateandTime);
                Toast.makeText(getContext(),request1.getcName(),Toast.LENGTH_LONG).show();

                dbref.push().setValue(request1);
            }
        });

         gallery = view.findViewById(R.id.gallery);

        setCbamaMarkers();



         return view;
    }

    public void fillimage(String cabinid){

        reference.child("upload").child(cabinid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    showimages(dataSnapshot);
                }else {
                    Toast.makeText(getContext(),"No images todispaly",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showimages(DataSnapshot dataSnapshot){
        inflater = LayoutInflater.from(getContext());
        if (gallery != null) {
            gallery.removeAllViews();
        }
        for (int i=1;i<=5;i++){
            View view1 = inflater.inflate(R.layout.imageitem,gallery,false);
            ImageView imageView = view1.findViewById(R.id.galleryimageView);

            Picasso.get().load(dataSnapshot.child("img"+i).getValue().toString()).into(imageView);
            gallery.addView(view1);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        TgoogleMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        TgoogleMap.setMyLocationEnabled(true);
        TgoogleMap.setTrafficEnabled(true);
        TgoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        TgoogleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (camrastatus){
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    CameraPosition zoom = CameraPosition.builder().target(latLng).zoom(15).bearing(0).tilt(45).build();
                    TgoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(zoom));
                    camrastatus = false;
                }


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void removeMkers()
    {
        for (Marker marker : locationaOfCabans)
        {
            marker.remove();
        }
    }

    public  void  setCbamaMarkers(){

        if (locationaOfCabans!=null){
            removeMkers();
        }

        reference.child("cabana").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Cabin> cabanas = new ArrayList<Cabin>();
                locationaOfCabans = new ArrayList<Marker>();

                int cunt =0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Cabin cabanaobj = new Cabin();
                    String Cid = dataSnapshot1.getKey();
                    cabanaobj.setAddress(dataSnapshot1.child("address").getValue().toString());
                    cabanaobj.setEmail(dataSnapshot1.child("email").getValue().toString());
                    cabanaobj.setName(dataSnapshot1.child("name").getValue().toString());
                    cabanaobj.setTp(dataSnapshot1.child("tp").getValue().toString());
                    cabanaobj.setPasword(dataSnapshot1.child("pasword").getValue().toString());
                    cabanaobj.setRoom_Double_Price(Double.valueOf(dataSnapshot1.child("room_Double_Price").getValue().toString()));
                    cabanaobj.setRoom_Single_Price(Double.valueOf(dataSnapshot1.child("room_Single_Price").getValue().toString()));
                    cabanaobj.setLocation_lat(Double.valueOf(dataSnapshot1.child("location_lat").getValue().toString()));
                    cabanaobj.setLocation_lon(Double.valueOf(dataSnapshot1.child("location_lon").getValue().toString()));

                    cabinlist.put(Cid,cabanaobj);

                    LatLng latLng = new LatLng(cabanaobj.getLocation_lat(),cabanaobj.getLocation_lon());
                    MarkerOptions options = new MarkerOptions().position(latLng).title(cabanaobj.getName()).snippet(Cid).icon(BitmapDescriptorFactory.fromResource(R.drawable.bighouse));
                    locationaOfCabans.add(TgoogleMap.addMarker(options));



                }
                TgoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                     //   Toast.makeText(getContext(),marker.getSnippet(),Toast.LENGTH_LONG).show();
                       // fillimage(marker.getSnippet());
                        Cabin cabin = cabinlist.get(marker.getSnippet());

                        txtnmae.setText(cabin.getName());
                        txtaddres.setText(cabin.getAddress());
                        txtdprice.setText(String.valueOf(cabin.getRoom_Double_Price()));
                        txtsprice.setText(String.valueOf(cabin.getRoom_Single_Price()));
                        txtTp.setText(cabin.getTp());

                        return false;
                    }

                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
