package main;
import java.util.*;
import java.io.*;
import profiles.*;
import profiles.Dependent;
import profiles.Profile;

public class Driver extends ProfileDatabase
{
	public Driver()
	{
		
	}
	
	Scanner kb = new Scanner(System.in);
	ProfileDatabase profile = new ProfileDatabase();

	private void printMenu() 
	{
		System.out.println("\n\n*------------------MiniNet Menu-------------------*");
		System.out.println("1.     Add Profile");
		System.out.println("2.     Remove Profile");
		System.out.println("3.     View Profile");
		System.out.println("4.     Connect Two People");
		System.out.println("5.     Are Two People Connected?");
		System.out.println("6.     List all Profile Names");
		System.out.println("7.     Display all Profiles");
		System.out.println("8.     Update Profile");
		System.out.println("9.     Print Profile Connections");
		System.out.println("10.    Show Children or Parents");
		System.out.println("11.    Read in Profile Data");
		System.out.println("0.    Exit");
		System.out.println("*----------------------------------------------------*");
	}
	
	private void question() // Method to refresh menu and handle incorrect input
    {
		System.out.println();
		System.out.println("Would you like to proceed or quit?");
		System.out.println("To proceed enter 9.");
		System.out.println("If you wish to quit enter 0.");
    
		switch(kb.nextInt()) 
		{
		    case 0: System.out.println ("Thank you and goodbye.");
		    		break;
		    case 9: runMenu();
		    		break;
		    default: System.err.println ( "Unrecognised option" );
		    		question();
		    		break;
		}
    }
	public void runMenu() // Main menu for user input
	{
		int selection =0;
		printMenu();
		selection = getInput(12);
		switch(selection)
		{
			case 1: addProfile(); question();	
					break;
			case 2: removeProfile(); question();
					break;
			case 3: viewProfile(); question();
					break;
			case 4: connect(); question();
					break;
			case 5: checkConnect(); question();
					break;
			case 6: listProfiles(); question();
					break;
			case 7: displayProfiles(); question();
					break;
			case 8: updateProfile(); question();
					break;
			case 9: printConnections(); question();
					break;
			case 10: getFamily(); question();
					break;
			case 11: profileData();	 question();
					break;
			case 0: System.exit(0);
					break;
			default: break;
		}

	}
	 
	private int getInput(int options) // Handles invalid input.
	{
		int selection = -1;
		
		while (selection < 0 || selection > options)
		{
			try
			{
				System.out.println("Enter Your Selection");
				selection = kb.nextInt();
				System.out.println(selection);
			}
			catch(InputMismatchException e)
			{
				System.out.println("Invalid selection, please try again.");
				kb.nextLine();
				selection = -1;
			}
			catch(NumberFormatException e)
			{
				System.out.println("Invalid selection, please try again.");
			}
		}
		return selection;		
	}

	public void addProfile() // Manually add a profile
	{
		System.out.println("Enter Name:");
		String name = kb.nextLine()+kb.nextLine();
		
		System.out.println("Enter your status or press return to skip."); // Optional status
		String status;
		status = kb.nextLine();
		

		System.out.println("Enter year of birth:");
		int year = kb.nextInt();
		System.out.println("Enter month of birth:");
		int month = kb.nextInt();
		System.out.println("Enter day of birth:");
		int day = kb.nextInt();
		int age = super.getAge(year,month,day);
		
		if(age>16) // Check age constraint
		{			
			super.addIndependent(name,status,year,month,day); // Older than 16 no dependency constraints
		}
		else 
		{
			//get database index for both parents and instantiate dependent profile
			int size = super.numProfiles();
			System.out.println("Profile is a dependent, please connect both parents");
			printProfList();
			System.out.println("Choose first parent");
			int parent1 = getInput(size);
			System.out.println("Choose second parent");
			int parent2 = getInput(size);
			super.addDependent(name, status, year, month, day, parent1, parent2); 		
		}
		
		
		System.out.println("Would you like to upload an image"); // Optional image upload. Haven't tested with actual images.
		System.out.println("Type 1 for Yes or 2 No");
		int choice = kb.nextInt();
		if(choice == 1)
		{			
			String fileName = "image.jpeg";
			Scanner inputStream=null;
			try
			{
				inputStream = new Scanner(new File(fileName));
				inputStream.useDelimiter(",");
			}
			catch(FileNotFoundException e)  
			{
				String line = inputStream.nextLine();
				System.out.println(line);
			}
			
			super.updateImage(super.numProfiles()-1, inputStream);
			System.out.println("Image uploaded.");
		}
		System.out.println("Profile Entered");
	}
	
	public void removeProfile()
	{
		System.out.println("Select the profile you would like to remove");
		printProfList();      //Display numbered list of all profiles
		int selection = getInput(super.numProfiles()); //numProfiles gives number of profiles in database
		super.removeProfile(selection-1); 
		System.out.println("Profile Removed");
	}
	
	public void viewProfile()
	{
		int size = super.numProfiles(); 
		System.out.println("Select Profile");
		printProfList();
		int index = getInput(size)-1;
		System.out.println("Profile "+(index+1));
		super.printProfile(index);
	}
	
