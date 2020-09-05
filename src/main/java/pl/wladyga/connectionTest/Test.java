package pl.wladyga.connectionTest;

import java.util.concurrent.BlockingQueue;

public class Test implements Runnable {

    protected BlockingQueue queue = null;

    public Test(BlockingQueue test) {
        queue = test;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (queue.isEmpty()) {
                    System.out.println(".");
                }

                System.out.println("-> " + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
