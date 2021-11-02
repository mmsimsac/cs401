import java.io.*;
import java.net.*;
import java.util.*;

class Connection extends Thread
{
    Server s;
    Socket socket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    int peerPort;
    int peer_listen_port;
    int peerID;
    InetAddress peerIP;
    char FILE_VECTOR[];
    ArrayList<Connection> connectionList;

    public Connection(Socket socket, ArrayList<Connection> connectionList, Server s) throws IOException
    {
        this.connectionList=connectionList;
        this.socket=socket;
        this.outputStream=new ObjectOutputStream(socket.getOutputStream());
        this.inputStream=new ObjectInputStream(socket.getInputStream());
        this.peerIP=socket.getInetAddress();
        this.peerPort=socket.getPort();
        this.s=s;

    }

    @Override
    public void run() {
        //wait for register packet.
        // once received, listen for packets with client requests.

        Packet p;
        while (true){
            try {
                p = (Packet) inputStream.readObject();
                eventHandler(p,s,connectionList);
                //s.printVectors(connectionList);

            }
            catch (Exception e) {break;}

        }

    }

    public void eventHandler(Packet p, Server s, ArrayList<Connection> connectionList)
    {
        int event_type = p.event_type;
        switch (event_type)
        {
            case 0: //client register
                peerID = p.sender;
                peer_listen_port = p.port_number;
                FILE_VECTOR = p.FILE_VECTOR;
                s.printVectors(connectionList);
                break;

            case 1: // client is requesting a file
                System.out.println("Client "+ p.sender +" is requesting file "+ p.req_file_index);
                int reqIndex = p.req_file_index;
                int hostID = 0;
                int peerPort = 0;
                for(Connection x : connectionList){
                    if(x.FILE_VECTOR[reqIndex]=='1'){
                        hostID = x.peerID;
                        peerPort = x.peer_listen_port;
                        break;
                    }
                }
                Packet responseP = new Packet();
                responseP.event_type=2;
                responseP.peerID = hostID;
                responseP.req_file_index = p.req_file_index;
                responseP.peer_listen_port = peerPort;
                try {
                    outputStream.writeObject(responseP);
                    System.out.println("Packet Sent");
                } catch(IOException e){
                    System.out.println("Error responding: case 1");
                }
                break;

            case 5: // client wants to quit

                System.out.println("Removing client " + peerID);
                connectionList.remove(this);
                System.out.println("Total Registered Clients: " + connectionList.size());
                System.out.println("Closed clientSocket");

                break;

            case 6:   // server wants to quit
                //System.out.println("Server wants to quit. I should too!");
                //connectionList.remove(this);



        };
    }
    //other methods go here



}