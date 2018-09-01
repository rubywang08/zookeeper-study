package src.com.mytest.createobjtest;

public class A {
    private int a;
    private static int b = setValue(1);

    public static int setValue(int i) {
        System.out.println("A.setValue=" + i);
        return i;
    }
    {
        System.out.println("Class A");
    }
    static {
        System.out.println("Static Class A");
    }

    public A() {
        System.out.println("Constructor A");
    }
}

