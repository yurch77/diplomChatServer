package org.team6.history;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Formatter;

import org.apache.log4j.Logger;

/**
 * Class for save private message in derby database
 * 
 */
public final class HistoryDB {
	private Connection connect = null;
	private boolean connectStatus = false;

	private static final Logger LOG = org.apache.log4j.LogManager
			.getLogger(HistoryDB.class.getName());

	private static HistoryDB historyInstance = new HistoryDB();
	private static final String MESSAGE_ERROR_RECEIVE = "Error occured: Can't receive history from derby db\r\n";
	private static final String MESSAGE_ERROR_INSERT = "Error occured: DB command insert error";
	private static final int USER_NOT_FOUND = -100;

	private HistoryDB() {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			connect = DriverManager.getConnection("jdbc:derby://localhost/c:"
					+ File.separator + "jet-db" + File.separator + "historyDB");
			connectStatus = true;
		} catch (ClassNotFoundException ex) {
			connectStatus = false;
		} catch (SQLException ex) {
			connectStatus = false;
		} catch (IllegalAccessException ex) {
			connectStatus = false;
		} catch (InstantiationException ex) {
			connectStatus = false;
		}
	}

	public static HistoryDB getInstance() {
		if (historyInstance == null) {
			synchronized (HistoryDB.class) {
				HistoryDB historyIns = historyInstance;
				if (historyIns == null) {
					synchronized (HistoryDB.class) {
						historyInstance = new HistoryDB();
					}
				}
			}
		}
		return historyInstance;
	}

	public boolean getConnectionStatus() {
		return connectStatus;
	}

	/**
	 * Get history for period from derby by name
	 */
	public String getHistory(String name, int month) {
		PreparedStatement cmd = null;
		ResultSet result = null;
		String history = "";

		try {
			final String query = "select usr.Name, h.ReceiverName, h.MessageDate, h.MessageTime, h.MessageText"
					+ "							 from jet_0002_team06.users usr"
					+ "							 join jet_0002_team06.history h"
					+ "							 on h.FK_Users = usr.ID and usr.Name=? where MONTH(MessageDate)=?";

			cmd = connect.prepareStatement(query);
			try {
				setParameters(cmd, name, month);
				result = cmd.executeQuery();

				try {
					history = createHistoryInput(result);
				} finally {
					result.close();
				}
			} finally {
				cmd.close();
			}
		} catch (SQLException ex) {
			return MESSAGE_ERROR_RECEIVE;
		}
		return history;
	}

	// PRETTY Jenkins - lets find more magic numbers, or we'll be use method
	private void setParameters(PreparedStatement stmt, final Object... args)
			throws SQLException {
		for (int i = 0; i < args.length; i++) {
			stmt.setObject(i + 1, args[i]);
		}
	}

	private String createHistoryInput(final ResultSet result)
			throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("Sender\tReceiver\tDate\t\tTime\t\tText\r\n");
		while (result.next()) {
			sb.append(concatStringToAppend(result.getString("Name"), "\t"));
			sb.append(concatStringToAppend(result.getString("ReceiverName"),
					"\t\t"));
			sb.append(concatStringToAppend(result.getDate("MessageDate")
					.toString(), "\t"));
			sb.append(concatStringToAppend(result.getTime("MessageTime")
					.toString(), "\t"));
			sb.append(concatStringToAppend(result.getString("MessageText"),
					"\r\n"));
		}
		return sb.toString();
	}

	private String concatStringToAppend(String string, String appendString) {
		return string + appendString;
	}

	/**
	 * Insert record to database
	 * 
	 */
	public void insert(String name, String receiverName, String message) {
		try {
			int userID = hasUser(name);
			// if there are no user then create them
			if (userID == USER_NOT_FOUND || userID == 0) {
				final String queryInsertUser = "insert into jet_0002_team06.users(Name) values(?)";
				PreparedStatement cmdInsertUser = connect
						.prepareStatement(queryInsertUser);
				try {
					setParameters(cmdInsertUser, name);

					cmdInsertUser.executeUpdate();
				} finally {
					cmdInsertUser.close();
				}
			}

			Formatter dateFormat = new Formatter();
			Formatter timeFormat = new Formatter();
			Calendar cal = Calendar.getInstance();

			dateFormat.format("%tF", cal);
			String date = dateFormat.toString();
			dateFormat.close();

			timeFormat.format("%tT", cal);
			String time = timeFormat.toString();
			timeFormat.close();

			final String queryInsertRecord = "insert into jet_0002_team06.history(ReceiverName, MessageDate, MessageTime, MessageText, FK_Users) values(?,?,?,?,(select ID from jet_0002_team06.users where Name=?))";
			PreparedStatement cmd = connect.prepareStatement(queryInsertRecord);
			try {
				setParameters(cmd, receiverName, date, time, message, name);

				cmd.executeUpdate();
			} finally {
				cmd.close();
			}
		} catch (SQLException ex) {
			consoleOut(MESSAGE_ERROR_INSERT);
		}
	}

	private int hasUser(String name) {
		PreparedStatement cmd = null;
		final String query = "select count(*) as UserCount from jet_0002_team06.users where Name = ?";
		try {
			cmd = connect.prepareStatement(query);
			try {
				setParameters(cmd, name);

				ResultSet hasUser = cmd.executeQuery();
				try {
					if (hasUser.next()) {
						return hasUser.getInt("UserCount");
					} else {
						return USER_NOT_FOUND;
					}
				} finally {
					hasUser.close();
				}
			} finally {
				cmd.close();
			}
		} catch (SQLException ex) {
			consoleOut(MESSAGE_ERROR_INSERT);
			return USER_NOT_FOUND;
		}
	}

	private void consoleOut(String message) {
		LOG.info(message);
	}
}
