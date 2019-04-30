package com.example.seniorproject.activty;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.seniorproject.ConnectionToServer.TCPRegister;
import com.example.seniorproject.R;

public class RegisterRestaurant extends AppCompatActivity {

    private static final String TAG = "RegiserActivity";
    private static final String HOST = "192.168.1.6";
    private static final int PORT = 8001;
    private EditText et_Name, et_Email, et_Password;
    private Button btnRegister;
    private TCPRegister mTCPregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Error" + Thread.currentThread().getStackTrace()[2], paramThrowable.getLocalizedMessage());
            }
        });/**/
    }
    private void initViews(){
        et_Name = (EditText) findViewById(R.id.et_name);
        et_Email = (EditText) findViewById(R.id.et_reg_email);
        et_Password = (EditText) findViewById(R.id.et_reg_password);
        btnRegister = (Button) findViewById(R.id.button);
    }
    private void initListeners(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = et_Name.getText().toString();
                String Email = et_Email.getText().toString();
                String Password = et_Password.getText().toString();
                new RegisterUser().execute(Name, Email, Password);
                //nullInputEditText();
                Intent i = new Intent(RegisterRestaurant.this, MainActivity.class);
                startActivity(i);
            }
            private void nullInputEditText(){
                et_Email.setText(null);
                et_Password.setText(null);
            }/**/
        });
    }/**/
    public void failedLogIn() {
        Toast.makeText(getBaseContext(), "Login Failed!", Toast.LENGTH_LONG).show();
        btnRegister.setEnabled(true);
    }/**/
    public boolean isValidEmail(){
        boolean valid = true;
        String Email = et_Email.getText().toString();
        if (Email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            et_Email.setError("Not valid Email");
            valid = false;
        }
        else{
            et_Email.setError(null);
        }
        return valid;

    }
    public void registerUser(){
        Log.d(TAG, "Login");
        if(!isValidEmail()){
            failedLogIn();
            return;
        }
        btnRegister.setEnabled(false);
        //verifyRegister();
    }/**/
    public class RegisterUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String Name = params[0].toString();
            String Email = params[1].toString();
            String Password = params[2].toString();
            //String Name = et_Name.getText().toString();
            //String Email = et_Email.getText().toString();
            //String Password = et_Password.getText().toString();
            mTCPregister = new TCPRegister();
            mTCPregister.run(Name, Email, Password);
            return null;
        }
    }
}
