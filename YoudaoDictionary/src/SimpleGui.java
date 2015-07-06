import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SimpleGui extends JFrame {
	private JTextField wordToSearch;
	private JTextArea resultArea;
	public SimpleGui() {
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setSize(240, 320);
		getContentPane().setLayout(null);
		
		wordToSearch = new JTextField();
		wordToSearch.setBounds(52, 6, 134, 28);
		getContentPane().add(wordToSearch);
		wordToSearch.setColumns(10);
		
		wordToSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String result = Dictionary.searchForDefination(
						wordToSearch.getText().replaceAll(
								Pattern.quote(" "), "_"));
				resultArea.setText(result);
			}
		});
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String result = Dictionary.searchForDefination(
						wordToSearch.getText().replaceAll(
								Pattern.quote(" "), "_"));
				resultArea.setText(result);
			}
		});
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
