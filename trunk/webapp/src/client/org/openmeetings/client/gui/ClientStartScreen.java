package org.openmeetings.client.gui;

import java.util.Date;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;
import org.openmeetings.client.screen.ClientBlankArea;
import org.openmeetings.client.screen.ClientSentScreen;
import org.openmeetings.client.beans.ClientConnectionBean;
import org.openmeetings.client.beans.ClientStatusBean;
import org.openmeetings.client.beans.ClientVirtualScreenBean;
import org.openmeetings.client.transport.ClientPacketMinaProcess;
import org.openmeetings.client.transport.ClientTransportMinaPool;
import org.openmeetings.client.util.ClientRasterList;


public class ClientStartScreen {
	
	private static Logger log = Logger.getLogger(ClientStartScreen.class);

	public static ClientStartScreen instance = null;
	
	public ClientPacketMinaProcess clientPacketMinaProcess = null;
	
	private ClientSentScreen clientSentScreen = null;

	java.awt.Container contentPane;
	
	JFrame t;
	JLabel textArea;
	JLabel textWarningArea;
	JLabel textAreaQualy;
	JButton startButton;
	JButton stopButton;
	JButton exitButton;
	JSpinner jSpin;
	JLabel tFieldScreenZoom;
	JLabel blankArea;
	ClientBlankArea virtualScreen;
	JLabel vscreenXLabel;
	JLabel vscreenYLabel;
	JSpinner jVScreenXSpin;
	JSpinner jVScreenYSpin;
	JLabel vscreenWidthLabel;
	JLabel vscreenHeightLabel;
	JSpinner jVScreenWidthSpin;
	JSpinner jVScreenHeightSpin;
	
	JLabel vScreenIconLeft;
	JLabel vScreenIconRight;
	JLabel vScreenIconUp;
	JLabel vScreenIconDown;	
	
	JLabel myBandWidhtTestLabel;

