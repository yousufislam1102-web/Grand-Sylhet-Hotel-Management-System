import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.*;
public class Dashboard extends JFrame {
    JTextField txtGuest, txtRoom, txtPrice, txtType, txtStatus;
    JTable table;
    DefaultTableModel model;
    String selectedID = "";
    DBConnection db = new DBConnection();
    public Dashboard() {
        setSize(1200, 900);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Headear
        JLabel lblHeader = new JLabel("GRAND SYLHET HOTEL MANAGEMENT", JLabel.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 40));
        lblHeader.setForeground(new Color(44, 62, 80));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(lblHeader, BorderLayout.NORTH);

        // Input areaa
        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));

        JPanel pnlInput = new JPanel(new GridLayout(5, 2, 15, 25));
        Font labelFont = new Font("Arial", Font.BOLD, 22);
        Font inputFont = new Font("Arial", Font.PLAIN, 22);

        pnlInput.add(createStyledLabel("Guest Name:", labelFont));
        txtGuest = new JTextField(); txtGuest.setFont(inputFont); pnlInput.add(txtGuest);

        pnlInput.add(createStyledLabel("Room No:", labelFont));
        txtRoom = new JTextField(); txtRoom.setFont(inputFont); pnlInput.add(txtRoom);

        pnlInput.add(createStyledLabel("Room Type:", labelFont));
        txtType = new JTextField(); txtType.setFont(inputFont); pnlInput.add(txtType);

        pnlInput.add(createStyledLabel("Status:", labelFont));
        txtStatus = new JTextField(); txtStatus.setFont(inputFont); pnlInput.add(txtStatus);

        pnlInput.add(createStyledLabel("Price ($):", labelFont));
        txtPrice = new JTextField(); txtPrice.setFont(inputFont); pnlInput.add(txtPrice);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 30));
        JButton btnAdd = createStyledButton("Add New", new Color(46, 204, 113));
        JButton btnLoad = createStyledButton("Load to Edit", new Color(52, 152, 219));
        JButton btnUpdate = createStyledButton("Update", new Color(241, 196, 15));
        JButton btnDelete = createStyledButton("Delete", new Color(231, 76, 60));
        JButton btnClear = createStyledButton("Clear", new Color(149, 165, 166));

        pnlButtons.add(btnAdd); pnlButtons.add(btnLoad); pnlButtons.add(btnUpdate);
        pnlButtons.add(btnDelete); pnlButtons.add(btnClear);

        pnlCenter.add(pnlInput);
        pnlCenter.add(pnlButtons);
        add(pnlCenter, BorderLayout.CENTER);

        // TABLE
        model = new DefaultTableModel(new String[]{"ID", "Guest", "Room", "Type", "Status", "Price"}, 0);
        table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 18));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));

        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(0).setMinWidth(0); tcm.getColumn(0).setMaxWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 250));
        add(scrollPane, BorderLayout.SOUTH);

        // Actions
        btnAdd.addActionListener(e -> addRecord());
        btnLoad.addActionListener(e -> loadToEdit());
        btnUpdate.addActionListener(e -> updateRecord());
        btnDelete.addActionListener(e -> deleteRecord());
        btnClear.addActionListener(e -> clearFields());
        loadData();
    }

    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(160, 50));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        return btn;
    }

    private void addRecord() {
        String sql = "INSERT INTO bookings (guest_name, room_number, room_type, status, price) VALUES (?,?,?,?,?)";
        try (PreparedStatement pst = db.con.prepareStatement(sql)) {
            pst.setString(1, txtGuest.getText());
            pst.setString(2, txtRoom.getText());
            pst.setString(3, txtType.getText());
            pst.setString(4, txtStatus.getText());
            pst.setString(5, txtPrice.getText());
            pst.executeUpdate();
            clearFields();
            loadData();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void loadToEdit() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Select a row first!");
            return;
        }
        selectedID = table.getModel().getValueAt(row, 0).toString();
        txtGuest.setText(table.getModel().getValueAt(row, 1).toString());
        txtRoom.setText(table.getModel().getValueAt(row, 2).toString());
        txtType.setText(table.getModel().getValueAt(row, 3).toString());
        txtStatus.setText(table.getModel().getValueAt(row, 4).toString());
        txtPrice.setText(table.getModel().getValueAt(row, 5).toString());
    }

    private void updateRecord() {
        if (selectedID.isEmpty()) return;
        String sql = "UPDATE bookings SET guest_name=?, room_number=?, room_type=?, status=?, price=? WHERE id=?";
        try (PreparedStatement pst = db.con.prepareStatement(sql)) {
            pst.setString(1, txtGuest.getText());
            pst.setString(2, txtRoom.getText());
            pst.setString(3, txtType.getText());
            pst.setString(4, txtStatus.getText());
            pst.setString(5, txtPrice.getText());
            pst.setString(6, selectedID);
            pst.executeUpdate();
            clearFields();
            loadData();
            selectedID = "";
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void deleteRecord() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        String id = table.getModel().getValueAt(row, 0).toString();
        try {
            db.stmt.executeUpdate("DELETE FROM bookings WHERE id=" + id);
            loadData();
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void clearFields() {
        txtGuest.setText("");
        txtRoom.setText("");
        txtType.setText("");
        txtStatus.setText("");
        txtPrice.setText("");
        selectedID = "";
    }

    private void loadData() {
        model.setRowCount(0);
        try (ResultSet rs = db.stmt.executeQuery("SELECT * FROM bookings")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"), rs.getString("guest_name"), rs.getString("room_number"),
                        rs.getString("room_type"), rs.getString("status"), rs.getString("price")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}