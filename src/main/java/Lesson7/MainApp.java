package Lesson7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        start(TestClass.class); //Передаем объект класса TestClass типа Class в метод start
    }

    public static void start(Class testClass) {
        List<Method> methodList = new ArrayList<>();
        for(Method method : testClass.getDeclaredMethods()) { //Наполняем список методами с аннотацией @Test
            if(method.isAnnotationPresent(BeforeSuite.class) || method.isAnnotationPresent(AfterSuite.class)) {
                continue;
            }
            methodList.add(method);
        }

        //Сортируем список по возрастанию значения value у аннотации @Test
        Comparator<Method> comparator = new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                return o1.getAnnotation(Test.class).value() - o2.getAnnotation(Test.class).value();
            }
        };
        methodList.sort(comparator);

//        methodList.sort(Comparator.comparingInt(i -> i.getAnnotation(Test.class).value())); //Тоже самое, но сокращенно

        //Добавляем в список методы с аннотациями @BeforeSuite и @AfterSuite
        for(Method m : testClass.getDeclaredMethods()) {
            if(m.isAnnotationPresent(BeforeSuite.class)) {
                if(methodList.get(0).isAnnotationPresent(BeforeSuite.class)) {
                    throw new RuntimeException("Аннотация @BeforeSuite может быть только в одном экземпляре");
                } else {
                    methodList.add(0, m);
                }
            }
            if(m.isAnnotationPresent(AfterSuite.class)) {
                if(methodList.get(methodList.size() - 1).isAnnotationPresent(AfterSuite.class)) {
                    throw new RuntimeException("Аннотация @AfterSuite может быть только в одном экземпляре");
                } else {
                    methodList.add(m);
                }
            }
        }

        //Вызываем методы
        for(Method m : methodList) {
            try {
                m.setAccessible(true); //Доступ к приватным методам
                m.invoke(testClass.newInstance()); //Вызов метода через создание экземпляра объекта
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
