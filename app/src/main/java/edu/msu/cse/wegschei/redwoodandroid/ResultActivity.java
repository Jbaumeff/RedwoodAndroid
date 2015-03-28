package edu.msu.cse.wegschei.redwoodandroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends ActionBarActivity {

    final static String WALK_DURATION = "walk_duration";
    final static String BUS_DURATION = "bus_duration";
    TextView textWalkDuration = null;
    TextView textBusDuration = null;
    int walkDuration;
    int busDuration;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        walkDuration = intent.getIntExtra(WALK_DURATION, -1);
        busDuration = intent.getIntExtra(BUS_DURATION, -1);

        textWalkDuration = (TextView)this.findViewById(R.id.textWalkDuration);
        textBusDuration = (TextView)this.findViewById(R.id.textBusDuration);
    }

    private void updateUI() {
        textWalkDuration.setText(Integer.toString(walkDuration));
        textBusDuration.setText(Integer.toString(busDuration));
    }
}
