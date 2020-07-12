package application;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Formatter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class SimpleTextEditor {
	
	private final String TITLE = "Обычный текстовый редактор";

	private JFrame frame;
	private JTextArea textArea;
	
	private File openFile;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					SimpleTextEditor window = new SimpleTextEditor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SimpleTextEditor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle(TITLE);
		frame.setBounds(100, 100, 865, 605);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 13));
		frame.getContentPane().add(textArea, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu mnFile = new JMenu("\u0424\u0430\u0439\u043B");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("\u041E\u0442\u043A\u0440\u044B\u0442\u044C");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				open();
			}
		});
		mnFile.add(mntmNewMenuItem);
		
		JMenuItem menuItem = new JMenuItem("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C \u043A\u0430\u043A");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				create();
			}
		});
		mnFile.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		mnFile.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("\u0417\u0430\u043A\u0440\u044B\u0442\u044C");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		mnFile.add(menuItem_2);
		
		
	}
	
	
	private void open() {
		try {
			
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Выбрать текстовый документ");
			chooser.showOpenDialog(null);
			
			openFile = chooser.getSelectedFile();
			if (openFile != null && !openFile.exists()) {
				JOptionPane.showMessageDialog(null, "Не удалось открыть файл, файл не существует!", "Ошибка", JOptionPane.ERROR_MESSAGE);
				openFile = null;
				return;
			}
			
			Scanner scanner = new Scanner(openFile);
			String contents = "";
			while (scanner.hasNextLine()) {
				contents += scanner.nextLine()+"\n";
			}
			scanner.close();
			textArea.setText(contents);
						
			frame.setTitle(TITLE+" - "+openFile.getName());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void create() {
		try {
			
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Сохранить новый файл");
			chooser.showSaveDialog(null);
			
			openFile = chooser.getSelectedFile();
			
			save();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void save() {
		try {
			
			if (openFile == null) {
				JOptionPane.showMessageDialog(null, "Не удалось сохранить файл, файл не выбран!", "Ошибка", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String contents = textArea.getText();
			Formatter form = new Formatter(openFile);
			form.format("%s", contents);
			form.close();
			
			frame.setTitle(TITLE+" - "+openFile.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	private void close() {
		if (openFile == null) {
			JOptionPane.showMessageDialog(null, "Не удалось закрыть файл, файл не выбран!", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			
			int input = JOptionPane.showConfirmDialog(null, "Вы хотите сохранить файл перед тем как его закрыть?", "Подождите!", JOptionPane.YES_NO_OPTION);
			
			if (input == JOptionPane.YES_OPTION) {
				save();
			}
			
			textArea.setText("");
			openFile = null;
			frame.setTitle(TITLE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

}
