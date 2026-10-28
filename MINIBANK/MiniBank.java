
import java.util.Scanner;

record BankInfo(String name, String branch) {}

enum MenuOption {
    OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, EXIT
}

public class MiniBank {

    public static void main(String[] args) {
        BankInfo bankInfo = new BankInfo(
            "MiniBank National", "Main Branch, Downtown"
        );

        System.out.println("==================================================");
        System.out.println(" Welcome to " + bankInfo.name());
        System.out.println(" Branch: " + bankInfo.branch());
        System.out.println(" Bank Details: " + bankInfo);
        System.out.println("==================================================");

        Scanner scanner = new Scanner(System.in);
        Customer[] customers = new Customer[100];
        Account[] accounts = new Account[100];
        int accountCount = 0;

        MenuOption[] options = MenuOption.values();
        boolean running = true;

        while (running) {
            System.out.println("\n--- MAIN MENU ---");

            for (int i = 0; i < options.length; i++) {
                System.out.printf("%d. %s%n",
                    i + 1, formatOptionName(options[i]));
            }

            System.out.print("Enter your choice (1-5): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Enter a number.");
                scanner.nextLine();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice < 1 || choice > options.length) {
                System.out.println("Invalid option. Choose 1-5.");
                continue;
            }

            MenuOption selectedOption = options[choice - 1];

            switch (selectedOption) {
                case OPEN_ACCOUNT -> {
                    if (accountCount >= accounts.length) {
                        System.out.println("Account limit reached.");
                        break;
                    }

                    String name = readName(scanner);
                    String email = readEmail(scanner);
                    String mobile = readMobile(scanner);
                    long openingBalance = readAmount(
                        scanner, "Enter opening balance (Rs.): ", true
                    );

                    Customer customer =
                        new Customer(name, email, mobile);
                    Account account =
                        new Account(name, openingBalance);

                    customers[accountCount] = customer;
                    accounts[accountCount] = account;
                    accountCount++;

                    System.out.println("\nAccount created successfully!");
                    System.out.println(
                        "Customer ID: " + customer.getCustomerId());
                    System.out.println(
                        "Account Number: " + account.getAccountNumber());
                    System.out.println(
                        "Account Holder: " + account.getOwnerName());
                    System.out.println(
                        "Balance: Rs. " + account.getBalance());
                }

                case DEPOSIT -> {
                    Account account =
                        findAccount(scanner, accounts, accountCount);

                    if (account == null) break;

                    long amount = readAmount(
                        scanner, "Enter deposit amount (Rs.): ", false
                    );

                    account.deposit(amount);
                    System.out.println("Deposit successful.");
                    System.out.println(
                        "New balance: Rs. " + account.getBalance());
                }

                case WITHDRAW -> {
                    Account account =
                        findAccount(scanner, accounts, accountCount);

                    if (account == null) break;

                    long amount = readAmount(
                        scanner, "Enter withdrawal amount (Rs.): ", false
                    );

                    if (account.withdraw(amount)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Insufficient balance.");
                    }

                    System.out.println(
                        "Current balance: Rs. " + account.getBalance());
                }

                case TRANSFER -> {
                    System.out.println("Select the source account:");
                    Account source =
                        findAccount(scanner, accounts, accountCount);
                    if (source == null) break;

                    System.out.println("Select the destination account:");
                    Account destination =
                        findAccount(scanner, accounts, accountCount);
                    if (destination == null) break;

                    if (source == destination) {
                        System.out.println(
                            "Choose two different accounts.");
                        break;
                    }

                    long amount = readAmount(
                        scanner, "Enter transfer amount (Rs.): ", false
                    );

                    if (source.withdraw(amount)) {
                        destination.deposit(amount);
                        System.out.println("Transfer successful.");
                        System.out.println(
                            "Source balance: Rs. " + source.getBalance());
                        System.out.println(
                            "Destination balance: Rs. "
                                + destination.getBalance());
                    } else {
                        System.out.println(
                            "Transfer failed: insufficient balance.");
                    }
                }

                case EXIT -> {
                    System.out.println(
                        "Thank you for banking with MiniBank. Goodbye!");
                    running = false;
                }
            }
        }

        scanner.close();
    }

    // Name must start with a capital letter.
    private static String readName(Scanner scanner) {
        while (true) {
            System.out.print("Enter customer name: ");
            String name = scanner.nextLine().trim();

            if (name.matches("[A-Z][a-zA-Z ]*")
                    && name.matches(".*[a-zA-Z].*")) {
                return name;
            }

            System.out.println(
                "Invalid name! Start with a capital letter, e.g. Sahil.");
        }
    }

    // Email must end in @gmail.com.
    private static String readEmail(Scanner scanner) {
        while (true) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();

            if (email.matches("[A-Za-z0-9._%+-]+@gmail\\.com")) {
                return email;
            }

            System.out.println(
                "Invalid email! Enter an address like sahil@gmail.com.");
        }
    }

    // Mobile number must contain exactly 10 digits.
    private static String readMobile(Scanner scanner) {
        while (true) {
            System.out.print("Enter mobile number: ");
            String mobile = scanner.nextLine().trim();

            if (mobile.matches("[0-9]{10}")) {
                return mobile;
            }

            System.out.println(
                "Invalid mobile number! Enter exactly 10 digits.");
        }
    }

    // Amount must be a whole number and cannot be negative.
    // If allowZero is false, the amount must be greater than zero.
    private static long readAmount(
            Scanner scanner, String prompt, boolean allowZero) {

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                long amount = Long.parseLong(input);

                if (amount < 0 || (!allowZero && amount == 0)) {
                    System.out.println(allowZero
                        ? "Invalid amount! Cannot be negative."
                        : "Invalid amount! Enter an amount greater than zero.");
                    continue;
                }

                return amount;
            } catch (NumberFormatException e) {
                System.out.println(
                    "Invalid amount! Enter numbers only, e.g. 5000.");
            }
        }
    }

    private static Account findAccount(
            Scanner scanner, Account[] accounts, int count) {

        if (count == 0) {
            System.out.println(
                "No accounts exist. Open an account first.");
            return null;
        }

        System.out.println("\nAvailable accounts:");

        for (int i = 0; i < count; i++) {
            System.out.printf(
                "%d. %s | %s | Balance: Rs. %d%n",
                i + 1,
                accounts[i].getAccountNumber(),
                accounts[i].getOwnerName(),
                accounts[i].getBalance()
            );
        }

        System.out.print("Select account number from this list: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Invalid selection. Enter a number.");
            scanner.nextLine();
            return null;
        }

        int selection = scanner.nextInt();
        scanner.nextLine();

        if (selection < 1 || selection > count) {
            System.out.println("Account selection out of range.");
            return null;
        }

        return accounts[selection - 1];
    }

    private static String formatOptionName(MenuOption option) {
        return switch (option) {
            case OPEN_ACCOUNT -> "Open Account";
            case DEPOSIT -> "Deposit";
            case WITHDRAW -> "Withdraw";
            case TRANSFER -> "Transfer";
            case EXIT -> "Exit";
        };
    }
}
