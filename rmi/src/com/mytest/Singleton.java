package src.com.test;

public class Singleton {

    private volatile static Singleton instance;

    private Singleton() {
    }

    ;

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        /*for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ": " + Singleton.getInstance());
                }
            }, "thread" + i).start();
        }*/

        for (int i = 0; i < 10; i++) {
            new Thread(() -> System.out.println(Thread.currentThread().getName() + ": " + Singleton.getInstance()), "thread" + i).start();
        }
    }
}
