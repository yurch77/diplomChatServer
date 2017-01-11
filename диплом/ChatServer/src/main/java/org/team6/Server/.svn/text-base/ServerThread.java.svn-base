package org.team6.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class ServerThread implements Runnable {
	private ServerHandler server;
	private Socket socket;
	private ObjectOutputStream outputStream;
	private static final Logger LOG = org.apache.log4j.LogManager
			.getLogger(ServerThread.class.getName());

	// user name
	private String name;

	// room name
	private String roomName;

	private static final String MESSAGE_REGISTER = "Please register. Enter your name";
	private static final String MESSAGE_NICK_EXISTED = "Error occured: Nick already exists";
	private static final String MESSAGE_NICK_COMMAND_BAD = "It is bad idea...";
	private static final String MESSAGE_NICK_TOO_LONG = "Your nick is too long! Maximum nick length is 100 characters.";
	private static final String MESSAGE_EMPTY_NICK = "Your nick is empty! Try one more time.";
	private static final int MAX_NICK_LENGTH = 100;

	public ServerThread(Socket socket, ServerHandler server) {
		this.server = server;
		this.socket = socket;

		roomName = ServerHandler.ROOM_ON_ENTER;
	}

	public String getName() {
		return name;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public ServerHandler getServerHandler() {
		return server;
	}

	public void run() {
		String message = "";
		ObjectInputStream inputStream = null;
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.flush();
			inputStream = new ObjectInputStream(socket.getInputStream());

			registerUser(inputStream);

			CommandExecuter executer = CommandExecuter.getCommandExecuter();
			do {
				message = inputStream.readUTF();
				executer.executeUserCommand(this, message);

			} while (!message.equals("bye"));

			// remove user from rooms
			server.getEntityes().get(roomName).remove(name);

			// logger
			consoleOut(name + " disconnected at " + new Date().toString());

		} catch (Exception e) {
			consoleOut("User " + name + " left without promt at "
					+ new Date().toString());
		} finally {
			try {
				server.getEntityes().get(roomName).remove(name);
				outputStream.close();
			} catch (IOException e) {
				consoleOut("User " + name + " throws IOException while exiting");
			}
		}

		// stop current serverThread
		return;
	}

	private void registerUser(ObjectInputStream inputStream) {
		sendMessage(MESSAGE_REGISTER);

		// check if name is unique
		String newName = uniqueNick(inputStream);

		name = newName;
		sendMessage(name + " is registered in chat");
		sendMessage("Your default room is: " + roomName);

		ManualConcurrentHashMap<String, ServerThread> defaultRoom = server
				.getEntityes().get(ServerHandler.ROOM_ON_ENTER);
		defaultRoom.put(name, this);

		// logger
		Date date = new Date();
		consoleOut(name + " connected at " + date.toString());
	}

	// Routine to resolve if nick is unique
	private String uniqueNick(ObjectInputStream inputStream) {
		boolean nickUniqueRequired = true;
		String newName = "";

		do {
			nickUniqueRequired = false;
			try {
				newName = inputStream.readUTF();
			} catch (Exception ex) {
				return null;
			}

			if (newName.compareTo("bye") == 0) {
				sendMessage(MESSAGE_NICK_COMMAND_BAD);
				nickUniqueRequired = true;
				continue;
			}

			// White spaces in the beginning and in the end of user's nick are
			// not convenient.
			newName = newName.trim();
			if (newName.equals(" ")) {
				newName = "";
			}

			if (newName.length() == 0) {
				sendMessage(MESSAGE_EMPTY_NICK);
				nickUniqueRequired = true;
				continue;
			}

			if (newName.length() > MAX_NICK_LENGTH) {
				sendMessage(MESSAGE_NICK_TOO_LONG);
				nickUniqueRequired = true;
				continue;
			}

			for (Entry<String, ManualConcurrentHashMap<String, ServerThread>> entry : getServerHandler()
					.getEntityes().entrySet()) {
				// check for every room
				if (entry.getValue().containsKey(newName)) {
					nickUniqueRequired = true;
					sendMessage(MESSAGE_NICK_EXISTED);
					continue;
				}
			}

		} while (nickUniqueRequired);

		return newName;
	}

	public void sendMessage(String string) {
		try {
			outputStream.writeUTF(string);
			outputStream.flush();
		} catch (IOException ioException) {
			consoleOut("User " + name + " throws error while sending command");
		}
	}

	public void loggerMessage(String message, String receiverName) {
		// logger
		consoleOut("Message from " + name + " at " + new Date().toString()
				+ " to " + receiverName + ": " + message);
	}

	private void consoleOut(String message) {
		LOG.info(message);
	}
}
