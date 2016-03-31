package com.example.chadreddick.depressionapp;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;

/**
 * MainActivity
 * First window that shows up in the app
 */

public class MainActivity extends ActionBarActivity implements View.OnClickListener
{
    public static String progressData = "dataSave";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvWB = (TextView)(findViewById(R.id.tvWB));
        EditText etName = (EditText)(findViewById(R.id.etName));

        SharedPreferences sharedPreferences = getSharedPreferences(progressData, MODE_PRIVATE);
        if(sharedPreferences.contains("nameStart"))
        {
            etName.setVisibility(View.INVISIBLE);
            String nameTXT = sharedPreferences.getString("nameStart", "nothing Here");
            tvWB.setText("Welcome Back " + nameTXT);
        }
        else
        {
            tvWB.setText("Enter Your Name");
            etName.setVisibility(View.VISIBLE);
        }
        Button btnBegin = (Button)(findViewById(R.id.btnBegin));
        btnBegin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnBegin:
                SharedPreferences sharedPreferences = getSharedPreferences(progressData, MODE_PRIVATE);
                EditText etName = (EditText)(findViewById(R.id.etName));
                if(sharedPreferences.contains("nameStart"))
                {
                    startActivity(new Intent(this, Questionaire.class));
                }
                else
                {
                    if (etName.getText() != null)
                    {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("nameStart", etName.getText().toString());
                        editor.commit();
                        startActivity(new Intent(this, Questionaire.class));
                    }
                }
                break;
        }
    }
}
