package Lesson4;

public class AbcAbcAbc {
    public static String ltr = "A";

    public static void main(String[] args) {
        Object monitor = new Object();
        int counter = 5;

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < counter; i++) {
                        synchronized (monitor) {
                            while (ltr.equals("B") || ltr.equals("C")) {
                                monitor.wait();
                            }
                            System.out.println(ltr + " " + Thread.currentThread().getName());
                            ltr = "B";
                            monitor.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < counter; i++) {
                        synchronized (monitor) {
                            while (ltr.equals("A") || ltr.equals("C")) {
                                monitor.wait();
                            }
                            System.out.println(ltr + " " + Thread.currentThread().getName());
                            ltr = "C";
                            monitor.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        new Thread(() -> {
            try {
                for (int i = 0; i < counter; i++) {
                    synchronized (monitor) {
                        while (ltr.equals("A") || ltr.equals("B")) {
                            monitor.wait();
                        }
                        System.out.println(ltr + " " + Thread.currentThread().getName());
                        ltr = "A";
                        monitor.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        thread1.start();
        thread2.start();
    }
}
