package org.openmeetings.screen.codec;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openmeetings.screen.codec.beans.ClientFrameBuffer;
import org.openmeetings.screen.codec.beans.ClientImageFrameModus;

public class CaptureScreenByDelta {
	
	private static Logger log = Logger.getLogger(CaptureScreenByDelta.class);
	
	private static final int bufferSize = 5;
	private static int bufferIndex = 1;
	
	private static List<ClientFrameBuffer> frameBuffer = new LinkedList<ClientFrameBuffer>();
	
	/**
	 * 
	 * 
	 * 1) Fill Buffer
	 * 2) Check Buffer Full
	 * 3) Do comparison => switch buffer index
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * each Buffer Pool is one second 
	 * so the PRE buffer is one second
	 * => we buffer always 1 second, analyze 
	 * that data and then send it
	 * 
	 * Buffer 1 
	 * Buffer 2
	 * Buffer 3
	 * Buffer 4
	 * Buffer 5
	 * 
	 * buffer1 != buffer2 && buffer1 != buffer3 && buffer1 != buffer4 && buffer1 != buffer5
	 * => send buffer1 with 0.1 quality
	 * 
	 * buffer1 != buffer2 && buffer1 != buffer3 && buffer1 != buffer4
	 * => send buffer1 with 0.2 quality
	 * 
	 * buffer1 != buffer2 && buffer1 != buffer3
	 * => send buffer1 with 0.4 quality
	 * 
	 * buffer1 != buffer2 
	 * => send buffer1 with 0.6 quality
	 * 
	 * buffer1 == buffer2
	 * => send nothing
	 * 
	 * => switch buffers in one direction
	 * 
	 */
	
	/**
	 * 
	 *
	 * 24.09.2009 10:24:43
	 * sebastianwagner
	 * 
	 *
	 */
	public CaptureScreenByDelta() {
		try {
			
			//log.debug("CaptureScreenByMode :: "+mode);
			
			
			//Get the current Screen
			//this.captureMode();
			
		} catch (Exception err) {
			log.error("[ClientCaptureScreen]",err);
		}
	}
	
//	/**
//	 * @param mode
//	 */
//	private void captureMode() throws Exception {
//		
//		List<ClientImageFrameModus> clientImageFrameByModues = ClientRasterByMode.tileScreen();
//		
//		
//		//Fill the buffer with Images
//		for (ClientImageFrameModus clientImageFrameModus : clientImageFrameByModues) {
//			
//			this.checkAndAddFrame(clientImageFrameModus);
//			
//		}
//		
//		//Analyze the buffer 
//		for (ClientFrameBuffer clientFrameBuffer : frameBuffer) {
//			
//			
//			log.debug("Buffer Size "+clientFrameBuffer.getBuffer().size());
//			
//			if (clientFrameBuffer.getBuffer().size() >= bufferSize) {
//				
//				log.debug("Buffer is Full for Tile "+clientFrameBuffer.getRect().getX()+" "+clientFrameBuffer.getRect().getY());
//				
//				BufferedImage bImage1 = clientFrameBuffer.getBuffer().get(0).getImage();
//				BufferedImage bImage2 = clientFrameBuffer.getBuffer().get(1).getImage();
//				BufferedImage bImage3 = clientFrameBuffer.getBuffer().get(2).getImage();
//				BufferedImage bImage4 = clientFrameBuffer.getBuffer().get(3).getImage();
//				BufferedImage bImage5 = clientFrameBuffer.getBuffer().get(4).getImage();
//				
//				if (this.imagesAreEqual(bImage1,bImage2)) {
//					
//					log.debug("baseImage1 != baseImage2");
//					
//					if (this.imagesAreEqual(bImage1,bImage4)) {
//						
//						if (this.imagesAreEqual(bImage1,bImage3)) {
//							
//							if (this.imagesAreEqual(bImage1,bImage5)) {
//								//Send with Quality of 0.1
//								this.sendDeltaBytes(clientFrameBuffer.getBuffer().get(0), new Float(0.1));
//								
//							} else {
//								//Send with Quality of 0.2
//								this.sendDeltaBytes(clientFrameBuffer.getBuffer().get(0), new Float(0.2));
//								
//							}
//							
//						} else {
//							//Send with Quality of 0.4
//							this.sendDeltaBytes(clientFrameBuffer.getBuffer().get(0), new Float(0.4));
//							
//						}
//						
//						
//					} else {
//						//Send with Quality of 0.6
//						this.sendDeltaBytes(clientFrameBuffer.getBuffer().get(0), new Float(0.6));
//						
//					}
//					
//				} else {
//					//Is equal, send nothing
//					log.debug("Is equal, send nothing "+clientFrameBuffer.getRect().getX()+" "+clientFrameBuffer.getRect().getY());
//					
//				}
//				
//				
//			} else if (clientFrameBuffer.getBuffer().size() == 1) {
//				
//				log.debug("SEND INITIALLY "+clientFrameBuffer.getRect().getX()+" "+clientFrameBuffer.getRect().getY());
//				
//				//Initially send them all
//				this.sendDeltaBytes(clientFrameBuffer.getBuffer().get(0), new Float(0.6));
//				
//			}
//			
//			
//		}
//		
//	}
	
