import java.util.*;


import java.sql.*;
import java.io.*;
import java.math.*;



public class BooksearchDAO {
	
private Connection myConn;
	
	public BooksearchDAO() throws Exception {
		
		Properties props = new Properties();
		props.load(new FileInputStream("demo.properties"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String dburl = props.getProperty("dburl");
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
	
	public List<Book> getAllBooks() throws Exception {
		List<Book> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from Book");
			
			while (myRs.next()) {
				Book tempBook = convertRowToBook(myRs);
				list.add(tempBook);
			}

			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	
	public List<Book> searchBook(String ISBN, String Title, String Authors) throws Exception{
		if(Authors.isEmpty())
			 Authors = "";
		
		if(ISBN.isEmpty())
			ISBN="";
		
		if(Title.isEmpty())
			Title ="";
		
		return searchBooks(ISBN, Title, Authors);
	}
	

	
	List<Book> searchBooks( String ISBN, String Title, String Authors) throws Exception {
		List<Book> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			Title = "%" + Title +"%";
			Authors = "%" + Authors + "%";
			ISBN ="%" + ISBN + "%";
			myStmt = myConn.prepareStatement("select * from Book where ISBN like ? AND Title Like ? AND Authors like ?");
			
			myStmt.setString(1, ISBN);
			myStmt.setString(2, Title);
			myStmt.setString(3, Authors);

			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Book tempBook = convertRowToBook(myRs);
				list.add(tempBook);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	

	private Book convertRowToBook(ResultSet myRs) throws SQLException {
		
		String ISBN = myRs.getString("ISBN");
		String Title = myRs.getString("Title");
		String Authors = myRs.getString("Authors");
		int Availability = myRs.getInt("Availability");
		
		Book tempBook = new Book(ISBN, Title, Authors, Availability);
		
		return tempBook;
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

	private void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);		
	}

	public static void main(String[] args) throws Exception {
		
		BooksearchDAO dao = new BooksearchDAO();
		System.out.println(dao.searchBook("97", "Mummies", ""));
		List<Book> list = new ArrayList<>();
		List<Book> list1 = new ArrayList<>();
		list = dao.searchBook("97", "Mummies", "");
		list1 = dao.searchBook("9780671870430", "", "");
		list.addAll(list1);
		if(!list.get(1).getany(4).equals("Availability = 1")){
		System.out.println(list.get(1).getany(4));
		}
		
		/*
		list = dao.searchBook("97", "Mummies", "");
		String qw = list;
		System.out.println(list.get(0));
		
		
		
		for(String str: list) {
		    if(str.trim().equals("A"))
		       return true;
		}
		return false;
		
		*/

		//System.out.println(dao.getAllBooks());
	}

}
