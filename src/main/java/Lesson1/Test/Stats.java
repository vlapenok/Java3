package Lesson1.Test;

public class Stats<T extends Number> {
    private T[] nums;

    public Stats(T[] nums) {
        this.nums = nums;
    }

    Double average() {
        double sum = 0.0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i].intValue();
        }
        System.out.println("Среднее значение: " + sum / nums.length);
        return sum / nums.length;
    }

    public void isSame(Stats<?> another) {
        if(Math.abs(this.average() - another.average()) < 0.6) {
            System.out.println("Одинаковые");
        } else System.out.println("Разные");
    }
}
