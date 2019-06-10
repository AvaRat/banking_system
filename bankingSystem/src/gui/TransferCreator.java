package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

public class TransferCreator extends JFrame {

	private JPanel contentPane;
	private JTextField nrInput;
	private JTextField valueInput;

	/**
	 * Create the frame.
	 */
	public TransferCreator(Master master) {
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 931, 536);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblTransferCreator = new JLabel("Transfer Creator");
		lblTransferCreator.setHorizontalAlignment(SwingConstants.CENTER);
		lblTransferCreator.setFont(new Font("Tahoma", Font.PLAIN, 24));
		contentPane.add(lblTransferCreator, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(304, 153, 194, 69);
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel label = new JLabel("Account Nr");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(label);
		
		nrInput = new JTextField();
		nrInput.setColumns(10);
		panel_1.add(nrInput);
		
		JLabel label_1 = new JLabel("Value");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(label_1);
		
		valueInput = new JTextField();
		valueInput.setColumns(10);
		panel_1.add(valueInput);
		
		JButton btnQuit = new JButton("QUIT");
		contentPane.add(btnQuit, BorderLayout.SOUTH);
		
		Master.TransferListener listener = master.new TransferListener(nrInput, valueInput, this, btnQuit);
		
		JButton btnSend = new JButton("Send");
		btnSend.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnSend.setBounds(329, 288, 154, 69);
		panel.add(btnSend);
		btnSend.addActionListener(listener);
		
	}

}
