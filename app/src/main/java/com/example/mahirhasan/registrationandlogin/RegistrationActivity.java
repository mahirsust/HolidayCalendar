package com.example.mahirhasan.registrationandlogin;

/**
 * Created by MAHIR HASAN on 12/10/2016.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
//import android.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private TextView tvLogin;
    private EditText fullName, email_to_register, password_to_register;
    private Spinner spinner;
    private Button registerButton;
    private Session session;
    private ProgressDialog pDialog;
    public static String getcategory;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler = new MyDBHandler(this, null, null, 1);

        spinner = (Spinner) findViewById(R.id.cat_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        session = new Session(RegistrationActivity.this);

        if (session.getLoggedIn()) {
            Intent intent = new Intent(RegistrationActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }
        registerButton = (Button) findViewById(R.id.register_button);
        fullName = (EditText) findViewById(R.id.fullname_register);
        email_to_register = (EditText) findViewById(R.id.email_register);
        password_to_register = (EditText) findViewById(R.id.password_register);
        tvLogin = (TextView) findViewById(R.id.tv_signin);
        spinner = (Spinner) findViewById(R.id.cat_spinner);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fullName.getText().toString();
                String email = email_to_register.getText().toString();
                String password = password_to_register.getText().toString();
                String category = spinner.getSelectedItem().toString();
                getcategory = category;

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !category.isEmpty()) {
                    registerUser(name, email, password, category);
                } else {
                    Snackbar.make(v, "Please enter the credentials!", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this,
                        LoginActivity.class);
                startActivity(intent);
               // finish();
            }
        });
    }

    private void registerUser(final String name, final String email,
                              final String password, final String category) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        //showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppURLs.URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        dbHandler.CreateTable(email);
                        String errorMsg = "Registration Successful!";
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(intent);

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                       /* Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMsg = "error_msg";
                /*Toast.makeText(getApplicationContext(),
                        errorMsg, Toast.LENGTH_LONG).show();*/
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "register");
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("category", category);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /*private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
*/
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}