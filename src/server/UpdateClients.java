package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Update the online clients in each client's view
 * @author xhao
 *
 */
public class UpdateClients {
	private DataOutputStream dout;
	
	/**
	 *  Add all the online clients to the new client's clientView
	 */
	public void informAllToOne(Socket socket) {
		for (ClientInfo u : ClientMap.getInstance().values()) {
			try {
				dout = new DataOutputStream(socket.getOutputStream());
				dout.writeUTF("create list##" + u.getNickname());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Add the new client to all the online clients' clientView
	 * @param newClient
	 */
	public void informOneToAll(ClientInfo newClient) {
		for(ClientInfo u : ClientMap.getInstance().values()) {
			Socket socket = u.getSocket();
			try {
				dout = new DataOutputStream(socket.getOutputStream());
				dout.writeUTF("add one##" + newClient.getNickname());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//
	public void informClientLogout(ClientInfo client) {
		for(ClientInfo u : ClientMap.getInstance().values()) {
			Socket socket = u.getSocket();
			try {
				dout = new DataOutputStream(socket.getOutputStream());
				dout.writeUTF("remove one##" + client.getNickname());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
