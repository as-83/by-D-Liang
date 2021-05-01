package multithreading.synchaccount;

import multithreading.account.Account;

public class SynchronTask implements  Runnable {
    private  int number;
    public SynchronTask(int i) {
        number = i;
    }

    @Override
    public void run() {
        SynchronizedAccount.deposit(1);
        System.out.println(number);
    }
}
