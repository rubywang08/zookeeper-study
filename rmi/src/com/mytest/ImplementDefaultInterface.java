package src.com.test;

public class ImplementDefaultInterface implements TestDefault {
    @Override
    public void test() {
        System.out.println("com/test");
    }

    public static void main(String[] args) {
        ImplementDefaultInterface i = new ImplementDefaultInterface();
        i.test();
        i.test1();
        i.test2();
    }
}
