package net.brainscorch.BRID.Client;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;

public class CommandSender {
	private final int TIMEOUT = 200;

	private String	strServerAddress;
	private Integer	intServerPort;
	
	public CommandSender() {
		strServerAddress = "localhost";
		intServerPort = 12021;
	}
	
	public boolean sendMessageToServer(String message) {
		InetAddress serverAddr;
		try {
			serverAddr = InetAddress.getByName(getStrServerAddress());
			Socket sendSocket = new Socket();
			sendSocket.connect(new InetSocketAddress(serverAddr, getIntServerPort()), TIMEOUT);
			OutputStream outStream = sendSocket.getOutputStream();
			outStream.write(message.getBytes());
			sendSocket.close();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * @return the strServerAddress
	 */
	public String getStrServerAddress() {
		return strServerAddress;
	}

	/**
	 * @param strServerAddress the strServerAddress to set
	 */
	public void setStrServerAddress(String strServerAddress) {
		this.strServerAddress = strServerAddress;
	}

	/**
	 * @return the intServerPort
	 */
	public Integer getIntServerPort() {
		return intServerPort;
	}

	/**
	 * @param intServerPort the intServerPort to set
	 */
	public void setIntServerPort(Integer intServerPort) {
		this.intServerPort = intServerPort;
	}
	
}
