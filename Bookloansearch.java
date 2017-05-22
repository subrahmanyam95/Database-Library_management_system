
public class Bookloansearch {
	
	private int Card_id;
	private String ISBN;
	private int Loan_id;

	public Bookloansearch(int l, String ISBN, int c) {
		super();
		this.Loan_id= l;
		this.Card_id= c;
		this.ISBN = ISBN;

	}
	
	
	public Bookloansearch(String ISBN, int c) {
		super();
		this.Card_id= c;
		this.ISBN = ISBN;

	}
	
	public Bookloansearch(int c) {
		super();
		this.Loan_id= c;

	}
	
	
	public String getany(int a){
		if(a == 1){
			return (ISBN);
		}
		else if(a==2){
			return (Integer.toString(Card_id));
		}
		else return null;
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
		
	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	@Override
	public String toString() {
		return String
				.format("Loan_id = %s, ISBN=%s, Card_id=%s \n", Loan_id, ISBN, Card_id);
	}
	
	

}



/*public class Bookloansearch {
	
	private int Card_id;
	private String Bname;
	private String ISBN;

	
	public Bookloansearch(String ISBN, String Bname, int c) {
		super();
		this.Card_id= c;
		this.Bname = Bname;
		this.ISBN = ISBN;

	}


	public int getCard_id() {
		return Card_id;
	}

	public void setCard_id(int Card_id) {
		this.Card_id = Card_id;
	}
		
	public String getBname() {
		return Bname;
	}

	public void setBname(String Bname) {
		this.Bname = Bname;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	@Override
	public String toString() {
		return String
				.format("ISBN=%s, Bname=%s, Card_id=%s \n", ISBN, Bname, Card_id);
	}
	
	

}
*/