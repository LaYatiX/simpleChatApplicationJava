package pl.piwosz.chat_gui.ui.controller;

import pl.piwosz.chat_gui.ui.view.MainFrame;
import pl.piwosz.server.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class MainFrameController {
    private MainFrame mainFrame;

    private JButton sendBtn;
    private JButton sendPrivateButton;
    private JTextArea messagesTxt;
    private JTextField messageInputTxt;
    private JTextField privateMessagesTextField;

    private SettingsController settingsController;

    private Socket socket;

    private String nick;
    ObjectOutputStream ous;

    public MainFrameController() {
        try {
            socket = new Socket(InetAddress.getByName("0.0.0.0"), 1234);
            ous = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        initComponents();
        initListeners();
    }

    public void showMainFrameWindow(){
        mainFrame.setVisible(true);
    }

    private void initComponents(){
        this.mainFrame = new MainFrame();
        settingsController = new SettingsController(this.mainFrame, socket);

        sendBtn = mainFrame.getSendBtn();
        sendPrivateButton = mainFrame.getSendPrivateButton();
        messagesTxt = mainFrame.getMessagesTxt();
        messageInputTxt = mainFrame.getMessageInputTxt();
        privateMessagesTextField = mainFrame.getPrivateMessagesTextField();

        mainFrame.getMessagesPanel().setVisible(false);
        mainFrame.getIntroPanel().setVisible(true);
    }

    private void initListeners() {
        sendBtn.addActionListener(new SendMessageListener());
        sendPrivateButton.addActionListener(new SendPrivateMessageListener());
    }

    private class SendMessageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = messageInputTxt.getText();
            nick = settingsController.getNickInput().getText();
            Message message = new Message(text, nick, false);
            writeToServer(socket, message);
        }
    }

    private class SendPrivateMessageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = privateMessagesTextField.getText();
            nick = settingsController.getNickInput().getText();
            Message message = new Message(text, nick, true);
            writeToServer(socket, message);
        }
    }
    private void writeToServer(Socket socket, Message data){
        try {
            ous.writeObject(data);
            ous.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
