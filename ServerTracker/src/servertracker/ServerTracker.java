package servertracker;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JTextArea;
public class ServerTracker {
            
    ServerSocket server;
    Socket peerSocket;
    ServerAction serverAction;
    JTextArea textAreaLog;
    public static ArrayList<ClientHandler> clientHandlers  = new ArrayList<>();
    
    Thread listenForClient = new Thread(){
        @Override
        public void run(){
            while(!server.isClosed()){
                try {
                    peerSocket = server.accept();
                    
                    if(peerSocket!=null){
                        ClientHandler clientHandler = new ClientHandler(peerSocket,serverAction);
                        Thread thread = new Thread(clientHandler);
                        thread.start();
                        textAreaLog.append("New peer: "+peerSocket.getLocalSocketAddress()+"\n");
                        clientHandlers.add(clientHandler);
                    }
                } catch (IOException ex) {}
                
            }
        }
    };
    
    
    public ServerTracker(JTextArea jTextAreaLog){
        try {
            this.textAreaLog = jTextAreaLog;
            server = new ServerSocket(9898);
            textAreaLog.append("ServerSocket ->  "+InetAddress.getLocalHost().getHostAddress()+":"+server.getLocalPort()+"\n");
            serverAction = new ServerAction(InetAddress.getLocalHost().getHostAddress());
            serverAction.DatabaseConnection();
            
        } catch (IOException ex) {}
    }
    public void quit(){
        try{
            textAreaLog.append("Log: Setting the NULL value to any peer in the database...\n");
            serverAction.onServerClose();
            textAreaLog.append("Log: Clearing peer list...\n");
            clientHandlers.clear();
            textAreaLog.append("Log: Closing server...\n");
            if(server != null){
                server.close();
            }
        }catch(IOException e){}
    }
    
    public Thread getListener(){
        return listenForClient;
    }
}
