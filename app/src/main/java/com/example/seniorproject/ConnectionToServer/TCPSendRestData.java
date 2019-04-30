package com.example.seniorproject.ConnectionToServer;

import android.util.Log;

import com.example.seniorproject.helper.GrabUUID;
import com.example.seniorproject.helper.IpPort;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class TCPSendRestData {

    IpPort ipPort;
    public final String SERVERIP = ipPort.HOST;//ipPort.getHost(); //your computer IP address
    public final int SERVERPORT = ipPort.PORT;
    PrintWriter out;
    BufferedReader in;
    private String name;
    private String discription;

    public TCPSendRestData(String restN, String restD){
        name = restN;
        discription = restD;
    }

    //set json format
    public JSONObject defineJSONObject(String restName, String restDisc) throws JSONException {
        JSONObject send_cred = new JSONObject();
        send_cred.put("Status", "Insert");
        send_cred.put("UUID", "sendign");
        //send_cred.put("ImageN", imageName);
        //send_cred.put("Size", imageSize);
        send_cred.put("RestN", restName);
        send_cred.put("RestD", restDisc);
        return send_cred;
    }
    public void run(){
        try {
            Log.d("TAG IN POST", discription);
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            Socket socket = new Socket(serverAddr, SERVERPORT);
            //mTCPClient = new TCPClient();
            //List<String> list = mTCPClient.accessApp();
            //Log.d("STRING TCP", String.valueOf(mTCPClient.getll()));
            //Log.d("TCPCLIENT", String.valueOf(list));
            Log.d("TCP", name);
            Log.d("TCP", discription);
            sendMessage(socket,name,discription);
            socket.close();
        } catch (Exception e) {
            Log.e("TCP", "C: Error", e);
        }/**/

    }
    public void sendMessage(Socket socket, String restName, String restDisc) throws IOException {
        try {
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            out.write(String.valueOf(defineJSONObject(restName, restDisc)));
            Log.d("SEND MESSAGE", String.valueOf(out));
            out.flush();
        } catch (Exception e){
            Log.e("TCP", "S: Error", e);
        }
    }
}
