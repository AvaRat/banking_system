package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.Controller;
import gui.Master.AuthenticationListener;

import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;

public class AuthenticationWindow extends JFrame {

	private JPanel contentPane;
	private JTextField loginField;
	private JPasswordField passwordField;

	/**
	 * Create the frame.
	 */
	public AuthenticationWindow(Master master) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(109, 67, 179, 25);
		contentPane.add(splitPane);
		
		loginField = new JTextField();
		splitPane.setRightComponent(loginField);
		loginField.setColumns(10);
		
		JLabel lblUsername = new JLabel("username");
		splitPane.setLeftComponent(lblUsername);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setBounds(109, 123, 179, 25);
		contentPane.add(splitPane_1);
		
		passwordField = new JPasswordField();
		splitPane_1.setRightComponent(passwordField);
		passwordField.setColumns(10);
		
		JLabel lblPassword = new JLabel("password");
		splitPane_1.setLeftComponent(lblPassword);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.setBounds(154, 159, 89, 23);
		
		Master.AuthenticationListener authListener = master.new AuthenticationListener(passwordField, loginField);
		btnLogIn.addActionListener(authListener);
		
		contentPane.add(btnLogIn);
		
		JButton btnSingIn = new JButton("Sing In");
		btnSingIn.setBounds(154, 227, 89, 23);
		contentPane.add(btnSingIn);
		
		JLabel lblDontHaveAn = new JLabel("Don't have an acount?");
		lblDontHaveAn.setBounds(135, 204, 132, 14);
		contentPane.add(lblDontHaveAn);
		
		JLabel lblWitajWNaszym = new JLabel("Witaj w naszym Banku");
		lblWitajWNaszym.setFont(new Font("Verdana", Font.PLAIN, 18));
		lblWitajWNaszym.setBounds(96, 11, 210, 36);
		contentPane.add(lblWitajWNaszym);
	}

}
