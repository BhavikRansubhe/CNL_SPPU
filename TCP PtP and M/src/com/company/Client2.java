package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
public class Client2 {
    public static void main(String[] args) {
        Socket s = null;
        InetAddress ip;
        DataInputStream dis;
        DataOutputStream dos;
        int port = 6000;
        try {
            ip = InetAddress.getLocalHost();
            s = new Socket(ip,port);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            System.out.println("\n\t\t\t\t\t\tConnection with Client : Success\n");
            Scanner sc = new Scanner(System.in);
            System.out.println("*************************** Register *************************");
            System.out.print("Name : ");
            String name = sc.nextLine();
            dos.writeUTF(name);
            System.out.println("**************************************************************");
            System.out.println("\n-------------------------Conversation------------------------\n");
            String otherClientName = dis.readUTF();
            while (true) {
                String send;
                String receive;
                receive = dis.readUTF();
                if(receive.toLowerCase().contains("bye"))
                {
                    System.out.println(otherClientName + "; says bye!");
                    s.close();
                    System.out.println("\n-------------------------------------------------------------\n");
                    break;
                }
                System.out.println(otherClientName + " : " + receive);
                System.out.print(name + " : ");
                send = sc.nextLine();
                if(send.toLowerCase().contains("bye"))
                {
                    dos.writeUTF(otherClientName + " : " + send);
                    s.close();
                    System.out.println("\n-------------------------------------------------------------\n");
                    break;
                }
                dos.writeUTF(send);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
