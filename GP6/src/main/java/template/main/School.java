package template.main;

public class School {
	private final int rating;
	private final String name;
	
	public School(String name, int rating){
		this.name = name;
		this.rating = rating;
	}
	
	public int getRating() {
		return rating;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString(){
		String toprint = new String("");		
		toprint += name + " : " + Integer.toString(rating);
		
		return toprint;
	}
	
}
