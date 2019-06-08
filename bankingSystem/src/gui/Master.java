package gui;

import java.awt.EventQueue;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.*;

import client.Controller;
import client.SessionClient;

public class Master {

	private JFrame frame;
	private Controller client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Controller c = new SessionClient();
					c.connectToServer();
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
		client = c;
	}
	public Master() {
		
	}
	public void setController(Controller c)
	{
		client = c;
		
	}
	public boolean connectToServer()
	{
		try
		{
			client.connectToServer();
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


	public  class AuthenticationListener implements ActionListener{
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
				if(client.authenticate(login, password) == true)
				{
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					frame = new ClientWindow();
					frame.setVisible(true);
				} else
				{
					JOptionPane.showMessageDialog(frame,
						    "wrong login or password",
						    "Warning",
						    JOptionPane.WARNING_MESSAGE);
				}
			}catch (Exception e)
			{
				JOptionPane.showMessageDialog(frame,
					    e.getMessage(),
					    "Warning",
					    JOptionPane.WARNING_MESSAGE);
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
}
