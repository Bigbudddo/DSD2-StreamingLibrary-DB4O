package streaminglibrary;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

/**
 * 
 * @author Stuart Harrison
 *
 */
public class Playlist {
	
	private int playlistID;
	private User user;
	private Calendar lastModified;
	private List<Item> items;
	
	public int getPlaylistID() { return playlistID; }
	public User getUser() { return user; }
	public Calendar getLastModified() { return lastModified; }
	public List<Item> getItems() { return items; }
	
	public void setPlaylistID(int value) { playlistID = value; }
	public void setUser(User value) { user = value; }
	public void setLastModified(Calendar value) { lastModified = value; }
	public void setItems(List<Item> value) { items = value; }
	
	public Playlist() {
		this.playlistID = 0;
		this.user = null;
		this.lastModified = null;
		this.items = new LinkedList<Item>();
	}
	
	public Playlist(Object o) {
	}
	
	public Playlist(int playlistID, User user, Calendar lastModified) {
		this.playlistID = playlistID;
		this.user = user;
		this.lastModified = lastModified;
		this.items = new LinkedList<Item>();
	}
	
	public Playlist(int playlistID, User user, Calendar lastModified, List<Item> items) {
		this.playlistID = playlistID;
		this.user = user;
		this.lastModified = lastModified;
		this.items = items;
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public void removeItem(Item item) {
		items.remove(item);
	}
	
	public String getDateString() {
		if (lastModified != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMM YY");
			String dateString = formatter.format(lastModified.getTime());
			return dateString;
		}
		else {
			return "Unknown Date";
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Playlist) {
			Playlist p = (Playlist)o;
			return p.getPlaylistID() == getPlaylistID();
		}
		else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return lastModified.hashCode()*7;
	}
	
	@Override
	public String toString() {
		return "Playlist(ID:" + getPlaylistID() + ", User:" + getUser().getUsername() +
				", Last Modified:(" + getDateString() + "), Items:" + getItems() + ")";
	}
}