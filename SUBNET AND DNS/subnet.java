package CN_JAVA;

import java.io.*;
import java.util.Scanner;
public class subnet {
 public static void main(String[] args) throws IOException {
 
 System.out.println("ENTER IP:");
 Scanner sc = new Scanner(System.in);
 String ip = sc.nextLine();
 String checkclass = ip.substring(0, 3);
 int cc = Integer.parseInt(checkclass);
 String mask = null;
 if(cc>0)
 {
 if(cc<=127)
 {
 mask = "255.0.0.0";
 System.out.println("Class A IP Address");
 System.out.println("SUBNET MASK:\n"+mask);
 }
 if(cc>=128 && cc<=191)
 {
 mask = "255.255.0.0";
 System.out.println("Class B IP Address");
 System.out.println("SUBNET MASK:\n"+mask);
 }
 if(cc>=192 && cc<=223)
 {
 mask = "255.255.255.0";
 System.out.println("Class C IP Address");
 System.out.println("SUBNET MASK:\n"+mask);
 }
// if(cc>=224 && cc<=239)
// {
// mask = "255.0.0.0";
// System.out.println("Class D IP Address Used for multicasting");
// }
// if(cc>=240 && cc<=254)// {
// mask = "255.0.0.0";
// System.out.println("Class E IP Address for Experimental Use");
// }
 }
 
 String networkAddr="";
 String[] ipAddrParts=ip.split("\\.");  //The dot "." is a special character in java regex engine, so you have to use "\\." to escape this character:
 String[] maskParts=mask.split("\\.");
 for(int i=0;i<4;i++){
 int x=Integer.parseInt(ipAddrParts[i]);
 int y=Integer.parseInt(maskParts[i]);
 int z=x&y;
 networkAddr+=z+".";
 }
 
 System.out.println("ADDRESS: "+networkAddr);
 }
}
