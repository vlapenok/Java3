package Lesson6;

import java.util.Arrays;

public class MainApp {
    private int[] srcArray = new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7};

    public int[] getSrcArray() {
        return srcArray;
    }

    public void main(String[] args) {
        System.out.println(Arrays.toString(numsAfterFour(srcArray))); // [1, 7]
        System.out.println(checkFoundOneOrFour(srcArray)); //true
    }

    /**
     * 2. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
     * Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
     * идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку,
     * иначе в методе необходимо выбросить RuntimeException.
     * Написать набор тестов для этого метода (по 3-4 варианта входных данных). Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
     */
    public int[] numsAfterFour(int[] arr) {
        int temp = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 4) {
                temp = i;
            }
        }
        if (temp == 0) {
            throw new RuntimeException("Number 4 not found");
        }
        return Arrays.copyOfRange(arr, temp + 1, arr.length);
    }

    /**
     * 3. Написать метод, который проверяет состав массива из чисел 1 и 4.
     * Если в нем нет хоть одной четверки или единицы, то метод вернет false;
     * Написать набор тестов для этого метода (по 3-4 варианта входных данных).
     */
    public boolean checkFoundOneOrFour(int[] arr) {
        boolean found = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1 || arr[i] == 4) {
                found = true;
                break;
            }
        }
        return found;
    }
}
