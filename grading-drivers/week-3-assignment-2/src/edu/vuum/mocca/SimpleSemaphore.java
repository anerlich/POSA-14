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
     * Define a ReentrantLock to protect the critical section.
     */
    // TODO - you fill in here
	private ReentrantLock mCriticalSectionLock; 

    /**
     * Define a Condition that waits while the number of permits is 0.
     */
    // TODO - you fill in here
	private Condition mPermitsCondition;
    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..
	volatile int mPermitsAvailable;

    public SimpleSemaphore(int permits, boolean fair) {
        // TODO - you fill in here to initialize the SimpleSemaphore,
        // making sure to allow both fair and non-fair Semaphore
        // semantics.
    	mPermitsAvailable = permits;
    	
    	mCriticalSectionLock = new ReentrantLock(fair);
    	mPermitsCondition = mCriticalSectionLock.newCondition();
    	
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here.
    	try
    	{
    		mCriticalSectionLock.lock();
    		while(mPermitsAvailable == 0)
    		{
    			mPermitsCondition.await();
    		}
    		--mPermitsAvailable;
    		
    	}
    	finally
    	{
    		mCriticalSectionLock.unlock();
    	}
    }

    /**
     * Acquire one permit from the semaphore in a manner that cannot be
     * interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here.
    	try
    	{
    		mCriticalSectionLock.lockInterruptibly();
    		while(mPermitsAvailable == 0)
    		{
    			mPermitsCondition.await();
    		}
    		--mPermitsAvailable;
    	}
    	catch (InterruptedException ie)
    	{
    		
    	}
    	finally
    	{
    		mCriticalSectionLock.unlock();
    	}
    	
    }

    /**
     * Return one permit to the semaphore.
     */
    void release() {
        // TODO - you fill in here.
    	try{
    		mCriticalSectionLock.lock();
    		++mPermitsAvailable;
    		
    		//signal that there is at least one semaphore available
    		mPermitsCondition.signalAll();
    	}
    	finally
    	{
    		mCriticalSectionLock.unlock();
    	}
    }

    /**
     * Return the number of permits available.
     */
    public int availablePermits() {
        // TODO - you fill in here by changing null to the appropriate
        // return value.
        return this.mPermitsAvailable;
    }
}
