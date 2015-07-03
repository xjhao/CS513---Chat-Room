package client;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.DropMode;
import javax.swing.JList;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JComboBox;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 * The view of client
 * @author xhao
 *
 */
public class ClientView implements Runnable{

	JFrame frame;
	
	private String userNickName;
	private ClientController cc;
	private String hostName;
	private int portNum;
	private DefaultListModel<String> usrList = new DefaultListModel<String>();
	private DefaultComboBoxModel<String> usrCombo = new DefaultComboBoxModel<String>();
	
	JTextArea bulletinBoard;
	JTextArea whisperBoard;
	JButton btnSendMsg;
	JButton btnLogin;
	JButton btnLogout;
	
	Thread t;

	/**
	 * Create the application.
	 */
	public ClientView(String userNickName, ClientController cc) {
		initialize(userNickName, cc);
		frame.setVisible(true);
		listenToServerThread();
	}
	
	public void listenToServerThread() {
		t = new Thread(this);
		// run the new client thread
		t.start();
	}

	/**
	 * Initialize the contents of the frame.
	 * @param userNickName 
	 */
	private void initialize(String userNickName, ClientController cc) {
		this.cc = cc;
		this.userNickName = userNickName;
		this.portNum = cc.portNum;
		this.hostName = cc.hostName;
		
		frame = new JFrame();
		frame.setTitle(userNickName + " - " + cc.socket.getRemoteSocketAddress());
		frame.setBounds(100, 100, 482, 454);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		bulletinBoard = new JTextArea();
		bulletinBoard.setEditable(false);
		bulletinBoard.setBounds(112, 30, 332, 78);
		frame.getContentPane().add(bulletinBoard);
		
		whisperBoard = new JTextArea();
		whisperBoard.setEditable(false);
		whisperBoard.setBounds(112, 143, 332, 71);
		frame.getContentPane().add(whisperBoard);
		
		JTextArea txtMsg = new JTextArea();
		txtMsg.setBounds(6, 226, 262, 46);
		frame.getContentPane().add(txtMsg);
		
		// "Send to" button
		btnSendMsg = new JButton("Send");
		btnSendMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String receiverNickName = (String) usrCombo.getSelectedItem(); 
				if (!cc.socket.isClosed()) {
					try {
						cc.dout.writeUTF("message##" + userNickName + "##" + receiverNickName + "##" + txtMsg.getText());
						txtMsg.setText("");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
					
			}
		});
		btnSendMsg.setBounds(394, 371, 82, 29);
		frame.getContentPane().add(btnSendMsg);
		
		// "log out" button
		btnLogout = new JButton("Log out");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cc.disconnectServer(userNickName);
					btnLogout.setEnabled(false);
					btnLogin.setEnabled(true);
					usrList.removeAllElements();
					usrCombo.removeAllElements();
					//System.exit(0);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLogout.setBounds(364, 274, 98, 29);
		frame.getContentPane().add(btnLogout);
		
