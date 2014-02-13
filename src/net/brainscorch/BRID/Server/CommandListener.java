package net.brainscorch.BRID.Server;

import java.awt.Dimension;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandListener extends Thread {
	private final int	MESSAGE_SIZE = 8;
	
	private final String	STATUS_REQUEST = "#STATUS#";
	private final String	DATABASE_DUMP = "#DBDUMP#";
	
	private final int	SERVER_PORT = 12021;
	
	private Socket		dataSocket;
	private ServerSocket	listenSocket;
	
	private DisplayInformation dInfo;
	
	CommandListener(DisplayInformation dInfo) {
		this.dInfo = dInfo;
	}
	
	
	@Override
	public void run() {
		try {
			listenSocket = new ServerSocket(SERVER_PORT);
			System.out.printf("CommandListener listening on port %s.\n", SERVER_PORT);
			while (true) {
				dataSocket = listenSocket.accept();
				System.out.printf("Accepted connection.\n");
				ObjectInputStream ois = new ObjectInputStream(dataSocket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(dataSocket.getOutputStream());
				System.out.printf("Got I/O Streams.\n");
				
				String message = (String)ois.readObject();
			
			
				System.out.printf("Got a Message: %s\n", message);
				switch (message) {
					case STATUS_REQUEST:
						String remoteAddr = stripAddress(dataSocket.getRemoteSocketAddress().toString());
						System.out.printf("CommandListener received STATUS request from %s.\n", remoteAddr);
						dInfo.refresh();
						Dimension dimension = dInfo.getScreenDimension();
						// add here more status information
						String outMessage = String.format("SD%04d%04d", (int)dimension.getWidth(), (int)dimension.getHeight());
						System.out.printf("StatusSender: Sending STATUS reply to %s: %s\n", remoteAddr, outMessage);
						oos.writeObject(outMessage);
						
						break;
					case DATABASE_DUMP:
						System.out.println(DBUtility.getImageMapFromDB().toString());
						oos.writeObject("Done.");
						break;
					default:
						System.err.printf("CommandListener received malformed message from %s. IGNORED. Message: %s\n", dataSocket.getRemoteSocketAddress().toString(), message);
						break;
				}
				
				oos.close();
				ois.close();
			}
			
		}
		catch (Exception e) {
			System.err.println(e.toString());
			e.printStackTrace();
		}
	}
	
	private String stripAddress(String addr) {
		String temp[] = addr.split(":");
		String ret = temp[0];
		return ret.substring(1);
	}
}
