package org.team6.Server;

public class CommandParser {
	private static CommandParser parser;

	public static CommandParser getParser() {
		if (parser == null) {
			synchronized (CommandParser.class) {
				CommandParser pars = parser;
				if (pars == null) {
					synchronized (CommandParser.class) {
						parser = new CommandParser();
					}
				}
			}
		}
		return parser;
	}

	public CommandType findCommandAccordingParse(String message) {
		String messageCommand = "";

		if (message.length() == 0) {
			return CommandType.EMPTY_CONFIRMATION;
		}

		int firstSpace = message.indexOf(' ');
		if (firstSpace == -1) {
			messageCommand = message;
		} else {
			messageCommand = message.substring(0, firstSpace);
		}

		return getCommand(messageCommand);
	}

	public String findUserMessageAccordingParse(String message) {
		if (message.startsWith("#")) {
			int firstSpace = message.indexOf(' ');
			if (firstSpace == -1) {
				return null;
			} else {
				return message.substring(firstSpace + 1, message.length());
			}
		} else {
			return message;
		}
	}
		
	private CommandType getCommand(String messageCommand) {

		if (messageCommand.equalsIgnoreCase("#change-room")) {
			return CommandType.CHANGE_ROOM;
		} else if (messageCommand.equalsIgnoreCase("#leave-room")) {
			return CommandType.LEAVE_ROOM;
		} else if (messageCommand.equalsIgnoreCase("#get-rooms")) {
			return CommandType.GET_ROOMS;
		} else if (messageCommand.equalsIgnoreCase("#send-to")) {
			return CommandType.SEND_TO;
		} else if (messageCommand.equalsIgnoreCase("#help")) {
			return CommandType.HELP_COMMAND;
		} else if (messageCommand.equalsIgnoreCase("#show-history")) {
			return CommandType.SHOW_HISTORY;
		} else if (messageCommand.equalsIgnoreCase("#show-users")) {
			return CommandType.SHOW_USERS;
		} else {
			return CommandType.SEND_TO_ALL;
		}
	}
}
