import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    
    int serverPort;
    int MAX_CONNECTED_CLIENTS;
    ServerSocket listener;
    int numClients;
    ArrayList<Connection> connectionList;
    //ServerSocket ss;
    //Socket s;

    
    public Server(){
        serverPort=5000;
        MAX_CONNECTED_CLIENTS=20;
        listener=null;
        numClients=0;
        connectionList= new ArrayList<Connection>();
    }
    
    public static void main(String args[])
    {
        Server server = new Server();
        System.out.println("Started Server. Waiting for a client...");
        Scanner scanner = new Scanner(System.in);

        try {
            //First, let's start our server and bind it to a port(5000).
            server.listener = new ServerSocket(server.serverPort);

            //Next let's start a thread that will handle incoming connections
            ServerSocketHandler ssh = new ServerSocketHandler(server, server.connectionList);
            ssh.start();
            // Note in programs shown in class, at this point we listen for incoming connections in the main method. However for this project since the server has to handle incoming connections and also handle user input simultaneously, we start a separate thread to listen for incoming connections in the Server. This is the ServerSocketHandler thread, which will in turn spawn new Connection Threads, for each client connection.

            //Done! Now main() will just loop for user input!.
            while (true) {
                System.out.println("Enter query");
                String input = scanner.nextLine();
                // wait on user inputs

                if(input.equals("q")){
                    //quit message to clients
                    if(server.numClients==0)
                        break;
                }
                else if(input.equals("p")){

                }

            }
            //will quit on user input
        } catch(IOException ex){

        }
    }

    // add other methods as necessaryu. For example, you will prbably need a method to print the incoming connection info.
    public void printNewConnection (Connection c, Socket clientSocket){
        System.out.println("A new client is connecting... : "+clientSocket);
        System.out.println("Port : "+c.peerPort+"\nIP : "+c.peerIP);
        System.out.println("Client Connected. Total registered clients : "+numClients);
    }
}
    
