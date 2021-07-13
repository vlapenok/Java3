package Lesson4;

import java.util.ArrayList;
import java.util.List;

public class MainApp {

    public static void main(String[] args) throws InterruptedException {
        List<String> list = new ArrayList<>();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        synchronized(list) {
                            Thread.sleep(200);
                            while(list.size() > 10) {
                                list.wait();
                            }
                            list.add(" ");
                            list.notifyAll();
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
                    while (true) {
                        synchronized (list) {
                            Thread.sleep(1000);
                            while (list.size() == 0) {
                                list.wait();
                            }
                            list.remove(list.size() - 1);
                            if (list.size() < 5) {
                                list.notifyAll();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

        while (true) {
            System.out.println(list.size());
            Thread.sleep(1000);
        }
    }
}
