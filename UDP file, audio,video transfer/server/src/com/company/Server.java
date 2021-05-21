package com.company;

import java.io.*;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress;

import java.net.SocketException;

import java.util.Scanner;


public class Server {



    static DatagramSocket serverSocket;

    static byte[] sendData;

    static byte[] receiveData;

    private static InetAddress ip;



    public Server(){



        try {

            serverSocket = new DatagramSocket(6000);

            ip = InetAddress.getLocalHost();

            sendData = new byte[1024];

            receiveData = new byte[1024];

            System.out.println("Client connected");




        }catch (Exception e){

            System.out.println("Socket could not be connected");

        }



    }



    public static void main(String[] args) throws IOException, InterruptedException {



        Server server = new Server();

        char doYouWantToContinue;



        Scanner sc = new Scanner(System.in);



        do {

            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);

            serverSocket.receive(receivePacket);

           String userChoice = new  String(receivePacket.getData(),receivePacket.getOffset(),receivePacket.getLength());





            if (userChoice.equals("a")) {

                server.sendAFile();

            } else if (userChoice.equals("b")) {

                server.sendASong();

            } else if (userChoice.equals("c")) {

                server.sendAVideo();

            } else {

                System.out.println("\nOops! Try again!!!\n");

            }



            System.out.print("\nDo you want to continue ? \nAns : ");

            doYouWantToContinue = sc.next().charAt(0);



        }while(doYouWantToContinue == 'y' || doYouWantToContinue == 'Y');

    }


    private void sendAFile() throws IOException, InterruptedException {



        byte[] b = new byte[10000];

        FileInputStream fos=new FileInputStream("C:\\Users\\bhavi\\Desktop\\CNL\\UCP file transfer\\To Client.txt");

        int i=0;

        System.out.println("\n\nSending...");

        Thread.sleep(2000);

        while(fos.available()!=0)

        {

            b[i]=(byte)fos.read();

            i++;

        }

        fos.close();

        serverSocket.send(new DatagramPacket(b,i,InetAddress.getLocalHost(),1000));



        System.out.println("\nFile sent Successfully!");

    }



    private void sendASong() throws IOException, InterruptedException {



        File myFile = new File("C:\\Users\\bhavi\\Desktop\\CNL\\UCP file transfer\\song.wav");



        BufferedInputStream bis = null;

        try {

            DatagramSocket audioSocket = new DatagramSocket();

            DatagramPacket dp;

            int packetsize = 1024;

            double nosofpackets;

            nosofpackets = Math.ceil(((int) myFile.length()) / packetsize);



            bis = new BufferedInputStream(new FileInputStream(myFile));

            for (double i = 0; i < nosofpackets + 1; i++) {

                byte[] mybytearray = new byte[packetsize];

                bis.read(mybytearray, 0, mybytearray.length);

                System.out.println("Packet:" + (i + 1));

                dp = new DatagramPacket(mybytearray,mybytearray.length, InetAddress.getLocalHost(), 2000);

                Thread.sleep(10L);

                audioSocket.send(dp);

            }



            System.out.println("\nFile sent Successfully!");



            bis.close();

            audioSocket.close();



        } catch (IOException e) {

            e.printStackTrace();

        } catch (InterruptedException e) {

            e.printStackTrace();

        }



    }



    private void sendAVideo() throws IOException, InterruptedException {



        File myFile = new File("C:\\Users\\bhavi\\Desktop\\CNL\\UCP file transfer\\video.mp4");



        BufferedInputStream bis = null;

        try {

            DatagramSocket videoSocket = new DatagramSocket();

            DatagramPacket dp;

            int packetsize = 1024;

            double nosofpackets;

            nosofpackets = Math.ceil(((int) myFile.length()) / packetsize);



            bis = new BufferedInputStream(new FileInputStream(myFile));

            for (double i = 0; i < nosofpackets + 1; i++) {



                byte[] mybytearray = new byte[packetsize];

                bis.read(mybytearray, 0, mybytearray.length);

                System.out.println("Packet:" + (i + 1));

                dp = new DatagramPacket(mybytearray,mybytearray.length, InetAddress.getLocalHost(), 3000);

                Thread.sleep(10L);

                videoSocket.send(dp);

            }

            System.out.println("\nFile sent Successfully!");

            bis.close();

            videoSocket.close();



        } catch (IOException | InterruptedException e) {

            e.printStackTrace();

        }

    }

}
