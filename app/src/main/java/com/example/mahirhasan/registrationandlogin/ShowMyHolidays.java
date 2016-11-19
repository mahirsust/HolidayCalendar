package com.example.mahirhasan.registrationandlogin;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ShowMyHolidays extends Activity
{
    private ArrayList<Holiday> list;
    private ListView lview;
    MyDBHandler dbHandler;
    private Button addbtn,deletebtn, backbtn;
    private listviewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_holidays);
        dbHandler = new MyDBHandler(this, null, null, 1);

        list = new ArrayList<Holiday>();
        populateList();
        lview = (ListView) findViewById(R.id.listview);
        adapter = new listviewAdapter(this, list);
        lview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        addbtn = (Button) findViewById(R.id.addBtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowMyHolidays.this, UpdateholidaytablesActivity.class);
                startActivity(intent);
            }
        });

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 // Show input box
                 System.out.println("KAJ KORSE");
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

    public void showInputBox(Holiday oldItem, final int index){
        final Dialog dialog=new Dialog(ShowMyHolidays.this);
        dialog.setContentView(R.layout.input_box);
        dialog.setTitle("Update Item");

        final EditText edit_name=(EditText)dialog.findViewById(R.id.edit_name);
        edit_name.setText(oldItem.getName());
        edit_name.setSelection(edit_name.getText().length());

        final EditText edit_category=(EditText)dialog.findViewById(R.id.edit_category);
        edit_category.setText(oldItem.getCategory());
        edit_category.setSelection(edit_category.getText().length());

        final EditText edit_date=(EditText)dialog.findViewById(R.id.edit_date);
        edit_date.setText(oldItem.getDate());
        edit_date.setSelection(edit_date.getText().length());

        Button save = (Button)dialog.findViewById(R.id.save_edit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.set(index, new Holiday(edit_name.getText().toString(), edit_date.getText().toString(), edit_category.getText().toString()));
                dbHandler.updateData(LoginActivity.getemail, edit_name.getText().toString(), edit_date.getText().toString(), edit_category.getText().toString());
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
                dbHandler.deleteoneData(LoginActivity.getemail, edit_date.getText().toString(), edit_category.getText().toString() );
                adapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        });

        dialog.show();
    }


    private void populateList() {
        this.list = dbHandler.databaseToString(LoginActivity.getemail);
    }
}