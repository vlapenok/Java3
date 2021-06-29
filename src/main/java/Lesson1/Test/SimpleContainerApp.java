package Lesson1.Test;

public class SimpleContainerApp {
    public static void main(String[] args) {
        SimpleContainer container1 = new SimpleContainer(42);
        SimpleContainer container2 = new SimpleContainer(195);
        if (container1.getObject() instanceof Integer && container2.getObject() instanceof Integer) {
            System.out.println((Integer) container1.getObject() + (Integer) container2.getObject());
        } else {
            System.out.println("Типы отличаются");
        }
    }
}
