package com.liyuan.hong.globaljavamentor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Create three threads:
 * <p>
 * 1st thread is infinitely writing random number to the collection;
 * 2nd thread is printing sum of the numbers in the collection;
 * 3rd is printing square root of sum of squares of all numbers in the collection.
 * Make these calculations thread-safe using synchronization block. Fix the possible deadlock.
 */
public class Task2 {
    static List<Integer> list;
    public static AtomicBoolean status = new AtomicBoolean(true);

    public static void main(String[] args) {
        list = new ArrayList<>();
        Thread t1 = new Thread(new WriteNumbersRunnable(list));
        Thread t2 = new Thread(new SumNumbersRunnable(list));
        Thread t3 = new Thread(new SquareRootNumbersRunnable(list));
        t1.start();
        t2.start();
        t3.start();
    }
}

class WriteNumbersRunnable implements Runnable {
    private final List<Integer> list;
    private Random rd;

    public WriteNumbersRunnable(List<Integer> list) {
        this.list = list;
        rd = new Random();
    }

    public void run() {
        while (Task2.status.get()) {
            synchronized (list) {
                int i = rd.nextInt(1000);
                list.add(i);
                System.out.println("add " + i + " and sleep");
            }
            try {
                Thread.sleep(123);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class SumNumbersRunnable implements Runnable {
    private final List<Integer> list;
    private int count;
    private int sum;

    public SumNumbersRunnable(List<Integer> list) {
        this.list = list;
        count = 0;
        sum = 0;
    }

    public void run() {
        while (Task2.status.get()) {
            synchronized (list) {
                while (count < list.size()) {
                    sum += list.get(count++);
                    System.out.println("Sum is " + sum);
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

class SquareRootNumbersRunnable implements Runnable {
    private final List<Integer> list;
    private int count;
    private int sumOfSquares;

    public SquareRootNumbersRunnable(List<Integer> list) {
        this.list = list;
        count = 0;
        sumOfSquares = 0;
    }

    public void run() {
        while (Task2.status.get()) {
            synchronized (list) {
                while (count < list.size()) {
                    int i = list.get(count++);
                    sumOfSquares += (i * i);
                    System.out.println("SquareRoot is " + Math.sqrt(sumOfSquares));
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
