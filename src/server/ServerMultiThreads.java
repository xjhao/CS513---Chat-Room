package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Server socket thread for a connected client
 * @author xhao
 *
 */
public class ServerMultiThreads extends Thread{
	private Socket userSocket;
	private DataOutputStream dout;
	private DataInputStream din;
	ArrayList<Socket> socketThreadList; // Record all the server socket threads
	
	public ServerMultiThreads(Socket userSocket, ArrayList<Socket> socketThreadList) {
		this.userSocket = userSocket;
		try {
			dout = new DataOutputStream(this.userSocket.getOutputStream());
			din = new DataInputStream(this.userSocket.getInputStream());
			this.socketThreadList = socketThreadList;
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * listen to the request sent from client side
	 */
	@Override
	public void run() {
		try {
			while(true){
				String dataIn = din.readUTF();
				String parseData[] = dataIn.split("##");
				if (parseData[0].equals("client login")) {
					// Login failed because of duplicate nickname 
					if (ClientMap.getInstance().containsKey(parseData[1])) {
						dout.writeUTF("duplicate nickname");
						break;
					} else {
						// Confirm the new client login
						dout.writeUTF("login confirm");
						// Create the new client information
						ClientInfo newUsr = new ClientInfo(parseData[1], userSocket);
						// Inform the new client all the online clients
						new UpdateClients().informAllToOne(userSocket);
						// Add the new client to client map
						ClientMap.getInstance().put(parseData[1], newUsr);
						// Inform all the online clients the new client has logged in
						new UpdateClients().informOneToAll(newUsr);
					}
					
				} 
				if (parseData[0].equals("server quit")) {
					dout.writeUTF("server quit");
					break;
				} 
				if (parseData[0].equals("client logout")) {
					ClientInfo user = ClientMap.getInstance().get(parseData[1]);
	
					// Inform all the other clients that this user logged out.
					// More specifically, update their online users list
					new UpdateClients().informClientLogout(user);
					
					// Update the user map
					ClientMap.getInstance().remove(parseData[1]);
					
					System.out.println("Client [" + parseData[1] + "] logged out!");
					break;
				} 
				if (parseData[0].equals("message")){
					String senderNickName = parseData[1];
					String receiverNickName = parseData[2];
					String msg = parseData[3];
					TransmitMessages tm = new TransmitMessages();
					if (receiverNickName.equals("All")) {
						tm.sendMsgPublic(senderNickName, msg);
					} else {
						tm.sendMsgPrivate(senderNickName, receiverNickName, msg);
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the socket of the user and return
			if (this.userSocket != null) {
				try {
					this.userSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// Update the arrayList of socket threads
			socketThreadList.remove(userSocket);
		}
		
	}

	public DataOutputStream getDout() {
		return dout;
	}

	public void setDout(DataOutputStream dout) {
		this.dout = dout;
	}
}
