package src.com.mytest.volatiletest;

public class TestVolatile {
    private static volatile String var;
    private static String nor;

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            var = "a";
            nor = "b";
        }).start();

        new Thread(() -> {
            System.out.println("var=" + var);
            System.out.println("nor=" + nor);
        }).start();
    }


}
