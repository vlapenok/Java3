package Lesson4.Test;

public class CounterApp {

    public static final Object object = new Object();

    public static class Counter {
        private int counter = 0;
        public int getCounter() {
            return counter;
        }
        private void increment() {
            counter++;
        }
    }

    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    synchronized (object) {
                        try {
                            Thread.sleep(200);
                            counter.increment();
                            System.out.println(counter.getCounter() + " " + Thread.currentThread().getName());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    synchronized (object) {
                        try {
                            Thread.sleep(200);
                            counter.increment();
                            System.out.println(counter.getCounter() + " " + Thread.currentThread().getName());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    synchronized (object) {
                        try {
                            Thread.sleep(200);
                            counter.increment();
                            System.out.println(counter.getCounter() + " " + Thread.currentThread().getName());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
