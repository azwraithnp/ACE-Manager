package com.example.ribes.anew;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VerifyActivity extends AppCompatActivity {

    ArrayList<UserModel> data = new ArrayList<>();
    ProgressDialog progressDialog;
    VerifyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        progressDialog = new ProgressDialog(VerifyActivity.this);
        progressDialog.setCancelable(false);

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
                        boolean active = Boolean.parseBoolean(object.getString("active"));
                        UserModel user = new UserModel(id, uid, name, email, address, roll, faculty, phone_number, user_type, created_at, updated_at);
                        //present.add(true);
                        if(!active) {
                            data.add(user);

                        }



                    }

//                    Toast.makeText(VerifyActivity.this, "" + data.size(), Toast.LENGTH_SHORT).show();
                    adapter = new VerifyAdapter(data, VerifyActivity.this);



                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
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



    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
        finish();
        startActivity(getIntent());
    }
}
