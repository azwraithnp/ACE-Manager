package com.example.ribes.anew;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Detailed_page extends AppCompatActivity {

    EditText name, email, address, roll, faculty, phone,user_type;
    ProgressDialog progressDialog;
    String id;
    Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_page);

        name = findViewById(R.id.display_name);
        email = findViewById(R.id.display_email);
        address = findViewById(R.id.display_address);
        roll = findViewById(R.id.display_roll);
        faculty = findViewById(R.id.display_faculty);
        phone = findViewById(R.id.display_phone);
        user_type=findViewById(R.id.display_type);
        verify = findViewById(R.id.verifyButton);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name.setText(intent.getStringExtra("name"));
        email.setText(intent.getStringExtra("email"));
        address.setText(intent.getStringExtra("address"));
        roll.setText(intent.getStringExtra("roll"));
        faculty.setText(intent.getStringExtra("faculty"));
        phone.setText(intent.getStringExtra("phone_number"));
       user_type.setText(intent.getStringExtra("user_type"));



        name.setEnabled(false);
        email.setEnabled(false);
        address.setEnabled(false);
        roll.setEnabled(false);
        faculty.setEnabled(false);
        phone.setEnabled(false);
        user_type.setEnabled(false);

        progressDialog = new ProgressDialog(Detailed_page.this);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Loading ...");


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();

                StringRequest strReq = new StringRequest(Request.Method.POST,
                        AppConfig.URL_VERIFY, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Attendance", "Attendance Response: " + response.toString());

                        hideDialog();

                        try {

                            JSONObject object = new JSONObject(response);
                            boolean error = object.getBoolean("error");
                            if (!error) {

                                Toast.makeText(Detailed_page.this, "Saved!", Toast.LENGTH_SHORT).show();

                            }


//                    boolean error = jObj.getBoolean("error");


                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Attendance", "Attendance Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        // Posting parameters to login url
                        Map<String, String> params = new HashMap<String, String>();



                        Date c = Calendar.getInstance().getTime();

                        params.put("student_id", id);


                        return params;
                    }


                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, "req_att");
            }





        });




    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
