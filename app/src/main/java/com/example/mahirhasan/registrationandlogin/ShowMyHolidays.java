package com.example.mahirhasan.registrationandlogin;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ShowMyHolidays extends Activity
{
    private ArrayList<Holiday> list;
    private ListView lview;
    MyDBHandler dbHandler;
    private Button addbtn,edit_date, backbtn;
    private listviewAdapter adapter;
    private String event0, date0, category0, event1, category1, date1, email, st, st1;
    private int year,month, day, id;
    private Calendar cal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_holidays);
        dbHandler = new MyDBHandler(this, null, null, 1);

        email = LoginActivity.getemail;

        list = new ArrayList<Holiday>();
        list = dbHandler.databaseToString(LoginActivity.getemail);
        lview = (ListView) findViewById(R.id.listview);
        adapter = new listviewAdapter(this, list);
        lview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        addbtn = (Button) findViewById(R.id.addBtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowMyHolidays.this, UpdateholidaytablesActivity.class);
                startActivityForResult(intent,1);
            }
        });

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 showInputBox(list.get(position),position);
             }
         });

        backbtn = (Button) findViewById(R.id.backBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowMyHolidays.this, AddholidaytablesActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 0)
        {
            ShowMyHolidays.this.finish();
        }
    }

    public void showInputBox(Holiday oldItem, final int index){
        final Dialog dialog=new Dialog(ShowMyHolidays.this);
        dialog.setContentView(R.layout.input_box);
        dialog.setTitle("Update Item");

        event0 = oldItem.getName();
        date0 = oldItem.getDate();
        category0 = oldItem. getCategory();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        cal = Calendar.getInstance();
        try {
            System.out.println(date0);
            cal.setTime(sdf1.parse(date0));
        } catch (ParseException e) {}
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        //System.out.println("1 "+ year + " " + month + " " + day);

        final EditText edit_name=(EditText)dialog.findViewById(R.id.edit_name);
        edit_name.setText(event0);
        edit_name.setSelection(edit_name.getText().length());

        edit_date=(Button) dialog.findViewById(R.id.edit_date);
        edit_date.setText(date0);
        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println("Call");
                new DatePickerDialog(ShowMyHolidays.this, myDateListener, year, month, day).show();

            }
        });
        Button save = (Button)dialog.findViewById(R.id.save_edit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event1 = edit_name.getText().toString();
                edit_date.setText(date1);
                category1 = category0;

                list.set(index, new Holiday(event1, date1, category1));
                dbHandler.updateData(LoginActivity.getemail, event1, date1, category1, event0, date0, category0);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        Button cancel = (Button)dialog.findViewById(R.id.cancel_edit);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button delete = (Button)dialog.findViewById(R.id.delete_edit);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(index);
                dbHandler.deleteoneData(LoginActivity.getemail, date0, category0);
                adapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            //System.out.println("2 "+ year + " " + month + " " + day);
            return new DatePickerDialog(ShowMyHolidays.this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {

                arg2++;
                st  = Integer.toString(arg2);
                if(arg2>=1 && arg2<=9) st = "0"+st;
                st1  = Integer.toString(arg3);
                if(arg3>=1 && arg3<=9) st1 = "0"+st1;
                date1 = arg1 + "-" + st + "-" + st1;
                edit_date.setText(date1);

        }
    };


}