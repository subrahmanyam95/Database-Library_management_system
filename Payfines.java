import java.util.*;


import java.sql.*;
import java.sql.Date;
import java.io.*;
import java.math.*;



public class Payfines {
	
	private static int Loan_id;	
	private boolean Paid = false;

	
	public Payfines(int Loan_id) {
		super();
		this.Loan_id= Loan_id;
	}
	
	public Payfines(int Loan_id, boolean Paid) {
		super();
		this.Loan_id= Loan_id;
		this.Paid= Paid;
	}
	
	public static int getLoan_id() {
		return Loan_id;
	}

	public void setLoan_id(int Loan_id) {
		this.Loan_id = Loan_id;
	}

	
	public boolean getPaid() {
		return Paid;
	}

	public void setPaid(boolean Paid) {
		this.Paid = Paid;
	}

	
	@Override
	public String toString() {
		return String
				.format("Loan_id=%s, Paid =%s  \n", Loan_id, Paid);
	}
	
		

}
