package org.team6.Server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ServerHandlerTest {

	private ServerHandler sHandler;
	
	@Before
	public void setUp() throws Exception {
		sHandler = new ServerHandler();
	}

	@Test
	public void testRoomsCount() {
		assertEquals(4, sHandler.getEntityes().size());
	}

}
