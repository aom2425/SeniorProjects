package com.example.seniorproject.ConnectionToServer;


import android.util.Log;

import java.io.*;
import java.net.*;
import java.lang.Object;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.*;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.JSONParser;


public class ConnectClient {

    private final String HOST = "192.168.1.6";
    private final int PORT = 8001;

    public void connectToServer(String email, String password) throws IOException {
    //static public void main(String args[]){
        InetAddress serverAdress = InetAddress.getByName(HOST);
        Log.e("TCP Client", "Connecting");
        Socket socket;
        socket = new Socket(serverAdress, PORT);


    }/**/
    public JSONObject defineJSONObject(String Email, String Password) throws JSONException {
        JSONObject send_cred = new JSONObject();
        send_cred.put("Email", Email);
        send_cred.put("Password", Password);
        send_cred.put("Entry", "lognin");
        return send_cred;
    }
    public boolean checkResponse(String inMsg){
        boolean flag = false;
        if(inMsg == "veikia"){
            flag = true;
        }
        return flag;
    }
    public boolean checkUsers(String email, String password) {
        //if (connectToServer(email, password) == true){
        //    return true;
        //}
        //else {
        //    return false;
        //}
        return true;
    }
}
/**/

/*
//DataOutputStream dOutputStr = new DataOutputStream(sock.getOutputStream());
//dOutputStr.write("Hello");
//dOutputStr.flush();
//dOutputStr.close();
//sock.close();
        }/* catch(Exception e) {
            e.printStackTrace();
        }/**/
 /*JSONObject json = new JSONObject();
            try{
                json.put("et_Email", "grantas@test.com");
                json.put("et_password", "grantas");
            } catch (JSONException e){
                e.printStackTrace();
            }
            System.out.print(json);
            //BufferedReader in = new BufferedReader(new InputStreamReader(is));
            //JSONObject json = new JSONObject(in.readLine());
            if(!json.has("result")) {
                System.out.println(noReset);
                result = false;
            }
            /**/
 /*

  public boolean connectToServer(String email, String password) {
    //static public void main(String args[]){

        String Login_sql = "select * from users where email=%s and password=%s";
        //String Login_sql = "select * from users";

        //String email = "grantas@test.com";
        //String password = "grantas";
        Map<String, String> dictionary = new HashMap<String,String>();
        //new stuff trying with JSON
        String reply;
        String noReset = "Could not reset.";
        String reset = "The server has been reset.";

        try {
            Socket sock = null;
            sock = new Socket("127.0.0.1", 8001);

            //dictionary.put("sql", Login_sql);
            //dictionary.put("email", email);
            //dictionary.put("password", password);
            //System.out.print(dictionary);

            //PrintWriter printWriter;
            //printWriter = new PrintWriter(sock.getOutputStream(), true);
            //printWriter.write(String.valueOf(send_cred));
            //printWriter.flush();
            try (OutputStreamWriter send = new OutputStreamWriter(sock.getOutputStream(), StandardCharsets.UTF_8)){
                send.write(defineJSONObject(email, password).toString());
            }
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            reply = inFromServer.readLine();
            //String str = reply;

            JSONObject json = new JSONObject(reply.toString());
            String flag = json.getString("success");
            if(flag =="True"){
                return true;
            }
            //System.out.print(flag);
            //printWriter.close();
        }
        catch (IOException e) {
            System.out.println(noReset);
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
                //System.out.println(reset);
                }  */

 /*
 String Login_sql = "select * from users where email=%s and password=%s";
        //String Login_sql = "select * from users";
        //String email = "grantas@test.com";
        //String password = "grantas";
        Map<String, String> dictionary = new HashMap<String,String>();
        String reply;
        String noReset = "Could not reset.";
        String reset = "The server has been reset.";

        try {
            Socket sock = new Socket("127.0.0.1", 8001);

            //String send_cred = "sending it";
            //PrintWriter printWriter;
            //printWriter = new PrintWriter(sock.getOutputStream(), true);
            //printWriter.write(String.valueOf(send_cred));
            //printWriter.flush();




            /*try (OutputStreamWriter send = new OutputStreamWriter(sock.getOutputStream(), StandardCharsets.UTF_8)){
                send.write(defineJSONObject(email, password).toString());
            }
//BufferedReader inFromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
//reply = inFromServer.readLine();
//String str = reply;

//JSONObject json = new JSONObject(reply.toString());
//String flag = json.getString("success");
//if(flag =="True"){
//    return true;
//}
        catch (IOException e) {
        System.out.println(noReset);
        e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        //return false;
        //System.out.println(reset);
  /**/
