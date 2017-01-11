package org.team6.exception;

import java.net.Socket;

public class ServerThreadNotStartException extends Exception {

	// [ХП] Говорил про это исключение с Андреем. Оно сейчас нигде в программе
	// не используется, наверное, его лучше просто удалить?

	private static final long serialVersionUID = 1L;
	private Socket socket;

	public ServerThreadNotStartException(Socket socket) {
		this.socket = socket;
	}

	@Override
	public String toString() {
		return "ServerThreadNotStartException [socket="
				+ socket.getInetAddress().getHostName() + "]";
	}

}
