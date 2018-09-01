package src.com.mytest.createobjtest;

public class B extends A{
    public static int setValue(int i) {
        System.out.println("B.setValue=" + i);
        return i;
    }
    {
        System.out.println("Class B");
    }
    static {
        System.out.println("Static Class B");
    }

    public B() {
        System.out.println("Constructor B");
    }
}

