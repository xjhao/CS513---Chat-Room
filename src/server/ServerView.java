package server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JTextField;

/**
 * View of server
 * @author xhao
 *
 */
public class ServerView {

	private JFrame frame;
	public ServerController server;
	private JTextField txtPortNum;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerView window = new ServerView();
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
	public ServerView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				server.quitServer();
			}
		});
		
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCsChatroomServer = new JLabel("CS513 Chatroom Server");
		lblCsChatroomServer.setForeground(Color.BLUE);
		lblCsChatroomServer.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblCsChatroomServer.setBounds(0, 0, 450, 92);
		lblCsChatroomServer.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblCsChatroomServer);
		
		JButton btnStart = new JButton("Start Server");
		JButton btnStop = new JButton("Stop Server");
		btnStop.setEnabled(false);
		
		// Start server handler
		btnStart.setBounds(95, 178, 115, 37);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server = new ServerController(Integer.parseInt(txtPortNum.getText()));
				btnStart.setEnabled(false);
				btnStop.setEnabled(true);
				server.start();
				
			}
		});
		frame.getContentPane().add(btnStart);
		
		// Stop server handler
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.stopServer();
				btnStart.setEnabled(true);
				btnStop.setEnabled(false);
			}
		});
		btnStop.setBounds(253, 178, 115, 37);
		frame.getContentPane().add(btnStop);
		
		txtPortNum = new JTextField();
		txtPortNum.setText("4444");
		txtPortNum.setBounds(210, 104, 129, 37);
		frame.getContentPane().add(txtPortNum);
		txtPortNum.setColumns(10);
		
		JLabel lblPortNumber = new JLabel("Port Number:");
		lblPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblPortNumber.setBounds(95, 104, 129, 37);
		frame.getContentPane().add(lblPortNumber);
	}
}
