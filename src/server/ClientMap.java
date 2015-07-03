package server;

import java.util.HashMap;

/**
 * The hash map of all the online clients
 * @author xhao
 *
 */
public class ClientMap {
	private static HashMap<String, ClientInfo> clientMap; 
	
	public static synchronized HashMap<String, ClientInfo> getInstance() {
		if (clientMap == null) {
			clientMap = new HashMap<String, ClientInfo>();
		}
		return clientMap;
	}
}
