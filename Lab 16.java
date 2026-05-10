import java.util.Random;
import java.util.concurrent.*;

@FunctionalInterface
interface DoNotGiveUp<T> {
    T execute();
}

class TryThreeTimes implements DoNotGiveUp<String> {
    private final Random rand = new Random();

    @Override
    public String execute() {
        for (int i = 1; i <= 3; i++) {
            int num = rand.nextInt(100) + 1;
            System.out.println("TryThreeTimes attempt " + i + ": " + num);

            if (num > 50) {
                return "You succeeded";
            }
        }
        return "Failed";
    }
}

class TryForEver implements DoNotGiveUp<String> {

    @Override
    public String execute() {
        for (int i = 1; i <= 1000; i++) {
            double num = Math.random();
            System.out.println("TryForEver attempt " + i + ": " + num);

            if (num < 0.4) {
                return "You succeeded";
            }
        }
        return "Failed after 1000 attempts";
    }
}

public class Main {
    public static long computeProductNested(int[] a, int[][][] B) {
        long product = 1;

        for (int i = 0; i < B.length; i++) {
            for (int j = 0; j < B[i].length; j++) {
                for (int k = 0; k < B[i][j].length; k++) {
                    product *= a[B[i][j][k]];
                }
            }
        }
        return product;
    }
    public static long computeProductThreaded(int[] a, int[][][] B) throws Exception {
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(cores);

        Future<Long>[] futures = new Future[B.length];

        for (int i = 0; i < B.length; i++) {
            final int row = i;
            futures[i] = pool.submit(() -> {
                long partial = 1;
                for (int j = 0; j < B[row].length; j++) {
                    for (int k = 0; k < B[row][j].length; k++) {
                        partial *= a[B[row][j][k]];
                    }
                }
                return partial;
            });
        }

        long product = 1;
        for (Future<Long> f : futures) {
            product *= f.get();
        }

        pool.shutdown();
        return product;
    }
    public static void timeRuns(int[] a, int[][][] B) throws Exception {
        long t1 = System.nanoTime();
        long p1 = computeProductNested(a, B);
        long t2 = System.nanoTime();

        long t3 = System.nanoTime();
        long p2 = computeProductThreaded(a, B);
        long t4 = System.nanoTime();

        System.out.println("\n=== Timing Results ===");
        System.out.println("Nested loops product: " + p1);
        System.out.println("Nested loops time:    " + (t2 - t1) / 1_000_000 + " ms");

        System.out.println("Threaded product:     " + p2);
        System.out.println("Threaded time:        " + (t4 - t3) / 1_000_000 + " ms");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== PART 1 ===");
        DoNotGiveUp<String> t1 = new TryThreeTimes();
        DoNotGiveUp<String> t2 = new TryForEver();

        System.out.println("TryThreeTimes result: " + t1.execute());
        System.out.println("TryForEver result:    " + t2.execute());
        System.out.println("\n=== PART 2 ===");

        int[] a = new int[10000];
        for (int i = 0; i < a.length; i++) a[i] = (i % 10) + 1;

        // Example 3D array B (small for demonstration)
        int[][][] B = {
            { {1, 2, 3}, {4, 5, 6} },
            { {7, 8, 9}, {10, 11, 12} }
        };

        timeRuns(a, B);
    }
}
