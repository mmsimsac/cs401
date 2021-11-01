

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientPacketHandler extends Thread{

    ObjectInputStream inputStream;

    public ClientPacketHandler(Client client) throws IOException {
        this.inputStream=new ObjectInputStream(client.s.getInputStream());
    }

    public void run(){
        Packet p;
        while(true){
            try {
                p = (Packet) inputStream.readObject();
                eventHandler(p);
            }
            catch (Exception e) {break;}

        }
    }

    public void eventHandler(Packet p){
        int event_type = p.event_type;
        switch (event_type){

            case 2:
                if(p.peerID == 0){
                    System.out.println("Server says no client has file "+p.req_file_index);
                }
                else{
                    System.out.println("Server says that peer " + p.peerID + " on listening port "+ p.peer_listen_port + " has file "+ p.req_file_index);
                }
                break;
        }
    }
}
