package org.team6.Receiver;

import org.apache.log4j.Logger;

public final class ReceiverApp {
	private static final Logger LOG = org.apache.log4j.LogManager
			.getLogger(ReceiverApp.class.getName());
	
	private ReceiverApp() {
	}

	public static void main(String[] args) {
		consoleOut("Initializing message console...");
		try {
			MessagingReceiver receiver = new MessagingReceiver();
			new Thread(receiver).start();
			
			consoleOut("Done");

		} catch (Exception e) {
			consoleOut("Error occured: " + e.getMessage());
			return;
		}
	}
	
	private static void consoleOut(String message) {
		LOG.info(message);
	}
}
