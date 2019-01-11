package pl.piwosz.server;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class PrivateChatListener implements Callable<String> {
    Socket mySocket;
    private JTextArea privateMessagesArea;
    PrivateFutureCallback<String> ft;

    public PrivateChatListener(Socket socket, JTextArea privateMessagesArea) {
        this.privateMessagesArea = privateMessagesArea;
        mySocket = socket;
        ft = new PrivateFutureCallback<String>(this);
    }

    @Override
    public String call() throws Exception {
        //nasłuchiwanie klienta na info od serwera
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            String str;
            while (!(str = in.readLine()).equals("exit")) {
                privateMessagesArea.append(str + "\r\n");
            }

            mySocket.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return "";
    }
    public PrivateFutureCallback<String> getFt() {
        return ft;
    }
}


class PrivateFutureCallback<T> extends FutureTask<T> {
    public PrivateFutureCallback(Callable<T> callable) {
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