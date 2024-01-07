package oop;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//its help to access the class and interfaces for database
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class royal1 {

    JFrame frame;
    private JTextField user;
    private JPasswordField pass;

    public static void main(String[] args) {
    	try (Connection connection = null) {
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    royal1 window = new royal1();
                    window.frame.setVisible(true);
                   
                  
                }
                    catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public royal1() //constructer
    {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(127, 255, 212));
        frame.setBounds(100, 100, 419, 417);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Username:");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 19));
        lblNewLabel.setBounds(75, 84, 185, 29);
        frame.getContentPane().add(lblNewLabel);

        user = new JTextField();
        user.setBounds(72, 112, 237, 29);
        frame.getContentPane().add(user);
        user.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Password:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 19));
        lblNewLabel_1.setBounds(75, 152, 185, 29);
        frame.getContentPane().add(lblNewLabel_1);

        pass = new JPasswordField();
        pass.setEchoChar('$');
        pass.setBounds(76, 185, 233, 29);
        frame.getContentPane().add(pass);

        JButton btnNewButton = new JButton("Login");
        btnNewButton.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
            	  if (user.getText().isEmpty() && pass.getText().isEmpty()) {
                      JOptionPane.showMessageDialog(frame, "Please enter both username and password.");}
            	 else {

            	try { //we must do the connection into try catch block
					Class.forName("com.mysql.cj.jdbc.Driver"); //this is sql driver name
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/user ","user","");
					System.out.print("connected");                                                    
					JOptionPane.showMessageDialog(null, "database had been connected successfully");//this is confirmation popup message.
					String sql = "SELECT * FROM `login` WHERE userid=? AND password=?"; //this is sql quary
				    PreparedStatement pst = con.prepareStatement(sql); //this is a statement for sql
				    
				    
				    
				    String userid=user.getText();//get the values for user
				    String password=pass.getText();
				    
				    pst.setString(1, userid);//set the values to arguments
				    pst.setString(2,password);
				    
				    ResultSet rs = pst.executeQuery();//here execute the sql quary
				    if (rs.next())//its check the quary  line by line 
				    {                 
				        JOptionPane.showMessageDialog(frame, "Login successful!");
				        con.close();   //close the database connection
					    frame.dispose(); //close the current frame
					    page1 frame = new page1(); //this is a way to open the next frame
						frame.setVisible(true);
				    } else {
				        JOptionPane.showMessageDialog(frame, "Login failed. Please check your username and password.");
				    }
				    
				    
				} catch (ClassNotFoundException e1) {
					                                                              
					e1.printStackTrace();
				} catch (SQLException e1) {
					                                                             
					e1.printStackTrace();
				
            }
        }}});

        btnNewButton.setBackground(new Color(255, 255, 204));
        btnNewButton.setHideActionText(true);
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 19));
        btnNewButton.setBounds(62, 237, 137, 36);
        frame.getContentPane().add(btnNewButton);
        
        final JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {btnReset.addActionListener(new ActionListener() {
        	    public void actionPerformed(ActionEvent e) {
        	        // Clear the text fields
        	        user.setText("");
        	        pass.setText("");
        	    }
        	});
        	}
        });
        btnReset.setHideActionText(true);
        btnReset.setFont(new Font("Tahoma", Font.BOLD, 19));
        btnReset.setBackground(new Color(255, 255, 204));
        btnReset.setBounds(214, 237, 137, 36);
        frame.getContentPane().add(btnReset);
        
        JLabel lblNewLabel_2 = new JLabel("CITYCAB RENT");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 50));
        lblNewLabel_2.setBounds(10, 11, 384, 62);
        frame.getContentPane().add(lblNewLabel_2);
        
        JButton btnRegister = new JButton("Signup");
        btnRegister.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 frame.dispose();
				    register frame = new register();
					frame.setVisible(true);
        		
        	}
        });
        btnRegister.setHideActionText(true);
        btnRegister.setFont(new Font("Tahoma", Font.BOLD, 19));
        btnRegister.setBackground(new Color(255, 255, 204));
        btnRegister.setBounds(134, 323, 137, 36);
        frame.getContentPane().add(btnRegister);
        
        JTextPane txtpnNotAMember = new JTextPane();
        txtpnNotAMember.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtpnNotAMember.setText("New Staff?");
        txtpnNotAMember.setBackground(new Color(102, 255, 204));
        txtpnNotAMember.setBounds(72, 292, 121, 20);
        frame.getContentPane().add(txtpnNotAMember);
    }
}
	

	
	

       