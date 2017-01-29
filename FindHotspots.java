package NYCwifi;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;
import java.net.URL;

/**
 * The FindHotspots class allows users to enter an NYC zipcode. Using the 2
 * files "zip_codes_NYC.csv" and "NYC_Wi-Fi_Hotspot_Locations.csv", the five
 * closest wi-fi hotspots will be displayed. The user will then be asked to see
 * five more closest wi-fi hotspots, enter another zipcode, or quit the program.
 * This repeats until the user decides to quit the program.
 *
 * @author Thomas Guo
 * @version 05/8/2016
 * @throws FileNotFoundException
 *             If files are either missing or incorrectly named.
 * @throws InputMismatchException
 *             If user enters an incorrect input.
 * @throws IndexOutOfBoundsException
 *             If the user attempts to display more hotspots than exists.
 */

public class FindHotspots {
	public static void main(String[] args)
			throws FileNotFoundException, InputMismatchException, IndexOutOfBoundsException {


		File zipCodesFile = new File("NYCwifi/zip_codes_NYC.csv");
		File hotspotsFile = new File("NYCwifi/NYC_Wi-Fi_Hotspot_Locations.csv");

		// checks to see if files exist
		if ((!zipCodesFile.exists()) || (!hotspotsFile.exists())) {
			System.err.println("No such file based on using File class.");
			System.exit(0);
		}

		/*
		 * creates a scanner object used to read the zipcodes file and another
		 * to count how many zipcodes are in the file
		 */
		Scanner zipCodesScanner = new Scanner(zipCodesFile);
		Scanner numberOfZipCodesScanner = new Scanner(zipCodesFile);

		// counts the number of zipcodes in the file and creates an array with
		// that many entries
		int numberOfZipCodes = 0;
		while (numberOfZipCodesScanner.hasNext()) {
			numberOfZipCodesScanner.nextLine();
			numberOfZipCodes++;
		}
		ZipCode[] zipCodes = new ZipCode[numberOfZipCodes - 1];

		// populates zipCodes array with each zipcode represented as an object
		int counter1 = 0;
		while (zipCodesScanner.hasNextLine()) {
			try {
				zipCodes[counter1] = new ZipCode(split(zipCodesScanner.nextLine()));
				counter1++;
			} catch (IllegalArgumentException ex) {

			} catch (IndexOutOfBoundsException ex) {
				break;
			}
		}

		// creates a scanner object used to read the hotspots file and another
		// to count how many hotspots are in the file

		Scanner hotspotsScanner = new Scanner(hotspotsFile);
		Scanner numberOfHotspotsScanner = new Scanner(hotspotsFile);

		// counts the number of hotspots in the file and creates an array with
		// that many entries
		int numberOfHotspots = 0;
		while (numberOfHotspotsScanner.hasNext()) {
			numberOfHotspotsScanner.nextLine();
			numberOfHotspots++;
		}
		Hotspot[] hotspots = new Hotspot[numberOfHotspots - 1];

		// populates hotspots array with each zipcode represented as an object
		int counter2 = 0;
		while (hotspotsScanner.hasNextLine()) {
			try {
				hotspots[counter2] = new Hotspot(split(hotspotsScanner.nextLine()));
				counter2++;
			} catch (IllegalArgumentException ex) {

			} catch (IndexOutOfBoundsException ex) {
				break;
			}
		}

		Scanner input = new Scanner(System.in);
		boolean keepGoing1 = true;
		while (keepGoing1) {
			ZipCode zipCode = new ZipCode();
			boolean keepGoing3 = true;
			while (keepGoing3) {
				System.out.print("Enter your NYC zipcode: ");
				try {
					int userZip = input.nextInt();
					zipCode = findZipCode(userZip, zipCodes);
					if (zipCode != null)
						break;
				} catch (InputMismatchException ex) {
					input.nextLine();
				}
				System.out.println("Incorrect NYC zipcode. Try again.");
			}

			int wifiCounter = 0;
			hotspots = hotspotSort(hotspots, zipCode);

			System.out.println("\nHere are the five closest wi-fi hotspots: ");
			for (int i = wifiCounter; i < (wifiCounter + 5); i++) {
				System.out.println("Location " + (i + 1) + ":");
				System.out.println(
						hotspots[i].getCity() + ", " + hotspots[i].getLocation() + "--" + hotspots[i].getLocation_t());
				System.out.println("Wifi name: " + hotspots[i].getSsid() + "\n");
			}

			wifiCounter += 5;
			boolean keepGoing2 = true;
			while (keepGoing2) {
				System.out.println("What do you want to do next?");
				System.out.println("D: display 5 more wi-fi hotspots");
				System.out.println("Z: enter another zipcode");
				System.out.println("Q: quit the program");
				String a = input.next();

				if (a.equals("D") || a.equals("d")) {
					if ((wifiCounter + 5) > hotspots.length) {
						System.out.print("No more available wi-fi hotspots.");
						System.exit(0);
					}
					System.out.println("\nHere are the next five closest wi-fi hotspots: ");
					for (int i = wifiCounter; i < (wifiCounter + 5); i++) {
						System.out.println("Location " + (i + 1) + ":");
						System.out.println(hotspots[i].getCity() + ", " + hotspots[i].getLocation() + "--"
								+ hotspots[i].getLocation_t());
						System.out.println("Wifi name: " + hotspots[i].getSsid() + "\n");
					}
					wifiCounter += 5;
				}

				else if (a.equals("Z") || a.equals("z")) {
					keepGoing2 = false;
				}

				else if (a.equals("Q") || a.equals("q")) {
					keepGoing1 = false;
					break;
				}

				else {
					System.out.println("Invalid command. Try again.\n");
				}
			}
		}
		zipCodesScanner.close();
		numberOfZipCodesScanner.close();
		hotspotsScanner.close();
		numberOfHotspotsScanner.close();
		input.close();
	}

