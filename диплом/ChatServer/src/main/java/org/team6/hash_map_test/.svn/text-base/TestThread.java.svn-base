package org.team6.hash_map_test;

import java.util.Set;

import org.apache.log4j.Logger;
import org.team6.Server.ManualConcurrentHashMap;

public class TestThread implements Runnable {

	/*
	 * A thread designed for TestApp.java. Can perform 4 actions on the MCHM:
	 * put(...), remove(...), get(...), and circular get(...) through the
	 * Map.keySet(). When started, randomly chooses and performs (COUNT)
	 * actions. Logs its activity.
	 */
	private static final Logger LOG = org.apache.log4j.LogManager
			.getLogger(TestThread.class.getName());
	private static final int COUNT = 10;
	private static final ManualConcurrentHashMap<String, String> LIST = TestApp.getHashMap();
	private static final int LENGTH = LIST.size();
	private StringBuffer result = new StringBuffer("");
	private static final int NUMBER_THREE = 3;
	private static final int NUMBER_FOUR = 4;	
	private static final String THREAD = "Thread ";

	@Override
	public void run() {
		LOG.info(THREAD + Thread.currentThread().getId() + " started.");
		for (int i = 0; i < COUNT; i++) {
			randomAction();
		}
		LOG.info(THREAD + Thread.currentThread().getId() + " finished.");
	}

	private void randomAction() {
		int rand = (int) Math.round(Math.random() * NUMBER_FOUR);
		switch (rand) {
		case 1:
			getSmth();
			break;
		case 2:
			removeSmth();
			break;
		case NUMBER_THREE:
			putSmth();
			break;
		case NUMBER_FOUR:
		default:
			iter();
			break;
		}
	}

	private void getSmth() {
		String key = getRndKey();		
		LOG.info(THREAD + Thread.currentThread().getId() + "\nGet key " + key
				+ ", value: " + LIST.get(key).toString());
	}

	private void removeSmth() {
		String key = getRndKey();		
		LOG.info(THREAD + Thread.currentThread().getId() + "\nRemove by key "
				+ key + ", value: " + LIST.remove(key));
	}

	private void putSmth() {
		String key = Integer.valueOf((int) Math.round(Math.random() * LENGTH))
				.toString();		
		String value = key + "*";
		LOG.info(THREAD + Thread.currentThread().getId() + "\nPut by key "
				+ key + ", value: " + LIST.put(key, value));
	}

	private void iter() {
		LIST.addReader();
		try {
			Set<String> keys = LIST.keySet();
			result.delete(0, result.length());
			for (String key : keys) {
				result.append(LIST.get(key));
				result.append(" ");
			}
			LOG.info(THREAD + Thread.currentThread().getId() + "\nIteration: "
					+ result.toString());
		} finally {
			LIST.removeReader();
		}
	}

	private String getRndKey() {
		String key = Integer.valueOf((int) Math.round(Math.random() * LENGTH))
				.toString();
		if (LIST.containsKey(key)) {
			return key;
		} else {
			return getRndKey();
		}
	}
}
