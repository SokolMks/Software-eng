import DatabaseConfiguration.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main_Calendar  {

	private ArrayList<Reservation> reservedTables;
	private Calendar_View calendar_view = new Calendar_View();

    public static void main(String [] args){
        Main_Calendar cal = new Main_Calendar();
        //cal.removeReservation(7);
        //cal.addReservation(new Reservation(3, "Rafael","07865673214","2018-07-17",new Time(135000)));
        //cal.getAllReservations();
    }

	public Main_Calendar(){
        reservedTables = new ArrayList<>();
    }

    public boolean addReservation(Reservation name) {
        try {
            Statement query = DatabaseConnection.getConnection().createStatement();
            query.executeUpdate("INSERT INTO `Reservations`" +
                    "(tableNum,customerName,mobile,bookingDate,bookingTime) " +
                    "VALUE ('"+name.tableNum+"','"+name.customerName+"', '"+name.contactNumber+"', '"+name.date+"', '"+name.time+"') ");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
	}

	//Assumption that reservation record is always in the DB
	public void removeReservation(int id) {
        try {
            Statement query = DatabaseConnection.getConnection().createStatement();
            query.executeUpdate("DELETE FROM Reservations WHERE id = "+id);
            resetId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetId(){
	    int oldId = count()-1;

        try {
            Statement query = DatabaseConnection.getConnection().createStatement();
            query.executeUpdate("ALTER TABLE Reservations AUTO_INCREMENT = "+oldId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public ArrayList<Reservation> getAllReservations() {
        return calendar_view.viewCalendar();
	}

	private int count(){
        Statement query;
        int rows = 0;

        try {
            query = DatabaseConnection.getConnection().createStatement();
            String sql = ("SELECT COUNT(*) FROM Reservations");
            ResultSet response = query.executeQuery(sql);

            while(response.next()){
                rows = response.getInt("count(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }

}