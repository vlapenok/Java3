package Lesson1;

import java.util.Arrays;
import java.util.List;

public class ArrayApp<T> {
    private T[] arr;

    public ArrayApp(T[] arr) {
        this.arr = arr;
    }

    public T[] getArr() {
        return arr;
    }

    /**
     * Метод меняет местами элементы массива
     */
    public T[] revers() {
        for (int i = 0; i < arr.length; i++) {
            T temp = arr[i];
            arr[i] = arr[arr.length - 1];
            arr[arr.length - 1] = temp;
        }
        return arr;
    }

    // Метод преобразовывает массив любого типа элементов в список
    public <V> List<V> convertToArrayList(V[] arr) {
        return Arrays.asList(arr);
    }

    public static class MainApp {
        public static void main(String[] args) {
            ArrayApp<String> stringContainer = new ArrayApp<>(new String[] {"100", "200"});
            ArrayApp<Integer> integerContainer = new ArrayApp<>(new Integer[] {1, 2});

            System.out.println(Arrays.toString(stringContainer.getArr())); // [100, 200]
            System.out.println(Arrays.toString(stringContainer.revers())); // [200, 100]

            System.out.println(Arrays.toString(integerContainer.getArr())); // [1, 2]
            System.out.println(Arrays.toString(integerContainer.revers())); // [2, 1]

            System.out.println(stringContainer.convertToArrayList(stringContainer.getArr())); // [200, 100]
            System.out.println(integerContainer.convertToArrayList(integerContainer.getArr())); // [2, 1]

        }
    }
}
