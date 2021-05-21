package CN_JAVA;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
public class dns {
 private InetAddress inetAddress;
 private void getDNS(String userInput){
 if(Character.isDigit(userInput.charAt(0))) {
 byte[] bytes = new byte[4];
 String[] bytesStr = userInput.split("[.]");
 for (int i = 0; i < bytesStr.length; i++) {
 bytes[i] = Integer.valueOf(bytesStr[i]).byteValue();
 }
 try {
 inetAddress=InetAddress.getByAddress(bytes);
 } catch (UnknownHostException e) {
 e.printStackTrace();
 }
 }
 else{
 try {
 inetAddress=InetAddress.getByName(userInput);
 } catch (UnknownHostException e) {
 e.printStackTrace();
 }
 }
 System.out.print("\nHost Name : "+inetAddress.getHostName()+"\nHost Address : "+inetAddress.getHostAddress());
 }
 public static void main(String[] args){
 Scanner scanner=new Scanner(System.in);
 System.out.print("Enter IP Address or URL :");
 String userIp=scanner.nextLine();
 dns dnsLookup=new dns();
 dnsLookup.getDNS(userIp); }
}
//31.13.79.35 facebook
