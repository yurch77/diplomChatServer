package org.team6.Server;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ServerThreadTest {

	ServerThread serverThreadMock;
	ServerHandler serverHandlerMock;
	ObjectInputStream objectInputStreamMock;
	ObjectOutputStream objectOutputStreamMock;

	private Map<String, ManualConcurrentHashMap<String, ServerThread>> connectionEntity = new ManualConcurrentHashMap<String, ManualConcurrentHashMap<String, ServerThread>>();

	@Before
	public void setUp() throws Exception {
		serverHandlerMock = mock(ServerHandler.class);
		serverThreadMock = mock(ServerThread.class);
		objectInputStreamMock = mock(ObjectInputStream.class);
		objectOutputStreamMock = mock(ObjectOutputStream.class);

		connectionEntity.put(ServerHandler.ROOM_ON_ENTER,
				new ManualConcurrentHashMap<String, ServerThread>());
		connectionEntity.put("Auto-room",
				new ManualConcurrentHashMap<String, ServerThread>());
		connectionEntity.put("Adult-room",
				new ManualConcurrentHashMap<String, ServerThread>());
		connectionEntity.put("Films-room",
				new ManualConcurrentHashMap<String, ServerThread>());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testServerThread() {
		assertNotNull(serverThreadMock);
	}

	@Test
	public void testGetName() {
		when(serverThreadMock.getName()).thenReturn("alfa");
		assertTrue(serverThreadMock.getName().equals("alfa"));
	}

	@Test
	public void testGetRoomName() {
		when(serverThreadMock.getRoomName()).thenReturn("Auto");
		assertTrue(serverThreadMock.getRoomName().equals("Auto"));
	}

	@Test
	public void testSetRoomName() {
		this.testGetRoomName();
	}

	@Test
	public void testGetServerHandler() {
		when(serverThreadMock.getServerHandler()).thenReturn(serverHandlerMock);
		assertTrue(serverThreadMock.getServerHandler() instanceof ServerHandler);

	}

	@Test
	public void testRun() throws IOException {
		serverThreadMock.run();
	}

	@Test
	public void testUniqueNick() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, IOException {

		// stubs
		when(objectInputStreamMock.readUTF()).thenReturn("alfa");
		when(serverHandlerMock.getEntityes()).thenReturn(connectionEntity);
		when(serverThreadMock.getServerHandler()).thenReturn(serverHandlerMock);

		Method method = ServerThread.class.getDeclaredMethod("uniqueNick",
				ObjectInputStream.class);
		method.setAccessible(true);

		assertEquals(method.invoke(serverThreadMock, objectInputStreamMock),
				"alfa");
	}	
	

	@Test
	public void testSendMessage() throws IOException {
		serverThreadMock.sendMessage("hi");
		verify(serverThreadMock).sendMessage("hi");
	}
}
