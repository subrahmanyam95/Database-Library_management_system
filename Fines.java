import java.util.*;


import java.sql.*;
import java.sql.Date;
import java.io.*;
import java.math.*;



public class Fines {
	
	private int Loan_id;	
	private Double Fine_amt = 0.00;
	private boolean Paid = false;

	
	public Fines(int Loan_id) {
		super();
		this.Loan_id= Loan_id;
	}
	
	public Fines(int Loan_id,  Double Fine_amt, boolean Paid) {
		super();
		this.Loan_id= Loan_id;
		this.Paid= Paid;
		this.Fine_amt= Fine_amt;
	}
	public int getLoan_id() {
		return Loan_id;
	}

	public void setLoan_id(int Loan_id) {
		this.Loan_id = Loan_id;
	}

	public Double getFine_amt() {
		return Fine_amt;
	}

	public void setFine_amt(double Fine_amt) {
		this.Fine_amt = Fine_amt;
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
				.format("Loan_id=%s, Fine_amt=%s, Paid =%s  \n", Loan_id, Fine_amt, Paid);
	}
	
	
	
	

}
