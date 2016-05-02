package streaminglibrary;

import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 
 * @author Stuart Harrison
 *
 */
public class LibraryMenu {

	private static boolean runLoop = true;
	private static char inputChoice;
	private static LibraryDAO librarydao;
	private static Scanner inputReader;
	
	public static void main(String[] args) {
		try {
			inputReader = new Scanner(System.in);
			librarydao = new LibraryDAO();
			setupInitialObjects(); //create the initial items in the DB (Scenario 1)
			do {
				System.out.println("----------------------------");
				System.out.println("// Streaming Library");
				System.out.println("// Coursework by Stuart Harrison S0907581");
				System.out.println("----------------------------");
				System.out.println("Users in System: " + 
						librarydao.getNumberOfUsers());
				System.out.println("Playlists in System: " + 
						librarydao.getNumberOfPlaylists());
				System.out.println("Items in System: " + 
						librarydao.getNumberOfItems());
				System.out.println("----------------------------");
				System.out.println("// Select an Option:");
				System.out.println("// 1. Register a new User - (Scenario 1)");
				System.out.println("// 2. Create a new Playlist - (Scenario 1)");
				System.out.println("// 3. Create a new Item - (Scenario 1)");
				System.out.println("// 4. List all Users - (Scenario 2)");
				System.out.println("// 5. List all Playlists - (Scenario 2)");
				System.out.println("// 6. List all Items - (Scenario 2)");
				System.out.println("// 7. Get a Specified User");
				System.out.println("// 8. Get a Specified Playlist - (Scenario 3)");
				System.out.println("// 9. Get a Specified Item");
				System.out.println("// A. Update Playlist - (Scenario 4)");
				System.out.println("// B. Delete a User - (Scenario 5)");
				System.out.println("// C. Get Playlists by User - (Scenario 6)");
				System.out.println("// D. Find Item on Playlists");
				System.out.println("// E. List Playlist Details - (Scenario 7)");
				System.out.println("----------------------------");
				System.out.println("// 'Q' or 'q' to Quit");
				System.out.println("----------------------------");
				
				System.out.print("Enter Choice ");
				inputChoice = inputReader.next().charAt(0);
				
				switch(inputChoice) {
					case '1':
						RegisterNewUser();
						break;
					case '2':
						CreateNewPlaylist(null);
						break;
					case '3':
						CreateNewItem();
						break;
					case '4':
						ListAllUsers();
						break;
					case '5':
						ListAllPlaylists();
						break;
					case '6':
						ListAllItems();
						break;
					case '7':
						ListUserByID();
						break;
					case '8':
						ListPlaylistByID();
						break;
					case '9':
						ListItemByID();
						break;
					case 'A':
					case 'a':
						UpdatePlaylistByID();
						break;
					case 'B':
					case 'b':
						DeleteUserByID();
						break;
					case 'C':
					case 'c':
						ListPlaylistsByUserID();
						break;
					case 'D':
					case 'd':
						ListPlaylistsByItemID();
						break;
					case 'E':
					case 'e':
						ListItemsByPlaylistID();
						break;
					case 'Q':
					case 'q':
						runLoop = false;
				}
				
			} 
			while (runLoop);
			System.out.println("Streaming Library Closing down...");
			inputReader.close();
			librarydao.close();
		}
		catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}
	
