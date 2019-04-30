package com.example.seniorproject.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.seniorproject.ConnectionToServer.TCPClient;
import com.example.seniorproject.ConnectionToServer.TCPSendRestData;
import com.example.seniorproject.R;
import com.example.seniorproject.activty.MainActivity;

import java.util.List;

public class RestaurantPostFragment extends Fragment {

    EditText restaurant_name, restraurant_dis;
    Button btnAddImage, btnPost;
    private TCPClient mTcpClient;
    private TCPSendRestData mtcpSendRestData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View postFrag = inflater.inflate(R.layout.fragment_restaurant_post, container, false);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
                Log.e("Error"+Thread.currentThread().getStackTrace()[2],paramThrowable.getLocalizedMessage());
            }
        });/**/
        initButtons(postFrag);
        initPost(postFrag);
        return postFrag;
    }
    public void initButtons(View postFrag){
        restaurant_name = (EditText) postFrag.findViewById(R.id.name_id);
        restraurant_dis = (EditText) postFrag.findViewById(R.id.discription_id);
        btnAddImage = (Button) postFrag.findViewById(R.id.add_pic);
        btnPost = (Button) postFrag.findViewById(R.id.id_post);
    }
    public void initPost(View postFrag){
        btnPost.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String Name = restaurant_name.getText().toString();
                Log.d("TAG NAME", Name);
                String Discription = restraurant_dis.getText().toString();
                Log.d("TAG DISC", Discription);
                new sendRestPost().execute(Name, Discription);
                nullInputEditText();
            }
            private void nullInputEditText(){
                restaurant_name.setText(null);
                restraurant_dis.setText(null);
            }/**/
        });
    }
    public class AssyncHandler extends AsyncTask<String, Void, String>{
        public AssyncHandler() {
            super();
        }

        @Override
        protected String doInBackground(String... params) {
            String Name = params[0].toString();
            String Discription = params[1].toString();

            //String discription = restraurant_dis.getText().toString();
            Log.d("NAME", Name);
            Log.d("DISCRIPTION", Discription);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public class sendRestPost extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String Name = params[0];
            String Discription = params[1];
            //Log.d("TAG TCPC UUID", mTcpClient.uuid);
            Log.d("TAG IN POST", Name);
            List<String> test;
            //Get intent try getting mainActivity intent
            //new mTCPClient(new TCPClient().receiveMessage())

            mtcpSendRestData = new TCPSendRestData(Name, Discription);
            mtcpSendRestData.run();
            return null;
        }
    }
}
