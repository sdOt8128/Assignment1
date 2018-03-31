package profiles;
import java.time.*;


import main.*;

public class Dependent extends Profile // Enforces dependent connection constraints
{		
	public Dependent(String name, String status, int year, int month, int day)
	{
		super(name,status,year,month,day);
		super.setDependent(true);
	}
	
	public void setConnection(String name, String relationship, int ageDiff)
	{	
		boolean success = false;
		if(ageDiff<0) ageDiff=ageDiff*-1;
		if (ageDiff > 3) 
		{
			System.out.println("Cannot be connected, age difference is greater than 3 years.");
			success = false;}
		else
		{
			success = true;
			Connection newConnection = new Connection(name, relationship);
			super.addConnection(name, relationship,success);
		}
	}
}
