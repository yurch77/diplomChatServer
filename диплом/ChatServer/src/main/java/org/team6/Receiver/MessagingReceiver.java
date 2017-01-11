package org.team6.Receiver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class MessagingReceiver implements Runnable {	
	private ServerSocket outterServer;
	private Socket outterSocket;
	private ObjectInputStream outterIn;

	private static final int OUTTER_PORT = 6006;
	private static final String MESSAGE_ERROR_EXEC = "Error occured: There is error during execution program";

	private static final Logger LOG = org.apache.log4j.LogManager
			.getLogger(MessagingReceiver.class.getName());
	
	public void run() {
		// bind second console to outter port
		try {
			outterServer = new ServerSocket(getPort());
			outterSocket = outterServer.accept();
			outterIn = new ObjectInputStream(outterSocket.getInputStream());
		} catch (Exception e1) {
			consoleOut(MESSAGE_ERROR_EXEC);
			return;
		}

		while (true) {

			String message = null;
			try {
				message = outterIn.readUTF();
				consoleOut(message);

				if (message.compareToIgnoreCase("bye") == 0) {
					close();

					return;
				}
			} catch (Exception ex) {
				consoleOut(MESSAGE_ERROR_EXEC);
				try {
					close();
				} catch (Exception e) {
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
				FileInputStream in = new FileInputStream(fData);
				DataInputStream data = new DataInputStream(in);
				int portNumber = data.readInt() + 1;

				DataOutputStream out = new DataOutputStream(
						new FileOutputStream(fData));
				out.writeInt(portNumber);

				data.close();

				out.close();
				in.close();

				return portNumber;
			} catch (IOException ex) {
				return OUTTER_PORT;
			}
		} else {
			try {
				if (fData.createNewFile()) {
					FileOutputStream out = new FileOutputStream(fData);
					DataOutputStream outData = new DataOutputStream(out);
					outData.writeInt(OUTTER_PORT);
					outData.close();
				}
				return OUTTER_PORT;
			} catch (IOException ex) {
				return OUTTER_PORT;
			}
		}
	}

	private void close() throws IOException {
		outterServer.close();
		outterSocket.close();

		outterIn.close();
	}	

	private void consoleOut(String message) {
		LOG.info(message);
	}
}
