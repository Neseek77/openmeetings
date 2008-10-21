package org.openmeetings.utils.geom;

import java.awt.Point;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Java Implementation of the Actionscript Class flash.geom.Point
 * 
 * @author swagner
 *
 */

public class GeomPoint extends Point {
	
	private static final Logger log = LoggerFactory.getLogger(GeomPoint.class);

	public GeomPoint add(GeomPoint p) {
		GeomPoint gPoint = new GeomPoint();
		gPoint.setLocation(this.getX() + p.getX(),this.getY() + p.getY());
		return gPoint;
	}
	
	public GeomPoint subtract(GeomPoint p) {
		GeomPoint gPoint = new GeomPoint();
		gPoint.setLocation(this.getX() - p.getX(),this.getY() - p.getY());
		return gPoint;
	}
	
	public GeomPoint clone() {
		GeomPoint p = new GeomPoint();
		p.setLocation(this.getX(), this.getY());
		return p;
	}
	
	public static GeomPoint interpolate(GeomPoint p1, GeomPoint p2, float f) {
		
		if (f == 0) {
			GeomPoint p = new GeomPoint();
			p.setLocation(p2.getX(), p2.getY());
			return p;
		} else if (f == 1) {
			GeomPoint p = new GeomPoint();
			p.setLocation(p1.getX(), p1.getY());
			return p;
		}
		
		double returnValueP1 = linearInterpolation(p1.getX(),p2.getX(),f);
		double returnValueP2 = linearInterpolation(p1.getY(),p2.getY(),f);
		
		GeomPoint p = new GeomPoint();
		p.setLocation(returnValueP1, returnValueP2);
		return p;
	}
	
	public double length() {
		return Math.sqrt( this.getX()*this.getX() + this.getY()*this.getY() );
	}
	
	public String toString() {
		return getClass().getName() + "[x=" + this.getX() + ",y=" + this.getY() + "]";
    }
	
	public void normalize(double newLength) {
		
		//Function for Line y = mx + 0 
		//given points 0,0 and this.getX(),this.getY();
		//		0 = m*0 + c
		//		m = -c;
		//		
		//		this.getY() = m*this.getX();

		double m;
		if (this.getX() == 0) {
			//log.debug("Case 1");
			if (this.getY() < 0) {
				this.setLocation(0, newLength * -1);
			} else {
				this.setLocation(0, newLength);
			}
			return;
			//m = this.getY();
		} else {
			m = this.getY() / this.getX();
			//log.debug("Case 2");
		}
		
		//log.debug("m :"+m +" newLength "+newLength);
		
		//Circle (x-x0)^2 + (y-y0)^2 = r^2
		//		anyY = m * anyX;
		//		newLength * newLength = anyY * anyY + anyX * anyX;
		//		
		//		newLength * newLength = (m * anyX) * (m * anyX) + anyX * anyX;
		//		
		//		newLength * newLength = m * m * anyX * anyX + anyX * anyX;
		//		newLength * newLength = ( m*m + 1 ) * anyX * anyX;
		//		
		//		newLength * newLength / ( m*m + 1 ) = anyX * anyX;
		
		double anyX = Math.sqrt( ( newLength * newLength ) / ( m*m + 1 ) );
		double anyY = m * anyX;
		
		//log.debug("anyX :"+anyX + " anyY: " + anyY);
		
		//translate to Flash point of origin
		if (this.getX() < 0) {
			anyX *= -1;
			anyY *= -1;
		}
		
		//log.debug("anyX :"+anyX + " anyY: " + anyY);
		this.setLocation(anyX, anyY);
		
	}
	
	/**
	 * Interpolate one number
	 * @param a
	 * @param b
	 * @param t
	 * @return
	 */
	public static double linearInterpolation(double a, double b, float t) {
		return (double)(a + t * (b - a));
	}
	

	public static GeomPoint getLineIntersection ( GeomPoint a1,GeomPoint a2,
			GeomPoint b1,GeomPoint b2) {
		
		//log.debug("b1: "+b1);
		//log.debug("b2: "+b2);
		
		double x,y,m1,m2;
	
		if ((a2.getX()-a1.getX()) == 0) {
			
			//log.debug("k1 ^ 0");
			
			double k2 = (b2.getY()-b1.getY()) / (b2.getX()-b1.getX());
			//log.debug("k2: "+k2);
			double diff = (b2.getY()-b1.getY());
			//log.debug("diff: "+diff);
			//log.debug("b1.getY(): "+b1.getY());
			//log.debug("b2.getY(): "+b2.getY());
			// an infinite directional constant means the line is vertical
			// so the intersection must be at the x coordinate of the line
			x = a1.getX();
            m2 = b1.getY() - k2 * b1.getX();
            //log.debug("m2: "+m2);
            y = k2 * x + m2;
            //log.debug("y: "+y);
			
		} else if((b2.getX()-b1.getX()) == 0) {
			
			//log.debug("k2 ^^ 0");
			
			double k1 = (a2.getY()-a1.getY()) / (a2.getX()-a1.getX());
			// same as above for line 2
			m1 = a1.getY() - k1 * a1.getX();
			x = b1.getX();
			y = k1 * x + m1;
			
		} else {
		
			//calculate directional constants
			double k1 = (a2.getY()-a1.getY()) / (a2.getX()-a1.getX());
			double k2 = (b2.getY()-b1.getY()) / (b2.getX()-b1.getX());
			
			// if the directional constants are equal, the lines are parallel,
			// meaning there is no intersection point.
			if( k1 == k2 ) return null;
		
			//log.debug("neither");
//			log.debug("k1: "+k1);
//			log.debug("k2: "+k2);
			
			m1 = a1.getY() - k1 * a1.getX();
			m2 = b1.getY() - k2 * b1.getX();				
			x = (m1-m2) / (k2-k1);
			y = k1 * x + m1;
		}
		
		
		GeomPoint gPoint = new GeomPoint();
		gPoint.setLocation(x, y);
		
		return gPoint;
	}	
}
