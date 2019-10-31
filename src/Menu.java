import DatabaseConfiguration.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Menu {

	private ArrayList<Item> menuItems;

	public Menu(){
		menuItems = new ArrayList<>();
		retrieveItems();
	}

	public void retrieveItems(){
		try {
			Statement query = DatabaseConnection.getConnection().createStatement();
			String sql = ("SELECT * FROM Item");
			ResultSet response = query.executeQuery(sql);

			while(response.next()) {
				menuItems.add(new Item(response.getInt("ItemID"),response.getString("Name"), response.getString("Description"), response.getDouble("Price"),
						response.getBoolean("Availability"),
						response.getString("ImagePath")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addItems(Item item) {
		menuItems.add(item);
	}


	public void removeItems(String item) {
		for(int j = 0; j < menuItems.size(); j++)
		{
			Item obj = menuItems.get(j);

			if(obj.equals(menuItems)){
				//found, delete.
				menuItems.remove(j);
			}

		}
	}

	public Item getItem(int id){
        for(Item item: menuItems){
            if(item.getItemID() == id)  return item;
        }
        return null;
    }

    public Item getItembyName(String name){
	    for(Item i : menuItems)
	        if(i.getName().equals(name)) return i;

	    return null;
    }


	public ArrayList<Item> getMenuItems() {
		return menuItems;

	}

	public void setMenuItems(ArrayList<Item> items) {
		menuItems = items;
	}

}