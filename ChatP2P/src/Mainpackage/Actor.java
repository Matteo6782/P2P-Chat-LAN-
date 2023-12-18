package Mainpackage;

import JFrame.JPanelPackage.PeerJPanel;

public class Actor {
    
    ServerConnection serverConnection;
    PeerHandler ph;
    
    
    public Actor() {
        this.serverConnection = new ServerConnection(this,"config.json");
        this.ph = new PeerHandler();
    }
    
    public void offlineLogin(){
        Main.mainJFrame.setPanel(new PeerJPanel(this));
    }

    
    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    public PeerHandler getPh() {
        return ph;
    }
    
    
}
