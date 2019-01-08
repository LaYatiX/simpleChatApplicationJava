package pl.piwosz.chat_gui.ui.controller;

import pl.piwosz.chat_gui.ui.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsController {
    private MainFrame mainFrame;
    private JButton goToChat;
    private JTabbedPane tabbedPanel;
    private JTextField nickInput;

    public SettingsController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }


    private void initComponents(){
        goToChat = mainFrame.getGoToChat();
        tabbedPanel = mainFrame.getTabbedPanel();
        nickInput = mainFrame.getNickInput();

        nickInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(mainFrame.getNickInput().getText()!=""){
                    goToChat.setEnabled(true);
                }
                else{
                    goToChat.setEnabled(false);
                }
            }
        });

        goToChat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = tabbedPanel.getSelectedIndex();
                tabbedPanel.setSelectedIndex(selectedIndex+1);
            }
        });
    }
}
