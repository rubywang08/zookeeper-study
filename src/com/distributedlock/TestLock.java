package src.com.distributedlock;

public class TestLock {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    DistrubutedLock lock = new DistrubutedLock();
                    lock.lock();
                    try {
                        Thread.currentThread().sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.unlock();
                }
            },"Thread"+i);
            thread.start();
        }
    }
}
