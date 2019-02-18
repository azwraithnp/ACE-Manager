package com.example.ribes.anew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class AdminDashboard extends AppCompatActivity {

    CardView Verify,Logout;

    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        Verify=findViewById(R.id.verify);
        Logout=findViewById(R.id.Logout);

        sessionManager = new SessionManager(getApplicationContext());

        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AdminDashboard.this,VerifyActivity.class));
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });


          }


    private void logoutUser() {
        sessionManager.setLogin(false);

        // Launching the login activity
        Intent intent = new Intent(AdminDashboard.this, Login.class);
        startActivity(intent);
        finish();
    }

}
