package com.company;

/*
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {

        Scanner sc=new Scanner(System.in);
        System.out.print("Server created");

        Socket s = null;                    //client socket
        ServerSocket ss = null;             //server socket object
        DataInputStream in = null;          //data input from socket
        DataOutputStream out= null;         //data output for socket


        try {

            ss=new ServerSocket(8008);  //create serversocket with port number 8008
            s=ss.accept();
            in=new DataInputStream(s.getInputStream());
            out=new DataOutputStream(s.getOutputStream());


            out.writeUTF("Hi from server\n");   //send hi message to client
            out.flush();                            //flush all data to stream
            System.out.println(in.readUTF());       //read hi from client



        } catch (IOException e) {
            System.out.println(e);

        }finally {
            //close all allocated resource
            if(s!=null) s.close();
            if(ss!=null) ss.close();
            if(in!=null)in.close();
            if(out!=null)out.close();

        }

    }
}
*/
/*
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
public class Main
{
    public static void main(String[] args) throws Exception
    {
        //Initialize Sockets
        ServerSocket ssock = new ServerSocket(5000);
        Socket socket = ssock.accept();
        //The InetAddress specification
        InetAddress IA = InetAddress.getByName("localhost");

        //Specify the file
        File file = new File("d:\\file1.txt");
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        //Get socket's output stream
        OutputStream os = socket.getOutputStream();
        //Read File Contents into contents array
        byte[] contents;
        long fileLength = file.length();
        long current = 0;
        long start = System.nanoTime();
        while(current!=fileLength){
            int size = 10000;
            if(fileLength - current >= size)
                current += size;
            else{
                size = (int)(fileLength - current);
                current = fileLength;
            }
            contents = new byte[size];
            bis.read(contents, 0, size);
            os.write(contents);
            System.out.print("Sending file ... "+(current*100)/fileLength+"% complete!");
        }
        os.flush();
        //File transfer done. Close the socket connection!
        socket.close();
        ssock.close();
        System.out.println("File sent succesfully!");
    } }
*/
/*
import java.io.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Main {


    public static void main(String[] args) throws IOException {

        Scanner sc=new Scanner(System.in);
        System.out.print("Server created");

        Socket s = null;                    //client socket
        ServerSocket ss = null;             //server socket object
        DataInputStream in = null;          //data input from socket
        DataOutputStream out= null;         //data output for socket


        try {

            ss=new ServerSocket(8008);  //create serversocket with port number 8008
            s=ss.accept();
            in=new DataInputStream(s.getInputStream());
            out=new DataOutputStream(s.getOutputStream());

            int choice=in.readInt();          //read operation choice from client
            while(choice!='0')
            {
                out.writeDouble(Calculation(choice,in.readDouble()));
                choice=in.readInt();
            }

        } catch (IOException e) {
            System.out.println(e);

        }finally {
            //close all allocated resource
            if(s!=null) s.close();
            if(ss!=null) ss.close();
            if(in!=null)in.close();
            if(out!=null)out.close();

        }

    }
    //trignometric calculation
    static Double Calculation(int choice,Double value)
    {

        switch (choice)
        {
            case 1:
                System.out.print("\n Answer of sin value sent to client :");
                return Math.sin(value);
            case 2:
                System.out.print("\n Answer of cos value sent to client :");
                return Math.cos(value);

            case 3:
                System.out.print("\n Answer of tan value  sent to client :");
                return Math.tan(value);
            case 4:
                System.out.print("\n Answer of cot value  sent to client :");
                return 1/Math.tan(value);
            case 5:
                System.out.print("\n Answer of sec value sent to client :");
                return 1/Math.cos(value);

            case 6:
                System.out.print("\n Answer of cosec value sent to client :");
                return 1/Math.sin(value);
        }
        return -1.0;
    }
}
*/

import java.io.DataInputStream;
        import java.io.DataOutputStream;
        import java.io.IOException;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.StringTokenizer;

public class Main
{
    public static void main(String args[]) throws IOException
    {

        // Step 1: Establish the socket connection.
        ServerSocket ss = new ServerSocket(4444);
        Socket s = ss.accept();

        // Step 2: Processing the request.
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        while (true)
        {
            // wait for input
            String input = dis.readUTF();

            if(input.equals("bye"))
                break;

            System.out.println("Equation received: " + input);
            int result;

            // Use StringTokenizer to break the equation into operand and
            // operation
            StringTokenizer st = new StringTokenizer(input);

            int oprnd1 = Integer.parseInt(st.nextToken());
            String operation = st.nextToken();
            int oprnd2 = Integer.parseInt(st.nextToken());

            // perform the required operation.
            if (operation.equals("+"))
            {
                result = oprnd1 + oprnd2;
            }

            else if (operation.equals("-"))
            {
                result = oprnd1 - oprnd2;
            }
            else if (operation.equals("*"))
            {
                result = oprnd1 * oprnd2;
            }
            else
            {
                result = oprnd1 / oprnd2;
            }
            System.out.println("Sending the result...");

            // send the result back to the client.
            dos.writeUTF(Integer.toString(result));
        }
    }
}

