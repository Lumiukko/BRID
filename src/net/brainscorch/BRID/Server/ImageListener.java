package net.brainscorch.BRID.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ImageListener extends Thread {
	final private int SERVER_PORT = 12023;
	final private String IMAGE_FILE = "./testfile.jpg";
	final private int BUFFER_SIZE = 16384;
	
	private DisplayInformation dInfo;
	private BRIDServer bServer;
	
	ImageListener(DisplayInformation dInfo, BRIDServer bServer) {
		this.dInfo = dInfo;
		this.bServer = bServer;
	}
	
	@Override
	public void run() {
		try {
			ServerSocket servsock = new ServerSocket(SERVER_PORT);
			System.out.printf("ImageListener listening on port %s.\n", SERVER_PORT);
			File myFile = new File(IMAGE_FILE);
			FileOutputStream fos = null;
			BufferedInputStream bis = null;
			while (true) {
				Socket sock = servsock.accept();
				try {
					myFile.createNewFile();
					byte[] mybytearray = new byte[BUFFER_SIZE];
					fos = new FileOutputStream(myFile);
					bis = new BufferedInputStream(sock.getInputStream());
					int count = 0;
					while ((count = bis.read(mybytearray)) >= 0) {
						//bis.read(mybytearray, 0, count);
						fos.write(mybytearray, 0, count);
					}
				} catch(IOException e) {
					System.err.printf("Error: %s\n", e.getMessage());
				} finally {
					fos.close();
					bis.close();
					sock.close();
					dInfo.setStrImageFile(IMAGE_FILE);
					bServer.loadNewImage();
					System.out.printf("File Received.\n");
				}
			}
		}
		catch (IOException e) {
			System.err.printf("Error: %s\n", e.getMessage());
			try {
				this.join();
			} catch (InterruptedException e2) {
				System.err.printf("Error: %s\n", e2.getMessage());
			}
		}
	}
}
