package oop;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// its help to access classes and interfaces for database.
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class register extends JFrame //register class inharite the JFrame class 
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField rname;
	private JPasswordField rpass;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					register frame = new register();
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
	public register() //constructor for register class
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 390);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(127, 255, 212));
		contentPane.setForeground(new Color(255, 204, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		rname = new JTextField();
		rname.setBackground(UIManager.getColor("Button.highlight"));
		rname.setBounds(66, 113, 250, 35);
		contentPane.add(rname);
		rname.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("USERID:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel.setBounds(66, 81, 161, 35);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("password:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(70, 159, 161, 36);
		contentPane.add(lblNewLabel_1);
		
		rpass = new JPasswordField();
		rpass.setBackground(UIManager.getColor("Button.highlight"));
		rpass.setBounds(70, 191, 246, 35);
		contentPane.add(rpass);
		
		JButton btnNewButton = new JButton("REGISTER");
		btnNewButton.setBackground(UIManager.getColor("Button.shadow"));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    try {
			        String userid = rname.getText();
			        String password = new String(rpass.getPassword());

			        // Validate userid length
			        if (userid.length() < 8) {
			            JOptionPane.showMessageDialog(null, "Userid should be at least 8 characters long.", "Validation Error", JOptionPane.ERROR_MESSAGE);
			            return; // Stop further processing if validation fails
			        }

			        // Validate password complexity
			        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z]).{8,}$") && password.length() < 8 ) {
			            JOptionPane.showMessageDialog(null, "Password should have at least 8 characters with at least one uppercase and one lowercase letter.", "Validation Error", JOptionPane.ERROR_MESSAGE);
			            return; // Stop further processing if validation fails
			        }

			        // If validation passes, proceed with database operations
			        Class.forName("com.mysql.cj.jdbc.Driver");
			        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "");
			        System.out.println("Connected to the database");

			        String sql = "INSERT INTO `login` (userid, password) VALUES (?, ?)";
			        PreparedStatement pst = con.prepareStatement(sql);

			        pst.setString(1, userid);
			        pst.setString(2, password);

			        int rowsAffected = pst.executeUpdate();

			        if (rowsAffected > 0) {
			            System.out.println("User registered successfully");
			            int choice = JOptionPane.showConfirmDialog(null, "Do you want to register this?", "Confirmation", JOptionPane.YES_NO_OPTION);
			            JOptionPane.showMessageDialog(null, "Registration successful.");
			            dispose();
			            royal1 frame = new royal1();
			            frame.frame.setVisible(true);
			        } else {
			            System.out.println("Failed to register user");
			        }

			        // Clear input fields
			        rname.setText("");
			        rpass.setText("");

			        // Close PreparedStatement and Connection
			        pst.close();
			        con.close();

			    } catch (SQLException | ClassNotFoundException e1) {
			        e1.printStackTrace();
			    }
			}
});

		btnNewButton.setBounds(40, 253, 341, 47);
		contentPane.add(btnNewButton);
		
		lblNewLabel_2 = new JLabel("New user registation");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_2.setBounds(24, 0, 371, 54);
		contentPane.add(lblNewLabel_2);
	}
}
