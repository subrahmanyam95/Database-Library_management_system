
import java.util.List;

import javax.swing.table.AbstractTableModel;



class BookloanTableModel extends AbstractTableModel {
	
	private static final int LOANID_COL = 0;
	private static final int ISBN_COL = 1;
	private static final int CARDID_COL = 2;
	private static final int DATEOUT_COL = 3;
	private static final int DUEDATE_COL = 4;
	private static final int DATEIN_COL = 5;

	private String[] columnNames = { "Loan_id", "ISBN", "Card_id", "Date_out", "Due_Date", "Date_in" };
	private List<Bookloans> bookloans;

	public BookloanTableModel(List<Bookloans> theBookloans) {
		bookloans = theBookloans;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return bookloans.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Bookloans tempBookloan = bookloans.get(row);

		switch (col) {
		case ISBN_COL:
			return tempBookloan.getISBN();
		case LOANID_COL:
			return tempBookloan.getLoan_id();
		case CARDID_COL:
			return tempBookloan.getCard_id();
		case DATEOUT_COL:
			return tempBookloan.getDate_out();
		case DATEIN_COL:
			return tempBookloan.getDate_in();
		case DUEDATE_COL:
			return tempBookloan.getDue_Date();
		
		default:
			return tempBookloan.getISBN();
		}

	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
