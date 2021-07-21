package Lesson7;

public class TestClass {

    @BeforeSuite
    private void testBeforeSuite() {
        System.out.println("Test before suite");
    }

    @AfterSuite
    private void testAfterSuite() {
        System.out.println("Test after suite");
    }

    @Test
    private void test1() {
        System.out.println("Test1");
    }

    @Test(value = 3)
    private void test2() {
        System.out.println("Test2");
    }

    @Test(value = 9)
    private void test3() {
        System.out.println("Test3");
    }

    @Test(value = 7)
    private void test4() {
        System.out.println("Test4");
    }

    @Test(value = 2)
    private void test5() {
        System.out.println("Test5");
    }
}
