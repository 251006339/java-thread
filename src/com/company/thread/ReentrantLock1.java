package com.company.thread;

import com.sun.deploy.security.SelectableSecurityManager;
import com.sun.org.apache.regexp.internal.RE;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ReentrantLock1 extends ReentrantLock {
    private static final long serialVersionUID = 7373984872572414699L;
    /**
     * Synchronizer providing all implementation mechanics
     */
    private final ReentrantLock1.Sync sync;

    /**
     * Base of synchronization control for this lock. Subclassed
     * into fair and nonfair versions below. Uses AQS state to
     * represent the number of holds on the lock.
     */
    abstract static class Sync extends AbstractQueuedSynchronizer1 {
        private static final long serialVersionUID = -5179523762034025860L;


        abstract void lock();
        abstract void lockTest();
        final boolean nonfairTryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
             // 1
        final boolean nonfairTryAcquireTest(int acquires) {
            //获得当前线程
            final Thread current = Thread.currentThread();
            int c = getState(); //获得状态码  1
            if (c == 0) { //第一个线程获得锁.第二也来了
                //抢锁
                if (compareAndSetState(0, 1)) {
                    //抢到锁的执行;设置当前线程;返回true
                    setExclusiveOwnerThread(current);
                    return true;//代表抢到锁
                }
                //抢到锁的线程,再次执行 会让状态加1;
            } else if (current == getExclusiveOwnerThread()) {
                //让状态码加1
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeder");
                setState(nextc);//设置当前状态为加之后的
            }
            return false;
        }


           /* final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }*/


        protected final boolean tryRelease(int releases) {
             //unlock状态减一;
            int c = getState() - releases;

            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();

            boolean free = false;
            if (c == 0) {
                //状态改变为0
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }
        protected final boolean tryReleaseTest(int releases) {
            //1-1==0;
            int c = getState() - releases;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }
        protected final boolean isHeldExclusively() {
            // While we must in general read state before owner,
            // we don't need to do so to check if current thread is owner
            return getExclusiveOwnerThread() == Thread.currentThread();
        }

        final ConditionObject newCondition() {
            return new ConditionObject();
        }

        // Methods relayed from outer class

        final Thread getOwner() {
            return getState() == 0 ? null : getExclusiveOwnerThread();
        }

        final int getHoldCount() {
            return isHeldExclusively() ? getState() : 0;
        }

        final boolean isLocked() {
            return getState() != 0;
        }

        /**
         * Reconstitutes the instance from a stream (that is, deserializes it).
         */
        private void readObject(java.io.ObjectInputStream s)
                throws java.io.IOException, ClassNotFoundException {
            s.defaultReadObject();
            setState(0); // reset to unlocked state
        }
    }

    /**
     * Sync object for non-fair locks
     */
    static final class NonfairSync extends ReentrantLock1.Sync {
        private static final long serialVersionUID = 7316153563782823691L;

        public   final void lock() {
            if (compareAndSetState(0, 1))
                setExclusiveOwnerThread(Thread.currentThread());
            else
                acquire(1);
        }

       public final void lockTest() {
            //第一个线程过来 执行cas操作
            if (compareAndSetState(0, 1))
                //抢到锁
                setExclusiveOwnerThread(Thread.currentThread());

            else
                acquireTest(1);


        }


        protected final boolean tryAcquire(int acquires) {
            return nonfairTryAcquire(acquires);
        }

        protected final boolean tryAcquireTest(int acquires) {
            return nonfairTryAcquireTest(acquires);
        }
    }

    /**
     * Sync object for fair locks
     */
    static final class FairSync extends ReentrantLock1.Sync {
        private static final long serialVersionUID = -3000897897090466540L;

        final void lockTest() {
            acquireTest(1);
        }

        final void lock() {
            acquire(1);
        }

        protected final boolean tryAcquireTest(int acquires) {
            return nonfairTryAcquireTest(acquires);
        }

        /**
         * Fair version of tryAcquire.  Don't grant access unless
         * recursive call or no waiters or is first.
         */
        protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                //当第一个线程进来时:执行cas ,当第二个线程进来时;  true-->head.next抢到线程
                if (!hasQueuedPredecessors() &&
                        compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
    }

    /**
     * Creates an instance of {@code ReentrantLock}.
     * This is equivalent to using {@code ReentrantLock(false)}.
     */
    public ReentrantLock1() {
        sync = new ReentrantLock1.NonfairSync();
    }

    /**
     * Creates an instance of {@code ReentrantLock} with the
     * given fairness policy.
     *
     * @param fair {@code true} if this lock should use a fair ordering policy
     */
    public ReentrantLock1(boolean fair) {
        sync = fair ? new ReentrantLock1.FairSync() : new ReentrantLock1.NonfairSync();
    }

    /**
     * Acquires the lock.
     *
     * <p>Acquires the lock if it is not held by another thread and returns
     * immediately, setting the lock hold count to one.
     *
     * <p>If the current thread already holds the lock then the hold
     * count is incremented by one and the method returns immediately.
     *
     * <p>If the lock is held by another thread then the
     * current thread becomes disabled for thread scheduling
     * purposes and lies dormant until the lock has been acquired,
     * at which time the lock hold count is set to one.
     */
    public void lock() {
        sync.lock();
    }

    public void lockTest() {
        sync.lockTest();
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }


    public boolean tryLock() {
        return sync.nonfairTryAcquire(1);
    }


    public boolean tryLock(long timeout, TimeUnit unit)
            throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }


    public void unlock() {
        sync.release(1);
    }
   /* public void unlockTest() {
        sync.releaseTest(1);
    }*/
    public Condition newCondition() {
        return sync.newCondition();
    }


    public int getHoldCount() {
        return sync.getHoldCount();
    }


    public boolean isHeldByCurrentThread() {
        return sync.isHeldExclusively();
    }

    public boolean isLocked() {
        return sync.isLocked();
    }


    protected Thread getOwner() {
        return sync.getOwner();
    }


    protected Collection<Thread> getQueuedThreads() {
        return sync.getQueuedThreads();
    }


    public boolean hasWaiters(Condition condition) {
        if (condition == null)
            throw new NullPointerException();
        if (!(condition instanceof AbstractQueuedSynchronizer1.ConditionObject))
            throw new IllegalArgumentException("not owner");
        return sync.hasWaiters((AbstractQueuedSynchronizer1.ConditionObject) condition);
    }


    public int getWaitQueueLength(Condition condition) {
        if (condition == null)
            throw new NullPointerException();
        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject))
            throw new IllegalArgumentException("not owner");
        return sync.getWaitQueueLength((AbstractQueuedSynchronizer1.ConditionObject) condition);
    }

    protected Collection<Thread> getWaitingThreads(Condition condition) {
        if (condition == null)
            throw new NullPointerException();
        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject))
            throw new IllegalArgumentException("not owner");
        return sync.getWaitingThreads((AbstractQueuedSynchronizer1.ConditionObject) condition);
    }


    public String toString() {
        Thread o = sync.getOwner();
        return super.toString() + ((o == null) ?
                "[Unlocked]" :
                "[Locked by thread " + o.getName() + "]");
    }
}

