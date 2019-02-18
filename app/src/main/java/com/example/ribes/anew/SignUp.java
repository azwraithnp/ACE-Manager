package com.example.ribes.anew;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private static final String TAG = SignUp.class.getSimpleName();

    private Spinner spinner;
    private EditText Username;
    private EditText Password;
    private EditText Email;
    private EditText Address;
    private EditText Phone;
    private Button Register;
    private TextView Login;
    private EditText Roll;
    private EditText Faculty;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        setup();

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(SignUp.this,
                    Main.class);
            startActivity(intent);
            finish();
        }

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( validate())
                {
                    String user_name = Username.getText().toString();
                    String user_email = Email.getText().toString().trim();
                    String user_add = Address.getText().toString().trim();
                    String user_psw = Password.getText().toString().trim();
                    String phone = Phone.getText().toString().trim();
                    String user_roll=Roll.getText().toString().trim();
                    String user_faculty=Faculty.getText().toString().trim();
                    String user_type = spinner.getSelectedItem().toString().trim();


                    registerUser(user_name, user_email,user_add,user_roll,user_faculty, user_psw, phone,user_type);
                } }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });
    }

    public void setup()
    {
        Username =(EditText)findViewById(R.id.input_name);
        Password=(EditText)findViewById(R.id.input_password);
        Email=(EditText)findViewById(R.id.input_email);
        Address=(EditText)findViewById(R.id.input_address) ;
        Register=(Button)findViewById(R.id.btn_signup);
        Roll=(EditText)findViewById(R.id.input_roll);
        Faculty=(EditText)findViewById(R.id.input_faculty);
        Login = (TextView)findViewById(R.id.link_login);
        Phone = (EditText) findViewById(R.id.input_phone);
        spinner=(Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    private boolean validate()
    {
        boolean valid = true;

        String name = Username.getText().toString();
        String email = Email.getText().toString();
        String address = Address.getText().toString();
        String password = Password.getText().toString();
        String roll=Roll.getText().toString();
        String faculty=Faculty.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            Username.setError("at least 3 characters");
            valid = false;
        } else {
            Username.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("enter a valid email address");
            valid = false;
        } else {
            Email.setError(null);

        }

        if (address.isEmpty() || address.length() < 5) {
            Address.setError("at least 5 characters");
            valid = false;
        } else {
            Address.setError(null);
        }

        if (roll.isEmpty() || roll.length() < 3) {
            Roll.setError("at least 3 characters");
            valid = false;
        } else {
            Roll.setError(null);
        }

        if (faculty.isEmpty() || faculty.length() < 3) {
            Faculty.setError("at least 3 characters");
            valid = false;
        } else {
            Faculty.setError(null);
        }



        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            Password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            Password.setError(null);
        }

        return valid;

    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String name, final String email, final String address, final String roll,
                              final String faculty, final String password, final String phone_number,final String user_type) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");
                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String address = user.getString("address");
                        String roll = user.getString("roll");
                        String faculty=user.getString("faculty");
                        String phone_number=user.getString("phone_number");
                        String user_type =user.getString("user_type");
                        String created_at = user.getString("created_at");

                        // Inserting row in users table
                     //   db.addUser(name, email,uid,address,roll,faculty,phone_number,user_type,created_at);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
                        // Launch login activity
                        Intent intent = new Intent(
                                SignUp.this,
                                Login.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("address",address);
                params.put("roll",roll);
                params.put("faculty",faculty);
                params.put("password", password);
                params.put("phone_number", phone_number);
                params.put("user_type", user_type);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
