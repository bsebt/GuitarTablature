package GraphicUserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final static JProgressBar barDo = new JProgressBar(0, 100);

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
		filename.setEditable(false);
		dir.setEditable(false);
		destination.setEditable(false);

		// making menu options
		JMenuItem open = new JMenuItem("Open");
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
		file.add(open);

		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		file.add(exit);

		b1.addActionListener(new ActionListener() {
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
				}
			}
		});

		convert.addActionListener(new btnDoAction()); //Add the button's action
	}

	//The action
	public static class btnDoAction implements ActionListener{
		public void actionPerformed (ActionEvent e){
			new Thread(new thread1()).start(); //Start the thread
		}
	}

	//The thread
	public static class thread1 implements Runnable{
		public void run(){
			for (int i=0; i<=100; i++){ //Progressively increment variable i
				barDo.setValue(i); //Set value
				barDo.repaint(); //Refresh graphics
				try{Thread.sleep(50);} //Sleep 50 milliseconds
				catch (InterruptedException err){}
			}
		}
	}

	public static void main(String[] args) {

		new GUI();

	}

}