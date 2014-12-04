package template.main;

import java.util.ArrayList;
import java.util.List;

import template.objects.DenFinder;

public class Algorithm {
	private CensusData censusData = null;
	public List<Integer> allIncScore = new ArrayList<Integer>();
	public List<Integer> allRelScore = new ArrayList<Integer>();
	public List<Integer> allAgeScore = new ArrayList<Integer>();
	public List<Integer> allComScore = new ArrayList<Integer>();
	public List<Integer> allTotScore = new ArrayList<Integer>();

	public Algorithm(CensusData censusData, DenFinder denfinder) throws Exception{
		this.censusData = censusData;
		if("DP03_0052E,DP03_0053E,DP03_0054E".equals(denfinder.getIncome_range())){
			System.out.println("DP03_0052E is called");
			processDP03_0052E();
		}
		if("DP03_0055E,DP03_0056E".equals(denfinder.getIncome_range())){
			System.out.println("DP03_0055E is called");
			processDP03_0055E();
		}
		if("DP03_0057E".equals(denfinder.getIncome_range())){
			System.out.println("DP03_0057E is called");
			processDP03_0057E();
		}
		if("DP03_0058E".equals(denfinder.getIncome_range())){
			System.out.println("DP03_0058E is called");
			processDP03_0058E();
		}
		if("DP02_0025E".equals(denfinder.getRelationship_status()) | "DP02_0026E".equals(denfinder.getRelationship_status())){
			processRelat();
		}
		processAge();
		processCommu(denfinder);
		System.out.println(allIncScore.get(censusData.County.indexOf(censusData.county)));
		System.out.println(allRelScore.get(censusData.County.indexOf(censusData.county)));
		System.out.println(allAgeScore.get(censusData.County.indexOf(censusData.county)));
		System.out.println(allComScore.get(censusData.County.indexOf(censusData.county)));
		for(int x = 0; x < censusData.TotalAge.size(); x++){
			allTotScore.add(allIncScore.get(x) + allRelScore.get(x) + allAgeScore.get(x) + allComScore.get(x));
		}
		System.out.println("Total score = " + (int)(allTotScore.get(censusData.County.indexOf(censusData.county))));
		
	}
	
	private void processDP03_0052E() throws Exception{
		if(censusData.State.indexOf(censusData.state) == -1){
			throw new Exception("State doesn't match any census data");
		}
		if(censusData.County.indexOf(censusData.county) == -1){
			throw new Exception("County doesn't match any census data");
		}
		for(int x = 0; x < censusData.DP03_0052E.size(); x++){
			int accu = censusData.DP03_0052E.get(x) + censusData.DP03_0053E.get(x) + censusData.DP03_0054E.get(x);
			double percent = (double)accu/censusData.DP02_0001E.get(x);
			allIncScore.add((int)(percent * 100));
		}
	}
	private void processDP03_0055E() throws Exception{
		if(censusData.State.indexOf(censusData.state) == -1){
			throw new Exception("State doesn't match any census data");
		}
		if(censusData.County.indexOf(censusData.county) == -1){
			throw new Exception("County doesn't match any census data");
		}
		for(int x = 0; x < censusData.DP03_0055E.size(); x++){
			int accu = censusData.DP03_0055E.get(x) + censusData.DP03_0056E.get(x);
			double percent = (double)accu/censusData.DP02_0001E.get(x);
			allIncScore.add((int)(percent * 100));
		}
	}
	private void processDP03_0057E() throws Exception{
		if(censusData.State.indexOf(censusData.state) == -1){
			throw new Exception("State doesn't match any census data");
		}
		if(censusData.County.indexOf(censusData.county) == -1){
			throw new Exception("County doesn't match any census data");
		}
		for(int x = 0; x < censusData.DP03_0055E.size(); x++){
			double percent = (double)censusData.DP03_0057E.get(x)/censusData.DP02_0001E.get(x);
			allIncScore.add((int)(percent * 100));
		}
	}
	private void processDP03_0058E() throws Exception{
		if(censusData.State.indexOf(censusData.state) == -1){
			throw new Exception("State doesn't match any census data");
		}
		if(censusData.County.indexOf(censusData.county) == -1){
			throw new Exception("County doesn't match any census data");
		}
		for(int x = 0; x < censusData.DP03_0058E.size(); x++){
			double percent = (double)censusData.DP03_0058E.get(x)/censusData.DP02_0001E.get(x);
			allIncScore.add((int)(percent * 100));
		}
	}
	
	private void processRelat(){
		for(int x = 0; x < censusData.Relationship.size(); x++){
			double percent = (double)censusData.Relationship.get(x)/censusData.TotalRelationship.get(x);
			allRelScore.add((int)(percent * 100));
		}
	}
	
	private void processAge(){
		for(int x = 0; x < censusData.Age.size(); x++){
			double percent = (double)censusData.Age.get(x)/censusData.TotalAge.get(x);
			allAgeScore.add((int)(percent * 100));
		}

	}
	
	private void processCommu(DenFinder denfinder){
		for(int x = 0; x < censusData.TotalAge.size(); x++){
			if("Rural".equals(denfinder.getCommunity_type())){
				if(censusData.TotalAge.get(x) < 10000){
					allComScore.add(5);
				}
			}
			else if("City".equals(denfinder.getCommunity_type())){
				if(censusData.TotalAge.get(x) > 1000000){
					allComScore.add(5);
				}
			}
			else if("Suburban".equals(denfinder.getCommunity_type())){
				if(censusData.TotalAge.get(x) < 1000000 && (censusData.TotalAge.get(x) > 10000)){
					allComScore.add(5);
				}
			}	
			allComScore.add(-10);
		}
	
	}
}
