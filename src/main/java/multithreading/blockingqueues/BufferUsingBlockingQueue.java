package multithreading.blockingqueues;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Buffer to store one integer value using BlockingQueue.
 * 2 threads put integers to queue. One thread gets integers from queue
 */
public class BufferUsingBlockingQueue {
    private static final LinkedBlockingQueue<Integer> buffer = new LinkedBlockingQueue<>(10);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new PutTuBufferTask(100));
        executorService.submit(new PutTuBufferTask(200));
        executorService.submit(new GetFromBufferTask());
        executorService.shutdown();
    }

    private static  class PutTuBufferTask implements  Runnable{
        private int id;
        PutTuBufferTask(int id){
            this.id = id;
        }
        @Override
        public void run() {
            for (int i = 0; i < 100 ; i++){
                try {
                    //int value = (int) (Math.random() * 10);
                    buffer.put(id + i);
                    System.out.println(id + i + "  put. Size = " + buffer.size());
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private static class GetFromBufferTask implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 200 ; i++){
                try {
                    System.out.println("\t\t\t\t\t" + buffer.take() + " polled. Size = " + buffer.size());
                    Thread.sleep((long) (Math.random() * 600));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
