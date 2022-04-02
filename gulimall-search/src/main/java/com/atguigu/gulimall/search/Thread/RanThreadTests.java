package com.atguigu.gulimall.search.Thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RanThreadTests {
    static final ExecutorService executorService = Executors.newFixedThreadPool(5);
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main start");
//        CompletableFuture future = CompletableFuture.runAsync(()->{
//            System.out.println(Thread.currentThread().getName());
//            int i = 10 /2;
//            System.out.println("result: "+ i);
//        }, executorService);
//
//        try {
//            future.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

//        CompletableFuture future = CompletableFuture.supplyAsync(()->{
//            return 1;
//        },executorService);
//
//        int result = (Integer) future.get();
//        System.out.println("result: " +result);
//        System.out.println("main end");

        CompletableFuture<Integer> future01 = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName());
            int i = 10 /2;
            System.out.println("result: "+ i);
            return i;
        }, executorService);

        CompletableFuture<String>  future02 = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName());
            int i = 10 /5;
            System.out.println("result: "+ i);
            return i+"";
        }, executorService);

//        future01.runAfterBothAsync(future02,()-> {
//            System.out.println("run after future1 and future2");
//        },executorService);
          CompletableFuture<Void> future = future01.thenAcceptBothAsync(future02,(f1,f2)->{
              System.out.println("task3 start");
          },executorService);

        CompletableFuture<String> future1 = future01.thenApply((ret)->{
            System.out.println("task3 start");
            return "";
        });
        //System.out.println("main end" + future.get());
    }
}
