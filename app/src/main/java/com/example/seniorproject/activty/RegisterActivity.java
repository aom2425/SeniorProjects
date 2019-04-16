package com.example.seniorproject.activty;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.example.seniorproject.Client;
import com.example.seniorproject.R;

public class RegisterActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private EditText etName, etEmail, etPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();
    }
    private void initViews(){
        etName = (EditText) findViewById(R.id.et_name);
        etEmail = (EditText) findViewById(R.id.et_reg_email);
        etPassword = (EditText) findViewById(R.id.et_reg_password);
        btnRegister = (Button) findViewById(R.id.button);
    }
    private void initListeners(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = etName.getText().toString();
                String Email = etEmail.getText().toString();
                String Password = etPassword.getText().toString();
                new RegisterUser().execute(Name, Email, Password);
            }
        });
    }/**/
    public class RegisterUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String Name = strings[0];
            String Email = strings[1];
            String Password = strings[2];

            //String finalUrl = url_Register + "&user_name=" + Name + "&user_id=" + Email + "&user_password=" + Password;
            /*try{
                Connection
            } catch (Exception e){

            }/**/
            return null;
        }
    }/**/
        private void initObjects(){}
}
