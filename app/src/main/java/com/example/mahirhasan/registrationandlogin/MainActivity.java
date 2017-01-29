package com.example.mahirhasan.registrationandlogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnLogout;
    private Button btnaddholiday;
    private Button btnupdateholiday;
    private Button btnshowcalendar;
    private Session session;
    private TextView welcome;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        email = LoginActivity.getemail;

        btnaddholiday = (Button) findViewById(R.id.btnaddholidaytables);
        btnaddholiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addholiday();
            }
        });


        btnshowcalendar = (Button) findViewById(R.id.btnshowmycalendar);
        btnshowcalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showcalendar();
            }
        });

        btnLogout = (Button) findViewById(R.id.btnLogout);
        session = new Session(MainActivity.this);

        if (!session.getLoggedIn()) {
            logoutUser();
        }
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    private void addholiday()
    {
        Intent intent = new Intent(MainActivity.this, AddholidaytablesActivity.class);
        startActivity(intent);
        //finish();
    }
    private void updateholiday()
    {
        Intent intent = new Intent(MainActivity.this, UpdateholidaytablesActivity.class);
        startActivity(intent);
        //finish();
    }
    private void showcalendar()
    {
        Intent intent = new Intent(MainActivity.this, ShowmycalendarActivity.class);
        startActivity(intent);
        //finish();
    }
    private void logoutUser() {
        session.setLogin(false);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        //finish();
    }
}
