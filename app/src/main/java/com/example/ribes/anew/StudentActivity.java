package com.example.ribes.anew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StudentActivity extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    static View.OnClickListener myOnClickListener;
    ArrayList<UserModel> data;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        progressDialog = new ProgressDialog(StudentActivity.this);
        progressDialog.setCancelable(false);

        data = new ArrayList<>();
        final CustomAdapter adapter = new CustomAdapter(data);

        progressDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GETUSERS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Students", "Students Response: " + response.toString());

                hideDialog();

                try {

                    JSONArray jsonArray = new JSONArray(response);

                    ArrayList<String> studentId = new ArrayList<>();
                    ArrayList<String> studentName = new ArrayList<>();

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
                        if(user.getUser_type().toUpperCase().equals("STUDENT")) {
                            data.add(user);
                            studentId.add(id);
                            studentName.add(name);
                        }

                        adapter.notifyDataSetChanged();

                    }

                    SharedPreferences.Editor editor = getSharedPreferences("ACE", MODE_PRIVATE).edit();

                    Set<String> setId = new HashSet<String>();
                    setId.addAll(studentName);

                    Set<String> setName = new HashSet<>();
                    setName.addAll(studentId);

                    editor.putStringSet("studentsId", setId);
                    editor.putStringSet("studentsName", setName);
                    editor.apply();

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


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
                Intent i = new Intent(StudentActivity.this, Students.class);
                String transitionName = "transition" + position;
                i.putExtra("transitionName", transitionName);
                i.putExtra("name", data.get(position).getName());
                i.putExtra("email", data.get(position).getEmail());
                i.putExtra("address", data.get(position).getAddress());
                i.putExtra("roll", data.get(position).getRoll());
                i.putExtra("faculty", data.get(position).getFaculty());
                i.putExtra("phone_number", data.get(position).getPhone_number());
                ActivityOptionsCompat options =

                        ActivityOptionsCompat.makeSceneTransitionAnimation(StudentActivity.this,
                                view,   // Starting view
                                transitionName    // The String
                        );
                //Start the Intent
                ActivityCompat.startActivity(StudentActivity.this, i, options.toBundle());


                startActivity(i);
            }

            @Override public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }));



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
