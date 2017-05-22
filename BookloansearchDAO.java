
import java.util.*;

import javax.swing.JOptionPane;

import java.sql.*;
import java.sql.Date;
import java.time.Instant;
import java.io.*;
import java.math.*;

public class  BookloansearchDAO {
	
private static Connection myConn;
	
	public  BookloansearchDAO() throws Exception {
		

		
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
	

	
	public List<Bookloansearch> getAllBookloansearch() throws Exception {
		List<Bookloansearch> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from Book_loans");
			
			while (myRs.next()) {
				Bookloansearch tempBookloanearch = convertRowToBookloansearchs(myRs);
				list.add(tempBookloanearch);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Bookloansearch> searchBookloansearch(String ISBN) throws Exception{

		List<Bookloansearch> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			
			ISBN ="%" + ISBN + "%";
			myStmt = myConn.prepareStatement("select * from Book_Loans, Borrower where ISBN like ? AND Borrower.Card_id=Book_loans.Card_id");
			
			myStmt.setString(1, ISBN);
		

			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Bookloansearch tempBookloansearchs = convertRowToBookloansearchs(myRs);
				list.add(tempBookloansearchs);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	

	
	List<Bookloansearch> searchBookloansearchs( String ISBN, int Card_id) throws Exception {
		List<Bookloansearch> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			//Card_id = "%" + Card_id + "%";
			ISBN ="%" + ISBN + "%";
			myStmt = myConn.prepareStatement("select * from Book_Loans, Borrower where (ISBN like ? AND book_loans.Card_id like ? )AND Borrower.Card_id=Book_loans.Card_id");
			
			myStmt.setString(1, ISBN);
			myStmt.setInt(2, Card_id);

			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Bookloansearch tempBookloansearchs = convertRowToBookloansearchs(myRs);
				list.add(tempBookloansearchs);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	


	private Bookloansearch convertRowToBookloansearchs(ResultSet myRs)
			throws SQLException {
		 
		int Card_id = myRs.getInt("Card_id");
		String ISBN = myRs.getString("ISBN");
		int Loan_id = myRs.getInt("Loan_id");
		
		Bookloansearch tempBookloan = new Bookloansearch(Loan_id, ISBN, Card_id);
		
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
		

		BookloansearchDAO blo = new BookloansearchDAO();
		
	
		System.out.println(blo.getAllBookloansearch());
		
		String isb ="9780806521213";
		String Bn = "John";
		int c = 3;
		Bookloansearch bls = new Bookloansearch(isb, c);
		
		//System.out.println(bls.get(1));

		
		System.out.println();
		System.out.println();
		System.out.println(blo.searchBookloansearchs(isb, c));
		
		System.out.println();
		System.out.println();
		System.out.println(blo.searchBookloansearch(isb));
		

	}
}


