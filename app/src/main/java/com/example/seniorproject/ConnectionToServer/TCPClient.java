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

public class TCPClient {

    private String serverMessage;
    public static final String SERVERIP = "192.168.1.6"; //your computer IP address
    public static final int SERVERPORT = 8001;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    PrintWriter out;
    BufferedReader in;

    // Constructor
    /*public TCPClient(){

    }/**/
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
        in = null;
        out = null;
        serverMessage = null;
    }
    public JSONObject defineJSONObject(String Email, String Password) throws JSONException {
        JSONObject send_cred = new JSONObject();
        send_cred.put("Email", Email);
        send_cred.put("Password", Password);
        send_cred.put("Entry", "");
        return send_cred;
    }
    public void run(String Email, String Password) {
        mRun = true;
        try {
            //here you must put your computer's IP address.
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            //Log.e("TCP Client", "C: Connecting...");
            //create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, SERVERPORT);
            Log.d("MyApp","I am here");
            sendMessage(socket, Email, Password);
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
            receiveMessage(socket);


            //out.write(String.valueOf("select email, password from users"));
            //receiveMessage(socket);
            /*in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //in this while the client listens for the messages sent by the server
            //while (true) {
            serverMessage = in.readLine();
            Log.d("Message From Server", serverMessage.toString());
            //if (serverMessage != null && mMessageListener != null) {
                //call the method messageReceived from MyActivity class
            //    mMessageListener.messageReceived(serverMessage);
            //}
            serverMessage = null;
         //}/**/
            //receive the message which the server sends back

        } catch (Exception e) {
            Log.e("TCP", "S: Error", e);
        } finally {
            //the socket must be closed. It is not possible to reconnect to this socket
            // after it is closed, which means a new socket instance has to be created.
            socket.close();
        }
    }
    public void receiveMessage(Socket socket) throws IOException{
        try {
            String line;
            StringBuilder sb = new StringBuilder();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            Log.d("Receive Message", "Do I even Go to Receive Message");
            Log.d("Message From Server", in.toString());

            //in this while the client listens for the messages sent by the server
            while ((line = in.readLine()) != null) {
                sb.append(line).append('\n');
            }
            Log.d("MESSAGE", sb.toString());

            //String msg = in.readLine();
            //SserverMessage = in.readLine();

            //Log.d("Message From Server", msg.toString());
            /*if (serverMessage != null && mMessageListener != null) {
            //call the method messageReceived from MyActivity class
                mMessageListener.messageReceived(serverMessage);
            }
            serverMessage = null;/**/
            //}/**/
            //receive the message which the server sends back
            //Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");

        }catch (Exception e){
            Log.e("TCP", "S: Error", e);
        }finally {
            //the socket must be closed. It is not possible to reconnect to this socket
            // after it is closed, which means a new socket instance has to be created.
            socket.close();
        }
    }
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }/**/
}
