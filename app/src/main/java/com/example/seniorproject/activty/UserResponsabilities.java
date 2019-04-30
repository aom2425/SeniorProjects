package com.example.seniorproject.activty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.example.seniorproject.R;

public class UserResponsabilities extends AppCompatActivity {
    Button btn_client, btn_cyclist, btn_res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_selection);
        initViews();
        initListenerClient();
        initListenerCyclist();
        initListenerRestaurant();
    }

    private void initViews() {
        btn_client = (Button) findViewById(R.id.btn_user_c);
        btn_cyclist = (Button) findViewById(R.id.btn_cyclist);
        btn_res = (Button) findViewById(R.id.btn_restaurants);
    }

    private void initListenerClient() {
        btn_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_res = "";
                user_res = "Client";
                Intent i = new Intent(UserResponsabilities.this, RegisterActivity.class);
                i.putExtra("Responsability", user_res);
                startActivity(i);
            }
        });
    }
    private void initListenerCyclist() {
        btn_cyclist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_res = "";
                user_res = "Cyclist";
                Intent i = new Intent(UserResponsabilities.this, RegisterActivity.class);
                i.putExtra("Responsability", user_res);
                startActivity(i);
            }
        });
    }
    private void initListenerRestaurant() {
        btn_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_res = "";
                user_res = "Restaurant";
                Intent i = new Intent(UserResponsabilities.this, RegisterActivity.class);
                i.putExtra("Responsability", user_res);
                startActivity(i);
            }
        });
    }

}