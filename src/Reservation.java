import java.sql.Date;
import java.sql.Time;

public class Reservation {
    //private final String tableName = "Reservations";
	public int tableNum;
    public String customerName;
    public String contactNumber;
    public Date date;
    public Time time;

	public Reservation(int a, String b, String c, Date d, Time e){
	    tableNum = a;
	    customerName = b;
	    contactNumber = c;
	    date = d;
	    time = e;
    }

	public int getTableNum() {
        return tableNum;
    }

	public String getName() {
        return customerName;
	}

	public String getNumber() {
        return contactNumber;
	}

	public Date getDate() {
        return date;
	}

    public Time getTime() {
        return time;
    }

}