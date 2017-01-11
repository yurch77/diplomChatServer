package org.team6.Server.Commands;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import org.team6.Server.CommandExecuter;

public class CommandExecuterTest {

		
	@Test
	public void testGetCurrentCommand() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
				
		Method mGetCurrentCommand = CommandExecuter.class.getDeclaredMethod(
				"getCurrentCommand", String.class);
		mGetCurrentCommand.setAccessible(true);
		assertTrue(mGetCurrentCommand.invoke(
				CommandExecuter.getCommandExecuter(), "#help") instanceof CommandOnHelp);
		assertTrue(mGetCurrentCommand.invoke(
				CommandExecuter.getCommandExecuter(), "#change-room") instanceof ChangeRoom);
		assertTrue(mGetCurrentCommand.invoke(
				CommandExecuter.getCommandExecuter(), "#leave-room") instanceof LeaveRooms);
		assertTrue(mGetCurrentCommand.invoke(
				CommandExecuter.getCommandExecuter(), "#get-rooms") instanceof GetRooms);
		assertTrue(mGetCurrentCommand.invoke(
				CommandExecuter.getCommandExecuter(), "#send-to") instanceof SendPrivateMessage);
		assertTrue(mGetCurrentCommand.invoke(
				CommandExecuter.getCommandExecuter(), "#show-history") instanceof ShowHistory);
		assertTrue(mGetCurrentCommand.invoke(
				CommandExecuter.getCommandExecuter(), "#show-users") instanceof ShowUsers);
		assertTrue(mGetCurrentCommand.invoke(
				CommandExecuter.getCommandExecuter(), "some text...") instanceof SendMessageToAllInRoom);
	}
	
	@Test
	public void testCreateInstance(){
		assertNotNull(CommandExecuter.getCommandExecuter());
	}
}
