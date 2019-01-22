package pl.piwosz.server.ui_server.controller;

import pl.piwosz.chat_gui.ui.model.Database;
import pl.piwosz.server.Message;
import pl.piwosz.server.ServerTCPCall;
import pl.piwosz.server.ui_server.view.MainFrameServer;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;
import static pl.piwosz.chat_gui.ui.model.Database.createStatement;
import static pl.piwosz.chat_gui.ui.model.Database.executeUpdate;
import static pl.piwosz.chat_gui.ui.model.Database.getConnection;

public class ServerMainController {

    private Integer port;
    MainFrameServer mainFrameServer;
    JButton connectButton;
    ServerSocket serverSocket;
    Connection con;

    public ServerMainController() {
        initComponents();
    }

    public void initComponents() {
        mainFrameServer = new MainFrameServer();
    }

    public void showMainFrameWindow() {
        mainFrameServer.setVisible(true);
    }

    public void initServer() {
        try {
            port = Integer.parseInt(mainFrameServer.getServerPortInput().getText());
        } catch (NumberFormatException e) {
            System.err.println("Wprowadź poprawny numer portu: " + e);
            return;
        }

        connectButton = mainFrameServer.getConnectButton();
        connectButton.addActionListener(w -> {
            connectButton.setText("Polaczono");
        });

        try {
            sleep(10);
            connectToDatabase();
            startListening();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (serverSocket != null)
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    connectButton.setText("Coś nie pykło");
                    e.printStackTrace();
                }
        }

    }

    public void startListening() throws IOException{

            // tworzymy socket
            serverSocket = new ServerSocket(Integer.parseInt(mainFrameServer.getServerPortInput().getText()));
            ExecutorService exec = Executors.newCachedThreadPool();
            List<Socket> sockets = new ArrayList<>();
            List<ObjectOutputStream> objectOutputStreams = new ArrayList<>();
            while (true) {
                // czekamy na zgłoszenie klienta ...
                Socket socket = serverSocket.accept();
                sockets.add(socket);
                objectOutputStreams.add(new ObjectOutputStream(socket.getOutputStream()));
                System.out.println("Podlaczono: " + socket);
                // tworzymy wątek dla danego połączenia i uruchamiamy go
                exec.execute(new ServerTCPCall(socket, objectOutputStreams, con).getFt());
            }
    }

    public void connectToDatabase() {
        //sprawdzanie sterownika
        if (Database.checkDriver("com.mysql.jdbc.Driver"))
            System.out.println(" ... BAZA DANYCH OK");
        else
            System.exit(1);
        //  połączenie

        con = getConnection("jdbc:mysql://",
                mainFrameServer.getDatabaseAddress().getText(),
                Integer.parseInt(mainFrameServer.getDatabasePort().getText()),
                mainFrameServer.getDatabaseUsername().getText(),
                mainFrameServer.getDatabasePassword().getText());
        Statement st = createStatement(con);
        // próba wybrania bazy
        if (executeUpdate(st, "USE messenger;") == 0)
            System.out.println("Baza wybrana");
        else {
            System.out.println("Baza nie istnieje! Tworzymy bazę: ");
            if (executeUpdate(st, "create Database messenger;") == 1)
                System.out.println("Baza utworzona");
            else
                System.out.println("Baza nieutworzona!");
            if (executeUpdate(st, "USE messenger;") == 0)
                System.out.println("Baza wybrana");
            else
                System.out.println("Baza niewybrana!");
        }
        if (executeUpdate(st,
                "CREATE TABLE IF NOT EXISTS messages (\n" +
                        "  `nick` varchar(255) NOT NULL,\n" +
                        "  `reciver` varchar(255) NOT NULL,\n" +
                        "  `text` text NOT NULL,\n" +
                        "  `isPrivate` BOOLEAN NOT NULL, \n" +
                        "  `time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP \n" +
                        ") DEFAULT CHARSET=utf8") == 0)
            System.out.println("Tabela utworzona");
        else
            System.out.println("Tabela nie utworzona!");
    }
    public Connection getCon() {
        return con;
    }
}

