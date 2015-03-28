package edu.msu.cse.wegschei.redwoodandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;


public class CommunicationDlg extends DialogFragment {

    private float sourceLat;
    private float sourceLon;
    private float destLat;
    private float destLon;

    private int walkDuration;
    private int busDuration;

    public void setSourceLat(float sourceLat) {
        this.sourceLat = sourceLat;
    }

    public void setSourceLon(float sourceLon) {
        this.sourceLon = sourceLon;
    }

    public void setDestLat(float destLat) {
        this.destLat = destLat;
    }

    public void setDestLon(float destLon) {
        this.destLon = destLon;
    }

    /**
     * Create the dialog box
     */
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the title
        builder.setTitle(R.string.calculating);

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        // Create the dialog box
        final AlertDialog dlg = builder.create();

        /*
         * Create a thread to load the hatting from the cloud
         */
        new Thread(new Runnable() {

            @Override
            public void run() {
                Cloud cloud = new Cloud();
                InputStream stream = cloud.getDirectionData(sourceLat, sourceLon, destLat, destLon);
                if(stream != null) {
                    if (parseReply(stream)) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getActivity(), ResultActivity.class);
                                intent.putExtra(ResultActivity.WALK_DURATION, walkDuration);
                                intent.putExtra(ResultActivity.BUS_DURATION, busDuration);
                                startActivity(intent);
                            }
                        });
                    }
                }

                dlg.dismiss();
            }
        }).start();

        return dlg;
    }

    private Boolean parseReply(InputStream stream) {
        String reply;
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(stream, writer, "UTF-8");
            reply = writer.toString();
        } catch(IOException e) {
            return false;
        }

        JSONObject json;
        try {
            json = new JSONObject(reply);
        } catch (JSONException e) {
            return false;
        }

        boolean success = false;
        try {
            String status = json.getString("status");
            if(status.equals("OK")) {
                walkDuration = json.getInt("walk_duration");
                busDuration = json.getInt("bus_duration");
                success = true;
            }
        } catch (JSONException e) {
            return false;
        }

        return success;
    }
}
