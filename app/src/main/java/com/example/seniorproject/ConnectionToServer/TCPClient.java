package com.example.seniorproject.ConnectionToServer;

import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.PasswordAuthentication;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPClient {

    private String serverMessage;
    public static final String SERVERIP = "192.168.1.6"; //your computer IP address
    public static final int SERVERPORT = 8001;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    PrintWriter out;
    BufferedReader in;

    public TCPClient(OnMessageReceived listener) {
        mMessageListener = listener;
    }/**/
    //Lets try sending packets
    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
        }
    }/**/
    public void stopClient() {

        // send mesage that we are closing the connection
        sendMessage(Constants.CLOSED_CONNECTION + "Kazy");

        mRun = false;

        if (out != null) {
            out.flush();
            out.close();
        }

        mMessageListener = null;
        //in = null;
        out = null;
        serverMessage = null;
    }
    public JSONObject defineJSONObject(String Email, String Password) throws JSONException {
        JSONObject send_cred = new JSONObject();
        send_cred.put("Status", "Login");
        send_cred.put("Email", Email);
        send_cred.put("Password", Password);
        send_cred.put("Entry", "");
        return send_cred;
    }
    public void run(String Email, String Password) {
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            //Log.e("TCP Client", "C: Connecting...");
            Socket socket = new Socket(serverAddr, SERVERPORT);
            sendMessage(socket, Email, Password);
            receiveMessage(socket);
            Log.d("MyApp",serverMessage);
            int size_json_rec = accessApp().size();
            Log.d("ACCESSS",accessApp().get(size_json_rec-1));
            socket.close();
        } catch (Exception e) {
            Log.e("TCP", "C: Error", e);
        }/**/
    }

    public void sendMessage(Socket socket, String Email, String Password) throws IOException{
        try {
            //send the message to the server
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.write(String.valueOf(defineJSONObject(Email,Password)));
            out.flush();
            //commented out nr 1
        } catch (Exception e) {
            Log.e("TCP", "S: Error", e);
        }
    }
    public void receiveMessage(Socket socket) throws IOException{
        String serverResponse = "";
        try {
            String line;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            //Log.d("RECEIVE From Server", in.toString());
            //in this while the client listens for the messages sent by the server
            if((line = in.readLine()) != null) {
                Log.d("LINE From Server", line);
                serverMessage = line;
            }
            Log.d("MESSAGE", serverMessage);

        }catch (Exception e){
            Log.e("TCP", "R: Error", e);
        }
            //the socket must be closed. It is not possible to reconnect to this socket
            // after it is closed, which means a new socket instance has to be created.
    }
    public List<String> accessApp(){
        Log.d("AUTHENTICATE", serverMessage);
        List<String> list = new ArrayList<String>();
        try {
            JSONObject chek_auth = new JSONObject(serverMessage);
            list.add((String) chek_auth.get("Email"));
            list.add((String) chek_auth.get("Entry"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }/**/
}


