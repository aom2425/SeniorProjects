package com.example.seniorproject.activty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.seniorproject.ConnectionToServer.ConnectClient;
import com.example.seniorproject.R;
import com.example.seniorproject.helper.InputValidation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    Button btn_Login;
    TextView tvRegister;
    EditText et_Email, et_Password;
    ConnectClient connectClient;

    private final AppCompatActivity activity = LoginActivity.this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        //initListeners();
    }
    private void initViews(){
        et_Email = (EditText) findViewById(R.id.et_email);
        et_Password = (EditText) findViewById(R.id.et_password);
        btn_Login = (Button) findViewById(R.id.btn_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);
    }
    private void initListeners(){
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = et_Email.getText().toString();
                String Password = et_Password.getText().toString();
                //new LoginUser().execute(Email, Password);
                switch (v.getId()){
                    case R.id.btn_login:
                        verifySQL();
                        break;
                    case R.id.tv_register:
                        Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(intentRegister);
                        break;
                }
            }
            private void verifySQL(){
                if(!InputValidation.isInputEditTextFilled(et_Email)) {
                    return;
                }
                if(!InputValidation.isInputEditTextEmail(et_Email)){
                    return;
                }
                if(!InputValidation.isInputEditTextFilled(et_Email)){
                    return;
                }
                if(connectClient.checkUsers(et_Email.getText().toString().trim(), et_Password.getText().toString().trim())){
                    Intent accountIntent = new Intent(activity, DashboardActivity.class);
                    accountIntent.putExtra("EMAIL", et_Email.getText().toString().trim());
                    nullInputEditText();
                    startActivity(accountIntent);
                }
            }
            private void nullInputEditText(){
                et_Email.setText(null);
                et_Password.setText(null);

            }
        });
    }/**/
    public class LoginUser extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings){
            String Email = strings[0];
            String Password = strings[1];
            return null;
        }
    }
}
