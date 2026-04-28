class SumThread extends Thread {
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

public class SumThreadMain {
    public static void main(String[] args) throws InterruptedException {
        SumThread t = new SumThread();

        t.start();
        t.join();

        System.out.println("Sum (Thread) = " + t.getSum());
    }
}
