package com.example.mahirhasan.registrationandlogin;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class ShowMyHolidays2 extends Activity
{
    private ArrayList<Holiday> list;
    private ListView lview;
    private Button addbtn, backbtn, edit_date;
    private listviewAdapter adapter;
    private String event1, date1, category1, event0, date0, category0, st, st1;
    private int year,month, day, id;
    private Calendar cal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_holidays);

        list = new ArrayList<Holiday>();
        list = Main2Activity.list;
        lview = (ListView) findViewById(R.id.listview);
        adapter = new listviewAdapter(ShowMyHolidays2.this, list);
        lview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        addbtn = (Button) findViewById(R.id.addBtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowMyHolidays2.this, UpdateholidaytablesActivity2.class);
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
                Intent intent = new Intent(ShowMyHolidays2.this, Main2Activity.class);
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
            ShowMyHolidays2.this.finish();
        }
    }

    public void showInputBox(Holiday oldItem, final int index){
        final Dialog dialog=new Dialog(ShowMyHolidays2.this);
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
       // System.out.println("1 "+ year + " " + month + " " + day);

        final EditText edit_name=(EditText)dialog.findViewById(R.id.edit_name);
        edit_name.setText(event0);
        edit_name.setSelection(edit_name.getText().length());

        edit_date=(Button) dialog.findViewById(R.id.edit_date);
        edit_date.setText(date0);
        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = 9999;
                new DatePickerDialog(ShowMyHolidays2.this, myDateListener, year, month, day).show();

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
                adapter.notifyDataSetChanged();
                admin_update1();
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
                admin_update2();
                adapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        });

        dialog.show();
    }


    private void admin_update1() {

        String tag_string_req = "req_addremovetables";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppURLs.URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                    } else {
                        String errorMsg = "error_msg";
                        /*Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_SHORT).show();*/
                    }
                } catch (JSONException e) {
                    String errorMsg = "error_msg1";
                    /*Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_SHORT).show();*/
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMsg = "error_msg2";
               /* Toast.makeText(getApplicationContext(),
                        errorMsg, Toast.LENGTH_SHORT).show();*/
                //hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "admin_update1");
                params.put("event0", event0);
                params.put("date0", date0);
                params.put("category0", category0);
                params.put("event1", event1);
                params.put("date1", date1);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void admin_update2()
    {

        String tag_string_req = "req_addremovetables";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppURLs.URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                    } else {
                        String errorMsg = "error_msg";
                       /* Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_SHORT).show();*/
                    }
                } catch (JSONException e) {
                    String errorMsg = "error_msg1";
                    /*Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_SHORT).show();*/
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMsg = "error_msg2";
                /*Toast.makeText(getApplicationContext(),
                        errorMsg, Toast.LENGTH_SHORT).show();*/
                //hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "admin_update2");
                params.put("event0", event0);
                params.put("date0", date0);
                params.put("category0", category0);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 9999) {
            return new DatePickerDialog(ShowMyHolidays2.this, myDateListener, year, month, day);
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