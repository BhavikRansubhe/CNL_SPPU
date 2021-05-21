package com.company;
/*
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        Socket s = null;                    //client socket
        DataInputStream in = null;          //data input from socket
        DataOutputStream out = null;         //data output for socket


        try {

            Socket socket = new Socket("localhost", 8008);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());


            System.out.println(in.readUTF());
            out.writeUTF("\n Hello from client");
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) in.close();
            if (out != null) out.close();
        }
    }
}
*/
/*
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
public class Main {
    public static void main(String[] args) throws Exception{
        //Initialize socket
        Socket socket = new Socket(InetAddress.getByName("localhost"), 5000);
        byte[] contents = new byte[10000];
        //Initialize the FileOutputStream to the output file's full path.
        FileOutputStream fos = new FileOutputStream("d:\\file2.txt");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        InputStream is = socket.getInputStream();
        //No of bytes read in one read() call
        int bytesRead = 0;
        while((bytesRead=is.read(contents))!=-1)
            bos.write(contents, 0, bytesRead);
        bos.flush();
        socket.close();
        System.out.println("File saved successfully!");
    }
}
*/
/*
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        Socket s = null;                    //client socket
        DataInputStream in = null;          //data input from socket
        DataOutputStream out = null;         //data output for socket

        try {

            Socket socket = new Socket("localhost", 8008);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            while (true) {

                System.out.print("\nChoose Trigonometric operation :\n 1.sin\n 2.cos\n 3.tan\n 4.cot" +
                        "\n 5.sec\n 6.cosec\n 7.exit\n --->>>");
                int choice = sc.nextInt();

                if (choice < 7) {

                    System.out.print("\nEnter angle Degree:");
                    Double value = sc.nextDouble();
                    out.writeInt(choice);
                    out.writeDouble((Double) (value * 3.14 / 180));  //convert degree to radian
                    System.out.println("\nANS :" + in.readDouble());   //print ans from server

                } else {
                    out.writeInt('0');  //for end connection send y to server
                    sc.close();         //close all allocated resources
                    in.close();
                    out.close();
                    System.exit(0);  //exit program
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (s != null) s.close();
            if (in != null) in.close();
            if (out != null) out.close();
        }
    }
}
*/

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        InetAddress ip = InetAddress.getLocalHost();
        int port = 4444;
        Scanner sc = new Scanner(System.in);

        // Step 1: Open the socket connection.
        Socket s = new Socket(ip, port);

        // Step 2: Communication-get the input and output stream
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        while (true)
        {
            // Enter the equation in the form-
            // "operand1 operation operand2"
            System.out.print("Enter the equation in the form: ");
            System.out.println("'operand operator operand'");

            String inp = sc.nextLine();

            if (inp.equals("bye"))
                break;

            // send the equation to server
            dos.writeUTF(inp);

            // wait till request is processed and sent back to client
            String ans = dis.readUTF();
            System.out.println("Answer = " + ans);
        }
    }
}

