/**
 * filename: MainController.java
 * author: Credera 
 * 
 * Updated by: Chase Sydow
 * Date modified: 10/4/2014
 * Added Request mapping for /results and api calls
 */
package template.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.json.*;

import template.objects.DenFinder;


@Controller
public class MainController {
	@RequestMapping(value = "/")
	public String home(Model model) 
	{
		model.addAttribute("df_results", new DenFinder());

		return "index";
	}
	
	@RequestMapping(value = "/map")
	public String map() 
	{
		return "map";
	}
	
	
	/**
	 * Maps the results.html page 
	 * @param df_results the object passed to the view
	 * @param model the Spring provided object that passes data to the view.
	 * @return
	 */
	@RequestMapping(value = "/results")
	public String createDenSelect(@ModelAttribute DenFinder df_results, Model model)
	{
		//Add df_results to the view so that the view can reference df_results using the name "df_results"
		model.addAttribute("df_results", df_results);
		
		//Make new restTemplate
		RestTemplate restTemplate = new RestTemplate();
		
		//Get the user's latitude and longitude coordinates
		String lat_coord = df_results.getLat();
		String long_coord = df_results.getLong();
		
		//NOTE the education api key will not work on your computer because your IP address does not match mine
		//Visit education.com, hover over schools, click school data tools, then click request your api key
		final String education_api_key = "8fec7672ea965dfad023fe7817c8b778";
		
		//Can be used on any computer regardless of IP address
		final String usa_today_api_key = "5hm6295dpxcswae86wm3epym";
		
		//Signifies the distance radius for the education api to look for schools in
		final String distance = "3";
        
		//Build the education url using the latitude and longitude coordinates plus the distance
		String education_url = "http://api.education.com/service/service.php?f=schoolSearch&sn=sf&key=" + education_api_key + "&v=2&";
		education_url += "latitude=" + lat_coord + "&longitude=" + long_coord + "&distance=" + distance;
		
		//HTTP GET education data. Take all of that data and store it in the String named "education_html_source."
		//String "education_html_source" contains the HTML source of the education data page. System.out.println(e) if you don't understand
		String education_html_source = restTemplate.getForObject(education_url, String.class);
        
		//Split the HTML education page source by newlines and returns. Tokenizes into array
        String [] education_data = education_html_source.split("\\r?\\n");
        List<String> schools = new ArrayList<String>();
        
        //Loop through html source and add all data in between <name> and <string> tags
        //Add to schools array list
        for(String d : education_data)
        {
        	if(d.contains("<name>") || d.contains("<string>"))
        	{
        		d = d.replaceAll("<name>", "");
        		d = d.replace("</name>", "");
        		d = d.replaceAll("<string>", "");
        		d = d.replaceAll("</string>", "");
        		d = d.trim();
        		schools.add(d);	
        	}
        	
        }
        
        //Loop through "schools" to find the city and state name of the first school found for the given lat, long coordinates
        int i = 0;
        while(!schools.get(i).equals("city"))
        {
        	i++;
        }
        //Stores city name of first school found
        String city_name = schools.get(i + 1);
        
        
        while(!schools.get(i).equals("state"))
        {
        	i++;
        }
        //Stores state postal name of first school found. Ex: TX
        String state = schools.get(i + 1);
        //Add schools to model so we can reference it in the view
        model.addAttribute("schools", schools);
        
        //Build the census url for quering the usa today data base using the city name we parsed out of "schools"
        //Census api does not take lat, long pairs as parameters
        String census_url = "http://api.usatoday.com/open/census/loc?keypat=";
        census_url += city_name + "&keyname=placename&sumlevid=4,6&api_key=" + usa_today_api_key;
        
        //HTTP GET census data page. Returns a JSON object of results.
        String census_string = restTemplate.getForObject(census_url, String.class);
        //Create JSONObject to represent and parse the returned JSON object
        JSONObject census_json = new JSONObject(census_string);
        
        //The array labeled "response" found in the JSON object contains the results of the cities found
        JSONArray location_matches = census_json.getJSONArray("response");
        //String used to represent the data matched to our given city and state name
        String census_match = "";
        
        //The census data will return any city name matched regardless of state. So check which result matches the state we parsed from "schools"
        //NOTE lat long coords found in the census data results DO NOT MATCH the lat long coords provided by the user or education results
        for(int l = 0; l < location_matches.length(); l++)
        {
        	String state_postal = location_matches.getJSONObject(l).getString("StatePostal");
        	if(state.equals(state_postal))
        	{
        		census_match = location_matches.getJSONObject(l).toString();
        	}
        }
        
        //Tokenize JSON string by the delimiter: ","
        String [] census_match_array = census_match.split("\",\"");
        //I put this into an array list because the output looks better when reference on the view
        List<String> c = Arrays.asList(census_match_array);
        
        //Pass these objects to the view so we can reference them in the view
        model.addAttribute("census_data", c);
        model.addAttribute("city", city_name);
        model.addAttribute("state", state);
        model.addAttribute("distance", distance);
        
        //Return the html page we wish to reference
		return "results";
	}
	
}
