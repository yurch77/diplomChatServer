package org.team6.Server;

import java.util.HashMap;
import java.util.concurrent.locks.*;

public class ManualConcurrentHashMap<K, V> extends HashMap<K, V> {
	/*
	 * This class introduces thread-safe reading, inserting and removing
	 * entries. Also manual lock for loop reading (for example) is allowed, but
	 * one should be careful when uses it. No manual control on the WriteLock is
	 * allowed, because when one forgot to unlock it, no further activity with
	 * the locked Map would be available.
	 */
	private static final long serialVersionUID = 8872519335314261765L;
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	// IMPORTANT! The ReadLock is now manually set locked by this method, so it
	// has to be manually set unlocked.
	public void addReader() {
		lock.readLock().lock();
	}

	// IMPORTANT! The ReadLock has to be manually unlocked by this method, if it
	// has been locked by the addReader method.
	public void removeReader() {
		lock.readLock().unlock();
	}

	@Override
	public V get(Object key) {
		V result = null;
		try {
			lock.readLock().lock();
			result = super.get(key);
		} finally {
			lock.readLock().unlock();
		}
		return result;
	}

	@Override
	public V put(K key, V value) {
		V result = null;
		try {
			if (lock.writeLock().tryLock()) {
				result = super.put(key, value);
			} else {
				try {
					Thread.sleep(1);
					this.put(key, value);
				} catch (InterruptedException e) {
					this.put(key, value);
				}
			}
		} finally {
			if (lock.writeLock().isHeldByCurrentThread()) {
				lock.writeLock().unlock();
			}
		}
		return result;
	}

	@Override
	public V remove(Object key) {
		V result = null;
		try {
			if (lock.writeLock().tryLock()) {
				result = super.remove(key);
			} else {
				try {
					Thread.sleep(1);
					this.remove(key);
				} catch (InterruptedException e) {
					this.remove(key);
				}
			}
		} finally {
			if (lock.writeLock().isHeldByCurrentThread()) {
				lock.writeLock().unlock();
			}
		}
		return result;
	}

	public int activeReaders() {
		return lock.getReadLockCount();
	}

	public boolean activeWriter() {
		return lock.isWriteLocked();
	}
}
