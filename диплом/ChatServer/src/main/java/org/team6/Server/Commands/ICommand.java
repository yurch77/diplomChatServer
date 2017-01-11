package org.team6.Server.Commands;

import org.team6.Server.ServerThread;

/**
 * Interface for pattern Command
 *
 */
public interface ICommand {
	void send(ServerThread currentUser, String... messageFromUser);
}
