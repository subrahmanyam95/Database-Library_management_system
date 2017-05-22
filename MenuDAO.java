import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MenuDAO extends JFrame {

	private JPanel contentPane;
	private 	BooksearchDAO booksearchDAO;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuDAO frame = new MenuDAO();
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
	/**
	 * 
	 */
	public MenuDAO() {
		setTitle("Library Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.CYAN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("SEARCH BOOKS");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			
				// create dialog
				BookSearch dialog = new BookSearch();

				// show dialog
				dialog.setVisible(true);
			}
		});
		btnNewButton.setBounds(150, 90, 130, 57);
		contentPane.add(btnNewButton);
		
		JButton btnAddANew = new JButton("TO ADD OR SEARCH Borrower ");
		btnAddANew.setBackground(Color.WHITE);
		btnAddANew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				// create dialog
				BorrowerSearch dialog = new BorrowerSearch();

				// show dialog
				dialog.setVisible(true);
			}
		});
		btnAddANew.setBounds(119, 11, 202, 52);
		contentPane.add(btnAddANew);
		
		JButton btnBookCheckout = new JButton("CHECKOUT");
		btnBookCheckout.setBackground(Color.WHITE);
		btnBookCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// create dialog
				Checkout dialog;
				try {
					dialog = new Checkout();
					
					// show dialog
					dialog.setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		});
		btnBookCheckout.setBounds(10, 90, 130, 44);
		contentPane.add(btnBookCheckout);
		
		JButton btnBookCheckin = new JButton("CHECKIN");
		btnBookCheckin.setBackground(Color.WHITE);
		btnBookCheckin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				// create dialog
				Checkin dialog = new Checkin();

				// show dialog
				dialog.setVisible(true);
			}
		});
		btnBookCheckin.setBounds(303, 90, 130, 44);
		contentPane.add(btnBookCheckin);
		
		JButton btnFines = new JButton("FINES");
		btnFines.setBackground(Color.WHITE);
		btnFines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// create dialog
				Payfinesapp dialog = new Payfinesapp();

				// show dialog
				dialog.setVisible(true);
				
				// Payfinesapp()
			}
		});
		btnFines.setBounds(156, 169, 106, 44);
		contentPane.add(btnFines);
	}

}
