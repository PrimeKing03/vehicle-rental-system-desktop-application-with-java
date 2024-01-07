package oop;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class page1 extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					page1 frame = new page1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param <vehicle>
	 */
	public <vehicle> page1() {
		getContentPane().setBackground(new Color(127, 255, 212));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 464);
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Customer");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				customer c=new customer();
				c.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 19));
		btnNewButton.setBackground(UIManager.getColor("Button.shadow"));
		btnNewButton.setBounds(151, 103, 238, 53);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("WELCOME TO CITYCAB RENT.");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel.setBackground(new Color(0, 0, 51));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 543, 67);
		getContentPane().add(lblNewLabel);
		
		JButton btnVehicles = new JButton("Vehicles");
		btnVehicles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				vehicle1 v=new vehicle1();
				v.setVisible(true);
			}
		});
		btnVehicles.setFont(new Font("Tahoma", Font.BOLD, 19));
		btnVehicles.setBackground(UIManager.getColor("Button.shadow"));
		btnVehicles.setBounds(151, 167, 238, 53);
		getContentPane().add(btnVehicles);
		
		JButton btnRentel = new JButton("Rentel");
		btnRentel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				rentel1 rentelFrame = new rentel1();
	                rentelFrame.setVisible(true);
			}
		});
		btnRentel.setFont(new Font("Tahoma", Font.BOLD, 19));
		btnRentel.setBackground(UIManager.getColor("Button.shadow"));
		btnRentel.setBounds(151, 231, 238, 53);
		getContentPane().add(btnRentel);
		
		JButton btnLogout = new JButton("logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				int choice = JOptionPane.showConfirmDialog(null, "Do you want to logout this?", "Confirmation", JOptionPane.YES_NO_OPTION);

				JOptionPane.showMessageDialog(null, "Logout had been finished successfully");
				JOptionPane.showMessageDialog(null, "database had been disconnected successfully");
 
				dispose();
			}
		});
		btnLogout.setFont(new Font("Tahoma", Font.BOLD, 19));
		btnLogout.setBackground(UIManager.getColor("Button.shadow"));
		btnLogout.setBounds(151, 295, 238, 53);
		getContentPane().add(btnLogout);
	}

}
