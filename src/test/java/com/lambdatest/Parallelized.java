package com.lambdatest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
 
import org.junit.runners.Parameterized;
import org.junit.runners.model.RunnerScheduler;
 
public class Parallelized extends Parameterized {
    private static class ThreadPoolScheduler implements RunnerScheduler {
        private ExecutorService executor;
        public ThreadPoolScheduler() {
            String threads = System.getProperty("junit.parallel.threads", "5");
            int numThreads = Integer.parseInt(threads);
            executor = Executors.newFixedThreadPool(numThreads);
        }
        @Override
        public void finished() {
            executor.shutdown();
            try {
                executor.awaitTermination(10, TimeUnit.MINUTES);
            } catch (InterruptedException exc) {
                throw new RuntimeException(exc);
            }
        }
        @Override
        public void schedule(Runnable childStatement) {
            executor.submit(childStatement);
        }
    }
 
    public Parallelized(Class<?> klass) throws Throwable {
        super(klass);
        setScheduler(new ThreadPoolScheduler());
    }
}