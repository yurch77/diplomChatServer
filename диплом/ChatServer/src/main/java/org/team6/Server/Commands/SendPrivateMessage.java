package org.team6.Server.Commands;

import java.util.Map.Entry;

import org.team6.Server.ManualConcurrentHashMap;
import org.team6.Server.ServerThread;
import org.team6.history.HistoryDB;

/**
 * Send private message to user in the chat. In task requirement: private
 * message can be send to user within the room.
 * 
 */
public class SendPrivateMessage implements ICommand {
	private String receiverName;
	private String senderName;
	private String message;
	private ServerThread currentUserThread;

	private static final int MAX_MESSAGE_LENGTH = 1000;
	private static final String MESSAGE_MESSAGE_TOO_LONG = "Your message is too long! Maximum message length is 1000 charcters.";
	private static final String MESSAGE_NO_USER = "There is no user in the chat with this Nick: ";
	private static final String MESSAGE_NOT_VALID_COMMAND = "Your command is not valid. Please, use help and try again.";
	private static final String MESSAGE_EMPTY_MESSAGE = "Your message is empty! Try one more time.";

	public void send(ServerThread currentUser, String... messageFromUser) {
		currentUserThread = currentUser;
		senderName = currentUser.getName();
		if (!isMessageCanBeSend(messageFromUser[0])) {
			return;
		}

		HistoryDB historyDB = HistoryDB.getInstance();
		boolean messageSend = false;

		// find user in the room: room by room
		for (Entry<String, ManualConcurrentHashMap<String, ServerThread>> entry : currentUser
				.getServerHandler().getEntityes().entrySet()) {
			entry.getValue().addReader();

			// if we found user in the room, we have to lock the room, otherwise
			// user can be moved from it before we send him a message
			try {
				if (entry.getValue().containsKey(receiverName)) {

					// if we found user - get his thread and send message
					ManualConcurrentHashMap<String, ServerThread> currentRoomForUser = entry
							.getValue();

					currentRoomForUser.get(receiverName).sendMessage(
							"From [" + senderName + "] whisper: " + message);
					currentUser.loggerMessage(message, receiverName);
					messageSend = true;

					// also we save this message to database
					if (historyDB.getConnectionStatus()) {
						historyDB.insert(senderName, receiverName, message);
					}
					break;
				}

			} finally {
				entry.getValue().removeReader();
			}
		}

		if (!messageSend) {
			currentUser.sendMessage(MESSAGE_NO_USER + receiverName);
		}
	}

	private boolean isMessageCanBeSend(String messageFromUser) {
		int firstSpace = messageFromUser.indexOf(' ');
		if (firstSpace != -1) {
			receiverName = messageFromUser.substring(0, firstSpace);
			message = messageFromUser.substring(firstSpace + 1,
					messageFromUser.length());

			// length and message requirement
			if (messageLengthRequirement(message)) {
				return true;
			} else {
				return false;
			}
		} else {
			currentUserThread.sendMessage(MESSAGE_NOT_VALID_COMMAND);
			return false;
		}
	}

	/**
	 * Routine to assure message requirement on length
	 * 
	 * @param message
	 * @param messageFromUser
	 * @return
	 */
	private boolean messageLengthRequirement(String message) {

		// length and message requirement
		if (message.length() == 0) {
			currentUserThread.sendMessage(MESSAGE_EMPTY_MESSAGE);
			return false;
		} else if (message.length() > MAX_MESSAGE_LENGTH) {
			currentUserThread.sendMessage(MESSAGE_MESSAGE_TOO_LONG);
			return false;
		} else {
			return true;
		}
	}
}
