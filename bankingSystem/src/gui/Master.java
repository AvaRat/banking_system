package gui;

import java.awt.EventQueue;
import java.awt.event.*;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;

import client.Controller;
import client.SessionClient;

public class Master {

	private JFrame frame;
	private Controller controller;
	private JButton updateButton;

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
	private void updateInfo()
	{
		if(updateButton != null)
			updateButton.doClick();
	}
	private boolean makeTransfer(int nr, double value) throws Exception
	{
		return controller.makeTransfer(nr, value);
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
	private int getAccountNr() throws Exception
	{
		return controller.getAccountNr();
	}
	/**
	 * function to open a new window for gathering signIn information
	 */
	public void signIn()
	{
		frame.setVisible(false);
		new SignInWindow(this); //
	}
	private void TransferCreator()
	{
		frame.setVisible(false);
		new TransferCreator(this); //
	}
	private void setUpdateButton(JButton b)
	{
		updateButton = b;
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
		JTextField accountInput;
		JTextField valueInput;
		JFrame transferFrame;
		public TransferListener(JTextField nr, JTextField value, JFrame tF, JButton quitBtn)
		{
			accountInput = nr;
			valueInput = value;
			transferFrame = tF;
			
			quitBtn.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					transferFrame.dispatchEvent(new WindowEvent(transferFrame, WindowEvent.WINDOW_CLOSING));
					frame.setVisible(true);
					
				}
			});
		}
		public void actionPerformed(ActionEvent event)
		{
			boolean success = false;
			int accountNr = Integer.parseInt(accountInput.getText());
			double value = Double.parseDouble(valueInput.getText());
			try {
				success = makeTransfer(accountNr, value);
			}catch (Exception e)
			{
				showErrorInDialogWindow(e.getMessage());
			}
			if(success)
			{
				JOptionPane.showMessageDialog(frame,
					    value+" $ sent to "+accountNr,
					    "Information",
					    JOptionPane.OK_OPTION);
				transferFrame.dispatchEvent(new WindowEvent(transferFrame, WindowEvent.WINDOW_CLOSING));
				updateInfo();
				frame.setVisible(true);
			}
		}
	}	
	public class RefreshListener implements ActionListener {
		JLabel customerName;
		JLabel balanceLabel;
		JPanel transferHistory;
		JLabel accountNumber;
		JPanel place2;
		
		public RefreshListener(JLabel name, JPanel history, JLabel balance, JLabel accountNr, JButton updateButton, JButton transferBtn, JPanel p2)
		{
			customerName = name;
			balanceLabel = balance;
			transferHistory = history; 
			accountNumber = accountNr;
			place2 = p2;
			
			setUpdateButton(updateButton);
			transferBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					TransferCreator();
				}
			});
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
			
			String history = "";
			try {
				history = getTransferHistory();
			}catch (Exception e)
			{
				e.printStackTrace();
				showErrorInDialogWindow(e.getMessage());
			}
			int accountNr = 100000;
			try {
				accountNr = getAccountNr();
			}catch (Exception e)
			{
				e.printStackTrace();
				showErrorInDialogWindow(e.getMessage());
			}
			accountNumber.setText(Integer.toString(accountNr));
			transferHistory.add(new JLabel(history));
			
		/*	
			
			JSONArray arr = new JSONArray(controller.getTransferJSONHistory());
			
			for(int i=0; i<history.length(); i++)
			{
				JSONObject transfer = arr.getJSONObject(i);
				JSONObject rec = transfer.getJSONObject("receiverNr");
				JSONObject sender = transfer.getJSONObject("senderNr");
				JTextField textField = new JTextField();
				place2.add(textField);
				textField.setColumns(10);
				place2.add(textField);
			}
			*/
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
