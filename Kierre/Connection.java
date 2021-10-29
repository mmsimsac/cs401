// The connection Thread is spawned from the ServerSocketHandler class for every new Client connections. Responsibilities for this thread are to hnadle client specific actions like requesting file, registering to server, and client wants to quit.
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

class Connection extends Thread
{
    Socket socket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    int peerPort;
    int peer_listen_port;
    int peerID;
    InetAddress peerIP;
    char FILE_VECTOR[];
    ArrayList<Connection> connectionList;    

    public Connection(Socket socket, ArrayList<Connection> connectionList) throws IOException
    {
        this.connectionList=connectionList;
        this.socket=socket;
        this.outputStream=new ObjectOutputStream(socket.getOutputStream());
        this.inputStream=new ObjectInputStream(socket.getInputStream());
        this.peerIP=socket.getInetAddress();    // Returns IP Address
        this.peerPort=socket.getPort(); // Returns socket number
        
    }

    @Override
    public void run() {
        // wait for register packet.
        // once received, listen for packets with client requests.
        Packet p;
        while (true){
            try {
                p = (Packet) inputStream.readObject();
                eventHandler(p);
            }
            catch (Exception e) {break;}
        }
    }

   

   

    public void eventHandler(Packet p) throws IOException {
        int event_type = p.event_type;
        switch (event_type)
        {
            case 0: //client register

                // Need to send id, listening port, file vector

                outputStream.writeObject(peerID);
                outputStream.writeObject(peer_listen_port);
                outputStream.writeObject(FILE_VECTOR);


            break;
            
            case 1: // client is requesting a file 
            break;

            case 5: // client wants to quit
                if(inputStream.equals("q")){
                    socket.close();
                    connectionList.remove(this);
                }
            break;
           
        };
    }
    
    //other methods go here
    
}
