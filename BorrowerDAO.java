
import java.util.*;


import java.sql.*;
import java.io.*;
import java.math.*;



public class BorrowerDAO {
	
private static Connection myConn;
	
	public BorrowerDAO() throws Exception {
		

		
		// get db properties
		Properties props = new Properties();
		props.load(new FileInputStream("demo.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
		
		// connect to database
		myConn = DriverManager.getConnection(dburl, user, password);
	/*	
		String user = "root";
		String password = "Getlost";
		String dburl = "jdbc:mysql://localhost:3306/library";
		*/
		// connect to database
		myConn = DriverManager.getConnection(dburl, user, password);
	//	myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Getlost");
		
		System.out.println("DB connection successful to: " + dburl);
	}
	
	
	public static void addBorrower(Borrower theBorrower) throws Exception {
		PreparedStatement myStmt = null;

		
		String SSN = theBorrower.getSSN();
		String Bname = theBorrower.getBname();
		String Add = theBorrower.getAddress();
		if(SSN != "" & Bname!= "" & Add != ""){
		
		try {
			// prepare statement
			myStmt = myConn.prepareStatement("insert into borrower"
					+ " (CARD_ID, Bname, SSN, Address, Phone, Books_Borrowed)"
					+ " values (?, ?, ?, ?, ?, ?)");
			
			// set params
			myStmt.setInt(1, theBorrower.getCARD_ID());
			myStmt.setString(2, theBorrower.getBname());
			myStmt.setString(3, theBorrower.getSSN());
			myStmt.setString(4, theBorrower.getAddress());
			myStmt.setString(5, theBorrower.getPhone());
			myStmt.setInt(6, theBorrower.getBooks_Borrowed());
			
			// execute SQL
			myStmt.executeUpdate();			
			System.out.println("Borrower added");
		}
		finally {
			close(myStmt, null);
		}
		
	} else System.out.println("Hehe");
	
	}
	
	public List<Borrower> getAllBorrowers() throws Exception {
		List<Borrower> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from Borrower");
			
			while (myRs.next()) {
				Borrower tempBorrower = convertRowToBorrower(myRs);
				list.add(tempBorrower);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	
	public List<Borrower> searchBorrower(String Bname, String SSN) throws Exception{
		
		if(Bname.isEmpty())
			Bname="";
		
		if(SSN.isEmpty())
			SSN ="";

		List<Borrower> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {

			SSN = "%" + SSN +"%";
			Bname = "%" + Bname + "%";

			myStmt = myConn.prepareStatement("select * from Borrower where Bname like ? AND SSN Like ?");
			
			myStmt.setString(1, Bname);
			myStmt.setString(2, SSN);


			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Borrower tempBorrower = convertRowToBorrower(myRs);
				list.add(tempBorrower);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	

	
	List<Borrower> searchBorrowers( int CARD_ID, String Bname, String SSN) throws Exception {
		List<Borrower> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			//CARD_ID = "%" + CARD_ID +"%";
			SSN = "%" + SSN +"%";
			Bname = "%" + Bname + "%";

			myStmt = myConn.prepareStatement("select * from Borrower where Bname like ? AND SSN Like ? AND CARD_ID like ?");
			
			myStmt.setString(1, Bname);
			myStmt.setString(2, SSN);
			myStmt.setInt(3, CARD_ID);

			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Borrower tempBorrower = convertRowToBorrower(myRs);
				list.add(tempBorrower);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	

	private Borrower convertRowToBorrower(ResultSet myRs) throws SQLException {
		
		int CARD_ID = myRs.getInt("CARD_ID");
		String Bname = myRs.getString("Bname");
		String SSN = myRs.getString("SSN");
		String Address = myRs.getString("Address");
		String Phone = myRs.getString("Phone");
		int Books_Borrowed = myRs.getInt("Books_Borrowed");
		
		Borrower tempBorrower = new Borrower(CARD_ID, Bname, SSN, Address, Phone, Books_Borrowed);
		
		return tempBorrower;
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
		
		BorrowerDAO dao = new BorrowerDAO();
		System.out.println(dao.searchBorrowers(7, "Mummies", ""));
		System.out.println("\n No CARD_ID\n");
		System.out.println(dao.searchBorrower("Harper", ""));

		List<Borrower> a = dao.searchBorrowers(7, "Mummies", "");
		if(dao.searchBorrower("", "906-63-3588") != a){
			//System.out.println("Doesnt exists");
			//System.out.println(dao.searchBorrower("Harper", ""));
		}
		//System.out.println(dao.getAllBorrowers());
		Borrower ac = new Borrower("JohnFK", "", "7760 MCallum", "4695348652" );
		//addBorrower(ac);
		
		System.out.println(dao.searchBorrowers(11,"", ""));
		//System.out.println(dao.getAllBorrowers());
		List<Borrower> list1 = new ArrayList<>();
		list1 = dao.searchBorrowers(11, "", "");
		System.out.println(list1.get(0));
		if(list1.get(0).getany(1)>3){
		System.out.println("HAHA "+list1.get(0).getany(1));
		}
		
	}

}
