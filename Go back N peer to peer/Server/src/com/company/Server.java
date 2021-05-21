package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Server {

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private static Scanner scanner=new Scanner(System.in);
    private int window;
    private boolean[] receivedAck;

    Server(){
        try {
            ServerSocket serverSocket = new ServerSocket(3000);
            Socket socket = serverSocket.accept();
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());
            if(socket.isConnected()){
                System.out.print("\nClient Connected");
            }
            sendData();
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
    private void sendData() throws IOException{
        Random random = new Random();
        boolean stat = true;
        System.out.print("\n\nEnter Number of Frames :");
        int frames = scanner.nextInt();
        System.out.print("Enter the Window Size :");
        window = scanner.nextInt();
        receivedAck = new boolean[window];
        dataOutputStream.writeInt(frames);

        int rand = random.nextInt(frames);

        int[] arrayToBeSend = new int[frames];

        System.out.println("\nEnter The Data To Be Sent Sequentially :");
        for (int i = 0; i < frames; i++) {
            System.out.print("Enter Data for Frame "+i+" :");
            arrayToBeSend[i] = scanner.nextInt();
        }
        System.out.print("\nSending The Data :");
        System.out.print("\n---------------------------:");
        int offset = 0;
        do {
            try {
                for (int i = 0; i < window; i++) {
                    if ((i + offset) != rand) {
                        dataOutputStream.writeInt(i + offset);
                        dataOutputStream.writeInt(arrayToBeSend[i + offset]);
                        System.out.print("\nFrame " + (i + offset) + " Sent");
                        receiveAck(i + offset);
                    }
                }
                sendUnsentFrames(arrayToBeSend,offset);
                offset += window;
            }catch (ArrayIndexOutOfBoundsException e) {
                stat = false;
            }
        } while (stat);
    }

    private void receiveAck(int index) throws IOException {
        int received=dataInputStream.readInt();
        if(received==index){
            if(received >=window) {
                receivedAck[index-window] = true;
            }else{
                receivedAck[index] = true;
            }
        }
    }

    public void sendUnsentFrames(int[] array,int offset) throws IOException {
        int index=-1;
        for(int i=0;i<receivedAck.length;i++){
            if(!receivedAck[i]){
                index=i;
            }
        }
        System.out.print("\nSending Unsuccessful Frames");
        if(index!=-1){
            dataOutputStream.writeInt(index+offset);
            dataOutputStream.writeInt(array[index+offset]);
            System.out.print("\n\nFrame " + (index +offset)+ " Sent");
            receiveAck(index);
            System.out.print("\nReceived Acknowledgment for Frame " + (index+offset));
        }else{
            dataOutputStream.writeInt(-1);
        }
    }
    public static void main(String[] args)  {
        new Server();
    }

}