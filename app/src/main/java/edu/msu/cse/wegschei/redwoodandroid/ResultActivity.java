package edu.msu.cse.wegschei.redwoodandroid;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class ResultActivity extends ActionBarActivity {

    final static String WALK_DURATION = "walk_duration";
    final static String BUS_DURATION = "bus_duration";

    final static String RED = "#FF8C8E";
    final static String GREEN = "#77D9AF";

    int walkDuration;
    int busDuration;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        walkDuration = intent.getIntExtra(WALK_DURATION, -1);
        busDuration = intent.getIntExtra(BUS_DURATION, -1);

        updateUI();
    }

    private void updateUI() {
        TextView textWalkDuration = (TextView)this.findViewById(R.id.textWalkDuration);
        TextView textBusDuration = (TextView)this.findViewById(R.id.textBusDuration);
        TextView textWalkArrival = (TextView)this.findViewById(R.id.textWalkArrival);
        TextView textBusArrival = (TextView)this.findViewById(R.id.textBusArrival);

        Calendar calWalk = Calendar.getInstance();
        calWalk.add(Calendar.SECOND, walkDuration);

        Calendar calBus = Calendar.getInstance();
        calBus.add(Calendar.SECOND, busDuration);

        DateFormat shortFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

        textWalkDuration.setText(Integer.toString(walkDuration / 60) + " min, " + Integer.toString(walkDuration % 60) + " secs");
        textBusDuration.setText(Integer.toString(busDuration / 60) + " min, " + Integer.toString(busDuration % 60) + " secs");
        textWalkArrival.setText(shortFormat.format(calWalk.getTime()));
        textBusArrival.setText(shortFormat.format(calBus.getTime()));

        int walkColor;
        int busColor;
        if(walkDuration <= busDuration) {
            walkColor = Color.parseColor(GREEN);
            busColor = Color.parseColor(RED);
        } else {
            walkColor = Color.parseColor(RED);
            busColor = Color.parseColor(GREEN);
        }

        (this.findViewById(R.id.walkLabel)).setBackgroundColor(walkColor);
        (this.findViewById(R.id.walkDuration)).setBackgroundColor(walkColor);
        (this.findViewById(R.id.walkArrival)).setBackgroundColor(walkColor);

        (this.findViewById(R.id.busLabel)).setBackgroundColor(busColor);
        (this.findViewById(R.id.busDuration)).setBackgroundColor(busColor);
        (this.findViewById(R.id.busArrival)).setBackgroundColor(busColor);
    }
}