	public static void setupInitialObjects() {
		try {
			//Create some Items
			Item i1 = new Item(1, "Love Gun", "KISS", 3.1);
			Item i2 = new Item(2, "7 Years", "Lukas Graham", 3.5);
			Item i3 = new Item(3, "Baba O'Riley", "The Who", 5.0);
			Item i4 = new Item(4, "Hotel California", "The Eagles", 6.3);
			ClassicalItem i5 = new ClassicalItem(5, "Ride of the Valkyries", 
					"Edison Records", "Richard Wagner", 3.0);
			//Store the Items in the DB
			librarydao.storeItem(i1);
			librarydao.storeItem(i2);
			librarydao.storeItem(i3);
			librarydao.storeItem(i4);
			librarydao.storeItem(i5);
			//Create some Users
			User u1 = new User(1, "Stuart", "password", "stuart@email.com");
			User u2 = new User(2, "Alex", "password2", "alex@email.com");
			//Create some Playlists
			//First create some item lists
			List<Item> li1 = new LinkedList<Item>();
			li1.add(i1);
			li1.add(i3);
			li1.add(i5);
			//Second
			List<Item> li2 = new LinkedList<Item>();
			li2.add(i2);
			li2.add(i5);
			//Third
			List<Item> li3 = new LinkedList<Item>();
			li3.add(i2);
			li3.add(i4);
			new GregorianCalendar();
			//Create the playlists
			Calendar now = new GregorianCalendar(2016,3,26); //Back-date a bit for update confirmation!
			Playlist p1 = new Playlist(1, u1, now, li1);
			Playlist p2 = new Playlist(2, u2, now, li2);
			Playlist p3 = new Playlist(3, u1, now, li3);
			//Assign them to the users
			u1.addPlaylist(p1);
			u1.addPlaylist(p3);
			u2.addPlaylist(p2);
			//Store Users
			librarydao.storeUser(u1);
			librarydao.storeUser(u2);
			//Store Playlists
			librarydao.storePlaylist(p1);
			librarydao.storePlaylist(p2);
			librarydao.storePlaylist(p3);
		}
		catch (Exception ex) {
			System.out.println("Failed to setup initial objects with exception: " + ex.getMessage());
			runLoop = false;
		}
	}
	
	public static void RegisterNewUser() {
		try {
			int newUserID = librarydao.getNextUserID();
			System.out.println("Registering a new User with ID: " + newUserID);
			String username = "";
			boolean userflag = true;
			do {
				System.out.print("Enter Username: ");
				username = inputReader.next();
				userflag = librarydao.checkUsernameExists(username);
				if (userflag) {
					System.out.println("User already exists in the System...");
				}
			}
			while (userflag && username == "");
			System.out.print("Enter Password: ");
			String password = inputReader.next();
			System.out.print("Enter Email Address: ");
			String email = inputReader.next();
			
			User nu = new User(newUserID, username, password, email);
			librarydao.storeUser(nu);
			System.out.println("New User Registered: " + nu);
			
			do {
				System.out.print("Would you like to add a Playlist to new User? (Y or N): ");
				inputChoice = inputReader.next().charAt(0);
			}
			while (inputChoice != 'y' && inputChoice != 'Y' && 
					inputChoice != 'n' && inputChoice != 'N');
			
			if (inputChoice == 'y' || inputChoice == 'Y') {
				CreateNewPlaylist(nu);
			}
			
			System.out.println("Finished registering new User");
		}
		catch (Exception ex) {
			System.out.println("Failed to Register new User with exception: " + ex.getMessage());
		}
	}
	
	public static void CreateNewPlaylist(User o) {
		try {
			int newPlaylistID = librarydao.getNextPlaylistID();
			System.out.println("Creating a new Playlist with ID: " + newPlaylistID);
			String input = "";
			User u = null;
			if (o == null) {
				System.out.print("Select User ID that the Playlist is for: ");
				inputChoice = inputReader.next().charAt(0);
				int selectedID = Integer.parseInt(String.valueOf(inputChoice));
				u = librarydao.getUserByID(selectedID);
			}
			else { u = o; }
			if (u != null) {
				System.out.println("User Selected: " + u);
				Calendar now = new GregorianCalendar();
				Playlist p = new Playlist(newPlaylistID, u, now);
				do {
					System.out.println("----------------------------");
					ListAllItemsNotInPlaylist(p);
					System.out.println("----------------------------");
					System.out.println("'f' to finish or 'b' to cancel");
					System.out.print("Item Choice: ");
					input = inputReader.next();
					if (tryParseInt(input)) {
						int selectedItem = Integer.parseInt(input);
						Item i = librarydao.getItemByID(selectedItem);
						if (i != null) {
							p.addItem(i);
						}
						else {
							System.out.println("Item does not exists.");
						}
					}
				}
				while (input.charAt(0) != 'f' && input.charAt(0) != 'f' && 
						input.charAt(0) != 'b' && input.charAt(0) != 'B');
				
				if (input.charAt(0) == 'f' || input.charAt(0) == 'F') {
					System.out.println("Creating Playlist...");
					u.addPlaylist(p);
					librarydao.storeUser(u);
					librarydao.storePlaylist(p);
					System.out.println("Playlist created...");
				}
				else {
					System.out.println("Did not create the Playlist...");
				}
			}
			else {
				System.out.println("Unable to find User");
				System.out.println("Failed to created Playlist..");
			}
		}
		catch (Exception ex) {
			System.out.println("Unable to create Playlist with exception: " + ex.getMessage());
		}
	}
	
