
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

public class rentel1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField RVNUM;
	private JTextField RCNUM;
	private JTextField rdate;
	private JTextField RCHARGE;
	private JTable table;
DefaultTableModel model;
private JTextField RADDVANCE;
private JTextField RBALANCE;
private JTextField RSEARCH;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					rentel1 frame = new rentel1();
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
	public rentel1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1085, 566);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(127, 255, 212));
		contentPane.setForeground(new Color(255, 204, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		RVNUM = new JTextField();
		RVNUM.setBounds(46, 81, 265, 22);
		contentPane.add(RVNUM);
		RVNUM.setColumns(10);
		
		RCNUM = new JTextField();
		RCNUM.setColumns(10);
		RCNUM.setBounds(46, 128, 265, 22);
		contentPane.add(RCNUM);
		
		rdate = new JTextField();
		rdate.setColumns(10);
		rdate.setBounds(46, 171, 265, 22);
		contentPane.add(rdate);
		
		RCHARGE = new JTextField();
		RCHARGE.setColumns(10);
		RCHARGE.setBounds(46, 214, 265, 22);
		contentPane.add(RCHARGE);
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setBackground(new Color(255, 204, 255));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Collect data from the text fields
		        String VEHICLENO = RVNUM.getText();//name=sql ku namma use panra name ok,cname enrathu text feild da name
		        //getText,setText ithu ellam feild name ku thaan varum.
		        String CUSTOMERID = RCNUM.getText();
		        String ISSUEDATE = rdate.getText();
		        String RENTELCHARGE = RCHARGE.getText();
		        String RENTELADVANCE = RADDVANCE.getText();
		        String BALANCE = RBALANCE.getText();
		        
		        try {
		            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "user", "");
		            
		            String sql = "INSERT INTO `rentel1` (VEHICLENO, CUSTOMERID, ISSUEDATE, RENTELCHARGE,RENTELADVANCE,BALANCE) VALUES (?, ?, ?, ?,?,?)";
		            PreparedStatement pst = con.prepareStatement(sql);
		            
		            pst.setString(1, VEHICLENO);
		            pst.setString(2, CUSTOMERID);
		            pst.setString(3, ISSUEDATE);
		            pst.setString(4, RENTELCHARGE);
		            pst.setString(5, RENTELADVANCE);
		            pst.setString(6, BALANCE);
		            
		            int rowsAffected = pst.executeUpdate();
		            
		            if (rowsAffected > 0) {
		                System.out.println("User registered successfully");
		                int choice = JOptionPane.showConfirmDialog(null, "Do you want to register this?", "Confirmation", JOptionPane.YES_NO_OPTION);
		                JOptionPane.showMessageDialog(null, "Registration successful.");
		            } else {
		                System.out.println("Failed to register user");
		            }
		            
		            // Clear input fields
		            RVNUM.setText("");
		            RCNUM.setText("");
		            rdate.setText("");
		            RCHARGE.setText("");
		            RADDVANCE.setText("");
		            RBALANCE.setText("");
		            
		            // Close PreparedStatement and Connection
		            pst.close();
		            con.close();
		            
		        } catch (SQLException e1) {
		            e1.printStackTrace();
		        }
		        
		        // Add data to the table
		        Object[] row = new Object[6];
		        row[0] = VEHICLENO;
		        row[1] = CUSTOMERID;
		        row[2] = ISSUEDATE;
		        row[3] = RENTELCHARGE;
		        row[4] = RENTELADVANCE;
		        row[5] = BALANCE;
		        
		        model.addRow(row);

		        System.out.println("Connected to the database");
		    }
		});

		btnNewButton.setBounds(46, 331, 121, 40);
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
		                String sql = "DELETE FROM `rentel1` WHERE VEHICLENO=?";
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

		btnNewButton_1.setBounds(184, 331, 127, 40);
		contentPane.add(btnNewButton_1);

		
		JButton btnNewButton_2 = new JButton("UPDATE");
		btnNewButton_2.setBackground(new Color(255, 204, 255));
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  int selectedRow = table.getSelectedRow();
			        
			        if (selectedRow >= 0) {
			            String updatedVEHICLENO = RVNUM.getText();
			            String CUSTOMERID = RCNUM.getText();
			            String ISSUEDATE = rdate.getText();
			            String RENTELCHARGE = RCHARGE.getText();
			            String RENTELADVANCE = RADDVANCE.getText();
			            String BALANCE = RBALANCE.getText();
			            
			            Object idToUpdate = model.getValueAt(selectedRow, 0); // Assuming 'VEHICLENO' is in the first column
			            
			            try {
			                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "user", "");
			                String sql = "UPDATE rentel1 SET VEHICLENO=?, CUSTOMERID=?, ISSUEDATE=?, RENTELCHARGE=?, RENTELADVANCE=?, BALANCE=? WHERE VEHICLENO=?";
			                PreparedStatement pst = con.prepareStatement(sql);
			                
			                pst.setString(1, updatedVEHICLENO);
			                pst.setString(2, CUSTOMERID);
			                pst.setString(3, ISSUEDATE);
			                pst.setString(4, RENTELCHARGE);
			                pst.setString(5, RENTELADVANCE);
			                pst.setString(6, BALANCE);
			                pst.setString(7, idToUpdate.toString());
			                
			                int rowsUpdated = pst.executeUpdate();
			                
			                if (rowsUpdated > 0) {
			                    // Record updated successfully
			                    model.setValueAt(updatedVEHICLENO, selectedRow, 0);
			                    model.setValueAt(CUSTOMERID, selectedRow, 1);
			                    model.setValueAt(ISSUEDATE, selectedRow, 2);
			                    model.setValueAt(RENTELCHARGE, selectedRow, 3);
			                    model.setValueAt(RENTELADVANCE, selectedRow, 4);
			                    model.setValueAt(BALANCE, selectedRow, 5);
			                    
			                    JOptionPane.showMessageDialog(null, "Record updated successfully.");
			                } else {
			                    // Record not updated
			                    JOptionPane.showMessageDialog(null, "Record not updated. Check the vehicle number.");
			                }
			                
			                con.close();
			            } catch (SQLException ex) {
			                ex.printStackTrace();
			                JOptionPane.showMessageDialog(null, "Error while updating the record.");
			            }
			        } else {
			            // Handle the case when no row is selected in the table.
			            JOptionPane.showMessageDialog(null, "No row selected in the table.");
			        }
			    }
		});
		btnNewButton_2.setBounds(46, 382, 127, 40);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("BACK");
		btnNewButton_3.setBackground(new Color(255, 204, 255));
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 dispose();
			        page1 frame = new page1(); // Create an instance of the registration frame
			        frame.setVisible(true); 
			
		}});
		btnNewButton_3.setBounds(184, 382, 127, 40);
		contentPane.add(btnNewButton_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(403, 37, 599, 359);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int i=table.getSelectedRow();
				RVNUM.setText(model.getValueAt(i,0).toString());
				RCNUM.setText(model.getValueAt(i,1).toString());
				rdate.setText(model.getValueAt(i,2).toString());
				RCHARGE.setText(model.getValueAt(i,3).toString());
				RADDVANCE.setText(model.getValueAt(i,4).toString());
				RBALANCE.setText(model.getValueAt(i,5).toString());
			}
		});
		model=new DefaultTableModel();
		Object[] column= {"V REGNO", "CUSTOMERID", "ISSUEDATE", "RENTELCHARGE","RENTELADVANCE","BALANCE"};
		final Object[] row=new Object[4];
		model.setColumnIdentifiers(column);
		table.setModel(model);
		
		scrollPane.setViewportView(table);
		scrollPane.setViewportView(table);

        // Fetch data from the database and populate the table
        fetchDataFromDatabase();

        // Pack and display the JFrame
     
        setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("VEHICLE REGNO:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBounds(46, 52, 192, 34);
		contentPane.add(lblNewLabel);
		
		JLabel lblCustomerAddress = new JLabel("CUSTOMER ID:");
		lblCustomerAddress.setForeground(Color.BLACK);
		lblCustomerAddress.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCustomerAddress.setBounds(46, 97, 192, 34);
		contentPane.add(lblCustomerAddress);
		
		JLabel lblNewLabel_1_1 = new JLabel("ISSUE DATE:");
		lblNewLabel_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(46, 142, 192, 34);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("RENTAL CHARGE:");
		lblNewLabel_1_2.setForeground(Color.BLACK);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_2.setBounds(46, 187, 192, 34);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1 = new JLabel("RENTAL");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel_1.setBounds(29, 11, 306, 49);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("RENTAL ADVANCE:");
		lblNewLabel_1_2_1.setForeground(Color.BLACK);
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_2_1.setBounds(46, 228, 192, 34);
		contentPane.add(lblNewLabel_1_2_1);
		
		RADDVANCE = new JTextField();
		RADDVANCE.setColumns(10);
		RADDVANCE.setBounds(46, 257, 265, 22);
		contentPane.add(RADDVANCE);
		
		JLabel lblNewLabel_1_2_1_1 = new JLabel("BALANCE:");
		lblNewLabel_1_2_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_2_1_1.setBounds(46, 273, 192, 34);
		contentPane.add(lblNewLabel_1_2_1_1);
		
		RBALANCE = new JTextField();
		RBALANCE.setColumns(10);
		RBALANCE.setBounds(46, 298, 265, 22);
		contentPane.add(RBALANCE);
		
		JButton btnNewButton_4 = new JButton("SEARCH");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String search = RSEARCH.getText();

                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "user", "");
                    String sql = "SELECT * FROM rentel1 WHERE VEHICLENO = ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, search);
                    ResultSet rs = pst.executeQuery();
                    boolean record = rs.next();
                    if (!record) {
                        JOptionPane.showMessageDialog(null, "Vehicle not found!");
                        con.close();
                        return;
                    }
                    String updatedVEHICLENO = rs.getString("VEHICLENO");
		            String CUSTOMERID = rs.getString("CUSTOMERID");
		            String ISSUEDATE = rs.getString("ISSUEDATE");
		            String RENTELCHARGE = rs.getString("RENTELCHARGE");
		            String RENTELADVANCE = rs.getString("RENTELADVANCE");
		            String BALANCE = rs.getString("BALANCE");

                    RVNUM.setText(updatedVEHICLENO);
                    RCNUM.setText(CUSTOMERID);
                    rdate.setText(ISSUEDATE);
                    RCHARGE.setText(RENTELCHARGE);
                    RADDVANCE.setText(RENTELADVANCE);
                    RBALANCE.setText(BALANCE);

                    con.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            
			}
		});
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_4.setBounds(401, 438, 184, 55);
		contentPane.add(btnNewButton_4);
		
		RSEARCH = new JTextField();
		RSEARCH.setBounds(669, 438, 203, 55);
		contentPane.add(RSEARCH);
		RSEARCH.setColumns(10);
	}
	
	
	@SuppressWarnings("unused")
	private void fetchDataFromDatabase() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "user", "");
            String sql = "SELECT * FROM rentel1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String VEHICLENO = rs.getString("VEHICLENO");
                String CUSTOMERID = rs.getString("CUSTOMERID");//VEHICLENO, CUSTOMERID, ISSUEDATE, RENTELCHARGE,RENTELADVANCE,BALANCE
                String ISSUEDATE = rs.getString("ISSUEDATE");
                String RENTELCHARGE = rs.getString("RENTELCHARGE");
                String RENTELADVANCE = rs.getString("RENTELADVANCE");
                String BALANCE = rs.getString("BALANCE");

                model.addRow(new Object[]{VEHICLENO, CUSTOMERID, ISSUEDATE, RENTELCHARGE,RENTELADVANCE,BALANCE});
            }

            rs.close();
            pst.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
}	
 }