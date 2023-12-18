package JFrame.JPanelPackage;

import JFrame.ChatJFrame;
import Mainpackage.Actor;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class PeerJPanel extends javax.swing.JPanel {
    private static final boolean OFFLINE = false;
    boolean STATUS = true;
    Actor actor;
    boolean isVisible = false;
    
    public PeerJPanel(Actor actor) {
        initComponents();
        this.actor = actor;
        update();
        updateContactList();
        
    }
    
    public final void update(){
        try{
            STATUS = actor.getServerConnection().isOnline();
            JLabeUUID.setText("UUID: "+ (STATUS == OFFLINE ? "NaN" : (isVisible == true ? actor.getServerConnection().getMyUUID() : "XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXX") ));
            JLabeIP.setText("IP: " + InetAddress.getLocalHost().getHostAddress());
            JLabelPort.setText("Port: " + this.actor.getPh().getServerSocket().getLocalPort());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        String status = (STATUS == OFFLINE ? "Offline" : "Online");
        JLabelStatus.setText(status);
        if (STATUS == OFFLINE) {
            JLabelStatus.setForeground(Color.red);
            JListContatti.setEnabled(false);
            jButtonAddContact.setEnabled(false);
            jButtonUUIDSwitch.setEnabled(false);
            jButtonReload.setEnabled(false);
        }
    }
    
    private void updateResultFinderList() {
        update();
        DefaultListModel model = new DefaultListModel();
        for (String s : actor.getPh().getPeerList()) {
            model.addElement(s);
        }
        JListResultFinder.setModel(model);
    }

    private void updateContactList() {
        update();
        DefaultListModel contact = new DefaultListModel();
        if(actor.getServerConnection().getContactList()==null)return;
        for (String s : actor.getServerConnection().getContactList()) {
            contact.addElement(s.replaceFirst(":", " -> "));
        }
        JListContatti.setModel(contact);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        JListContatti = new javax.swing.JList<>();
        jButtonReload = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        JLabelStatus = new javax.swing.JLabel();
        JLabeIP = new javax.swing.JLabel();
        JLabelPort = new javax.swing.JLabel();
        JLabelFinder = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        JListResultFinder = new javax.swing.JList<>();
        jButtonSearch = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jButtonAddContact = new javax.swing.JButton();
        JLabeUUID = new javax.swing.JLabel();
        jButtonUUIDSwitch = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 51, 51));

        JListContatti.setBackground(new java.awt.Color(51, 51, 51));
        JListContatti.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        JListContatti.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        JListContatti.setForeground(new java.awt.Color(255, 255, 255));
        JListContatti.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JListContattiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JListContatti);

        jButtonReload.setBackground(new java.awt.Color(0, 0, 0));
        jButtonReload.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonReload.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReload.setText("R");
        jButtonReload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonReloadMouseClicked(evt);
            }
        });
        jButtonReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReloadActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Online contacts:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Status:");

        JLabelStatus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        JLabelStatus.setForeground(new java.awt.Color(51, 255, 0));
        JLabelStatus.setText("Online");

        JLabeIP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        JLabeIP.setForeground(new java.awt.Color(255, 255, 255));
        JLabeIP.setText("IP: 192.168.16.XXX");

        JLabelPort.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        JLabelPort.setForeground(new java.awt.Color(255, 255, 255));
        JLabelPort.setText("Port: 5526");

        JLabelFinder.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        JLabelFinder.setForeground(new java.awt.Color(255, 255, 255));
        JLabelFinder.setText("Peer finder:");

        JListResultFinder.setBackground(new java.awt.Color(51, 51, 51));
        JListResultFinder.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        JListResultFinder.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        JListResultFinder.setForeground(new java.awt.Color(255, 255, 255));
        JListResultFinder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JListResultFinderMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(JListResultFinder);

        jButtonSearch.setBackground(new java.awt.Color(0, 0, 0));
        jButtonSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonSearch.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSearch.setText("Refresh incoming requests");
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jToggleButton1.setBackground(new java.awt.Color(0, 0, 0));
        jToggleButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jToggleButton1.setForeground(new java.awt.Color(255, 255, 255));
        jToggleButton1.setText("Try connection");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jButtonAddContact.setBackground(new java.awt.Color(0, 0, 0));
        jButtonAddContact.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonAddContact.setForeground(new java.awt.Color(255, 255, 255));
        jButtonAddContact.setText("Add");
        jButtonAddContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddContactActionPerformed(evt);
            }
        });

        JLabeUUID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        JLabeUUID.setForeground(new java.awt.Color(255, 255, 255));

        jButtonUUIDSwitch.setBackground(new java.awt.Color(0, 0, 0));
        jButtonUUIDSwitch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButtonUUIDSwitch.setForeground(new java.awt.Color(255, 255, 255));
        jButtonUUIDSwitch.setText("Mostra UUID");
        jButtonUUIDSwitch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUUIDSwitchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonAddContact, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonReload, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(JLabelFinder)
                            .addComponent(jButtonSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JLabelPort, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JLabeIP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JLabelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonUUIDSwitch, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JLabeUUID, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JLabelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JLabeIP, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JLabelPort, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonUUIDSwitch, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(JLabeUUID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonReload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JLabelFinder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonAddContact, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton1)
                .addGap(74, 74, 74))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReloadActionPerformed

        actor.getServerConnection().getContact();
        updateContactList();
    }//GEN-LAST:event_jButtonReloadActionPerformed

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed

        updateResultFinderList();
        
    }//GEN-LAST:event_jButtonSearchActionPerformed

    private void JListResultFinderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JListResultFinderMouseClicked
        JList list = (JList)evt.getSource();
        if (evt.getClickCount() == 2) {
            int index = list.locationToIndex(evt.getPoint());
            if(index>=0){
                String[] info = actor.getPh().getPeerList().get(index).split(":");
                actor.getPh().getPeerList().remove(index);
                new ChatJFrame(info).setVisible(true);
            }
        }
    }//GEN-LAST:event_JListResultFinderMouseClicked

    
    
    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed

        String ip = JOptionPane.showInputDialog(null,"Destination IP","!");
        String port = JOptionPane.showInputDialog(null,"destination port","!");
        tryConnection(ip,port);
        
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void JListContattiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JListContattiMouseClicked

        JList list = (JList)evt.getSource();
        if (evt.getClickCount() == 2) {
            int index = list.locationToIndex(evt.getPoint());
            if(index>=0){
                String s = actor.getServerConnection().getContactList()[index];
                String[] parse = s.substring(s.indexOf(":") + 1).split(":");
                if(!parse[0].equals("NULL")){
                    tryConnection(parse[0],parse[1]);
                    updateResultFinderList();
                }else{
                    JOptionPane.showMessageDialog(null, "Peer not available","Error",JOptionPane.OK_OPTION);
                }
            }
        }
        
    }//GEN-LAST:event_JListContattiMouseClicked

    private void jButtonReloadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonReloadMouseClicked

        updateContactList();
    }//GEN-LAST:event_jButtonReloadMouseClicked

    private void jButtonAddContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddContactActionPerformed

        //Nuovo contatto
        String nome = JOptionPane.showInputDialog(null,"Contact name: ","");
        String UUID = JOptionPane.showInputDialog(null,"Enter the first 8 characters of the UUID: ","");
        if(JOptionPane.showConfirmDialog(null, "Name: "+nome+"\nUUID: "+UUID+"-XXXX-XXXX-XXXX-XXXXXXXXXXX\n","Confirm",JOptionPane.YES_NO_OPTION)==1) return;
        
        actor.getServerConnection().newContact(nome, UUID);
        
    }//GEN-LAST:event_jButtonAddContactActionPerformed

    private void jButtonUUIDSwitchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUUIDSwitchActionPerformed

        isVisible = !isVisible;
        if(isVisible)jButtonUUIDSwitch.setText("Nascondi UUID");else{jButtonUUIDSwitch.setText("Mostra UUID");}
        JLabeUUID.setText(isVisible == true ? actor.getServerConnection().getMyUUID() : "XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXX");
        
    }//GEN-LAST:event_jButtonUUIDSwitchActionPerformed

    void tryConnection(String ip,String port){
        try {
            Socket socket = new Socket(ip,Integer.parseInt(port));
            try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                out.write("N"+InetAddress.getLocalHost().getHostAddress()+":"+actor.getPh().getServerSocket().getLocalPort());
                out.newLine();
                out.flush();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Peer not available","Error",JOptionPane.OK_OPTION);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLabeIP;
    private javax.swing.JLabel JLabeUUID;
    private javax.swing.JLabel JLabelFinder;
    private javax.swing.JLabel JLabelPort;
    private javax.swing.JLabel JLabelStatus;
    private javax.swing.JList<String> JListContatti;
    private javax.swing.JList<String> JListResultFinder;
    private javax.swing.JButton jButtonAddContact;
    private javax.swing.JButton jButtonReload;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonUUIDSwitch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}
