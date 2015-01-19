package eecs2311project;

import java.awt.Dimension;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * A view for the image viewer app.
 * 
 * @author CSE1030_W14
 *
 */
public class ViewerView extends JFrame {

	private static final long serialVersionUID = 5851493549391433289L;
	private JLabel img;
	private JFileChooser fc;


	/**
	 * Create a view using the given controller.
	 * 
	 * @param controller the controller for the image viewer app
	 */
	public ViewerView(ViewerController controller) {
		super("Text to PDF converter");

		img = new JLabel("");

		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);

		JMenu menu = new JMenu("File");
		bar.add(menu);

		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(controller);
		open.setActionCommand(ViewerController.OPEN);
		menu.add(open);

		JMenuItem clear = new JMenuItem("Clear");
		clear.addActionListener(controller);
		clear.setActionCommand(ViewerController.CLEAR);
		menu.add(clear);
		
		fc = new JFileChooser("/cse/dept/www/course/classpath/1030/img");
		fc.setMultiSelectionEnabled(true);




	
		/*JButton prev = new JButton("Previous");
		prev.addActionListener(controller);
		prev.setActionCommand(ViewerController.PREVIOUS);
		
		JButton next = new JButton("Next");
		next.addActionListener(controller);
		next.setActionCommand(ViewerController.NEXT);*/

		JButton convert = new JButton("Convert");
		convert.addActionListener(controller);
		convert.setActionCommand(ViewerController.CONVERT);

		this.setMinimumSize(new Dimension(500, 500));
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		//this.add(prev);
		//this.add(next);
		this.add(convert);
		this.add(img);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	/**
	 * Gets an array of files to open from a file chooser dialog.
	 * The returned array has length zero if the user has not selected
	 * any files, or has chosen to cancel the file chooser dialog.
	 * 
	 * @return an array of files to open
	 */
	public File[] getFilesToOpen() {
		File[] files = new File[0];
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			files = fc.getSelectedFiles();
		}
		return files;
	}

	/**
	 * Sets the image shown by the view to the specified image.
	 * 
	 * @param icon an image to show in the view
	 */
	public void setImage(ImageIcon icon) {
		if (this.img.getIcon() != icon) {
			this.img.setIcon(icon);
			this.pack();
		}
	}
}


