package com.example.mahirhasan.registrationandlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Main2Activity extends AppCompatActivity{

    private Button btnLogout, btn1,btn2, btn3, btn4, btn5, btn6, btn7;
    private TextView welcome;
    private Session session;
    MyDBHandler dbHandler;
    public static String select;
    public static ArrayList<Holiday> list;
    private String date, event, category,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        dbHandler = new MyDBHandler(this, null, null, 1);
        list = new ArrayList<Holiday>();

        email = LoginActivity.getemail;

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btn1 = (Button) findViewById(R.id.sust_student);
        btn2 = (Button) findViewById(R.id.sust_teacher);
        btn3 = (Button) findViewById(R.id.du_student);
        btn4 = (Button) findViewById(R.id.du_teacher);
        btn5 = (Button) findViewById(R.id.buet_student);
        btn6 = (Button) findViewById(R.id.buet_teacher);
        btn7 = (Button) findViewById(R.id.bank_holidays);
        session = new Session(Main2Activity.this);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = get_category(""+1);
                populateList(LoginActivity.getemail, select);
            }

        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = get_category(""+2);
                populateList(LoginActivity.getemail, select);
            }

        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = get_category(""+3);
                populateList(LoginActivity.getemail, select);
            }

        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = get_category(""+4);
                populateList(LoginActivity.getemail, select);
            }

        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = get_category(""+5);
                populateList(LoginActivity.getemail, select);
            }

        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = get_category(""+6);
                populateList(LoginActivity.getemail, select);
            }

        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = get_category(""+7);
                populateList(LoginActivity.getemail, select);
            }

        });
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    private void logoutUser() {
        session.setLogin(false);
        Intent intent = new Intent(Main2Activity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void populateList(final String email1, final String addremove2) {
        //System.out.println("here for " + addremove2);
        // Tag used to cancel the request
        String tag_string_req = "req_addremovetables";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppURLs.URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    list.clear();
                    if (!error) {

                        JSONArray ar = jObj.getJSONArray("user");
                        for( int ii=0; ii<ar.length(); ii++)
                        {
                            date = ar.getJSONObject(ii).getString("Date");
                            event = ar.getJSONObject(ii).getString("Event");
                            category = get_category(ar.getJSONObject(ii).getString("Category"));
                            Holiday holiday = new Holiday(event, date, category);
                            list.add(holiday);
                        }

                       show_holiday();
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
               /* Toast.makeText(getApplicationContext(),
                        errorMsg, Toast.LENGTH_SHORT).show();*/
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

    public void refreshList(final String email1, final String addremove2) {
        //System.out.println("here for " + addremove2);
        // Tag used to cancel the request
        String tag_string_req = "req_addremovetables";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppURLs.URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    list.clear();
                    if (!error) {

                        JSONArray ar = jObj.getJSONArray("user");
                        for( int ii=0; ii<ar.length(); ii++)
                        {
                            date = ar.getJSONObject(ii).getString("Date");
                            event = ar.getJSONObject(ii).getString("Event");
                            category = get_category(ar.getJSONObject(ii).getString("Category"));
                            Holiday holiday = new Holiday(event, date, category);
                            list.add(holiday);
                        }

                       // show_holiday();
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
                params.put("tag", "getalldata1");
                params.put("email", email1);
                params.put("u_category", addremove2);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private String get_category(String ii) {

        String ret = "";
        if(ii.equals( "1")) ret = "SUST_Student";
        else if(ii.equals( "2")) ret = "SUST_Teacher";
        else if(ii.equals( "3")) ret = "DU_Student";
        else if(ii.equals( "4")) ret = "DU_Teacher";
        else if(ii.equals( "5")) ret = "Buet_Student";
        else if(ii.equals( "6")) ret = "Buet_Teacher";
        else if(ii.equals( "7")) ret = "Bank_Holidays";
        //System.out.println("ret = " + ret);
        return ret;

    }
    private void show_holiday()
    {
        Intent intent = new Intent(Main2Activity.this, ShowMyHolidays2.class);
        startActivity(intent);
    }
}
