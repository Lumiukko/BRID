package net.brainscorch.BRID.Server;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

public class ImageData {
	private static final String	IMAGE_FOLDER = "./img/";
	
	private String		hashString;
	private String		originalName;
	private Dimension	dimensions;
	private Integer		databaseID;
	private String		description;
	private File		imageFile;
	
	ImageData(String hashString) throws FileNotFoundException {
		this.hashString = hashString.toLowerCase();
		imageFile = new File(IMAGE_FOLDER + this.getHashString() + ".jpg");
		if (!imageFile.exists())
			throw new FileNotFoundException(IMAGE_FOLDER + this.getHashString() + ".jpg");
	}
	
	ImageData(String hashString, Integer imageID, String description, String originalName, Integer width, Integer height) throws FileNotFoundException {
		this.hashString = hashString.toLowerCase();
		this.databaseID = imageID;
		this.description = description;
		this.originalName = originalName;
		this.dimensions = new Dimension(width, height);		
	}
	
	public static String getImageFolder() {
		return IMAGE_FOLDER;
	}
	
	@Override
	public String toString() {
		return String.format("%8d - %24s - %48s - %24s - %dx%d", 
			databaseID,
			hashString,
			description,
			originalName,
			(int)dimensions.getWidth(),
			(int)dimensions.getHeight());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ImageData other = (ImageData) obj;
		if (!Objects.equals(this.hashString, other.hashString)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 47 * hash + Objects.hashCode(this.getHashString());
		return hash;
	}

	/**
	 * @return the originalName
	 */
	public String getOriginalName() {
		return originalName;
	}

	/**
	 * @param originalName the originalName to set
	 */
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	/**
	 * @return the dimensions
	 */
	public Dimension getDimensions() {
		return dimensions;
	}

	/**
	 * @param dimensions the dimensions to set
	 */
	public void setDimensions(Dimension dimensions) {
		this.dimensions = dimensions;
	}

	/**
	 * @return the databaseID
	 */
	public Integer getDatabaseID() {
		return databaseID;
	}

	/**
	 * @param databaseID the databaseID to set
	 */
	public void setDatabaseID(Integer databaseID) {
		this.databaseID = databaseID;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the hashString
	 */
	public String getHashString() {
		return hashString;
	}

}
