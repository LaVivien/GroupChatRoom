package ccc;

import java.io.*; 
import java.net.*; 
import java.util.*;

public class ChatServer {
	
	static ArrayList<ClientHandler> clients = new ArrayList<>(); 
	
	public static void main(String[] args) throws IOException  { 
		int port = Integer.parseInt(args[0]);
        ServerSocket server = new ServerSocket(port); 
        System.out.println("Server Started ....");       
        while (true)   { 
            Socket socket = null;              
            try { 
                socket = server.accept();         
                System.out.println("A new client is connected : " + socket); 
                DataInputStream dis = new DataInputStream(socket.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());   
                System.out.println("Assigning new thread for this client");           
                ClientHandler client = new ClientHandler(socket, dis, dos);               
                clients.add(client);
                client.start();           
            } catch (Exception e){ 
                socket.close(); 
                server.close();
                System.out.println("server exit");
            } 
        }
    } 
}

class ClientHandler extends Thread{
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s; 
    String nickName="";

    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) { 
        this.s = s; 
        this.dis = dis; 
        this.dos = dos; 
    } 
  
    @Override
    public void run() { 
        String received; 
        String msg;
        boolean exit=false;
        while (!exit) { 
            try { 
                dos.writeUTF("\nPlease enter command:\n"+
                			"/nick <name>\n"+
                			"/dm <name> <msg>\n"+
                            "/quit\n"); 
                msg = dis.readUTF();                  
                String[] strs =msg.split("\\s+");
                received = strs[0];
                switch (received) {              
                    case "/nick" : 
                    	nickName = strs[1];
                    	System.out.println("client nick name: "+strs[1]);
                        dos.writeUTF("set nickname " + strs[1]); 
                        break;                     
                    case "/dm" : 
                    	int msgPos= msg.indexOf(strs[1]) + strs[1].length()+1;
                        String outMsg = msg.substring(msgPos);                        
                        for(ClientHandler x: ChatServer.clients) {                    
                        	if(x.nickName.equals(strs[1])) {	                        
                        		x.dos.writeUTF(outMsg);
                        		System.out.println("Send msg to "+ strs[1] + " " + outMsg);
                        	} 
                        }
                        break;                     
                    case "/quit":
                        System.out.println("Client " + this.s + " sends exit..."); 
                        System.out.println("Closing this connection."); 
                        ChatServer.clients.remove(this);
                        this.s.close(); 
                        System.out.println("Connection closed"); 
                        exit =true;
                        break; 
                    default: 
                    	if (received.length()>1) {
	                    	System.out.println("Send msg to all");
	                    	for(ClientHandler x: ChatServer.clients) {  
	                    		x.dos.writeUTF(msg);
	                    	}
                    	}
                        break; 
                } 
            } catch (IOException e) { 
            	 System.out.println("invalid input, try again");
            } 
        }       
        try { 
            this.dis.close(); 
            this.dos.close();          
        } catch(IOException e){ 
        	System.out.println("server exit");
        } 
    } 
} 
