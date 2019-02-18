package com.example.ribes.anew;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class AttStudentStatus extends AppCompatActivity {

    ProgressDialog progressDialog;
    String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        progressDialog = new ProgressDialog(AttStudentStatus.this);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Loading ...");

        AlertDialog.Builder alert = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog);


        alert.setMessage("Select the subject of which the attendance is to be checked");
        alert.setTitle("Select the subject");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final Spinner spinner = new Spinner(this);
        spinner.setPadding(50, 50, 50, 50);
        spinner.setBackgroundColor(getResources().getColor(R.color.iron));
        spinner.setBackground(getDrawable(android.R.drawable.btn_dropdown));

        final String[] subjects = {"OOAD", "DBMS", "Embedded", "OS", "Minor", "Economics", "AI"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subjects);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        layout.addView(spinner);



        layout.setPadding(100, 50, 100, 50);
        alert.setView(layout); // Again this is a set method, not add

        alert.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                subject = spinner.getSelectedItem().toString();

                showDialog();

                StringRequest strReq = new StringRequest(Request.Method.POST,
                        AppConfig.URL_ATTSHOW, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Attendance Show", "Attendance Show Response: " + response.toString());

                        hideDialog();

                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String teacher_id = object.getString("teacher_id");
                                String att_date = object.getString("att_date");
                                String student_id = object.getString("student_id");
                                String subjectName = object.getString("subject");
                                String presentSts = object.getString("present");

                                SessionManager sessionManager = new SessionManager(getApplicationContext());
                                String id = sessionManager.getID();

                                Date c = Calendar.getInstance().getTime();
                                String today = c.toString().substring(0, 10);


                                String dateatt = att_date.substring(0,10);




                                if(student_id.equals(id) && today.equals(dateatt) && subject.equals(subjectName))
                                {



                                            boolean presentStatus = Boolean.parseBoolean(presentSts);

                                            if(presentStatus)
                                            {
                                                setContentView(R.layout.activity_present);
                                            }
                                            else
                                            {
                                                setContentView(R.layout.activity_absent);
                                            }

                                            Toolbar toolbar = findViewById(R.id.toolbar);
                                    TextView date = findViewById(R.id.last_updated);
                                    TextView subject = findViewById(R.id.subjectname);



                                            setSupportActionBar(toolbar);

                                    date.setText("Date: " + dateatt);
                                    subject.setText("Subject: " + subjectName);






                                }


                                }



                                //present.add(true);




                            }




//                    boolean error = jObj.getBoolean("error");




                         catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Attendance Show", "Attendance Show Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }
                }) {


                };

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, "req_attstudentshow");


            }
        });

        alert.setCancelable(false);
        alert.show();

















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
