import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollBar;

public class Checkout extends JFrame {

	private JPanel contentPane;
	private JTextField textisbn;
	private JTextField textcardid;

	private Checkout checkout1;
	private BooksearchDAO bsdao;
	FinesDAO fd;
	BorrowerDAO bodao;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Checkout frame = new Checkout();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 9780671870430
	 * Create the frame.
	 * @throws Exception 
	 */
	public Checkout() throws Exception {
		
		bsdao = new BooksearchDAO();
		 bodao= new BorrowerDAO();
		 fd = new FinesDAO();
		setTitle("Check Out App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBounds(0, 96, 346, 40);
		contentPane.add(panel);
		
		JLabel lblIsbn = new JLabel("ISBN");
		panel.add(lblIsbn);
		
		textisbn = new JTextField();
		textisbn.setBackground(Color.WHITE);
		panel.add(textisbn);
		textisbn.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setBounds(238, 25, 339, 40);
		contentPane.add(panel_1);
		
		JLabel lblCardId = new JLabel("Card Id");
		panel_1.add(lblCardId);
		
		textcardid = new JTextField();
		textcardid.setColumns(10);
		panel_1.add(textcardid);
		
		JButton btnSave = new JButton("Add to cart");
		btnSave.setBackground(Color.GREEN);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					bookcheckouts();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			private void bookcheckouts() throws Exception {
				// TODO Auto-generated method stub
				
				
				String ISBN = textisbn.getText();
				String Cd = textcardid.getText();
				
		//		System.out.println(ISBN +"\n" + Cd);
				
				bsdao.searchBook(ISBN, "", "");
				
				int Card_id = Integer.parseInt(Cd);


				Bookloans tempCheckout = new Bookloans(ISBN, Card_id);
				List<Borrower> list1 = new ArrayList<>();
				list1 = bodao.searchBorrowers(Card_id, "", "");
				System.out.println("HAHA "+list1.get(0).getany(1));
				if(list1.get(0).getany(1)<3){
				System.out.println(list1.get(0).getany(1));
				}

				
				List<Book> list = new ArrayList<>();
				
				list = bsdao.searchBooks(ISBN, "", "");
				
				
				if(list1.get(0).getany(1)<3){
				System.out.println(list1.get(0).getany(1));
				
				
				if(fd.searchFines(Card_id)){
				
				
				
				
				
				if(list.get(0).getany(4).equals("Availability = 1")){
					//System.out.println(list.get(0).getany(4));
					
				
				try {
					// save to the database change B to b if it doesnt work
					CheckoutDAO az = new CheckoutDAO();
					az.checkout(tempCheckout);
					//checkout(tempCheckout);

					// close dialog
					setVisible(false);
					dispose();

					// show success message
					JOptionPane.showMessageDialog(checkout1,
							"Checkout added succesfully.",
							"Checkout Added",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(
							checkout1,
							"Error saving checkout: "
									+ exc.getMessage(), "Error8",
							JOptionPane.ERROR_MESSAGE);
					System.out.println(exc);
				}
				}
				
				else {
					JOptionPane.showMessageDialog(checkout1,
							"Book not Available",
							"Not Available",
							JOptionPane.INFORMATION_MESSAGE);
				}
				
				} else {
					JOptionPane.showMessageDialog(checkout1,
							"Fine due",
							"CANNOT CHECKOUT",
							JOptionPane.INFORMATION_MESSAGE);
				}
				} else {
					JOptionPane.showMessageDialog(checkout1,
							"Books checkedout is greater than 3",
							"CANNOT CHECKOUT",
							JOptionPane.INFORMATION_MESSAGE);
				}
				
				

			}
		});
		btnSave.setBounds(130, 147, 97, 51);
		contentPane.add(btnSave);
		
		JButton btnCancle = new JButton("Cancel");
		btnCancle.setBackground(Color.RED);
		btnCancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				setVisible(false);
				dispose();				
				
			}
		});
		btnCancle.setBounds(408, 147, 97, 51);
		contentPane.add(btnCancle);
	}


}
