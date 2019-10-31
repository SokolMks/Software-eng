public class Item {

	private int itemID;
	private String name;
	private String description;
	private double price;
	private boolean availability;
	private String imagePath;

	/**
	 *
	 * @param nme
	 */

	public Item(int id, String nme, String des, double prc, boolean avail, String imgPath)
	{
		itemID = id;
		name = nme;
		description =des;
		price = prc;
		availability = avail;
		imagePath = imgPath;
	}

	public void setItemID(int id) {
		itemID = id;
	}

	public int getItemID() {
		return itemID;
	}



	public void setName(String nme) {
		name = nme;
	}

	public String getName() {
		return name;
	}
	/**
	 *
	 * @param des
	 */
	public void setDescription(String des) {
		description = des;
	}
	public String getDescription()
	{
		return description;
	}
	/**
	 *
	 * @param prc
	 */
	public void setPrice(double prc) {
		price = prc;
	}

	public double getPrice()
	{
		return price;
	}

	public boolean setAvailability() {

		return availability;

	}

	public boolean getAvailability() {
		return availability;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}