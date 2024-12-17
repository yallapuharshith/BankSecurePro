
/*## **3. BankAccount Class**

Handles account creation and account details.

```java*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankAccount {
    public static void createAccount(long accountNumber, String accountHolderName, double balance) {
        String sql = "INSERT INTO accounts (account_number, account_holder, balance) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Set values for the PreparedStatement
            stmt.setLong(1, accountNumber);  // account_number
            stmt.setString(2, accountHolderName); // account_holder_name
            stmt.setDouble(3, balance); // balance
            
            // Execute the insert query
            stmt.executeUpdate();
            
            System.out.println("Account created for: " + accountHolderName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static String getAccountDetails(long accNum) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        StringBuilder details = new StringBuilder();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, accNum);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                details.append("Account Number: ").append(rs.getInt("account_number"))
                       .append("\nAccount Holder: ").append(rs.getString("account_holder"))
                       .append("\nBalance: $").append(rs.getDouble("balance"))
                       .append("\nCreated At: ").append(rs.getTimestamp("created_at"));
            } else {
                details.append("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details.toString();
    }
}