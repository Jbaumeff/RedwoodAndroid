package edu.msu.cse.wegschei.redwoodandroid;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Cloud {
    private final static String REQUEST_URL = "http://webdev.cse.msu.edu/~baumjeff/SpartaNow/server.php";
    private final static String MAGIC = "CB5D56A21FE65143";

    public InputStream getDirectionData(float sourceLat, float sourceLon, float destLat, float destLon) {
        // Create a get query
        String query = REQUEST_URL + "?slat=" + sourceLat + "&slon=" + sourceLon + "&dlat="
                + destLat + "&dlon=" + destLon + "&MAGIC=" + MAGIC;

        try {
            URL url = new URL(query);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStream stream = conn.getInputStream();
            // logStream(stream);
            return stream;

        } catch (MalformedURLException e) {
            // Should never happen
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

    public static void logStream(InputStream stream) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream));

        Log.e("476", "logStream: If you leave this in, code after will not work!");
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                Log.e("476", line);
            }
        } catch (IOException ex) {
            return;
        }
    }
}
