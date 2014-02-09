package net.brainscorch.BRID.Client;

import java.awt.Dimension;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class StatusListener extends Thread {
	private final int	MESSAGE_SIZE = 10;
	private final int	SERVER_PORT = 12022;
	
	private Socket		dataSocket;
	private ServerSocket	listenSocket;
	private InputStream	inStream;
	
	private ServerInformation sInfo;
	private BRIDClient bridClient;
	
	StatusListener(BRIDClient bridClient) {
		this.sInfo = new ServerInformation();
		this.bridClient = bridClient;
	}
	
	
	@Override
	public void run() {
		try {
			listenSocket = new ServerSocket(SERVER_PORT);
			System.out.printf("StatusListener listening on port %s.\n", SERVER_PORT);
			while (true) {
				dataSocket = listenSocket.accept();
				inStream = dataSocket.getInputStream();
				
				int i = inStream.read();
				String message = new String();
				while (message.length() < MESSAGE_SIZE) {
					message += (char)i;
					i = inStream.read();
				}
				
				if (message.startsWith("SD")) {
					System.out.printf("StatusListener received STATUS reply from %s: %s\n", dataSocket.getRemoteSocketAddress().toString(), message);
					Integer sWidth = Integer.valueOf(message.substring(2, 6));
					Integer sHeight = Integer.valueOf(message.substring(6, 10));
					Dimension dim = new Dimension(sWidth, sHeight);
					sInfo.setScreenDimension(dim);		// set in Model
					bridClient.setScreenDimension(dim);	// set in View
				}
				else {
					System.err.printf("StatusListener received malformed message from %s. IGNORED. Message: %s\n", dataSocket.getRemoteSocketAddress().toString(), message);
				}
			}
			
		}
		catch (Exception e) {
			System.err.println(e.toString());
			e.printStackTrace();
		}
	}
}
