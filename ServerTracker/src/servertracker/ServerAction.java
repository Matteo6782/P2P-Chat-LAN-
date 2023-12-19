package servertracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static servertracker.ServerTracker.clientHandlers;

public class ServerAction {
    String addr;
    Statement statement;
    Connection connection;
            
    public ServerAction(String addr){
        this.addr = addr;
    }
    
    public synchronized final void DatabaseConnection(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://"+this.addr+":3306/p2p","peer","");
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            throw new RuntimeException("Error connecting to the database", e);
        }
        System.out.println("Database connected");
    }
    
    public boolean register(String ID, String name, String password, String IP, String porta){
        try{
            String insertquery = "INSERT INTO `peer`(`ID`, `nome`, `password`,`IP`, `port`) VALUES ('"+ID+"','"+name+"','"+password+"','"+IP+"','"+porta+"')";
            statement.executeUpdate(insertquery);
        } catch(SQLException e){
            System.err.println("Error creating a new user!\nVerify database connection\n\nError: "+e.getMessage());
            return false;
        }
        System.out.println("New user");
        return true;
    }
    
    public String login(String name, String password, String ip, String port){
        try {
            String selectquery = "SELECT * FROM `peer` WHERE `nome` = '"+name+"' AND `password` = '"+password+"'";
            ResultSet result = statement.executeQuery(selectquery);
            result.next();
            String UUID = result.getString(1);
            if(result.isLast()){
                String updatequery = "UPDATE `peer` SET `IP`='"+ip+"',`port`='"+port+"' WHERE `nome` = '"+name+"' AND `password` = '"+password+"'";
                statement.executeUpdate(updatequery);
                return UUID;
            }
        } catch (SQLException ex) {
            System.err.println("Error during login: " + ex.getMessage());
            return null;
        }
        System.out.println("User not found");
        return null;
    }
    
    public boolean newContact(String nome,String UUID,String peerID){
        try {
            String selectquery = "SELECT * FROM `peer` WHERE `nome` = '"+nome+"' AND `ID` LIKE '"+UUID+"%'";//cerca l'utente
            ResultSet result = statement.executeQuery(selectquery);
            result.next();
            UUID = result.getString(1);
        } catch (SQLException ex) {
            System.err.println("User not found: " + ex.getMessage());
            return false;
        }
        
        try{
            statement.executeUpdate("INSERT INTO `list`(`FK_peer`) VALUES ('"+UUID+"')");
            statement.executeUpdate("INSERT INTO `relationship`(`FK_lista`, `FK_peer`) VALUES (LAST_INSERT_ID(),'"+peerID+"')");
        } catch(SQLException e){
            System.err.println("Error adding a new contact!\nVerify database connection\n\nError: "+e.getMessage());
            return false;
        }
        System.out.println("New user");
        return true;
    }
    
    public ArrayList<String> getContact(String UUID){
        try {
            String selectquery = "SELECT list.FK_peer FROM list,relationship WHERE relationship.FK_peer = '"+UUID+"' AND FK_lista = ID_lista";//cerca lista contatti
            ResultSet result = statement.executeQuery(selectquery);
            ArrayList<String> ipList = new ArrayList<>();
            while(result.next()){
                ipList.add(result.getString("FK_peer"));
            }
            ArrayList<String> socketList = new ArrayList<>();
            
            for(String s : ipList){
                String selectIPquery = "SELECT * FROM `peer` WHERE `ID` = '"+s+"'";
                ResultSet r = statement.executeQuery(selectIPquery);
                r.next();
                socketList.add(r.getString("nome")+":"+r.getString("IP")+":"+r.getString("port"));
            }
            return socketList;
        } catch (SQLException ex) {
            System.err.println("User not found: " + ex);
            return null;
        }
    }
    
    public void removePeer(String ip, String port, ClientHandler c){
        clientHandlers.remove(c);
        String removeIPQuery = "UPDATE `peer` SET `IP`='NULL',`port`='NULL' WHERE IP='"+ip+"' AND port='"+port+"'";
        try {
            statement.executeUpdate(removeIPQuery);
        } catch (SQLException ex){
            System.err.println("Error removing peer: " + ex.getMessage());
        }
    }
    public void onServerClose(){
        String removeAllIpQuery = "UPDATE `peer` SET `IP`='NULL',`port`='NULL'";
        try {
            statement.executeUpdate(removeAllIpQuery);
        } catch (SQLException ex){
            System.err.println("Error: " + ex.getMessage());
        }
    }
    public String getIP(){
        return addr;
    }
    public Statement getStatement(){
        return statement;
    }
}
