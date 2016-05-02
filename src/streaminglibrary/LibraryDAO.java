package streaminglibrary;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

@SuppressWarnings("rawtypes")
public class LibraryDAO {
	
	private final File file;
	private ObjectContainer db;
	
	public LibraryDAO() throws IOException {
		file = new File("./streamlibdb");
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		}
		else {
			file.createNewFile();
		}
		open();
	}
	
	private void open() {
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().objectClass(streaminglibrary.User.class).objectField("playlists").cascadeOnDelete(true);
		config.common().objectClass(streaminglibrary.Playlist.class).objectField("items").cascadeOnUpdate(true);
		config.common().objectClass(streaminglibrary.Item.class);
		config.common().objectClass(streaminglibrary.ClassicalItem.class);
		db = Db4oEmbedded.openFile(config, file.getAbsolutePath());
	}
	
	public void close() {
		db.close();
	}
	
	public int getNumberOfUsers() {
		return getAllUsers().size();
	}
	
	public int getNumberOfPlaylists() {
		return getAllPlaylists().size();
	}
	
	public int getNumberOfItems() {
		return getAllItems().size();
	}
	
	public int getNextUserID() {
		int val = getNumberOfUsers() + 1;
		boolean flag = false;
		do {
			User proto = new User(val, null, null, null, null);
			if (db.queryByExample(proto).size() == 0) {
				//We have found an value that no other User has as an ID
				flag = true;
			}
			else { val++; }
		}
		while (!flag);
		return val;
	}
	
	public int getNextPlaylistID() {
		int val = getNumberOfPlaylists() + 1;
		boolean flag = false;
		do {
			Playlist proto = new Playlist(val, null, null);
			if (db.queryByExample(proto).size() == 0) {
				flag = true;
			}
			else { val++; }
		}
		while (!flag);
		return val;
	}
	
	public int getNextItemID() {
		int val = getNumberOfItems() + 1;
		boolean flag = false;
		do {
			Item proto = new Item(val, null, null, 0);
			if (db.queryByExample(proto).size() == 0) {
				flag = true;
			}
			else { val++; }
		}
		while (!flag);
		return val;
	}
		
	public boolean checkUsernameExists(String checkValue) {
		User proto = new User(0, checkValue, null, null, null);
		ObjectSet result = db.queryByExample(proto);
		if (result.size() > 0) {
			return true;
		}
		else { return false; }
	}
	
	public List<User> getAllUsers() {
		User proto = new User(null);
		List<User> users = db.queryByExample(proto);
		return users;
	}
	
	public List<Playlist> getAllPlaylists() {
		Playlist proto = new Playlist(null);
		List<Playlist> playlists = db.queryByExample(proto);
		return playlists;
	}
	
	public List<Item> getAllItems() {
		Item proto = new Item(null);
		List<Item> items = db.queryByExample(proto);
		return items;
	}
	
	public User getUserByID(int id) {
		User proto = new User(id, null, null, null, null);
		ObjectSet result = db.queryByExample(proto);
		if (result.size() > 0) {
			return (User)result.next();
		}
		else {
			return null;
		}
	}
	
	public Playlist getPlaylistByID(int id) {
		Playlist proto = new Playlist(id, null, null);
		ObjectSet result = db.queryByExample(proto);
		if (result.size() > 0) {
			return (Playlist)result.next();
		}
		else {
			return null;
		}
	}
	
	public List<Playlist> getPlaylistsByUser(int userID) {
		User u = getUserByID(userID);
		if (u != null) {
			Playlist proto = new Playlist(0, u, null);
			List<Playlist> playlists = db.queryByExample(proto);
			return playlists;
		}
		else {
			return new LinkedList<Playlist>();
		}
	}
	
	public List<Playlist> getPlaylistsByItem(int itemID) {
		Item i = getItemByID(itemID);
		if (i != null) {
			List<Item> itemList = new LinkedList<Item>();
			itemList.add(i);
			Playlist proto = new Playlist(0, null, itemList);
			List<Playlist> playlists = db.queryByExample(proto);
			return playlists;
		}
		else {
			return new LinkedList<Playlist>();
		}
	}
	
	public Item getItemByID(int id) {
		Item proto = new Item(id, null, null, 0);
		ObjectSet result = db.queryByExample(proto);
		if (result.size() > 0) {
			return (Item)result.next();
		}
		else {
			return null;
		}
	}
	
	public void storeUser(User u) {
		db.store(u);
		db.commit();
	}
	
	public void storePlaylist(Playlist p) {
		db.store(p);
		db.commit();
	}
	
	public void storeItem(Item i) {
		db.store(i);
		db.commit();
	}
	
	public void deleteUser(int id) {
		User u = getUserByID(id);
		if (u != null) {
			db.delete(u);
		}
	}
	
	public void deletePlaylist(int id) {
		Playlist p = getPlaylistByID(id);
		if (p != null) {
			db.delete(p);
		}
	}
}