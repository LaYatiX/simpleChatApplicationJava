package pl.piwosz.chat_gui.ui.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private JPanel mainPanel;

    private JPanel messagesPanel;
    private JPanel introPanel;

    private JButton sendBtn;
    private JTextArea messagesTxt;
    private JTextField messageInputTxt;

    private JLabel introLabel;
    private JTabbedPane tabbedPanel;
    private JTextField nickInput;
    private JPanel nickPanel;
    private JButton goToChat;
    private JPanel privateMessagesPanel;
    private JTextArea privateMessagesArea;
    private JTextField privateMessagesTextField;
    private JButton sendPrivateButton;
    private JTextField privateNickField;
    private JButton goToPrivateChat;
    private JPanel privateNickPanel;

    public MainFrame(){
        setSize(WIDTH, HEIGHT);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);

    }

    public JButton getSendBtn() {
        return sendBtn;
    }

    public JTextField getMessageInputTxt() {
        return messageInputTxt;
    }

    public JTextArea getMessagesTxt() {
        return messagesTxt;
    }

    public JPanel getMessagesPanel() {
        return messagesPanel;
    }

    public JPanel getIntroPanel() {
        return introPanel;
    }
    public JLabel getIntroLabel() {
        return introLabel;
    }

    public JTabbedPane getTabbedPanel() {
        return tabbedPanel;
    }

    public JTextField getNickInput() {
        return nickInput;
    }

    public JPanel getNickPanel() {
        return nickPanel;
    }

    public JButton getGoToChat() {
        return goToChat;
    }
}
