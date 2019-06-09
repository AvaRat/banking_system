package gui;

import java.awt.EventQueue;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.*;

import client.Controller;
import client.SessionClient;

public class Master {

	private JFrame frame;
	private Controller controller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception{
		Controller c = new SessionClient();
		c.connectToServer();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Master window = new Master();
					window.setController(c);
				} catch (Exception e) {
					JFrame f = new JFrame("warning");
					JOptionPane.showMessageDialog(f,
						    e.getMessage(),
						    "Warning",
						    JOptionPane.WARNING_MESSAGE);
					f.dispose();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Master(Controller c) {
		controller = c;
	}
	public Master() {
		
	}
	public void setController(Controller c)
	{
		controller = c;
		
	}
	public boolean connectToServer()
	{
		try
		{
			controller.connectToServer();
		} catch (Exception e) 
		{			
			JFrame f = new JFrame("warning");
			JOptionPane.showMessageDialog(f,
				    e.getMessage(),
				    "Warning",
				    JOptionPane.WARNING_MESSAGE);
			f.dispose();
			return false;
		}
		return true;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		authenticateWindow();
	}

	private void authenticateWindow()
	{
		frame = new AuthenticationWindow(this);
		frame.setVisible(true);
	}
	private void startSession()
	{
		frame = new SessionWindow(this);
		frame.setVisible(true);
	}
	public void showErrorInDialogWindow(String err)
	{
		JOptionPane.showMessageDialog(frame,
			    err,
			    "Warning",
			    JOptionPane.WARNING_MESSAGE);
	}
	/**
	 * 
	 * @return
	 * @throws Exception when there was an error while reading socket input reader
	 */
	private double getCustomerBalance() throws Exception
	{
		return controller.getBalance();
	}
	private String getCustomerName() throws Exception
	{
		return controller.getName();
	}
	private String getTransferHistory() throws Exception
	{
		return controller.getTransferHistory();
	}
	/**
	 * function to open a new window for gathering signIn information
	 */
	public void signIn()
	{
		frame.setVisible(false);
		new SignInWindow(this); //
	}
	
	private void signIn(int age_, String name_, String surname_,
			String street_, int streetNr_, String city_, String country_, String login, String password) throws Exception
	{
		controller.signIn(age_, name_, surname_,
			street_, streetNr_, city_, country_, login, password);
	}

	public  class AuthenticationListener implements ActionListener {
		private JPasswordField passwordField;
		private JTextField loginField;
		
		public AuthenticationListener(JPasswordField p, JTextField l)
		{
			passwordField = p;
			loginField = l;
		}
		/**
		 * if authentication succedeed old frame is dispached and new->ClientSession is created
		 * if failed nothing happened
		 */
		public void actionPerformed(ActionEvent event)
		{
			
			char []pass = passwordField.getPassword();
			String password = String.valueOf(pass);
			String login = loginField.getText().toString();
			passwordField.setText("");
			loginField.setText("");
			try
			{
				if(controller.authenticate(login, password) == true)
				{
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					startSession();
				} else
				{
					JOptionPane.showMessageDialog(frame,
						    "wrong login or password",
						    "Warning",
						    JOptionPane.WARNING_MESSAGE);
				}
			}catch (Exception e)
			{
				showErrorInDialogWindow(e.getMessage());
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}	
		}
	}
	public class TransferListener implements ActionListener {
		public TransferListener()
		{
			
		}
		public void actionPerformed(ActionEvent event)
		{
		}
	}	
	public class RefreshListener implements ActionListener {
		JLabel customerName;
		JLabel balanceLabel;
		JTextField transferHistory;
		
		public RefreshListener(JLabel name, JTextField history, JLabel balance)
		{
			customerName = name;
			balanceLabel = balance;
			transferHistory = history; 
		}
		public void actionPerformed(ActionEvent event)
		{
			double balance = 0000000;
			try {
				balance = getCustomerBalance();
			}catch (Exception e)
			{
				e.printStackTrace();
				showErrorInDialogWindow(e.getMessage());
			}
			balanceLabel.setText(String.valueOf(balance));
			
			String name = "????";
			try {
				name = getCustomerName();
			}catch (Exception e)
			{
				e.printStackTrace();
				showErrorInDialogWindow(e.getMessage());
			}
			customerName.setText(name);
			
			String history = "????";
			try {
				history = getTransferHistory();
			}catch (Exception e)
			{
				e.printStackTrace();
				showErrorInDialogWindow(e.getMessage());
			}
			transferHistory.setText(history);
			
		}
	}
	public class SignInListener implements ActionListener{
		JTextField nameField;
		JTextField surNameField;
		JTextField ageField;
		JTextField streetNameField;
		JTextField streetNrField;
		JTextField cityField;
		JTextField countryField;
		JTextField loginField;
		JPasswordField passwordField;
		JFrame signInFrame;
		
		public SignInListener(JFrame frame, JTextField nameField, JTextField surNameField,
					JTextField ageField,JTextField streetNameField,JTextField streetNrField,
					JTextField cityField, JTextField countryField, JTextField loginField,
					JPasswordField passwordField)
		{
			this.signInFrame = frame;
			this.nameField = nameField;
			this.surNameField =surNameField;
			this.ageField = ageField;
			this.streetNameField = streetNameField;
			this.streetNrField = streetNrField;
			this.cityField = cityField;
			this.countryField = countryField;
			this.loginField = loginField;
			this.passwordField = passwordField;
		}
		
		public void actionPerformed(ActionEvent event)
		{
			try {
				signIn(Integer.parseInt(ageField.getText()), nameField.getText(), surNameField.getText(),
						streetNameField.getText(), Integer.parseInt(streetNrField.getText()), cityField.getText(),
						countryField.getText(), loginField.getText(), String.valueOf(passwordField.getPassword()));
			}catch (NumberFormatException e)
			{
				showErrorInDialogWindow("Not filled in properly");
			}
			catch (Exception e)
			{
				showErrorInDialogWindow(e.getMessage());
				
				return;
			}
			frame.setVisible(true);
			signInFrame.dispatchEvent(new WindowEvent(signInFrame, WindowEvent.WINDOW_CLOSING));
		}
		
	}
	
}
