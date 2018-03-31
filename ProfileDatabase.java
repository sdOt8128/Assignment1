package profiles;
import java.util.*;
import java.time.*;

//Database class for storing profiles

public class ProfileDatabase
{
	ArrayList<Profile> profiles = new ArrayList<Profile>(); //ArrayList to store all profiles
	private static int numberOfProfiles=0; //counter and place keepers
	LocalDate currentDate = LocalDate.now(); //Used for date of birth (DOB) and calculating exact age difference
	
	public ProfileDatabase() {}
	
	public int getAge(int year, int month, int day) //input DOB output age in years
	{
        LocalDate birthDate = LocalDate.of(year, month, day);
		LocalDate currentDate = LocalDate.now();
		if ((birthDate != null) && (currentDate != null)) 
        {
            return Period.between(birthDate, currentDate).getYears();
        } 
        else return 0;
    }
	public int getAge(int index) {return profiles.get(index).getAge();}
	
	public String getName(int index)
	{
		String name = profiles.get(index).getName();
		return name;
	}
	
	public boolean getDependent(int index)
	{
		boolean dependent = profiles.get(index).getDependent();
		return dependent;
	}
		
	public boolean addIndependent(String name, String status, int year, int month, int day) 
	{   //Instantiate independent profile and store in arraylist
		Profile profile = new Independent(name,status,year,month,day);
		profiles.add(numberOfProfiles,profile);
		numberOfProfiles++;
		return true;
	}
	
	public boolean addDependent(String name, String status, int year, int month, int day,int parent1, int parent2)
	{	//Instantiate dependent profile and store in arralist
		Profile profile = new Dependent(name,status,year,month,day);
		profiles.add(numberOfProfiles,profile);
		numberOfProfiles++;
		connectParents(name,parent1,parent2); //connect dependent to two parents
		return true;
	}
	
	public int findProfile(String name) //input name, output Arraylist index
	{	
		int num = -1;
		for(int i=0; i<profiles.size();i++)
		{
			if (profiles.get(i).getName().equals(name))
			{
				return i;
			}			
		} return num;
	}
	
	public boolean addConnection(int nameIndex1, int nameIndex2, String relationship1, String relationship2) 
	{	//Connect two profiles
		String name1 = profiles.get(nameIndex1).getName();
		String name2 = profiles.get(nameIndex2).getName();
		LocalDate dobPerson1 = profiles.get(nameIndex1).getBirthDate();
		LocalDate dobPerson2 = profiles.get(nameIndex2).getBirthDate();
		
		if(profiles.get(nameIndex1).getAge()<2||profiles.get(nameIndex2).getAge()<2) //Enforce 2 year old or less cannot connect constraint
		{return false;}
		
		int ageDiff = Period.between(dobPerson1, dobPerson2).getYears(); // Get age difference for dependent max age difference constraint
		profiles.get(nameIndex1).setConnection(name2,relationship1,ageDiff); //Instantiate Connection object and store in Profile ArrayList
		profiles.get(nameIndex2).setConnection(name1,relationship2,ageDiff); //Do the same for the other person
		return true;
	}
	
	public void removeProfile(int nameIndex) // Remove profile from arraylist
	{
		profiles.remove(nameIndex);
		System.out.println("Profile has been removed");
		numberOfProfiles--;
	}
	
	public void printProfile(int nameIndex)
	{
		profiles.get(nameIndex).printProfile();
	}
	
	public void checkConnect(int nameIndex1, int nameIndex2) //Check if two profiles are connected by searching their ArrayList of Connection objects
	{
		String name1 = profiles.get(nameIndex1).getName();
		String name2 = profiles.get(nameIndex2).getName();
		if(profiles.get(nameIndex1).connected(name2) == true)
		{
			System.out.println("Yes "+name1+" and "+name2+" are connected.");
		}
		else System.out.println("No "+name1+" and "+name2+" are not connected.");
		
	}
	
	public void listProfiles() //Print all profile names
	{
		for (int i=0;i<profiles.size();i++)
		{
			System.out.println(profiles.get(i).getName());
		}
	}
	
	public void printAllProfiles() // Print all information for all profiles
	{	
		for (int i = 0; i < profiles.size(); i++)
		{
			profiles.get(i).printProfile();
		}
	}
	
	public void printFamily(int nameIndex) //Print children or parents connected to a profile
	{
		profiles.get(nameIndex).getFamily();
	}
	
	public void printConnections(int nameIndex) //Print connection list
	{
		profiles.get(nameIndex).printConnections();	
	}
	//Update name, age, status and image next four methods.
	public void updateName(int nameIndex,String newName) 
	{
		profiles.get(nameIndex).setName(newName);
	}
	
	public void updateAge(int nameIndex,int year, int month, int day) 
	{	
		profiles.get(nameIndex).getAge(year,month,day);
	}
	
	public void updateStatus(int nameIndex, String status)
	{
		profiles.get(nameIndex).setStatus(status);
	}
	
	public void updateImage(int nameIndex,Scanner image)
	{
		profiles.get(nameIndex).setImage(image);
	}
	//Iterate through profiles ArrayList to print names with concatenated number for menu selection.
	//Had a lot of trouble trying to get them to concatenate on the same line. Decided more important things to focus on.

	public void printProfList()
	{
		System.out.println("Please enter a profile number");
		for(int i=0;i<profiles.size();i++)
		{
			String name = profiles.get(i).getName();
			String num = Integer.toString(i+1) + ".  ";
			System.out.print(num);
			System.out.println(name);
		}
	}
	//Method used to initally connect a dependent to parents
	public void connectParents(String name, int parentIndex1, int parentIndex2)
	{
		int nameIndex = findProfile(name);
		boolean yes = true;
		String parent1 = profiles.get(parentIndex1).getName();
		String parent2 = profiles.get(parentIndex2).getName();
		profiles.get(nameIndex).connectParents(parent1,parent2);
		profiles.get(parentIndex1).addConnection(name, "child",yes);
		profiles.get(parentIndex2).addConnection(name, "child",yes);
	}
	
	public int numProfiles() {return this.numberOfProfiles;}
	
	//Method to connect pre loaded dependents to parents 
	public void readProfile(String name, String status, int year, int month, int day,int count)
	{
		 int age = getAge(year,month,day);
		 int parents[] = {0,1,0,1,0,1,3,4,3,4};
		 int i = (count-5)*2;
		 if(age<16)
		 {
			 addDependent(name,status,year,month,day,parents[i],parents[i+1]);
		 } 
		 else addIndependent(name,status,year,month,day);
	}
}

