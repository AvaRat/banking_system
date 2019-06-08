package gui;

import java.awt.EventQueue;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.*;

import client.Controller;
import client.TerminalClient;

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
					Master window = new Master();

					window.authenticateWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Master() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
	}

	private void authenticateWindow() throws Exception
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
		public void actionPerformed(ActionEvent event)
		{
			
			String password = passwordField.getPassword().toString();
			String login = loginField.getText().toString();
			try
			{
				if(client.authenticate(login, password) == true)
				{
					
				}
			}catch (Exception e)
			{
				
			}
			
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			frame = new SignInWindow();
			frame.setVisible(true);
		}
	}
}
