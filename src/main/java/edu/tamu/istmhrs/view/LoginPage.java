package edu.tamu.istmhrs.view;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage {
	JFrame frame;
	JLabel username;
	JLabel password;
	JTextField userField;
	JPasswordField passField;
	JButton login;
	Container contPane;

	public LoginPage() {
		frame = new Frame().create();
		// Declare the components
		username = new JLabel("Enter Username");
		password = new JLabel("Enter Password");
		userField = new JTextField();
		passField = new JPasswordField();
		login = new JButton("Login");
	}

	// Create and all the components to the frame
	public void create() {
		contPane = frame.getContentPane();
		contPane.setLayout(null);
		addUserPass();
		addLogin();
		frame.setVisible(true);
	}

	public void addUserPass() {
		username.setBounds(160, 100, 120, 40);
		username.setFont(new Font("Serif", Font.BOLD, 17));
		userField.setBounds(290, 100, 200, 40);
		userField.setFont(new Font("Serif", Font.BOLD, 17));

		password.setBounds(160, 170, 120, 40);
		password.setFont(new Font("Serif", Font.BOLD, 17));
		passField.setBounds(290, 170, 200, 40);
		passField.setFont(new Font("Serif", Font.BOLD, 17));
		
		contPane.add(username);
		contPane.add(password);
		contPane.add(userField);
		contPane.add(passField);
	}

	public void addLogin() {
		login.setBounds(190, 250, 120, 40);
		login.setFont(new Font("Serif", Font.BOLD, 17));
		contPane.add(login);

		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (!(userField.getText().equals("admin") && (passField.getText().equals("admin")))) {
					JOptionPane.showMessageDialog(null, "Wrong credentials. Use username/password as admin/admin");
				} else {
					validate("login");
				} } });
	}

	public void validate(String source) {
		if (source.equals("login")) {
			SuggestPage page = new SuggestPage();
			page.create();
		}
	}
}
