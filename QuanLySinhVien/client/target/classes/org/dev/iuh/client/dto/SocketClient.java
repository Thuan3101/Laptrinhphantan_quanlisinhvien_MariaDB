package org.dev.iuh.client.dto;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient {
	private Socket socket;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	
	public SocketClient() {
		
	}
	
	public SocketClient(Socket socket, ObjectInputStream fromServer, ObjectOutputStream toServer) {
		this.socket = socket;
		this.fromServer = fromServer;
		this.toServer = toServer;
	}
	
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public ObjectInputStream getFromServer() {
		return fromServer;
	}
	public void setFromServer(ObjectInputStream fromServer) {
		this.fromServer = fromServer;
	}
	public ObjectOutputStream getToServer() {
		return toServer;
	}
	public void setToServer(ObjectOutputStream toServer) {
		this.toServer = toServer;
	} 
	
}
