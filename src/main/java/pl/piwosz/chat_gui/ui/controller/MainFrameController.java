package pl.piwosz.chat_gui.ui.controller;

import pl.piwosz.chat_gui.ui.view.MainFrame;
import pl.piwosz.server.GroupChatListener;
import pl.piwosz.server.Message;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private Socket socket;

    private String nick;
    private ObjectOutputStream ous;

    public MainFrameController() {
        try {
            socket = new Socket(InetAddress.getByName("0.0.0.0"), 1234);

        } catch (IOException e) {
            e.printStackTrace();
        }
        initComponents();
        initListeners();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new GroupChatListener(socket, messagesTxt, privateMessagesArea, privateNickField, nickField).getFt());
    }

    public void showMainFrameWindow() {
        mainFrame.setVisible(true);
    }

    private void initComponents() {
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
        mainFrame.getMessagesPanel().setVisible(false);
        mainFrame.getIntroPanel().setVisible(true);
        try {
            ous = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e1) {
            e1.printStackTrace();
        }

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

}
