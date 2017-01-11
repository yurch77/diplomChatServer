package org.team6.Server.Commands;

import java.util.Map.Entry;
import org.team6.Server.ManualConcurrentHashMap;
import org.team6.Server.ServerHandler;
import org.team6.Server.ServerThread;

/**
 * Command for send message to all users in the room
 * 
 * 
 */
public class SendMessageToAllInRoom implements ICommand {
	private String roomName;
	private String senderName;
	private String message;
	private ServerThread currentUserThread;

	private static final String MESSAGE_INVALID_ROOM = "Error occured: Invalid room name";
	private static final String MESSAGE_MESSAGE_TOO_LONG = "Your message is too long! Maximum message length is 1000 charcters.";
	private static final String MESSAGE_EMPTY_MESSAGE = "Your message is empty! Try one more time.";
	private static final String MESSAGE_WRONG_COMMAND = "You are trying to use wrong command\r\nCheck command and try again";
	private static final int MAX_MESSAGE_LENGTH = 1000;

	public void send(ServerThread currentUser, String... messageFromUser) {
		currentUserThread = currentUser;
		if (!messageLengthRequirement(messageFromUser)) {
			return;
		}

		senderName = currentUser.getName();
		roomName = currentUser.getRoomName();
		message = messageFromUser[0];

		ManualConcurrentHashMap<String, ServerThread> currentRoom = getRoom(
				currentUser, roomName);
		if (currentRoom != null) {
			currentRoom.addReader();
			try {
				for (Entry<String, ServerThread> entry : currentRoom.entrySet()) {
					entry.getValue().sendMessage(
							"From [" + senderName + "] " + message);
				}
				currentUser.loggerMessage(message, "all");
			} finally {
				currentRoom.removeReader();
			}
		} else {
			currentUser.sendMessage(MESSAGE_INVALID_ROOM);
		}
	}

	/**
	 * Routine to assure message requirement on length
	 * 
	 * @param messageCommand
	 * @param messageFromUser
	 * @return
	 */
	private boolean messageLengthRequirement(String... messageFromUser) {
		if (messageFromUser[0] == null) {
			currentUserThread.sendMessage(MESSAGE_WRONG_COMMAND);
			return false;
		} else if (messageFromUser.length == 0) {
			currentUserThread.sendMessage(MESSAGE_EMPTY_MESSAGE);
			return false;
		} else if (messageFromUser[0].length() > MAX_MESSAGE_LENGTH) {
			currentUserThread.sendMessage(MESSAGE_MESSAGE_TOO_LONG);
			return false;
		}
		return true;
	}

	private ManualConcurrentHashMap<String, ServerThread> getRoom(
			ServerThread currentUser, String roomName) {
		ServerHandler hServer = currentUser.getServerHandler();
		ManualConcurrentHashMap<String, ServerThread> currentRoom = hServer
				.getEntityes().get(roomName);
		return currentRoom;
	}
}
