package com.example.ribes.anew;
//Dashoard
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import com.example.ribes.anew.Planner.Main2Activity;

public class Home extends AppCompatActivity {

    private CardView Events,News,Planner,Routine,Login,Logout,ViewAtt;
    private CardView academics;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(getApplicationContext());

        Events=(CardView)findViewById(R.id.events);
       // News=(CardView)findViewById(R.id.News);
        Planner=(CardView)findViewById(R.id.Planner);
        Routine=(CardView)findViewById(R.id.Routine);
//        Login=(CardView)findViewById(R.id.Login);
        academics = findViewById(R.id.academics);
       // StudentInfo=(CardView)findViewById(R.id.Studentinfo);
        Planner=findViewById(R.id.Planner);
        ViewAtt=findViewById(R.id.viewattendance);
    Logout=findViewById(R.id.logout);

//        Login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Home.this, Login.class));
//                finish();
//            }
//        });

        Routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,Routine.class));
                finish();
            }
        });

        Events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,EventsActivity.class));

            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        academics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, GraphActivity.class));
            }
        });

        Planner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Main2Activity.class));
            }
        });


        ViewAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,AttStudentStatus.class));
            }
        });

//        StudentInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Home.this,StudentActivity.class));
//                finish();
//                }
//        });

    }

    private void logoutUser() {
        sessionManager.setLogin(false);

        // Launching the login activity
        Intent intent = new Intent(Home.this, Login.class);
        startActivity(intent);
        finish();
    }
}
