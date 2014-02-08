package net.brainscorch.BRID.Client;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class StatusListener extends Thread {
	private final int	MESSAGE_SIZE = 8;
	private final int	SERVER_PORT = 12022;
	
	private Socket		dataSocket;
	private ServerSocket	listenSocket;
	private InputStream	inStream;
	
	
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
				
				if (message.startsWith("SREPLY#")) {
					System.out.printf("StatusListener received STATUS reply from %s.\n", dataSocket.getRemoteSocketAddress().toString());
					// Create StatusSender Thread and send status to the client.
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
