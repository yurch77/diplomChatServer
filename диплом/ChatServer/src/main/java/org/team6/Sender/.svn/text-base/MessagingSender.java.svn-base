package org.team6.Sender;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class MessagingSender implements Runnable {	
	private Socket socket;
	private ObjectOutputStream out;

	private static final String MESSAGE_ERROR_EXEC = "Error occured: There is error during execution program";
	private static final Logger LOG = org.apache.log4j.LogManager
			.getLogger(MessagingSender.class.getName());
	
	public MessagingSender(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
		} catch (IOException e) {
			consoleOut(MESSAGE_ERROR_EXEC);
			return;
		} catch (Exception ex) {
			consoleOut(MESSAGE_ERROR_EXEC);
			return;
		}

		// we get new message from client and send it to server
		// ...thats all
		Scanner reader = new Scanner(System.in);
		while (true) {
			try {
				consoleOut(">: ");

				String message = reader.nextLine();
				sendMessage(message);

				if (message.equalsIgnoreCase("bye")) {
					reader.close();
					close();

					return;
				}
			} catch (Exception ex) {
				consoleOut(MESSAGE_ERROR_EXEC);
				reader.close();
				try {
					close();
				} catch (Exception ex1) {
					consoleOut(MESSAGE_ERROR_EXEC);
					return;
				}
				return;
			}
		}
	}

	private void close() throws IOException {
		socket.close();
		out.close();
	}	

	private void sendMessage(String message) throws IOException {
		out.writeUTF(message);
		out.flush();
	}

	private void consoleOut(String message) {
		LOG.info(message);
	}
}