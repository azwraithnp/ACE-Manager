package com.example.ribes.anew;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Attendance extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    ArrayList<UserModel> data;
    ProgressDialog progressDialog;
    ArrayList<Boolean> present;
    ArrayList<String> ids;
    Button submit;
    AttendanceAdapter adapter;
    String subject = "";
    ArrayList<String> names = new ArrayList<>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        recyclerView = (RecyclerView) findViewById(R.id.att_recycler_view);
        recyclerView.setHasFixedSize(true);
        submit = findViewById(R.id.submit);

        AlertDialog.Builder alert = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog);

        sharedPreferences = getSharedPreferences("ACE", MODE_PRIVATE);


        alert.setMessage("Select the subject of which the attendance is being taken");
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
            }
        });

        alert.setCancelable(false);
        alert.show();

        progressDialog = new ProgressDialog(Attendance.this);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Loading ...");
        showDialog();

            present = new ArrayList<>();
            ids = new ArrayList<>();

            data = new ArrayList<>();





            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_GETUSERS, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d("Students", "Students Response: " + response.toString());

                    hideDialog();

                    try {

                        JSONArray jsonArray = new JSONArray(response);



                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String uid = object.getString("unique_id");
                            String name = object.getString("name");
                            String email = object.getString("email");
                            String address = object.getString("address");
                            String roll = object.getString("roll");
                            String faculty = object.getString("faculty");
                            String phone_number = object.getString("phone_number");
                            String user_type = object.getString("user_type");
                            String created_at = object.getString("created_at");
                            String updated_at = object.getString("updated_at");
                            UserModel user = new UserModel(id, uid, name, email, address, roll, faculty, phone_number, user_type, created_at, updated_at);
                            //present.add(true);
                            if(user.getUser_type().toUpperCase().equals("STUDENT")) {
                                String idname = id + "-" + name;
                                names.add(idname);


                                data.add(user);

                            }





                        }
                        SharedPreferences.Editor editor = getSharedPreferences("ACE", MODE_PRIVATE).edit();
                        Set<String> set = new HashSet<String>();
                        set.addAll(names);
                        editor.putStringSet("names", set);
                        editor.apply();


                        adapter = new AttendanceAdapter(data, Attendance.this);


                        layoutManager = new LinearLayoutManager(Attendance.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);

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
                    Log.e("Students", "Students Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }) {


            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, "req_users");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present = adapter.getPresent();
                ids = adapter.getIds();


                for (int num = 0; num < present.size(); num++) {

                    final int j = num;

                    showDialog();


                    StringRequest strReq = new StringRequest(Request.Method.POST,
                            AppConfig.URL_ATT, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.d("Attendance", "Attendance Response: " + response.toString());

                            hideDialog();

                            try {

                                JSONObject object = new JSONObject(response);
                                boolean error = object.getBoolean("error");
                                if (!error) {

                                    Toast.makeText(Attendance.this, "Saved!", Toast.LENGTH_SHORT).show();

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

                            SessionManager sessionManager = new SessionManager(getApplicationContext());
                            String id = sessionManager.getID();

                            Date c = Calendar.getInstance().getTime();

                            params.put("teacher_id", id);
                            params.put("att_date", c.toString());
                            params.put("student_id", ids.get(j));
                            params.put("subject", subject);
                            params.put("present", present.get(j).toString());


                            return params;
                        }


                    };

                    // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strReq, "req_att");
                }

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
