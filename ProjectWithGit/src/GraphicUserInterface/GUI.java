package GraphicUserInterface;

import javax.swing.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	final static JProgressBar barDo = new JProgressBar(0, 100);
	JTextField filename2 = new JTextField();
	JTextField dir2 = new JTextField();
	JTextField dest = new JTextField();
	StringBuffer s = new StringBuffer ();

	public GUI() {
		frame();
	}

	public void frame() {
		
		// creating an empty frame
		final JPanel panel = new JPanel(new GridLayout(8, 10));
		final JTextField filename = new JTextField();
		final JTextField dir = new JTextField();
		final JButton b2 = new JButton("Select destination");
		final JTextField destination = new JTextField();
		final JButton convert = new JButton("Convert");
		final JButton b1 = new JButton("Select your file");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem exit = new JMenuItem("Exit");

		final JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(320, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		// creating a menu
		JMenuBar mb = new JMenuBar();
		frame.setJMenuBar(mb);

		// making menues on the bar
		JMenu file = new JMenu("File");
		mb.add(file);

		// add a button
		panel.add(b1);
		dir.setText("directory of input: ");
		filename.setText("Name of input file:");
		destination.setText("destination of converted file:");
		panel.add(filename);
		panel.add(dir);
		panel.add(b2);
		panel.add(destination);
		panel.add(convert);
		panel.add(barDo);
		file.add(open);
		file.add(exit);
		filename.setEditable(false);
		dir.setEditable(false);
		destination.setEditable(false);

		// making menu options
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				int response = fc.showOpenDialog(GUI.this);
				if (response == JFileChooser.APPROVE_OPTION) {
					panel.add(dir);
					panel.add(filename);
					filename.setText("Input name: "
							+ fc.getSelectedFile().getName());
					dir.setText("Input directory: "
							+ fc.getCurrentDirectory().toString());
				}
			}
		});

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				int response = fc.showOpenDialog(GUI.this);
				if (response == JFileChooser.APPROVE_OPTION) {
					panel.add(dir);
					panel.add(filename);
					filename.setText("Input name: "
							+ fc.getSelectedFile().getName());
					filename2.setText(fc.getSelectedFile().getName());
					dir.setText("Input directory: "
							+ fc.getCurrentDirectory().toString());
					dir2.setText(fc.getCurrentDirectory().toString());
					panel.add(b1);
					panel.add(filename);
					panel.add(dir);
					panel.add(b2);
					panel.add(destination);
					panel.add(convert);

				}
			}
		});

		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("choosertitle");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				//
				// disable the "All files" option.
				//
				chooser.setAcceptAllFileFilterUsed(false);
				//
				if (chooser.showOpenDialog(GUI.this) == JFileChooser.APPROVE_OPTION) {
					destination.setText(chooser.getCurrentDirectory()
							.toString());
					dest.setText(chooser.getCurrentDirectory()
							.toString());
				}
			}
		});

		//convert.addActionListener(new btnDoAction()); // Add the button's action
		
		convert.addActionListener(new ActionListener()  {
			public void actionPerformed(ActionEvent e) {
				try {
					s.append(dir2.getText());
					s.append("\\");
					s.append(filename2.getText());
					System.out.println(dest.getText()+"\\Converted.pdf");
					System.out.println(s.toString());
					new GUI().createPdf(dest.getText()+"\\Converted.pdf",s.toString());
				} catch (DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
		});
	}
	
	public void createPdf(String des, String source)
			throws DocumentException, IOException {
		BufferedReader input = null;
		Document output = null;
    
		try{
			input = new BufferedReader (new FileReader(source));
			output = new Document();
			PdfWriter.getInstance(output, new FileOutputStream(des));
    	
			output.open();
    	
			String line = "";
			while(null != (line = input.readLine())){
				System.out.println(line);
				Paragraph p = new Paragraph(line);
				output.add(p);
			}
    	
			System.out.println("Done.");
			output.close();
			input.close();
			System.exit(0);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	// The action
	public static class btnDoAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new Thread(new thread1()).start(); // Start the thread
		}
	}
	
	// The thread
	public static class thread1 implements Runnable {
		public void run() {
			for (int i = 0; i <= 100; i++) { // Progressively increment variable
												// i
				barDo.setValue(i); // Set value
				barDo.repaint(); // Refresh graphics
				try {
					Thread.sleep(50);
				} // Sleep 50 milliseconds
				catch (InterruptedException err) {
				}
			}
		}
	}

	public static void main(String[] args) {

		new GUI();

	}

}