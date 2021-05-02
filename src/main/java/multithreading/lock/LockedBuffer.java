package multithreading.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* Buffer to store one integer value.
* The buffer provides the method write(int) to add an int value to the buffer and the method read()
* to read and delete an int value from the buffer. To synchronize the operations, used a lock with
* two conditions: notEmpty (i.e., the buffer is not empty) and notFull (i.e., the buffer is not full).
* When a task adds an int to the buffer, if the buffer is full, the task will wait for the notFull condition.
* When a task reads an int from the buffer, if the buffer is empty, the task will wait
* for the notEmpty condition
*/
public class LockedBuffer {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new PutTuBufferTask());
        executorService.submit(new GetFromBufferTask());
        executorService.shutdown();
    }




    private static  class PutTuBufferTask implements  Runnable{
        @Override
        public void run() {
            while(true){
                int value = (int) (Math.random() * 10);
                Buffer.write(value);
                try {
                    Thread.sleep((long) (Math.random() * 4000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private static class GetFromBufferTask implements Runnable{

        @Override
        public void run() {
            while(true){
                Buffer.read();
                try {
                    Thread.sleep((long) (Math.random() * 3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Buffer{
        private static List<Integer> buffer = new ArrayList<>();
        private static Lock lock = new ReentrantLock(true);
        private static Condition notEmpty = lock.newCondition();
        private static Condition notFull = lock.newCondition();

        public static void write(int value){
            lock.lock();
            if(buffer.size() == 1){
                System.out.println(value + " - waiting in queue. Buffer is full.");
                try {
                    while(buffer.size() == 1) {
                        notFull.await();
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            buffer.add(value);
            System.out.println("Value - " + value + " put to buffer");
            notEmpty.signalAll();
            lock.unlock();
        }

        public static void read(){
            lock.lock();
            if(buffer.size() == 0){
                System.out.println("Nothing to read!!! Buffer is empty!!!");
                try {
                    while(buffer.size() == 0) {
                        notEmpty.await();
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Value from buffer" + buffer.remove(0));
            notFull.signalAll();
            lock.unlock();
        }


    }
}
