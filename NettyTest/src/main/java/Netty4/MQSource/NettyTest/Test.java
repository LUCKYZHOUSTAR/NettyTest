package Netty4.MQSource.NettyTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

    private static AtomicInteger  count = new AtomicInteger(0);
    private static CountDownLatch latch = new CountDownLatch(100000);

    public static void main(String[] args) throws Exception {
        final long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100000; i++) {
            executor.submit(new task(latch));

        }
        latch.await();
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) / 1000);
        executor.shutdown();

    }

}

class task implements Runnable {

    private CountDownLatch latch;

    public task(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        String result = DefaultSocketClient.sendReceive("localhost", 8888, Thread.currentThread()
            .getName() + "你红啊吗");
        latch.countDown();
        System.out.println(result);
    }

}