	public static void CreateNewItem() {
		try {
			int newItemID = librarydao.getNextItemID();
			System.out.println("Creating a new Item with ID: " + newItemID);
			System.out.println("Select Category:");
			System.out.println("1. Item");
			System.out.println("2. Classical Item");
			System.out.print("Which Category?: ");
			inputChoice = inputReader.next().charAt(0);
			
			if (inputChoice == '1' || inputChoice == '2') {
				System.out.print("Enter Item Title: ");
				String title = inputReader.next();
				System.out.print("Enter Item Artist: ");
				String artist = inputReader.next();
				System.out.print("Enter Item Length: ");
				double length = inputReader.nextDouble();
				
				Item i = null;
				if (inputChoice == '2') {
					System.out.print("Enter Composer: ");
					String composer = inputReader.next();
					System.out.println("Creating new Item...");
					i = new ClassicalItem(newItemID, title, artist, composer, length);
				}
				else {
					System.out.println("Creating new Item...");
					i = new Item(newItemID, title, artist, length);
				}
				
				System.out.println("Finished Item creation: ");
				System.out.println(i);
				System.out.println("Storing new Item");
				librarydao.storeItem(i);
				System.out.println("Item stored successfully..");
			}
			else {
				System.out.println("Unrecognised Category.");
				System.out.println("Failed to create new Item");
			}
		}
		catch (Exception ex) {
			System.out.println("Failed to create new Item");
		}
	}
	
	public static void ListAllUsers() {
		List<User> usersInDb = librarydao.getAllUsers();
		System.out.println(usersInDb.size() + " user(s) found:");
		for (Iterator<User> i = usersInDb.iterator(); i.hasNext();) {
			User u = (User)i.next();
			System.out.println(u);
		}
	}
	
	public static void ListAllPlaylists() {
		List<Playlist> playlistsInDb = librarydao.getAllPlaylists();
		System.out.println(playlistsInDb.size() + " playlist(s) found:");
		for (Iterator<Playlist> i = playlistsInDb.iterator(); i.hasNext();) {
			Playlist p = (Playlist)i.next();
			System.out.println(p);
		}
	}
	
	public static void ListAllItems() {
		List<Item> itemsInDb = librarydao.getAllItems();
		System.out.println(itemsInDb.size() + " item(s) found:");
		for (Iterator<Item> i = itemsInDb.iterator(); i.hasNext();) {
			Item fi = (Item)i.next();
			System.out.println(fi);
		}
	}
	
	public static void ListAllItemsInPlaylist(Playlist p) {
		for (Iterator<Item> iter = p.getItems().iterator(); iter.hasNext();) {
			Item i = (Item)iter.next();
			System.out.println(i);
		}
	}
	
	public static void ListAllItemsNotInPlaylist(Playlist p) {
		List<Item> items = librarydao.getAllItems();
		for (Iterator<Item> iter = items.iterator(); iter.hasNext();) {
			Item i = (Item)iter.next();
			if (!p.getItems().contains(i)) {
				System.out.println(i);
			}
		}
	}
	
