package pl.piwosz.server;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class GroupChatListener implements Callable<String> {
    Socket mySocket;
    private JTextArea messagesTxt;
    ClientFutureCallback<String> ft;

    public GroupChatListener(Socket socket, JTextArea messagesTxt) {
        this.messagesTxt = messagesTxt;
        mySocket = socket;
        ft = new ClientFutureCallback<String>(this);
    }

    @Override
    public String call() throws Exception {
        String txt = mySocket.getInetAddress().getHostName();
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            //nasłuchiwanie klienta na wysłanie info od serwera
            String str;
            while (!(str = in.readLine()).equals("exit")) {
                messagesTxt.append(str + "\r\n");
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
        String s = "Zakończenie nasłuchiwania na chat prywatny";
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