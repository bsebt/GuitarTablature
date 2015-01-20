package GraphicUserInterface;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	
	public GUI() {
		frame();
		}
	
	public void frame(){
		//creating an empty frame
		
		final JPanel panel = new JPanel(new GridLayout(8,10));
		final JTextField filename = new JTextField();
		final JTextField dir = new JTextField();
		JButton b2 = new JButton("Select destination");
		final JTextField destination = new JTextField();
		JButton convert = new JButton("Convert");
		
		
		
		
		
	//	GridBagConstraints c = new GridBagConstraints();
		final JButton b1 = new JButton("Select your file");
		//panel.setLayout(new GridBagLayout());
				
				final JFrame frame = new JFrame();
				frame.setVisible(true);
				frame.setSize(320,300);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(panel);
				// frame.getContentPane().add(panel, BorderLayout.NORTH)
				//creating a menu
				JMenuBar mb = new JMenuBar();
				frame.setJMenuBar(mb);
				//making menues on the bar
				JMenu file = new JMenu("File");
				mb.add(file);
				final int barMin=0;
				final int barMax= 300;
				//making menu options
				JMenuItem open = new JMenuItem("Open");
				
				open.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						final JFileChooser fc = new JFileChooser();
						int response = fc.showOpenDialog(GUI.this);
						if (response == JFileChooser.APPROVE_OPTION){
							panel.add(dir);
							panel.add(filename);
							filename.setText("Input name: " + fc.getSelectedFile().getName());
							dir.setText("Input directory: "+fc.getCurrentDirectory().toString());
						
					}
				}});
				file.add(open);
				
				
				JMenuItem exit = new JMenuItem("Exit");
				exit.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
					System.exit(0);
						
					}
				});
				file.add(exit);
				//add a panel
								
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
				filename.setEditable(false);
				dir.setEditable(false);
				destination.setEditable(false);
				b1.addActionListener(new ActionListener() {
				@Override
					public void actionPerformed(ActionEvent e) {
						final JFileChooser fc = new JFileChooser();
						int response = fc.showOpenDialog(GUI.this);
						if (response == JFileChooser.APPROVE_OPTION){
							panel.add(dir);
							panel.add(filename);
							filename.setText("Input name: " + fc.getSelectedFile().getName());
							dir.setText("Input directory: "+fc.getCurrentDirectory().toString());
							
							
					}
				}});
				
				
				b2.addActionListener(new ActionListener() {
							
					@Override
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
						        destination.setText(chooser.getCurrentDirectory().toString());
						      }
						
					}
				});
				convert.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
					}
					
						// Access the main program to convert.	
						
						
				});
				
						
				
	}
	
	public static void main(String[] args) {
		
		new GUI();	
		
	}

}
