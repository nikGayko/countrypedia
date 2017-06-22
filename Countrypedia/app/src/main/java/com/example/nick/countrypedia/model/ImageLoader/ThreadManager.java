package com.example.nick.countrypedia.model.ImageLoader;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {

    private final int THREAD_COUNT = 3;
    private final ExecutorService mExecutorService;

    ThreadManager() {
        mExecutorService = Executors.newFixedThreadPool(THREAD_COUNT);
    }



}
