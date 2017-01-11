package org.team6.Server.Commands;

import java.util.Map.Entry;
import org.team6.Server.ManualConcurrentHashMap;
import org.team6.Server.ServerThread;

/**
 * Show nicks of users in the chat
 * 
 */
public class ShowUsers implements ICommand {
	public void send(ServerThread currentUser, String... messageFromUser) {
		currentUser.sendMessage(getUsers(currentUser));
	}

	private String getStringToAppend(String string, String appendString) {
		return string + appendString;
	}

	private String getUsers(ServerThread currentUser) {
		StringBuffer sb = new StringBuffer();
		sb.append("There are following users in the chat:\r\n");

		// get all users from chat
		for (Entry<String, ManualConcurrentHashMap<String, ServerThread>> entry : currentUser
				.getServerHandler().getEntityes().entrySet()) {
			ManualConcurrentHashMap<String, ServerThread> usersInTheRoom = entry
					.getValue();
			usersInTheRoom.addReader();
			
			// Lock the room to make its users set immutable until we finish
			// iteration
			try {
				for (String user : usersInTheRoom.keySet()) {
					sb.append(getStringToAppend(user, "\r\n"));
				}
			} finally {
				usersInTheRoom.removeReader();
			}
		}
		
		return sb.toString();
	}
}
