package org.team6.Server.Commands;

import org.team6.Server.ServerThread;

public class SendEmptyConfirmation implements ICommand {
	private static final String MESSAGE_EMPTY_MESSAGE = "Your message is empty! Try one more time.";

	public void send(ServerThread currentUser, String... messageFromUser) {
		currentUser.sendMessage(MESSAGE_EMPTY_MESSAGE);
	}
}
