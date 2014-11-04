package template.main;

// WARNING: Use Elijah's CensusData for proper JSONObject handling
// This CensusData handles only US Census outputs

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class CensusData {
	private final String lat;
	private final String lon;
	public final String state;
	public final String county;
	private final String fipsCode;
	private final JSONObject fccData;

	private final static int NUMOFDATA = 8;
	//NUMOFDATA = how many data is below this
	public List<Integer> DP02_0001E = new ArrayList<Integer>(); //# of households
	public List<Integer> DP03_0054E = new ArrayList<Integer>(); //income from 25k to 35k
	public List<Integer> DP03_0055E = new ArrayList<Integer>(); //income from 25k to 35k
	public List<Integer> DP03_0056E = new ArrayList<Integer>(); //income from 25k to 35k
	public List<Integer> DP03_0057E = new ArrayList<Integer>(); //income from 50k to 75k
	public List<Integer> DP03_0058E = new ArrayList<Integer>(); //income from 75k to 100k
	//public List<String> NAME = new ArrayList<String>();
	public List<String> State = new ArrayList<String>();
	public List<String> County = new ArrayList<String>();

	public CensusData(String lat, String lon) throws IOException {
		this.lat = lat;
		this.lon = lon;

		URL fcc = new URL(
				"http://data.fcc.gov/api/block/find?format=json&latitude="
						+ this.lat + "&longitude=" + this.lon
						+ "&showall=false");
		URLConnection fccConnection = fcc.openConnection();
		BufferedReader fccIn = new BufferedReader(new InputStreamReader(
				fccConnection.getInputStream()));
		String fccInputStr = "";
		int fccCharIn = 0;
		while (fccCharIn != -1) {
			fccCharIn = fccIn.read();
			fccInputStr = fccInputStr + (char) fccCharIn;
		}
		fccIn.close();
		fccData = new JSONObject(fccInputStr);
		fipsCode = fccData.getJSONObject("Block").getString("FIPS")
				.substring(0, 5);

		this.state = fipsCode.substring(0, 2);
		this.county = fipsCode.substring(2, 5);
		
		URL census = new URL(
				"http://api.census.gov/data/2012/acs5/profile?get="
						// Categories options you want IN ORDER
						+ "DP02_0001E,DP03_0054E,DP03_0055E,DP03_0056E,DP03_0057E,DP03_0058E" 
						+ "&for=county:*&in=state:" + state
						// + "+place="
						// + fipsCode.substring(2, 4)
						+ "&key=8a9b047b194a6ba728e926ffc57ba70536ef0377");
		URLConnection censusConnection = census.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				censusConnection.getInputStream()));
		String censusInputStr = "";
		int censusCharIn = 0;
		while (censusCharIn != -1) {
			censusCharIn = in.read();
			censusInputStr = censusInputStr + (char) censusCharIn;
		}
		in.close();

		generateDataSets(censusInputStr);
		int index = County.indexOf(this.county);
		System.out.println(DP03_0054E.get(index) + " " + DP03_0055E.get(index) + " " + DP03_0056E.get(index) + " " + DP03_0057E.get(index) + " " +DP03_0058E.get(index) + " " + DP02_0001E.get(index));
	}

	public String toString() {
		return DP02_0001E.toString() + '\n'
				+ DP03_0055E + '\n'
				+ DP03_0056E + '\n'				
				+ DP03_0057E + '\n'
				+ DP03_0058E + '\n'
				+ State + '\n'
				+ County;
	}

	private void generateDataSets(String censusInputStr) throws IOException {
		censusInputStr = censusInputStr.replace("[", "").replace("]", "").replace("\"", "");// .replace(","," ").replace("\"","");
		
		String[] tokens = censusInputStr.split(",\n*");
		
//		old code for parsing census Data with category NAME, NAME makes things difficult
//		String[] tokens = censusInputStr
//				.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)\n*");
		
		/*
		 * Copy and paste this code in this for loop for data from API call
		 * verification, just change the string in the quotes to it's expected
		 * and place the code according to the order
		 * 
		 * if(!"DP02_0011E".equals(tokens[x++].replace("\"", ""))) { 
		 * throw new IOException("Unexcepted data or missing"); }
		 */
		for (int x = 0; x < NUMOFDATA;) {
			if (!"DP02_0001E".equals(tokens[x++])) {
				throw new IOException("Unexcepted data or missing");
			}
			if (!"DP03_0054E".equals(tokens[x++])) {
				throw new IOException("Unexcepted data or missing");
			}
			if (!"DP03_0055E".equals(tokens[x++])) {
				throw new IOException("Unexcepted data or missing");
			}
			if (!"DP03_0056E".equals(tokens[x++])) {
				throw new IOException("Unexcepted data or missing");
			}
			if (!"DP03_0057E".equals(tokens[x++])) {
				throw new IOException("Unexcepted data or missing");
			}
			if (!"DP03_0058E".equals(tokens[x++])) {
				throw new IOException("Unexcepted data or missing");
			}
			if (!"state".equals(tokens[x++])) {
				throw new IOException("Unexcepted data or missing");
			}
			if (!"county".equals(tokens[x++])) {
				throw new IOException("Unexcepted data or missing");
			}
		}
		for (int x = NUMOFDATA; x < tokens.length;) {
			DP02_0001E.add(Integer.parseInt(tokens[x++]));
			DP03_0054E.add(Integer.parseInt(tokens[x++]));
			DP03_0055E.add(Integer.parseInt(tokens[x++]));
			DP03_0056E.add(Integer.parseInt(tokens[x++]));
			DP03_0057E.add(Integer.parseInt(tokens[x++]));
			DP03_0058E.add(Integer.parseInt(tokens[x++]));
			State.add(tokens[x++]);
			County.add(tokens[x++]);
		}
	}

	public static void main(String[] args) throws IOException {
		System.out.println(new CensusData("32.7758", "-96.7967"));
	}
}