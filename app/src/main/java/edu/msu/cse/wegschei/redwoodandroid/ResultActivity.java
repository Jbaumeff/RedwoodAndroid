package edu.msu.cse.wegschei.redwoodandroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class ResultActivity extends ActionBarActivity {

    final static String LATITUDE = "latitude";
    final static String LONGITUDE = "longitude";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }
}
