package Lesson1;

public class FruitApp {
    public static void main(String[] args) {
        Apple apple = new Apple("Яблоко", 1.0f);
        Orange orange = new Orange("Апельсин", 1.5f);
        Box<Apple> appleBox = new Box<>();
        Box<Apple> appleBox1 = new Box<>();
        Box<Orange> orangeBox = new Box<>();

        appleBox.addFruit(apple);
        appleBox.addFruit(apple);
        appleBox.addFruit(apple);
        orangeBox.addFruit(orange);
        orangeBox.addFruit(orange);

        System.out.println("Вес коробки с яблоками: " + appleBox.getBoxWeight());
        System.out.println("Вес коробки с апельсинами: " + orangeBox.getBoxWeight());

        System.out.println("Вес коробок равен? - " + appleBox.compare(orangeBox));

        appleBox.transfer(appleBox1, apple);

        System.out.println("Вес коробок равен? - " + orangeBox.compare(appleBox1));
    }
}
