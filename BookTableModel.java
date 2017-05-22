
import java.util.List;

import javax.swing.table.AbstractTableModel;



class BookTableModel extends AbstractTableModel {

	private static final int ISBN_COL = 0;
	private static final int TITLE_COL = 1;
	private static final int AUTHORS_COL = 2;
	private static final int AVAILABILITY_COL = 3;

	private String[] columnNames = { "ISBN", "Title", "Authors", "Availability" };
	private List<Book> books;

	public BookTableModel(List<Book> theBooks) {
		books = theBooks;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return books.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Book tempBook = books.get(row);

		switch (col) {
		case ISBN_COL:
			return tempBook.getISBN();
		case TITLE_COL:
			return tempBook.getTitle();
		case AUTHORS_COL:
			return tempBook.getAuthors();
		case AVAILABILITY_COL:
			return tempBook.getAvailability();
		default:
			return tempBook.getTitle();
		}

	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
