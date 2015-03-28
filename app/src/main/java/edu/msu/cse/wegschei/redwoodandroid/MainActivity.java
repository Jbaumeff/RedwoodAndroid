package edu.msu.cse.wegschei.redwoodandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;

public class MainActivity extends ActionBarActivity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener {

    private final static String PARAMS = "params";
    private final static int START_ZOOM = 16;

    private MapFragment mapFragment;
    private Parameters params = new Parameters();
    private boolean loaded = false;

    private static class Parameters implements Serializable {
        float lat = Float.NaN;
        float lon = Float.NaN;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        if(bundle != null) {
            params = (Parameters) bundle.getSerializable(PARAMS);
            loaded = true;
        }

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.getMap().setOnMapClickListener(this);
        mapFragment.getMap().setOnMapLongClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_forward:
                goToResult();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng start;
        map.setMyLocationEnabled(true);

        if(!loaded) {
            start = new LatLng(42.7256411f, -84.4799548f);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(start, START_ZOOM));
        } else {
            if(!(Float.isNaN(params.lat) || Float.isNaN(params.lon))) {
                start = new LatLng(params.lat, params.lon);
                addOurMarker(map, start);
            }
        }
    }

    @Override
    public void onMapClick(LatLng point) {
        mapFragment.getMap().clear();
        params.lat = Float.NaN;
        params.lon = Float.NaN;
    }

    @Override
    public void onMapLongClick(LatLng point) {
        GoogleMap map = mapFragment.getMap();
        map.clear();
        addOurMarker(map, point);
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putSerializable(PARAMS, params);
    }

    private void goToResult() {
        if(Float.isNaN(params.lat) || Float.isNaN(params.lon)) {
            Toast.makeText(this, R.string.no_marker, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra(ResultActivity.LATITUDE, params.lat);
            intent.putExtra(ResultActivity.LONGITUDE, params.lon);
            startActivity(intent);
        }
    }

    private void addOurMarker(GoogleMap map, LatLng point) {
        params.lat = (float) point.latitude;
        params.lon = (float) point.longitude;

        map.addMarker(new MarkerOptions()
                .position(point)
                .draggable(true)
                .title("Destination"));
    }
}