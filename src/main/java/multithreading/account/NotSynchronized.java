package multithreading.account;

/*
    Create and launch 100 threads, each of which adds a dollar to an account.
    Define a class named Account to model the account, a class named AddDollarTask to
    add a dollar to the account, and a main class that creates and launches threads.
*/

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotSynchronized {
    public static void main(String[] args) {
        Account account = new Account();
        ExecutorService executorService  = Executors.newCachedThreadPool();
        System.out.println(account.getBalance());
        for (int i = 0; i < 100; i++) {
            executorService.submit(new AddDollarTask(i));
        }

        executorService.shutdown();

        while(!executorService.isTerminated()){
        }
        System.out.println();
        System.out.println(account.getBalance());
    }

}


