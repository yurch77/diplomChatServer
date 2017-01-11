package org.team6.Server.Commands;

import org.team6.Server.ServerHandler;
import org.team6.Server.ServerThread;

/**
 * This command to get list of available rooms on the server
 * 
 */
public class GetRooms implements ICommand {

	public void send(ServerThread currentUser, String... messageFromUser) {
		currentUser.sendMessage(getRooms(currentUser));
	}

	private String getRooms(ServerThread currentUser) {
		StringBuffer sb = new StringBuffer();
		sb.append("Server can suggest you following rooms:\r\n");
		
		ServerHandler sHandler = currentUser.getServerHandler();
		for (String roomName : sHandler.getEntityes().keySet()) {
			sb.append(getStringToAppend(roomName, "\r\n"));
		}
		return sb.toString();
	}

	private String getStringToAppend(String string, String appendString) {
		return string + appendString;
	}
}
