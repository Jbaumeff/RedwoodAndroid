package edu.msu.cse.wegschei.redwoodandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends ActionBarActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener {

    MapFragment mapFragment;
    private Parameters params = new Parameters();

    private static class Parameters implements Parcelable {
        public LatLng markerPosition;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            markerPosition.writeToParcel(dest, flags);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.getMap().setOnMapClickListener(this);
        mapFragment.getMap().setOnMapLongClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng start = new LatLng(42.7256411f, -84.4799548f);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 16));
    }

    @Override
    public void onMapClick(LatLng point) {
        mapFragment.getMap().clear();
    }

    @Override
    public void onMapLongClick(LatLng point) {
        GoogleMap map = mapFragment.getMap();
        map.clear();
        map.addMarker(new MarkerOptions()
                .position(point)
                .draggable(true)
                .title("Destination"));
    }

    private void goToResult(LatLng point) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.LATITUDE, point.latitude);
        intent.putExtra(ResultActivity.LONGITUDE, point.longitude);
        startActivity(intent);
    }
}
