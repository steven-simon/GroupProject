package template.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import template.objects.DenFinder;

public class EducationData {
	private final String county;
	private final ArrayList<School> listOschools = new ArrayList<School>();
	
	
	public EducationData(String county, DenFinder denfinder) throws IOException{
		this.county = county;		
		
		URL fcc = new URL("http://api.education.com/service/service.php?f=schoolSearch&key=a1356c1296cc1e77d643b98b893a9a98&sn=sf&v=4&resf=json&" + "latitude =" + denfinder.getLat() + "&longitude=" + denfinder.getLong() + "&distance=" + 5);
		URLConnection fccConnection = fcc.openConnection();
		BufferedReader fccIn = new BufferedReader(new InputStreamReader(fccConnection.getInputStream()));
		String fccInputStr = "";
		int fccCharIn = 0;
		while (fccCharIn != -1) {
			fccCharIn = fccIn.read();
			fccInputStr = fccInputStr + (char) fccCharIn;
		}
		fccIn.close();
		System.out.println(fccInputStr);
		JSONArray fccData = new JSONArray(fccInputStr);
		//loop to iterate through the array with Jsonarray.getlength
		
		for(int i = 0; i < fccData.length(); i++) {
			JSONObject currentObject = fccData.getJSONObject(i);
			System.out.println(currentObject);
			String currentRatingStr = currentObject.getString("testrating_text");
			int currentRating = Integer.parseInt(currentRatingStr.split(" ")[2]);
			School currentSchool = new School(currentObject.getString("schoolname"), currentRating);
			listOschools.add(currentSchool);
		}
		
		
		for(School e: listOschools){
			System.out.println(e.toString());			
		}
		
		
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(new EducationData("Bexar"));
	}
}
