package org.team6.Server.Commands;

import org.team6.Server.ManualConcurrentHashMap;
import org.team6.Server.ServerHandler;
import org.team6.Server.ServerThread;

/**
 * This command to change room of user
 * 
 */
public class ChangeRoom implements ICommand {
	private String newRoomName;

	private static final String MESSAGE_ALREADY_IN_ROOM = "You have already entered this room";
	private static final String MESSAGE_ERROR_ROOM = "There is no such room";

	public void send(ServerThread currentUser, String... messageFromUser) {
		newRoomName = messageFromUser[0];

		// change room on server
		if (currentUser.getRoomName().equalsIgnoreCase(newRoomName)) {
			currentUser.sendMessage(MESSAGE_ALREADY_IN_ROOM);
		} else {

			ManualConcurrentHashMap<String, ServerThread> newRoom = getRoom(
					currentUser, newRoomName);
			if (newRoom == null) {
				currentUser.sendMessage(MESSAGE_ERROR_ROOM);
				return;
			}

			String oldRoomName = currentUser.getRoomName();
			String nickName = currentUser.getName();

			// remove from current room
			ManualConcurrentHashMap<String, ServerThread> currentRoom = getRoom(
					currentUser, oldRoomName);
			currentRoom.remove(nickName);

			// add to necessary room
			currentUser.setRoomName(newRoomName);
			newRoom.put(nickName, currentUser);

			// send notification to user
			currentUser.sendMessage("Welcome to the " + newRoomName + " room!");
		}
	}

	private ManualConcurrentHashMap<String, ServerThread> getRoom(
			ServerThread currentUser, String roomName) {
		ServerHandler hServer = currentUser.getServerHandler();
		ManualConcurrentHashMap<String, ServerThread> currentRoom = hServer
				.getEntityes().get(roomName);
		return currentRoom;
	}
}
