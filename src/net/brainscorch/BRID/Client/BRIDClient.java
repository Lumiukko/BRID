package net.brainscorch.BRID.Client;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import net.brainscorch.BRID.ImageData;

public class BRIDClient extends JFrame {
	
	private final String	STATUS_REQUEST = "#STATUS#";
	private final String	DATABASE_DUMP = "#DBDUMP#";
	private final String	IMAGE_LIST_REQUEST = "#RILIST#";
	
	private CommandSender	cSend;
	private ImageSender	iSend;
	private ServerInformation sInfo;
	
	/**
	 * Creates new form BRIDClient
	 */
	public BRIDClient() {
		super("BRID Client");
		initComponents();
		
		sInfo = new ServerInformation();
		
		cSend = new CommandSender(this);
		iSend = new ImageSender();
		
		MessageLogger.setTextArea(jTextAreaLog);
		
		jTextFieldServerAddress.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) { onChange(); }
			@Override
			public void removeUpdate(DocumentEvent e) { onChange(); }
			@Override
			public void insertUpdate(DocumentEvent e) { onChange(); }
			
			public void onChange() {
				cSend.setStrServerAddress(jTextFieldServerAddress.getText());
				iSend.setStrServerAddress(jTextFieldServerAddress.getText());
			}
		});
	}
	
	
	
	public void setScreenDimension(Dimension d) {
		String dimensionText = String.format("%dx%d", (int)d.getWidth(), (int)d.getHeight());
		jLabelDimensions.setText(dimensionText);
		Integer ratio = gcd((int)d.getWidth(), (int)d.getHeight());
		String aspectRatio = String.format("(%d:%d)", (int)(d.getWidth()/ratio), (int)(d.getHeight()/ratio));
		jLabelDimensionsRatio.setText(aspectRatio);
	}
	
	public void addImageToTable(ImageData iData) {
		DefaultTableModel dtm = (DefaultTableModel) jTableServerContents.getModel();
		Object[] rowData = {
			iData.getDatabaseID(),
			iData.getDescription(),
			iData.getOriginalName(),
			(int)iData.getDimensions().getWidth() + "x" + (int)iData.getDimensions().getHeight(),
			"?",
			iData.getHashString()
		};
		dtm.addRow(rowData);	
	}
	
	private int gcd(int a, int b) { return b==0 ? a : gcd(b, a%b); }

	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextFieldServerAddress = new javax.swing.JTextField();
        jButtonConnect = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabelDimensions = new javax.swing.JLabel();
        jLabelDimensionsRatio = new javax.swing.JLabel();
        jButtonSelectImage = new javax.swing.JButton();
        jLabelImageSelection = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonSendImage = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaLog = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableServerContents = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldDescription = new javax.swing.JTextField();
        jLabelFilename = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabelDimensionsUpload = new javax.swing.JLabel();
        jLabelAspectRatioUpload = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabelFilesize = new javax.swing.JLabel();
        jButtonGetImageList = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Server Address:");

        jTextFieldServerAddress.setText("localhost");
        jTextFieldServerAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldServerAddressActionPerformed(evt);
            }
        });

        jButtonConnect.setText("Get Info");
        jButtonConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConnectActionPerformed(evt);
            }
        });

        jLabel6.setText("Dimensions:");

        jLabelDimensions.setText("-");

        jLabelDimensionsRatio.setText("(-)");

        jButtonSelectImage.setText("Select Image");
        jButtonSelectImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectImageActionPerformed(evt);
            }
        });

        jLabelImageSelection.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelImageSelection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/brainscorch/BRID/Client/img/nopic.png"))); // NOI18N
        jLabelImageSelection.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setText("Upload:");

        jButtonSendImage.setText("Upload Image");
        jButtonSendImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendImageActionPerformed(evt);
            }
        });

        jTextAreaLog.setColumns(20);
        jTextAreaLog.setEditable(false);
        jTextAreaLog.setRows(5);
        jScrollPane1.setViewportView(jTextAreaLog);

        jLabel3.setText("MessageLog:");

        jTableServerContents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Description", "Name", "Dimensions", "Size", "Hash"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableServerContents);

        jLabel4.setText("Description:");

        jLabel5.setText("Filename:");

        jLabelFilename.setText("-");

        jLabel7.setText("Dimensions:");

        jLabelDimensionsUpload.setText("-");

        jLabelAspectRatioUpload.setText("(-)");

        jLabel8.setText("Filesize:");

        jLabelFilesize.setText("-");

        jButtonGetImageList.setText("Get Image List");
        jButtonGetImageList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGetImageListActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonSelectImage, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSendImage, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldServerAddress))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(31, 31, 31)
                                .addComponent(jLabelDimensions)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelDimensionsRatio)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonConnect, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                            .addComponent(jButtonGetImageList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelImageSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldDescription))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelFilename, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelFilesize)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabelDimensionsUpload)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabelAspectRatioUpload)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldServerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonConnect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabelDimensions)
                    .addComponent(jLabelDimensionsRatio)
                    .addComponent(jButtonGetImageList))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelImageSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonSelectImage)
                            .addComponent(jButtonSendImage)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabelFilename))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabelDimensionsUpload)
                            .addComponent(jLabelAspectRatioUpload))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabelFilesize))))
                .addGap(20, 20, 20)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
	
	private void jTextFieldServerAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldServerAddressActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_jTextFieldServerAddressActionPerformed

	private void jButtonConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConnectActionPerformed
		// TODO add your handling code here:
		cSend.sendMessageToServer(STATUS_REQUEST);
	}//GEN-LAST:event_jButtonConnectActionPerformed

	private void jButtonSelectImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectImageActionPerformed
		// TODO add your handling code here:
		JFileChooser fChooser = new JFileChooser();
		FileNameExtensionFilter fFilter = new FileNameExtensionFilter("JPG Images", "jpg");
		fChooser.setFileFilter(fFilter);
		int fReturnVal = fChooser.showOpenDialog(this);
		if (fReturnVal == JFileChooser.APPROVE_OPTION) {
			MessageLogger.log(String.format("Selected Image: %s\n", fChooser.getSelectedFile().getAbsolutePath()));
			iSend.setImageFile(fChooser.getSelectedFile());
			try {
				BufferedImage bImage = ImageIO.read(iSend.getImageFile());
				float aspectRatio = bImage.getWidth() / bImage.getHeight();
				Image sImage;
				if (aspectRatio >= (16/9)) {
					float scaleFactor = (float)160 / (float)bImage.getWidth();
					sImage = bImage.getScaledInstance(160, Math.round(bImage.getHeight()*scaleFactor), Image.SCALE_SMOOTH);
				}
				else {
					float scaleFactor = (float)90 / (float)bImage.getHeight();
					sImage = bImage.getScaledInstance(Math.round(bImage.getWidth()*scaleFactor), 90, Image.SCALE_SMOOTH);
				}
				
				ImageIcon icon = new ImageIcon(sImage);
				jLabelImageSelection.setIcon(icon);
			}
			catch (IOException e) {
				System.err.printf("Error: could not read image \"%s\"\n", iSend.getImageFile().getAbsoluteFile());
				MessageLogger.log(String.format("Error: Could not read image \"%s\"\n", iSend.getImageFile().getAbsoluteFile()));
			}
			
			
		}
	}//GEN-LAST:event_jButtonSelectImageActionPerformed
	
	private void jButtonSendImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendImageActionPerformed
		iSend.sendImageToServer();
	}//GEN-LAST:event_jButtonSendImageActionPerformed

	private void jButtonGetImageListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGetImageListActionPerformed
		cSend.sendMessageToServer(IMAGE_LIST_REQUEST);
	}//GEN-LAST:event_jButtonGetImageListActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/*
		 * Set the Nimbus look and feel
		 */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
		 * If Nimbus (introduced in Java SE 6) is not available, stay
		 * with the default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(BRIDClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(BRIDClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(BRIDClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(BRIDClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/*
		 * Create and display the form
		 */
		java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new BRIDClient().setVisible(true);
			}
		});
	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConnect;
    private javax.swing.JButton jButtonGetImageList;
    private javax.swing.JButton jButtonSelectImage;
    private javax.swing.JButton jButtonSendImage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelAspectRatioUpload;
    private javax.swing.JLabel jLabelDimensions;
    private javax.swing.JLabel jLabelDimensionsRatio;
    private javax.swing.JLabel jLabelDimensionsUpload;
    private javax.swing.JLabel jLabelFilename;
    private javax.swing.JLabel jLabelFilesize;
    private javax.swing.JLabel jLabelImageSelection;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTableServerContents;
    private javax.swing.JTextArea jTextAreaLog;
    private javax.swing.JTextField jTextFieldDescription;
    private javax.swing.JTextField jTextFieldServerAddress;
    // End of variables declaration//GEN-END:variables
}
