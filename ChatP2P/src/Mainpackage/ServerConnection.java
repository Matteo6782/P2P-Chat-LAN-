package Mainpackage;

import JFrame.JPanelPackage.PeerJPanel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.FileReader;

public class ServerConnection {
    private final String SERVER_ADDRESS;
    private final int SERVER_PORT;
    
    public String myUUID;
        
    private Socket serverConnection;
    private BufferedReader in;
    private BufferedWriter out;
    
    Actor actor;
    String[] contactList;
    
    private boolean isOnline = false;
    private ExecutorService thread = Executors.newSingleThreadExecutor();
    
    public ServerConnection(Actor actor, String configFilePath){
        
        JsonObject config = readConfig(configFilePath);

        // Assegna l'indirizzo IP e la porta del server dal file di configurazione
        this.SERVER_ADDRESS = config.get("serverAddress").getAsString();
        this.SERVER_PORT = config.get("serverPort").getAsInt();
        
        tryConnection();
        this.actor = actor;
    }
    
    private JsonObject readConfig(String configFilePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(configFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            // Gestisci l'eccezione (ad esempio, log o avviso)
            e.printStackTrace();
        }

        Gson gson = new Gson();
        return gson.fromJson(content.toString(), JsonObject.class);
    }
    
    /**
     * Connects to the serverTracker.
     * 
     * @return true if the connection was successful, false otherwise.
     */
    public final boolean connect() {
        try {
            serverConnection = new Socket();
            serverConnection.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
            out = new BufferedWriter(new OutputStreamWriter(serverConnection.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
            startServerListener();
            isOnline = true;
            return true;
        } catch (SocketTimeoutException e) {
            // log the exception
            return false;
        } catch (IOException ex) {
            // log the exception
        }
        return false;
    }
    
    public final boolean tryConnection(){
        if(connect()){startServerListener();return true;}
        return false;
    }
        
    /**
     * Starts a new thread to listen for responses from the serverTracker.
     */
    private void startServerListener() {
        thread.submit(() -> {
            while (!thread.isShutdown()){
                if(serverConnection.isConnected()){
                    try {           
                        String response = in.readLine();
                        processServerResponse(response);
                    } catch (IOException ex) {
                        closeAfterException();
                    }
                }
            }
        });
    }
            
    /**
     * Sends a login request to the server.
     * 
     * @param nome the name of the user.
     * @param password the password of the user.
     * @param ip the ip of the user
     * @param port the port of the user
     */
    public void login(String nome, String password,String ip,String port) {
        if(out==null){serverTrackerOffline();return;}
        
        String hashedPassword = PasswordHasher.hashPassword(password);
        
        sendMsgToServerTracker("L" + nome + "&" + hashedPassword + "&" + ip + "&" + port);
    }
    /**
     * Sends a register request to the server.
     * 
     * @param ID the ID of the user.
     * @param nome the name of the user.
     * @param password the password of the user.
     * @param IP the IP address of the user.
     * @param Port the port number of the user.
     */
    public void register(String ID, String nome, String password, String IP, String Port) {
        if(out==null){serverTrackerOffline();return;}
        
        String hashedPassword = PasswordHasher.hashPassword(password);

        sendMsgToServerTracker("R" + ID + "&" + nome + "&" + hashedPassword + "&" + IP + "&" + Port);
    }
    /**
     * Sends a request to the server to create a new contact
     * 
     * @param name the name of the user.
     * @param UUID the first the first 8 characters of the UUID of the user.
     */
    public void newContact(String name,String UUID){
        if(out==null){serverTrackerOffline();return;}
        sendMsgToServerTracker("C" + name + "&" + UUID + "&" + myUUID);
    }
    
    /**
     * Sends a request to the server to retrieve its contact list from the db
     */
    public void getContact(){
        sendMsgToServerTracker("U"+myUUID);
    }
    /*
    This method processes a message received from the server.
    The message is expected to start with a single character identifier, followed by additional information.
    The method determines the appropriate action to take based on the identifier,
    and calls the corresponding method to handle the message.
    */
    public void processServerResponse(String serverResponse) {
        switch (serverResponse.charAt(0)) {
            case 'R' -> handleRegistrationResponse();
            case 'L' -> handleLoginResponse(serverResponse);
            case 'U' -> handleContactResponse(serverResponse.substring(1));
            case 'E' -> handleErrorResponse();
            default -> {
            }
        }
    }
    /*This method is called when a message with an identifier of 'U' (Update) is received from the trackerServer. 
    It updates the contact list with the new information received from the server.*/
    private void handleContactResponse(String serverResponse){
        contactList = serverResponse.split("&");
    }
    /*This method is called when a message with an identifier of 'R' is received from the trackerServer. It handles the response to a user registration request
    and displays a message to the user indicating that the registration was successful.*/
    private void handleRegistrationResponse() {
        JOptionPane.showMessageDialog(null, "Successfully registered user", "Server", JOptionPane.OK_OPTION);
    }
    /*This method is called when a message with an identifier of 'L' is received from the trackerServer. It handles the response to a user login request and sets the user's UUID.*/
    private void handleLoginResponse(String serverResponse) {
        myUUID = serverResponse.substring(1);
        PeerJPanel peer = new PeerJPanel(this.actor);
        System.out.println("aa");
        Main.mainJFrame.setPanel(peer);
    }
    /*This method is called when a message with an identifier of 'E' is received from the trackerServer. It handles an error response from the server and displays a message
    to the user indicating that an error has occurred.*/
    private void handleErrorResponse() {
        JOptionPane.showMessageDialog(null, "Error", "Server", JOptionPane.OK_OPTION);
    } 
    
    public void sendMsgToServerTracker(String msg){
        try {
            out.write(msg);
            out.newLine();
            out.flush();
        } catch (IOException ex) {}
    }
    
    public void closeAfterException(){
        thread.shutdown();
        isOnline = false;
        JOptionPane.showMessageDialog(null, "The server is no longer reachable!","Error",JOptionPane.OK_OPTION);
    }
    public void serverTrackerOffline(){
        JOptionPane.showMessageDialog(null, "Server tracker offline","Server",JOptionPane.OK_OPTION);
        isOnline = false;
    }

    
    public ExecutorService getThread() {
        return thread;
    }
    public Socket getServerConnection() {
        return serverConnection;
    }
    public String getMyUUID() {
        return myUUID;
    }

    public String[] getContactList() {
        return contactList;
    }

    public boolean isOnline() {
        return isOnline;
    }
    
    
}
