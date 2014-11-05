package template.main;

import template.objects.DenFinder;

public class Algorithm {
	private CensusData censusData = null;
	public int incomeScore = 0;
	public int relaScore = 0;
	public int ageScore = 0;
	public int commuMod = 0; 
	public int totalScore = 0;
	public Algorithm(CensusData censusData, DenFinder denfinder) throws Exception{
		this.censusData = censusData;
		if("DP03_0052E,DP03_0053E,DP03_0054E".equals(denfinder.getIncome_range())){
			System.out.println("DP03_0052E is called");
			incomeScore = processDP03_0052E();
		}
		if("DP03_0055E,DP03_0056E".equals(denfinder.getIncome_range())){
			System.out.println("DP03_0055E is called");
			incomeScore = processDP03_0055E();
		}
		if("DP03_0057E".equals(denfinder.getIncome_range())){
			System.out.println("DP03_0057E is called");
			incomeScore = processDP03_0057E();
		}
		if("DP03_0058E".equals(denfinder.getIncome_range())){
			System.out.println("DP03_0058E is called");
			incomeScore = processDP03_0058E();
		}
		if("DP02_0025E".equals(denfinder.getRelationship_status()) | "DP02_0026E".equals(denfinder.getRelationship_status())){
			relaScore = processRelat();
		}
		ageScore = processAge();
		commuMod = processCommu(denfinder);
		System.out.println(incomeScore);
		System.out.println(relaScore);
		System.out.println(ageScore);
		System.out.println(commuMod);
		totalScore = (incomeScore + relaScore + ageScore + commuMod);
		System.out.println("Total score = " + (int)(incomeScore + relaScore + ageScore + commuMod));
		
	}
	
	private int processDP03_0052E() throws Exception{
		if(censusData.State.indexOf(censusData.state) == -1){
			throw new Exception("State doesn't match any census data");
		}
		if(censusData.County.indexOf(censusData.county) == -1){
			throw new Exception("County doesn't match any census data");
		}
		int index = censusData.County.indexOf(censusData.county);
		int accu0055n0056 = censusData.DP03_0052E.get(index) + censusData.DP03_0053E.get(index) + censusData.DP03_0054E.get(index);;
		double percentage = (double)accu0055n0056/censusData.DP02_0001E.get(index);
		
		
		return (int)(percentage * 100);
	}
	private int processDP03_0055E() throws Exception{
		if(censusData.State.indexOf(censusData.state) == -1){
			throw new Exception("State doesn't match any census data");
		}
		if(censusData.County.indexOf(censusData.county) == -1){
			throw new Exception("County doesn't match any census data");
		}
		int index = censusData.County.indexOf(censusData.county);
		int accu0055n0056 = censusData.DP03_0055E.get(index) + censusData.DP03_0056E.get(index);
		double percentage = (double)accu0055n0056/censusData.DP02_0001E.get(index);
		
		
		return (int)(percentage * 100);
	}
	private int processDP03_0057E() throws Exception{
		if(censusData.State.indexOf(censusData.state) == -1){
			throw new Exception("State doesn't match any census data");
		}
		if(censusData.County.indexOf(censusData.county) == -1){
			throw new Exception("County doesn't match any census data");
		}
		int index = censusData.County.indexOf(censusData.county);
		double percentage = (double)censusData.DP03_0057E.get(index)/censusData.DP02_0001E.get(index);
		
		
		return (int)(percentage * 100);
	}
	private int processDP03_0058E() throws Exception{
		if(censusData.State.indexOf(censusData.state) == -1){
			throw new Exception("State doesn't match any census data");
		}
		if(censusData.County.indexOf(censusData.county) == -1){
			throw new Exception("County doesn't match any census data");
		}
		int index = censusData.County.indexOf(censusData.county);
		double percentage = (double)censusData.DP03_0058E.get(index)/censusData.DP02_0001E.get(index);
		
		
		return (int)(percentage * 100);
	}
	
	private int processRelat(){
		int index = censusData.County.indexOf(censusData.county);
		double percentage = (double)censusData.Relationship.get(index)/censusData.TotalRelationship.get(index);
		return (int)(percentage * 100);
	}
	
	private int processAge(){
		int index = censusData.County.indexOf(censusData.county);
		double percentage = (double)censusData.Age.get(index)/censusData.TotalAge.get(index);
		return (int)(percentage * 100);
	}
	
	private int processCommu(DenFinder denfinder){
		int index = censusData.County.indexOf(censusData.county);
		if("Rural".equals(denfinder.getCommunity_type())){
			if(censusData.TotalAge.get(index) < 10000){
				return 5;
			}
		}
		else if("City".equals(denfinder.getCommunity_type())){
			if(censusData.TotalAge.get(index) > 1000000){
				return 5;
			}
		}
		else if("Suburban".equals(denfinder.getCommunity_type())){
			if(censusData.TotalAge.get(index) < 1000000 && (censusData.TotalAge.get(index) > 10000)){
				return 5;
			}
		}
		return -10;
	}
}
