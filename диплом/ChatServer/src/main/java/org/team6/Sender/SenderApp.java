package org.team6.Sender;

import java.net.Socket;

import org.apache.log4j.Logger;


public final class SenderApp {
	private static final int CHAT_PORT = 2004;
	private static final Logger LOG = org.apache.log4j.LogManager.getLogger(SenderApp.class.getName());
	
	private SenderApp() {
	}

	public static void main(String[] args) {

		try {
			Socket socket = new Socket("localhost", CHAT_PORT);
			consoleOut("Welcome to Chat...");

			RedirectMessagingFromServer senderFromServer = new RedirectMessagingFromServer(
					socket);
			new Thread(senderFromServer).start();

			MessagingSender senderToServer = new MessagingSender(socket);
			new Thread(senderToServer).start();

		} catch (Exception e) {
			consoleOut("Error occured: " + e.getMessage());
			return;
		}
	}
	
	private static void consoleOut(String message) {		
		LOG.info(message);
	}
}
