package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JLayeredPane;

public class SignInWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignInWindow frame = new SignInWindow();
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
	public SignInWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(10, 11, 179, 25);
		contentPane.add(splitPane);
		
		JLabel lblImi = new JLabel("name");
		splitPane.setLeftComponent(lblImi);
		
		textField = new JTextField();
		splitPane.setRightComponent(textField);
		textField.setColumns(10);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setBounds(10, 36, 179, 25);
		contentPane.add(splitPane_1);
		
		JLabel lblFamilyName = new JLabel("surname");
		splitPane_1.setLeftComponent(lblFamilyName);
		
		textField_1 = new JTextField();
		splitPane_1.setRightComponent(textField_1);
		textField_1.setColumns(10);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setBounds(10, 60, 179, 25);
		contentPane.add(splitPane_2);
		
		JLabel lblNewLabel = new JLabel("age");
		splitPane_2.setLeftComponent(lblNewLabel);
		
		textField_2 = new JTextField();
		splitPane_2.setRightComponent(textField_2);
		textField_2.setColumns(10);
		
		JSplitPane splitPane_3 = new JSplitPane();
		splitPane_3.setBounds(10, 83, 179, 25);
		contentPane.add(splitPane_3);
		
		JLabel lblUlica = new JLabel("street name");
		splitPane_3.setLeftComponent(lblUlica);
		
		textField_3 = new JTextField();
		splitPane_3.setRightComponent(textField_3);
		textField_3.setColumns(10);
		
		JSplitPane splitPane_4 = new JSplitPane();
		splitPane_4.setBounds(10, 106, 179, 25);
		contentPane.add(splitPane_4);
		
		JLabel lblMiasto = new JLabel("street nr");
		splitPane_4.setLeftComponent(lblMiasto);
		
		textField_4 = new JTextField();
		splitPane_4.setRightComponent(textField_4);
		textField_4.setColumns(10);
		
		JSplitPane splitPane_5 = new JSplitPane();
		splitPane_5.setBounds(10, 130, 179, 25);
		contentPane.add(splitPane_5);
		
		JLabel lblStreetNumber = new JLabel("city");
		splitPane_5.setLeftComponent(lblStreetNumber);
		
		textField_5 = new JTextField();
		splitPane_5.setRightComponent(textField_5);
		textField_5.setColumns(10);
		
		JSplitPane splitPane_6 = new JSplitPane();
		splitPane_6.setBounds(10, 155, 179, 25);
		contentPane.add(splitPane_6);
		
		JLabel lblCountry = new JLabel("country");
		splitPane_6.setLeftComponent(lblCountry);
		
		textField_6 = new JTextField();
		splitPane_6.setRightComponent(textField_6);
		textField_6.setColumns(10);
		
		JSplitPane splitPane_7 = new JSplitPane();
		splitPane_7.setBounds(225, 60, 179, 25);
		contentPane.add(splitPane_7);
		
		JLabel lblUsername = new JLabel("username");
		splitPane_7.setLeftComponent(lblUsername);
		
		JTextArea textArea_1 = new JTextArea();
		splitPane_7.setRightComponent(textArea_1);
		
		JSplitPane splitPane_8 = new JSplitPane();
		splitPane_8.setBounds(225, 130, 179, 25);
		contentPane.add(splitPane_8);
		
		JLabel lblPassword = new JLabel("password");
		splitPane_8.setLeftComponent(lblPassword);
		
		JTextArea textArea = new JTextArea();
		splitPane_8.setRightComponent(textArea);
	}
}
