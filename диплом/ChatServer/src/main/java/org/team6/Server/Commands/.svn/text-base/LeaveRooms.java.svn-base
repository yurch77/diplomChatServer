package org.team6.Server.Commands;

import java.util.Date;
import java.util.Map.Entry;
import org.team6.Server.ManualConcurrentHashMap;
import org.team6.Server.ServerHandler;
import org.team6.Server.ServerThread;

/**
 * Leave current room
 * 
 */
public class LeaveRooms implements ICommand {
	private static final String MESSAGE_ERROR = "Error occured: You are already in the main room";
	private static final String MESSAGE_DEFAULT_ROOM = "You have successfully left room.\r\nWelcome to main room!";

	public void send(ServerThread currentUser, String... messageFromUser) {
		if (currentUser.getRoomName().equalsIgnoreCase(
				ServerHandler.ROOM_ON_ENTER)) {
			currentUser.sendMessage(MESSAGE_ERROR);
		} else {
			String oldRoom = currentUser.getRoomName();
			String currentName = currentUser.getName();

			// remove user from room
			ManualConcurrentHashMap<String, ServerThread> currentRoom = getRoom(
					currentUser, oldRoom);
			currentRoom.remove(currentName);

			// inform other users in the room about this event
			Date date = new Date();
			currentRoom.addReader();
			try {
				for (Entry<String, ServerThread> otherUsers : currentRoom
						.entrySet()) {
					otherUsers.getValue().sendMessage(
							"User " + currentName + " leave room at "
									+ date.toString());
				}
			} finally {
				currentRoom.removeReader();
			}

			// change room to default
			currentUser.setRoomName(ServerHandler.ROOM_ON_ENTER);

			// put user to default room
			ManualConcurrentHashMap<String, ServerThread> defaultRoom = getRoom(
					currentUser, ServerHandler.ROOM_ON_ENTER);
			defaultRoom.put(currentName, currentUser);

			currentUser.sendMessage(MESSAGE_DEFAULT_ROOM);
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
