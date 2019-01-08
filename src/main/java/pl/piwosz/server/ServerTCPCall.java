package pl.piwosz.server;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ServerTCPCall implements Callable<String> {
    Socket mySocket;
    List<Socket> sockets;
    FutureTaskCallback<String> ft;

    public ServerTCPCall(Socket socket, List<Socket> sockets) {
        mySocket = socket;
        this.sockets = sockets;
        ft = new FutureTaskCallback<String>(this);
    }

    public FutureTaskCallback<String> getFt() {
        return ft;
    }

    @Override
    public String call() {
        String txt = mySocket.getInetAddress().getHostName();
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

            /* odbieramy i drukujemy ... */
            String str;
            while (!(str = in.readLine()).equals("exit")) {
                final String str2 = str;
                System.out.println(str);

                sockets.forEach(socket -> {
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println(str2 + " server ");
                    out.flush();
                });
            }

//			ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());

//            A fromServer = (A) ois.readObject();
//			System.out.println(fromServer.name);

            mySocket.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return "done ;) Socket " + txt + " is closed.";
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