		// "Log in" button
		btnLogin = new JButton("Re-log in");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cc.reconnectServer(hostName, portNum);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				boolean connected = cc.connectServer(userNickName);	
				if (connected) {
					listenToServerThread();
					btnLogin.setEnabled(false);
					btnLogout.setEnabled(true);
					btnSendMsg.setEnabled(true);
				}
			}
		});
		btnLogin.setEnabled(false);
		btnLogin.setBounds(364, 309, 98, 23);
		frame.getContentPane().add(btnLogin);
		
		JLabel lblOnlineUsers = new JLabel("Online Users");
		lblOnlineUsers.setHorizontalAlignment(SwingConstants.CENTER);
		lblOnlineUsers.setBounds(364, 6, 101, 16);
		frame.getContentPane().add(lblOnlineUsers);
		
		JLabel lblBroadboard = new JLabel("Bulletin Board");
		lblBroadboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblBroadboard.setBounds(6, 6, 335, 16);
		frame.getContentPane().add(lblBroadboard);
		
		JLabel lblWisperBoard = new JLabel("Whisper Board");
		lblWisperBoard.setHorizontalAlignment(SwingConstants.CENTER);
		lblWisperBoard.setBounds(6, 177, 335, 29);
		frame.getContentPane().add(lblWisperBoard);
		
		JComboBox<String> comboBox = new JComboBox<String>(usrCombo);
		comboBox.setMaximumRowCount(20);
		comboBox.setBounds(275, 369, 121, 32);
		frame.getContentPane().add(comboBox);
		
		JList<String> list = new JList<String>(usrList);
		list.setEnabled(false);
		list.setBounds(10, 30, 82, 184);
		frame.getContentPane().add(list);
		
		JScrollPane scrollPaneBulletin = new JScrollPane(bulletinBoard);
		scrollPaneBulletin.setBounds(16, 30, 321, 144);
		frame.getContentPane().add(scrollPaneBulletin);
		
		JScrollPane scrollPaneWhisper = new JScrollPane(whisperBoard);
		scrollPaneWhisper.setBounds(16, 206, 321, 130);
		frame.getContentPane().add(scrollPaneWhisper);
		
		JScrollPane scrollPaneMsg = new JScrollPane(txtMsg);
		scrollPaneMsg.setBounds(16, 351, 231, 62);
		frame.getContentPane().add(scrollPaneMsg);
		
		JScrollPane scrollPaneUsr = new JScrollPane(list);
		scrollPaneUsr.setBounds(364, 30, 100, 232);
		frame.getContentPane().add(scrollPaneUsr);
		
		JLabel lblTo = new JLabel("To");
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTo.setBounds(242, 370, 46, 29);
		frame.getContentPane().add(lblTo);

		
		// When closing the window of a client, it means this client logged out the server
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					if (!cc.socket.isClosed()) {
						cc.disconnectServer(userNickName);
					}
					System.exit(0);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String info;
		try {
			usrCombo.addElement("All");
			while ((info = cc.din.readUTF()) != null) {
				String s[] = info.split("##");
				if (s[0].equals("create list")) {
					if(!s[1].equals(userNickName)) {
						usrCombo.addElement(s[1]);
					}
					usrList.addElement(s[1]);
				} 
				if(s[0].equals("add one")) {
					if(!s[1].equals(userNickName)) {
						usrCombo.addElement(s[1]);
					}
					usrList.addElement(s[1]);
					bulletinBoard.append("[SYSTEM]: " + s[1] + " logged in!\n");
				} 
				if(s[0].equals("remove one")) {
					if (s[1].equals(userNickName)) {
						btnSendMsg.setEnabled(false);
						bulletinBoard.append("[SYSTEM]: " + s[1] + " logged out!\n");
						break;
					} else {
						usrList.removeElement(s[1]);
						usrCombo.removeElement(s[1]);
						bulletinBoard.append("[SYSTEM]: " + s[1] + " logged out!\n");
					}
					
				}
				if (s[0].equals("public")) {
					bulletinBoard.append("[" + s[1] + "]: " + s[2] + "\n");
				}
				if (s[0].equals("private")) {
					if (s[2].equals(userNickName)) {
						whisperBoard.append("[" + s[1] + " to me]: " + s[3] + "\n");	
					}
					if(s[1].equals(userNickName)) {
						whisperBoard.append("[me to " + s[2] + "]: " + s[3] + "\n");
					}
				}
				if (s[0].equals("server stopped")) {
					bulletinBoard.append("[SYSTEM]: Server stopped!\n");
					bulletinBoard.append("[SYSTEM]: You can either log out or wait until the server restart!\n");
					btnSendMsg.setEnabled(false);
				}
				if (s[0].equals("server quit")) {
					bulletinBoard.append("[SYSTEM]: Server quit forever!\n");
					bulletinBoard.append("[SYSTEM]: Please close the window and choose a new server with your friends!\n");
					btnLogout.setEnabled(false);
					btnSendMsg.setEnabled(false);
					break;
				}
				if (s[0].equals("server restarted")) {
					bulletinBoard.append("[SYSTEM]: Server restarted!\n");
					btnSendMsg.setEnabled(true);
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (cc.socket != null) {
				try {
					cc.socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


	public DefaultComboBoxModel getUsrCombo() {
		return usrCombo;
	}

	public void setUsrCombo(DefaultComboBoxModel usrCombo) {
		this.usrCombo = usrCombo;
	}
}
