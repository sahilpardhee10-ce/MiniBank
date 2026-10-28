
public class Customer {

    private String name;
    private String email;
    private String mobile;

    private final String customerId;

    private static long customerCounter = 100;

    // Private method to generate customer ID
    private static String generateCustomerId() {
        customerCounter++;
        return "CUST" + customerCounter;
    }

    // Constructor
    public Customer(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.customerId = generateCustomerId();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCustomerId() {
        return customerId;
    }
}
