package multithreading.synchaccount;

import multithreading.account.Account;
import multithreading.account.AddDollarTask;
import multithreading.account.NotSynchronized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedAccountTest {
    public static void main(String[] args) {
        SynchronizedAccount account = new SynchronizedAccount();
        ExecutorService executor  = Executors.newCachedThreadPool();
        System.out.println(account.getBalance());
        for (int i = 0; i < 100; i++) {
            executor.submit(new SynchronTask(i));
        }

        executor.shutdown();

        while(!executor.isTerminated()){
        }
        System.out.println();
        System.out.println(account.getBalance());
    }

}
