package Lesson4.Test;

import java.util.ArrayList;
import java.util.List;

public class WaitAndNotifyApp {
    static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        synchronized (list) {
                            Thread.sleep(100);
                            while(list.size() > 20) {
                                list.wait();
                            }
                            list.add("str");
                            list.notifyAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        producer.start();

        Thread consumer = new Thread(new Runnable() {
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
        consumer.start();

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(list.size());
        }
    }
}
