package ccc;
import java.io.*; 
import java.net.*; 
import java.util.Scanner; 

public class ChatClient {	  
    public static void main(String args[]) throws UnknownHostException, IOException  {
    	String serverName=args[0];
    	int serverPort = Integer.parseInt(args[1]);
    	try {	
	        InetAddress ip = InetAddress.getByName(serverName); 
	        Socket s = new Socket(ip, serverPort); 
	        DataInputStream dis = new DataInputStream(s.getInputStream()); 
	        DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
	        Scanner scn = new Scanner(System.in); 
	        Thread sendMessage = new Thread(new Runnable()   { 
	            @Override
	            public void run() { 
	                while (true) { 
	                	try { 
		                    String msg = scn.nextLine();   
	                        dos.writeUTF(msg); 
	                    } catch (IOException e) { 
	                    	scn.close();
	                    	System.exit(0);
	                    } 
	                } 
	            } 
	        }); 

	        Thread readMessage = new Thread(new Runnable(){ 
	            @Override
	            public void run() { 	            
	                while (true) { 
	                    try { 
	                        String msg = dis.readUTF();  
	                        if(msg.equals("/quit")) { 
		                        System.out.println("Closing this connection : " + s); 
		                        s.close(); 
		                        System.out.println("Connection closed"); 
		                        System.exit(0);
		                        break; 
		                    } 
	                        System.out.println(msg); 
	                    } catch (IOException e) { 
	                    	System.exit(0);
	                    } 
	                } 	         
	            } 
	        }); 
	        sendMessage.start(); 
	        readMessage.start(); 	  
    	} catch (Exception e) {  	    		
    		System.out.println("client connection error, exit!");
    	}
    } 
}
