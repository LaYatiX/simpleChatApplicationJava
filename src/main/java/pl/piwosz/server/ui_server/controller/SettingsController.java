package pl.piwosz.server.ui_server.controller;

import pl.piwosz.server.ui_server.view.MainFrameServer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class SettingsController {
    private MainFrameServer mainFrameServer;
    private JButton goToChat;
    private JButton goToPrivateChat;

    private JTextField privateNickField;
    private JTabbedPane tabbedPanel;

    private JTextField nickInput;
    private JTextArea messagesTxt;
    private JPanel labeledMessagesPanel;
    private JTextArea privateMessagesArea;
    private Socket socket;

    ExecutorService exec;

    public SettingsController(MainFrameServer mainFrameServer, Socket socket) {
//        this.socket = socket;
        this.mainFrameServer = mainFrameServer;
        initComponents();
    }

    private void initComponents(){
        goToChat = mainFrameServer.getGoToChat();
        tabbedPanel = mainFrameServer.getTabbedPanel();
        nickInput = mainFrameServer.getNickInput();
        messagesTxt = mainFrameServer.getMessagesTxt();
        labeledMessagesPanel = mainFrameServer.getLabeledMessagesPanel();
        goToPrivateChat = mainFrameServer.getGoToPrivateChat();
        privateNickField = mainFrameServer.getPrivateNickField();
        privateMessagesArea = mainFrameServer.getPrivateMessagesArea();
        goToChat.setEnabled(false);
        goToPrivateChat.setEnabled(false);

        goToChat.addActionListener((e)-> {
            int selectedIndex = tabbedPanel.getSelectedIndex();
            tabbedPanel.setSelectedIndex(selectedIndex+1);
        });

        goToPrivateChat.addActionListener((e)-> {
            int selectedIndex = tabbedPanel.getSelectedIndex();
            tabbedPanel.setSelectedIndex(selectedIndex+2);
        });

        nickInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                nickEnableLogic();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                nickEnableLogic();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                nickEnableLogic();
            }
        });
        privateNickField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                privateNickEnableLogic();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                privateNickEnableLogic();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                privateNickEnableLogic();
            }
        });
    }
    private void nickEnableLogic(){
        if(nickInput.getText().isEmpty())
            goToChat.setEnabled(false);
        else
            goToChat.setEnabled(true);
    }
    private void privateNickEnableLogic(){
        if(!nickInput.getText().isEmpty() && !nickInput.getText().isEmpty())
            goToPrivateChat.setEnabled(true);
        else
            goToPrivateChat.setEnabled(false);
    }
    public JTextField getNickInput() {
        return nickInput;
    }
    public JTextField getPrivateNickField() {
        return privateNickField;
    }
}
