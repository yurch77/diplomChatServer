package org.team6.Server.Commands;

import org.team6.Server.ServerThread;

/**
 * This command for show help window to user if he wants
 * 
 * 
 */
public class CommandOnHelp implements ICommand {
	public void send(ServerThread currentUser, String... messageFromUser) {
		StringBuffer sb = new StringBuffer();
		sb.append("You can use the following commands:\r\n");
		sb.append("1. #get-rooms: Show all available rooms on the server\r\n");
		sb.append("2. #change-room <x>: Change room to x, where x is rooms name\r\n");
		sb.append("3. #leave-room: You leave room and go to default room\r\n");
		sb.append("4. #send-to <x> <message>: Send private your <message> to user <x>\r\n");
		sb.append("5. #show-history <x>: Show your history private messages during <x> month\r\n");
		sb.append("6. #show-users: Show users had registered in the chat\r\n");		
		sb.append("7. #help: Show help\r\n");

		currentUser.sendMessage(sb.toString());
	}
}
