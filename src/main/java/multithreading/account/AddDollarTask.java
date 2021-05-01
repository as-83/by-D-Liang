package multithreading.account;

public class AddDollarTask implements  Runnable{
    private  int number;
    public AddDollarTask(int i) {
        number = i;
    }

    @Override
    public void run() {
        Account.deposit(1);
        System.out.println(number);
    }
}
