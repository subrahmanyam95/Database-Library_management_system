import java.util.*;

import javax.swing.JOptionPane;

import java.sql.*;
import java.sql.Date;
import java.time.Instant;
import java.io.*;
import java.math.*;



public class ShowFinesDAO {
	
private static Connection myConn;
	
	public ShowFinesDAO() throws Exception {
		
		Properties props = new Properties();
		props.load(new FileInputStream("demo.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		/*
		
		
		String user = "root";
		String password = "hello123";
		String dburl = "jdbc:mysql://localhost:3306/library?zeroDateTimeBehavior=convertToNull";
		*/
		// connect to database
		myConn = DriverManager.getConnection(dburl, user, password);
	//	myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "hello123");
		
		System.out.println("DB connection successful to: " + dburl);
	}
	
	
	
	public List<Fines> getAllFines() throws Exception {
		
		List<Fines> list = new ArrayList<>();
		Statement myStmt = null;

		ResultSet myRs = null;

	try {
			// prepare statement

			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("Select * from Fines");

			while (myRs.next()) {
				Fines tempFine = convertRowToFine(myRs);
				if(tempFine!=null)
				list.add(tempFine);
				 		
				}

			return list;	
			
		}
		finally {
			close(myStmt, myRs);
		}
		
	}
	


	//CheckoutDAO da= new CheckoutDAO();

	
	private Fines convertRowToFine(ResultSet myRs) throws Exception {
		// TODO Auto-generated method stub

		int Loan_id = myRs.getInt("Loan_id");
		boolean Paid = myRs.getBoolean("Paid");
		double Fine_amt = myRs.getDouble("Fine_amt");

		Fines tempFines = new Fines(Loan_id, Fine_amt, Paid);
		return tempFines;

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
		
		ShowFinesDAO fd = new ShowFinesDAO();
		System.out.println(fd.getAllFines());
		
		List<Fines> fines = null;
		fines = fd.getAllFines();

		for (Fines temp : fines){
		System.out.println(temp);
	}
		
	}
	
}