	/**
	 * This method converts one line in a csv file into an ArrayList object
	 *
	 * @param textLine
	 *            text line in csv file converted to an ArrayList object
	 * @return ArrayList object of the text line in the csv file
	 */
	public static ArrayList<String> split(String textLine) {
		ArrayList<String> entries = new ArrayList<String>();
		int lineLength = textLine.length();
		StringBuffer nextWord = new StringBuffer();
		char nextChar;
		boolean insideQuotes = false;

		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);
			// add character to the current entry
			if ((nextChar != ',') && (nextChar != '"')) {
				nextWord.append(nextChar);
			}
			// double quote found, decide if it is opening or closing one

			else if (nextChar == '"') {
				if (insideQuotes) {
					insideQuotes = false;
				} else {
					insideQuotes = true;
				}
			}
			// found comma inside double quotes, just
			// add it to the string
			else if (nextChar == ',' && insideQuotes) {
				nextWord.append(nextChar);
			}
			// end of the current entry reached, add it
			// to the list of entries
			// and reset the next Word to empty string
			else if (nextChar == ',' && !insideQuotes) {

				// trim the white space before adding to the list
				entries.add(nextWord.toString().trim());

				nextWord = new StringBuffer();
			}

			else {
				System.err.println("This should never be printed.\n");
			}
		}
		// add the last word
		// trim the white space before adding it to the list
		entries.add(nextWord.toString().trim());

		return entries;
	}

	/**
	 * Computes the distance between two locations with latitude and longitude
	 * given.
	 *
	 * @param lat1
	 *            latitude of location 1
	 * @param lon1
	 *            longitude of location 1
	 * @param lat2
	 *            latitude of location 2
	 * @param lon2
	 *            longitude of location 2
	 * @return the distance between the two locations
	 */
	public static double haversine(double lat1, double lon1, double lat2, double lon2) {
		final double R = 6372.8; // In kilometers
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return R * c;
	}

	/**
	 * Sorts the Hotspot object in the array hotspot from closest to farthest
	 * distance from given zipcode.
	 *
	 * @param hotspots
	 *            array containing Hotspot objects representing wi-fi hotspots
	 * @param zipCode
	 *            array containing Zipcode objects representing NYC zipcodes
	 * @return sorted Hotspot array
	 */
	public static Hotspot[] hotspotSort(Hotspot[] hotspots, ZipCode zipCode) {
		for (int i = 0; i < hotspots.length - 1; i++) {
			Hotspot hotspotMin = hotspots[i];
			int hotspotMinIndex = i;
			for (int j = i + 1; j < hotspots.length; j++) {
				if (haversine(hotspots[j].getLatitude(), hotspots[j].getLongitude(), zipCode.getLatitude(),
						zipCode.getLongitude()) < haversine(hotspotMin.getLatitude(), hotspotMin.getLongitude(),
								zipCode.getLatitude(), zipCode.getLongitude())) {
					hotspotMin = hotspots[j];
					hotspotMinIndex = j;
				}
			}
			if (hotspotMinIndex != i) {
				hotspots[hotspotMinIndex] = hotspots[i];
				hotspots[i] = hotspotMin;
			}
		}
		return hotspots;
	}

	/**
	 * Given a user input, returns the corresponding ZipCode object representing
	 * that zipcode.
	 *
	 * @param userZip
	 *            zipcode that the user enters
	 * @param zipCodes
	 *            array of all NYC zipcodes
	 * @return corresponding ZipCode object representing the userZip
	 */
	public static ZipCode findZipCode(int userZip, ZipCode[] zipCodes) {
		for (int i = 0; i < zipCodes.length; i++) {
			if (zipCodes[i].getZip() == userZip)
				return zipCodes[i];
		}
		return null;
	}
}
