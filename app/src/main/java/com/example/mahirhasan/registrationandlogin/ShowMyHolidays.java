package com.example.mahirhasan.registrationandlogin;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

public class ShowMyHolidays extends Activity{

    private TableLayout tableLayout;
    private Context context;
    MyDBHandler dbHandler;
    private Button addbtn;
    private Button deletebtn;
    private Button backbtn;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_holidays);
        context = this;
        dbHandler = new MyDBHandler(this, null, null, 1);
        tableLayout = (TableLayout) findViewById(R.id.tablelayout);
        addbtn = (Button) findViewById(R.id.addBtn);
        deletebtn = (Button) findViewById(R.id.deleteBtn);
        backbtn = (Button) findViewById(R.id.backBtn);
        email = LoginActivity.getemail;
        System.out.println(email);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowMyHolidays.this, AddholidaytablesActivity.class);
                finish();
                startActivity(intent);

            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowMyHolidays.this, UpdateholidaytablesActivity.class);
                startActivity(intent);
                finish();

            }
        });
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "This feature will be added soon!", Toast.LENGTH_SHORT).show();

            }
        });
        addHeaderRow();
        printDataBase();

    }


    public  void addHeaderRow(){
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        String[] headerText={"Holiday","Date","Category"};
        for(String c:headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);

    }
    public void printDataBase() {
        //System.out.println("YESSSSS");
        Vector<Holiday> data;
        data = (Vector) dbHandler.databaseToString(email).clone();
        //System.out.println("Paisiiiiiiiiiiiiiii -> " + data.size());
        for(int i=0; i<data.size(); i++){
            String name = data.get(i).getName();
            String date = data.get(i).getDate();
            String category = data.get(i).getCategory();
            // System.out.println(name + " " + date + " " + category);

            TableRow row = new TableRow(context);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            row.setBackgroundResource(R.drawable.border);
            String[] colText={name,date,category};

            for(String text:colText) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setBackgroundResource(R.drawable.border);
                tv.setGravity(Gravity.LEFT);
                tv.setTextSize(16);
                tv.setPadding(5, 5, 5, 5);
                tv.setText(text);
                row.addView(tv);
            }
            tableLayout.addView(row);


        }
    }
}