package pl.piwosz.server.ui_server.view;

import javax.swing.*;

public class MainFrameServer extends JFrame{
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
    private JPanel labeledMessagesPanel;

    public MainFrameServer(){
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

    public JPanel getPrivateMessagesPanel() {
        return privateMessagesPanel;
    }

    public JTextArea getPrivateMessagesArea() {
        return privateMessagesArea;
    }

    public JTextField getPrivateMessagesTextField() {
        return privateMessagesTextField;
    }

    public JButton getSendPrivateButton() {
        return sendPrivateButton;
    }

    public JTextField getPrivateNickField() {
        return privateNickField;
    }

    public JButton getGoToPrivateChat() {
        return goToPrivateChat;
    }

    public JPanel getPrivateNickPanel() {
        return privateNickPanel;
    }

    public JPanel getLabeledMessagesPanel() {
        return labeledMessagesPanel;
    }
}
