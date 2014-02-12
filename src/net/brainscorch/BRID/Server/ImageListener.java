package net.brainscorch.BRID.Server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ImageListener extends Thread {
	final private int SERVER_PORT = 12023;
	final private String IMAGE_FILE = ImageData.getImageFolder() + "___tmp";
	final private int BUFFER_SIZE = 4096;
	
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
				System.out.printf("Image send request from %s accepted.\n", sock.getRemoteSocketAddress().toString());
				try {
					myFile.createNewFile();
					byte[] mybytearray = new byte[BUFFER_SIZE];
					fos = new FileOutputStream(myFile);
					bis = new BufferedInputStream(sock.getInputStream());
					int count = 0;
					while ((count = bis.read(mybytearray)) >= 0) {
						fos.write(mybytearray, 0, count);
					}
					dInfo.setStrImageFile(IMAGE_FILE);
					bServer.loadNewImage();
					System.out.printf("File Received.\n");
					
					// Temporary, since the description is not yet transmitted
					DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
					Calendar cal = Calendar.getInstance();
					String description = String.format("No description. Added: %s", dFormat.format(cal.getTime()));
					String originalName = "[unknown].jpg";
					
					DBUtility.persistImage(IMAGE_FILE, description, originalName);
					
				} catch(IOException e) {
					System.err.printf("Error: %s\n", e.getMessage());
				} finally {
					fos.close();
					bis.close();
					sock.close();
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
