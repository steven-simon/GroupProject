package template.main;

import template.objects.DenFinder;

public class Algorithm {
	CensusData censusData = null;
	public Algorithm(CensusData censusData, DenFinder denfinder) throws Exception{
		this.censusData = censusData;
		int incomeScore = 0;
		if("DP03_0055E".equals(denfinder.getIncome_range())){
			System.out.println("DP03_0055E is called");
			incomeScore = processDP03_0055E(censusData);
		}
		
		if("DP03_0057E".equals(denfinder.getIncome_range())){
			System.out.println("DP03_0057E is called");
			incomeScore = processDP03_0057E(censusData);
		}
		if("DP03_0058E".equals(denfinder.getIncome_range())){
			System.out.println("DP03_0058E is called");
			incomeScore = processDP03_0058E(censusData);
		}
		System.out.println(incomeScore);
		
	}
	
	private int processDP03_0055E(CensusData censusData) throws Exception{
		if(censusData.State.indexOf(censusData.state) == -1){
			throw new Exception("State doesn't match any census data");
		}
		if(censusData.County.indexOf(censusData.county) == -1){
			throw new Exception("County doesn't match any census data");
		}
		int index = censusData.County.indexOf(censusData.county);
		double percentage = (double)censusData.DP03_0055E.get(index)/censusData.DP02_0001E.get(index);
		
		
		return (int)(percentage * 100);
	}
	private int processDP03_0057E(CensusData censusData) throws Exception{
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
	private int processDP03_0058E(CensusData censusData) throws Exception{
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
}
