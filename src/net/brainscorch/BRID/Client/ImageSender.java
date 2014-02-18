package net.brainscorch.BRID.Client;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import javax.imageio.ImageIO;


public class ImageSender {
	private final int TIMEOUT = 200;
	final private int BUFFER_SIZE = 4096;

	private String	strServerAddress;
	private Integer intServerPort;
	
	private File	imageFile;
	
	ImageSender() {
		strServerAddress = "localhost";
		intServerPort = 12023;
	}
	
	public void sendImageToServer() {
		sendImageToServer(imageFile);
	}
	
	public void sendImageToServer(File f) {
		try {
			MessageLogger.log("Sending image to server...\n");
			
			Socket socket = new Socket(strServerAddress, intServerPort);
			socket.setSoTimeout(TIMEOUT);
			BufferedOutputStream outStream = new BufferedOutputStream(socket.getOutputStream());
			BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(f));
			byte[] buffer = new byte[BUFFER_SIZE];
			int partsTenPercent = (int)(f.length() / BUFFER_SIZE / 10);
			int partsFinished = 0;
			MessageLogger.logSingle(" - Sent:  ");
			for (int read = inStream.read(buffer); read >= 0; read = inStream.read(buffer)) {
				outStream.write(buffer, 0, read);
				partsFinished++;
				if (partsFinished % partsTenPercent == 0)
					MessageLogger.logSingle(String.format("%d", (int)(partsFinished / partsTenPercent * 10)) + "%  ");
			}
			MessageLogger.log("");
			MessageLogger.log("Image has been sent successfully.\n");
			inStream.close();
			outStream.close();
		}
		catch (Exception e) {
			MessageLogger.log(String.format("Error: %s\n", e.getMessage()));
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

	/**
	 * @return the imageFile
	 */
	public File getImageFile() {
		return imageFile;
	}

	/**
	 * @param imageFile the imageFile to set
	 */
	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}
	
}
