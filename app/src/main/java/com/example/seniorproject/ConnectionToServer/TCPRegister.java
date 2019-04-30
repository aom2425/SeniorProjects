package com.example.seniorproject.ConnectionToServer;

import android.util.Log;

import com.example.seniorproject.helper.IpPort;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TCPRegister {

    private String serverMessage;
    private IpPort ipPort;
    private final String SERVERIP = ipPort.HOST; //your computer IP address
    private final int SERVERPORT = ipPort.PORT;
    public String resp;
    PrintWriter out;
    BufferedReader in;

    public TCPRegister(String responsability){
        resp = responsability;
    }
    public TCPRegister(){}

    public JSONObject defineJSONObject(String Name, String Email, String Password) throws JSONException {
        JSONObject send_cred = new JSONObject();
        send_cred.put("Status", "Register");
        //Log.d("RESPONSABILITY", resp);
        send_cred.put("Responsability",  resp);
        send_cred.put("UUID", "");
        send_cred.put("Name", Name);
        send_cred.put("Email", Email);
        send_cred.put("Password", Password);
        send_cred.put("Entry", "");
        return send_cred;
    }
    public void run(String Name, String Email, String Password) {
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            Log.e("TCP Client", "C: Connecting...");
            Socket socket = new Socket(serverAddr, SERVERPORT);

            //Hash password to send
            HandleMd5 hmd5 = new HandleMd5(Password);
            Password = hmd5.md5(Password);
            Log.d("Password", Password);

            sendMesage(socket, Name, Email, Password);
            receiveMessage(socket);
            Log.d("MyApp",serverMessage);
            socket.close();
        } catch (Exception e) {
            Log.e("TCP", "C: Error", e);
        }/**/
    }
    public void sendMesage(Socket socket, String name, String email, String password)throws IOException {
        try {
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.write(String.valueOf(defineJSONObject(name, email, password)));
            out.flush();
        } catch (Exception e){
            Log.e("TCP", "S: Error", e);
        }
    }
    public void receiveMessage(Socket socket) throws IOException{
        try{
            String line;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            if((line = in.readLine()) != null){
                Log.d("LINE FROM SERVER", line);
                serverMessage = line;
            }
        } catch (Exception e){
            Log.e("TCP", "R: Error", e);
        }
    }
}
