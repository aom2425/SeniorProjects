package com.example.seniorproject.activty;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seniorproject.helper.Client;
import com.example.seniorproject.ConnectionToServer.ConnectClient;
import com.example.seniorproject.ConnectionToServer.TCPClient;
import com.example.seniorproject.R;
import com.example.seniorproject.UserSession.SessionManager;
import com.example.seniorproject.helper.InputValidation;

import java.net.ServerSocket;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Client client = new Client();
    EditText et_Email, et_Password;
    TextView tvRegister;
    Button btnLogin;
    ConnectClient connectClient;
    InputValidation inputValidation;
    private SessionManager session;
    private Context mContext;

    private static final String HOST = "192.168.1.6";
    private static final int PORT = 8001;
    private ServerSocket serverSocket;
    private ArrayList<String> arrayList;
    private TCPClient mTcpClient;


    private final AppCompatActivity activity = MainActivity.this;
    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Error"+Thread.currentThread().getStackTrace()[2],paramThrowable.getLocalizedMessage());
            }
        });
        initViews();

        initListenerLogin();
        //----------------------------------
        initListenerRegister();
    }

    private void initViews(){
        et_Email = (EditText) findViewById(R.id.et_email);
        et_Password = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        tvRegister = (TextView) findViewById(R.id.tv_register);
    }
    private void initListenerRegister() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
    private void initListenerLogin(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInUser();
                nullInputEditText();
            }
            private void nullInputEditText(){
                et_Email.setText(null);
                et_Password.setText(null);
            }/**/
        });
    }
    public void logInUser() {
        Log.d(TAG, "Login");
        new connectTask().execute("");
        if(!validate()){
            failedLogIn();
            return;
        }/**/
        btnLogin.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, R.style.Theme_AppCompat_Light);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating!");
        progressDialog.show();/**/
        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
        startActivity(intent);
        //TODO: verify if the user is right and go into Dashboard
    }
    public void loginCheck(final String Email, final String Password){
        String tag_aut = "login_rec";
    }
    public void failedLogIn() {
        Toast.makeText(getBaseContext(), "Login Failed!", Toast.LENGTH_LONG).show();
        btnLogin.setEnabled(true);
    }
    public void successLogIn(){
        //TODO: Toast set to success -> true and make a sucess method where it send to server
    }

    public boolean validate(){
        boolean valid = true;
        String Email = et_Email.getText().toString();
        String Password = et_Password.getText().toString();
        if (Email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            et_Email.setError("Not valid Email");
            valid = false;
        }
        else{
            et_Email.setError(null);
        }
        if(Password.isEmpty() || Password.length() < 4 || Password.length() > 10){
            et_Password.setError("Not valid Password");
            valid = false;
        }
        else{
            et_Password.setError(null);
        }
        return valid;
    }
    public  class connectTask extends AsyncTask<String,String,TCPClient>{
        @Override
        protected TCPClient doInBackground(String... strings) {
            mTcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
                @Override
                public void messageReceived(String message) {
                    publishProgress(message);
                }
            });
            String Email = et_Email.getText().toString();
            String Password = et_Password.getText().toString();
            mTcpClient.run(Email, Password);
            return null;
        }

        /*String msg = "Wrong username or password";
        new AlertDialog.Builder(this.context).setTitle("Error in login").setMessage(msg).setOnDismissListener(new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {

        }
        }).show();/**/
    }

}
