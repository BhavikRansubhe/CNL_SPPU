package com.company;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class Client {

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private int[] receivedArray;
    private static  int indexReceived=0;

    Client(){
        try {
            Socket socket = new Socket("localhost", 3000);
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());
            if(socket.isConnected()){
                System.out.print("\nConnected To Server\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            receiveData();
        }catch (SocketException s){
            showData();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void receiveData() throws IOException {
        int frames=dataInputStream.readInt();
        receivedArray=new int[frames];
        boolean stat=true;
        int index;
        do{
            try {
                System.out.print("\n\nACKNOWLEDGEMENTS :");
                System.out.print("\n---------------------------:");
                for(int i=0;i<frames;i++) {
                    index = dataInputStream.readInt();
                    receivedArray[indexReceived] = dataInputStream.readInt();
                    indexReceived++;
                    sendAcknowledgement(index);
                }
                receiveUnsentFrames();
            }catch (ArrayIndexOutOfBoundsException a){
                stat=false;
            }

        }while (stat);
    }

    void sendAcknowledgement(int index) throws IOException {
        dataOutputStream.writeInt(index);
        System.out.print("\nSent Acknowledgement For Frame "+index);
    }
    void receiveUnsentFrames() throws IOException {
        System.out.println();
        System.out.print("\n\nReceiving Failed Frames :");
        System.out.print("\n---------------------------:");
        int recv,frameResent;
        recv=dataInputStream.readInt();
        if(recv!=-1) {
            System.out.print("\nReceived Frame " + recv);
            frameResent = dataInputStream.readInt();
            indexReceived++;
            try {
                receivedArray[indexReceived] = frameResent;
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
            sendAcknowledgement(recv);
        }
    }
    void showData(){
        System.out.println();
        System.out.print("\nRECEIVED FRAMES:");
        System.out.print("\n---------------------------:");
        for(int i=0;i< receivedArray.length;i++){
            System.out.print("\nFrame "+i+" : "+ receivedArray[i]);
        }
    }
    public static void main(String[] args){
        new Client();
    }
}