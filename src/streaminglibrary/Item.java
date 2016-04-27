package streaminglibrary;

public class Item {
	
	protected int itemID;
	protected String title;
	protected String artist;
	protected double length;
	
	public int getItemID() { return itemID; }
	public String getTitle() { return title; }
	public String getArtist() { return artist; }
	public double getLength() { return length; }
	
	public void setItemID(int value) { itemID = value; }
	public void setTitle(String value) { title = value; }
	public void setArtist(String value) { artist = value; }
	public void setLength(double value) { length = value; }
	
	public Item() {
		title = null;
		artist = null;
	}
	
	public Item(int itemID, String title, String artist, double length) {
		this.itemID = itemID;
		this.title = title;
		this.artist = artist;
		this.length = length;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Item) {
			Item i = (Item)o;
			return i.getItemID() == getItemID() &&
					i.getTitle().equals(getTitle()) &&
					i.getArtist().equals(getArtist()) &&
					i.getLength() == getLength();
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "Item(ID:" + getItemID() + ", Title:" + getTitle() + 
				", Artist:" + getArtist() + ", Length:" + getLength() + ")";
	}
}
