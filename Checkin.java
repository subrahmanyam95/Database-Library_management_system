import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Checkin extends JFrame {

	private JPanel contentPane;
	private JTextField textcardid;
	private JTextField textISBN;
	private JTextField textloanid;
	private JTable table;
	private BookloansearchDAO bookloansearchDAO;
	private Component bookloans;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Checkin frame = new Checkin();
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
	public Checkin() {
		setTitle("Check In  App");
		
	
		 
			try {
				bookloansearchDAO = new BookloansearchDAO();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this,  "Error2:" + e, "Error22", JOptionPane.ERROR_MESSAGE);
			} 
			
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 649, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(346, 62, 202, 35);
		contentPane.add(panel);
		
		JLabel lblCardid = new JLabel("Card_id");
		panel.add(lblCardid);
		
		textcardid = new JTextField();
		panel.add(textcardid);
		textcardid.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(14, 62, 189, 35);
		contentPane.add(panel_1);
		
		JLabel lblIsbn = new JLabel("ISBN");
		panel_1.add(lblIsbn);
		
		textISBN = new JTextField();
		textISBN.setColumns(10);
		panel_1.add(textISBN);
		

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 140, 575, 211);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBackground(Color.LIGHT_GRAY);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				try{
					int Card_id;
					String ISBN = textISBN.getText();
					String Card = textcardid.getText();
					
					List<Bookloansearch> bookloansearch = null;
					
					if(ISBN.isEmpty())
						 ISBN = "";
					
					
					if(Card.isEmpty() & ISBN.isEmpty())
						bookloansearch = bookloansearchDAO.getAllBookloansearch();

					if(Card.isEmpty())
						bookloansearch = bookloansearchDAO.searchBookloansearch(ISBN);

					if(!Card.isEmpty()){
						Card_id = Integer.parseInt(Card);
						bookloansearch = bookloansearchDAO.searchBookloansearchs(ISBN, Card_id);}
						
						for (Bookloansearch temp : bookloansearch){
						System.out.println(temp);
					}
						
						
						BookloansearchTableModel model = new BookloansearchTableModel(bookloansearch);
						
						table.setModel(model);
				}
					catch(Exception e){
						JOptionPane.showMessageDialog(Checkin.this,  "Error1:" + e, "Error11", JOptionPane.ERROR_MESSAGE);
						System.out.println(e);
					}
			
			}
		});
		btnSearch.setBounds(213, 88, 118, 35);
		contentPane.add(btnSearch);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.LIGHT_GRAY);
		panel_3.setBounds(14, 375, 276, 32);
		contentPane.add(panel_3);
		
		JLabel lblNewLabel = new JLabel("Loan_Id");
		panel_3.add(lblNewLabel);
		
		textloanid = new JTextField();
		panel_3.add(textloanid);
		textloanid.setColumns(10);
		
		JButton btnCheckIn = new JButton("Check In");
		btnCheckIn.setBackground(Color.GREEN);
		btnCheckIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				checkin();
				
			}

			private void checkin() {
				// TODO Auto-generated method stub
				
				int Loan_id;
				String Loan = textloanid.getText();
				Bookloans tempBookloans = null;

				if(!Loan.isEmpty()){
					Loan_id = Integer.parseInt(Loan);
					tempBookloans = new Bookloans(Loan_id);

				}
				
				
				try {
					// save to the database change B to b if it doesnt work
					CheckinDAO cdao = new CheckinDAO();
					cdao.checkin(tempBookloans);

					// close dialog
					setVisible(false);
					dispose();

					// refresh gui list
					//borrowerSearch.refreshBorrowersView();
					
					// show success message
					//JOptionPane.showMessageDialog(borrowerSearch, "Borrower added succesfully.", "Borrower Added",JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(
							bookloans,
							"Error saving borrower: "
									+ exc.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
				
				
			}
		});
		btnCheckIn.setBounds(393, 372, 104, 35);
		contentPane.add(btnCheckIn);
	}
}
