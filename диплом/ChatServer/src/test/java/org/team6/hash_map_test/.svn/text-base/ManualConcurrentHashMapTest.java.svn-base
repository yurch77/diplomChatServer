package org.team6.hash_map_test;
import org.team6.Server.ManualConcurrentHashMap;

import junit.framework.TestCase;

public class ManualConcurrentHashMapTest extends TestCase {
	private ManualConcurrentHashMap<String, String> hashMap = new ManualConcurrentHashMap<String, String>();

	public ManualConcurrentHashMapTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		for (int i = 0; i < 10; i++) {
			String key = Integer.valueOf(i).toString();
			String value = "value " + key;
			hashMap.put(key, value);
		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddReader() {
		int i = hashMap.activeReaders();
		hashMap.addReader();
		assertEquals(i + 1, hashMap.activeReaders());
	}

	public void testRemoveReader() {
		hashMap.addReader();
		int i = hashMap.activeReaders();
		hashMap.removeReader();
		assertEquals(i - 1, hashMap.activeReaders());
	}
	
	public void testGetObject() {
		String result = hashMap.get("3");
		assertEquals("value 3", result);
	}
	
	public void testPutKV() {
		String put1 = "alfa";
		String put2 = "beta";
		int i = hashMap.size();
		hashMap.put("11", put1);
		assertEquals(i + 1, hashMap.size());
		i = hashMap.size();
		hashMap.put("5", put2);
		assertEquals(i, hashMap.size());
		assertEquals("alfa", hashMap.get("11"));
		assertEquals("beta", hashMap.get("5"));
	}

	public void testRemoveObject() {
		int i = hashMap.size();
		hashMap.remove("8");
		assertEquals(i - 1, hashMap.size());
		assertEquals(false, hashMap.containsKey("8"));
		assertEquals(false, hashMap.containsValue("value 8"));
	}

	public void testActiveReaders() {
		assertEquals(0, hashMap.activeReaders());
		hashMap.addReader();
		hashMap.addReader();
		assertEquals(2, hashMap.activeReaders());
		hashMap.removeReader();
		assertEquals(1, hashMap.activeReaders());
	}

	public void testActiveWriter() {
		assertEquals(false, hashMap.activeWriter());
	}

}
