package com.example.mahirhasan.registrationandlogin;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ShowMyHolidays2 extends Activity
{
    private ArrayList<Holiday> list;
    private ListView lview;
    private Button addbtn, backbtn;
    private listviewAdapter adapter;
    private String event, date, category;
    private Toast toast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_holidays);

        list = new ArrayList<Holiday>();
        toast = Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_SHORT);
        //populateList(LoginActivity.getemail, Main2Activity.select);
        list = Main2Activity.list;
        lview = (ListView) findViewById(R.id.listview);
        adapter = new listviewAdapter(ShowMyHolidays2.this, list);
        lview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //System.out.println("Size " + list.size());


        addbtn = (Button) findViewById(R.id.addBtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowMyHolidays2.this, UpdateholidaytablesActivity.class);
                startActivity(intent);
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
                finish();
            }
        });

    }

    public void showInputBox(Holiday oldItem, final int index){
        final Dialog dialog=new Dialog(ShowMyHolidays2.this);
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
                //dbHandler.updateData(LoginActivity.getemail, edit_name.getText().toString(), edit_date.getText().toString(), edit_category.getText().toString());
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
                //dbHandler.deleteoneData(LoginActivity.getemail, edit_date.getText().toString(), edit_category.getText().toString() );
                adapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        });

        dialog.show();
    }


   /* private void populateList(final String email1, final String addremove2) {
        //System.out.println("here for " + addremove2);
        // Tag used to cancel the request
        String tag_string_req = "req_addremovetables";
        System.out.println(email1 + " " + addremove2);

        //pDialog.setMessage("Registering ...");
        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppURLs.URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //hideDialog();

                try {
                    //System.out.println(response);
                    //System.out.println("1error");
                    JSONObject jObj = new JSONObject(response);
                    //System.out.println("error1");
                    boolean error = jObj.getBoolean("error");
                    //System.out.println("errorpaisi");
                    //data = new Vector<>();
                    list.clear();
                    if (!error) {

                        JSONArray ar = jObj.getJSONArray("user");
                        for( int ii=0; ii<ar.length(); ii++)
                        {
                            date = ar.getJSONObject(ii).getString("Date");
                            event = ar.getJSONObject(ii).getString("Event");
                            category = ar.getJSONObject(ii).getString("Category");
                            Holiday holiday = new Holiday(event, date, category);
                            list.add(holiday);
                        }
                        System.out.println("Size " + list.size());
                        toast.cancel();


                    } else {
                        String errorMsg = "error_msg";
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    String errorMsg = "error_msg1";
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMsg = "error_msg2";
                Toast.makeText(getApplicationContext(),
                        errorMsg, Toast.LENGTH_SHORT).show();
                //hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "getalldata1");
                params.put("email", email1);
                params.put("u_category", addremove2);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }*/
}