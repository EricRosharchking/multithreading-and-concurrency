package com.liyuan.hong.globaljavamentor;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Implement message bus using Producer-Consumer pattern.
 * <p>
 * Implement asynchronous message bus. Do not use queue implementations from java.util.concurrent.
 * Implement producer, which will generate and post randomly messages to the queue.
 * Implement consumer, which will consume messages on specific topic and log to the console message payload.
 * (Optional) Application should create several consumers and producers that run in parallel.
 */
public class Task3 {
    /* pseudo code
    one MesssageBus, one producer <-> one consumer
     */
    public static void main(String[] args) {
        Queue<String> msgQueue = new LinkedList<String>();
        Thread producerThread = new Thread(new MsgProducer(msgQueue));
        Thread consumerThread = new Thread(new MsgConsumer(msgQueue));
        producerThread.start();
        consumerThread.start();
    }
}

class MsgProducer implements Runnable {
    private Queue<String> msgQueue;
    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 5;
    private Random rd;

    public MsgProducer(Queue<String> queue) {
        msgQueue = queue;
        rd = new Random();
    }

    public void run() {
        while (true) {
            synchronized (msgQueue) {
                msgQueue.add(randomString());
            }
            try {
                Thread.sleep(rd.nextInt(500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            if (rd.nextInt(1000) < 200) {
//                System.out.println("Terminate Producer");
//                break;
//            }
        }
    }

    public String randomString() {
        return rd.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}

class MsgConsumer implements Runnable {
    private Queue<String> msgQueue;
    private Random rd;

    public MsgConsumer(Queue<String> queue) {
        msgQueue = queue;
        rd = new Random();
    }

    public void run() {
        while (true) {
            synchronized (msgQueue) {
                if (!msgQueue.isEmpty()) {
                    System.out.println(msgQueue.poll());
                }
            }
            try {
                Thread.sleep(rd.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            if (rd.nextInt(1000) < 200) {
//                System.out.println("Terminate Consumer");
//                break;
//            }
        }
    }
}
