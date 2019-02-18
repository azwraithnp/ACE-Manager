package com.example.ribes.anew;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static java.util.Calendar.FRIDAY;
import static java.util.Calendar.PM;

public class Login extends AppCompatActivity {

    private EditText Username;
    private  EditText Password;
    private Button Login;
    private TextView Reg;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    private SessionManager session;
    private SQLiteHandler db;
    //  private static int SPLASH_TIME_OUT=4000;

    private PendingIntent pendingIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Calendar calendar = Calendar.getInstance();

        ArrayList<String> subjects = new ArrayList<>();
        ArrayList<Integer> hours = new ArrayList<>();
        ArrayList<Integer> mins = new ArrayList<>();

        String[] sun = {getString(R.string.Embedded), getString(R.string.economics), getString(R.string.lab)};
        String[] suntime = {getString(R.string.time2), getString(R.string.time1), getString(R.string.time3)};

        String[] mon = {getString(R.string.ai), getString(R.string.Embedded), getString(R.string.data), getString(R.string.ooad)};
        String[] montime = {getString(R.string.time2), getString(R.string.time1), getString(R.string.time4), getString(R.string.time5)};

        String[] tue = {getString(R.string.lab1), getString(R.string.os), getString(R.string.time8), };
        String[] tuetime = {getString(R.string.time6), getString(R.string.time7), getString(R.string.time9)};

        String[] wed = {getString(R.string.economics), getString(R.string.ai), getString(R.string.ait), getString(R.string.os)};
        String[] wedtime = {getString(R.string.time2), getString(R.string.time1), getString(R.string.time10), getString(R.string.time11)};

        String[] thur = {getString(R.string.lab2), getString(R.string.data)};
        String[] thurtime = {getString(R.string.time12), getString(R.string.time13)};

        String[] fri = {getString(R.string.Embedded), getString(R.string.os), getString(R.string.ooad)};
        String[] fritime = {getString(R.string.time2), getString(R.string.time1), getString(R.string.time3)};

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                for(int i=0;i<sun.length;i++)
                {
                    subjects.add(sun[i]);
                    String time = suntime[i];   //8:40-10:20
                    String[] abc = time.split("-");
                    String first_part = abc[0];
                    String[] time_parts = first_part.split(":");
                    String hour = time_parts[0];
                    String min = time_parts[1];
                    hours.add(Integer.parseInt(hour));
                    mins.add(Integer.parseInt(min));


                }
                break;

            case Calendar.MONDAY:
                // Current day is Monday
                for(int i=0;i<mon.length;i++)
                {
                    subjects.add(mon[i]);
                    String time = montime[i];   //8:40-10:20
                    String[] abc = time.split("-");
                    String first_part = abc[0];
                    String[] time_parts = first_part.split(":");
                    String hour = time_parts[0];
                    String min = time_parts[1];
                    hours.add(Integer.parseInt(hour));
                    mins.add(Integer.parseInt(min));


                }
                break;

            case Calendar.TUESDAY:
                for(int i=0;i<tue.length;i++)
                {
                    subjects.add(tue[i]);
                    String time = tuetime[i];   //8:40-10:20
                    String[] abc = time.split("-");
                    String first_part = abc[0];
                    String[] time_parts = first_part.split(":");
                    String hour = time_parts[0];
                    String min = time_parts[1];
                    hours.add(Integer.parseInt(hour));
                    mins.add(Integer.parseInt(min));


                }
                break;

            case Calendar.WEDNESDAY:
                for(int i=0;i<wed.length;i++)
                {
                    subjects.add(wed[i]);
                    String time = wedtime[i];   //8:40-10:20
                    String[] abc = time.split("-");
                    String first_part = abc[0];
                    String[] time_parts = first_part.split(":");
                    String hour = time_parts[0];
                    String min = time_parts[1];
                    hours.add(Integer.parseInt(hour));
                    mins.add(Integer.parseInt(min));


                }
                break;

            case Calendar.THURSDAY:
                for(int i=0;i<thur.length;i++)
                {
                    subjects.add(thur[i]);
                    String time = thurtime[i];   //8:40-10:20
                    String[] abc = time.split("-");
                    String first_part = abc[0];
                    String[] time_parts = first_part.split(":");
                    String hour = time_parts[0];
                    String min = time_parts[1];
                    hours.add(Integer.parseInt(hour));
                    mins.add(Integer.parseInt(min));


                }
                break;

