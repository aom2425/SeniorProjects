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

public class TCPHome {

    private String serverMessage;
    IpPort ipPort;
    public final String SERVERIP = ipPort.HOST;//ipPort.getHost(); //your computer IP address
    public final int SERVERPORT = ipPort.PORT;
    PrintWriter out;
    BufferedReader in;

    public JSONObject defineJSONObject() throws JSONException {
        JSONObject send_cred = new JSONObject();
        send_cred.put("Status", "GetItem");
        send_cred.put("Responsability", "");
        send_cred.put("Entry", "");
        return send_cred;
    }
    public void sendMessage(Socket socket) throws IOException {
        try {
            //send the message to the server
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.write(String.valueOf(defineJSONObject()));
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
    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            //Log.e("TCP Client", "C: Connecting...");
            Socket socket = new Socket(serverAddr, SERVERPORT);
            sendMessage(socket);
            receiveMessage(socket);

            Log.d("MyApp",serverMessage);

            socket.close();
        } catch (Exception e) {
            Log.e("TCP", "C: Error", e);
        }/**/
    }

}
