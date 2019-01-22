package pl.piwosz.chat_gui.ui.controller;

import pl.piwosz.chat_gui.ui.model.Database;
import pl.piwosz.chat_gui.ui.view.MainFrame;
import pl.piwosz.server.GroupChatListener;
import pl.piwosz.server.Message;
import pl.piwosz.server.ui_server.controller.ServerMainController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static pl.piwosz.chat_gui.ui.model.Database.*;

public class MainFrameController {
    private MainFrame mainFrame;

    private JButton sendBtn;
    private JButton sendPrivateButton;
    private JTextArea messagesTxt;
    private JTextArea privateMessagesArea;
    private JTextField privateNickField;
    private JTextField nickField;
    private JTextField messageInputTxt;
    private JTextField privateMessagesTextField;
    private SettingsController settingsController;
    private ServerMainController connection;
    private JTextField adresPortInput;
    private JTextField serverPortInput;
    private JButton connectButton;

    private Socket socket;

    private String nick;
    private ObjectOutputStream ous;
    Lock lock;

    public MainFrameController() {
        initComponents();

        initListeners();
    }

    public void showMainFrameWindow() {
        mainFrame.setVisible(true);
    }

    private void initComponents() {
        connection = new ServerMainController();
        connection.connectToDatabase();
        lock = new ReentrantLock();
        this.mainFrame = new MainFrame();
        settingsController = new SettingsController(this.mainFrame, socket);

        sendBtn = mainFrame.getSendBtn();
        sendPrivateButton = mainFrame.getSendPrivateButton();
        messagesTxt = mainFrame.getMessagesTxt();
        messageInputTxt = mainFrame.getMessageInputTxt();
        privateMessagesTextField = mainFrame.getPrivateMessagesTextField();
        privateMessagesArea = mainFrame.getPrivateMessagesArea();
        privateNickField = mainFrame.getPrivateNickField();
        nickField = mainFrame.getServerAdresInput();
        connectButton = mainFrame.getConnectButton();
        adresPortInput = mainFrame.getAdresPortInput();
        serverPortInput = mainFrame.getServerPortInput();
        mainFrame.getMessagesPanel().setVisible(false);
        mainFrame.getIntroPanel().setVisible(true);
        messagesTxt.append(getLastMessages());

    }

    private void initListeners() {
        final ExecutorService exec = Executors.newCachedThreadPool();

        sendBtn.addActionListener(new SendMessageListener());
        sendPrivateButton.addActionListener(new SendPrivateMessageListener());
        connectButton.addActionListener((e)->{
            try {
                socket = new Socket(InetAddress.getByName(adresPortInput.getText()), Integer.parseInt(serverPortInput.getText()));
                try {
                    ous = new ObjectOutputStream(socket.getOutputStream());
                    exec.execute(new GroupChatListener(socket, messagesTxt, privateMessagesArea, privateNickField, nickField).getFt());

                } catch (IOException e1) {
                    connectButton.setText("Bledny adres lub port serwera");
                    e1.printStackTrace();
                }

            } catch (IOException w) {
                connectButton.setText("Bledny adres lub port serwera");
                w.printStackTrace();
            }
            connectButton.setText("Poloczono");
            connectButton.setEnabled(false);
        });
    }

    private class SendMessageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = messageInputTxt.getText();
            nick = settingsController.getNickInput().getText();
            Message message = new Message(text, nick, null, false);
            writeToServer(message);
        }
    }

    private class SendPrivateMessageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = privateMessagesTextField.getText();
            nick = settingsController.getNickInput().getText();
            Message message = new Message(text, nick, settingsController.getPrivateNickField().getText(), true);
            writeToServer(message);
        }
    }

    private void writeToServer(Message data) {
        try {
            ous.writeObject(data);
            ous.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public String getLastMessages(){
        lock.lock();
        Statement st = createStatement(this.connection.getCon());
        String sql = "SELECT * FROM `messages` WHERE `time` >= NOW() - INTERVAL 60 SECOND";
        System.out.println(sql);
        String res= Database.printDataFromQuery(executeQuery(st, sql));
        lock.unlock();
        return res;
    }

}
