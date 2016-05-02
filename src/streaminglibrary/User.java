package streaminglibrary;

import java.util.List;
import java.util.LinkedList;

/**
 * 
 * @author Stuart Harrison
 *
 */
public class User {
	
	//Instance Variables
	private int userID;
	private String username;
	private String password;
	private String email;
	private List<Playlist> playlists;
	
	//Getter properties
	public int getUserID() { return userID; }
	public String getUsername() { return username; }
	public String getPassword() { return password; }
	public String getEmail() { return email; }
	public List<Playlist> getPlaylists() { return playlists; }
	
	//Setter properties
	public void setUserID(int value) { userID = value; }
	public void setUsername(String value) { username = value; }
	public void setPassword(String value) { password = value; }
	public void setEmail(String value) { email = value; }
	public void setPlaylists(List<Playlist> value) { playlists = value; }
	
	public User() {
		this.username = null;
		this.password = null;
		this.email = null;
		this.playlists = new LinkedList<Playlist>();
	}
	
	public User(Object o) {	
	}
	
	public User(int userID, String username, String password, String email) {
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.email = email;
		this.playlists = new LinkedList<Playlist>();
	}
	
	public User(int userID, String username, String password, String email, 
				List<Playlist> playlists) {
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.email = email;
		this.playlists = playlists;
	}
	
	public void addPlaylist(Playlist playlist) {
		playlists.add(playlist);
	}
	
	public void deletePlaylist(Playlist playlist) {
		playlists.remove(playlist);
	}
	
	@Override
	public int hashCode() {
		return username.hashCode()*32 + password.hashCode()*32 + 
				email.hashCode()*1024;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof User) {
			User u = (User)o;
			return u.getUserID() == getUserID() &&
					u.getUsername().equals(getUsername()) &&
					u.getPassword().equals(getPassword()) &&
					u.getEmail().equals(getEmail());
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "User(ID:" + getUserID() + ", Username:" + getUsername() + 
				", Password:" + getPassword() + ", Email:" + getEmail() + 
				", Playlists:" + getPlaylists() + ")";
	}
}
