package pl.piwosz.chat_gui.ui.controller;

import pl.piwosz.chat_gui.ui.view.MainFrame;
import pl.piwosz.server.ClientTCPCall;
import pl.piwosz.server.ServerTCPCall;

import javax.swing.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettingsController {
    private MainFrame mainFrame;
    private JButton goToChat;
    private JTabbedPane tabbedPanel;
    private JTextField nickInput;
    private JTextArea messagesTxt;
    Socket socket;

    public SettingsController(MainFrame mainFrame, Socket socket) {
        this.socket = socket;
        this.mainFrame = mainFrame;
        initComponents();
    }


    private void initComponents(){
        goToChat = mainFrame.getGoToChat();
        tabbedPanel = mainFrame.getTabbedPanel();
        nickInput = mainFrame.getNickInput();
        messagesTxt = mainFrame.getMessagesTxt();

        nickInput.addActionListener((e) -> {
                if(mainFrame.getNickInput().getText()!=""){
                    goToChat.setEnabled(true);
                }
                else{
                    goToChat.setEnabled(false);
                }
        });

        goToChat.addActionListener((e)-> {
            int selectedIndex = tabbedPanel.getSelectedIndex();
            tabbedPanel.setSelectedIndex(selectedIndex+1);
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new ClientTCPCall(socket, messagesTxt).getFt());
        });
    }
}
