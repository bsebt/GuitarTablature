package KevinsWork;

import javax.swing.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	final static JProgressBar barDo = new JProgressBar(0, 100);
	JTextField source = new JTextField();
	JTextField dest = new JTextField();
	final JTextField OutputFile = new JTextField("Output file name: ");

	public GUI() {
		frame();
	}

	public void frame() {

		// creating an empty frame
		final JPanel panel = new JPanel(null);
		
		
		
		
		JMenuItem open = new JMenuItem("Open");
		JMenuItem exit = new JMenuItem("Exit");
		
		final JFrame frame = new JFrame("Convert Guitar Notes to pdf Format");
		frame.setVisible(true);
		frame.setSize(930, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		
		
		

		// creating a menu
		JMenuBar mb = new JMenuBar();
		frame.setJMenuBar(mb);

		// making menues on the bar
		JMenu file = new JMenu("File");
		mb.add(file);

		// add a button
		JLabel inputLabel = new JLabel("Input File ");
		inputLabel.setBounds(0, 0, 100, 30);
		panel.add(inputLabel);
		
		final JTextField input = new JTextField("Input Address: ");
		input.setBounds(0, 30, 300, 31);
		panel.add(input);
		
		final JButton InputSelect = new JButton("Brows...");
		InputSelect.setBounds(300, 30, 100, 30);
		panel.add(InputSelect);
		
		JLabel outputLabel = new JLabel("Output File ");
		outputLabel.setBounds(420, 0, 100, 30);
		panel.add(outputLabel);
		
		final JTextField destination = new JTextField("Destination: ");
		destination.setBounds(420, 30, 400, 31);
		panel.add(destination);
		
		final JButton OutputSelect = new JButton("Brows...");
		OutputSelect.setBounds(820, 30, 100, 30);		
		panel.add(OutputSelect);
		
		final JCheckBox box = new JCheckBox("or click to use the same name");
		box.setBounds(620, 65, 500, 20);
		panel.add(box);
		
		OutputFile.setBounds(420, 60, 200, 31);
		panel.add(OutputFile);
		
		final JButton convert = new JButton("Convert");
		convert.setBounds(800, 101, 100, 30);
		panel.add(convert);
		
		final JButton privewOut = new JButton("Preview of input");
		privewOut.setBounds(200, 100, 200, 30);
		panel.add(privewOut);
		
		panel.add(barDo);
		file.add(open);
		file.add(exit);
		frame.setResizable(false);
		

		// making menu options
		
		privewOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 try {
				      Desktop desktop = null;
				      if (Desktop.isDesktopSupported()) {
				        desktop = Desktop.getDesktop();
				      }

				       desktop.open(new File(source.getText().substring(source.getText().indexOf("/"),input.getText().length())));
				    } catch (IOException ioe) {
				      ioe.printStackTrace();
				    }
				
			}
		});
		
		
		box.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				File f = new File(source.getText());
				if (!box.isSelected()){
					OutputFile.setText("Output file name: ");
					OutputFile.setBounds(420, 60, 200, 31);
					panel.add(OutputFile);
					panel.repaint();
					}else{
						panel.remove(OutputFile);
						OutputFile.setText("hello: "+f.getName().substring(0, f.getName().indexOf("."))+".pdf");
						System.out.println(OutputFile.getText());
						panel.repaint();
			}
		}});
		
		
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				int response = fc.showOpenDialog(GUI.this);
				if (response == JFileChooser.APPROVE_OPTION) {
					input.setText("Input Address: "
							+ fc.getSelectedFile().getPath());
					source.setText(input.getText());
				}
			}
		});

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

		InputSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				int response = fc.showOpenDialog(GUI.this);
				if (response == JFileChooser.APPROVE_OPTION) {
					input.setText("Input Address: "
							+ fc.getSelectedFile().getPath());
					source.setText(input.getText());

				}
			}
		});

		OutputSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("Output directory");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				//
				// disable the "All files" option.
				//
				chooser.setAcceptAllFileFilterUsed(false);
				//
				if (chooser.showOpenDialog(GUI.this) == JFileChooser.APPROVE_OPTION) {
					destination.setText(chooser.getSelectedFile().getPath());
					dest.setText(destination.getText());
				}
			}
		});

		convert.addActionListener(new btnDoAction()); // Add the button's action

		convert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//System.out.println(OutputFile.getText());
					//OutputFile.setText(OutputFile.getText().substring(OutputFile.getText().indexOf(":")+2,OutputFile.getText().length()));
					//dest.setText(dest.getText().substring(dest.getText().indexOf("/"),dest.getText().length()));
					//source.setText(source.getText().substring(source.getText().indexOf("/"),input.getText().length()));
					//new GUI().createPdf(destination.getText()+"/"+OutputFile.getText(), source.getText());
					BarLinesPDF.convertPDF("Test.txt");
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

	public void createPdf(String des, String source) throws DocumentException,
			IOException {
		BufferedReader input = null;
		Document output = null;

		try {
			input = new BufferedReader(new FileReader(source));
			output = new Document();
			PdfWriter.getInstance(output, new FileOutputStream(des));

			output.open();

			String line = "";
			while (null != (line = input.readLine())) {
				System.out.println(line);
				Paragraph p = new Paragraph(line);
				output.add(p);
			}

			System.out.println("Done.");
			output.close();
			input.close();
			// System.exit(0);
		} catch (Exception e) {
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