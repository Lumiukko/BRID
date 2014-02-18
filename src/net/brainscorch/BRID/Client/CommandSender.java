package net.brainscorch.BRID.Client;

import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import net.brainscorch.BRID.ImageData;
import net.brainscorch.BRID.ImageMap;

public class CommandSender {
	private final int TIMEOUT = 200;

	private String	strServerAddress;
	private Integer	intServerPort;
	private BRIDClient bridClient;
	private ServerInformation sInfo;
	
	
	public CommandSender(BRIDClient bridClient) {
		this.bridClient = bridClient;
		this.sInfo = new ServerInformation();
		strServerAddress = "localhost";
		intServerPort = 12021;
	}
	
	public boolean sendMessageToServer(String message) {
		InetAddress serverAddr;
		try {
			serverAddr = InetAddress.getByName(getStrServerAddress());
			Socket sendSocket = new Socket();
			sendSocket.connect(new InetSocketAddress(serverAddr, getIntServerPort()), TIMEOUT);
			ObjectOutputStream oos = new ObjectOutputStream(sendSocket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(sendSocket.getInputStream());
			
			oos.writeObject(message);
			MessageLogger.log(String.format("Request sent to: %s\n", serverAddr.getHostName()));
						
			Object response = ois.readObject();
			MessageLogger.log(String.format("Response received from: %s\n", serverAddr.getHostName()));

			if (response instanceof ImageMap) {
				MessageLogger.log(" - Response is IMAGE LIST reply (see Table).\n");
				ImageMap iMapResponse = (ImageMap) response;
				System.out.println(iMapResponse.toString());
				
				List<ImageData> iDataList = iMapResponse.getImageDataList();
				for (ImageData iData : iDataList) {
					bridClient.addImageToTable(iData);
				}
			}
			else if (response instanceof String) {
				String strResponse = (String) response;
			
				if (strResponse.startsWith("SD")) {
					MessageLogger.log(String.format(" - Response is STATUS reply: %s\n", strResponse));
					Integer sWidth = Integer.valueOf(strResponse.substring(2, 6));
					Integer sHeight = Integer.valueOf(strResponse.substring(6, 10));
					Dimension dim = new Dimension(sWidth, sHeight);
					sInfo.setScreenDimension(dim);		// set in Model
					bridClient.setScreenDimension(dim);	// set in View
				}
			}
			
			oos.close();
			ois.close();
			sendSocket.close();
			return true;
		}
		catch (UnknownHostException e) {
			MessageLogger.log(String.format("Error: Unknown Host: \"%s\"\n", e.getMessage()));
			return false;
		}
		catch (IOException e) {
			MessageLogger.log(String.format("Error: I/O Failure: \"%s\"\n", e.getMessage()));
			e.printStackTrace();
			return false;
		}
		catch (Exception e) {
			MessageLogger.log(String.format("Error: \"%s\"\n", e.getMessage()));
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
