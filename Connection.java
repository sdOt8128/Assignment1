package profiles;

public class Connection 
//Class to store connection objects
//Store name and type of connection in a Connection object
{
	private String name="";
	private String relationship = "";

	
	public Connection(String name, String relationship) 
	{
		this.name = name;
		this.relationship = relationship;
	};
	
	
	public String getName()
	{
		return name;
	}

	public void printName()
	{
		System.out.println(this.name);
	}
	
}
