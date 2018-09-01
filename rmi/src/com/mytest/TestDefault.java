package src.com.mytest;

interface TestDefault {
    void test();

    default void test1() {
        System.out.println("test1");
    }

    default void test2() {
        System.out.println("test2");
    }

    static void test3() {
        System.out.println("test3");
    }
}
