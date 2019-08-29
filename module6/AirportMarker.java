package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	    //System.out.println(city.getProperties());
		//java.util.HashMap<String, Object> properties = city.getProperties();
		//System.out.println(this.getProperties());
		//System.out.println(routes);
	}
	
	public void draw(PGraphics pg, float x, float y) {
		// For starter code just drawMaker(...)
		if (!hidden) {
			drawMarker(pg, x, y);
			if (selected) {
				showTitle(pg, x, y);
				
			}
		}
	}
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		
		float magnitude = this.getMagnitude();
		if(magnitude>4000 && magnitude<=5000){
			pg.fill(255,0,0);
			pg.ellipse(x, y, 5, 5);
		}else if(magnitude>5000 && magnitude<=6000){
			pg.fill(255,255,0);
			pg.rect(x, y, 5, 5);
		}else if(magnitude>6000){
			pg.fill(0,0,255);
			pg.triangle(x, y-5, x-5, y+5, x+5, y+5);
		}
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		 // show rectangle with title
		pg.fill(0);
		//pg.rect(x, y, 10,10);
		String label = this.getCountry()+"-"+this.getName()+":"+this.getMagnitude();
		//System.out.println(getRoutes());
		pg.text(label, x, y);
		// show routes
		
		
	}
	
	public static List<SimpleLinesMarker> getRoutes() {
		
		return routes;
	}

	public static void setRoutes(List<SimpleLinesMarker> routes) {
		AirportMarker.routes = routes;
	}

	/*
	 * getters for Airport properties
	 */
	
	public float getMagnitude() {
		return Float.parseFloat(getProperty("altitude").toString());
	}
	
	public String getCountry() {
		return (String) getProperty("country");
	}
	
	public String getCity() {
		return (String) getProperty("city");
	}
	
	public String getName() {
		return (String) getProperty("name");
	}
	
	public String getCode() {
		return (String) getProperty("code");	
		
	}
	
	public float getRadius() {
		return Float.parseFloat(getProperty("radius").toString());
	}
	
}
