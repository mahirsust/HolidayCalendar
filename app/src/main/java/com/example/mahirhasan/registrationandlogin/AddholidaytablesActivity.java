package com.example.mahirhasan.registrationandlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class AddholidaytablesActivity extends AppCompatActivity {

    private Button btnadd;
    private Button btncancel;
    private Button btnedit;
    private CheckBox check, c1, c2, c3, c4, c5, c6, c7;
    private static String addremove1, addremove2, email1, cc1;
    private boolean arr[] = new boolean[7];
    private static String date1, event1, category1, choices;
    Vector <Holiday> data = new Vector<Holiday>();
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addholidaytables);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        c1 = (CheckBox)findViewById(R.id.checkBox);
        c2 = (CheckBox)findViewById(R.id.checkBox2);
        c3 = (CheckBox)findViewById(R.id.checkBox3);
        c4 = (CheckBox)findViewById(R.id.checkBox4);
        c5 = (CheckBox)findViewById(R.id.checkBox5);
        c6 = (CheckBox)findViewById(R.id.checkBox6);
        c7 = (CheckBox)findViewById(R.id.checkBox7);

        dbHandler = new MyDBHandler(this, null, null, 1);
        email1 = LoginActivity.getemail;
        dbHandler.setName(email1);
        choices = LoginActivity.getchoices;
        /*Toast.makeText(getApplicationContext(),
                "Here i got " + choices, Toast.LENGTH_SHORT).show();*/

        if(choices != null) markchoices();

        btnadd = (Button) findViewById(R.id.button);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String addstr="";
                addremove1=checked();

                //choices = getchoices(email1);
                System.out.println(addremove1 + " " + choices);
                changechoices( email1, addremove1);
                //choices = checked();
                for( int i=0; i<addremove1.length(); i++)
                {
                    System.out.println(i + " " + addremove1.charAt(i)+choices.charAt(i));
                    if(addremove1.charAt(i) != choices.charAt(i))
                    {
                        if(addremove1.charAt(i) == '1')
                            Addtables(email1, get_category(Integer.toString(i+1)));
                        else Removetables(email1, get_category(Integer.toString(i+1)));
                    }

                }
                choices = addremove1;
                LoginActivity.getchoices = addremove1;
                markchoices();
            }
        });

        btncancel = (Button) findViewById(R.id.button3);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        btnedit = (Button) findViewById(R.id.editButton);
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choices.equals("0000000")) {
                    Toast.makeText(getApplicationContext(),
                            "No Choices!", Toast.LENGTH_SHORT).show();
                }
                else show_holiday();
            }
        });

    }
    private void markchoices(){
        c1.setChecked(Boolean.valueOf(getBool(choices.charAt(0))));
        c2.setChecked(Boolean.valueOf(getBool(choices.charAt(1))));
        c3.setChecked(Boolean.valueOf(getBool(choices.charAt(2))));
        c4.setChecked(Boolean.valueOf(getBool(choices.charAt(3))));
        c5.setChecked(Boolean.valueOf(getBool(choices.charAt(4))));
        c6.setChecked(Boolean.valueOf(getBool(choices.charAt(5))));
        c7.setChecked(Boolean.valueOf(getBool(choices.charAt(6))));
    }
    private String checked()
    {
        String str="";
        if(c1.isChecked() == true) str += "1";
        else str += "0";
        if(c2.isChecked() == true) str += "1";
        else str += "0";
        if(c3.isChecked() == true) str += "1";
        else str += "0";
        if(c4.isChecked() == true) str += "1";
        else str += "0";
        if(c5.isChecked() == true) str += "1";
        else str += "0";
        if(c6.isChecked() == true) str += "1";
        else str += "0";
        if(c7.isChecked() == true) str += "1";
        else str += "0";

        return str;
    }
    private void show_holiday()
    {
        Intent intent = new Intent(AddholidaytablesActivity.this, ShowMyHolidays.class);
        startActivity(intent);
        finish();
    }
    private void cancel()
    {
        Intent intent = new Intent(AddholidaytablesActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void Addtables(final String email1, final String addremove2)
    {
        System.out.println("here for " + addremove2);
        // Tag used to cancel the request
        String tag_string_req = "req_addremovetables";

        //pDialog.setMessage("Registering ...");
        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppURLs.URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //hideDialog();

                try {
                    System.out.println(response);
                    //System.out.println("1error");
                    JSONObject jObj = new JSONObject(response);
                    //System.out.println("error1");
                    boolean error = jObj.getBoolean("error");
                    //System.out.println("errorpaisi");
                    //data = new Vector<>();

                    data.clear();
                    if (!error) {

                        JSONArray ar = jObj.getJSONArray("user");
                        for( int ii=0; ii<ar.length(); ii++)
                        {
                            date1 = ar.getJSONObject(ii).getString("Date");
                            event1 = ar.getJSONObject(ii).getString("Event");
                            category1 = ar.getJSONObject(ii).getString("Category");
                            Holiday holiday = new Holiday(event1, date1, category1);
                            data.add(holiday);
                        }

                        String successMsg = "Your choice has been updated!";
                        System.out.println(successMsg);
                        Toast.makeText(getApplicationContext(),
                                successMsg, Toast.LENGTH_SHORT).show();
                        dbHandler.addData(email1, data);

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
    }
    private void changechoices(final String email1, final String addremove2)
    {
        // Tag used to cancel the request
        String tag_string_req = "req_addremovetables";

        //pDialog.setMessage("Registering ...");
        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppURLs.URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //hideDialog();

                try {
                    System.out.println(response);
                    //System.out.println("1error");
                    JSONObject jObj = new JSONObject(response);
                    //System.out.println("error1");
                    boolean error = jObj.getBoolean("error");
                    //System.out.println("errorpaisi");
                    //data = new Vector<>();

                    if (!error) {

                        String successMsg = "Your choice has been updated!";
                        System.out.println(successMsg);
                        Toast.makeText(getApplicationContext(),
                                successMsg, Toast.LENGTH_SHORT).show();

                    } else {
                        String errorMsg = "error_msg";
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    String errorMsg = "error_msg1Here";
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMsg = "error_msg2";
                Toast.makeText(getApplicationContext(),
                        errorMsg, Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "replacedata1");
                params.put("email", email1);
                params.put("u_category", addremove2);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void Removetables(final String email1, final String addremove2) {
        dbHandler.deleteData(email1,addremove2);

    }


    public String getBool(char ch) {
        if(ch == '1') return "true";
        else return "false";

    }

    private String get_category(String ii) {

        String ret = "";
        if(ii.equals( "1")) ret = "SUST_Student";
        else if(ii.equals( "2")) ret = "SUST_Teacher";
        else if(ii.equals( "3")) ret = "DU_Student";
        else if(ii.equals( "4")) ret = "DU_Teacher";
        else if(ii.equals( "5")) ret = "Buet_Student";
        else if(ii.equals( "6")) ret = "Buet_Teacher";
        else if(ii.equals( "7")) ret = "bankholidays";
        //System.out.println("ret = " + ret);
        return ret;

    }


    /**private void showDialog() {
     if (!pDialog.isShowing())
     pDialog.show();
     }

     private void hideDialog() {
     if (pDialog.isShowing())
     pDialog.dismiss();
     }*/

}
