package Lesson5;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    Semaphore semaphore; //Ограничение одновременного доступа к ресурсам

    public Tunnel(int maxCars) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        semaphore = new Semaphore(maxCars); //maxCars - макс. кол-во одновременно выполняемых потоков
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                semaphore.acquire(); //Эквайр - блокировка на одновременный доступ, заданный при инициализации
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                semaphore.release(); //Освобождение доступа для следующих потоков
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
