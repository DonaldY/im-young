package com.donald.sdk;

/**
 * @author donald
 * @date 2021/08/28
 */
public class Test {

    private static ThreadLocal<Long> requestId = new ThreadLocal<>();

    public static void main(String[] args) {

        Thread thread1 = generateThread("线程1", 1);
        Thread thread2 = generateThread("线程2", 2);

        thread1.start();
        thread2.start();
    }

    private static Thread generateThread(String name, long num) {

        Thread thread = new Thread(){

            public void run() {
                requestId.set(num);
                System.out.println(this.getName() + " : " + requestId.get());
            }
        };

        thread.setName(name);

        return thread;
    }
}
