package net.brainscorch.BRID.Server;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import javax.imageio.ImageIO;

public class DBUtility {
	private final static String DATABASE_NAME = "images2.db";
	private final static String TABLE_NAME = "images";
	
	private static Connection con = null;
	
	
	/*
	public static void main( String args[] )
	{
		connect();
		if (checkIfTableExists()) System.println("Table found.");
		disconnect();
	}
	*/
	
	public static boolean persistImage(String filePath, String description, String originalName) {
		String hash = getHash(filePath);
		if (hash == null) {
			System.err.printf("[persistImage] Could not persist file: %s\n", filePath);
			return false;
		}
		
		File imageFile = new File(ImageData.getImageFolder() + hash + ".jpg");
		File tmpImageFile = new File(filePath);
		
		if (imageFile.exists())
			System.out.printf("[persistImage] Image file already exists: %s\n", imageFile.getAbsolutePath());
		else {
			try {
				Files.copy(tmpImageFile.toPath(), imageFile.toPath());
			}
			catch (IOException e) {
				System.err.printf("[persistImage] Could not copy file: [%s] to [%s] -- %s\n", tmpImageFile.getAbsolutePath(), imageFile.getAbsolutePath(), e.getMessage());
			}
		}
			
		
		
		return saveImageDataToDB(hash, description, originalName);
	}
	
	private static boolean saveImageDataToDB(String hashString, String description, String originalName) {
		File imageFile = new File(ImageData.getImageFolder() + hashString + ".jpg");
		try {
			BufferedImage bImage = ImageIO.read(imageFile);
			int iWidth = bImage.getWidth();
			int iHeight = bImage.getHeight();
			
			if (hashExists(hashString)) return false;
			connect();
			Statement st = con.createStatement();
			String sql = String.format(
				"INSERT INTO images"
				+ "(hash, description, originalName, width, height)"
				+ "VALUES ('%s', '%s', '%s', %d, %d);",
				hashString,
				description,
				originalName,
				iWidth,
				iHeight);
			st.executeUpdate(sql);
			st.close();
			return true;
		}
		catch (IOException e) {
			System.err.printf("[saveImageDataToDB] Could not read file: %s -- %s\n", imageFile.getAbsolutePath(), e.getMessage());
		}
		catch (SQLException e) {
			System.err.printf("[saveImageDataToDB] Database Error: %s\n", e.getMessage());
		}
		finally { disconnect(); }
		return false;
	}
	
	private static boolean hashExists(String hash) {
		try {
			connect();
			Statement st = con.createStatement();
			String sql = String.format("SELECT * FROM %s WHERE hash='%s';", TABLE_NAME, hash);
			ResultSet res = st.executeQuery(sql);
			while (res.next()) {
				System.out.printf("Hash value found: hash: %s, imageID: %s, desc: %s, origName: %s, w: %s, h: %s\n",
					hash,
					res.getInt("imageID"),
					res.getString("description"),
					res.getString("originalName"),
					res.getInt("width"),
					res.getInt("height"));
				return true;
			}
			st.close();
		}
		catch (SQLException e) {
			System.err.printf("[hashExists] Database Error: %s\n", e.getMessage());
		}
		finally { disconnect(); }
		return false;
	}
	
	private static String getHash(String filePath) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			FileInputStream fis = new FileInputStream(filePath);
			byte[] dataBytes = new byte[4096];
			int nread;
			while ((nread = fis.read(dataBytes)) >= 0) {
				md.update(dataBytes, 0, nread);
			}
			byte[] mdbytes = md.digest();
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toHexString(0xff & mdbytes[i]));
			}
			return sb.toString();
		}
		catch (NoSuchAlgorithmException e) {
			System.err.printf("[getHash] Could not find SHA-256 Hashing Alrogithm: %s\n", e.getMessage());
		}
		catch (FileNotFoundException e) {
			System.err.printf("[getHash] Could not find file: %s\n", e.getMessage());
		}
		catch (IOException e) {
			System.err.printf("[getHash] Could not read file: %s -- %s\n", filePath, e.getMessage());
		}
		System.err.printf("[getHash] Could not create file hash, for whatever reason.\n");
		return null;
	}
	
	public static ImageMap getImageMapFromDB() {
		ImageMap iMap = new ImageMap();
		try {
			connect();
			Statement st = con.createStatement();
			String sql = String.format("SELECT * FROM %s;", TABLE_NAME);
			ResultSet res = st.executeQuery(sql);			
			
			while (res.next()) {
				try {
					
					ImageData iData = new ImageData(
						res.getString("hash"),
						res.getInt("imageID"),
						res.getString("description"),
						res.getString("originalName"),
						res.getInt("width"),
						res.getInt("height"));
					iMap.addImage(iData);
					
				}
				catch(FileNotFoundException e) {
					System.err.printf("[getImageMapFromDB] Could not find file: %s\n", e.getMessage());
				}
			}
			res.close();
			st.close();
		}
		catch (SQLException e) {
			System.err.printf("[getImageMapFromDB] Database Error: %s\n", e.getMessage());
			
		}
		finally { disconnect(); }
		return iMap;
	}
	
	
	
		
	private static void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME);
			//System.out.println("BRID Server: Opened database.");
			checkIfTableExists();
		}
		catch (ClassNotFoundException e) {
			System.err.printf("[connect] Error: Class not found: %s\n", e.getMessage());
		}
		catch (SQLException e) {
			System.err.printf("[connect] Database Error: %s\n", e.getMessage());
		}
	}
	
	private static boolean checkIfTableExists() {
		try {
			Statement st = con.createStatement();
			String sql = String.format(
				"SELECT name"
				+ " FROM sqlite_master"
				+ " WHERE type='table'"
				+ " AND name='%s';", TABLE_NAME);
			
			ResultSet res = st.executeQuery(sql);
			while (res.next()) {
				String tableName = res.getString("name");
				if (tableName.equals(TABLE_NAME))
					return true;
			}
			res.close();
			st.close();
		}
		catch (SQLException e) {
			System.err.printf("[checkIfTableExists] Database Error: %s\n", e.getMessage());
			
		}
		return false;
	}
	
	private static void disconnect() {
		try {
			con.close();
		}
		catch (SQLException e) {
			System.err.printf("[disconnect] Database Error: %s\n", e.getMessage());
		}
	}
}
