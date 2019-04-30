package com.example.seniorproject.ConnectionToServer;

import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.util.Log;

import com.example.seniorproject.activty.RegisterActivity;
import com.example.seniorproject.helper.GrabUUID;
import com.example.seniorproject.helper.IpPort;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.PasswordAuthentication;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class TCPClient {

    RegisterActivity registerActivity;

    private String serverMessage;
    IpPort ipPort;
    public final String SERVERIP = ipPort.HOST;//ipPort.getHost(); //your computer IP address
    public final int SERVERPORT = ipPort.PORT;
    //private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;
    public String uuid;
    private List<String> ll;
    PrintWriter out;
    BufferedReader in;
    /*public TCPClient(OnMessageReceived listener) {
        mMessageListener = listener;
    }/**/

    public TCPClient() {
        this.ll = new ArrayList<String>();
    }
    public void setll(List<String> maybe){
        this.ll = maybe;
    }
    public List<String> getll(){
        return this.ll;
    }
    //Lets try sending packets
    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
        }
    }/**/

    public JSONObject defineJSONObject(String Email, String Password) throws JSONException {
        JSONObject send_cred = new JSONObject();
        send_cred.put("Status", "Login");
        send_cred.put("Responsability", "");
        send_cred.put("UUID", "");
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

            //Hash password to send
            HandleMd5 hmd5 = new HandleMd5(Password);
            Password = hmd5.md5(Password);
            Log.d("Password", Password);

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
    public String receivedMessage(Socket socket) throws IOException {
        String serverResponse = "";
        try {
            String line;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            //in this while the client listens for the messages sent by the server
            if ((line = in.readLine()) != null) {
                Log.d("LINE From Server", line);
                serverResponse = line;
            }
        }catch (Exception e){
            Log.e("TCP", "R: Error", e);
        }
        Log.d("RECEIVE From Server",serverResponse);
        return serverResponse;
    }
    public List<String> accessApp(){
        List<String> list = new ArrayList<String>();
        if (serverMessage != null){
            Log.d("AUTHENTICATE", serverMessage);
            try {
                JSONObject chek_auth = new JSONObject(serverMessage);
                list.add((String) chek_auth.get("Responsability"));
                list.add((String) chek_auth.get("UUID"));
                list.add((String) chek_auth.get("Email"));
                list.add((String) chek_auth.get("Entry"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Access App list", String.valueOf(list));
            uuid = list.get(1);
            setll(list);
            //GrabUUID grabUUID = null;
            //grabUUID.gList = new ArrayList<String>(list);
            //Log.d("grab UUID", String.valueOf(grabUUID.gList));

        }
        return list;
    }

    public int mesageSize(){
        return accessApp().size();
    }
    /*public interface OnMessageReceived {
        public void messageReceived(String message);
    }/**/
}


