package Lesson1.Test;

public class TypeContainer<T> {
    private T object;

    public TypeContainer(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public void showType() {
        System.out.println("Тип объекта: " + object.getClass().getName());
    }
}
