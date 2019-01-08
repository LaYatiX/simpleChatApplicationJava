package pl.piwosz.chat_gui.ui.controller;

import pl.piwosz.chat_gui.ui.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrameController {
    private MainFrame mainFrame;

    private JButton sendBtn;
    private JTextArea messagesTxt;
    private JTextField messageInputTxt;

    private SettingsController settingsController;

    public MainFrameController() {
        initComopnents();
        initListeners();
    }

    public void showMainFrameWindow(){
        mainFrame.setVisible(true);
    }

    private void initComopnents(){
        this.mainFrame = new MainFrame();

        settingsController = new SettingsController(this.mainFrame);

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
        }
    }
}
