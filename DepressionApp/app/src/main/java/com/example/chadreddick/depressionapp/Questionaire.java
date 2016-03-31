package com.example.chadreddick.depressionapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.sql.Connection;

/**
 * Questionaire
 * Shows the user Questions needed to be answered for input data
 */

public class Questionaire extends ActionBarActivity implements View.OnClickListener
{
    int progressValue;
    public static String progressData = "dataSave";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);

        SeekBar feelingBar = (SeekBar) (findViewById(R.id.seekBar));
        final TextView tvFeelingValue = (TextView) (findViewById(R.id.tvSeekBarValue));

        Button btnSubmit = (Button) (findViewById(R.id.btnSubmit));
        Button btnClearData = (Button)(findViewById(R.id.btnClearSave));

        feelingBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                tvFeelingValue.setText(String.valueOf(progress + 1));
                progressValue = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        btnSubmit.setOnClickListener(this);
        btnClearData.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.btnSubmit:
                SharedPreferences sharedPreferences = getSharedPreferences(progressData, MODE_PRIVATE);
                if(sharedPreferences.contains("progress"))
                {
                    //Retrieve the string of feeling data from the SharedPreferences(save data)
                    StringBuilder stringBuilder = new StringBuilder();
                    String tempString = sharedPreferences.getString("progress", "nothing Here");
                    //Add new data to save string data
                    stringBuilder.append(tempString);
                    stringBuilder.append(String.valueOf(progressValue));
                    String finalString = stringBuilder.toString();

                    //Retrieve the Calendar dat data from the SharedPreferences(save data)
                    Set<String> progressSet = new HashSet<String>();
                    progressSet = sharedPreferences.getStringSet("dateProgress", null);
                    if(progressSet != null)
                    {
                        String[] SetCheck = progressSet.toArray(new String[progressSet.size()]);
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        Calendar recCalender = Calendar.getInstance();
                        try
                        {
                            recCalender.setTime(sdf.parse(SetCheck[SetCheck.length - 1]));
                        }
                        catch(java.text.ParseException e)
                        {
                            e.printStackTrace();
                        }
                        recCalender.add(Calendar.DATE, 1);
                        Date date = recCalender.getTime();
                        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                        //add new Calendar date data to String Set
                        progressSet.add(format.format(date));
                    }

                    //Save the new data to SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("progress", finalString);
                    editor.putStringSet("dateProgress", progressSet);
                    editor.commit();
                    System.out.println(finalString);
                }
                else
                {
                    Calendar calender = Calendar.getInstance();
                    Date date = calender.getTime();
                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                    Set<String> DateSet = new HashSet<String>();
                    DateSet.add(format.format(date));


                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("progress", String.valueOf(progressValue));
                    editor.putStringSet("dateProgress", DateSet);
                    editor.commit();
                    System.out.println(String.valueOf(progressValue));
                }
                startActivity(new Intent(this, ProgressGraph.class));
                break;

            case R.id.btnClearSave:
                SharedPreferences SP = getSharedPreferences(progressData, MODE_PRIVATE);
                SharedPreferences.Editor editor = SP.edit();
                editor.clear();
                editor.commit();
                System.exit(0);
                break;
        }
    }
}
