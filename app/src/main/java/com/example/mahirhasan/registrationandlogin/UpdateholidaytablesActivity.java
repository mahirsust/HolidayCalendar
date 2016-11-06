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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class UpdateholidaytablesActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private Button fromView;
    private Button toView;
    private Button btncancel;
    private Button btnsave;
    private String category, email, event, date2;
    private EditText event_name, cat;
    private int year, month, day, id;
    private String from_date, to_date, mid_date, st, st1;
    Vector<Holiday> data = new Vector<Holiday>();
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateholidaytables);
        fromView = (Button) findViewById(R.id.from_field);
        toView = (Button) findViewById(R.id.to_field);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        email = LoginActivity.getemail;
        dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.setName(email);

        fromView.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
        toView.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));

        event_name = (EditText) findViewById(R.id.editText);
        cat = (EditText) findViewById(R.id.editText2);

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

                category = cat.getText().toString();
                event = event_name.getText().toString();
                //setDate(view);
                mid_date = from_date;
                System.out.println(from_date +" " + to_date);

                if (event.isEmpty() || category.isEmpty()) {
                    Snackbar.make(view, "Please enter the credentials!", Snackbar.LENGTH_LONG)
                            .show();
                }

                else{
                    //System.out.println("paisi -> " + mid_date);
                    int tag = 0;
                    //updatetables(email2, category, mid_date, event);
                    for(int i=0; ; i++){
                        //System.out.println("Here is "+category);
                        Holiday holiday = new Holiday(event, mid_date, category);
                        data.add(holiday);
                        if (mid_date.equals(to_date)) break;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar c = Calendar.getInstance();
                        try {
                            c.setTime(sdf.parse(mid_date));
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "Error!", Toast.LENGTH_SHORT).show();
                        }
                        c.add(Calendar.DATE, 1);  // number of days to add
                        //System.out.println("error paisi2");
                        mid_date = sdf.format(c.getTime()).toString();
                        // dt is now the new date
                        //System.out.println("error paisi3");
                        //updatetables(email2, category, mid_date, event);
                        //System.out.println("error paisi4");
                        //System.out.println("paisi -> " + mid_date);

                    }
                    dbHandler.addData(email, data);
                    Toast.makeText(getApplicationContext(),
                            "Added!", Toast.LENGTH_SHORT).show();
                    //System.out.println("Here is " + data.size());
                }
                cancel();
            }
        });

        btncancel = (Button) findViewById(R.id.cancel_button);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

    }

    private void cancel() {
        Intent intent = new Intent(UpdateholidaytablesActivity.this, ShowMyHolidays.class);
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
        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999 || id == 9999) {
            return new DatePickerDialog(UpdateholidaytablesActivity.this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            //showDate(arg1, arg2+1, arg3);

            if (id == 9999)
            {
                fromView.setText(new StringBuilder().append(arg1).append("-").append(arg2).append("-").append(arg3));
                //arg2++;
                st  = Integer.toString(arg2);
                if(arg2>=1 && arg2<=9) st = "0"+st;
                st1  = Integer.toString(arg3);
                if(arg3>=1 && arg3<=9) st1 = "0"+st1;
                from_date = arg1 + "-" + st + "-" + st1;
            }
            else if (id == 999)
            {
                toView.setText(new StringBuilder().append(arg1).append("-").append(arg2).append("-").append(arg3));
                arg2++;
                st  = Integer.toString(arg2);
                if(arg2>=1 && arg2<=9) st = "0"+st;
                st1  = Integer.toString(arg3);
                if(arg3>=1 && arg3<=9) st1 = "0"+st1;
                to_date = arg1 + "-" + st + "-" + st1;
            }
        }
    };
    /** private void showDate(int year, int month, int day) {
     dateView.setText(new StringBuilder().append(day).append("/")
     .append(month).append("/").append(year));


    /**private void showDialog() {
     if (!pDialog.isShowing())
     pDialog.show();
     }

     private void hideDialog() {
     if (pDialog.isShowing())
     pDialog.dismiss();
     }*/

}