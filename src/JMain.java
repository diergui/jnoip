/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import json.JSONException;
import json.JSONObject;
import java.awt.AWTException;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import status.NoipStatusCurrentIP;
import status.NoipStatusFailedUpdate;
import status.NoipStatusSuccessfulUpdate;

/**
 *
 * @author diego
 */
public class JMain extends javax.swing.JDialog implements INoipStatusListener {

    private final JMain thisInstance = this;

    public final Timer timerUpdate = new Timer(Configs.updatetime_fail, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            checkIt();
        }
    });

    public JMain(boolean startMinimized) {
        super();
        initComponents();

        setTitle(Configs.APP_NAME + " " + Configs.APP_VERSION);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        setLocationRelativeTo(getParent());

        if (startMinimized && SystemTray.isSupported()) {
            setVisible(false);
        } else {
            setVisible(true);
        }

        lblHost.setText("");
        lblIP.setText("");
        lblUpdated.setText("");
        lblNextUpdate.setText("");
        txtLog.setText("");
        txtLog.setEditable(false);
        txtLog.setWrapStyleWord(true);
        txtLog.setLineWrap(true);

        cmdDoUpdate.setEnabled(false);

        setComportamientoToTray();

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                try {
                    final File configFile = new File(getMyPath() + "/config.cfg");

                    System.out.println("Config file path: " + configFile);

                    final String fileContent = ReadWriteTextFile.read(configFile);

                    final JSONObject json = new JSONObject(fileContent);
                    Configs.hostname = json.getString("hostname").trim();
                    Configs.username = json.getString("username").trim();
                    Configs.password = json.getString("password").trim();
                    Configs.updatetime_ok = json.getInt("updatetime_ok");
                    Configs.updatetime_fail = json.getInt("updatetime_fail");

                    if (Configs.updatetime_ok < 5) {
                        Configs.updatetime_ok = 5;
                    }

                    if (Configs.updatetime_ok > 1440) {
                        Configs.updatetime_ok = 1440;
                    }

                    if (Configs.updatetime_fail < 0) {
                        Configs.updatetime_fail = 0;
                    }

                    if (Configs.updatetime_fail > 1440) {
                        Configs.updatetime_fail = 1440;
                    }

                    lblHost.setText(Configs.hostname);
                    lblIP.setText("");
                    txtLog.setText("");

                    cmdDoUpdate.setEnabled(true);

                    log("Initialization OK.");

                    checkIt();

                } catch (JSONException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid configuration file content. " + ex);
                    System.exit(1);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Configuration file not found.");
                    System.exit(1);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex);
                    System.exit(1);
                }

            }
        });

    }

    @Override
    public void setNoipStatus(Object status) {

        if (status instanceof NoipStatusCurrentIP) {
            final NoipStatusCurrentIP st = (NoipStatusCurrentIP) status;
            lblIP.setText(st.getIp());

        } else if (status instanceof NoipStatusFailedUpdate) {
            final NoipStatusFailedUpdate st = (NoipStatusFailedUpdate) status;

            if (st.getError() != null) {
                log(st.getError().getMessage());
            }

            if (st.getNextUpdate() != null) {
                lblNextUpdate.setText(formatearFechaHora2(st.getNextUpdate()));
            }

            changeTimerDelay(Configs.updatetime_fail);

        } else if (status instanceof NoipStatusSuccessfulUpdate) {
            final NoipStatusSuccessfulUpdate st = (NoipStatusSuccessfulUpdate) status;

            if (st.getIp() != null) {
                lblIP.setText(st.getIp());
            }

            lblUpdated.setText(formatearFechaHora2(st.getUpdated()));
            lblNextUpdate.setText(formatearFechaHora2(st.getNextUpdate()));

            if (st.getMessage() != null) {
                log(st.getMessage());
            }

            changeTimerDelay(Configs.updatetime_ok);
        }

        cmdDoUpdate.setEnabled(true);
    }

    private void checkIt() {
        cmdDoUpdate.setEnabled(false);

        new Thread(new Runnable() {

            @Override
            public void run() {
                final UpdaterEngine noip = new UpdaterEngine(thisInstance);
                noip.update();
            }
        }).start();
    }

    private void log(String str) {
        txtLog.append("[" + millisToHHmmss(new Date()) + "] " + str + "\n");
        txtLog.setCaretPosition(txtLog.getText().length());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblHost = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblIP = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblUpdated = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblNextUpdate = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        cmdDoUpdate = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Host:");

        lblHost.setForeground(new java.awt.Color(3, 4, 186));
        lblHost.setText("jLabel5");

        jLabel2.setText("IP: ");

        lblIP.setForeground(new java.awt.Color(3, 4, 186));
        lblIP.setText("jLabel5");

        jLabel3.setText("Updated:");

        lblUpdated.setForeground(new java.awt.Color(3, 4, 186));
        lblUpdated.setText("jLabel5");

        jLabel4.setText("Next update:");

        lblNextUpdate.setForeground(new java.awt.Color(3, 4, 186));
        lblNextUpdate.setText("jLabel5");

        cmdDoUpdate.setText("Update now");
        cmdDoUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDoUpdateActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Noto Sans", 0, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(128, 128, 128));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("by Diego Ergui | diergui@gmail.com");

        lblExit.setForeground(new java.awt.Color(0, 6, 255));
        lblExit.setText("[ Exit ]");
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExitMouseClicked(evt);
            }
        });

        txtLog.setColumns(20);
        txtLog.setFont(new java.awt.Font("Courier 10 Pitch", 0, 11)); // NOI18N
        txtLog.setRows(5);
        jScrollPane1.setViewportView(txtLog);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                            .addComponent(lblUpdated, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                            .addComponent(lblNextUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                            .addComponent(lblHost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cmdDoUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(161, 161, 161)
                        .addComponent(lblExit))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblHost))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblIP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblUpdated))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblNextUpdate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdDoUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblExit, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdDoUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDoUpdateActionPerformed
        checkIt();
    }//GEN-LAST:event_cmdDoUpdateActionPerformed

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblExitMouseClicked

    private String getMyPath() {

        String ret = JMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        if (!ret.equals("/")) {
            final String[] splited = ret.split("/");

            ret = "";

            for (int i = 0; i < (splited.length - 1); i++) {
                ret = ret + splited[i] + "/";
            }

        }

        return ret;
    }

    public static String millisToHHmmss(final Date date) {
        try {
            final DateFormat df = new SimpleDateFormat("HH:mm:ss");
            return df.format(date);
        } catch (Exception e) {
            return "?";
        }
    }

    public static String formatearFechaHora2(final Date date) {
        try {
            final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return df.format(date);
        } catch (Exception e) {
            return "?";
        }
    }

    private void setComportamientoToTray() {

        if (SystemTray.isSupported()) {
            final SystemTray tray = SystemTray.getSystemTray();
            final PopupMenu popup = new PopupMenu();

            final TrayIcon icono = new TrayIcon(EResources.getImage(EResources.APP_LOGO), Configs.APP_NAME, popup);
            icono.setImageAutoSize(true);

            icono.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == 1) {
                        comportamientoRestore();
                    }
                }
            });

            try {
                tray.add(icono);
            } catch (final AWTException e1) {
                System.out.println(e1.getStackTrace());
            }
        }

    }

    private void comportamientoRestore() {
        if (isVisible()) {
            //setState(Frame.ICONIFIED);
            setVisible(false);
        } else {
            setVisible(true);
            //setState(Frame.NORMAL);
            requestFocus();
        }
    }

    public void changeTimerDelay(int minutes) {
        timerUpdate.stop();

        if (minutes == 0) {
            timerUpdate.setInitialDelay(20000);
        } else {
            timerUpdate.setInitialDelay(minutes * 60000);
        }

        timerUpdate.start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdDoUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblHost;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblNextUpdate;
    private javax.swing.JLabel lblUpdated;
    private javax.swing.JTextArea txtLog;
    // End of variables declaration//GEN-END:variables

}
