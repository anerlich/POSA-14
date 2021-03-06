package edu.vuum.mocca;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

/**
 * @class SimpleSemaphore
 * 
 * @brief This class provides a simple counting semaphore
 *        implementation using Java a ReentrantLock and a
 *        ConditionObject (which is accessed via a Condition). It must
 *        implement both "Fair" and "NonFair" semaphore semantics,
 *        just liked Java Semaphores.
 */
public class SimpleSemaphore {
    /**
     * Define a Lock to protect the critical section.
     */
    // TODO - you fill in here
    ReentrantLock mLock;

    /**
     * Define a Condition that waits while the number of permits is 0.
     */
    // TODO - you fill in here
    Condition mCondPermitsAvailable;

    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..
    volatile int mCountPermitsAvailable = 0;

    public SimpleSemaphore(int permits, boolean fair) {
        // TODO - you fill in here to initialize the SimpleSemaphore,
        // making sure to allow both fair and non-fair Semaphore
        // semantics.
		mCountPermitsAvailable = permits;
		mLock = new ReentrantLock(fair);
		mCondPermitsAvailable = mLock.newCondition();
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here
    	mLock.lock();
    	try {
    		while (mCountPermitsAvailable <= 0) {
    			mCondPermitsAvailable.await();
    		}
    		mCountPermitsAvailable--;
    	} finally {
    		mLock.unlock();
    	}
    }

    /**
     * Acquire one permit from the semaphore in a manner that cannot be
     * interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here
    	mLock.lock();
    	try {
    		while (mCountPermitsAvailable <= 0) {
    			mCondPermitsAvailable.awaitUninterruptibly();
    		}
    		mCountPermitsAvailable--;
    	} finally {
    		mLock.unlock();
    	}
    }

    /**
     * Return one permit to the semaphore.
     */
    void release() {
        // TODO - you fill in here
    	mLock.lock();
    	try {
    		mCountPermitsAvailable++;
    		mCondPermitsAvailable.signal();
    	} finally {
    		mLock.unlock();
    	}
	
    }

    /**
     * Return the number of permits available.
     */
    public int availablePermits(){
    	// TODO - you fill in here
    	int intPermits = 0;
    	mLock.lock();
    	intPermits = mCountPermitsAvailable;
    	mLock.unlock();
    	return intPermits; // You will change this value. 
    }
    
}
