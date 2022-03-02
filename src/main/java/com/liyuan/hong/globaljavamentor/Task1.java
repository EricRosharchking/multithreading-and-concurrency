package com.liyuan.hong.globaljavamentor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create HashMap<Integer, Integer>. The first thread adds elements into the map,
 * the other go along the given map and sum the values. Threads should work before
 * catching ConcurrentModificationException. Try to fix the problem with
 * ConcurrentHashMap and Collections.synchronizedMap(). What has happened after
 * simple Map implementation exchanging? How it can be fixed in code? Try to write
 * your custom ThreadSafeMap with synchronization and without. Run your samples
 * with different versions of Java (6, 8, and 10, 11) and measure the performance.
 * Provide a simple report to your mentor.
 */
public class Task1 {
    public static Map<Integer, Integer> numbersMap;

    public static void main(String[] args) {
//        runWithHashMap();

//        runWithConcurrentHashMap();

//        runWithSynchronizedMap();
    }

    public static void runWithHashMap() {
        initHashMap();
        int limit = 100;
        runThreads(new FillMapRunnable(numbersMap, limit), new SumValueRunnable(numbersMap, limit));
        limit = 1000;
        runThreads(new FillMapRunnable(numbersMap, limit), new SumValueRunnable(numbersMap, limit));
    }

    public static void runWithConcurrentHashMap() {
        initConcurrentHashMap();
        int limit = 100;
        runThreads(new FillMapRunnable(numbersMap, limit), new SumValueRunnable(numbersMap, limit));
        limit = 1000;
        runThreads(new FillMapRunnable(numbersMap, limit), new SumValueRunnable(numbersMap, limit));
    }

    public static void runWithSynchronizedMap() {
        numbersMap = returnSynchronizedMap();
        int limit = 100;
        runThreads(new FillMapRunnable(numbersMap, limit), new SumSynchronizedValuesRunnable(numbersMap, limit));
        limit = 1000;
        runThreads(new FillMapRunnable(numbersMap, limit), new SumSynchronizedValuesRunnable(numbersMap, limit));
    }

    public static void runThreads(Runnable r1, Runnable r2) {
        Thread t1 = new Thread(r1);
        t1.start();
        Thread t2 = new Thread(r2);
        Thread t3 = new Thread(r2);
        t2.start();
        t3.start();
        try {
//            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void initHashMap() {
        numbersMap = new HashMap<Integer, Integer>();
    }

    public static void initConcurrentHashMap() {
        numbersMap = new ConcurrentHashMap<Integer, Integer>();
    }

    public static Map<Integer, Integer> returnSynchronizedMap() {
        return Collections.synchronizedMap(new HashMap<Integer, Integer>());
    }
}

class FillMapRunnable implements Runnable {
    private Map<Integer, Integer> map;
    private int limit;
    private Random rd;

    public FillMapRunnable(Map<Integer, Integer> _map, int _limit) {
        map = _map;
        limit = _limit;
        rd = new Random();
    }

    public void run() {
        for (int i = 0; i < limit; i++) {
            map.put(i, rd.nextInt(2));
//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}

class SumValueRunnable implements Runnable {
    private Map<Integer, Integer> map;
    private int limit;
    private int sum;

    public SumValueRunnable(Map<Integer, Integer> _map, int _limit) {
        map = _map;
        limit = _limit;
        sum = 0;
    }

    public void run() {
        for (int i : map.values()) {
            sum += i;
//            try {
//                Thread.sleep(3);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        System.out.println("Sum of " + limit + " numbers is " + sum);
    }
}

class SumSynchronizedValuesRunnable implements Runnable {
    private Map<Integer, Integer> map;
    private int limit;
    private int sum;

    public SumSynchronizedValuesRunnable(Map<Integer, Integer> _values, int _limit) {
        map = _values;
        limit = _limit;
    }

    public void run() {
        synchronized (map) {
//            Iterator<Integer> values = map.values().iterator();
            for (int i : map.values()) {
                sum += i;
//                try {
//                    Thread.sleep(30);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }
        System.out.println("Sum of " + limit + " numbers is " + sum);
    }
}
