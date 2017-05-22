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
import javax.swing.JTabbedPane;
import java.awt.Color;


public class BookSearch extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField isbntext;
	private JTextField titletext;
	private JTextField authortext;
	private JTable table;
	
	private BooksearchDAO booksearchDAO;
	private BookSearch bookSearch;

	/**
	 * Launch the application.
	 
	public BookSearch(BooksearchDAO theBooksearchDAO){
		this();
		booksearchDAO = theBooksearchDAO;
		//bookSearch = theBookSearch;
	}*/
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookSearch frame = new BookSearch();
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
	public BookSearch() {
		
		try {
			booksearchDAO = new BooksearchDAO();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,  "Error2:" + e, "Error22", JOptionPane.ERROR_MESSAGE);
		} 
		
		setTitle("Book Search App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 621, 422);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 28, 158, 36);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);
		//contentPane.add(panel);
		
		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblIsbn);
		
		isbntext = new JTextField();
		panel.add(isbntext);
		isbntext.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(200, 97, 197, 36);
		flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_1, BorderLayout.NORTH);
		//contentPane.add(panel_1);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(lblTitle);
		
		titletext = new JTextField();
		titletext.setColumns(10);
		panel_1.add(titletext);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(415, 39, 169, 36);
		flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel_2, BorderLayout.NORTH);
		//contentPane.add(panel_2);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(lblAuthor);
		
		authortext = new JTextField();
		authortext.setColumns(10);
		panel_2.add(authortext);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(262, 28, 89, 25);
		contentPane.add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 160, 599, 215);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try{
					String ISBN = isbntext.getText();
					String Title = titletext.getText();
					String Author = authortext.getText();
					
					System.out.println("ISBN is " +ISBN);
					if(Title.isEmpty())
					System.out.println("Title is " +Title);
					System.out.println("Author is " +Author);
					
					List<Book> books = null;
					
					if(Author.isEmpty())
						 Author = "";
					
					if(ISBN.isEmpty())
						ISBN="";
					
					if(Title.isEmpty())
						Title ="";
					//if(!Author.isEmpty() && !ISBN.isEmpty() && !Title.isEmpty())
						books = booksearchDAO.searchBooks(ISBN, Title, Author);
					//else 
					//	books = booksearchDAO.getAllBooks();
					
						for (Book temp : books){
						System.out.println(temp);
					}
						
						
						BookTableModel model = new BookTableModel(books);
						
						table.setModel(model);
				}
					catch(Exception e){
						JOptionPane.showMessageDialog(BookSearch.this,  "Error1:" + e, "Error11", JOptionPane.ERROR_MESSAGE);
						System.out.println(e);
					}
			}
		});
	}
}
