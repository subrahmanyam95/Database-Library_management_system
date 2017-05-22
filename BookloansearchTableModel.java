

import java.util.List;

import javax.swing.table.AbstractTableModel;



class BookloansearchTableModel extends AbstractTableModel {
	
	private static final int LOANID_COL = 0;
	private static final int ISBN_COL = 1;
	private static final int CARDID_COL = 2;


	private String[] columnNames = { "Loan_id", "ISBN", "Card_id" };
	private List<Bookloansearch> bookloansearch;

	public BookloansearchTableModel(List<Bookloansearch> theBookloansearch) {
		bookloansearch = theBookloansearch;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return bookloansearch.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Bookloansearch tempBookloan = bookloansearch.get(row);

		switch (col) {
		case ISBN_COL:
			return tempBookloan.getISBN();
		case LOANID_COL:
			return tempBookloan.getLoan_id();
		case CARDID_COL:
			return tempBookloan.getCard_id();
		
		default:
			return tempBookloan.getLoan_id();
		}

	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
