package profiles;
import java.util.*;
import java.time.*;

abstract public class Profile 
{	//Abstract class where profiles are defined. 
	//Counters and placekeepers for both Arraylist of Connections and String array of family members and their relationships 
	private int numberOfConnections=0;
	private int currentConnection=0;
	private int numOfFamily=0;
	private String name = "";
	private Scanner image;
	private int age = 0;
	private String status = "";
	private ArrayList<Connection> connections; //Stores connections
	private LocalDate birthDate;
	private String[] familyMembers = new String[100]; //Stores children and parents
	private boolean dependent = false;
	
	//Constructor instantiated through sub classes Dependent and Independent
	public Profile(String name, String status, int year, int month, int day) 
	{	
		this.name = name;
		this.birthDate = LocalDate.of(year,month,day);
		this.age = getAge(year,month,day);
		this.status=status;
		this.connections = new ArrayList<Connection>();
	}
	// Getters
	public String getName() {return name;} 
	public int getAge() {return age;}
	public String getStatus(){return status;}
	public LocalDate getBirthDate() {return birthDate;}
	public int getNumConnections() {return numberOfConnections;}
	public boolean getDependent() {return dependent;}
	
	// Calculate Age
	public int getAge(int year, int month, int day)
	{
        LocalDate birthDate = LocalDate.of(year, month, day);
		LocalDate currentDate = LocalDate.now();
		if ((birthDate != null) && (currentDate != null)) 
        {
            return Period.between(birthDate, currentDate).getYears();
        } 
        else return 0;
    }
	//Setters
	public void setName(String name) {this.name = name;}
	public void setStatus(String status){this.status = status;}
	public void setImage(Scanner image){this.image = image;}
	public void setDependent(boolean dependent) {this.dependent=dependent;}
	
	public int findConnection(String name) //Find connection by name, returns index
	{		
		int num = -1;
		for(int i=0; i<connections.size();i++)
		{
			if (connections.get(i).getName().equals(name))
			{
				return i;
			}			
		} return num;
	}
	// Connection constraints depend on subclass
	abstract public void setConnection(String name, String relationship, int ageDiff);
	
	public void addConnection(String name, String relationship, boolean success)
	{
		if (success = true)
		{
			Connection connection = new Connection(name, relationship);
			connections.add(currentConnection,connection);
			if (relationship.equals("parent")||relationship.equals("child")) //Store parent or children information in familyMembers array
			{
				addFamily(name, relationship);
			}	
		}
	}
	
	public void connectParents(String parent1, String parent2) // initial connection for dependents
	{	
		boolean yes = true;
		addConnection(parent1,"parent",yes);
		addConnection(parent2,"parent",yes);
	}


	public void printProfile() //Self explanatory
	{
		System.out.println("Name: "+getName());
		System.out.println("Age: "+getAge());
		System.out.println("Status: "+getStatus());
		System.out.println();
	}
	
	public boolean connected(String name) //See if connected
	{
		for (int i = 0; i<connections.size();i++)
		{
			if (connections.get(i).getName().equals(name)) return true;
		}
		return false;
	}
	
	public void printConnections() //Print all name variables in connections array
	{
		for (int i = 0; i < connections.size();i++)
		{
			connections.get(i).printName();
		}
	}
	
	private void addFamily(String name, String relationship) //Store children or parent information
	{
		familyMembers[numOfFamily++] = name;
		familyMembers[numOfFamily++] = relationship;
	}
	
	public void getFamily() //iterate through familyMember array, print all children or parents
	{
		for(int i=0;i<numOfFamily;i++)
		{
			System.out.println(familyMembers[i]);
		}
	}
	
	public void printName() {System.out.print(name);}
	


}
