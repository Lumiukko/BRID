package net.brainscorch.BRID.Client;

import java.awt.Dimension;


public class ServerInformation {
	private Dimension screenDimension;

	
	ServerInformation() {
		screenDimension = new Dimension();
	}

	
	/**
	 * @param aScreenDimension the screenDimension to set
	 */
	public void setScreenDimension(Dimension aScreenDimension) {
		screenDimension = aScreenDimension;
	}
	
	/**
	 * @return the screenDimension
	 */
	public Dimension getScreenDimension() {
		return screenDimension;
	}
}
