package src.com.mytest;

public class ImplementDefaultInterface implements TestDefault {
    @Override
    public void test() {
        System.out.println("com/mytest");
    }

    public static void main(String[] args) {
        ImplementDefaultInterface i = new ImplementDefaultInterface();
        i.test();
        i.test1();
        i.test2();
    }
}
