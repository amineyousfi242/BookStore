package com.app.notespro;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class MyThread implements Runnable{
    private String msg;

    Socket s;

    @Override
    public void run() {
        try {
            System.out.println(msg);

            Socket socket = new Socket(  "192.168.1.104", 400);


            OutputStream os = socket.getOutputStream();

            PrintWriter pw = new PrintWriter(os, true);

            pw.println(msg);
            System.out.println("diffuse");



            InputStream is = socket.getInputStream();
            InputStreamReader isr= new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            msg = br.readLine();


            socket.close();

        }
        catch(Exception ex) {
            System.out.println(ex.toString());
        }
    }
    public void sendMessage(String msg){
        this.msg = msg;
        run();
    }
    public String getMessage(){
        return msg;
    }




}