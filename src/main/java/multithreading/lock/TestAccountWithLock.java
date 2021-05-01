package multithreading.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestAccountWithLock {
    public static void main(String[] args) {
        ExecutorService executorService =  executorService = Executors.newFixedThreadPool(2);
        try {
            executorService.submit(new DepositTask());
            executorService.submit(new WithdrawTask());

        } finally {
            executorService.shutdown();
        }
    }

    private static class DepositTask  implements Runnable{

        @Override
        public void run() {
            while(true) {
                long deposit = (long) (Math.random()*10);
                Account.deposit(deposit);
                try {
                    Thread.sleep((long) (Math.random()*6000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private  static  class WithdrawTask implements Runnable{

        @Override
        public void run() {
            while(true) {
                long withdraw = (long) (Math.random()*20);
                Account.withdraw(withdraw);
                try {
                    Thread.sleep((long) (Math.random()*6000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    private static class Account{
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
                    System.out.println("Waiting for new deposit!!! balance < value ");
                }
                balance -= value;
                System.out.println("withdraw  " + value + " \t\t\t\t\t" + getBalance());
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
}
