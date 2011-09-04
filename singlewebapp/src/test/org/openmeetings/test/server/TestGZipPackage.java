package org.openmeetings.test.server;

import static org.openmeetings.client.beans.ClientConnectionBean.imgQuality;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Locale;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openmeetings.client.beans.ClientConnectionBean;

/**
 * @author sebastianwagner
 *
 */
public class TestGZipPackage extends TestCase {

	private static Logger log = Logger.getLogger(TestGZipPackage.class);
	
	@Test
	public void testTestSocket(){
		try {
			
			
			Robot robot = new Robot();
			
			Rectangle screenRectangle = new Rectangle(0,0,400,400);
			
			BufferedImage imageScreen = robot.createScreenCapture(screenRectangle);
			
			int scaledWidth = Math.round(400 * ClientConnectionBean.imgQuality);
			int scaledHeight = Math.round(400 * ClientConnectionBean.imgQuality);
			
			Image img = imageScreen.getScaledInstance(scaledWidth, scaledHeight,Image.SCALE_SMOOTH);
			
			//BufferedImage.TYPE_INT_RGB //21234
			//TYPE_INT_ARGB //21729
			//TYPE_3BYTE_BGR //14795
			//TYPE_BYTE_GRAY //13202
			//TYPE_BYTE_BINARY //13812--
			//TYPE_BYTE_INDEXED //24545
			BufferedImage image = new BufferedImage(scaledWidth, scaledHeight,BufferedImage.TYPE_BYTE_BINARY);
			
			Graphics2D biContext = image.createGraphics();
			biContext.drawImage(img, 0, 0, null);
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			// Find a jpeg writer
			ImageWriter writer = null;
			Iterator<ImageWriter> iter = ImageIO
					.getImageWritersByFormatName("jpg");
			if (iter.hasNext()) {
				writer = iter.next();
			}
			writer.setOutput(out);
			ImageWriteParam iwparam = new JPEGImageWriteParam(
					Locale.getDefault());
			iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			iwparam.setCompressionQuality(imgQuality);

			writer.write(null, new IIOImage(image, null, null),
					iwparam);
			
			imageScreen.flush();
			
			byte[] payload = out.toByteArray();
			
			
			//GZip
			ByteArrayOutputStream byteGzipOut = new ByteArrayOutputStream();
	    	GZIPOutputStream gZipOut = new GZIPOutputStream(byteGzipOut);
	    	
	    	gZipOut.write(payload);
	    	gZipOut.close();
	    	
	    	
	    	//BZip2
//			ByteArrayOutputStream bytebzip2Out = new ByteArrayOutputStream();
//	    	CBZip2OutputStream bZip2Out = new CBZip2OutputStream(bytebzip2Out);
//	    	
//	    	bZip2Out.write(payload);
//	    	bZip2Out.close();
	    	
	    	log.debug("byteGzipOut LENGTH "+byteGzipOut.toByteArray().length);
			log.debug("payload LENGTH "+payload.length);
			
			log.debug("JPEG RAW: "+payload.length);
    		log.debug("JPEG GZIP: "+byteGzipOut.toByteArray().length);
//    		log.debug("JPEG BZIP2: "+bytebzip2Out.toByteArray().length);
    		
			String imagePath_1 = "pic_.jpg";
			FileOutputStream fos_1 = new FileOutputStream(imagePath_1);
			fos_1.write(payload);
			fos_1.close();
			
			
			byte[] myBytes = byteGzipOut.toByteArray();
			
			
			String imagePath = "pic_.gzip";
			
			FileOutputStream fos = new FileOutputStream(imagePath);
			fos.write(myBytes);
			fos.close();
			
			ByteArrayInputStream byteGzipIn = new ByteArrayInputStream(myBytes);
    		GZIPInputStream gZipIn = new GZIPInputStream(byteGzipIn);

    		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
    		
    		byte[] buffer = new byte[1024];
    		int count = 0;
    		while ((count = gZipIn.read(buffer)) > 0 ){
    			bytesOut.write(buffer,0,count);
			}
			bytesOut.close();
			gZipIn.close();
			
			log.debug("gZipIn CLosed");
			
//			
//			ByteArrayInputStream byteBZip2In = new ByteArrayInputStream(myBytes);
//    		CBZip2InputStream bZipIn = new CBZip2InputStream(byteBZip2In);
//
//    		ByteArrayOutputStream bytesOut_bzip2 = new ByteArrayOutputStream();
//    		
//    		byte[] buffer_bzip2 = new byte[1024];
//    		int count_1 = 0;
//    		while ((count_1 = gZipIn.read(buffer_bzip2)) > 0 ){
//    			bytesOut_bzip2.write(buffer_bzip2,0,count_1);
//			}
//    		bytesOut_bzip2.close();
//    		bZipIn.close();
//    		
//    		log.debug("bZipIn CLosed");
    		
    		
		} catch (Exception err) {
			log.error("[TestSocket] ",err);
		}
	}

}
