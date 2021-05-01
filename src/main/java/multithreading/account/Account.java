package multithreading.account;

public  class Account{
    private static long balance;
    public static void deposit(long value){
        long newValue = balance + value;
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        balance = newValue;
    }

    public long getBalance() {
        return balance;
    }
}
