package org.team6.Server;

import java.util.HashMap;
import java.util.Map;

import org.team6.Server.Commands.ChangeRoom;
import org.team6.Server.Commands.CommandOnHelp;
import org.team6.Server.Commands.GetRooms;
import org.team6.Server.Commands.ICommand;
import org.team6.Server.Commands.LeaveRooms;
import org.team6.Server.Commands.SendEmptyConfirmation;
import org.team6.Server.Commands.SendMessageToAllInRoom;
import org.team6.Server.Commands.SendPrivateMessage;
import org.team6.Server.Commands.ShowHistory;
import org.team6.Server.Commands.ShowUsers;

public class CommandExecuter {
	private Map<CommandType, ICommand> commandList = new HashMap<CommandType, ICommand>();
	private static CommandExecuter executer;
	private CommandParser parser = CommandParser.getParser();

	private CommandExecuter() {
		commandList.put(CommandType.HELP_COMMAND, new CommandOnHelp());
		commandList.put(CommandType.CHANGE_ROOM, new ChangeRoom());
		commandList.put(CommandType.GET_ROOMS, new GetRooms());
		commandList.put(CommandType.LEAVE_ROOM, new LeaveRooms());
		commandList.put(CommandType.SEND_TO, new SendPrivateMessage());
		commandList.put(CommandType.SHOW_HISTORY, new ShowHistory());
		commandList.put(CommandType.SHOW_USERS, new ShowUsers());
		commandList.put(CommandType.SEND_TO_ALL, new SendMessageToAllInRoom());
		commandList.put(CommandType.EMPTY_CONFIRMATION,
				new SendEmptyConfirmation());
	}

	public static CommandExecuter getCommandExecuter() {
		if (executer == null) {
			synchronized (CommandExecuter.class) {
				CommandExecuter exec = executer;
				if (exec == null) {
					synchronized (CommandExecuter.class) {
						executer = new CommandExecuter();
					}
				}
			}
		}
		return executer;
	}

	/**
	 * Execute command
	 * 
	 * @param currentThread
	 * @param commandFromUser
	 */
	public void executeUserCommand(ServerThread currentThread,
			String commandFromUser) {		
		ICommand currentCommand = getCurrentCommand(commandFromUser);
		currentCommand.send(currentThread,
				parser.findUserMessageAccordingParse(commandFromUser));
	}

	/**
	 * Get instance of current command in parse result
	 * 
	 * @param commandFromUser
	 * @return
	 */
	private ICommand getCurrentCommand(String commandFromUser) {		
		return commandList.get(parser
				.findCommandAccordingParse(commandFromUser));
	}
}
