
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CRUDProgram extends JFrame implements ActionListener {
    private JTextField tfId, tfNama, tfAlamat;
    private JButton btnTambah, btnUpdate, btnHapus;
    private JTable table;
    private DefaultTableModel tableModel;

    private Connection connection;
    private Statement statement;

    public CRUDProgram() {
        setTitle("Program CRUD Sederhana");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        JLabel lblId = new JLabel("ID:");
        tfId = new JTextField();
        JLabel lblNama = new JLabel("Nama:");
        tfNama = new JTextField();
        JLabel lblAlamat = new JLabel("Alamat:");
        tfAlamat = new JTextField();
        btnTambah = new JButton("Tambah");
        btnTambah.addActionListener(this);
        btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(this);
        btnHapus = new JButton("Hapus");
        btnHapus.addActionListener(this);

        formPanel.add(lblId);
        formPanel.add(tfId);
        formPanel.add(lblNama);
        formPanel.add(tfNama);
        formPanel.add(lblAlamat);
        formPanel.add(tfAlamat);
        formPanel.add(btnTambah);
        formPanel.add(btnUpdate);
        formPanel.add(btnHapus);

        add(formPanel, BorderLayout.NORTH);

        table = new JTable();
        tableModel = new DefaultTableModel();
        table.setModel(tableModel);
        tableModel.addColumn("ID");
        tableModel.addColumn("Nama");
        tableModel.addColumn("Alamat");

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // Pusatkan jendela di tengah layar

        // Koneksi ke database MySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_gui", "root",
                    "");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        // Mengambil data dari database dan menampilkan di tabel
        refreshTableData();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnTambah) {
            tambahData();
        } else if (e.getSource() == btnUpdate) {
            updateData();
        } else if (e.getSource() == btnHapus) {
            hapusData();
        }
    }

    private void refreshTableData() {
        // Menghapus data lama dari tabel
        tableModel.setRowCount(0);

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM tabel_data");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nama = resultSet.getString("nama");
                String alamat = resultSet.getString("alamat");

                tableModel.addRow(new Object[] { id, nama, alamat });
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void tambahData() {
        String nama = tfNama.getText();
        String alamat = tfAlamat.getText();

        try {
            String query = "INSERT INTO tabel_data (nama, alamat) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nama);
            preparedStatement.setString(2, alamat);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            clearForm();
            refreshTableData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateData() {
        int id = Integer.parseInt(tfId.getText());
        String nama = tfNama.getText();
        String alamat = tfAlamat.getText();

        try {
            String query = "UPDATE tabel_data SET nama = ?, alamat = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nama);
            preparedStatement.setString(2, alamat);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            clearForm();
            refreshTableData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void hapusData() {
        int id = Integer.parseInt(tfId.getText());

        try {
            String query = "DELETE FROM tabel_data WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            clearForm();
            refreshTableData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        tfId.setText("");
        tfNama.setText("");
        tfAlamat.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CRUDProgram program = new CRUDProgram();
            program.setVisible(true);
        });
    }
}
