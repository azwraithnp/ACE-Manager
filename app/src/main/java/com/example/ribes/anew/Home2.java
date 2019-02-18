package com.example.ribes.anew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class Home2 extends AppCompatActivity {

    CardView Events ,Studentinfo,Attendance,Logout,ViewAtt;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        sessionManager = new SessionManager(getApplicationContext());

        setup();

        Events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home2.this,EventsActivity.class));
            }
        });

        Studentinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home2.this,StudentActivity.class));
            }
        });

        Attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home2.this, com.example.ribes.anew.Attendance.class));
            }
        });

        ViewAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home2.this,AttendanceStatus.class));
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });


    }

    void setup()
    {
        Events = findViewById(R.id.events);
        Studentinfo=findViewById(R.id.Studentinfo);
        Attendance=findViewById(R.id.attendance);
        Logout=findViewById(R.id.logout);
        ViewAtt=findViewById(R.id.showattendance);
    }

    private void logoutUser() {
        sessionManager.setLogin(false);


        // Launching the login activity
        Intent intent = new Intent(Home2.this, Login.class);
        startActivity(intent);
        finish();
    }
}


