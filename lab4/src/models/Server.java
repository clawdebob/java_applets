package models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


class Server {
	public static void main(String[] args){
		try {
			System.out.println("Server is running");
			int port = 3333;
			// создание серверного сокета
			ServerSocket ss = new ServerSocket(port);
			// Ждет клиентов и для каждого создает отдельный поток
			while (true) {
				Socket s = ss.accept();
				ServerConnectionProcessor p =
					new ServerConnectionProcessor(s);
				p.start();
			}
		}
		catch(Exception e) { System.out.println(e); }
	}
}

class ServerConnectionProcessor extends Thread {
	private Socket sock;
	public ServerConnectionProcessor(Socket s) {
		sock = s;
	}
	public void run() {
		try {
			// Получает запрос
			DataInputStream inStream = new DataInputStream(
					sock.getInputStream());
			String operationId = inStream.readUTF();
			System.out.println(operationId);
			// Отправляет ответ
			DataOutputStream outStream = new DataOutputStream(
					sock.getOutputStream());
			outStream.writeUTF("Success");
			// Подождем немного и завершим поток
			sleep(1000);
			inStream.close(); outStream.close(); sock.close();

		}
		catch(Exception e) { System.out.println(e); }
	}
}