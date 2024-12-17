import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class MainWindow {
    
    // Set the main colors of the application
    private static final Color PRIMARY_COLOR = new Color(0, 120, 255); // Bank blue
    private static final Color SECONDARY_COLOR = new Color(242, 242, 242); // Light background
    private static final Color BUTTON_HOVER_COLOR = new Color(0, 102, 204); // Darker blue on hover
    
    public static void main(String[] args) {
        // Create the frame for the main window
        JFrame frame = new JFrame("BankSecure Pro");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        
        // Add a panel for the header (logo and title)
        JPanel headerPanel = createHeaderPanel();
        frame.add(headerPanel, BorderLayout.NORTH);
        
        // Add the main buttons to a panel
        JPanel buttonPanel = createButtonPanel();
        frame.add(buttonPanel, BorderLayout.CENTER);
        
        // Set the background color of the frame
        frame.getContentPane().setBackground(SECONDARY_COLOR);
        
        // Set default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

    // Method to create the header panel
    private static JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Add a logo (for now, using a label as a placeholder)
        JLabel logoLabel = new JLabel("BankSecure Pro");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logoLabel.setForeground(Color.WHITE);
        
        headerPanel.add(logoLabel);
        return headerPanel;
    }

    // Method to create the button panel with main actions
    private static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 20, 20)); // 5 buttons, with padding
        
        // Create the buttons
        JButton createAccountBtn = createStyledButton("Create Account", "icons/create-account.png");
        JButton viewAccountBtn = createStyledButton("View Account", "icons/view-account.png");
        JButton depositBtn = createStyledButton("Deposit", "icons/deposit.png");
        JButton withdrawBtn = createStyledButton("Withdraw", "icons/withdraw.png");
        JButton exitBtn = createStyledButton("Exit", "icons/exit.png");

        // Add action listeners
        createAccountBtn.addActionListener(e -> {
            try {
                String name = JOptionPane.showInputDialog("Enter Account Holder Name:");
                String accountNumberStr = JOptionPane.showInputDialog("Enter Account Number:");
                
                // Validate input before parsing
                if (accountNumberStr == null || accountNumberStr.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Account number cannot be empty");
                    return; // Exit if account number is empty
                }

                long accountNumber = Long.parseLong(accountNumberStr);  // parse the account number
                String accountHolderName = name; // assuming name is taken from the input dialog
                double balance = 0.0;  // Example: Set a default balance or get input for it
                
                // Create account
                BankAccount.createAccount(accountNumber, accountHolderName, balance);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid account number format. Please enter a valid number.");
            }
        });

        viewAccountBtn.addActionListener(e -> {
            String accountNumber = JOptionPane.showInputDialog("Enter Account Number:");
            String accountDetails = BankAccount.getAccountDetails(Long.parseLong(accountNumber));
            JOptionPane.showMessageDialog(null, accountDetails);
        });

        depositBtn.addActionListener(e -> {
            String accountNumber = JOptionPane.showInputDialog("Enter Account Number:");
            String amountStr = JOptionPane.showInputDialog("Enter Deposit Amount:");
            double amount = Double.parseDouble(amountStr);
            Transaction.deposit(Long.parseLong(accountNumber), amount);
        });

        withdrawBtn.addActionListener(e -> {
            String accountNumber = JOptionPane.showInputDialog("Enter Account Number:");
            String amountStr = JOptionPane.showInputDialog("Enter Withdrawal Amount:");
            double amount = Double.parseDouble(amountStr);
            Transaction.withdraw(Long.parseLong(accountNumber), amount);
        });

        exitBtn.addActionListener(e -> System.exit(0));

        // Add buttons to the panel
        buttonPanel.add(createAccountBtn);
        buttonPanel.add(viewAccountBtn);
        buttonPanel.add(depositBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(exitBtn);

        return buttonPanel;
    }

    // Method to create styled buttons with icons
    private static JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setIcon(new ImageIcon(iconPath)); // Add an icon next to the text

        // Button hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }
}
