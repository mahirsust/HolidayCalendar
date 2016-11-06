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

public class MainActivity extends AppCompatActivity {

    private Button btnLogout;
    private Button btnaddholiday;
    private Button btnupdateholiday;
    private Button btnshowcalendar;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        btnaddholiday = (Button) findViewById(R.id.btnaddholidaytables);
        btnaddholiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addholiday();
            }
        });

       /* btnupdateholiday = (Button) findViewById(R.id.btnupdateholidaytables);
        btnupdateholiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateholiday();
            }
        });*/

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
        finish();
    }
    private void updateholiday()
    {
        Intent intent = new Intent(MainActivity.this, UpdateholidaytablesActivity.class);
        startActivity(intent);
        finish();
    }
    private void showcalendar()
    {
        Intent intent = new Intent(MainActivity.this, ShowmycalendarActivity.class);
        startActivity(intent);
        finish();
    }
    private void logoutUser() {
        session.setLogin(false);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
/*<TextView
                android:id="@+id/textView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center_horizontal"
                android:layout_marginBottom="20dp"
                android:layout_marginTop="30dp"
                android:text="StartingAndroid.com"
                android:textColor="#2c3e50"
                android:textSize="30sp"
                android:textStyle="bold" />
                */