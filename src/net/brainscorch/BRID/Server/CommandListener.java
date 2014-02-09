package net.brainscorch.BRID.Server;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandListener extends Thread {
	private final int	MESSAGE_SIZE = 8;
	
	private final String	STATUS_REQUEST = "#STATUS#";
	
	private final int	SERVER_PORT = 12021;
	
	private Socket		dataSocket;
	private ServerSocket	listenSocket;
	private InputStream	inStream;
	
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
				inStream = dataSocket.getInputStream();
				
				int i = inStream.read();
				String message = new String();
				while (message.length() < MESSAGE_SIZE) {
					message += (char)i;
					i = inStream.read();
				}
				
				if (message.equals(STATUS_REQUEST)) {
					String remoteAddr = stripAddress(dataSocket.getRemoteSocketAddress().toString());
	
					System.out.printf("CommandListener received STATUS request from %s.\n", remoteAddr);			
					dInfo.refresh();
					
					StatusSender sSend = new StatusSender(remoteAddr, dInfo);
					sSend.start();
					
				}
				else {
					System.err.printf("CommandListener received malformed message from %s. IGNORED. Message: %s\n", dataSocket.getRemoteSocketAddress().toString(), message);
				}
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
