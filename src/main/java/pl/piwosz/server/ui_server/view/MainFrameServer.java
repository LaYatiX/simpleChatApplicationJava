package pl.piwosz.server.ui_server.view;

import javax.swing.*;

public class MainFrameServer extends JFrame{
    public static final int WIDTH = 400;
    public static final int HEIGHT = 250;


    private JPanel serverAdres;
    private JTextField serverPortInput;
    private JTextField databaseAddress;
    private JPanel serverPort;
    private JPanel databaseAddres;
    private JTextField databasePort;
    private JTextField databaseUsername;
    private JTextField databasePassword;
    private JPanel databasePortPanel;
    private JPanel userDatabasePanel;
    private JPanel databasePasswordPanel;
    private JButton connectButton;
    private JPanel settingPanel;
    private JLabel serverLabel;
    private JTextField serverAdresInput;
    private JPanel mainPanel;

    public MainFrameServer(){
        setSize(WIDTH, HEIGHT);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
    }


    public JPanel getServerAdres() {
        return serverAdres;
    }

    public JTextField getServerPortInput() {
        return serverPortInput;
    }

    public JTextField getDatabaseAddress() {
        return databaseAddress;
    }

    public JPanel getServerPort() {
        return serverPort;
    }

    public JPanel getDatabaseAddres() {
        return databaseAddres;
    }

    public JTextField getDatabasePort() {
        return databasePort;
    }

    public JTextField getDatabaseUsername() {
        return databaseUsername;
    }

    public JTextField getDatabasePassword() {
        return databasePassword;
    }

    public JPanel getDatabasePortPanel() {
        return databasePortPanel;
    }

    public JPanel getUserDatabasePanel() {
        return userDatabasePanel;
    }

    public JPanel getDatabasePasswordPanel() {
        return databasePasswordPanel;
    }

    public JButton getConnectButton() {
        return connectButton;
    }
    public JPanel getSettingPanel() {
        return settingPanel;
    }

    public JLabel getServerLabel() {
        return serverLabel;
    }

    public JTextField getServerAdresInput() {
        return serverAdresInput;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
