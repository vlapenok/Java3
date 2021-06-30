package Lesson1.Test;

public class TwoTypeContainer <T, V> {
    private T firstObject;
    private V secondObject;

    public TwoTypeContainer(T firstObject, V secondObject) {
        this.firstObject = firstObject;
        this.secondObject = secondObject;
    }

    public T getFirstObject() {
        return firstObject;
    }

    public V getSecondObject() {
        return secondObject;
    }

    void showType() {
        System.out.println("Тип первого объекта: " + firstObject.getClass().getName() +
                " Тип второго объекта: " + secondObject.getClass().getName());
    }
}
