package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import server.ClientMap;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * The view of log in
 * @author xhao
 *
 */
public class Login {

	private JFrame frame;
	private JTextField txtNickName;
	private JTextField txtHostName;
	private JTextField txtPortNum;
	
	private ClientController cc;
	private DataInputStream  din;
	private DataOutputStream dout;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 302);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CS513 Chatroom Login");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setBounds(0, 6, 450, 49);
		frame.getContentPane().add(lblNewLabel);
		
		txtNickName = new JTextField();
		txtNickName.setBounds(202, 67, 134, 28);
		frame.getContentPane().add(txtNickName);
		txtNickName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Nick Name: ");
		lblNewLabel_1.setBounds(103, 73, 87, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		txtHostName = new JTextField();
		txtHostName.setText("localhost");
		txtHostName.setBounds(202, 107, 134, 28);
		frame.getContentPane().add(txtHostName);
		txtHostName.setColumns(10);
		
		JLabel lblHostName = new JLabel("Host Name:");
		lblHostName.setBounds(103, 113, 87, 16);
		frame.getContentPane().add(lblHostName);
		
		txtPortNum = new JTextField();
		txtPortNum.setText("4444");
		txtPortNum.setBounds(202, 147, 134, 28);
		frame.getContentPane().add(txtPortNum);
		txtPortNum.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Port Number:");
		lblNewLabel_2.setBounds(103, 153, 87, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		// "log in" button
		JButton btnLogin = new JButton("Log in");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(txtNickName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please input your nick name!");
				} else if(txtHostName.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please input server's host name!");
				} else if(txtPortNum.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please input server's port number!");
				} else {
					try {
						cc = new ClientController(txtHostName.getText(), Integer.parseInt(txtPortNum.getText()));
						boolean connected = cc.connectServer(txtNickName.getText());	
						if (connected) {
							frame.dispose();
							ClientView cv = new ClientView(txtNickName.getText(), cc);
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						System.out.println("Client connection refused!");
						JOptionPane.showMessageDialog(null, "Log in failed! \nPlease make sure the server is "
								+ "started\n and you type in the correct host name and port number!");
					}
					
				}
			}
		});
		btnLogin.setBounds(171, 206, 117, 29);
		frame.getContentPane().add(btnLogin);
	}
}
