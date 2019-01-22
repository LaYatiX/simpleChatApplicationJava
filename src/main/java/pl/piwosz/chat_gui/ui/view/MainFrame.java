package pl.piwosz.chat_gui.ui.view;

import javax.swing.*;

public class MainFrame extends JFrame{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private JPanel mainPanel;

    private JPanel messagesPanel;
    private JPanel introPanel;

    private JButton sendBtn;
    private JTextArea messagesTxt;
    private JTextField messageInputTxt;

    private JLabel serverLabel;
    private JTabbedPane tabbedPanel;
    private JTextField serverAdresInput;
    private JPanel settingPanel;
    private JButton goToChat;

    private JPanel privateMessagesPanel;
    private JTextArea privateMessagesArea;
    private JTextField privateMessagesTextField;
    private JButton sendPrivateButton;
    private JTextField privateNickField;
    private JButton goToPrivateChat;
    private JPanel privateNickPanel;
    private JTextField adresPortInput;
    private JTextField serverPortInput;
    private JButton connectButton;
    private JPanel labeledMessagesPanel;

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
    public JLabel getServerLabel() {
        return serverLabel;
    }

    public JTabbedPane getTabbedPanel() {
        return tabbedPanel;
    }

    public JTextField getServerAdresInput() {
        return serverAdresInput;
    }

    public JPanel getSettingPanel() {
        return settingPanel;
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

    public JTextField getAdresPortInput() {
        return adresPortInput;
    }

    public JTextField getServerPortInput() {
        return serverPortInput;
    }

    public JButton getConnectButton() {
        return connectButton;
    }
}
