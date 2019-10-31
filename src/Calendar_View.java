import DatabaseConfiguration.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Calendar_View {

	public Calendar_View(){
	    viewCalendar();
    }

    // Void???
	public ArrayList<Reservation> viewCalendar() {
        Statement query;
        ArrayList<Reservation> reservedTables = new ArrayList<>();
        try {
            query = DatabaseConnection.getConnection().createStatement();
            String sql = ("SELECT * FROM Reservations");
            ResultSet response = query.executeQuery(sql);

            while(response.next()) {
                reservedTables.add(new Reservation(response.getInt("tableNum"),
                        response.getString("customerName"),
                        response.getString("mobile"),
                        response.getDate("bookingDate"),
                        response.getTime("bookingTime")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservedTables;
	}

}