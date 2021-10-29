// The server class will implement the functions listed in the project description.

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
        System.out.println("Started Server. Waiting for a client . . .");
        Scanner scanner = new Scanner(System.in);

        try {
            //First, let's start our server and bind it to a port(5000).
            server.listener = new ServerSocket(server.serverPort);

            //Next let's start a thread that will handle incoming connections
            ServerSocketHandler ssh = new ServerSocketHandler(server, server.connectionList);
            ssh.start();    // Server -> SSH ->
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
                    //list client id's and file vectors, get from connection class?
                }

            }
            //will quit on user input
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    // add other methods as necessary. For example, you will probably need a method to print the incoming connection info.

}