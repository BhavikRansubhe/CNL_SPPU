package com.company;



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Sender {


    private DataOutputStream dataOutputStream;
    private static Scanner scanner=new Scanner(System.in);
    Socket socket;

    Sender(){

        try {
            ServerSocket serverSocket = new ServerSocket(3000);
            socket = serverSocket.accept();
            dataOutputStream=new DataOutputStream(socket.getOutputStream());
            if(socket.isConnected()){
                System.out.print("\nClient Connected");
            }
            while (true) {
                sendData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendData() throws IOException {

        System.out.print("Select The Method :\n1.Char With Spaces\n2.String At A Time\n3.Sentence At A Time\n4.File");
        int ch=scanner.nextInt();
        switch (ch){
            case 1:scanner.nextLine();
                System.out.print("Enter Characters with spaces :");
                String ip=scanner.nextLine();
                dataOutputStream.writeInt(1);
                dataOutputStream.writeInt(ip.length());

                for(int i=0;i<ip.length();i++){
                    dataOutputStream.writeUTF(String.valueOf(ip.charAt(i)));
                }

                break;

            case 2:scanner.nextLine();
                System.out.print("Enter String :");
                String ip1=scanner.nextLine();
                String[] str=ip1.split(" ");
                dataOutputStream.writeInt(2);
                dataOutputStream.writeInt(5);
                sendBuffer(getCountFrame(str));
                dataOutputStream.writeInt(2);
                break;

            case 3:scanner.nextLine();
                System.out.print("Enter Sentences with \".\" :");
                String ip2=scanner.nextLine();
                String[] str1=ip2.split(Pattern.quote("."));
                dataOutputStream.writeInt(3);
                dataOutputStream.writeInt(5);
                sendBuffer(getCountFrame(str1));
                dataOutputStream.writeInt(2);
                break;

            case 4:
                dataOutputStream.writeInt(4);
                dataOutputStream.writeInt(6);
                sendFile();
                dataOutputStream.writeInt(2);
        }

    }

    private void sendFile(){
        try {
            byte b[]=new byte[10000];
            byte send[][]=new byte[100][10000];
            InputStream in = new FileInputStream(new File("D:\\sendFile.txt"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            String outData=out.toString();
            String str[]=outData.split(Pattern.quote(" "));
            String op=getCountFrame(str);

            OutputStream outputStream = socket.getOutputStream();

            for(int i=0;i<op.length();i++){
                if(Character.isDigit(op.charAt(i))){
                    StringBuilder sendB = new StringBuilder("");
                    int l=Integer.parseInt(String.valueOf(op.charAt(i)));
                    for(int j=i+1;j<l+i+1;j++){
                        sendB.append(op.charAt(j));
                    }
                    send[i]=sendB.toString().getBytes();
                    dataOutputStream.writeInt(1);
                    outputStream.write(send[i],0,send[i].length);
                }
            }
            System.out.println("\nFile sent Successfully!");
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    private void sendBuffer(String frame) throws IOException {
        for(int i=0;i<frame.length();i++){
            if(Character.isDigit(frame.charAt(i))){
                StringBuilder sendB = new StringBuilder("");
                int l=Integer.parseInt(String.valueOf(frame.charAt(i)));
                for(int j=i+1;j<l+i+1;j++){
                    sendB.append(frame.charAt(j));
                }
                dataOutputStream.writeInt(1);
                dataOutputStream.writeUTF(sendB.toString());
            }
        }
    }


    private String getCountFrame(String[] arr){
        StringBuilder frame = new StringBuilder("");
        for (String s : arr) {
            frame.append(s.length());
            frame.append(s);
        }
        return frame.toString();
    }


    public static void main(String[] args){
        new  Sender();
    }
}
