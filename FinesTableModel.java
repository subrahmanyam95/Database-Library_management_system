
import java.util.List;

import javax.swing.table.AbstractTableModel;



class FinesTableModel extends AbstractTableModel {
	
	private static final int LOANID_COL = 0;
	private static final int FINEAMT_COL = 1;
	private static final int PAID_COL = 2;


	private String[] columnNames = { "Loan_id", "Fine_amt", "Paid" };
	private List<Fines> fines;

	public FinesTableModel(List<Fines> theFines) {
		fines = theFines;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return fines.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Fines tempFines = fines.get(row);

		switch (col) {
		case LOANID_COL:
			return tempFines.getLoan_id();
		case FINEAMT_COL:
			return tempFines.getFine_amt();
		case PAID_COL:
			return tempFines.getPaid();
		default:
			return tempFines.getLoan_id();
		}

	}

	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
