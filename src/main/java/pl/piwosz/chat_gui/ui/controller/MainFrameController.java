package pl.piwosz.chat_gui.ui.controller;

import pl.piwosz.chat_gui.ui.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MainFrameController {
    private MainFrame mainFrame;

    private JButton sendBtn;
    private JTextArea messagesTxt;
    private JTextField messageInputTxt;

    private SettingsController settingsController;

    private Socket socket;

    public MainFrameController() {
        initComopnents();
        initListeners();
        try {
            socket = new Socket(InetAddress.getByName("0.0.0.0"), 1234);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainFrameWindow(){
        mainFrame.setVisible(true);
    }

    private void initComopnents(){
        this.mainFrame = new MainFrame();

        settingsController = new SettingsController(this.mainFrame, socket);

        sendBtn = mainFrame.getSendBtn();
        messagesTxt = mainFrame.getMessagesTxt();
        messageInputTxt = mainFrame.getMessageInputTxt();

        mainFrame.getMessagesPanel().setVisible(false);
        mainFrame.getIntroPanel().setVisible(true);
    }

    private void initListeners() {
        sendBtn.addActionListener(new SendMessageListener());
    }

    private class SendMessageListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String text = messageInputTxt.getText();
            messagesTxt.append(text);
            try {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.println(text);
        		out.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
}
