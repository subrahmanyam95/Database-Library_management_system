import java.sql.*;
public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Getlost");
		
			Statement myStmt = myConn.createStatement();
		
		ResultSet myRs = myStmt.executeQuery("Select * from book");
		
		while(myRs.next()){
			
			System.out.println(myRs.getString("Title"));
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
