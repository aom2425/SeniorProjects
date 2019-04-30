package com.example.seniorproject.helper;

public class Client {

    private String name;
    private String uname;
    private String email;
    private String password;
    private String session;
    public String[] message = new String[2];

    public String getName() {   return name;}
    public void setName(String name) {  this.name = name; }
    public String getEmail() {  return email; }
    public void setEmail(String email) {    this.email = email; }
    public String getPassword() {   return password; }
    public void setPassword(String password) { this.password = password; }
    public String getSession() { return session; }
    public void setSession(String session) { this.session = session; }
    public String getUname() { return uname; }
    public void setUname(String uname) { this.uname = uname; }
    public String[] getMessage() { return message; }
    public void setMessage(String[] message) { this.message = message; }
}
