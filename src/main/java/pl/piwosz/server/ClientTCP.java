package tcp.call;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
class A implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -1590808355799495667L;
String name;
	public A(String name) {
	super();
	this.name = name;
}
	@Override
	public String toString() {
		return "A [" + name + "]";
	}
	
}
public class ClientTCP {
	public static void main(String args[]) {
		if (args.length < 2)
			System.out.println("WprowadŸ adres serwera TCP oraz numer portu");
		else{
			int port = 0;
			try {
				port = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				System.err.println("WprowadŸ poprawny numer portu: " + e);
				return;
			}
			try {
				Socket socket = new Socket(InetAddress.getByName(args[0]), port);
				PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				Scanner sc = new Scanner(System.in);
				String str;
//				while (sc.hasNext()){
//					str = sc.nextLine();
//					out.println(str);
//					out.flush();

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String str2;
                    while (!(str2 = in.readLine()).isEmpty()) {
//                        String str2 = in.readLine();
                        if (!str2.isEmpty()) System.out.println("Od serwera" + str2);
                    }
//					if(str.equals("exit"))
//						break;
//				}
//				ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream());
//				ous.writeObject(new A("Ala ma kota"));
//				sc.close();
//				socket.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}
}
