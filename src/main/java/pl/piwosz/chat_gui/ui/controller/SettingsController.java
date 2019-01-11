package pl.piwosz.chat_gui.ui.controller;

import pl.piwosz.chat_gui.ui.view.MainFrame;
import pl.piwosz.server.GroupChatListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettingsController {
    private MainFrame mainFrame;
    private JButton goToChat;
    private JButton goToPrivateChat;
    private JTextField privateNickField;
    private JTabbedPane tabbedPanel;

    private JTextField nickInput;
    private JTextArea messagesTxt;
    private JPanel labeledMessagesPanel;
    private Socket socket;

    ExecutorService exec;

    public SettingsController(MainFrame mainFrame, Socket socket) {
        this.socket = socket;
        this.mainFrame = mainFrame;
        exec = Executors.newCachedThreadPool();
        initComponents();
    }


    private void initComponents(){
        goToChat = mainFrame.getGoToChat();
        tabbedPanel = mainFrame.getTabbedPanel();
        nickInput = mainFrame.getNickInput();
        messagesTxt = mainFrame.getMessagesTxt();
        labeledMessagesPanel = mainFrame.getLabeledMessagesPanel();
        goToPrivateChat = mainFrame.getGoToPrivateChat();
        privateNickField = mainFrame.getPrivateNickField();
        goToChat.setEnabled(false);
        goToPrivateChat.setEnabled(false);
        exec.execute(new GroupChatListener(socket, messagesTxt).getFt());


        goToChat.addActionListener((e)-> {
            int selectedIndex = tabbedPanel.getSelectedIndex();
            tabbedPanel.setSelectedIndex(selectedIndex+1);
        });

        goToPrivateChat.addActionListener((e)-> {
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
}
