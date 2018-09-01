package src.com.mytest.volatiletest;

public class TestVolatile2 {
    private static volatile String var;
    private static String nor;

    public static void main(String[] args) {
        var = "a";
        nor = "b";
    }


}
