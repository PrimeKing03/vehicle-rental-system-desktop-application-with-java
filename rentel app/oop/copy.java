package oop;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class copy extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField vregno;
    private JTextField vbrand;
    private JTextField vmodel;
    private JTextField vtype;
    private JTable table;
    DefaultTableModel modell;
    private JTextField vsearch;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    copy frame = new copy();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public copy() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1023, 593);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(204, 153, 204));
        contentPane.setForeground(new Color(204, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        vregno = new JTextField();
        vregno.setFont(new Font("Tahoma", Font.PLAIN, 15));
        vregno.setBounds(138, 86, 265, 34);
        contentPane.add(vregno);
        vregno.setColumns(10);

        vbrand = new JTextField();
        vbrand.setFont(new Font("Tahoma", Font.PLAIN, 15));
        vbrand.setColumns(10);
        vbrand.setBounds(138, 142, 265, 34);
        contentPane.add(vbrand);

        vmodel = new JTextField();
        vmodel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        vmodel.setColumns(10);
        vmodel.setBounds(138, 202, 265, 34);
        contentPane.add(vmodel);

        vtype = new JTextField();
        vtype.setFont(new Font("Tahoma", Font.PLAIN, 15));
        vtype.setColumns(10);
        vtype.setBounds(138, 259, 265, 34);
        contentPane.add(vtype);

        JButton btnNewButton = new JButton("ADD");
        btnNewButton.setBackground(new Color(255, 204, 255));
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String regno = vregno.getText();
                String brand = vbrand.getText();
                String model = vmodel.getText();
                String type = vtype.getText();

                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "user", "");

                    String sql = "INSERT INTO vehicle1 (regno, brand, model, type) VALUES (?, ?, ?, ?)";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, regno);
                    pst.setString(2, brand);
                    pst.setString(3, model);
                    pst.setString(4, type);

                    int rowsAffected = pst.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Vehicle added successfully.");
                    } else {
                        System.out.println("Failed to add the vehicle.");
                    }

                    vregno.setText("");
                    vbrand.setText("");
                    vmodel.setText("");
                    vtype.setText("");

                    pst.close();
                    con.close();

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                // Add data to the table
                Object[] row = new Object[4];
                row[0] = regno;
                row[1] = brand;
                row[2] = model;
                row[3] = type;
                modell.addRow(new Object[]{regno, brand, model, type});
               
            }
        });

        btnNewButton.setBounds(37, 363, 140, 56);
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("DELETE");
        btnNewButton_1.setBackground(new Color(255, 204, 255));
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();

                if (selectedRow >= 0) {
                    // Get the regno from the selected row (assuming it's in column 0)
                    Object regnoToDelete = modell.getValueAt(selectedRow, 0);

                    // Remove the selected row from the table
                    modell.removeRow(selectedRow);

                    try {
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "user", "");
                        String sql = "DELETE FROM vehicle1 WHERE regno=?";
                        PreparedStatement pst = con.prepareStatement(sql);
                        pst.setString(1, regnoToDelete.toString());
                        int rowsAffected = pst.executeUpdate();
                        pst.close();
                        con.close();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Vehicle deleted successfully from the database.");
                        } else {
                            System.out.println("Failed to delete the vehicle from the database.");
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    System.out.println("No row selected in the table.");
                }
            }
        });

        btnNewButton_1.setBounds(187, 363, 136, 56);
        contentPane.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("UPDATE");
        btnNewButton_2.setBackground(new Color(255, 204, 255));
        btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int i = table.getSelectedRow();
                modell.setValueAt(vregno.getText(), i, 0);
                modell.setValueAt(vbrand.getText(), i, 1);
                modell.setValueAt(vmodel.getText(), i, 2);
                modell.setValueAt(vtype.getText(), i, 3);

                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "user", "");
                    String sql = "UPDATE vehicle1 SET regno=?, brand=?, model=?, type=? WHERE regno=?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, vregno.getText());
                    pst.setString(2, vbrand.getText());
                    pst.setString(3, vmodel.getText());
                    pst.setString(4, vtype.getText());
                    // Assuming the Vehicle regno is used to identify the record

                    int rowsUpdated = pst.executeUpdate();
                    if (rowsUpdated > 0) {
                        // Record updated successfully
                        modell.setValueAt(vregno.getText(), i, 0);
                        modell.setValueAt(vbrand.getText(), i, 1);
                        modell.setValueAt(vmodel.getText(), i, 2);
                        modell.setValueAt(vtype.getText(), i, 3);

                        JOptionPane.showMessageDialog(null, "Record updated successfully.");

                    } else {
                        // Record not updated
                        JOptionPane.showMessageDialog(null, "Record not updated. Check the Reg No.");
                    }
                    vregno.setText("");
                    vbrand.setText("");
                    vmodel.setText("");
                    vtype.setText("");

                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error while updating the record.");
                }
            }
        });

        btnNewButton_2.setBounds(37, 432, 136, 56);
        contentPane.add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("BACK");
        btnNewButton_3.setBackground(new Color(255, 204, 255));
        btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                page1 frame = new page1();
                frame.setVisible(true);
            }
        });

        btnNewButton_3.setBounds(187, 432, 136, 56);
        contentPane.add(btnNewButton_3);

        JScrollPane vtable = new JScrollPane();
        vtable.setBounds(452, 25, 526, 357);
        contentPane.add(vtable);

        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();
                vregno.setText(modell.getValueAt(i, 0).toString());
                vbrand.setText(modell.getValueAt(i, 1).toString());
                vmodel.setText(modell.getValueAt(i, 2).toString());
                vtype.setText(modell.getValueAt(i, 3).toString());
            }
        });

        modell = new DefaultTableModel();
        Object[] column = {"REGNO", "BRAND", "MODEL", "TYPE"};
        modell.setColumnIdentifiers(column);
        table.setModel(modell);

        vtable.setViewportView(table);

        // Fetch data from the database and populate the table
        fetchDataFromDatabase();

        setLocationRelativeTo(null);

        JLabel lblNewLabel = new JLabel("REG NO :");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setForeground(new Color(0, 0, 0));
        lblNewLabel.setBounds(46, 86, 192, 34);
        contentPane.add(lblNewLabel);

        JLabel lblCustomerAddress = new JLabel("BRAND :");
        lblCustomerAddress.setForeground(Color.BLACK);
        lblCustomerAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblCustomerAddress.setBounds(46, 142, 192, 34);
        contentPane.add(lblCustomerAddress);

        JLabel lblNewLabel_1_1 = new JLabel("MODEL :");
        lblNewLabel_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_1_1.setBounds(46, 199, 192, 34);
        contentPane.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("TYPE :");
        lblNewLabel_1_2.setForeground(Color.BLACK);
        lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_1_2.setBounds(46, 259, 192, 34);
        contentPane.add(lblNewLabel_1_2);

        JLabel lblNewLabel_1 = new JLabel("VEHICLES");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 45));
        lblNewLabel_1.setBounds(29, 11, 306, 49);
        contentPane.add(lblNewLabel_1);

        JButton btnNewButton_4 = new JButton("SEARCH");
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String search = vsearch.getText();

                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "user", "");
                    String sql = "SELECT * FROM vehicle1 WHERE regno = ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, search);
                    ResultSet rs = pst.executeQuery();
                    boolean record = rs.next();
                    if (!record) {
                        JOptionPane.showMessageDialog(null, "Vehicle not found!");
                        con.close();
                        return;
                    }
                    String regno = rs.getString("regno");
                    String brand = rs.getString("brand");
                    String model = rs.getString("model");
                    String type = rs.getString("type");

                    vregno.setText(regno);
                    vbrand.setText(brand);
                    vmodel.setText(model);
                    vtype.setText(type);

                    con.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 25));
        btnNewButton_4.setBounds(437, 440, 166, 49);
        contentPane.add(btnNewButton_4);

        vsearch = new JTextField();
        vsearch.setFont(new Font("Tahoma", Font.PLAIN, 18));
        vsearch.setBounds(680, 440, 192, 49);
        contentPane.add(vsearch);
        vsearch.setColumns(10);
    }

    private void fetchDataFromDatabase() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "user", "");
            String sql = "SELECT * FROM vehicle1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String regno = rs.getString("regno");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                String type = rs.getString("type");
                modell.addRow(new Object[]{regno, brand, model, type});
            }

            rs.close();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