	public static void ListUserByID() {
		try {
			System.out.print("Enter User ID to search: ");
			String input = inputReader.next();
			if (tryParseInt(input)) {
				int selectedID = Integer.parseInt(input);
				System.out.println("Trying to find User with ID: " + selectedID);
				User u = librarydao.getUserByID(selectedID);
				if (u != null) {
					System.out.println(u);
				}
				else {
					System.out.println("Unable to find User");
				}
			}
		}
		catch (Exception ex) {
			System.out.println("Unable to find User with exception: " + ex.getMessage());
		}
	}
	
	public static void ListPlaylistByID() {
		try {
			System.out.print("Enter Playlist ID to search: ");
			String input = inputReader.next();
			if (tryParseInt(input)) {
				int selectedID = Integer.parseInt(input);
				System.out.println("Trying to find Playlist with ID: " + selectedID);
				Playlist p = librarydao.getPlaylistByID(selectedID);
				if (p != null) {
					System.out.println(p);
				}
				else {
					System.out.println("Unable to find Playlist");
				}
			}
		}
		catch (Exception ex) {
			System.out.println("Unable to find Playlist with exception: " + ex.getMessage());
		}
	}
	
	public static void ListItemByID() {
		try {
			System.out.print("Enter Item ID to search: ");
			String input = inputReader.next();
			if (tryParseInt(input)) {
				int selectedID = Integer.parseInt(input);
				System.out.println("Trying to find Item with ID: " + selectedID);
				Item i = librarydao.getItemByID(selectedID);
				if (i != null) {
					System.out.println(i);
				}
				else {
					System.out.println("Unable to find Item");
				}
			}			
		}
		catch (Exception ex) {
			System.out.println("Unable to find Item with exception: " + ex.getMessage());
		}
	}
	
	public static void UpdatePlaylistByID() {
		try {
			System.out.print("Enter Playlist ID to Update: ");
			String input = inputReader.next();
			Calendar now = new GregorianCalendar();
			if (tryParseInt(input)) {
				int selectedPlaylist = Integer.parseInt(input);
				Playlist p = librarydao.getPlaylistByID(selectedPlaylist);
				User u = p.getUser();
				if (p != null && u != null) {
					System.out.print("Found Playlist: ");
					System.out.println(p);
					System.out.println("What would you like to do with this Playlist?");
					System.out.println("1. Add Item to Playlist");
					System.out.println("2. Delete Item from Playlist");
					System.out.println("3. Delete Playlist");
					System.out.print("Select Option: ");
					input = inputReader.next();
					
					if (input.charAt(0) == '1') {
						do {
							System.out.println("----------------------------");
							ListAllItemsNotInPlaylist(p);
							System.out.println("----------------------------");
							System.out.println("'f' to finish or 'b' to cancel");
							System.out.print("Item Choice: ");
							input = inputReader.next();
							if (tryParseInt(input)) {
								int selectedItem = Integer.parseInt(input);
								Item i = librarydao.getItemByID(selectedItem);
								if (i != null) {
									p.addItem(i);
								}
								else {
									System.out.println("Item does not exists.");
								}
							}
						}
						while (input.charAt(0) != 'f' && input.charAt(0) != 'F' &&
								input.charAt(0) != 'b' && input.charAt(0) != 'B');
						
						if (input.charAt(0) == 'f' || input.charAt(0) == 'F'){
							p.setLastModified(now);
							u.deletePlaylist(p);
							u.addPlaylist(p);
							librarydao.storeUser(u);
							librarydao.storePlaylist(p);
							System.out.println("Playlist altered...");
						}
					}
					else if (input.charAt(0) == '2') {
						do {
							System.out.println("----------------------------");
							ListAllItemsInPlaylist(p);
							System.out.println("----------------------------");
							System.out.println("'f' to finish or 'b' to cancel");
							System.out.print("Item Choice: ");
							input = inputReader.next();
							if (tryParseInt(input)) {
								int selectedItem = Integer.parseInt(input);
								Item i = librarydao.getItemByID(selectedItem);
								if (i != null) {
									p.getItems().remove(i);
								}
								else {
									System.out.println("Item does not exists.");
								}
							}
						}
						while (input.charAt(0) != 'f' && input.charAt(0) != 'F' &&
								input.charAt(0) != 'b' && input.charAt(0) != 'B');
						
						if (input.charAt(0) == 'f' || input.charAt(0) == 'F'){
							p.setLastModified(now);
							u.deletePlaylist(p);
							u.addPlaylist(p);
							librarydao.storeUser(u);
							librarydao.storePlaylist(p);
							System.out.println("Playlist altered...");
						}
					}
					else if (input.charAt(0) == '3') {
						u.deletePlaylist(p);
						librarydao.storeUser(u);
						librarydao.deletePlaylist(p.getPlaylistID());
						System.out.println("Playlist deleted...");
					}
					
					System.out.println("Finished Updating Playlist");
				}
				else {
					System.out.println("No Playlist exists with ID: " + selectedPlaylist);
				}
			}
		}
		catch (Exception ex) {
			System.out.println("Failed to update Playlist with exception: " + ex.getMessage());
		}
	}
	
