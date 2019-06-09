package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class SessionWindow extends JFrame {

	private static final long serialVersionUID = 12343243L;
	private JPanel contentPane;
	private JTextField transactionHistory;


	/**
	 * Create the frame.
	 */
	public SessionWindow(Master master) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1028, 691);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pulpit = new JPanel();
		pulpit.setBounds(448, 44, 359, 197);
		contentPane.add(pulpit);
		pulpit.setLayout(new BorderLayout(0, 0));
		
		JPanel balancePanel = new JPanel();
		balancePanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		balancePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		pulpit.add(balancePanel, BorderLayout.CENTER);
		balancePanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel label = new JLabel("Balance");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 24));
		balancePanel.add(label);
		
		JLabel balance = new JLabel("55.15");
		balance.setHorizontalAlignment(SwingConstants.CENTER);
		balance.setFont(new Font("Tahoma", Font.PLAIN, 21));
		balance.setAlignmentX(Component.CENTER_ALIGNMENT);
		balancePanel.add(balance);
		balance.setText("elo elo 320");
		
		
		JPanel powitanie = new JPanel();
		powitanie.setAlignmentX(Component.RIGHT_ALIGNMENT);
		pulpit.add(powitanie, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Welcome");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		powitanie.add(lblNewLabel);
		
		JLabel nameButton = new JLabel("<twoje Imie>");
		nameButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		powitanie.add(nameButton);
		
		
		JPanel panel = new JPanel();
		pulpit.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel lblYourAccountNr = new JLabel("Your account nr");
		lblYourAccountNr.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblYourAccountNr);
		
		JLabel AccountNr = new JLabel("000000000000");
		AccountNr.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(AccountNr);
		
		JButton btnNewTransfer = new JButton("New transfer");
		btnNewTransfer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewTransfer.setBounds(655, 469, 201, 33);
		contentPane.add(btnNewTransfer);

		transactionHistory = new JTextField();
		transactionHistory.setBounds(40, 84, 330, 494);
		contentPane.add(transactionHistory);
		transactionHistory.setColumns(10);
		transactionHistory.setEditable(false);
		
		JLabel lblHistoriaOperacji = new JLabel("transfer history");
		lblHistoriaOperacji.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblHistoriaOperacji.setBounds(119, 40, 174, 33);
		contentPane.add(lblHistoriaOperacji);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnRefresh.setBounds(706, 304, 121, 48);
		contentPane.add(btnRefresh);
		Master.RefreshListener refreshListener = master.new RefreshListener(nameButton, transactionHistory, balance);
		btnRefresh.addActionListener(refreshListener);
	}
}
