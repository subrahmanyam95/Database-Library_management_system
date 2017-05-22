import java.util.*;


import java.sql.*;
import java.sql.Date;
import java.io.*;
import java.math.*;



public class Bookloans {
	
	private String ISBN;
	private Date Date_out;
	private Date Due_Date;
	private Date Date_in;
	private int Card_id;	
	private int Loan_id;	
	

	
	public Bookloans(int Loan_id) {
		super();
		this.Loan_id= Loan_id;
	}
	
	public Bookloans( String ISBN, int Card_id) {
		super();
		Loan_id=0;
		this.ISBN = ISBN;
		this.Card_id = Card_id;
	}
	
	
	public Bookloans( String ISBN, int Card_id, Date Date_out, Date Due_Date, Date Date_in) {
		super();
		Loan_id=0;
		this.ISBN = ISBN;
		this.Date_out = Date_out;
		this.Due_Date = Due_Date;
		this.Card_id = Card_id;
		this.Date_in = Date_in;
	}
	
	public Bookloans( int Loan_id, String ISBN, int Card_id, Date Date_out, Date Due_Date, Date Date_in) {
		super();
		this.Loan_id = Loan_id;
		this.ISBN = ISBN;
		this.Date_out = Date_out;
		this.Due_Date = Due_Date;
		this.Card_id = Card_id;
		this.Date_in = Date_in;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public Date getDate_out() {
		return Date_out;
	}

	public void setDate_out(Date Date_out) {
		this.Date_out = Date_out;
	}

	public Date getDue_Date() {
		return Due_Date;
	}

	public void setDue_Date(Date Due_Date) {
		this.Due_Date = Due_Date;
	}

	public Date getDate_in() {
		System.out.println(Date_in);
		return Date_in;
	}

	public void setDate_in(Date Date_in) {
		this.Date_in = Date_in;
	}

	public int getCard_id() {
		return Card_id;
	}

	public void setCard_id(int Card_id) {
		this.Card_id = Card_id;
	}
	
	public int getLoan_id() {
		return Loan_id;
	}

	public void setLoan_id(int Loan_id) {
		this.Loan_id = Loan_id;
	}

	@Override
	public String toString() {
		return String
				.format("Loan_id=%s, ISBN=%s, Card_id=%s, Date_out=%s, Due_Date=%s, Date_in=%s \n", Loan_id, ISBN, Card_id, Date_out, Due_Date, Date_in);
	}
	
	
	
	

}
