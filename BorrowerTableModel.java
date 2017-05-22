import java.util.List;

import javax.swing.table.AbstractTableModel;



class BorrowerTableModel extends AbstractTableModel {

	private static final int CARD_ID_COL = 0;
	private static final int BNAME_COL = 1;
	private static final int SSN_COL = 2;
	private static final int ADDRESS_COL = 3;
	private static final int PHONE_COL = 4;
	private static final int BOOKS_BORROWED_COL = 5;
	

	private String[] columnNames = { "CARD_ID", "Bname", "SSN", "Address", "Phone", "Books_Borrowed" };
	private List<Borrower> borrowers;

	public BorrowerTableModel(List<Borrower> theBorrowers) {
		borrowers = theBorrowers;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return borrowers.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Borrower tempBorrower = borrowers.get(row);

		switch (col) {
		case CARD_ID_COL:
			return tempBorrower.getCARD_ID();
		case BNAME_COL:
			return tempBorrower.getBname();
		case SSN_COL:
			return tempBorrower.getSSN();
		case PHONE_COL:
			return tempBorrower.getPhone();
		case ADDRESS_COL:
			return tempBorrower.getAddress();
		case BOOKS_BORROWED_COL:
			return tempBorrower.getBooks_Borrowed();
		default:
			return tempBorrower.getBname();
		}

	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
