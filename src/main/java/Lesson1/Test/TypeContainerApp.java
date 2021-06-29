package Lesson1.Test;

public class TypeContainerApp {
    public static void main(String[] args) {
        TypeContainer<Integer> container1 = new TypeContainer<>(42);
        TypeContainer<Integer> container2 = new TypeContainer<>(195);
        int sum = container1.getObject() + container2.getObject();
        System.out.println(sum);

        TypeContainer<String> stringTypeContainer = new TypeContainer<>("Строка");
        String str = stringTypeContainer.getObject();

        container1.showType();
        stringTypeContainer.showType();

        TwoTypeContainer<String, Integer> twoTypeContainer = new TwoTypeContainer<>("Строка", 45);
        twoTypeContainer.showType();

        Stats<Integer> integerStats = new Stats<>(new Integer[]{1, 2, 3, 4, 5});
        Stats<Integer> integerStats1 = new Stats<>(new Integer[]{1, 2, 3});
        Stats<Double> doubleStats = new Stats<>(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0});
        Stats<Double> doubleStats1 = new Stats<>(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0});

//        integerStats.average();
//        integerStats1.average();
//        doubleStats.average();
//        integerStats.isSame(integerStats1);
        doubleStats.isSame(integerStats);


    }
}
