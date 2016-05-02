package streaminglibrary;

import java.util.List;
import java.io.IOException;
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
			setupInitialObjects(); //create the initial items in the DB
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
				System.out.println("// 1. Register a new User");
				System.out.println("// 2. Create a new Playlist");
				System.out.println("// 3. Create a new Item");
				System.out.println("// 4. List all Users");
				System.out.println("// 5. List all Playlists");
				System.out.println("// 6. List all Items");
				System.out.println("// 7. Get a Specified User");
				System.out.println("// 8. Get a Specified Playlist");
				System.out.println("// 9. Get a Specified Item");
				System.out.println("// A. Update Playlist");
				System.out.println("// B. Delete a User");
				System.out.println("// C. Get Playlists by User");
				System.out.println("// D. Find Item on Playlists");
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
						CreateNewPlaylist();
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
						break;
					case 'B':
					case 'b':
						break;
					case 'C':
					case 'c':
						break;
					case 'D':
					case 'd':
						break;
					case 'Q':
					case 'q':
						runLoop = false;
				}
				
			} while (runLoop);
			
			System.out.println("Streaming Library Closing down...");
			inputReader.close();
			librarydao.close();
		}
		catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}
	
	public static void setupInitialObjects() throws IOException {
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
		//Create the playlists
		Playlist p1 = new Playlist(1, u1, li1);
		Playlist p2 = new Playlist(2, u2, li2);
		Playlist p3 = new Playlist(3, u1, li3);
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
	
	public static void RegisterNewUser() {
		
	}
	
	public static void CreateNewPlaylist() {
		
	}
	
	public static void CreateNewItem() {
		
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
	
	public static void ListUserByID() {
		try {
			System.out.print("Enter User ID to search: ");
			inputChoice = inputReader.next().charAt(0);
			int selectedID = Integer.parseInt(String.valueOf(inputChoice));
			System.out.println("Trying to find User with ID: " + selectedID);
			User u = librarydao.getUserByID(selectedID);
			System.out.println(u);
		}
		catch (Exception ex) {
			System.out.println("Unable to find User");
		}
	}
	
	public static void ListPlaylistByID() {
		try {
			System.out.print("Enter Playlist ID to search: ");
			inputChoice = inputReader.next().charAt(0);
			int selectedID = Integer.parseInt(String.valueOf(inputChoice));
			System.out.println("Trying to find Playlist with ID: " + selectedID);
			Playlist p = librarydao.getPlaylistByID(selectedID);
			System.out.println(p);
		}
		catch (Exception ex) {
			System.out.println("Unable to find Playlist");
		}
	}
	
	public static void ListItemByID() {
		try {
			System.out.print("Enter Item ID to search: ");
			inputChoice = inputReader.next().charAt(0);
			int selectedID = Integer.parseInt(String.valueOf(inputChoice));
			System.out.println("Trying to find Item with ID: " + selectedID);
			Item i = librarydao.getItemByID(selectedID);
			System.out.println(i);
		}
		catch (Exception ex) {
			System.out.println("Unable to find Item");
		}
	}

}
