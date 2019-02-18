package com.example.ribes.anew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;

public class Students extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.textview);

        EditText name, email, address, roll, faculty, phone;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView banner = findViewById(R.id.banner);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        name = findViewById(R.id.display_name);
        email = findViewById(R.id.display_email);
        address = findViewById(R.id.display_address);
        roll = findViewById(R.id.display_roll);
        faculty = findViewById(R.id.display_faculty);
        phone = findViewById(R.id.display_phone);


        Intent intent = getIntent();
        String transitionName = intent.getStringExtra("transitionName");
        banner.setTransitionName(transitionName);

        name.setText(intent.getStringExtra("name"));
        email.setText(intent.getStringExtra("email"));
        address.setText(intent.getStringExtra("address"));
        roll.setText(intent.getStringExtra("roll"));
        faculty.setText(intent.getStringExtra("faculty"));
        phone.setText(intent.getStringExtra("phone_number"));


        name.setEnabled(false);
        email.setEnabled(false);
        address.setEnabled(false);
        roll.setEnabled(false);
        faculty.setEnabled(false);
        phone.setEnabled(false);

    }
}
