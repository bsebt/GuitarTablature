package RamisInterface;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Holds all the information of the recently opened files/
 * @author 
 */
public class RecentOpen {
	private static ArrayList<String> recent = new ArrayList<String>();
	private JMenu fileMenu;
	private ActionListener actionListener;
    
    /**
     * Creates a RecentOpen object which holds the files in a file and arrayList(recent)
     * if the file being passed does not exist it will create it
     * otherwise it will read the file and get the last open files
     * @param file - that holds the recently opened files
     */
    public RecentOpen() throws IOException {
    	fileMenu = new JMenu("Recent");
	    fileMenu.setEnabled(false);
    }
    
    /**
     * Adds the file to the top of stack of recent file discarding the file in the 6th position
     * @param file - File to be added to the list, or moved to the top if it already exists.
     * @throws IOException
     */
    public void add (File userFile) throws IOException {
    	if (recent.contains(userFile.getAbsolutePath())) {
			recent.remove(userFile.getAbsolutePath());
			recent.add(0, userFile.getAbsolutePath());
		} else {
			recent.add(0, userFile.getAbsolutePath());
			while (recent.size() > 6)
				recent.remove(6);
		}
		
		if (fileMenu != null) 
			fileMenu.removeAll();
		
	    int index = 1;
	    for (String item: recent) {
	    	File temp = new File(item);
	    	JMenuItem temp2 = new JMenuItem(index + ". " + temp.getName());
	    	temp2.setActionCommand("recent" + (index - 1));
	    	fileMenu.add(temp2);
	    	index++;
		}
	    
		if (actionListener != null)
			this.addActionListener(actionListener);
		
		fileMenu.setEnabled(true);
    }
    
    /**
     * set the action listener for the fileMenu
     * @param l - actionListener that the fileMenu responds too
     */
    public void addActionListener(ActionListener l){
    	actionListener = l;
    	for (int i = 0; i < fileMenu.getItemCount(); i++)
    		fileMenu.getItem(i).addActionListener(actionListener);
    }
    
    /**
     * Returns the file as string at position I
     * @param i - index of desired file
     * @return String - file as a string at index i
     */
    public String get(int i) {
    	return recent.get(i);
    }
    
    /**
     * Returns the JMenu with all the recent files
     * @return JMenu - returns a JMenu with all the recent files
     */
    public JMenu getMenu() {
    	return fileMenu;
    }
}
