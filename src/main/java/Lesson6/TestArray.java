package Lesson6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestArray {
    private MainApp mainApp;

    @BeforeEach //Выполняется автоматически перед каждым тестом
    public void initialization() {
        mainApp = new MainApp();
    }

    @Test
    public void test1() {
        int[] srcArr = new int[]{5, 8, 9, 4, 3, 3, 3, 3};
        int[] outArr = new int[]{3, 3, 3, 3};
        Assertions.assertArrayEquals(outArr, mainApp.numsAfterFour(srcArr));
    }

    @Test
    public void test2() {
        int[] in = new int[]{5, 8, 9, 0, 3, 3, 3, 3};
        Assertions.assertThrows(RuntimeException.class, () ->
                mainApp.numsAfterFour(in));
    }

    @Test //Сравнение двух объектов (объекты разные)
    public void test3() {
        int[] srcArr = new int[]{5, 8, 9, 0, 3, 3, 3, 4};
        Assertions.assertNotSame(srcArr, mainApp.getSrcArray());
    }

    @Test
    public void test4() {
        int[] srcArr = new int[]{2, 3, 5, 6, 7};
        Assertions.assertFalse(mainApp.checkFoundOneOrFour(srcArr));
    }

    @Test
    public void test5() {
        int[] srcArr = new int[]{1, 1, 4, 4};
        Assertions.assertTrue(mainApp.checkFoundOneOrFour(srcArr));
    }
}