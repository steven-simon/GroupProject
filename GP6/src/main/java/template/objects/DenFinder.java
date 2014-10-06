/**
 * Filename: DenFinder.java
 * author: Chase Sydow
 * Date Created: 09/2014
 * Verison: 1.0
 */
package template.objects;

/**
 * Class: DenFinder
 * Description: Class used to encapsulate the form data the user submits
 * @author chase
 *
 */
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
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getIncome_range() {
		return income_range;
	}

	public void setIncome_range(String income_range) {
		this.income_range = income_range;
	}

	public String getRelationship_status() {
		return relationship_status;
	}

	public void setRelationship_status(String relationship_status) {
		this.relationship_status = relationship_status;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getCommunity_type() {
		return community_type;
	}

	public void setCommunity_type(String community_type) {
		this.community_type = community_type;
	}

	public String getSchool_quality() {
		return school_quality;
	}

	public void setSchool_quality(String school_quality) {
		this.school_quality = school_quality;
	}
	
	public String getLat()
	{
		String location_entry = this.location.trim();
		return location_entry.substring(0, location_entry.indexOf(','));
	}
	
	public String getLong()
	{
		String location_entry = this.location.trim();
		return location_entry.substring(location_entry.indexOf(',') + 1, location_entry.length());
	}

}
