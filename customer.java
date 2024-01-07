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

public class customer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField cname;
	private JTextField cadd;
	private JTextField cnic;
	private JTextField cphone;
	private JTable table;
DefaultTableModel model;
private JTextField csearch;
private JTextField cid;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					customer frame = new customer();//fram la name change pannanum pola
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public customer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 947, 472);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(127, 255, 212));
		contentPane.setForeground(new Color(255, 204, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cname = new JTextField();
		cname.setBounds(46, 137, 265, 22);
		contentPane.add(cname);
		cname.setColumns(10);
		
		cadd = new JTextField();
		cadd.setColumns(10);
		cadd.setBounds(46, 192, 265, 22);
		contentPane.add(cadd);
		
		cnic = new JTextField();
		cnic.setColumns(10);
		cnic.setBounds(46, 238, 265, 22);
		contentPane.add(cnic);
		
		cphone = new JTextField();
		cphone.setColumns(10);
		cphone.setBounds(46, 288, 265, 22);
		contentPane.add(cphone);
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setBackground(new Color(255, 204, 255));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Collect data from the text fields
		    	 String ID = cid.getText();
		         String NAME = cname.getText();
		         String ADDRESS = cadd.getText();
		         String NIC = cnic.getText();
		         String PHONE = cphone.getText();
		         
		         try {
		             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "user", "");
		             
		             String sql = "INSERT INTO `customer` (ID, NAME, ADDRESS, NIC, PHONE) VALUES (?, ?, ?, ?, ?)";
		             PreparedStatement pst = con.prepareStatement(sql);
		             pst.setString(1, ID);
		             pst.setString(2, NAME);
		             pst.setString(3, ADDRESS);
		             pst.setString(4, NIC);
		             pst.setString(5, PHONE);
		             
		             int rowsAffected = pst.executeUpdate();
		             
		             if (rowsAffected > 0) {
		                 System.out.println("User registered successfully");
		                 int choice = JOptionPane.showConfirmDialog(null, "Do you want to register this?", "Confirmation", JOptionPane.YES_NO_OPTION);
		                 JOptionPane.showMessageDialog(null, "Registration successful.");
		             } else {
		                 System.out.println("Failed to register user");
		             }
		             
		             // Clear input fields
		             cid.setText("");
		             cname.setText("");
		             cadd.setText("");
		             cnic.setText("");
		             cphone.setText("");
		             
		             // Close PreparedStatement and Connection
		             pst.close();
		             con.close();
		             
		         } catch (SQLException e1) {
		             e1.printStackTrace();
		         }
		         
		         // Add data to the table
		         Object[] row = new Object[5];
		         row[0] = ID;
		         row[1] = NAME;
		         row[2] = ADDRESS;
		         row[3] = NIC;
		         row[4] = PHONE;
		         model.addRow(row);

		         System.out.println("Connected to the database");
		     }
		});

		btnNewButton.setBounds(46, 321, 121, 40);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("DELETE");
		btnNewButton_1.setBackground(new Color(255, 204, 255));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = table.getSelectedRow();

		        if (selectedRow >= 0) {
		            // Get the ID from the selected row (assuming it's in column 0)
		            Object idToDelete = model.getValueAt(selectedRow, 0);

		            // Remove the selected row from the table
		            model.removeRow(selectedRow);

		            try {
		                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "user", "");
		                String sql = "DELETE FROM `customer` WHERE ID=?";
		                PreparedStatement pst = con.prepareStatement(sql);
		                pst.setString(1, idToDelete.toString()); // Assuming idToDelete is a string
		                int rowsAffected = pst.executeUpdate();
		                pst.close();
		                con.close();
		                if (rowsAffected > 0) {
		                    System.out.println("Record deleted successfully from the database.");
		                } else {
		                    System.out.println("Failed to delete record from the database.");
		                }
		            } catch (SQLException e1) {
		                e1.printStackTrace();
		            }
		        } else {
		            // Handle the case when no row is selected in the table.
		            System.out.println("No row selected in the table.");
		        }
		    }
		});

		btnNewButton_1.setBounds(184, 321, 127, 40);
		contentPane.add(btnNewButton_1);

		
		JButton btnNewButton_2 = new JButton("UPDATE");
		btnNewButton_2.setBackground(new Color(255, 204, 255));
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=table.getSelectedRow();
				model.setValueAt(cid.getText(), i, 0);
				model.setValueAt(cname.getText(), i, 1);
				model.setValueAt(cadd.getText(), i, 2);
				model.setValueAt(cnic.getText(), i, 3);
				model.setValueAt(cphone.getText(), i, 4);

				try {
				    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "user", "");
				    String sql = "UPDATE customer SET ID=?,NAME=?, ADDRESS=?, NIC=?, PHONE=? WHERE ID=?";
				    PreparedStatement pst = con.prepareStatement(sql);
				    
				    pst.setString(1, cid.getText());
				    pst.setString(2, cname.getText());
				    pst.setString(3, cadd.getText());
				    pst.setString(4, cnic.getText());
				    pst.setString(5, cphone.getText());
				    pst.setString(6, cid.getText()); // Assuming the customer's name is used to identify the record//ithu p key maari 
				    
				    int rowsUpdated = pst.executeUpdate();

				    if (rowsUpdated > 0) {
				        // Record updated successfully
				    	 model.setValueAt(cid.getText(), i, 0);
				        model.setValueAt(cname.getText(), i, 1);
				        model.setValueAt(cadd.getText(), i, 2);
				        model.setValueAt(cnic.getText(), i, 3);
				        model.setValueAt(cphone.getText(), i, 4);
				        JOptionPane.showMessageDialog(null, "Record updated successfully.");
				    } else {
				        // Record not updated
				        JOptionPane.showMessageDialog(null, "Record not updated. Check the customer name.");
				    }

				    con.close();
				} catch (SQLException ex) {
				    ex.printStackTrace();
				    JOptionPane.showMessageDialog(null, "Error while updating the record.");
				}

		}});
		btnNewButton_2.setBounds(46, 372, 127, 40);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("BACK");
		btnNewButton_3.setBackground(new Color(255, 204, 255));
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 dispose();
			        page1 frame = new page1(); // Create an instance of the registration frame
			        frame.setVisible(true); 
			}
		});
		btnNewButton_3.setBounds(184, 372, 127, 40);
		contentPane.add(btnNewButton_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(359, 11, 526, 357);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int i=table.getSelectedRow();
				cid.setText(model.getValueAt(i,0).toString());
				cname.setText(model.getValueAt(i,1).toString());
				cadd.setText(model.getValueAt(i,2).toString());
				cnic.setText(model.getValueAt(i,3).toString());
				cphone.setText(model.getValueAt(i,4).toString());
				


			}
		});
		model=new DefaultTableModel();
		Object[] column= {"ID","NAME","ADDRESS","NIC","PHONE"};
		final Object[] row=new Object[5];
		model.setColumnIdentifiers(column);
		table.setModel(model);
		
		scrollPane.setViewportView(table);
		scrollPane.setViewportView(table);

        // Fetch data from the database and populate the table
        fetchDataFromDatabase();

        // Pack and display the JFrame
     
        setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("CUSTOMER NAME:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBounds(46, 113, 192, 34);
		contentPane.add(lblNewLabel);
		
		JLabel lblCustomerAddress = new JLabel("CUSTOMER ADDRESS:");
		lblCustomerAddress.setForeground(Color.BLACK);
		lblCustomerAddress.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCustomerAddress.setBounds(46, 168, 192, 34);
		contentPane.add(lblCustomerAddress);
		
		JLabel lblNewLabel_1_1 = new JLabel("CUSTOMER NIC:");
		lblNewLabel_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(46, 213, 192, 34);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("CUSTOMER PHONE:");
		lblNewLabel_1_2.setForeground(Color.BLACK);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_2.setBounds(46, 258, 192, 34);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1 = new JLabel("CUSTOMER");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel_1.setBounds(29, 11, 306, 49);
		contentPane.add(lblNewLabel_1);
		
		cid = new JTextField();
		cid.setColumns(10);
		cid.setBounds(46, 95, 265, 22);
		contentPane.add(cid);
		
		JLabel lblCustomerId = new JLabel("CUSTOMER ID:");
		lblCustomerId.setForeground(Color.BLACK);
		lblCustomerId.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCustomerId.setBounds(46, 71, 192, 34);
		contentPane.add(lblCustomerId);
		
		JButton btnNewButton_4 = new JButton("SEARCH");
		
	
	    ////my code
	
    btnNewButton_4.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String search = csearch.getText();

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "user", "");
                String sql = "SELECT * FROM customer WHERE ID = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, search);
                ResultSet rs = pst.executeQuery();
                boolean record = rs.next();
                if (!record) {
                    JOptionPane.showMessageDialog(null, "Customer not found!");
                    con.close();
                    return;
                }
                String id = rs.getString("ID");
                String name = rs.getString("NAME");
                String address  = rs.getString("ADDRESS");
                String nic = rs.getString("NIC");
                String phone = rs.getString("PHONE");
                
                cid.setText(id);
                cname.setText(name);
                cadd.setText(address);
                cnic.setText(nic);
                cphone.setText(phone);

                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });
    btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 15));
    btnNewButton_4.setBounds(433, 372, 163, 40);
    contentPane.add(btnNewButton_4);

    csearch = new JTextField();
    csearch.setFont(new Font("Tahoma", Font.PLAIN, 18));
    csearch.setBounds(606, 377, 178, 35);
    contentPane.add(csearch);
    csearch.setColumns(10);
}

	
	
	//
	
	@SuppressWarnings("unused")
	private void fetchDataFromDatabase() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "user", "");
            String sql = "SELECT * FROM customer";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
            	 String id = rs.getString("ID");
                String name = rs.getString("NAME");
                String address = rs.getString("ADDRESS");
                String nic = rs.getString("NIC");
                String phone = rs.getString("PHONE");
                model.addRow(new Object[]{id,name, address, nic, phone});
            }

            rs.close();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
}	
}
 