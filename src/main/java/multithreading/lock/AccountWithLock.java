package multithreading.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountWithLock {
    private static long balance;
    private static Lock lock = new ReentrantLock();
    private static Condition newDeposit = lock.newCondition();

    public static  void deposit(long value) {
        lock.lock();
        try{
            balance += value;
            System.out.println("New Deposit " + value + " \t\t\t\t\t" + getBalance());
            newDeposit.signalAll();
        }
        finally {
            lock.unlock();
        }


    }

    public static  void withdraw(long value){
        lock.lock();

        try{
            while (balance < value) {
                newDeposit.await();
            }
            balance -= value;
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    public static long getBalance() {
        return balance;
    }
}
