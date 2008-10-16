package org.openmeetings.servlet.outputhandler;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.openmeetings.app.batik.serlvets.AbstractBatikServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ExportToImageTest extends AbstractBatikServlet {
	 
	private static final Logger log = LoggerFactory.getLogger(ExportToImageTest.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {

		try {
			
	        // Get a DOMImplementation.
	        DOMImplementation domImpl =
	            GenericDOMImplementation.getDOMImplementation();

	        // Create an instance of org.w3c.dom.Document.
	        //String svgNS = "http://www.w3.org/2000/svg";
	        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;

	        Document document = domImpl.createDocument(svgNS, "svg", null);
	        
	        // Get the root element (the 'svg' element).
	        Element svgRoot = document.getDocumentElement();

	        
	        // Set the width and height attributes on the root 'svg' element.
	        svgRoot.setAttributeNS(null, "width", "2400");
	        svgRoot.setAttributeNS(null, "height", "1600");
	        

	        // Create an instance of the SVG Generator.
	        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

	        SVGGraphics2D svgGenerator8 = new SVGGraphics2D(svgGenerator);
	        //SVGGraphics2D svgGenerator2 = new SVGGraphics2D(document);
	        
	        //NOTE: Font.ITALIC+Font.BOLD = Font AND Bold !
	        this.paintDiagramText(svgGenerator8, 500, 300, 600, 360, "Process 1 asd asd as dasas " +
	        		"	dasdasdasda sdasdad a  das dasdas dasdasdasd Process 1 asd asd as dasas dasdasdasdasdasdad a  das dasd" +
	        		"	asdasdasdasd Process 1 asd asd as dasasdasdasdasdasdasdad a  das dasdasdasdasdasd", Font.PLAIN, 11,
	        		new Color(255,0,0));
	        
	        // Finally, stream out SVG to the standard output using
	        // UTF-8 encoding.
	        boolean useCSS = true; // we want to use CSS style attributes
	        //Writer out = new OutputStreamWriter(System.out, "UTF-8");
	        
	        String requestedFile = "diagram_xyz_"+new Date().getTime()+".svg";
	        
	        //OutputStream out = httpServletResponse.getOutputStream();
			//httpServletResponse.setContentType("APPLICATION/OCTET-STREAM");
			//httpServletResponse.setHeader("Content-Disposition","attachment; filename=\"" + requestedFile + "\"");
	        Writer out = httpServletResponse.getWriter();
	        
	        svgGenerator.stream(out, useCSS);

			
		} catch (Exception er) {
			log.error("ERROR ", er);
			System.out.println("Error exporting: " + er);
			er.printStackTrace();
		}
	}
}
