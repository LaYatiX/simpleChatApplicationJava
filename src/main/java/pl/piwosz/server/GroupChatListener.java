package pl.piwosz.server;

import pl.piwosz.chat_gui.ui.controller.SettingsController;
import pl.piwosz.chat_gui.ui.view.MainFrame;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class GroupChatListener implements Callable<String> {
    private Socket mySocket;
    private JTextArea messagesTxt;
    private JTextArea privateMessagesArea;
    private JTextField privateNickField;
    private  JTextField nickField;

    private ClientFutureCallback<String> ft;
    public GroupChatListener(Socket socket, JTextArea messagesTxt, JTextArea privateMessagesArea, JTextField privateNickField, JTextField nickField) {
        this.messagesTxt = messagesTxt;
        this.privateMessagesArea = privateMessagesArea;
        this.privateNickField = privateNickField;
        this.nickField = nickField;
        mySocket = socket;
        ft = new ClientFutureCallback<String>(this);
    }

    @Override
    public String call() throws Exception {
        String txt = mySocket.getInetAddress().getHostName();
        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            ObjectInputStream in = new ObjectInputStream(mySocket.getInputStream());
            //nasłuchiwanie klienta na wysłanie info od serwera
            Message message;
            while (!(message = (Message) in.readObject()).equals("")) {
                if(!message.isPrivate() && message.getReciver() == null)
                    messagesTxt.append(message + "\r\n");
                else{
                    String sender = message.getNick();
                    String reciver = message.getReciver();
                    String myNick = nickField.getText();
                    String yourNick = privateNickField.getText();
                    if(sender.equals(myNick) || sender.equals(yourNick) || reciver.equals(myNick) || reciver.equals(yourNick)){
                        privateMessagesArea.append(message + "\r\n");
                    }
                }
            }

            mySocket.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return "";
    }
    public ClientFutureCallback<String> getFt() {
        return ft;
    }
}


class ClientFutureCallback<T> extends FutureTask<T> {
    public ClientFutureCallback(Callable<T> callable) {
        super(callable);
    }

    /**
     * Metoda uruchamiana po zakończeniu wykonywania zadania
     */
    public void done() {
        String s = "Zakończenie nasłuchiwania na chat grupowy";
        if (isCancelled())
            s += "Anulowano";
        else {
            try {
                s += get();
            } catch (Exception exc) {
                s += exc.toString();
            }
        }
        System.out.println("\n" + s + "\n");
    }
}