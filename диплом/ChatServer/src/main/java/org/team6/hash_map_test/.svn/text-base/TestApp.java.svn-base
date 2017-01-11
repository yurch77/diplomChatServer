package org.team6.hash_map_test;

import org.apache.log4j.Logger;
import org.team6.Server.ManualConcurrentHashMap;

public final class TestApp {

	/*
	 * Simple application to test the ManualConcurrentHashMap (MCHM). Generates
	 * a MCHM<String, String>, filled with keys from 1 to (LENGTH) and values
	 * from 1# to (LENGTH)#. Then starts (THREADS) threads (implementation
	 * performed in TestThread.java). Computes time to run all threads, logs
	 * some information every (LOGGING_PAUSE_MILLIS) milliseconds, shows
	 * snapshots of initial and final MCHM contents. The elapsed time shown in
	 * log is larger then real one, because invocations of additional methods to
	 * log actions taken decrease the performance.
	 */
	private static final Logger LOG = org.apache.log4j.LogManager
			.getLogger(TestApp.class.getName());
	private static ManualConcurrentHashMap<String, String> list = new ManualConcurrentHashMap<String, String>();
	private static final int LENGTH = 100;
	private static final int THREADS = 100;
	private static final int LOGGING_PAUSE_MILLIS = 10;
	private static final int MILLISECONDS = 1000000;

	private static final String START = "#Main thread started.";
	private static final String FILLED = "#Hash Map filled with initial values.";
	private static final String STARTED = "#All threads started.";
	private static final String ACTIVE_THREADS = "#Active threads: ";
	private static final String ACTIVE_READERS = "\n#Active readers count: ";
	private static final String ACTIVE_WRITER = "\n#Active writer: ";
	private static final String TOTAL = "#Total time, millis: ";
	private static final String AFTER_CHANGES = "#Hash Map after changes:";
	private static final String FINISHED = "#Main thread finished.";
	private static final String SHARP = "#";

	public static ManualConcurrentHashMap<String, String> getHashMap() {
		return list;
	}

	private TestApp() {

	}

	public static void main(String[] args) {

		LOG.info(START);

		for (int i = 0; i < LENGTH; i++) {
			String key = Integer.valueOf(i + 1).toString();
			String value = key + SHARP;
			list.put(key, value);
		}

		LOG.info(FILLED);
		LOG.info(list.toString());
		final long begin = System.nanoTime();

		for (int i = 0; i < THREADS; i++) {
			new Thread(new TestThread()).start();
		}

		LOG.info(STARTED);

		while (Thread.activeCount() > 1) {
			LOG.info(ACTIVE_THREADS + Thread.activeCount() + ACTIVE_READERS
					+ list.activeReaders() + ACTIVE_WRITER
					+ list.activeWriter());
			try {
				Thread.sleep(LOGGING_PAUSE_MILLIS);
			} catch (InterruptedException e) {
				continue;
			}
		}

		final long end = System.nanoTime();
		final long passed = (end - begin) / MILLISECONDS;
		LOG.info(TOTAL + passed);

		LOG.info(AFTER_CHANGES);
		LOG.info(list.toString());
		LOG.info(FINISHED);
	}

}
