package gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SignInWindow extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1562456456L;
	private JPanel contentPane;
	private JTextField nameInput;
	private JTextField surnameInput;
	private JTextField ageInput;
	private JTextField streetnameInput;
	private JTextField streetNrInput;
	private JTextField cityInput;
	private JTextField countryInput;
	private JTextField loginInput;
	private JPasswordField passwordInput;


	public void run()
	{
		
	}
	
	/**
	 * Create the frame.
	 */
	public SignInWindow(Master master) {
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 734, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel personalInfoPanel = new JPanel();
		contentPane.add(personalInfoPanel);
		personalInfoPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel name = new JPanel();
		personalInfoPanel.add(name);
		name.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel label = new JLabel("name");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		name.add(label);
		
		JLabel label_1 = new JLabel("surname");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		name.add(label_1);
		
		JLabel label_2 = new JLabel("age");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		name.add(label_2);
		
		JLabel label_3 = new JLabel("street name");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		name.add(label_3);
		
		JLabel label_4 = new JLabel("street Nr");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		name.add(label_4);
		
		JLabel label_5 = new JLabel("city");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		name.add(label_5);
		
		JLabel label_6 = new JLabel("country");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		name.add(label_6);
		
		JPanel input = new JPanel();
		personalInfoPanel.add(input);
		input.setLayout(new GridLayout(0, 1, 0, 0));
		
		nameInput = new JTextField();
		label.setLabelFor(nameInput);
		input.add(nameInput);
		nameInput.setColumns(10);
		
		surnameInput = new JTextField();
		label_1.setLabelFor(surnameInput);
		input.add(surnameInput);
		surnameInput.setColumns(10);
		
		ageInput = new JTextField();
		label_2.setLabelFor(ageInput);
		input.add(ageInput);
		ageInput.setColumns(10);
		
		streetnameInput = new JTextField();
		label_3.setLabelFor(streetnameInput);
		input.add(streetnameInput);
		streetnameInput.setColumns(10);
		
		streetNrInput = new JTextField();
		label_4.setLabelFor(streetNrInput);
		input.add(streetNrInput);
		streetNrInput.setColumns(10);
		
		cityInput = new JTextField();
		label_5.setLabelFor(cityInput);
		input.add(cityInput);
		cityInput.setColumns(10);
		
		countryInput = new JTextField();
		label_6.setLabelFor(countryInput);
		input.add(countryInput);
		countryInput.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 140, 284, 73);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel_1.add(panel);
		panel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel label_7 = new JLabel("Login");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label_7);
		
		loginInput = new JTextField();
		loginInput.setColumns(10);
		panel.add(loginInput);
		
		JLabel label_8 = new JLabel("Password");
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label_8);
		
		passwordInput = new JPasswordField();
		panel.add(passwordInput);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.setFont(new Font("Tahoma", Font.PLAIN, 24));
		Master.SignInListener signListener = master.new SignInListener(this, nameInput, surnameInput, ageInput,
				streetnameInput, streetNrInput, cityInput, countryInput, loginInput, passwordInput);
		btnSignIn.addActionListener(signListener);
		
		btnSignIn.setBounds(95, 292, 151, 80);
		panel_1.add(btnSignIn);
	}

}
