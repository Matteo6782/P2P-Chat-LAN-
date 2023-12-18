package JFrame;

import JFrame.JPanelPackage.MainJPanel;
import Mainpackage.*;
import java.io.IOException;
import javax.swing.JPanel;

public class MainJFrame extends javax.swing.JFrame {

    Actor actor = new Actor();;
    
    public MainJFrame() {
        initComponents();
        setPanel(new MainJPanel(actor));
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                ServerConnection sc = actor.getServerConnection();
                sc.getServerConnection().shutdownInput();
                sc.getServerConnection().shutdownOutput();
                sc.getServerConnection().close();
                sc.getThread().shutdown();
                sc.getThread().shutdownNow();
            } catch (IOException ex) {}
        }, "Shutdown-thread"));
    }
    
    public final void setPanel(JPanel panel){
        DefaultJPanel.removeAll();
        panel.setVisible(true);
        panel.setSize(DefaultJPanel.getSize());
        DefaultJPanel.add(panel);
        DefaultJPanel.validate();
        DefaultJPanel.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DefaultJPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout DefaultJPanelLayout = new javax.swing.GroupLayout(DefaultJPanel);
        DefaultJPanel.setLayout(DefaultJPanelLayout);
        DefaultJPanelLayout.setHorizontalGroup(
            DefaultJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 833, Short.MAX_VALUE)
        );
        DefaultJPanelLayout.setVerticalGroup(
            DefaultJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 643, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DefaultJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DefaultJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DefaultJPanel;
    // End of variables declaration//GEN-END:variables
}
