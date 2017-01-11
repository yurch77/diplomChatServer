package org.team6.Server;

import java.io.IOException;

import org.apache.log4j.Logger;

public final class MainApp {
	private static final Logger LOG = org.apache.log4j.LogManager
			.getLogger(MainApp.class.getName());
	
	private MainApp() {
	}

	private static final String MESSAGE_ERROR = "IO Exception occur. Program exists";

	public static void main(String[] args) throws IOException {
		ServerHandler server = new ServerHandler();
		try {
			server.start();
		} catch (IOException e) {
			LOG.info(MESSAGE_ERROR);
			return;
		}
		server.stop();
	}
}
