package profiles;

public class Independent extends Profile
{
	public Independent(String name, String status, int year, int month, int day)
	{
		super(name,status,year,month,day);	 //Instantiate independent profile objects
		super.setDependent(false);
	}
	
	public void setConnection(String name, String relationship, int ageDiff)
	{
		boolean success = true;
		Connection newConnection = new Connection(name, relationship);
		super.addConnection(name, relationship, success);
		//Connect independents
	}
}
