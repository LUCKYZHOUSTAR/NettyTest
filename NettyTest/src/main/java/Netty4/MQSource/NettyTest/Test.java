package Netty4.MQSource.NettyTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 100; i++) {
            executor.submit(new task());

        }
        executor.shutdown();
        //        Thread[] threads = new Thread[100];
        //        for (int i = 0; i < threads.length; i++) {
        //            threads[i] = new Thread(new task());
        //        }
        //
        //        for (int i = 0; i < 100; i++) {
        //            threads[i].start();
        //        }
    }

}

class task implements Runnable {

    public void run() {
        String result = DefaultSocketClient.sendReceive("localhost", 8888, Thread.currentThread()
            .getName() + "你红啊吗");
        System.out.println(result);
    }

}