            case Calendar.FRIDAY:
                for(int i=0;i<fri.length;i++)
                {
                    subjects.add(fri[i]);
                    String time = fritime[i];   //8:40-10:20
                    String[] abc = time.split("-");
                    String first_part = abc[0];
                    String[] time_parts = first_part.split(":");
                    String hour = time_parts[0];
                    String min = time_parts[1];
                    hours.add(Integer.parseInt(hour));
                    mins.add(Integer.parseInt(min));


                }
                break;
        }

        setup();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "Routine";
//            String description = "Routine info";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel("12345", name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        // Create an explicit intent for an Activity in your app
//        Intent nintent = new Intent(this, SignUp.class);
//        nintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nintent, 0);
//
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "12345")
//                .setSmallIcon(android.R.drawable.ic_media_ff)
//                .setContentTitle("Hello")
//                .setContentText("This is a notification")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//        // notificationId is a unique int for each notification that you must define
//        notificationManager.notify(1, mBuilder.build());


        int count =0;
        String subject = "";
        for(int i=0;i<subjects.size();i++)
        {
            subject = subjects.get(i);
            calendar.set(Calendar.HOUR_OF_DAY, hours.get(i));
            calendar.set(Calendar.MINUTE, mins.get(i));
            calendar.set(Calendar.SECOND, 0);


            Intent myIntent = new Intent(Login.this, AlarmReciever.class);
            myIntent.putExtra("id", count);
            myIntent.putExtra("name", subject);
            pendingIntent = PendingIntent.getBroadcast(Login.this, count, myIntent,0);

            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
            count++;

        }




        progressDialog = new ProgressDialog(Login.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setCancelable(false);

        db = new SQLiteHandler(getApplicationContext());

        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            String type = session.getType();

            if(type.toUpperCase().equals("TEACHER"))
            {

                // Launch main activity
                Intent intent = new Intent(Login.this,
                        Home2.class);

                startActivity(intent);
                finish();

            }

            else
            {
                // Launch main activity
                Intent intent = new Intent(Login.this,
                        Home.class);

                startActivity(intent);
                finish();


            }
            finish();
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                boolean valid = true;

                String username = Username.getText().toString();
                String password = Password.getText().toString();

                if (username.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                    Username.setError("enter a valid email address");
                    valid = false;
                } else {
                    Username.setError(null);
                }

                if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                    Password.setError("between 4 and 10 alphanumeric characters");
                    valid = false;
                } else {
                    Password.setError(null);
                }

                if(valid) {
                    checkLogin(Username.getText().toString(), Password.getText().toString());
                }
            }
        });


        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });
    }

//

    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Login", "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    boolean active = jObj.getBoolean("active");

                    // Check for error node in json
                    if (!error && active) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);


                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");
                        String id = jObj.getString("id");
                        session.setID(id);
                        JSONObject user = jObj.getJSONObject("user");



                        String name = user.getString("name");
                        String email = user.getString("email");
                        String user_type = user.getString("user_type");
                        session.setType(user_type);
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);


                        if(user_type.toUpperCase().equals("TEACHER"))
                        {

                            // Launch main activity

                            Intent intent = new Intent(Login.this,
                                    Home2.class);

                            startActivity(intent);
                            finish();

                        }

                        else if(user_type.toUpperCase().equals("ADMIN"))
                        {
                            // Launch main activity
                            Intent intent = new Intent(Login.this,
                                    AdminDashboard.class);

                            startActivity(intent);
                            finish();


                        }
                        else
                        {
                            // Launch main activity
                            Intent intent = new Intent(Login.this,
                                    Home.class);

                            startActivity(intent);
                            finish();

                        }
                    } else {
                        // Error in login. Get the error message

                        String errorMsg;
                        if(!active)
                        {
                            errorMsg = "Your account is not activated yet. Please contact the admin!";
                        }
                        else
                        {
                            errorMsg = jObj.getString("error_msg");
                        }
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Login", "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }




    public void setup() {
        Username = (EditText) findViewById(R.id.input_name);
        Password = (EditText) findViewById(R.id.input_password);
        Login = (Button) findViewById(R.id.btn_login);
        Reg = (TextView) findViewById(R.id.link_signup);
    }


}
