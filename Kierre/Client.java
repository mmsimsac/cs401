// The client class will implemnet the functions listed in the project description. 
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.lang.*; 

public class Client {

     int serverPort;
     InetAddress ip=null; 
     Socket s; 
     ObjectOutputStream outputStream ;
     ObjectInputStream inputStream ;
     int peerID;
     int peer_listen_port;
     char FILE_VECTOR[];
     

    public static void main(String args[])
    {
        //System.out.println("Running client.");





        // parse client config and server ip. Sent from the Connection class?
        // create client object and connect to server. If successful, print success message , otherwise quit.
        
        // Once connected, send registration info, event_type=0 -> This method is in the Connection class. To register the client.
       // start a thread to handle server responses. This class is not provided. You can create a new class called ClientPacketHandler to process these requests.
       
        //done! now loop for user input
            while (true){
                
               // wait for user commands.
        }
       
    }

 
    // implement other methods as necessary

}
