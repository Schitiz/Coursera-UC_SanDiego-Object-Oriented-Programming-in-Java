package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
	
	public void setup() {
		// setting up PAppler
		size(800,600, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 150, 50, 950, 550);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
	
			//m.setRadius(5);
			if(Float.parseFloat(feature.getProperty("altitude").toString())>4000){
				airportList.add(m);
				//System.out.println(feature.getProperty("altitude"));
				// put airport in hashmap with OpenFlights unique id for key
				airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
			}		
			//System.out.println(feature.getProperties());
			
		
		}
		
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
		
			//System.out.println(sl.getProperties());
			 //System.out.println(route.getLocations());
			//System.out.println(route.getProperties());
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			routeList.add(sl);
		}
		
		
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		
		map.addMarkers(routeList);
		
		map.addMarkers(airportList);
		/*for(Marker s: airportList){
			System.out.println(s.getProperties());
		}*/
		
	}
	
	public void draw() {
		background(0);
		map.draw();
		addKey();
	}
	
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		fill(153);
		rect(10, 250, 150, 150);
		textSize(18);
		fill(0, 102, 153, 51);
		text("Airport key", 15, 270);
		fill(color(255,0,0));
		ellipse(30, 290, 18, 18);
		textSize(12);
		text("4000+ magnitude", 50, 290);
		fill(color(255, 255, 0));
		rect(20, 320, 12, 12);
		textSize(12);
		text("5000+ magnitude", 50, 330);
		fill(color(0,0,255));
		triangle(25, 360, 30, 350,35,360);
		textSize(12);
		text("6000+ magnitude", 50, 360);
		

	
	}
	
	public void mouseMoved() {
		// Deselect all marker
		for (Marker marker : map.getMarkers()) {
			//if(marker != null && (Float.parseFloat(marker.getProperty("altitude").toString())>4000))
			marker.setSelected(false);
		}

		// Select hit marker
		// Note: Use getHitMarkers(x, y) if you want to allow multiple selection.
		Marker marker = map.getFirstHitMarker(mouseX, mouseY);
		if (marker != null) {
			marker.setSelected(true);
		}
	}

}
