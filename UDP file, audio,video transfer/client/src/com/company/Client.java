package com.company;

import java.io.*;

import java.net.DatagramPacket; import java.net.DatagramSocket;
import java.net.InetAddress;

import java.util.Arrays;

import java.util.Scanner;



public class Client {
    
    static DatagramSocket clientSocket;

    static InetAddress ip;

    static byte[] sendData;

    static byte[] receiveData;
    
    public Client() {

        try {

            clientSocket = new DatagramSocket();

            ip = InetAddress.getLocalHost();

            sendData = new byte[1024];

            receiveData = new byte[1024];

        } catch (Exception e) {

            System.out.println("Socket could not be connected");

        }

    }


    public static void main(String[] args) throws IOException, InterruptedException {
        
        Client client = new Client();


        String choice;

        char doYouWantToContinue;



        Scanner sc = new Scanner(System.in);



        do {



            System.out.print("\na. Download a File\n"

                    + "b. Download a Song\n"

                    + "c. Download a Video\n"

                    + "Enter your choice : ");



            choice = sc.nextLine();

            sendData = choice.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, 6000);
            clientSocket.send(sendPacket);



            switch (choice) {



                case "a":

                    client.receiveAFile();

                    break;

                case "b":

                    client.receiveASong();

                    break;

                case "c":

                    client.receiveAVideo();

                    break;

                default:

                    System.out.println("\nOops! Try again!!!\n");
                    break;

            }

            System.out.print("\nDo you want to continue ? \nAns: ");

            doYouWantToContinue = sc.next().charAt(0);

            sc.nextLine();
            

        } while (doYouWantToContinue == 'y' || doYouWantToContinue == 'Y');


    }
    private void receiveAFile() throws IOException, InterruptedException {

        byte b[] = new byte[3072];

        String packet;

        DatagramSocket fileSocket = new DatagramSocket(1000);

        FileOutputStream fis = new FileOutputStream("C:\\Users\\bhavi\\Desktop\\CNL\\UCP file transfer\\file from server.txt");

        Thread.sleep(4000);

        System.out.println("\nReceiving...");

        DatagramPacket dp = new DatagramPacket(b, b.length);

        fileSocket.receive(dp);

        packet = new String(dp.getData(), 0, dp.getLength());

        System.out.println("-----Contents of File-----");

                System.out.println(packet);

        System.out.println("----End of File----");


        packet.getBytes();

        fis.write(b);

        fileSocket.close();

        Thread.sleep(2000);

        System.out.println("\nFile saved Successfully!");



    }






    private void receiveASong() throws IOException, InterruptedException {



        DatagramSocket audioSocket = new DatagramSocket(2000);

        int packetsize = 1024;

        FileOutputStream fos = null;

        BufferedOutputStream bos=null;

        try {

            fos = new FileOutputStream("C:\\Users\\bhavi\\Desktop\\CNL\\UCP file transfer\\song from server.wav");

            bos = new BufferedOutputStream(fos);

            double nosofpackets = Math.ceil(((int) (new File("C:\\Users\\bhavi\\Desktop\\CNL\\UCP file transfer\\song.wav")).length()) / packetsize);

            byte[] mybytearray = new byte[packetsize];

        DatagramPacket receivePacket = new DatagramPacket(mybytearray,mybytearray.length);
        System.out.println(nosofpackets + " " + Arrays.toString(mybytearray) + " " + packetsize);





            for (double i = 0; i < nosofpackets + 1; i++) {



                audioSocket.receive(receivePacket);

                byte[] audioData = receivePacket.getData();

                System.out.println("Packet:" + (i + 1));

                System.out.println(Arrays.toString(audioData));

                bos.write(audioData, 0, audioData.length);



            }

            System.out.println("\nFile saved Successfully!");

            bos.close();

            audioSocket.close();



        }catch (IOException e) {

            e.printStackTrace();

        }

    }



    private void receiveAVideo() throws IOException {



        DatagramSocket videoSocket = new DatagramSocket(3000);

        int packetsize = 1024;

        FileOutputStream fos = null;

        BufferedOutputStream bos=null;

        try {

            fos = new FileOutputStream("C:\\Users\\bhavi\\Desktop\\CNL\\UCP file transfer\\video from server.mp4");

            bos = new BufferedOutputStream(fos);

            double nosofpackets = Math.ceil(((int) (new File("C:\\Users\\bhavi\\Desktop\\CNL\\UCP file transfer\\video.mp4")).length()) / packetsize);

            byte[] mybytearray = new byte[packetsize];

            DatagramPacket receivePacket = new DatagramPacket(mybytearray, mybytearray.length);



            System.out.println(nosofpackets + " " + Arrays.toString(mybytearray) + " " + packetsize);



            for (double i = 0; i < nosofpackets + 1; i++) {



                videoSocket.receive(receivePacket);

                byte[] audioData = receivePacket.getData();

                System.out.println("Packet:" + (i + 1));

                System.out.println(Arrays.toString(audioData));

                bos.write(audioData, 0, audioData.length);



            }

            System.out.println("\nFile saved Successfully!");

            bos.close();

            videoSocket.close();

        }catch (IOException e) {

            e.printStackTrace();

        }

    }

}

