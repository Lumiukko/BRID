package net.brainscorch.BRID.Server;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class StatusSender extends Thread {
	private final int TIMEOUT = 200;
	
	private String	strClientAddress;
	private Integer	intClientPort;
	
	StatusSender(String clientAddress) {
		intClientPort = 12022;
		strClientAddress = clientAddress;
	}
	
	public boolean sendMessageToClient(String message) {
		InetAddress clientAddr;
		try {
			clientAddr = InetAddress.getByName(getStrClientAddress());
			Socket sendSocket = new Socket();
			sendSocket.connect(new InetSocketAddress(clientAddr, getIntClientPort()), TIMEOUT);
			OutputStream outStream = sendSocket.getOutputStream();
			outStream.write(message.getBytes());
			sendSocket.close();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public void run() {
		
	}
	
	
	/**
	 * @return the strServerAddress
	 */
	public String getStrClientAddress() {
		return strClientAddress;
	}

	/**
	 * @param strServerAddress the strServerAddress to set
	 */
	public void setStrClientAddress(String strClientAddress) {
		this.strClientAddress = strClientAddress;
	}

	/**
	 * @return the intServerPort
	 */
	public Integer getIntClientPort() {
		return intClientPort;
	}

	/**
	 * @param intServerPort the intServerPort to set
	 */
	public void setIntClientPort(Integer intClientPort) {
		this.intClientPort = intClientPort;
	}
}
