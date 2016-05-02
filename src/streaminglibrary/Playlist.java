package streaminglibrary;

import java.util.List;
import java.util.LinkedList;

/**
 * 
 * @author Stuart Harrison
 *
 */
public class Playlist {
	
	private int playlistID;
	private User user;
	private List<Item> items;
	
	public int getPlaylistID() { return playlistID; }
	public User getUser() { return user; }
	public List<Item> getItems() { return items; }
	
	public void setPlaylistID(int value) { playlistID = value; }
	public void setUser(User value) { user = value; }
	public void setItems(List<Item> value) { items = value; }
	
	public Playlist() {
		this.playlistID = 0;
		this.user = null;
		this.items = new LinkedList<Item>();
	}
	
	public Playlist(Object o) {
	}
	
	public Playlist(int playlistID, User user, List<Item> items) {
		this.playlistID = playlistID;
		this.user = user;
		this.items = items;
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public void removeItem(Item item) {
		items.remove(item);
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
	public String toString() {
		return "Playlist(ID:" + getPlaylistID() + ", User:" + getUser().getUsername() +
				", Items:" + getItems() + ")";
	}
}
