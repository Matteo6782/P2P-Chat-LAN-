package Mainpackage;

import JFrame.ChatJFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;

public class PeerHandler {
    ArrayList<String> peerList = new ArrayList<>();
    private final ExecutorService thread = Executors.newSingleThreadExecutor();
    private ServerSocket serverSocket;
    
    public PeerHandler(){
        try {
            serverSocket = new ServerSocket(0);
            startPeerListener();
        } catch (IOException ex) {}
    }
    
    /**
     * Starts a new thread to listen for incoming connections from other peers.
     */
    private void startPeerListener() {
        thread.submit(() -> {
            while (!serverSocket.isClosed()) {
                try {
                    Socket peerConnection = serverSocket.accept();
                    
                    if (peerConnection != null) {
                        String info;
                        try (BufferedReader in_fromPeer = new BufferedReader(new InputStreamReader(peerConnection.getInputStream()))) {
                            info = in_fromPeer.readLine(); //contains ip:port of serverSocket of the peer that requested a connection
                            processServerSocketResponse(info);
                        }
                    }
                } catch (IOException ex) {
                    closeAfterException();
                }
            }
        });
    }
    
    /*
    This method processes a message received from other peers.
    The message is expected to start with a single character identifier, followed by additional information.
    The method determines the appropriate action to take based on the identifier,
    and calls the corresponding method to handle the message.
    */
    public void processServerSocketResponse(String msg) {
        switch (msg.charAt(0)) {
            case 'N' -> peerList.add(msg.substring(1));
            case 'C' -> handleConnectionRequest(msg.substring(1));
            default -> {
            }
        }
    }
    /*This metho d is called when a message with an identifier of 'C' (Connection established) is received from the peerServer. It handles the request to connect to another peer, 
    and displays a message to the user indicating that the connection has been accepted.*/
    private void handleConnectionRequest(String info) {
        JOptionPane.showMessageDialog(null, "Connection request accepted! ", info, JOptionPane.OK_OPTION);
        new ChatJFrame(info.split(":"), "X").setVisible(true);
    }
    public void closeAfterException(){
        JOptionPane.showMessageDialog(null, "Restart now","Error",JOptionPane.OK_OPTION);
        System.exit(0);
    }

    public ArrayList<String> getPeerList() {
        return peerList;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
