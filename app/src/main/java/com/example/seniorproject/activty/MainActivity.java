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

import com.example.seniorproject.ConnectionToServer.HandleMd5;
import com.example.seniorproject.activty.Restaurant.RestaurantActivity;
import com.example.seniorproject.fragments.RestaurantPostFragment;
import com.example.seniorproject.helper.Client;
import com.example.seniorproject.ConnectionToServer.ConnectClient;
import com.example.seniorproject.ConnectionToServer.TCPClient;
import com.example.seniorproject.R;
import com.example.seniorproject.UserSession.SessionManager;
import com.example.seniorproject.helper.InputValidation;

import java.net.ServerSocket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  {

    static EditText et_Email;
    static EditText et_Password;
    TextView tvRegister;
    Button btnLogin;

    private static final String HOST = "192.168.1.4";
    private static final int PORT = 8001;
    private static TCPClient mTcpClient;
    private final AppCompatActivity activity = MainActivity.this;
    private static final String TAG = "LoginActivity";
    public static String uuid;
    public static List<String> mList = null;

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
                Intent i = new Intent(MainActivity.this, UserResponsabilities.class);
                startActivity(i);
            }
        });
    }
    private void initListenerLogin(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInUser();
                Log.d("TAG mLIST", String.valueOf(mList));
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
        btnLogin.setEnabled(true);
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, R.style.Theme_AppCompat_Light);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating!");
        progressDialog.show();/**/

    }
    public void createCustommerDash(){
        Intent i = new Intent(MainActivity.this, DashboardActivity.class);
        startActivity(i);
    }
    public void createRestaurantDash(){
        Intent i = new Intent(MainActivity.this, RestaurantActivity.class);
        startActivity(i);
    }
    public  void verifyLogIn(){
        List<String> mList = mTcpClient.accessApp();
        int size_json_sz =  mList.size();
        String entry = "Email";
        Log.d("TAG list", String.valueOf(mList.get(0)));
        uuid = String.valueOf(mList.get(1));
        Log.d("Receiver size", String.valueOf(size_json_sz));
        String flag = mList.get(size_json_sz - 1);
        if (Boolean.valueOf(flag)) {
            if(String.valueOf(mList.get(0)).equals("Custommer")|| String.valueOf(mList.get(0)).equals("Cyclist")){
                createCustommerDash();
            }
            else {
                createRestaurantDash();
            }
            //

        } else {
            String msg = "Wrong username or password";
            OpenDialogMsg openDialogMsg = new OpenDialogMsg();
            openDialogMsg.show(getSupportFragmentManager(), "Error Message");
        }
    }

    public void failedLogIn() {
        Toast.makeText(getBaseContext(), "Login Failed!", Toast.LENGTH_LONG).show();
        btnLogin.setEnabled(true);
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

    public class connectTask extends AsyncTask<String,String,String>{

        List<String> list;
        Bundle args = new Bundle();

        /*public connectTask(List<String> a){
            a = this.list;
        }/**/
        @Override
        /*protected TCPClient doInBackground(String... strings) {
            mTcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
                @Override
                public void messageReceived(String message) {
                    publishProgress(message);
                }
            });/**/
        //protected TCPClient doInBackground(String... strings) {
        protected String doInBackground(String... strings) {
            mTcpClient = new TCPClient();/**/

            String Email = et_Email.getText().toString();
            String Password = et_Password.getText().toString();
            //String hashToDb = BCrypt.hashpw(Password, BCrypt.getsalt());
            //HandleMd5 hmd5 = new HandleMd5(Password);

            mTcpClient.run(Email, Password);
            Log.d("TAG SIZE", String.valueOf(mTcpClient.mesageSize()));
            verifyLogIn();

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }/**/

        public void test(){
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
