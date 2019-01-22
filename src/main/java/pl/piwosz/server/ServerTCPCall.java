package pl.piwosz.server;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static pl.piwosz.chat_gui.ui.model.Database.createStatement;
import static pl.piwosz.chat_gui.ui.model.Database.executeUpdate;

public class ServerTCPCall implements Callable<String> {
    Socket mySocket;
    List<ObjectOutputStream> objectOutputStreams;
    FutureTaskCallback<String> ft;
    Connection connection;
    Lock lock;


    public ServerTCPCall(Socket socket, List<ObjectOutputStream> objectOutputStreams, Connection connection) {
        this.connection = connection;
        mySocket = socket;
        this.objectOutputStreams = objectOutputStreams;
        ft = new FutureTaskCallback<String>(this);
        lock = new ReentrantLock();// utworzenie rygla

    }

    public FutureTaskCallback<String> getFt() {
        return ft;
    }

    @Override
    public String call() {
        String txt = mySocket.getInetAddress().getHostName();
        try {
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());

            Message message;
            while (!(message = (Message) ois.readObject()).equals("")) {
                final Message message2 = message;

                objectOutputStreams.forEach(out -> {
                    try {
                        out.writeObject(message2);
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                insertMessage(message);
            }

            mySocket.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return "done ;) Socket " + txt + " is closed.";
    }

    public void insertMessage(Message message){
        lock.lock();
        Statement st = createStatement(this.connection);
        String sql = "INSERT INTO messages (nick, reciver, text, isPrivate) VALUES('"+message.getNick()+"', '"+message.getReciver()+"', '"+message.getText()+"', "+message.isPrivate()+");";
        System.out.println(sql);
        executeUpdate(st, sql);
        lock.unlock();
    }
}

/**
 * @param <T>
 * @author S³awek Klasa rozsze¿aj¹ca klasê FutureTask
 */
class FutureTaskCallback<T> extends FutureTask<T> {
    public FutureTaskCallback(Callable<T> callable) {
        super(callable);
    }

    /**
     * Metoda uruchamiana po zakoñczeniu wykonywania zadania
     */
    public void done() {
        String msg = "Wynik: ";
        if (isCancelled())
            msg += "Anulowane.";
        else {
            try {
                msg += get();
            } catch (Exception exc) {
                msg += exc.toString();
            }
        }
        System.out.println("\n" + msg + "\n");
    }
}