	private boolean imagesAreEqual(BufferedImage image1, BufferedImage image2) throws Exception {
		
		for (int width = 0;width < image1.getWidth(); width ++) {
			
			for (int height = 0;height < image1.getHeight(); height ++) {
				
				int color1 = image1.getRGB(width, height);
				int color2 = image2.getRGB(width, height);
				
				if (color1 != color2) {
					return false;
				}
				
			}
			
		}
		
		return true;
	}
	
	private void sendDeltaBytes(ClientImageFrameModus clientImageFrameModus, Float quality) {
		try {
			
			
			
		} catch (Exception err) {
			log.error("[sendDeltaBytes]",err);
		}
	}
	
//	private void sendByteBufferAsJpeg(ClientImageFrameModus clientImageFrameModus, Float quality) {
//		try {
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			
//	// Find a jpeg writer
//	ImageWriter writer = null;
//	Iterator<ImageWriter> iter = ImageIO
//			.getImageWritersByFormatName("jpg");
//	if (iter.hasNext()) {
//		writer = iter.next();
//	}
//	writer.setOutput(out);
//	ImageWriteParam iwparam = new JPEGImageWriteParam(
//			Locale.getDefault());
//	iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//	iwparam.setCompressionQuality(imgQuality);
//
//	writer.write(null, new IIOImage(clientImageFrameModus.getImage(), null, null),
//			iwparam);
//			
//			byte[] payload = out.toByteArray();
//			
//			//GZip
//			ByteArrayOutputStream byteGzipOut = new ByteArrayOutputStream();
//	    	GZIPOutputStream gZipOut = new GZIPOutputStream(byteGzipOut);
//	    	
//	    	gZipOut.write(payload);
//	    	gZipOut.close();
//	    	
//	    	log.debug("SEND NEW FRAME "+clientImageFrameModus.getRect().getX()+" "+clientImageFrameModus.getRect().getY()+" q:"+quality);
//			
//			ClientFrameBean clientFrameBean = new ClientFrameBean();
//			clientFrameBean.setMode(ClientConnectionBean.mode);
//			clientFrameBean.setSequenceNumber(ClientConnectionBean.getFrameNumber());
//			clientFrameBean.setPublicSID(ClientConnectionBean.publicSID);
//			clientFrameBean.setXValue(Double.valueOf(clientImageFrameModus.getRect().getX()).intValue());
//			clientFrameBean.setYValue(Double.valueOf(clientImageFrameModus.getRect().getY()).intValue());
//			clientFrameBean.setHeight(Double.valueOf(clientImageFrameModus.getRect().getHeight()).intValue());
//			clientFrameBean.setWidth(Double.valueOf(clientImageFrameModus.getRect().getWidth()).intValue());
//			clientFrameBean.setImageBytes(byteGzipOut.toByteArray());
//			
//			ClientTransportMinaPool.sendMessage(clientFrameBean);
//			
//		} catch (Exception err) {
//			log.error("[sendByteBuffer]",err);
//		}
//	}
	
	private void checkAndAddFrame(ClientImageFrameModus clientImageFrameModus) {
		try {
			
			boolean foundRect= false;
			
			for (ClientFrameBuffer clientFrameBuffer : frameBuffer) {
				
				if (clientFrameBuffer.getRect().getX() == clientImageFrameModus.getRect().getX() 
						&& clientFrameBuffer.getRect().getY() == clientImageFrameModus.getRect().getY()) {
					log.debug("FOUND SAME POSITION");
					foundRect = true;
					
					
					//Weather this buffer is already full then we need to slice the first and add it 
					//to the end or the buffer is not yet full, then we just add it to the end
					if (clientFrameBuffer.getBuffer().size() >= bufferSize) {
						clientFrameBuffer.getBuffer().remove(0);
					}
					
					clientFrameBuffer.getBuffer().add(clientImageFrameModus);
					
				}
				
			}
			
			if (!foundRect) {
				log.debug("TILE NOT in Buffer: "+clientImageFrameModus.getRect().getX()+" "+clientImageFrameModus.getRect().getY());
				ClientFrameBuffer clientFrameBuffer = new ClientFrameBuffer();
				clientFrameBuffer.getBuffer().add(clientImageFrameModus);
				clientFrameBuffer.setRect(clientImageFrameModus.getRect());
				frameBuffer.add(clientFrameBuffer);
			}
			
		} catch (Exception err) {
			log.error("[checkAndAddFrame]",err);
		}
	}
	
	
}