package cares.innostark.com.cares;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import cares.innostark.com.cares.Models.OperationWorkPlaces;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        i=getIntent();
        String callingActivity=i.getStringExtra("callingActivity");
        if(callingActivity.equals("BookingStep1"))
        {
            showMapForStep1();
        }
        if(callingActivity.equals("SelectLocation"))
        {
            showMapForSelectLoc();
        }

    }

    @Override
    public  void onBackPressed()
    {
        this.finish();
    }

    public void showMapForStep1()
    {
        OperationWorkPlaces obj;
        obj= i.getParcelableExtra("loc_info");
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        LatLng loc = new LatLng(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
        if(obj.getLocationName() == null || obj.getLocationName().equals("")) {
            mMap.addMarker(new MarkerOptions().position(loc).snippet(obj.getAddress()+"\n"+obj.getPhone()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }
        else if(obj.getAddress() == null || obj.getAddress().equals("")) {
            mMap.addMarker(new MarkerOptions().position(loc).title(obj.getLocationName()).snippet(obj.getPhone()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }
        else if(obj.getPhone() == null || obj.getPhone().equals("")) {
            mMap.addMarker(new MarkerOptions().position(loc).title(obj.getLocationName()).snippet(obj.getAddress()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }
        else
            mMap.addMarker(new MarkerOptions().position(loc).title(obj.getLocationName()).snippet(obj.getAddress()+"\n"+obj.getPhone()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                Context mContext = getApplicationContext();
                LinearLayout info = new LinearLayout(mContext);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(mContext);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(mContext);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
    }

    public void showMapForSelectLoc()
    {
        ArrayList<OperationWorkPlaces> locs=i.getParcelableArrayListExtra("all_locations_list");
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        for (int k=0;k < locs.size(); k++) {
            OperationWorkPlaces obj = locs.get(k);
            if (obj.getLatitude() != null && obj.getLongitude() != null) {      // if lat/lng are not null then show location on map
                LatLng loc = new LatLng(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude()));
                if (obj.getLocationName() == null || obj.getLocationName().equals("")) {
                    mMap.addMarker(new MarkerOptions().position(loc).snippet(obj.getAddress() + "\n" + obj.getPhone()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                } else if (obj.getAddress() == null || obj.getAddress().equals("")) {
                    mMap.addMarker(new MarkerOptions().position(loc).title(obj.getLocationName()).snippet(obj.getPhone()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                } else if (obj.getPhone() == null || obj.getPhone().equals("")) {
                    mMap.addMarker(new MarkerOptions().position(loc).title(obj.getLocationName()).snippet(obj.getAddress()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                } else
                    mMap.addMarker(new MarkerOptions().position(loc).title(obj.getLocationName()).snippet(obj.getAddress() + "\n" + obj.getPhone()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(5.0f));
                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        Context mContext = getApplicationContext();
                        LinearLayout info = new LinearLayout(mContext);
                        info.setOrientation(LinearLayout.VERTICAL);

                        TextView title = new TextView(mContext);
                        title.setTextColor(Color.BLACK);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.BOLD);
                        title.setText(marker.getTitle());

                        TextView snippet = new TextView(mContext);
                        snippet.setTextColor(Color.GRAY);
                        snippet.setText(marker.getSnippet());

                        info.addView(title);
                        info.addView(snippet);

                        return info;
                    }
                });
            }
        }
    }

}
