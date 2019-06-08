package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

public class ClientWindow extends JFrame {

	private static final long serialVersionUID = 12343243L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow frame = new ClientWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientWindow() {
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
		
		JPanel balance = new JPanel();
		balance.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		balance.setAlignmentX(Component.LEFT_ALIGNMENT);
		pulpit.add(balance, BorderLayout.CENTER);
		balance.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel label = new JLabel("Balance");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 24));
		balance.add(label);
		
		JLabel label_1 = new JLabel("55.15");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 21));
		label_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		balance.add(label_1);
		
		JPanel powitanie = new JPanel();
		powitanie.setAlignmentX(Component.RIGHT_ALIGNMENT);
		pulpit.add(powitanie, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Welcome");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		powitanie.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("<twoje Imie>");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		powitanie.add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		pulpit.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel lblYourAccountNr = new JLabel("Your account nr");
		lblYourAccountNr.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblYourAccountNr);
		
		JLabel lblNewLabel_2 = new JLabel("000000000000");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_2);
		
		JButton btnNewTransfer = new JButton("New transfer");
		btnNewTransfer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewTransfer.setBounds(655, 469, 201, 33);
		contentPane.add(btnNewTransfer);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 44, 381, 597);
		
		contentPane.add(scrollPane);
		
		JPanel historia = new JPanel();
		scrollPane.setViewportView(historia);
		historia.setLayout(new BorderLayout(0, 0));
		
		JLabel lblHistoriaOperacji = new JLabel("transfer history");
		lblHistoriaOperacji.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblHistoriaOperacji.setBounds(147, 11, 174, 33);
		contentPane.add(lblHistoriaOperacji);
	}
}
