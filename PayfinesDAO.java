import java.util.*;

import javax.swing.JOptionPane;

import java.sql.*;
import java.sql.Date;
import java.time.Instant;
import java.io.*;
import java.math.*;



public class PayfinesDAO {
	
private static Connection myConn;
	
	public PayfinesDAO() throws Exception {
		
		Properties props = new Properties();
		props.load(new FileInputStream("demo.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		/*
		
		
		String user = "root";
		String password = "Getlost";
		String dburl = "jdbc:mysql://localhost:3306/library?zeroDateTimeBehavior=convertToNull";
		*/
		// connect to database
		myConn = DriverManager.getConnection(dburl, user, password);
	//	myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Getlost");
		
		System.out.println("DB connection successful to: " + dburl);
	}
	
	
	public void payfine(Payfines thePayfine) throws Exception {
		PreparedStatement myStmt = null;
		

		try {
			// prepare statement

			myStmt = myConn.prepareStatement("update Fines"
					+ " set Paid = ? "
					+ " Where loan_id = ?");
			
			
			//update book_loans set Date_in = now() where loan_id=2;
			// set params		
			  	myStmt.setBoolean(1, true);
				myStmt.setInt(2, Payfines.getLoan_id());

			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt, null);
		}
		
	}
	
	

	
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs)
			throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			
		}
		
		if (myConn != null) {
			myConn.close();
		}
	}

	
		
	private static void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);		
	}

	public static void main(String[] args) throws Exception {
		
		//CheckoutDAO dao = new CheckoutDAO();

		PayfinesDAO p = new PayfinesDAO();
		Payfines pf = new Payfines(61);
		p.payfine(pf);
		System.out.println("\n No CARD_ID\n" );
	
	}
	
}
