package org.team6.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import org.apache.log4j.Logger;

public class ServerHandler {	
	private Map<String, ManualConcurrentHashMap<String, ServerThread>> connectionEntity = new ManualConcurrentHashMap<String, ManualConcurrentHashMap<String, ServerThread>>();
	private static final String MESSAGE_SERVER_START = "Chat Server started";	
	private static final int SERVER_PORT = 2004;
	private boolean stopServer = false;
	private static final Logger LOG = org.apache.log4j.LogManager
			.getLogger(ServerHandler.class.getName());
	
	public static final String ROOM_ON_ENTER = "Main";

	public ServerHandler() {
		connectionEntity
				.put(ROOM_ON_ENTER, new ManualConcurrentHashMap<String, ServerThread>());
		connectionEntity.put("Auto-room", new ManualConcurrentHashMap<String, ServerThread>());
		connectionEntity.put("Adult-room", new ManualConcurrentHashMap<String, ServerThread>());
		connectionEntity.put("Films-room", new ManualConcurrentHashMap<String, ServerThread>());
	}

	public Map<String, ManualConcurrentHashMap<String, ServerThread>> getEntityes() {
		return connectionEntity;
	}

	public void start() throws IOException {
		ServerSocket serverSocket = new ServerSocket(SERVER_PORT);		
		LOG.info(MESSAGE_SERVER_START);

		try {
			while (!stopServer) {
				Socket connection = serverSocket.accept();
				ServerThread sThread = new ServerThread(connection, this);
				new Thread(sThread).start();
				
			}
		} finally {
			serverSocket.close();
		}

	}

	public void stop() {
		this.stopServer = true;
	}

}
