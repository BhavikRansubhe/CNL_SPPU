package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Client {

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    Socket socket;

    Client() {
        try {
            socket = new Socket("localhost", 3000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            if (socket.isConnected()) {
                System.out.print("\nConnected To Server\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                receiveData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void receiveData() throws IOException {
        int ch = dataInputStream.readInt();
        switch (ch) {
            case 1:
                System.out.print("Characters At A Time : ");
                break;
            case 2:
                System.out.print("\nString At A Time : ");
                break;
            case 3:
                System.out.print("\nSentences At A Time : ");
                break;
            case 4:
                System.out.print("\nFile : ");
                break;
            case 5: int loops;
                boolean stat;
                do{
                    loops=dataInputStream.readInt();
                    if(loops==1) {
                        String buffer = dataInputStream.readUTF();
                        System.out.println(buffer);
                        stat=true;
                    }else{
                        stat=false;
                    }

                }while (stat);
                break;
            case 6:
                byte[] c = new byte[10000];
                do{
                    loops=dataInputStream.readInt();
                    if(loops==1) {
                        receiveFile(c);
                        stat=true;
                    }else{
                        stat=false;
                    }
                }while (stat);
                break;
        }
    }

    private void receiveFile(byte[] c){
        InputStream inputStream = null;
        byte[] b = new byte[10000];
        try {
            inputStream = socket.getInputStream();
            inputStream.read(b,0,b.length);
            FileOutputStream fos = new FileOutputStream("C:\\Users\\bhavi\\Desktop\\CNL\\Framing Technique\\Client\\src\\com\\company\\receivedFile");
            fos.write(b,0,b.length);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }


    public static void main(String[] args){
        new Client();
    }
}
