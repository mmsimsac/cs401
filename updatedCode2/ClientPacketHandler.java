

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientPacketHandler extends Thread{

    ObjectInputStream inputStream;
    Client client;

    public ClientPacketHandler(Client client) throws IOException {
        this.inputStream=new ObjectInputStream(client.s.getInputStream());
        this.client = client;
    }

    public void run(){
        Packet p;
        while(true){
            try {
                p = (Packet) inputStream.readObject();
                eventHandler(p,client);
            }
            catch (Exception e) {break;}

        }
    }

    public void eventHandler(Packet p, Client client) throws IOException {
        int event_type = p.event_type;
        switch (event_type){

            case 2: //file request server response
                if(p.peerID == 0){
                    System.out.println("Server says no client has file "+p.req_file_index);
                }
                else{
                    System.out.println("Server says that peer " + p.peerID + " on listening port "+ p.peer_listen_port + " has file "+ p.req_file_index);
                }
                break;

            case 6: //server quit
                System.out.println("Server wants to quit. I should too!");
                client.clientQuit(client);
                inputStream.close();
                System.exit(0);
        }
    }

}
