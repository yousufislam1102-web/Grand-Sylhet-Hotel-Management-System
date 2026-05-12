import javax.swing.*;
import java.sql.*;
public class Signup extends JFrame {
    JLabel lblUser, lblEmail, lblPhone, lblPass;
    JTextField txtUser, txtEmail, txtPhone;
    JPasswordField txtPass;
    JButton btnSignup;
    DBConnection db = new DBConnection();

    public Signup() {
        setSize(350, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        // Username
        lblUser = new JLabel("Username:");
        lblUser.setBounds(30, 30, 80, 30);
        add(lblUser);
        txtUser = new JTextField();
        txtUser.setBounds(120, 30, 170, 30);
        add(txtUser);
        // Email
        lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 80, 80, 30);
        add(lblEmail);
        txtEmail = new JTextField();
        txtEmail.setBounds(120, 80, 170, 30);
        add(txtEmail);

        // Phone
        lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(30, 130, 80, 30);
        add(lblPhone);
        txtPhone = new JTextField();
        txtPhone.setBounds(120, 130, 170, 30);
        add(txtPhone);

        // Password Label & Box
        lblPass = new JLabel("Password:");
        lblPass.setBounds(30, 180, 80, 30);
        add(lblPass);
        txtPass = new JPasswordField();
        txtPass.setBounds(120, 180, 170, 30);
        add(txtPass);

        // Register Button
        btnSignup = new JButton("Register");
        btnSignup.setBounds(110, 250, 110, 35);
        add(btnSignup);

        btnSignup.addActionListener(e -> {

            if (db.con == null) {
                JOptionPane.showMessageDialog(null, "Database Connection Failed!");
                return;
            }

            String sql = "INSERT INTO users (username, email, phone, password) VALUES (?,?,?,?)";
            try {

                PreparedStatement pst = db.con.prepareStatement(sql);
                pst.setString(1, txtUser.getText());
                pst.setString(2, txtEmail.getText());
                pst.setString(3, txtPhone.getText());
                pst.setString(4, new String(txtPass.getPassword()));

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Registered Successfully!");
                new Login().setVisible(true);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}