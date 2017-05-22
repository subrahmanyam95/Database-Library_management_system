import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class Payfinesapp extends JFrame {

	private JPanel contentPane;
	private JTextField textloanid;
	private FinesDAO finesDAO;
	private ShowFinesDAO showfinesDAO;
	private JTable table;
	private Component payfines;
	//private FinesDAO finesDAO;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Payfinesapp frame = new Payfinesapp();
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
	public Payfinesapp() {
		setTitle(" Fines App");
		
		 
			try {
				showfinesDAO = new ShowFinesDAO();
				finesDAO = new FinesDAO();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this,  "Error2:" + e, "Error22", JOptionPane.ERROR_MESSAGE);
			}
		
			
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 522, 464);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(12, 359, 340, 44);
		contentPane.add(panel);
		
		JLabel label = new JLabel("Loan_Id");
		panel.add(label);
		
		textloanid = new JTextField();
		textloanid.setColumns(10);
		panel.add(textloanid);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 91, 418, 255);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnSearch = new JButton("Show all Fines");
		btnSearch.setBackground(Color.DARK_GRAY);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try{
					List<Fines> fines = null;
						fines = showfinesDAO.getAllFines();

						for (Fines temp : fines){
						System.out.println(temp);
					}
						
						FinesTableModel model = new FinesTableModel(fines);
						
						table.setModel(model);
				}
					catch(Exception ex){
						JOptionPane.showMessageDialog(Payfinesapp.this,  "Error1:" + ex, "Error11", JOptionPane.ERROR_MESSAGE);
						//System.out.println(e);
					}
			}
		});
		btnSearch.setBounds(244, 32, 196, 44);
		contentPane.add(btnSearch);
		
		JButton btnPayFine = new JButton("Pay fine");
		btnPayFine.setBackground(Color.DARK_GRAY);
		btnPayFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				finepaid();
				
			}

			private void finepaid() {
				// TODO Auto-generated method stub
				int Loan_id;
				String Loan = textloanid.getText();
				Payfines temppayfines = null;

				if(!Loan.isEmpty()){
					Loan_id = Integer.parseInt(Loan);
					temppayfines = new Payfines(Loan_id);

				}
				
				
				try {
					// save to the database change B to b if it doesnt work
					PayfinesDAO pdao = new PayfinesDAO();
					pdao.payfine(temppayfines);

					// close dialog
					setVisible(false);
					dispose();

					
					// show success message
					//JOptionPane.showMessageDialog(borrowerSearch, "Borrower added succesfully.", "Borrower Added",JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(
							payfines,
							"Error saving borrower: "
									+ exc.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnPayFine.setBounds(396, 359, 77, 32);
		contentPane.add(btnPayFine);
		
		JButton btnGenerateFines = new JButton("Generate Fines");
		btnGenerateFines.setBackground(Color.DARK_GRAY);
		btnGenerateFines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					finesDAO.getAllFiness();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnGenerateFines.setBounds(36, 32, 150, 44);
		contentPane.add(btnGenerateFines);
	}
}