	public boolean connect()
	{
		int size = super.numProfiles(); //declare number of valid input options
		System.out.println("Select person you would like to connect.");
		printProfList();
		int nameIndex1 = getInput(size)-1;
		String name1 = super.getName(nameIndex1);
		System.out.println("Select person you would like to connect to.");
		printProfList();
		int nameIndex2 = getInput(size)-1;
		String name2 = super.getName(nameIndex2);
		
		boolean x = super.getDependent(nameIndex1);
		boolean y = super.getDependent(nameIndex2);
		if(x^y == true)
		{
			System.out.println("Dependent cannot connect to Independent");
			return false;
		}
		if(x||y == true) 
		{
			int age1 = super.getAge(nameIndex1);
			int age2 = super.getAge(nameIndex2);
			if(age1 < 2|| age2 < 2) 
			{
				System.out.println("Cannot connect to a profile of two years of age or younger");
				return false;
			}
		}
		
		// Declare type of relationship
		System.out.println("How are they related?");
		System.out.println("1.     Friend");
		System.out.println("2.     Parent");
		System.out.println("3.     Child");
		System.out.println("4.     Married");
		System.out.println("5.     Siblings");
		
		int selection = getInput(6);
		String relation1 = "";
		String relation2 = "";
		do
		{
		if (selection == 1)
		{
			relation1 = "friend";
			relation2 = "friend";
			selection =6;
		}	
		else if (selection == 2)
		{
			relation1 = "parent";
			relation2 = "child";
			System.out.println(name1+" is connected as the parent of "+name2+".");
			selection =6;
		}
		else if (selection == 3)
		{
			relation1 = "child";
			relation2 = "parent";
			System.out.println(name1+" is connected as the child of "+name2+".");
			selection =6;
		}
		else if (selection == 4)
		{
			relation1 = "married";
			relation2 = "married";
			selection = 6;
		}
		else if (selection == 5)
		{
			relation1 = "sibling";
			relation2 = "sibling";
			selection = 6;
		}
		}
		while(selection != 6);
		
		super.addConnection(nameIndex1,nameIndex2,relation1,relation2);
		System.out.println(name1+" is connected to "+name2);
		return true;
		
	}
	
	public void checkConnect()
	{
		System.out.println("Select two profiles to see if they're connected");
		System.out.println("Select profile 1:");
		printProfList();
		int name1 = getInput(super.numProfiles())-1;
		System.out.println("Select name 2:");
		int name2 = getInput(super.numProfiles())-1;

		super.checkConnect(name1,name2);
	}
	
	public void listProfiles() //Print names of all profiles
	{
		super.listProfiles();
	}
	
	public void displayProfiles() //Print name, status and dob of all profiles
	{
		super.printAllProfiles();
	}
	
	public void getFamily() // See all children or parents of a profile
	{
		System.out.println("Select profile to see their family");
		printProfList();
		int nameIndex = getInput(super.numProfiles())-1;
		super.printFamily(nameIndex);
	}
	
	public void printConnections() // See list of connections for a profile
	{
		System.out.println("Select profile to see connections");
		printProfList();
		int selection = getInput(super.numProfiles())-1;
		super.printConnections(selection);
	}
	
	public void updateProfile() // Update, name, status or image of a profile.
	{
		System.out.println("Select profile you would like to update:");
		printProfList();
		int index = getInput(super.numProfiles())-1;
		
		System.out.println("Which field would you like to update?");
		System.out.println("1.     Name");
		System.out.println("2.     Status");
		System.out.println("3.     Image");
		System.out.println("4.     Date of Birth");
		System.out.println("5.     Exit");
		
		System.out.println("What you you like to update?");
		int selection = getInput(5);
		do
		{
		if (selection == 1 )
		{					
			System.out.println("Enter New Name");
			String newName = kb.nextLine()+kb.nextLine();
			
			super.updateName(index,newName);
			System.out.println("New name is "+newName);
			selection =5;
		}	
		else if (selection == 2)
		{
			System.out.println("Enter new status:");
			String status = kb.nextLine()+kb.nextLine();
			
			super.updateStatus(index,status);
			selection =5;
		}
		else if (selection == 3)
		{
			System.out.println("Upload a new image:");
			System.out.println("Enter image filepath:");
			String imageFile = kb.nextLine();
			Scanner image = new Scanner(imageFile);
			System.out.println("New image has been uploaded");
			selection =5;
			super.updateImage(index,image);
		}
		else if (selection == 4)
		{
			System.out.println("Enter year:");
			int year = kb.nextInt();
			System.out.println("Enter month:");
			int month = kb.nextInt();
			System.out.println("Enter day:");
			int day = kb.nextInt();
			
			super.updateAge(index,year,month,day);
			System.out.println("Date of Birth has been Updated.");
			
			selection =5;
		}
		}
		while(selection != 5);
	}
	
	public void profileData() //Read in pre made profiles from test file.
							  //There are 4 adults and 3 dependents of varying ages
	{
		String fileName = "C:\\Users\\seanr\\eclipse-workspace\\s3422814_A1\\profiles.txt";
		Scanner inputStream = null;
		String name;
		String status;
		int year; int month; int day;
		int count=0;
		
		try
		{
			inputStream = new Scanner(new File(fileName));
			inputStream.useDelimiter(",");
		}
		catch(FileNotFoundException e)
		{
			String line = inputStream.nextLine();
			System.out.println(line);
		}	
		while (inputStream.hasNextLine()) // Use a loop to enter data
		{
			try
			{
				name = inputStream.next();
				status = inputStream.next();
				String yearW = inputStream.next(); 
				String monthW = inputStream.next();
				String dayW = inputStream.next();
				year = Integer.parseInt(yearW); // coerce to int
				month = Integer.parseInt(monthW);
				day = Integer.parseInt(dayW);
				super.readProfile(name,status,year,month,day,count); //Add dependent connections to parents
				count++;
			} 
			catch(NoSuchElementException e)
			{
				break;
			}
		}
		System.out.println("Profile data loaded.");
			
	}
	
	public void printProfList() //Prints numbered profile list for selection.
	{
		super.printProfList();
	}
	
}
