package pl.piwosz.server;

import pl.piwosz.chat_gui.ui.model.Database;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static pl.piwosz.chat_gui.ui.model.Database.createStatement;
import static pl.piwosz.chat_gui.ui.model.Database.executeUpdate;
import static pl.piwosz.chat_gui.ui.model.Database.getConnection;

public class ServerTCP {
	public static void main(String args[]) {


		if (args.length == 0)
			System.out.println("WprowadŸ numer portu, na którym serwer bêdzie oczekiwa³ na klientów");
		else {
			int port = 0;
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.err.println("WprowadŸ poprawny numer portu: " + e);
				return;
			}

			//sprawdzanie sterownika
			if (Database.checkDriver("com.mysql.jdbc.Driver"))
				System.out.println(" ... BAZA DANYCH OK");
			else
				System.exit(1);
			//  po³¹czenie
			Connection con = getConnection("jdbc:mysql://", "localhost", 3306, "root", "");
			Statement st = createStatement(con);
			// próba wybrania bazy
			if (executeUpdate(st, "USE messenger;") == 0)
				System.out.println("Baza wybrana");
			else {
				System.out.println("Baza nie istnieje! Tworzymy bazê: ");
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
					"CREATE TABLE `publiczne_wiadomosci` (\n" +
							"  `nick` varchar(255) NOT NULL,\n" +
							"  `reciver` varchar(255) NOT NULL,\n" +
							"  `text` text NOT NULL\n" +
							"  `isPrivate` boolean NOT NULL\n" +
							") DEFAULT CHARSET=utf8") == 0)
				System.out.println("Tabela utworzona");
			else
				System.out.println("Tabela nie utworzona!");


			ServerSocket serverSocket = null;
			try {
				// tworzymy socket
				serverSocket = new ServerSocket(port);
				ExecutorService exec = Executors.newCachedThreadPool();
				List<Socket> sockets = new ArrayList<>();
                List<ObjectOutputStream> objectOutputStreams = new ArrayList<>();
				while (true) {
					// czekamy na zg³oszenie klienta ...
					Socket socket = serverSocket.accept();
					sockets.add(socket);
                    objectOutputStreams.add(new ObjectOutputStream(socket.getOutputStream()));
					System.out.println("Pod³¹czono: " + socket);
					// tworzymy w¹tek dla danego po³¹czenia i uruchamiamy go
					exec.execute(new ServerTCPCall(socket, objectOutputStreams).getFt());
				}
			} catch (Exception e) {
				System.err.println(e);
			}finally{
				if(serverSocket != null)
					try {
						serverSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}
}