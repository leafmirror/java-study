package com.tracenull.future;

import java.util.concurrent.CompletableFuture;

public class JDKThreadPoolCompeleteExecutorTest2 {
    public static void main(String[] args) throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "-女神：我开始化妆了，好了我叫你。");
//            throw new RuntimeException("男神约我看电影了，我们下次再约吧，你是个好人。");
            return "女神好了";
        }).handleAsync((result, exception) -> {
            if (exception != null) {
                System.out.println(Thread.currentThread().getName() + "-女神放你鸽子了！");
                return exception.getCause();
            } else {
                return result;
            }
        }).thenApplyAsync((returnStr) -> {
            System.out.println(Thread.currentThread().getName() + "-" + returnStr);
            return returnStr;
        });

        System.out.println(Thread.currentThread().getName() + "-等女神化妆的时候可以干点自己的事情。");
        Thread.currentThread().join();
    }
}
