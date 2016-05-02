package streaminglibrary;

public class ClassicalItem extends Item {
	
	private String composer;
	
	public String getComposer() { return composer; }
	public void setComposer(String value) { composer = value; }
	
	public ClassicalItem() {
		super();
	}
	
	public ClassicalItem(Object o) {
	}
	
	public ClassicalItem(int itemID, String title, String artist, 
			String composer, double length) {
		super(itemID, title, artist, length);
		this.composer = composer;
	}
	
	@Override
	public int hashCode() {
		return title.hashCode()*32 + artist.hashCode()*32 + 
				composer.hashCode()*32;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ClassicalItem) {
			ClassicalItem i = (ClassicalItem)o;
			return i.getItemID() == getItemID() &&
					i.getTitle().equals(getTitle()) &&
					i.getArtist().equals(getArtist()) &&
					i.getComposer().equals(getComposer()) &&
					i.getLength() == getLength();
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "ClassicalItem(ID:" + getItemID() + ", Title:" + getTitle() + 
				", Artist:" + getArtist() + ", Composer:" + getComposer() +
				", Length:" + getLength() + ")";
	}
}
