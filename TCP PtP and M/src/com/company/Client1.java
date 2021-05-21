package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Client1 {
    public static void main(String[] args) throws IOException {
        ServerSocket ss;
        Socket s;
        DataInputStream dis;
        DataOutputStream dos;
        int port = 6000;
        ss = new ServerSocket(port);
        while (true){
            try {
                s = ss.accept();
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                Thread t = new Chat(s, dis, dos);
                t.start();
                if (s.isConnected()) {
                    System.out.println("\n\t\t\t\t\t\tConnection with Client : Success\n");
                } else {
                    System.out.println("F A I L E D");
                }
            } catch (Exception e) {
                System.out.println("Socket could not be created " + e);
            }
        }
    }
}