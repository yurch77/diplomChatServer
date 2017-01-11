package org.team6.Client;

import java.io.IOException;

import org.apache.log4j.Logger;

public final class ClientApp {
	private static final Logger LOG = org.apache.log4j.LogManager.getLogger(ClientApp.class.getName());
	
	private ClientApp() {
	}

	private static final String MESSAGE_ERROR = "There is exception while executing program";

	public static void main(String[] args) {
		try {
			Runtime.getRuntime().exec("cmd /c start receiver.cmd");
			Thread.sleep(1000);
			Runtime.getRuntime().exec("cmd /c start sender.cmd");
		} catch (IOException e) {
			LOG.info(MESSAGE_ERROR);
		}
		catch(InterruptedException ex){
			LOG.info(MESSAGE_ERROR);
		}
	}
}
