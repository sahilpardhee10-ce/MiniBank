
public class TestAccounts {
    public static void main(String[] args) {

        // Test Customer class
        Customer c1 = new Customer(
            "Sahil", "sahil@example.com", "9876543210"
        );
        Customer c2 = new Customer(
            "Rahul", "rahul@example.com", "9876543211"
        );

        System.out.println("Customer 1 ID: " + c1.getCustomerId());
        System.out.println("Customer 1 Name: " + c1.getName());
        System.out.println("Customer 2 ID: " + c2.getCustomerId());

        // Test Account class
        Account acc = new Account("Sahil", 5000);

        System.out.println("Account Number: " + acc.getAccountNumber());
        System.out.println("Opening Balance: Rs. " + acc.getBalance());

        acc.deposit(2000);
        System.out.println("After Deposit: Rs. " + acc.getBalance());

        System.out.println("Withdrawal Successful: " + acc.withdraw(3000));
        System.out.println("Final Balance: Rs. " + acc.getBalance());
    }
}
