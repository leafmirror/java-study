package com.tracenull.j20201209;

import java.util.concurrent.*;

/**
 * 一个JDK线程池BUG引发的GC机制思考
 * https://mp.weixin.qq.com/s/81qQI10005BSZN9mTwsw1g
 */
public class ThreadPoolTest {
    public static void main(String[] args) {
        final ThreadPoolTest threadPoolTest = new ThreadPoolTest();
        for (int i = 0; i < 8; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Future<String> future = threadPoolTest.submit();
                        try {
                            String s = future.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (Error e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            // 子线程不停gc，模拟偶发的gc
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        System.gc();
                    }
                }
            }).start();
        }
    }

    // 异步执行任务
    public Future<String> submit() {
        // 关键点，通过Executors.newSingleThreadExecutor创建一个单线程的线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        FutureTask<String> futureTask = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(50);
                return System.currentTimeMillis() + " ";
            }
        });
        executorService.execute(futureTask);
        return futureTask;
    }
}
