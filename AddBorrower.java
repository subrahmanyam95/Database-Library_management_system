import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

public class AddBorrower extends JFrame {

	private JPanel contentPane;
	private JTextField textname;
	private JTextField textssn;
	private JTextField textaddress;
	private JTextField textphone;

	
	private BorrowerDAO borrowerDAO;
	
	BorrowerDAO dao;

	private BorrowerSearch borrowerSearch;	

	public AddBorrower(BorrowerSearch theBorrowerSearch, BorrowerDAO theBorrowerDAO) throws Exception {
		this();
		borrowerDAO = theBorrowerDAO;
		borrowerSearch = theBorrowerSearch;
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddBorrower frame = new AddBorrower();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public AddBorrower() throws Exception {
		
		
		 dao = new BorrowerDAO();
		 
		setTitle("Add Borrower");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 30);
		contentPane.add(panel);
		
		JLabel lblAddress = new JLabel("Address");
		panel.add(lblAddress);
		
		textaddress = new JTextField();
		panel.add(textaddress);
		textaddress.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 31, 434, 30);
		contentPane.add(panel_1);
		
		JLabel lblSsn = new JLabel("SSN");
		panel_1.add(lblSsn);
		
		textssn = new JTextField();
		textssn.setHorizontalAlignment(SwingConstants.RIGHT);
		textssn.setColumns(10);
		panel_1.add(textssn);
		
		JLabel lblxxxxxxxxx = new JLabel("Should be 9 digits");
		panel_1.add(lblxxxxxxxxx);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 64, 434, 30);
		contentPane.add(panel_2);
		
		JLabel lblName = new JLabel("Name");
		panel_2.add(lblName);
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		
		textname = new JTextField();
		panel_2.add(textname);
		textname.setHorizontalAlignment(SwingConstants.LEFT);
		textname.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 95, 434, 39);
		contentPane.add(panel_3);
		
		JLabel lblPhone = new JLabel("Phone");
		panel_3.add(lblPhone);
		
		textphone = new JTextField();
		textphone.setColumns(10);
		panel_3.add(textphone);
		
		JLabel lblxxxxxxxxxx = new JLabel("Should be 10 digits");
		panel_3.add(lblxxxxxxxxxx);
		
		JButton savebtn = new JButton("Save");
		savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//
				try {
					saveBorrower();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//
			}

			private void saveBorrower() throws Exception {
				// TODO Auto-generated method stub
				
				// get the borrower info from gui
				String Bname = textname.getText();
				String Address = textaddress.getText();
				String Phone = textphone.getText();
				String SSN = textssn.getText();
				
				Borrower tempBorrower=null;;

			//	if(SSN!=null & Address != null & Bname!= null ){	
				List<Borrower> a = dao.searchBorrowers(7, "Mummies", "");
					if(!SSN.equals("") & !Bname.equals("") & !Address.equals("")){	
						if(dao.searchBorrower("", SSN).equals(a)){
						
						
				tempBorrower = new Borrower(Bname,
						SSN, Address, Phone);


				try {
					// save to the database change B to b if it doesnt work
					dao.addBorrower(tempBorrower);

					// close dialog
					setVisible(false);
					dispose();

					// refresh gui list
					//borrowerSearch.refreshBorrowersView();
					
					// show success message
					JOptionPane.showMessageDialog(borrowerSearch,
							"Borrower added succesfully.",
							"Borrower Added",
							JOptionPane.INFORMATION_MESSAGE); 
					
					}
				 catch (Exception exc) {
					JOptionPane.showMessageDialog(
							borrowerSearch,
							"Error saving borrower: "
									+ exc.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
						}
						else
						{
							JOptionPane.showMessageDialog(borrowerSearch,									
									"SSN already used",
									"Error",
									JOptionPane.INFORMATION_MESSAGE);
						}
				
				
				}
				else {
					JOptionPane.showMessageDialog(borrowerSearch,
				
						"Error in Details. Please check again",
						"Error",
						JOptionPane.INFORMATION_MESSAGE); 
			}
				
			}
		});
		savebtn.setBounds(66, 157, 111, 45);
		contentPane.add(savebtn);
		
		JButton canclebtn = new JButton("Cancel");
		canclebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();				
				
			}
		});
		canclebtn.setActionCommand("Cancel");
		canclebtn.setBounds(283, 157, 111, 45);
		contentPane.add(canclebtn);
	}
}