	public void initMainFrame() {
		try {

			UIManager.setLookAndFeel(new com.incors.plaf.kunststoff.KunststoffLookAndFeel());


//			 make Web Start happy
//			 see http://developer.java.sun.com/developer/bugParade/bugs/4155617.html
			UIManager.getLookAndFeelDefaults().put( "ClassLoader", getClass().getClassLoader()  );
			
			
			t = new JFrame("Desktop Publisher");
			contentPane = t.getContentPane();
			contentPane.setBackground(Color.WHITE);
			textArea = new JLabel();
			textArea.setBackground(Color.WHITE);
			contentPane.setLayout(null);
			contentPane.add(textArea);
			textArea.setText("This application will publish your screen");
			textArea.setBounds(10, 0, 400,24);
			
			startButton = new JButton( "start Sharing" );
			startButton.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					captureScreenStart();
				}
			});
			startButton.setBounds(10, 50, 200, 24);
			t.add(startButton);
			
			
			stopButton = new JButton( "stop Sharing" );
			stopButton.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					captureScreenStop();
				}
			});
			stopButton.setBounds(290, 50, 200, 24);
			stopButton.setEnabled(false);
			t.add(stopButton);	
			
			jSpin = new JSpinner(new SpinnerNumberModel(80, 10, 100, 5));
			jSpin.setBounds(140, 80, 50, 24);
			jSpin.addChangeListener( new ChangeListener(){
				public void stateChanged(ChangeEvent arg0) {
					// TODO Auto-generated method stub
					setNewStepperValues();
				}	
			});
			t.add(jSpin);	
			
			textAreaQualy = new JLabel();
			contentPane.add(textAreaQualy);
			textAreaQualy.setText("Quality (%)");
			textAreaQualy.setBackground(Color.LIGHT_GRAY);
			textAreaQualy.setBounds(10, 80, 100, 24);	
			
			//add the small screen thumb to the JFrame
			new ClientVirtualScreen();
			
			textWarningArea = new JLabel();
			contentPane.add(textWarningArea);
			textWarningArea.setBounds(10, 330, 400,54);
			//textWarningArea.setBackground(Color.WHITE);
			
			exitButton = new JButton( "exit" );
			exitButton.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					t.setVisible(false);
					System.exit(0);
				}
			});
			exitButton.setBounds(290, 380, 200, 24);
			t.add(exitButton);
			
			Image im_left = ImageIO.read(ClientStartScreen.class.getResource("/background.png"));	
			ImageIcon iIconBack = new ImageIcon(im_left);
			
			JLabel jLab = new JLabel(iIconBack);
			jLab.setBounds(0, 0, 500, 440);
			t.add(jLab);
			
			t.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					t.setVisible(false);
					System.exit(0);
				}
	
			});
			t.pack();
			t.setLocation(30, 30);
			t.setSize(500, 440);
			t.setVisible(true);
			t.setResizable(false);
			
			log.debug("initialized");
			
			//Init UDP-Connection
			//clientPacketMinaProcess = new ClientPacketMinaProcess(ClientConnectionBean.port);
			ClientTransportMinaPool.startConnections();
			
		} catch (Exception err) {
			System.out.println("randomFile Exception: ");
			err.printStackTrace();
		}
	}
	
	void setNewStepperValues(){
		//System.out.println(jSpin.getValue());
		ClientConnectionBean.imgQuality=new Float(Double.valueOf(jSpin.getValue().toString())/100);
	}
	
	public void _showBandwidthWarning(String warning){
		textWarningArea.setText(warning);
		//JOptionPane.showMessageDialog(t, warning);
	}
	
	void captureScreenStart(){
		try {
			
			log.debug("captureScreenStart");
			
//			ClientSendStatus clientStatus = new ClientSendStatus();
//			clientStatus.sendStart();
			
			Rectangle rect = new Rectangle(ClientVirtualScreenBean.vScreenSpinnerX,ClientVirtualScreenBean.vScreenSpinnerY,
					ClientVirtualScreenBean.vScreenSpinnerWidth,ClientVirtualScreenBean.vScreenSpinnerHeight);
			
//			(0,ClientConnectionBean.getFrameNumber(), 
//					ClientConnectionBean.publicSID, rect, 0, 0, new byte[0]);
			
			ClientStatusBean clientStatusBean = new ClientStatusBean();
			clientStatusBean.setMode(0);
			clientStatusBean.setPublicSID(ClientConnectionBean.publicSID);
			clientStatusBean.setXValue(Double.valueOf(rect.getX()).intValue());
			clientStatusBean.setYValue(Double.valueOf(rect.getY()).intValue());
			clientStatusBean.setHeight(Double.valueOf(rect.getHeight()).intValue());
			clientStatusBean.setWidth(Double.valueOf(rect.getWidth()).intValue());
			clientStatusBean.setTileHeight(ClientConnectionBean.tileHeight);
			clientStatusBean.setTileWidth(ClientConnectionBean.tileWidth);
			
			ClientTransportMinaPool.sendMessage(clientStatusBean);
			ClientRasterList.resetFrameHistory();
			ClientSentScreen.threadRunning = false;
			
			
			clientSentScreen = new ClientSentScreen();
			
			
//			new ClientCaptureScreen(1);
//			new ClientCaptureScreen(2);
//			new ClientCaptureScreen(3);
//			new ClientCaptureScreen(4);
			
			ClientSentScreen.threadRunning = true;
			clientSentScreen.start();
			
			//For testing and debugging the Protocol Codec Filters
			//new ClientCaptureScreen(true);
			
			//For testing and debugging the Compression
			//new ClientCaptureScreen(false);
			//Make the Server Cache Full
			//new ClientCaptureScreen(true);
			
			
			ClientStartScreen.instance.vScreenIconLeft.setEnabled(false);
			ClientStartScreen.instance.vScreenIconRight.setEnabled(false);
			ClientStartScreen.instance.vScreenIconUp.setEnabled(false);
			ClientStartScreen.instance.vScreenIconDown.setEnabled(false);
			
			ClientStartScreen.instance.jVScreenXSpin.setEnabled(false);
			ClientStartScreen.instance.jVScreenYSpin.setEnabled(false);
			ClientStartScreen.instance.jVScreenWidthSpin.setEnabled(false);
			ClientStartScreen.instance.jVScreenHeightSpin.setEnabled(false);
			
			ClientVirtualScreenBean.isActive = false;
			
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			
		} catch (Exception err) {
			System.out.println("captureScreenStart Exception: ");
			System.err.println(err);
			log.error("[captureScreenStart]",err);
			
			textArea.setText("Exception: "+err);
		}
	}

	void captureScreenStop(){
		try {
			
			ClientStatusBean clientStatusBean = new ClientStatusBean();
			clientStatusBean.setMode(4); //Mode 4 means stop
			clientStatusBean.setPublicSID(ClientConnectionBean.publicSID);
			clientStatusBean.setXValue(0);
			clientStatusBean.setYValue(0);
			clientStatusBean.setHeight(0);
			clientStatusBean.setWidth(0);
			clientStatusBean.setTileHeight(0);
			clientStatusBean.setTileWidth(0);
			
			ClientTransportMinaPool.sendMessage(clientStatusBean);
			
			ClientSentScreen.threadRunning = false;
			
			ClientStartScreen.instance.vScreenIconLeft.setEnabled(true);
			ClientStartScreen.instance.vScreenIconRight.setEnabled(true);
			ClientStartScreen.instance.vScreenIconUp.setEnabled(true);
			ClientStartScreen.instance.vScreenIconDown.setEnabled(true);
			
			ClientStartScreen.instance.jVScreenXSpin.setEnabled(true);
			ClientStartScreen.instance.jVScreenYSpin.setEnabled(true);
			ClientStartScreen.instance.jVScreenWidthSpin.setEnabled(true);
			ClientStartScreen.instance.jVScreenHeightSpin.setEnabled(true);
			
			ClientVirtualScreenBean.isActive = true;
			
			startButton.setEnabled(true);
			stopButton.setEnabled(false);			
		} catch (Exception err) {
			System.out.println("captureScreenStop Exception: ");
			log.error(err);
			textArea.setText("Exception: "+err);
		}
	}
	
	public ClientStartScreen(String host, String port, String SID, String room, String domain, String publicSID, String record){
		log.debug("captureScreenStop START ");
		
		//JOptionPane.showMessageDialog(t, "publicSID: "+publicSID);
		
		ClientConnectionBean.host = host;
		ClientConnectionBean.port = Integer.parseInt(port);
		ClientConnectionBean.SID = SID;
		ClientConnectionBean.room = room;
		ClientConnectionBean.domain = domain;	
		ClientConnectionBean.publicSID = publicSID;
		ClientConnectionBean.record = record;
		
		if (ClientConnectionBean.record == "yes") {
			ClientConnectionBean.mode = 1;
		}
		
		instance=this;
		
		//instance.showBandwidthWarning("StartScreen: "+SID+" "+room+" "+domain+" "+url);
		this.initMainFrame();
	}
	
	public static void main(String[] args){
		String host = args[0];
		String port = args[1];
		String SID = args[2]; 
		String room = args[3];
		String domain = args[4];
		String publicSID = args[5];
		String record = args[6];
		new ClientStartScreen(host,port,SID,room,domain,publicSID,record);
	}

}
