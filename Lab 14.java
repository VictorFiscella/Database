class SumRunnable implements Runnable {
    private long sum = 0;

    public long getSum() {
        return sum;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            sum += i;
        }
    }
}

public class SumRunnableMain {
    public static void main(String[] args) throws InterruptedException {
        SumRunnable task = new SumRunnable();
        Thread t = new Thread(task);

        t.start();
        t.join(); 

        System.out.println("Sum (Runnable) = " + task.getSum());
    }
}
