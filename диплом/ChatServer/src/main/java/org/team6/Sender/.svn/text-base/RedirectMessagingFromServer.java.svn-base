package org.team6.Sender;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

public class RedirectMessagingFromServer implements Runnable {
	private Socket socket;
	private Socket internalSocket;

	private ObjectInputStream in;
	private ObjectOutputStream internalOut;

	private static final int INTERNAL_PORT = 6006;
	private static final String MESSAGE_ERROR_EXEC = "Error occured: There is error during execution program";
	private static final Logger LOG = org.apache.log4j.LogManager
			.getLogger(RedirectMessagingFromServer.class.getName());
	
	public RedirectMessagingFromServer(Socket socket) {
		this.socket = socket;		
	}

	public void run() {
		// get internal socket for redirect to second console
		try {
			internalSocket = new Socket("localhost", getPort());
			in = new ObjectInputStream(socket.getInputStream());

			internalOut = new ObjectOutputStream(
					internalSocket.getOutputStream());
		} catch (IOException e1) {
			consoleOut(MESSAGE_ERROR_EXEC);
			return;
		}

		while (true) {
			try {
				String message = in.readUTF();

				// take message and redirect this message to
				// second console
				sendMessage(message);

				if (message.equalsIgnoreCase("bye")) {
					close();

					return;
				}
			} catch (Exception efEx) {
				consoleOut(MESSAGE_ERROR_EXEC);
				try {
					close();
				} catch (Exception e) {
					consoleOut(MESSAGE_ERROR_EXEC);
					return;
				}
				return;
			}
		}
	}

	private int getPort() {
		File fData = new File(System.getProperty("java.io.tmpdir")
				+ "chatPort.data");
		if (fData.exists()) {
			try {
				FileInputStream inPort = new FileInputStream(fData);
				DataInputStream data = new DataInputStream(inPort);
				int portNumber = data.readInt();
				data.close();
				inPort.close();

				return portNumber;
			} catch (FileNotFoundException e) {
				return INTERNAL_PORT;
			} catch (IOException ex) {
				return INTERNAL_PORT;
			}
		}

		return INTERNAL_PORT;
	}

	private void close() throws IOException {
		socket.close();
		internalSocket.close();

		in.close();
		internalOut.close();
	}

	private void sendMessage(String message) throws IOException {
		internalOut.writeUTF(message);
		internalOut.flush();
	}
	
	private void consoleOut(String message) {
		LOG.info(message);
	}
}
