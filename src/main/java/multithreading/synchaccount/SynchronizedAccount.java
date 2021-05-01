package multithreading.synchaccount;

public class SynchronizedAccount {
    private static long balance;

    public static synchronized void deposit(long value) {
        long newValue = balance + value;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        balance = newValue;
    }

    public long getBalance() {
        return balance;
    }
}
