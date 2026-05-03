import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

class BankAccount {

    private double balance;

    public BankAccount(double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("The initial balance cannot be negative");
        }
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The deposit must be > 0");
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The withdraw amount must be > 0");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
    
    public void transfer(BankAccount target, double amount) {
        if (target == null) {
            throw new IllegalArgumentException("Target cannot be null");
        }
        this.withdraw(amount);
        target.deposit(amount);
    }
}
class Computations {

    public static int fibonacci(int n) {
        if (n < 0) throw new IllegalArgumentException();
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    public static boolean isOdd(int n) {
        return !isEven(n);
    }

    public static double toCelsius(double f) {
        return (f - 32) * 5.0 / 9.0;
    }

    public static double toFahrenheit(double c) {
        return (c * 9.0 / 5.0) + 32;
    }
}
public class AllTests {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount(100.0);
    }

    @AfterEach
    void tearDown() {
        account = null;
    }

    @Test
    void testDeposit() {
        account.deposit(50);
        assertEquals(150.0, account.getBalance());
    }

    @Test
    void testWithdraw() {
        account.withdraw(40);
        assertEquals(60.0, account.getBalance());
    }

    @Test
    void testInvalidDeposit() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-10);
        });
    }

    @Test
    void testOverdraft() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(200);
        });
    }

    @Test
    void testNegativeInitialBalance() {
        assertThrows(IllegalArgumentException.class, () -> {
            new BankAccount(-50);
        });
    }

    @Test
    void testTransfer() {
        BankAccount other = new BankAccount(50);
        account.transfer(other, 30);

        assertEquals(70.0, account.getBalance());
        assertEquals(80.0, other.getBalance());
    }

    @Test
    void testArrayValuesGreaterThan20() {
        int[] values = {25, 30, 40};

        for (int v : values) {
            assertTrue(v >= 20, "Value less than 20: " + v);
        }
    }

    @Test
    void testStringsEqual() {
        String strOne = "hello";
        String strTwo = "hello";

        assertEquals(strOne, strTwo);
    }

    @Test
    void testFibonacci() {
        assertEquals(0, Computations.fibonacci(0));
        assertEquals(1, Computations.fibonacci(1));
        assertEquals(5, Computations.fibonacci(5));
    }

    @Test
    void testFibonacciNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            Computations.fibonacci(-1);
        });
    }

    @Test
    void testIsPrime() {
        assertTrue(Computations.isPrime(7));
        assertFalse(Computations.isPrime(4));
        assertFalse(Computations.isPrime(1));
    }

    @Test
    void testEvenOdd() {
        assertTrue(Computations.isEven(4));
        assertFalse(Computations.isEven(5));
        assertTrue(Computations.isOdd(5));
        assertFalse(Computations.isOdd(4));
    }

    @Test
    void testTemperatureConversions() {
        assertEquals(0, Computations.toCelsius(32), 0.001);
        assertEquals(32, Computations.toFahrenheit(0), 0.001);
    }
}