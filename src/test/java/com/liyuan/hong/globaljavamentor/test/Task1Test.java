package com.liyuan.hong.globaljavamentor.test;

import com.liyuan.hong.globaljavamentor.Task1;
import org.junit.Assert;
import org.junit.Test;

public class Task1Test {

    @Test
    public void testHashMap() {
        Task1.runWithHashMap();
    }

    @Test
    public void testConcurrentHashMap() {
        Task1.runWithConcurrentHashMap();
    }

    @Test
    public void testSynchronizedMap() {
        Task1.runWithSynchronizedMap();
    }
}
