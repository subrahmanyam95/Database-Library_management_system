public class Book {
		
	private String ISBN;
	private String Title;
	private String Authors;
	private int Availability;
	
	public Book(String ISBN, String Title, String Authors, int Availability) {
		super();
		this.ISBN = ISBN;
		this.Title = Title;
		this.Authors = Authors;
		this.Availability = Availability;
	}

	
	public String getany(int a){
		if(a == 1){
			return (ISBN);
		}
		if(a==2){
			return (Title);
		}
		if(a==3){
			return (Authors);
		}
		if(a==4){
			return ("Availability = " + Availability);
		}
		
		
		return null;
	}
	
	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String Title) {
		this.Title = Title;
	}

	public String getAuthors() {
		return Authors;
	}

	public void setAuthors(String Authors) {
		this.Authors = Authors;
	}



	public int getAvailability() {
		return Availability;
	}

	public void setAvailability(int Availability) {
		this.Availability = Availability;
	}

	@Override
	public String toString() {
		return String
				.format("ISBN=%s, Title=%s, Authors=%s, Availability=%s \n", ISBN, Title, Authors, Availability);
	}
	
	

}
