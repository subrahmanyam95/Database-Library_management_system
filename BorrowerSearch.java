import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;


public class BorrowerSearch extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField cardtext;
	private JTextField bnametext;
	private JTextField ssntext;
	private JTable table;
	
	private BorrowerDAO borrowerDAO;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BorrowerSearch frame = new BorrowerSearch();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BorrowerSearch() {
		
		try {
			borrowerDAO = new BorrowerDAO();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,  "Error2:" + e, "Error22", JOptionPane.ERROR_MESSAGE);
		} 
		
		setTitle("Borrower Search App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 819, 514);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(304, 23, 247, 25);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);
		//contentPane.add(panel);
		
		JLabel lblCard = new JLabel("CARD ID");
		lblCard.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblCard);
		
		cardtext = new JTextField();
		panel.add(cardtext);
		cardtext.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(36, 23, 223, 25);
		flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_1, BorderLayout.NORTH);
		//contentPane.add(panel_1);
		
		JLabel lblBname = new JLabel("Bname");
		lblBname.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(lblBname);
		
		bnametext = new JTextField();
		bnametext.setColumns(10);
		panel_1.add(bnametext);
		

		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(36, 59, 223, 25);
		flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_2, BorderLayout.NORTH);
		//contentPane.add(panel_2);
		
		JLabel lblSSN = new JLabel("SSN");
		lblSSN.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(lblSSN);
		
		ssntext = new JTextField();
		ssntext.setColumns(10);
		panel_2.add(ssntext);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(153, 95, 89, 25);
		contentPane.add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 136, 502, 280);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);
		
		

		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try{
					String CARD_ID = cardtext.getText();
					String Bname = bnametext.getText();
					String SSN = ssntext.getText();
					
					System.out.println("CARD_ID is " +CARD_ID);
					System.out.println("Bname is " +Bname);
					System.out.println("SSN is " +SSN);
					
					List<Borrower> borrowers = null;
										
					if(SSN.isEmpty())
						 SSN = "";
					
					if(Bname.isEmpty())
						Bname ="";
					
					if(CARD_ID.isEmpty())
						{
						borrowers = borrowerDAO.searchBorrower(Bname, SSN);
						System.out.println("Bname isnt " +Bname);
						System.out.println("SSN is " +SSN);
						}
					
					if(!CARD_ID.isEmpty()){
						int CARD = Integer.parseInt(CARD_ID);
						borrowers = borrowerDAO.searchBorrowers(CARD, Bname, SSN);
					}
					//else 
					//	borrowers = borrowerDAO.getAllBorrowers();
					
						for (Borrower temp : borrowers){
						System.out.println(temp);
					}
						
						
						
						BorrowerTableModel model = new BorrowerTableModel(borrowers);
						
						table.setModel(model);
				}
					catch(Exception e){
						JOptionPane.showMessageDialog(BorrowerSearch.this,  "Error1:" + e, "Error11", JOptionPane.ERROR_MESSAGE);
						System.out.println(e);
					}
			}
		});
		//panel.add(btnSearch);
		JButton btnAddBorrower = new JButton("Add Borrower");
		btnAddBorrower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// create dialog
				AddBorrower dialog;
				try {
					dialog = new AddBorrower(BorrowerSearch.this, borrowerDAO);
					
					
					// show dialog
					dialog.setVisible(true);
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}
		});
		btnAddBorrower.setBounds(541, 228, 151, 25);
		contentPane.add(btnAddBorrower);
		
		
	}

	public void refreshBorrowersView() {
		try {
			List<Borrower> borrowers = borrowerDAO.getAllBorrowers();

			// create the model and update the "table"
			BorrowerTableModel model = new BorrowerTableModel(borrowers);

			table.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
}

