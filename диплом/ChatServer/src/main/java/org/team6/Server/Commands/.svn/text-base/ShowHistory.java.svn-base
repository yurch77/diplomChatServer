package org.team6.Server.Commands;

import java.util.Calendar;

import org.team6.Server.ServerThread;
import org.team6.history.HistoryDB;

/**
 * Show history from derby
 * 
 */
public class ShowHistory implements ICommand {
	private static final String MESSAGE_ERROR_CONNECT = "Error occured: Can't connect to db\r\n";

	public void send(ServerThread currentUser, String... messageFromUser) {
		HistoryDB history = HistoryDB.getInstance();
		if (history.getConnectionStatus()) {

			String hStr = history.getHistory(currentUser.getName(),
					getMonth(messageFromUser));
			currentUser.sendMessage(hStr);
		} else {
			currentUser.sendMessage(MESSAGE_ERROR_CONNECT);
		}
	}

	private int getMonth(String... messageFromUser) {
		Calendar cal = Calendar.getInstance();
		if (messageFromUser.length == 0) {
			return cal.get(Calendar.MONTH) + 1;
		}

		int month = 0;
		try {
			month = Integer.parseInt(messageFromUser[0]);
		} catch (NumberFormatException ex) {
			month = cal.get(Calendar.MONTH) + 1;
		}
		return month;
	}
}
