
public class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static int accountCounter = 0;

    private static String generateAccountNumber() {
        accountCounter++;
        return String.format("AC%04d", accountCounter);
    }

    // Constructor with opening balance
    public Account(String ownerName, long openingBalance) {
        if (openingBalance < 0) {
            throw new IllegalArgumentException(
                "Opening balance cannot be negative."
            );
        }

        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    // Constructor without opening balance
    public Account(String ownerName) {
        this(ownerName, 0);
    }

    // Deposit money
    public void deposit(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                "Deposit amount cannot be negative."
            );
        }

        balance += amount;
    }

    // Withdraw money
    public boolean withdraw(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                "Withdrawal amount cannot be negative."
            );
        }

        if (amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }

    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }
}
