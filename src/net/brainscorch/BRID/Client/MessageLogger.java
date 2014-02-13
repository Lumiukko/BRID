package net.brainscorch.BRID.Client;

import javax.swing.JTextArea;

public class MessageLogger {
	
	private static Thread logThread;
	private static JTextArea jta;
	private static String logMessage;
		
	public static void setTextArea(JTextArea jTextArea) {
		MessageLogger.jta = jTextArea;
	}
	
	public static void log(String logMessage) {
		String currentText = jta.getText();
		currentText += logMessage;
		if (!logMessage.endsWith("\n")) currentText += "\n";
		jta.setText(currentText);
		System.out.print(logMessage);
	}
	
	public static void logSingle(String logMessage) {
		String currentText = jta.getText();
		currentText += logMessage;
		jta.setText(currentText);
		System.out.print(logMessage);
	}
	
}
