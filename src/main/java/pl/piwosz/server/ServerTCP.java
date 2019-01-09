package pl.piwosz.server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerTCP {
	public static void main(String args[]) {
		if (args.length == 0)
			System.out.println("Wprowad� numer portu, na kt�rym serwer b�dzie oczekiwa� na klient�w");
		else {
			int port = 0;
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.err.println("Wprowad� poprawny numer portu: " + e);
				return;
			}
			ServerSocket serverSocket = null;
			try {
				// tworzymy socket
				serverSocket = new ServerSocket(port);
				ExecutorService exec = Executors.newCachedThreadPool();
				List<Socket> sockets = new ArrayList<>();
				while (true) {
					// czekamy na zg�oszenie klienta ...
					Socket socket = serverSocket.accept();
					sockets.add(socket);
					// tworzymy w�tek dla danego po��czenia i uruchamiamy go
					exec.execute(new ServerTCPCall(socket, sockets).getFt());
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