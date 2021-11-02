import java.io.*;
import java.net.*;
import java.util.*;
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


    public static void main(String args[]) throws FileNotFoundException {
        Scanner s = new Scanner(System.in);
        String fIn="";
        if(args.length > 0){
            if(args[0].equals("-c"))
                fIn = args[1];
        }
        else{ //else is here so client can be run from IDE / if config file not included in cmd line.
            System.out.print("Please enter config file path: ");
            fIn = s.nextLine();
        }
        //String fIn = s.nextLine();
        File f = new File(fIn);
        Scanner fScan = new Scanner(f); //scanner to read in file

        // parse client config and server ip.
        // create client object and connect to server. If successful, print success message , otherwise quit.
        try{
            Client client = new Client();
            client.s=new Socket("localhost",5000);
            System.out.println("Connected to server ... "+client.s);

            while(fScan.hasNextLine()){
                String str = fScan.nextLine();
                String[] arr = str.split(" ");

                if(arr[0].equals("CLIENTID"))
                    client.peerID = Integer.parseInt(arr[1]);
                else if(arr[0].equals("SERVERPORT"))
                    client.serverPort = Integer.parseInt(arr[1]);
                else if(arr[0].equals("MYPORT"))
                    client.peer_listen_port = Integer.parseInt(arr[1]);
                else if(arr[0].equals("FILE_VECTOR")){
                    client.FILE_VECTOR = new char[arr[1].length()];
                    for(int i=0;i<arr[1].length();i++){
                        client.FILE_VECTOR[i] = arr[1].charAt(i);
                    }
                }

            }
            // Once connected, send registration info, event_type=0
            Packet p = new Packet();
            p.event_type = 0;
            p.sender = client.peerID;
            p.port_number = client.peer_listen_port;
            p.FILE_VECTOR = client.FILE_VECTOR;

            //send packet
            client.outputStream = new ObjectOutputStream(client.s.getOutputStream());
            client.outputStream.writeObject(p);
            System.out.println("Packet Sent");


            // start a thread to handle server responses. This class is not provided. You can create a new class called ClientPacketHandler to process these requests.
            ClientPacketHandler cph = new ClientPacketHandler(client);
            cph.start();
            //done! now loop for user input
            while (true){

                System.out.println("Enter query");

                String input = s.next();
                // wait for user commands.
                if(input.equals("f")){
                    System.out.println("Enter the file index you want");
                    int index = s.nextInt();

                    if(client.FILE_VECTOR[index]=='1')
                        System.out.println("I already have this file block!");

                    else{
                        System.out.println("I don't have this file. Let me contact server...");
                        Packet pRequest = new Packet();
                        pRequest.event_type=1;
                        pRequest.req_file_index=index;
                        pRequest.sender = client.peerID;
                        client.outputStream.writeObject(pRequest);
                    }

                }
                if(input.equals("q")){
                    //send quit packet to server here
                    System.out.println("Getting ready to quit . . . " + client.s);
                    Packet clientQuitRequestP = new Packet();
                    clientQuitRequestP.event_type = 5; // 5 -> Client wants to quit
                    client.outputStream.writeObject(clientQuitRequestP); // Send quit packet to connection
                    s.close();
                    break;
                }
                if(input.equals("")){
                    System.out.println("next input picked up last int entered");
                }
            }
        } catch (IOException e){
            System.out.println("Could not connect to server");
        }

    }


    // implement other methods as necessary
    public void printVector(char[] vector){
        for(char x : vector){
            System.out.print(x);
        }
        System.out.println();
    }
}
