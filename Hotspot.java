package NYCwifi;
import java.util.ArrayList;

/**
 * The Hotspot class represents one hotspot, storing its location, latitude,
 * longitude, location type, city, and ssid. It allows the user to get all of
 * these values.
 *
 * @author Thomas Guo
 * @version 05/8/2016
 */
public class Hotspot {
	private String location;
	private Double latitude;
	private Double longitude;
	private String location_t;
	private String city;
	private String ssid;

	/**
	 * Constructor for the Hotspot object. Converts a line of text from the file
	 * "NYC_Wi-Fi_Hotspot_Locations.csv" into a corresponding Hotspot object.
	 *
	 * @param textLine
	 *            text line containing the location, latitude, longitude,
	 *            location type, city, and ssid of the Hotspot object.
	 * @throws IllegalArgumentException
	 *             If a private data field variable is set to a value that does
	 *             not match its type.
	 */
	public Hotspot(ArrayList<String> textLine) throws IllegalArgumentException {
		location = textLine.get(5);
		latitude = Double.parseDouble(textLine.get(6));
		longitude = Double.parseDouble(textLine.get(7));
		location_t = textLine.get(10);
		city = textLine.get(12);
		ssid = textLine.get(13);
	}

	/**
	 * Returns the latitude of the Hotspot object.
	 *
	 * @return Double containing the latitude
	 *
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * Returns the longitude of the Hotspot object.
	 *
	 * @return Double containing the longitude
	 *
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * Returns the city of the Hotspot object.
	 *
	 * @return String containing the city
	 *
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Returns the location of the Hotspot object.
	 *
	 * @return String containing the location
	 *
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Returns the location type of the Hotspot object.
	 *
	 * @return String containing the location type
	 *
	 */
	public String getLocation_t() {
		return location_t;
	}


	/**
	 * Returns the SSID of the Hotspot object.
	 *
	 * @return String containing the SSID
	 *
	 */
	public String getSsid() {
		return ssid;
	}
}
