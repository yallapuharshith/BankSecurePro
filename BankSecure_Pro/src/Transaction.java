
/*## **4. Transaction Class**

Handles deposit and withdrawal operations.

```java*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transaction {
    public static void deposit(long accNum, double amount) {
        String updateBalance = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateBalance)) {
            stmt.setDouble(1, amount);
            stmt.setLong(2, accNum);
            stmt.executeUpdate();
            System.out.println("Deposited: $" + amount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void withdraw(long accNum, double amount) {
        String updateBalance = "UPDATE accounts SET balance = balance - ? WHERE account_number = ? AND balance >= ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateBalance)) {
            stmt.setDouble(1, amount);
            stmt.setLong(2, accNum);
            stmt.setDouble(3, amount);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Withdrawn: $" + amount);
            } else {
                System.out.println("Insufficient balance or invalid account.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

