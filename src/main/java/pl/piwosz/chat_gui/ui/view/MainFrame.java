package pl.piwosz.chat_gui.ui.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private JPanel mainPanel;
    private JButton sendBtn;
    private JTextArea messagesTxt;
    private JTextField messageInputTxt;

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
}
