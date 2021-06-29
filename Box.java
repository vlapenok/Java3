package Lesson1;

import java.util.ArrayList;
import java.util.List;

public class Box <T extends Fruit> {
    private T fruit;
    private float boxWeight;
    List<Fruit> list = new ArrayList<>();

    public List<Fruit> getList() {
        return list;
    }

    // Добавляем фрукты в список
    public void addFruit(T fruit) {
        list.add(fruit);
    }

    // Удаляем фрукты из списка
    public void removeFruit(T fruit) {
        list.remove(fruit);
    }

    // Считаем вес коробки со всеми фруктами из списка
    public float getBoxWeight() {
        for (int i = 0; i < list.size(); i++) {
            boxWeight += list.get(i).getWeight();
        }
        return boxWeight;
    }

    // Сравниваем вес коробок с разными фруктами
    public boolean compare(Box<?> another) {
        return Math.abs(this.boxWeight - another.boxWeight) < 0.001;
    }

    // Перекладываем фрукты из одной коробки в другую
    public void transfer(Box<T> another, T fruit) {
        another.list.add(fruit);
        list.remove(fruit);
    }
}
