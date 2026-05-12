import javax.swing.*;
import java.sql.*;
public class Login extends JFrame {
    JLabel lblUser, lblPass;
    JTextField txtUser;
    JPasswordField txtPass;
    JButton btnLogin, btnGoSignup;


    DBConnection db = new DBConnection();

    public Login() {

        setSize(350, 250);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Labels
        lblUser = new JLabel("Username:");
        lblUser.setBounds(30, 30, 80, 30);
        add(lblUser);

        lblPass = new JLabel("Password:");
        lblPass.setBounds(30, 70, 80, 30);
        add(lblPass);

        // Input Boxes
        txtUser = new JTextField();
        txtUser.setBounds(120, 30, 150, 30);
        add(txtUser);

        txtPass = new JPasswordField();
        txtPass.setBounds(120, 70, 150, 30);
        add(txtPass);

        // Buttons
        btnLogin = new JButton("Login");
        btnLogin.setBounds(60, 140, 90, 30);
        add(btnLogin);

        btnGoSignup = new JButton("Signup");
        btnGoSignup.setBounds(170, 140, 90, 30);
        add(btnGoSignup);

        // Action Listeners
        btnLogin.addActionListener(e -> {

            if (db.con == null) {
                JOptionPane.showMessageDialog(null, "Database Connection Failed!");
                return;
            }

            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            try {

                PreparedStatement pst = db.con.prepareStatement(sql);
                pst.setString(1, txtUser.getText());
                pst.setString(2, new String(txtPass.getPassword()));

                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    new Dashboard().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Credentials");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnGoSignup.addActionListener(e -> {
            new Signup().setVisible(true);
            dispose();
        });
    }
}