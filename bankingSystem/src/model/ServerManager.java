package model;

import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class ServerManager extends Thread {

	private JFrame frame;
	private Server server;

	/**
	 * Launch the application.
	 */
	public ServerManager(Server s) 
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new JFrame("Server Manager");
					frame.setVisible(true);	
					server = s;
					initialize();
				}catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JTextArea textArea = new JTextArea();
		panel.add(textArea, BorderLayout.CENTER);
		
		JButton btnViewactivesessions = new JButton("ViewActiveSessions");
		btnViewactivesessions.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(btnViewactivesessions, BorderLayout.NORTH);
		btnViewactivesessions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//			/	textArea.setText("");
				textArea.setText(server.getActiveSessions());
			}
		});
		
		JButton btnQuit = new JButton("Quit");
		panel.add(btnQuit, BorderLayout.WEST);
		btnQuit.addActionListener(new ActionListener(){  
		    public void actionPerformed(ActionEvent e){  
		    	server.listening = false;
		    	frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		    }  
		    });
		
	}

}
