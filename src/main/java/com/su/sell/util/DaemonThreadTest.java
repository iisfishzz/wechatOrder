package com.su.sell.util;

public class DaemonThreadTest {
    public static void main(String[] arg) {
        Thread t = new Thread(()->{
                Thread innerT = new Thread(()->{
                    while(true){
                        System.out.println(Thread.currentThread().getName()+"do something");
                        try {
                            Thread.sleep(1_000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            innerT.setDaemon(true);
            innerT.start();
            System.out.println(Thread.currentThread().getName() + "running");
            try {
                Thread.sleep(15_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "done");
        });
        t.start();
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "done");

    }
}
