public class Borrower {
	
	private int CARD_ID=0;
	private String Bname;
	private String SSN;
	private String Address;
	private String Phone;
	private int Books_Borrowed;
	
	
	public Borrower(String Bname, String SSN, String Address, String Phone) {
		super();
		CARD_ID= 0;
		this.Bname = Bname;
		this.SSN = SSN;
		this.Address = Address;
		this.Phone = Phone;
		//Books_Borrowed=0;
	}

	public Borrower(int CARD_ID, String Bname, String SSN, String Address, String Phone, int Books_Borrowed) {
		super();
		this.CARD_ID= CARD_ID;
		this.Bname = Bname;
		this.SSN = SSN;
		this.Address = Address;
		this.Phone = Phone;
		this.Books_Borrowed = Books_Borrowed;
	}

	public int getany(int a){
		if(a == 1){
			return (Books_Borrowed);
		}
		else return 0;
		}
	
	public int getCARD_ID() {
		return CARD_ID;
	}

	public void setCARD_ID(int CARD_ID) {
		this.CARD_ID = CARD_ID;
	}
		
	public String getBname() {
		return Bname;
	}

	public void setBname(String Bname) {
		this.Bname = Bname;
	}

	public String getSSN() {
		return SSN;
	}

	public void setSSN(String SSN) {
		this.SSN = SSN;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String Address) {
		this.Address = Address;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String Phone) {
		this.Phone = Phone;
	}



	public int getBooks_Borrowed() {
		return Books_Borrowed;
	}

	public void setBooks_Borrowed(int Books_Borrowed) {
		this.Books_Borrowed = Books_Borrowed;
	}

	@Override
	public String toString() {
		return String
				.format("CARD_ID=%s, Bname=%s, SSN=%s, Address=%s, Phone=%s, Books_Borrowed=%s \n", CARD_ID, Bname, SSN, Address, Phone, Books_Borrowed);
	}
	
	

}
