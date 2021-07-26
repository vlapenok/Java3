package Lesson4.Test;

public class RaceCondition {

    private static int counter = 0;
    private static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Runnable () {
            @Override
            public void run() {
                for(int i = 0; i < 10000; i++) {
                    synchronized (obj) {
                        counter++;
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 10000; i++) {
                    synchronized(obj) {
                        counter++;
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(counter);
    }
}
