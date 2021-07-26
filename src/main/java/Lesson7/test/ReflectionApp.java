package Lesson7.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionApp {

    static class Cat {
        public String name;
        public String color;
        private int age;

        public Cat(String name, String color, int age) {
            this.name = name;
            this.color = color;
            this.age = age;
        }

        public Cat() {
        }

        @MyAnnotation
        public String getName() {
            return name;
        }

        public String getColor() {
            return color;
        }

        @MyValueAnnotation(value = 10)
        public int getAge() {
            return age;
        }

        private void info() {
            System.out.println(this);
        }

        private void info(String str, int times) {
            for (int i = 0; i < times; i++) {
                System.out.println(str);
            }
        }

        @Override
        public String toString() {
            return "Cat{" +
                    "name='" + name + '\'' +
                    ", color='" + color + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static void main(String[] args) {
        Class catClass = Cat.class;
        Field[] publicField = catClass.getFields();
        for (Field field : publicField) {
            System.out.println(field.getName() + " " + field.getType().getName());
        }
        System.out.println();
        Field[] allFields = catClass.getDeclaredFields();
        for (Field field : allFields) {
            System.out.println(field.getName() + " " + field.getType().getName());
        }

        Cat cat = new Cat();
        System.out.println(cat + "\n");
        try {
            Field fieldCatName = cat.getClass().getField("name");
            fieldCatName.set(cat, "Barsik");
            Field fieldCatAge = cat.getClass().getDeclaredField("age");
            fieldCatAge.setAccessible(true);
            fieldCatAge.set(cat, 5);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(cat + "\n");

        Cat cat1 = new Cat("Shusha", "black", 10);

        try {
            Method method = cat1.getClass().getDeclaredMethod("info");
            method.setAccessible(true);
            method.invoke(cat1);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println();

        Method[] declaredMethods = catClass.getDeclaredMethods();
        for (Method m : declaredMethods) {
            System.out.println(m.getName() + " " + Arrays.toString(m.getParameterTypes()));
        }
        try {
            Method methodInfo = catClass.getDeclaredMethod("info", String.class, int.class);
            methodInfo.setAccessible(true);
            methodInfo.invoke(cat1, "hello kitty", 5);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Constructor[] constructors = catClass.getConstructors();
        for (Constructor c : constructors) {
            System.out.println(c.getName() + " " + Arrays.toString(c.getParameterTypes()));
        }
        try {
            Constructor constructor = catClass.getConstructor(String.class, String.class, int.class);
            Object cat2 = constructor.newInstance("Bars", "cian", 3);
            ((Cat) cat2).info();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        List<Method> methodList = new ArrayList<>();
        for (Method m : catClass.getDeclaredMethods()) {
            methodList.add(m);
        }
        System.out.println("---");

        try {
            Method getAgeMethod = catClass.getMethod("getAge");
            MyValueAnnotation annotation = getAgeMethod.getAnnotation(MyValueAnnotation.class);
            int value = annotation.value();
            int value1 = catClass.getMethod("getAge").getAnnotation(MyValueAnnotation.class).value();
            if(value > 6) {
                System.out.println("Method name: " + getAgeMethod.getName() + "\nvalue: " + value1);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        for (Method m : methodList) {
            if(m.getAnnotation(MyValueAnnotation.class) == null) {
                continue;
            }
            if(m.getAnnotation(MyValueAnnotation.class).value() == 10) {
                System.out.println("Method " + m.getName() + " with annotation value " + 10);
            }
            System.out.println(m);
        }
    }
}
