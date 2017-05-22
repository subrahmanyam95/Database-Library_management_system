
import java.util.*;

import javax.swing.JOptionPane;

import java.sql.*;
import java.sql.Date;
import java.time.Instant;
import java.io.*;
import java.math.*;



public class FinesDAO {
	
private static Connection myConn;
	
	public FinesDAO() throws Exception {
		
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
	
	
	
	public List<Fines> getAllFiness() throws Exception {
		
		List<Fines> list = new ArrayList<>();
		List<Fines> list1 = new ArrayList<>();
		Statement myStmt = null;
		Statement myStmt4 = null;
		
		PreparedStatement myStmt1 = null;
		PreparedStatement myStmt2 = null;
		PreparedStatement myStmt3 = null;
		ResultSet myRs = null;
		ResultSet myRs1 = null;

	try {
			// prepare statement

			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("Select * from book_loans where NOT exists (Select * from Fines where book_loans.Loan_id = Fines.Loan_id);");
			


			while (myRs.next()) {
				Fines tempFine = convertRowToFine(myRs);
				
				if(tempFine!=null)
				list.add(tempFine);
				   java.sql.Date Date_in= myRs.getDate("Date_in");
					java.sql.Date Due_Date= myRs.getDate("Due_Date");
				   
				if(Date_in != null){
					
				myStmt1 = myConn.prepareStatement("insert into Fines(Loan_id, Fine_amt) Values(?, ?)");	
				

				int Loan_id = myRs.getInt("Loan_id");
				int f= da.comparedates(Due_Date, Date_in);
				Double Fine_amt = 0.25*f;
				myStmt1.setDouble(2, Fine_amt);
				myStmt1.setInt(1, Loan_id);
				System.out.println(Loan_id +"  \t" +Due_Date);
				myStmt1.executeUpdate();
				}
				else {
					//System.out.println(Date_in+ "\t"+ Due_Date);
					myStmt1 = myConn.prepareStatement("insert into Fines(Loan_id, Fine_amt) Values(?, ?)");	
					
				//	java.sql.Date Due_Date= myRs.getDate("Due_Date");
					   java.util.Date now = new java.util.Date();
			           java.sql.Date Datenow = new java.sql.Date(now.getTime());
			           System.out.println(Date_in+ "\t"+ Due_Date + "\t" + Datenow);
					int Loan_id = myRs.getInt("Loan_id");
					int f= da.comparedates(Due_Date, Datenow);
					System.out.println(f);
					Double Fine_amt = 0.25*f;
					System.out.println(Fine_amt);
					myStmt1.setInt(1, Loan_id);
					myStmt1.setDouble(2, Fine_amt);
					//System.out.println(myRs.getDate("Due_Date"));
					myStmt1.executeUpdate();
					
				}
			}


			
			myStmt4 = myConn.createStatement();
			myRs1 = myStmt4.executeQuery("Select * from book_loans");
			
				while (myRs1.next()) {
				Fines tempFine1 = convertRowToFine(myRs1);
				
				if(tempFine1!=null){
				list1.add(tempFine1);}
				
				   java.sql.Date Date_in= myRs1.getDate("Date_in");
					java.sql.Date Due_Date= myRs1.getDate("Due_Date");
				   
					if(Date_in != null){
				myStmt3 = myConn.prepareStatement(" update fines,book_loans set Fine_amt = ? where book_loans.loan_id=fines.loan_id and paid= 0 and Fine_amt != ? and fines.Loan_id = ? ");	
			

			int Loan_id = myRs1.getInt("Loan_id");
			int f= da.comparedates(Due_Date, Date_in);
			Double Fine_amt = 0.25*f;
			System.out.println(Loan_id+ "\t"+Fine_amt);
			myStmt3.setDouble(1, Fine_amt);
			myStmt3.setDouble(2, Fine_amt);
			myStmt3.setInt(3, Loan_id);
		//	System.out.println(myRs1.getDate("Due_Date"));
			myStmt3.executeUpdate();
				} else {
					
					myStmt3 = myConn.prepareStatement(" update fines,book_loans set Fine_amt = ? where book_loans.loan_id=fines.loan_id and paid= 0 and Fine_amt != ? and fines.Loan_id = ? ");	
					

					int Loan_id = myRs1.getInt("Loan_id");
					   java.util.Date now = new java.util.Date();
			           java.sql.Date Datenow = new java.sql.Date(now.getTime());
					int f= da.comparedates(Due_Date, Datenow);
					Double Fine_amt = 0.25*f;
					myStmt3.setDouble(1, Fine_amt);
					myStmt3.setDouble(2, Fine_amt);
					myStmt3.setInt(3, Loan_id);
					//System.out.println(myRs1.getDate("Due_Date"));
					System.out.println(Loan_id+ "\t"+Fine_amt);
					myStmt3.executeUpdate();
					
					
					myStmt2 = myConn.prepareStatement(" Delete from Fines where Fine_amt =0");	
					myStmt2.executeUpdate();
					
				}
				}
			return list;	
			
		}
		finally {
			close(myStmt, myRs);
		}
		
	}
	
	
	
public Boolean searchFines(int a) throws Exception {
		
		Statement myStmt = null;
	//	List<boolean> a = new List<boolean>();
		PreparedStatement myStmt1 = null;

		ResultSet myRs = null;
		List<Boolean> list=new ArrayList<Boolean>();

	try {
			// prepare statement
	
				myStmt1 = myConn.prepareStatement("select Paid from Fines as F, Book_loans as B where B.card_id= ? AND B.Loan_id= F.Loan_id");	
				
				myStmt1.setInt(1, a);
	
				myRs = myStmt1.executeQuery();
				int z1=0;
				
				while (myRs.next()) {
					//System.out.println(myRs);
					boolean z = convertRowToPaid(myRs);
					list.add(z);
				}
				
				for(boolean tem: list){
					if(tem){
						}
					else z1++;
				}

				//System.out.println(z1);
				//	boolean p = convertRowToPaid(myRs);
			
					if(z1==0){
			return true;	}
					else return false;
			
		}
		finally {
			close(myStmt, myRs);
		}
		
	}
	
	
	
	


	private boolean convertRowToPaid(ResultSet myRs) throws SQLException {
	// TODO Auto-generated method stub
		boolean a = myRs.getBoolean("Paid");
	return a;
}



	CheckoutDAO da= new CheckoutDAO();

	
	private Fines convertRowToFine(ResultSet myRs) throws Exception {
		// TODO Auto-generated method stub
		java.sql.Date Date_in= myRs.getDate("Date_in");
		if(Date_in != null){
			
		   java.util.Date no = new java.util.Date();
           java.sql.Date now = new java.sql.Date(no.getTime());
		   java.sql.Date Due_Date= myRs.getDate("Due_Date");
		   java.sql.Date Date_out= myRs.getDate("Date_out");
		int Loan_id = myRs.getInt("Loan_id");
		int f= da.comparedates(Due_Date, Date_in);
		//System.out.println(Loan_id +"\t" + Date_out + "\t" + Due_Date +"\t" + Date_in);System.out.println();
		//System.out.println(f);
		Double Fine_amt = 0.25*f;
		boolean Paid = false;
		
		if(f>0){
		Fines tempFines = new Fines(Loan_id, Fine_amt, Paid);
		//System.out.println(myRs.getDate("Due_Date"));
		return tempFines;
		}
		else return null;
	
	}
	
	else return null;
	
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
		
		FinesDAO fd = new FinesDAO();
		System.out.println(fd.getAllFiness());
		
		List<Fines> fines = null;
		fines = fd.getAllFiness();
		
	//	fd.searchFines(4);

		for (Fines temp : fines){
		System.out.println(temp);
	}
		
	}
	
}
