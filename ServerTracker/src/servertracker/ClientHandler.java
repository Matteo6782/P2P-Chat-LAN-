package servertracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    ServerAction serverAction;
    
    String IP_SERVERSOCKETPEER;
    String PORT_SERVERSOCKETPEER;
    
    public ClientHandler(Socket s,ServerAction serverAction){
        try{
            this.socket = s;
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.serverAction = serverAction;
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    @Override
    public void run() {
        
        String msgFromClient;
        while(socket.isConnected()){
            try{
                msgFromClient = in.readLine();
                clientRequest(msgFromClient);
            }catch(IOException e){
            break;
            }
        }
    }
    
    public void clientRequest(String clientMsg) throws IOException{//è importante passare i dati nel seguente ordine IP-Nome-Password-IP-Porta
        try{
            String[] data = clientMsg.substring(1).split("&");
            switch (clientMsg.charAt(0)) {
                case 'R' ->{//register
                    if(serverAction.register(data[0],data[1],data[2],data[3],data[4])){
                        reply("R");
                        return;
                    }
                    reply("E");
                    return;
                }
                case 'L' ->{//Login
                    String r = serverAction.login(data[0],data[1],data[2],data[3]);
                    if(r!=null){
                        IP_SERVERSOCKETPEER = data[2];
                        PORT_SERVERSOCKETPEER = data[3];
                        reply("L"+r);
                        return;
                    }
                    reply("E");
                    return;
                }
                case 'C' ->{//new contact
                    if(serverAction.newContact(data[0],data[1],data[2])){
                        reply("C");
                        return;
                    }
                    reply("E");
                    return;
                }
                case 'U'->{//update/get contact list
                    String r = String.join("&", serverAction.getContact(data[0]));
                    if(r!=null){
                        reply("U"+r);
                        return;
                    }
                    reply("E");
                    return;
                }
            }
        }catch(Exception e){
            closeAll();
        }
    }
    
    
    public void closeAll(){
            serverAction.removePeer(IP_SERVERSOCKETPEER, PORT_SERVERSOCKETPEER,this);
            try{
                if(in != null){
                    in.close();
                }
                if(out != null){
                    out.close();
                }
                if(socket != null){
                    socket.close();
                }
            }catch(IOException ez){}
    }
    
    public void reply(String msg){
        try {
            out.write(msg);
            out.newLine();
            out.flush();
        } catch (IOException ex){}
    }
    
    public Socket getSocket(){
        return socket;
    }
}
