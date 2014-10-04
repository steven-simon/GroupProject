package template.objects;

public class DenFinder {
	
	private String location;
	private String income_range;
	private String relationship_status;
	private String age;
	private String community_type;
	private String school_quality;
	
	public DenFinder()
	{
		location = "";
		income_range = "";
		relationship_status = "";
		age = "";
		community_type = "";
		school_quality = "";
	}
	
	public DenFinder(String l, String ir, String rs, String a, String ct, String sq)
	{
		location = l;
		income_range = ir;
		relationship_status = rs;
		age = a;
		community_type = ct;
		school_quality = sq;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder("");
		sb.append("location: " + location);
		sb.append("\nincome_range: " + income_range);
		sb.append("\nrelationship_status " + relationship_status);
		sb.append("\nage: " + age);
		sb.append("\ncommunity_type : " + community_type);
		sb.append("\nschool_quality : " + school_quality);
		return sb.toString();
		
	}

}
