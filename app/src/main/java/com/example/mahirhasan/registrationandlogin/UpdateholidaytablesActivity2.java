package com.example.mahirhasan.registrationandlogin;

import android.app.DatePickerDialog;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class UpdateholidaytablesActivity2 extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private Button fromView;
    private Button toView;
    private Button btncancel;
    private Button btnsave;
    private String category, email, event, date2;
    private EditText event_name, cat;
    private int year, month, day, id, cnt1, cnt2;
    private String from_date, to_date, mid_date, st, st1;
    Vector<Holiday> data = new Vector<Holiday>();
    MyDBHandler dbHandler;
    private Date from, to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateholidaytables2);
        fromView = (Button) findViewById(R.id.from_field);
        toView = (Button) findViewById(R.id.to_field);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        category = Main2Activity.select;

        email = LoginActivity.getemail;
        dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.setName(email);

        fromView.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
        toView.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));

        event_name = (EditText) findViewById(R.id.editText);

        st  = Integer.toString(month);
        if(month>=1 && month<=9) st = "0"+st;
        st1  = Integer.toString(day);
        if(day>=1 && day<=9) st1 = "0"+st1;

        from_date = Integer.toString(year) + "-" + st + "-" + st1;
        to_date = Integer.toString(year) + "-" + st + "-" + st1;
        btnsave = (Button) findViewById(R.id.save_button);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                event = event_name.getText().toString();
                mid_date = from_date;

                if (event.equals("")) {
                    Snackbar.make(view, "Please enter the credentials!", Snackbar.LENGTH_LONG)
                            .show();
                }

                else{
                    System.out.println(from_date + " " + to_date);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        from = sdf1.parse(from_date);
                        to = sdf1.parse(to_date);
                    } catch (ParseException e) {}

                    if (to.compareTo(from) < 0) {
                        Snackbar.make(view, "Check your dates!", Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        cnt1 = cnt2 = 0;
                        for(int i=0; ; i++){
                            cnt1++;
                            Holiday holiday = new Holiday(event, mid_date, category);
                            admin_update3(holiday);
                            if (mid_date.equals(to_date)) break;
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Calendar c = Calendar.getInstance();
                            try {
                                c.setTime(sdf.parse(mid_date));
                            } catch (Exception e) {}
                            c.add(Calendar.DATE, 1);  // number of days to add
                            //System.out.println("error paisi2");
                            mid_date = sdf.format(c.getTime()).toString();

                        }

                        Toast.makeText(getApplicationContext(),
                                "Added!", Toast.LENGTH_SHORT).show();

                    }

                }


            }
        });

        btncancel = (Button) findViewById(R.id.cancel_button);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void newIntent() {

        setResult(0);
        new Main2Activity().refreshList(LoginActivity.getemail, Main2Activity.select);
        Intent intent = new Intent(UpdateholidaytablesActivity2.this, ShowMyHolidays2.class);
        startActivity(intent);
        finish();


    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        switch (view.getId()) {
            case R.id.from_field:
                id = 9999;
                break;
            case R.id.to_field:
                id = 999;
                break;
            default:
                throw new RuntimeException("Unknow button ID");
        }
        showDialog(id);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999 || id == 9999) {
            return new DatePickerDialog(UpdateholidaytablesActivity2.this, myDateListener, year, month-1, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            if (id == 9999) {
                arg2++;
                st  = Integer.toString(arg2);
                if(arg2>=1 && arg2<=9) st = "0"+st;
                st1  = Integer.toString(arg3);
                if(arg3>=1 && arg3<=9) st1 = "0"+st1;
                from_date = arg1 + "-" + st + "-" + st1;
                fromView.setText(from_date);
            }
            else if (id == 999) {
                arg2++;
                st  = Integer.toString(arg2);
                if(arg2>=1 && arg2<=9) st = "0"+st;
                st1  = Integer.toString(arg3);
                if(arg3>=1 && arg3<=9) st1 = "0"+st1;
                to_date = arg1 + "-" + st + "-" + st1;
                toView.setText(to_date);
            }
        }
    };

    private void admin_update3(final Holiday h)
    {

        String tag_string_req = "req_addremovetables";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppURLs.URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        cnt2++;
                        if(cnt1 == cnt2) newIntent();
                    }
                    else {
                        String errorMsg = "error_msg";
                       /* Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_SHORT).show();*/
                    }
                } catch (JSONException e) {
                    String errorMsg = "error_msg1";
                   /* Toast.makeText(getApplicationContext(),
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
                params.put("tag", "admin_update3");
                params.put("table_name", h.getCategory());
                params.put("event", h.getName());
                params.put("date", h.getDate());
                params.put("category", getNum(h.getCategory()));

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private String getNum(String ii) {
        String ret = "";
        if(ii.equals( "SUST_Student")) ret = "1";
        else if(ii.equals( "SUST_Teacher")) ret = "2";
        else if(ii.equals( "DU_Student")) ret = "3";
        else if(ii.equals( "DU_Teacher")) ret = "4";
        else if(ii.equals( "Buet_Student")) ret = "5";
        else if(ii.equals( "Buet_Teacher")) ret = "6";
        else if(ii.equals( "Bank_Holidays")) ret = "7";
        return ret;

    }

}