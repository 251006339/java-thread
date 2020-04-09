package com.company.cas;

import com.company.thread.AbstractQueuedSynchronizer1;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

public class AQS{
 static    volatile Node head;
   static volatile Node tail;
    private volatile int state;

    static class Node{
        static final Node SHARED = new Node();
        static Node EXCLUSIVE = null;
        static final int CANCELLED = 1;

        static final int SIGNAL = -1;
        static final int CONDITION = -2;
        static final int PROPAGATE = -3;
        volatile int waitStatus;//状态
        volatile Node prev;
        volatile Node next;
        volatile Thread thread;
        Node nextWaiter;

        public Node(Thread thread, Node nextWaiter) {
            this.thread = thread;
            this.nextWaiter = nextWaiter;
        }

        public Node() {
        }
    }

    public static void main(String[] args) {
        Node newNode=new Node(Thread.currentThread(),Node.EXCLUSIVE);
        AQS aqs = new AQS();
        aqs.enqTest(newNode);
        Node newNode1=new Node(Thread.currentThread(),Node.SHARED);
        aqs.enqTest(newNode1);
        Node newNode2=new Node(Thread.currentThread(),Node.SHARED);
        aqs.enqTest(newNode2);
        head.waitStatus=-1;
        Node node=head.next;
        Node s=head.next;
         //s就是链表遍历的第一个进行解锁;
            for (Node t = tail; t != null && t != node; t = t.prev)
                if (t.waitStatus <= 0)
                    s = t;
    }
    private  Node enqTest(final Node node) {

        for (; ; ) {
            Node t = tail; //队列已经有过节点了
            if (t == null) {
                //给head节点赋值
                if (compareAndSetHead1(null,new Node()))
                    tail = head;//赋值好了,给尾节点也赋值;

            } else {
                //把尾节点的值赋值临时节点
                node.prev = t;
                //新创建的 1 node  把尾节点变称 2
                if (compareAndSetTail1(t, node)) {
                    t.next = node;//给aqs的head下一个节点附上 node<tail>
                    return t;//返回head节点;
                    //head -->next--tail  tail .thread  .  -->prev-->head;
                }
            }
        }
    }
        protected final boolean compareAndSetState(int expect, int update) {
            return expect == compareAndSetState1(expect, update);
        }

        public int compareAndSetState1(int expect, int update) {
            int oldValue = state;
            if (expect == oldValue) {
                state = update;
            }
            return oldValue;
        }
      boolean compareAndSetHead1(Node expect, Node update) {
        return expect == compareAndSetHead(expect, update);
    }

    public Node compareAndSetHead(Node expect, Node update) {
        Node oldValue = head;
        if (expect == oldValue) {
            head = update;
        }
        return oldValue;
    }
    boolean compareAndSetTail1(Node expect, Node update) {
        return expect == compareAndSetTail(expect, update);
    }

    public Node compareAndSetTail(Node expect, Node update) {
        Node oldValue = tail;
        if (expect == oldValue) {
            tail = update;
        }
        return oldValue;
    }
}


