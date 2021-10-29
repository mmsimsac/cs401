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
     

    public static void main(String args[]) throws IOException {
        //System.out.println("Running client.");

        Client newClient = new Client();
        Scanner input = new Scanner(System.in);

        ObjectOutputStream clientOutputStream = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream clientInputStream = new ObjectInputStream(s.getInputStream()); // Receive info from Connection?

        try {
            Socket s = new Socket(newClient.ip, newClient.serverPort);
            //Scanner istream = new Scanner(s.getInputStream());
            //PrintWriter ostream =  new PrintWriter(s.getOutputStream(), true);
            //inputStream.readObject();
            //outputStream.println(newClient.ip);

            } catch (IOException ioException) {
            ioException.printStackTrace();
        }


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
