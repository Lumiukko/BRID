package net.brainscorch.BRID.Client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;


public class ImageSender {
	private final int TIMEOUT = 200;

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
			Socket socket = new Socket(strServerAddress, intServerPort);
			socket.setSoTimeout(TIMEOUT);
			BufferedOutputStream outStream = new BufferedOutputStream(socket.getOutputStream());
			BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(f));
			byte[] buffer = new byte[4096];
			for (int read = inStream.read(buffer); read >= 0; read = inStream.read(buffer)) {
				outStream.write(buffer, 0, read);
			}
			inStream.close();
			outStream.close();
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
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
