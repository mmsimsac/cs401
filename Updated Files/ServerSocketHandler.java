
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

class ServerSocketHandler extends Thread
{

    Server s;
    ArrayList<Connection> connectionList;

    public ServerSocketHandler(Server s, ArrayList<Connection> connectionList){
        this.s=s;
        this.connectionList=connectionList;
    }

    public void run(){
        Socket clientSocket;

        while (true){
           // wait for incoming connections. Start a new Connection Thread for each incoming connection.
            try {

                if(s.numClients < s.MAX_CONNECTED_CLIENTS) {
                    clientSocket = s.listener.accept();
                    Connection c = new Connection(clientSocket, connectionList, s);
                    c.start();
                    connectionList.add(c);
                    s.numClients++;
                    s.printNewConnection(c,clientSocket);
                }
                //else
                    //System.out.println("Max Clients Connected!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    //other methods may be necessary. Include them when appropriate.

}
