import java.util.Random;

public class SumSquaresAllInOne {

    // Single Thread version
    public static long sumSquaresSingle(int[] arr) {
        long sum = 0;
        for (int x : arr) {
            sum += (long) x * x;
        }
        return sum;
    }

    // Two Thread version
    public static long sumSquaresTwoThread(int[] arr) throws InterruptedException {
        final long[] partial = new long[2];

        Thread t1 = new Thread(() -> {
            long s = 0;
            for (int i = 0; i < arr.length / 2; i++) {
                s += (long) arr[i] * arr[i];
            }
            partial[0] = s;
        });

        Thread t2 = new Thread(() -> {
            long s = 0;
            for (int i = arr.length / 2; i < arr.length; i++) {
                s += (long) arr[i] * arr[i];
            }
            partial[1] = s;
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        return partial[0] + partial[1];
    }

    // This is the main program
    public static void main(String[] args) throws Exception {

        if (args.length == 0) {
            System.out.println("Usage: java SumSquaresAllInOne <n>");
            return;
        }

        int n = Integer.parseInt(args[0]);
        Random r = new Random();

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = r.nextInt(100) + 1;
        }

        // Single thread timing
        long start = System.nanoTime();
        long s1 = sumSquaresSingle(arr);
        long end = System.nanoTime();
        long singleTime = end - start;

        // Two thread timing
        start = System.nanoTime();
        long s2 = sumSquaresTwoThread(arr);
        end = System.nanoTime();
        long twoTime = end - start;

        // Output
        System.out.println("n = " + n);
        System.out.println("Single-thread sum = " + s1);
        System.out.println("Two-thread sum    = " + s2);
        System.out.println("Single-thread time (ns): " + singleTime);
        System.out.println("Two-thread time    (ns): " + twoTime);
    }
}
