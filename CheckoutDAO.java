
import java.util.*;

import javax.swing.JOptionPane;

import java.sql.*;
import java.sql.Date;
import java.time.Instant;
import java.io.*;
import java.math.*;



public class CheckoutDAO {
	
private static Connection myConn;
	
	public CheckoutDAO() throws Exception {
		
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
	
	
	public void checkout(Bookloans theBookloan) throws Exception {
		PreparedStatement myStmt = null;
		PreparedStatement myStmt1 = null;
		PreparedStatement myStmt2 = null;
		

		try {
			// prepare statement
			myStmt = myConn.prepareStatement("insert into book_loans"
					+ " (Loan_id, ISBN, CARD_ID, Date_out, Due_Date, Date_in)"
					+ " values (?, ?, ?, ?, ?, ?)");
			
			// set params		
			   java.util.Date now = new java.util.Date();
	           java.sql.Date sqlDate = new java.sql.Date(now.getTime());
	           java.util.Date dd = new java.util.Date( now.getYear(), now.getMonth(), now.getDate() + 14 );
	           java.sql.Date dued = new java.sql.Date(dd.getTime());

			myStmt.setInt(1, theBookloan.getLoan_id());
			myStmt.setString(2, theBookloan.getISBN());
			myStmt.setInt(3, theBookloan.getCard_id());
			myStmt.setDate(4, sqlDate);
			myStmt.setDate(5, dued);
			myStmt.setDate(6, theBookloan.getDate_in());
			// execute SQL
			myStmt.executeUpdate();		
			
			myStmt1 = myConn.prepareStatement("Update book"
					+ " SET Availability = 0"
					+ " where ISBN = ?");
			myStmt1.setString(1, theBookloan.getISBN());
			myStmt1.executeUpdate();		
			
			myStmt2 = myConn.prepareStatement("Update borrower"
					+ " SET Books_Borrowed = Books_Borrowed +1"
					+ " where Card_id = ?");
			myStmt2.setInt(1, theBookloan.getCard_id());
			myStmt2.executeUpdate();
			
		}
		finally {
			close(myStmt, null);
		}
		
	}
	

	public static List<Bookloans> getAllBookloans() throws Exception {
		List<Bookloans> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from Book_loans");
			
			while (myRs.next()) {
				Bookloans tempBookloan = convertRowToBookloans(myRs);
				list.add(tempBookloan);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	

	private static Bookloans convertRowToBookloans(ResultSet myRs) throws SQLException {
		Bookloans tempBookloan = null;
		int Loan_id = myRs.getInt("Loan_id");
		int Card_id = myRs.getInt("Card_id");
		String ISBN = myRs.getString("ISBN");
		Date Date_out = myRs.getDate("Date_out");
		Date Due_Date = myRs.getDate("Due_Date");
		Date Date_in = myRs.getDate("Date_in");
		
				tempBookloan = new Bookloans(Loan_id, ISBN, Card_id, Date_out, Due_Date, Date_in);
		
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

		public int comparedates(java.util.Date da1, java.util.Date da2){
							
			java.util.Date sd = da1;
			java.util.Date ed = da2;
			int d1 = sd.getDate();
			int d2 = ed.getDate();
			int m1 = sd.getMonth();
			int m2 = ed.getMonth();
			if(da2.after(da1)){
				
				if(m2-m1<2){
			
			if(sd.getYear() == ed.getYear()){
				if(m1==m2){
					return (d2-d1);
				}
				else{
					if(m1 == 0 || m1 == 2 || m1 == 4 || m1 == 6 || m1== 7 || m1== 9 || m1== 11){
						return (31-d1+d2);}
					else if(m1 == 1 || m1 == 3 || m1 == 5 || m1== 7 || m1== 10){
						return (30-d1+d2);}
					else if((sd.getYear()%4) != 0){
						return (28-d1+d2);
					}
					else return (29-d1+d2);
				}
					}
				
			else return 31-d1+d2;}
				else {
					java.util.Date md = new java.util.Date( da1.getYear(), da1.getMonth() +1, da1.getDate() );
				//	System.out.println("HAHA"+md);
					
					if(m1 == 0 || m1 == 2 || m1 == 4 || m1 == 6 || m1== 7 || m1== 9 || m1== 11){
						return (31+comparedates(md,da2));}
					else if(m1 == 1 || m1 == 3 || m1 == 5 || m1== 7 || m1== 10){
						return (30+comparedates(md,da2));}
					else if((sd.getYear()%4) != 0){
						return (28+comparedates(md,da2));
					}
					else return (29+comparedates(md,da2));
					
				}
			}
			else return 0;
		}
	
	
	public int comparedates(Date da1, Date da2){
		
		java.util.Date sd = da1;
		java.util.Date ed = da2;
		int d1 = sd.getDate();
		int d2 = ed.getDate();
		int m1 = sd.getMonth();
		int m2 = ed.getMonth();
		
		if(da2.after(da1)){
			
			if(m2-m1<2){
			
			
		if(sd.getYear() == ed.getYear()){
			if(m1==m2){
				return (d2-d1);
			}
			else{
				if(m1 == 0 || m1 == 2 || m1 == 4 || m1 == 6 || m1== 7 || m1== 9 || m1== 11){
					return (31-d1+d2);}
				else if(m1 == 1 || m1 == 3 || m1 == 5 || m1== 7 || m1== 10){
					return (30-d1+d2);}
				else if((sd.getYear()%4) != 0){
					return (28-d1+d2);
				}
				else return (29-d1+d2);
				}
			}
		else return 31-d1+d2;}
			
			else {
				Date md = new Date( da1.getYear(), da1.getMonth() +1, da1.getDate() );
				System.out.println("HAHA"+md);
				
				if(m1 == 0 || m1 == 2 || m1 == 4 || m1 == 6 || m1== 7 || m1== 9 || m1== 11){
					return (31+comparedates(md,da2));}
				else if(m1 == 1 || m1 == 3 || m1 == 5 || m1== 7 || m1== 10){
					return (30+comparedates(md,da2));}
				else if((sd.getYear()%4) != 0){
					return (28+comparedates(md,da2));
				}
				else return (29+comparedates(md,da2));
				
			}
		}
		else return 0;
	}
	
		
	private static void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);		
	}

	public static void main(String[] args) throws Exception {
		
		CheckoutDAO dao = new CheckoutDAO();
		BorrowerDAO bdao = new BorrowerDAO();
		System.out.println("\n No CARD_ID\n" );
		
		String is = "9780671870430"; 
		Bookloans bl = new Bookloans(is, 11);
		dao.checkout(bl);
		List<Borrower> list1 = new ArrayList<>();
		list1 = bdao.searchBorrowers(11, "", "");
		System.out.println(list1.get(0));
		if(list1.get(0).getany(1)<3){
		System.out.println("HAHA "+list1.get(0).getany(1));
		}
		else System.out.println("No HAHA "+list1.get(0).getany(1));

		
	
		//System.out.println(dao.getAllBookloans());

		
		   //java.util.Date now = new java.util.Date();
           //java.sql.Date sqlDate = new java.sql.Date(now.getTime());

          // System.out.println("Value of todays Date : " + now);
           //System.out.println("Value of tomorrows's Date : " + tomorrow);
		 /* 
		
		   java.util.Date now = new java.util.Date();
           java.sql.Date sqlDate = new java.sql.Date(now.getTime());
           java.util.Date dd = new java.util.Date( now.getYear(), now.getMonth(), now.getDate() + 15 );
           java.sql.Date dued = new java.sql.Date(dd.getTime());
           java.util.Date di = new java.util.Date( now.getYear(), now.getMonth(), now.getDate() + 56 );
           java.sql.Date din = new java.sql.Date(di.getTime());
          
           System.out.println(now);
           System.out.println(dd);
           System.out.println(di);
           
            
           System.out.println(dao.comparedates(sqlDate,dued));
           System.out.println(dao.comparedates(dued,din));
           System.out.println(dao.comparedates(sqlDate,din));
           System.out.println(dao.comparedates(din, sqlDate));
              System.out.println(dued.getMonth());
           System.out.println(din.getMonth());
           System.out.println(dd.getMonth());
           System.out.println(di.getMonth());
        
           System.out.println(dao.comparedates(now,dd));
           System.out.println(dao.comparedates(dd,di));
           System.out.println(dao.comparedates(now,di));
           System.out.println(dao.comparedates(di, now));	*/
         //  System.out.println(di.getMonth());
          // if(di.getMonth()==4){
        	 //  System.out.println((30 + di.getDate()) - dd.getDate());
           //}
		
		//Bookloan ac = new Bookloan("JohnFK", "469543835", "7760 MCallum", "4695348652" );
		//addBookloan(ac);
		//String Cd = "11";
		/*int Cad = Integer.parseInt(Cd);
		System.out.println(Cd);
		System.out.println(Cd+1);
		System.out.println(Cad);
		System.out.println(Cad+1);
		
		String ISBN = "9780771074677";

		
		int Card_id = Integer.parseInt(Cd);
		Bookloans tempCheckout = new Bookloans(ISBN, 13);
		checkout(tempCheckout);
		System.out.println(dao.getAllBookloans());
		*/
	}
	
}
