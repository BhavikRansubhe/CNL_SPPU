package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
public class Chat extends Thread{
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    public Chat(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*************************** Register *************************");
        System.out.print("Name : ");
        String name = sc.nextLine();
        try {
            dos.writeUTF(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("**************************************************************");
        String otherClientName = null;
        try {
            otherClientName = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n-------------------------Conversation------------------------\n");
        while(true) {
            try {
                String send;
                String receive;
                System.out.print(name + " : ");
                send = sc.nextLine();
                if(send.toLowerCase().contains("bye"))
                {
                    dos.writeUTF(otherClientName + " : " + send);
                    System.out.println("\n-------------------------------------------------------------\n");
                    break;
                }
                dos.writeUTF(send);
                receive = dis.readUTF();
                if(receive.toLowerCase().contains("bye"))
                {
                    System.out.println(otherClientName + "; says bye!");
                    System.out.println("\n-------------------------------------------------------------\n");
                    break;
                }
                System.out.println(otherClientName + " : " + receive);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