	public static void DeleteUserByID() {
		try {
			System.out.print("Enter User ID to delete: ");
			String input = inputReader.next();
			if (tryParseInt(input)) {
				int selectedID = Integer.parseInt(input);
				librarydao.deleteUser(selectedID);
				System.out.println("Deleted User with ID: " + selectedID);
			}
		}
		catch (Exception ex) {
			System.out.println("Failed to Delete User");
		}
	}
	
	public static void ListPlaylistsByUserID() {
		try {
			System.out.print("Enter User ID to retrieve Playlists: ");
			String input = inputReader.next();
			if (tryParseInt(input)) {
				int selectedID = Integer.parseInt(input);
				List<Playlist> playlistsInDb = librarydao.getPlaylistsByUser(selectedID);
				System.out.println(playlistsInDb.size() + " playlist(s) found created for User ID: " + selectedID);
				for (Iterator<Playlist> i = playlistsInDb.iterator(); i.hasNext();) {
					Playlist p = (Playlist)i.next();
					System.out.println(p);
				}
			}
		}
		catch (Exception ex) {
			System.out.println("Failed to get Playlists with exception: " + ex.getMessage());
		}
	}
	
	public static void ListPlaylistsByItemID() {
		try {
			System.out.print("Enter Item ID to retrieve Playlists: ");
			String input = inputReader.next();
			if (tryParseInt(input)) {
				int selectedID = Integer.parseInt(input);
				List<Playlist> playlistsInDb = librarydao.getPlaylistsByItem(selectedID);
				System.out.println(playlistsInDb.size() + " playlist(s) found containing Item ID: " + selectedID);
				for (Iterator<Playlist> i = playlistsInDb.iterator(); i.hasNext();) {
					Playlist p = (Playlist)i.next();
					System.out.println(p);
				}
			}
		}
		catch (Exception ex) {
			System.out.println("Failed to get Playlists with exception: " + ex.getMessage());
		}
	}
	
	public static void ListItemsByPlaylistID() {
		try {
			System.out.print("Enter Playlist ID to retrieve information on: ");
			String input = inputReader.next();
			if (tryParseInt(input)) {
				int selectedID = Integer.parseInt(input);
				Playlist p = librarydao.getPlaylistByID(selectedID);
				if (p != null) {
					User u = p.getUser();
					System.out.println("----------------------------");
					System.out.println("Playlist Details");
					System.out.println("Created by: " + u);
					System.out.println("Items:");
					ListAllItemsInPlaylist(p);
					System.out.println("----------------------------");
				}
				else {
					System.out.println("Unable to find Playlist with ID " + selectedID);
				}
			}
			else {
				System.out.println("Invalid input, unable to get Playlist information");
			}
		}
		catch (Exception ex) {
			System.out.println("Failed to get Items in the Playlist with exception: " + ex.getMessage());
		}
	}
	
	public static boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		}
		catch (NumberFormatException ex) {
			return false;
		}
	}
}