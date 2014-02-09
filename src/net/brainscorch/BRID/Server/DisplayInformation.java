package net.brainscorch.BRID.Server;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;


public class DisplayInformation {
	private Dimension screenDimension;
	
	DisplayInformation() {
		screenDimension = new Dimension();
		refresh();
	}
	
	public void refresh() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		screenDimension.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
		System.out.printf("Renewed: %s\n", this.toString());
	}

	@Override
	public String toString() {
		return String.format("DisplayInformation: %4dx%4d", (int)screenDimension.getWidth(), (int)screenDimension.getHeight());
	}
	
	/**
	 * @return the screenDimension
	 */
	public Dimension getScreenDimension() {
		return screenDimension;
	}
}
