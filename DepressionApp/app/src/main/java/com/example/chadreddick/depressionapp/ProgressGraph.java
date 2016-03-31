package com.example.chadreddick.depressionapp;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ProgressGraph
 * Shows the Graph data overtime from use of this app
 */

public class ProgressGraph extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_graph);

        //Make a temp integer array to store previous and current feeling values to display
        ArrayList<Integer> progressValues = new ArrayList<Integer>();

        //retrieve the saved app data from previous uses
        SharedPreferences sharedPreferences = getSharedPreferences(Questionaire.progressData, 0);
        String dataRetrieve = sharedPreferences.getString("progress", "not found");

        //get the String of previously saved dates from a HashSet
        Set<String> dates = new HashSet<String>();
        dates = sharedPreferences.getStringSet("dateProgress", null);

        //put dates into a local String array
        String datesArray[] = dates.toArray(new String[dates.size()]);

        //use a string builder to retrieve the individual numbers(converting from string to Integer)
        StringBuilder tempBuilder = new StringBuilder();

        //get each char one by one
        for (int i = 0; i < dataRetrieve.length(); i++) {
            //add char to StringBuilder object(tempBuilder)
            tempBuilder.append(dataRetrieve.charAt(i));
            //Add appended char value on tempBuilder to the ArrayList progressValues
            progressValues.add(Integer.parseInt(tempBuilder.toString()) + 1);
            //output for debugging
            System.out.println(tempBuilder.toString());
            //erase tempBuiler values
            tempBuilder.setLength(0);
        }

        //Make an ArrayList of dates to store our previous dates
        ArrayList<Date> dateAL = new ArrayList<Date>();

        //check for dates in the for loop in the stored String Dates
        for (int i = 0; i < datesArray.length; i++) {
            try {
                //local DateFormat variable to format the string data
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                //Add the previous dates to the Date ArrayList
                dateAL.add(sdf.parse(datesArray[i]));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        //Store the Date DataPoints(Points on the Graph) and make the array the size of the Date ArrayList size
        DataPoint[] DP = new DataPoint[dateAL.size()];

        for (int i = 0; i < DP.length; i++) {
            //Make each DataPoint on each previous date
            DP[i] = new DataPoint(dateAL.get(i), progressValues.get(i));
        }


        //Create a GraphView object to output data
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(DP);


        //Checking the values on the String HashSet
        for (int i = 0; i < datesArray.length; i++) {
            System.out.println(Integer.toString(i) + " " + datesArray[i]);
        }

        //Add the series data we just for looped
        graph.addSeries(series);

        //Create Labels for easier visuals
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setVerticalLabels(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.setTitle("Progress");
        graph.setMinimumWidth(200);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
        graph.getGridLabelRenderer().setTextSize(0.5f);


        //Make the GraphView Zoomable and Scrollable by touch(also set max Y Values from 1 - 10)
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setXAxisBoundsManual(true);
        viewport.setMinX(dateAL.get(0).getTime());
        viewport.setMaxX(dateAL.get(0).getTime() + 1 * 24 * 60 * 60 * 1000);
        viewport.setMinY(1);
        viewport.setMaxY(10);
        viewport.setScrollable(true);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed()
    {

    }

    @Override
    public void onStart()
    {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ProgressGraph Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.chadreddick.depressionapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop()
    {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ProgressGraph Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.chadreddick.depressionapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
