import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import java.awt.Color;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SimpleGui extends JFrame {
	
	private JTextField wordToSearch;
	private JTextArea resultArea;
	public SimpleGui() {
		
		class SearchListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				SearchResult result = Dictionary.search(
						wordToSearch.getText());
				if (result.hasResult() == true) {
					resultArea.setForeground(Color.BLACK);
					resultArea.setText(result.getContent());
				} else {
					resultArea.setForeground(Color.RED);
					resultArea.setText(result.getContent());
				}
				
			}
		}
		
		SearchListener listener = new SearchListener();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setSize(240, 320);
		getContentPane().setLayout(null);
		
		wordToSearch = new JTextField();
		wordToSearch.setBounds(40, 5, 160, 30);
		getContentPane().add(wordToSearch);
		
		wordToSearch.addActionListener(listener);
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(listener);
		searchButton.setBounds(62, 40, 117, 29);
		getContentPane().add(searchButton);
		
		resultArea = new JTextArea();
		resultArea.setText("");
		resultArea.setBounds(6, 81, 228, 211);
		getContentPane().add(resultArea);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new SimpleGui().setVisible(true);;
		});
	}
}
