import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SARfrontend extends JFrame {

    private JComboBox<String> designationComboBox;
    private JTextField authorizedIDField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public SARfrontend() {
        setTitle("SAR Image Recognition Login");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);

        JLabel headerLabel = new JLabel("SAR IMAGE RECOGNITION", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        formPanel.setOpaque(false);

        JLabel designationLabel = new JLabel("Designation:");
        formPanel.add(designationLabel);
        String[] designations = {"Admin", "Analyst", "Researcher", "Technician"};
        designationComboBox = new JComboBox<>(designations);
        formPanel.add(designationComboBox);

        JLabel idLabel = new JLabel("Authorized ID:");
        formPanel.add(idLabel);
        authorizedIDField = new JTextField(20);
        formPanel.add(authorizedIDField);

        JLabel passLabel = new JLabel("Password:");
        formPanel.add(passLabel);
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        formPanel.add(loginButton);

        messageLabel = new JLabel("", JLabel.CENTER);
        formPanel.add(messageLabel);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JLabel footerLabel = new JLabel("INDIAN SPACE RESEARCH ORGANIZATION", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        footerLabel.setForeground(Color.BLUE);
        new Thread(new ScrollingText(footerLabel)).start();
        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String designation = (String) designationComboBox.getSelectedItem();
            String authorizedID = authorizedIDField.getText();
            String password = new String(passwordField.getPassword());

            if (authorizedID.equals("admin") && password.equals("password")) {
                messageLabel.setText("Login successful!");
                messageLabel.setForeground(Color.GREEN);
                openImageUploadPage();
            } else {
                messageLabel.setText("Invalid credentials.");
                messageLabel.setForeground(Color.RED);
            }
        }
    }

    private void openImageUploadPage() {
        this.setVisible(false);

        JFrame uploadFrame = new JFrame("Upload Image");
        uploadFrame.setSize(400, 200);
        uploadFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        uploadFrame.setLocationRelativeTo(null);

        JPanel uploadPanel = new JPanel(new BorderLayout());
        JLabel uploadLabel = new JLabel("Upload your image", JLabel.CENTER);
        uploadLabel.setFont(new Font("Arial", Font.BOLD, 18));
        uploadPanel.add(uploadLabel, BorderLayout.NORTH);

        JButton uploadButton = new JButton("Choose Image");
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(null, "Image uploaded successfully!");
            }
        });
        uploadPanel.add(uploadButton, BorderLayout.CENTER);

        uploadFrame.add(uploadPanel);
        uploadFrame.setVisible(true);
    }

    private class ScrollingText implements Runnable {
        private JLabel label;
        private String text;
        private int index;

        public ScrollingText(JLabel label) {
            this.label = label;
            this.text = label.getText();
            this.index = 0;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String scrollingText = text.substring(index) + " " + text.substring(0, index);
                    label.setText(scrollingText);
                    index = (index + 1) % text.length();
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SARfrontend loginPage = new SARfrontend();
            loginPage.setVisible(true);
        });
    }
}
