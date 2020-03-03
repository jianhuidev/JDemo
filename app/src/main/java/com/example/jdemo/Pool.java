package com.example.jdemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pool {

    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    public static void execute(Runnable command) {
        fixedThreadPool.execute(command);
    }
}
