package NYCwifi;
import java.util.ArrayList;

/**
 * The ZipCode class represents one zipcode, storing its zipcode value,
 * latitude, and longitude. It allows the user to get all of these values.
 *
 * @author Thomas Guo
 * @version 05/8/2016
 */
public class ZipCode {
	private int zip;
	private Double latitude;
	private Double longitude;

	/**
	 * Constructor for the ZipCode object. Used as a placeholder for the
	 * creation of zipcode objects not yet assigned a value.
	 */
	public ZipCode() {

	}

	/**
	 * Constructor for the ZipCode object. Converts a line of text from the file
	 * "zip_codes_NYC.csv" into a corresponding ZipCode object.
	 *
	 * @param textLine
	 *            text line containing the zipcode value, latitude, and
	 *            longitude of the zipcode object.
	 * @throws IllegalArgumentException
	 *             If a private data field variable is set to a value that does
	 *             not match its type.
	 */
	public ZipCode(ArrayList<String> textLine) throws IllegalArgumentException {
		zip = Integer.parseInt(textLine.get(0));
		latitude = Double.parseDouble(textLine.get(1));
		longitude = Double.parseDouble(textLine.get(2));
	}

	/**
	 * Returns the zipcode value of the ZipCode object.
	 *
	 * @return integer containing the zipcode value
	 * @throws NullPointerException
	 *             If ZipCode object is null.
	 */
	public int getZip() throws NullPointerException {
		return zip;
	}

	/**
	 * Returns the latitude of the ZipCode object.
	 *
	 * @return Double containing the zipcode's latitude.
	 * @throws NullPointerException
	 *             If ZipCode object is null.
	 */
	public Double getLatitude() throws NullPointerException {
		return latitude;
	}

	/**
	 * Returns the longitude of the ZipCode object.
	 *
	 * @return Double containing the zipcode's longitude.
	 * @throws NullPointerException
	 *             If ZipCode object is null.
	 */
	public Double getLongitude() throws NullPointerException {
		return longitude;
	}

}
