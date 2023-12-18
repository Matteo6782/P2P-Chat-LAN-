package JFrame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
public class ChatJFrame extends javax.swing.JFrame {

    ServerSocket serverSocket;
    Socket connectionSocket;
    BufferedReader in;
    BufferedWriter out;

    public ChatJFrame(String[] info) {
        initComponents();
        this.setResizable(false);
        try {
            serverSocket = new ServerSocket(0);
            replyToRequest(info);
            waitForConnection(true);
            listenForIncomingMessages();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    public ChatJFrame(String[] info,String x) {
        initComponents();
        this.setResizable(false);
        try {
            serverSocket = new ServerSocket(0);
            //--------------------
            System.out.println(info[0]+""+info[1]);
            Socket replySocket = new Socket(info[0],Integer.parseInt(info[1]));
            out = new BufferedWriter(new OutputStreamWriter(replySocket.getOutputStream()));
            sendMessage(InetAddress.getLocalHost().getHostAddress() + ":" + serverSocket.getLocalPort());
            //-------------------
            waitForConnection(false);
            listenForIncomingMessages();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    
    
    public final void waitForConnection(boolean isInitiator) throws IOException {
        connectionSocket = serverSocket.accept();
        out = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        if(isInitiator) {
            String[] d = in.readLine().split(":");
            Socket fromPeer = new Socket(d[0], Integer.parseInt(d[1]));
            in = new BufferedReader(new InputStreamReader(fromPeer.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(fromPeer.getOutputStream()));
        }
    }


    
    public final void replyToRequest(String[] socket) throws IOException {
        Socket s = new Socket(socket[0], Integer.parseInt(socket[1]));
        out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        sendMessage("C" + InetAddress.getLocalHost().getHostAddress() + ":" + serverSocket.getLocalPort());
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaChat = new javax.swing.JTextArea();
        jTextNewMsg = new javax.swing.JTextField();
        jButtonSend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTextAreaChat.setEditable(false);
        jTextAreaChat.setBackground(new java.awt.Color(0, 0, 0));
        jTextAreaChat.setColumns(20);
        jTextAreaChat.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jTextAreaChat.setForeground(new java.awt.Color(255, 255, 255));
        jTextAreaChat.setRows(5);
        jScrollPane1.setViewportView(jTextAreaChat);

        jTextNewMsg.setBackground(new java.awt.Color(51, 51, 51));
        jTextNewMsg.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        jTextNewMsg.setForeground(new java.awt.Color(255, 255, 255));

        jButtonSend.setBackground(new java.awt.Color(0, 0, 0));
        jButtonSend.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonSend.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSend.setText("Send");
        jButtonSend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonSendMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextNewMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextNewMsg)
                    .addComponent(jButtonSend, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public final void listenForIncomingMessages() {
        Thread listenThread = new Thread() {
            @Override
            public void run() {
                try {
                    while(connectionSocket.isConnected()) {
                        String message = in.readLine();
                        if(message != null) {
                            jTextAreaChat.append("Peer: " + message + "\n");
                        }
                    }
                } catch (IOException ex) {}
            }
        };
        listenThread.start();
    }
    
    private void jButtonSendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonSendMouseClicked

    String message = jTextNewMsg.getText();
    if (message.isBlank()) return;

    sendMessage(message); // handle the exception
    jTextAreaChat.append("You: " + message + "\n");
    jTextNewMsg.setText("");

    }//GEN-LAST:event_jButtonSendMouseClicked

    public final void sendMessage(String message) {
        try {
            out.write(message);
            out.newLine();
            out.flush();
        } catch (IOException ex) {}
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSend;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaChat;
    private javax.swing.JTextField jTextNewMsg;
    // End of variables declaration//GEN-END:variables
}
