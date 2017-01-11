package org.team6.Server.Commands;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.team6.Server.CommandParser;
import org.team6.Server.CommandType;

public class CommandParserTest {

	private CommandParser parser;

	@Before
	public void setUp() {
		parser = mock(CommandParser.class);
	}

	@Test
	public void testFindCommandAccordingParse() {
		when(parser.findCommandAccordingParse(anyString())).thenReturn(
				CommandType.EMPTY_CONFIRMATION);
		assertTrue(parser.findCommandAccordingParse(anyString()).equals(
				CommandType.EMPTY_CONFIRMATION));

		when(parser.findCommandAccordingParse("#help")).thenReturn(
				CommandType.HELP_COMMAND);
		assertTrue(parser.findCommandAccordingParse("#help") == CommandType.HELP_COMMAND);

		when(parser.findCommandAccordingParse("#change-room")).thenReturn(
				CommandType.CHANGE_ROOM);
		assertTrue(parser.findCommandAccordingParse("#change-room") == CommandType.CHANGE_ROOM);

		when(parser.findCommandAccordingParse("#leave-room")).thenReturn(
				CommandType.LEAVE_ROOM);
		assertTrue(parser.findCommandAccordingParse("#leave-room") == CommandType.LEAVE_ROOM);

		when(parser.findCommandAccordingParse("#get-rooms")).thenReturn(
				CommandType.GET_ROOMS);
		assertTrue(parser.findCommandAccordingParse("#get-rooms") == CommandType.GET_ROOMS);

		when(parser.findCommandAccordingParse("#send-to")).thenReturn(
				CommandType.SEND_TO);
		assertTrue(parser.findCommandAccordingParse("#send-to") == CommandType.SEND_TO);

		when(parser.findCommandAccordingParse("#show-history")).thenReturn(
				CommandType.SHOW_HISTORY);
		assertTrue(parser.findCommandAccordingParse("#show-history") == CommandType.SHOW_HISTORY);

		when(parser.findCommandAccordingParse("#show-users")).thenReturn(
				CommandType.SHOW_USERS);
		assertTrue(parser.findCommandAccordingParse("#show-users") == CommandType.SHOW_USERS);

		when(parser.findCommandAccordingParse("some message...")).thenReturn(
				CommandType.SEND_TO_ALL);
		assertTrue(parser.findCommandAccordingParse("some message...") == CommandType.SEND_TO_ALL);
	}

	@Test
	public void testFindUserMessageAccordingParse() {
		when(parser.findUserMessageAccordingParse("#show-history 12")).thenReturn(
				"12");
		assertTrue(parser.findUserMessageAccordingParse("#show-history 12").startsWith("12"));
		
		when(parser.findUserMessageAccordingParse("#help")).thenReturn(
				null);
		assertTrue(parser.findUserMessageAccordingParse("#help") == null);
		
		when(parser.findUserMessageAccordingParse("#change-room Auto-room")).thenReturn(
				"Auto-room");
		assertTrue(parser.findUserMessageAccordingParse("#change-room Auto-room").startsWith("Auto-room"));
		
		when(parser.findUserMessageAccordingParse("#leave-room")).thenReturn(
				null);
		assertTrue(parser.findUserMessageAccordingParse("#leave-room") == null);
		
		when(parser.findUserMessageAccordingParse("##get-rooms")).thenReturn(
				null);
		assertTrue(parser.findUserMessageAccordingParse("#get-rooms") == null);
		
		when(parser.findUserMessageAccordingParse("#send-to alfa my message")).thenReturn(
				"alfa my message");
		assertTrue(parser.findUserMessageAccordingParse("#send-to alfa my message").startsWith("alfa my message"));
		
		when(parser.findUserMessageAccordingParse("#show-users")).thenReturn(
				null);
		assertTrue(parser.findUserMessageAccordingParse("#show-users") == null);
	}
	
	@Test
	public void testGetCommand() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Method mGetCommand = CommandParser.class.getDeclaredMethod("getCommand", String.class);
		mGetCommand.setAccessible(true);
		
		assertEquals(mGetCommand.invoke(parser, "#help"), CommandType.HELP_COMMAND);
		assertEquals(mGetCommand.invoke(parser, "#change-room"), CommandType.CHANGE_ROOM);	
		assertEquals(mGetCommand.invoke(parser, "#leave-room"), CommandType.LEAVE_ROOM);	
		assertEquals(mGetCommand.invoke(parser, "#get-rooms"), CommandType.GET_ROOMS);	
		assertEquals(mGetCommand.invoke(parser, "#send-to"), CommandType.SEND_TO);	
		assertEquals(mGetCommand.invoke(parser, "#show-history"), CommandType.SHOW_HISTORY);	
		assertEquals(mGetCommand.invoke(parser, "#show-users"), CommandType.SHOW_USERS);	
		assertEquals(mGetCommand.invoke(parser, "some messsage..."), CommandType.SEND_TO_ALL);		
				
	}
	
	@Test
	public void testCreateInstance(){
		assertNotNull(CommandParser.getParser());
	}
	
}
