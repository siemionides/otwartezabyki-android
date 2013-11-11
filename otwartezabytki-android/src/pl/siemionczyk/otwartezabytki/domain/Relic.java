package pl.siemionczyk.otwartezabytki.domain;

/** Represents relic object taken from API */
public class Relic {
	
	private int id;
	
	private String name;
	
	/** From the user naturally, in kilometers */
	private double distanceFrom; 
	
	private double latitude;
	
	private double longitude;
	
	/** Dating is the textual description taken from DB, eg. "pocz. XIX w." */
	private String dating; 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDistanceFrom() {
		return distanceFrom;
	}

	public void setDistanceFrom(double distanceFrom) {
		this.distanceFrom = distanceFrom;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getDating() {
		return dating;
	}

	public void setDating(String dating) {
		this.dating = dating;
	}
	
	/** The default constructor */
	public Relic(){
		
		
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	
	

}
