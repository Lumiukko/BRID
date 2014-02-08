package net.brainscorch.BRID;

import net.brainscorch.BRID.Client.BRIDClient;
import net.brainscorch.BRID.Server.BRIDServer;


public class BRIDStart {
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 0) {
			if (args[0].equals("-s") || (args[0].equals("--server")))
				BRIDServer.main(args);
		}
		else {
			BRIDClient.main(args);
		}
	}
}
