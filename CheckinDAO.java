
import java.util.*;

import javax.swing.JOptionPane;

import java.sql.*;
import java.sql.Date;
import java.time.Instant;
import java.io.*;
import java.math.*;



public class CheckinDAO {
	
private static Connection myConn;
	
	public CheckinDAO() throws Exception {
		
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
	
	
	public void checkin(Bookloans theBookloan) throws Exception {
		PreparedStatement myStmt = null;
		PreparedStatement myStmt1 = null;
		PreparedStatement myStmt2 = null;

		try {
			// prepare statement

			myStmt = myConn.prepareStatement("update book_loans"
					+ " set Date_in = ? "
					+ " Where loan_id = ?");
			
			
			//update book_loans set Date_in = now() where loan_id=2;
			// set params		
				int l = theBookloan.getLoan_id();
			   java.util.Date now = new java.util.Date();
	           java.sql.Date sqlDate = new java.sql.Date(now.getTime());

				myStmt.setDate(1, sqlDate);
				myStmt.setInt(2, l);

			
			// execute SQL
			myStmt.executeUpdate();			
			

			List<Bookloansearch> listser = new ArrayList<>();
			
			PreparedStatement myStmt0 = null;
			ResultSet myRs0 = null;
			
			
				myStmt0 = myConn.prepareStatement("select * from Book_loans where loan_id = ?");
				myStmt0.setInt(1, l);
				myRs0 = myStmt0.executeQuery();
				
				while (myRs0.next()) {
					Bookloansearch tempBookloan1 = convertRowToBL(myRs0);
					listser.add(tempBookloan1);
				}
				
				
			/*		
					if(listser.get(0).getany(1)!=null){
						System.out.println(listser.get(0).getany(1));
						System.out.println(listser.get(0).getany(2));
						}
			*/
				
				String ISBN = listser.get(0).getany(1);
				String Cd = listser.get(0).getany(2);
					int Card_id = Integer.parseInt(Cd);
					
			myStmt1 = myConn.prepareStatement("Update book"
					+ " SET Availability = 1"
					+ " where ISBN = ?");
			myStmt1.setString(1, ISBN);
			myStmt1.executeUpdate();		
			System.out.println(theBookloan.getISBN());
			
			myStmt2 = myConn.prepareStatement("Update borrower"
					+ " SET Books_Borrowed = Books_Borrowed -1"
					+ " where Card_id = ?");
			myStmt2.setInt(1, Card_id);
			myStmt2.executeUpdate();
			
			
			
			
			
		}
		finally {
			close(myStmt, null);
		}
		
	}
	
	

	
	private Bookloansearch convertRowToBL(ResultSet myRs) throws SQLException {
		// TODO Auto-generated method stub
		Bookloansearch tempBookloan = null;
		int Loan_id = myRs.getInt("Loan_id");
		int Card_id = myRs.getInt("Card_id");
		String ISBN = myRs.getString("ISBN");

		
				tempBookloan = new Bookloansearch(Loan_id, ISBN, Card_id);
		
		return tempBookloan;
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
		
		CheckoutDAO dao = new CheckoutDAO();

		
		System.out.println("\n No CARD_ID\n" );
		
		String is = "9780671870430"; 
		Bookloans bl = new Bookloans(is, 11);
		//dao.checkout(bl);
		//System.out.println(dao.getAllBookloans());			
		bl = new Bookloans(100);
		CheckinDAO dio = new CheckinDAO();
		dio.checkin(bl);
		//System.out.println(dao.getAllBookloans());		
	}
	
}
