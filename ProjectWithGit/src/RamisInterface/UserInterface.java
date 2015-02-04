package RamisInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.DocumentException;

import RamisWork.*;


public class UserInterface extends JFrame implements ActionListener, KeyListener, MouseListener, WindowListener {
	//Static/Finals
    private static final long serialVersionUID = 1L;
	private static final int defaultZoom = 100, textDelay = 800, zoomDelay = 500;
	private static JFrame frame;
	private static RecentOpen recentOpen;
   
	//GUI
	private JTextField fileTitle, title, author, zoom;
	private JPanel body, sidebar, basicButtons;
	private JComboBox<String> fontType;
	private JComboBox<Integer> fontSizeTitle, fontSizeAuthor, fontSize;
	private JSlider numberSpacing, measureSpacing, lineSpacing, zoomSlide, leftMargin, rightMargin;
	private JButton open, help, save, print, reset;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem openMenu, saveMenu, printMenu, aboutMenu, helpMenu, exitMenu, resetMenu, 
	zoomInMenu, zoomOutMenu;
	private Timer textTimer, zoomTimer;
	
	
	private int indexOfHelvetica;
	private  float defaultSpacing;
	private boolean opened = false, fileSaved = true, changesSaved = true;
	private String prevDir, defaultTitle, defaultSubtitle;
	
    private Tablature userTab;
	private Style userStyle = new Style();
    
	private PDFPanel preview;
	

    public static void main(String[] args) throws DocumentException, IOException {
		createAndShowGUI();
	}
    //change

    public static void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Tab2PDF");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ImageIcon icon = createImageIcon("images/placeholder");
       // frame.setIconImage(icon.getImage());
        
        
        //set look and feel to OS's look and feel, if it fails will just use the default Java look and feel
        try	{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) { }

        try {
			recentOpen = new RecentOpen();
		} catch (IOException e1) { }
        
        //Create and set up the content pane.
        UserInterface demo = new UserInterface();
        frame.setJMenuBar(demo.createMenuBar());
        frame.add(demo.createBody(), BorderLayout.CENTER);
 
        //Display the window.
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

   
    public JPanel createB1() {
    	JPanel temp = new JPanel(new GridLayout(2,2,10,10));
    	temp.setOpaque(true);
    	
    	//create a jPanel with open, save, help and more buttons
    	ImageIcon icon = createImageIcon("images/placeholder");
    	open = new JButton("Open", icon);
    	open.setToolTipText("Open ASCII tablature to convert");
    	open.setMnemonic(KeyEvent.VK_O);
    	open.setActionCommand("open");
    	open.addActionListener(this);
    	
    	icon = createImageIcon("images/placeholder");
    	save = new JButton("Save", icon);
    	save.setToolTipText("Save this tablature as a PDF");
    	save.setMnemonic(KeyEvent.VK_S);
    	save.setActionCommand("save");
    	save.addActionListener(this);
    	save.setEnabled(false); 	
    	
    	
    	
    	
    	
    	//Add the text area to the content pane.
    	temp.add(open);
    	temp.add(save);
    	
    	
    	temp.setBorder(new TitledBorder(new EtchedBorder(), "Buttons"));
    	return temp;
    }
    
    
    public JPanel createBody() { 
        body = new JPanel(new BorderLayout());
        sidebar = new JPanel(new BorderLayout());
        basicButtons = createB1();
        
        frame.addWindowListener(this);
        
        sidebar.add(basicButtons, BorderLayout.PAGE_START);
        body.add(sidebar, BorderLayout.LINE_START);
        body.add(createS1(), BorderLayout.SOUTH);
        return body;
    }
    
   
    public JMenuBar createMenuBar() {
    	ImageIcon icon;
        menuBar = new JMenuBar(); //Create the menu bar.
 
        /*---FIRST MENU-------------------------------------------------------------------*/
        menu = new JMenu("File"); 
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
        menuBar.add(menu);
        
        icon = createImageIcon("images/placeholder");
        openMenu = new JMenuItem("Open", icon);
        openMenu.setActionCommand("open");
        openMenu.addActionListener(this);
        menu.add(openMenu);
 
        icon = createImageIcon("images/placeholder");
        saveMenu = new JMenuItem("Save", icon);
        saveMenu.setActionCommand("save");
        saveMenu.addActionListener(this);
        saveMenu.setEnabled(false);
        menu.add(saveMenu);
        
        icon = createImageIcon("images/placeholder");
        
		menu.addSeparator();
		recentOpen.addActionListener(this);
	    menu.add(recentOpen.getMenu());

        menu.addSeparator();
        icon = createImageIcon("images/placeholder");
        exitMenu = new JMenuItem("Exit", icon);
        exitMenu.setActionCommand("exit");
        exitMenu.setMnemonic(KeyEvent.VK_E);
        exitMenu.addActionListener(this);
        menu.add(exitMenu);
        
        /*---SECOND MENU(options)------------------------------------------------*/
        menu = new JMenu("Edit");
        menuBar.add(menu);
        
        icon = createImageIcon("images/placeholder");
        resetMenu = new JMenuItem("Reset", icon);
        resetMenu.setEnabled(false);
        resetMenu.addActionListener(this);
        resetMenu.setAccelerator(KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_R, 
                java.awt.Event.CTRL_MASK));
        resetMenu.setActionCommand("reset");
        menu.add(resetMenu);
        menu.addSeparator();
        
        icon = createImageIcon("images/placeholder");
        zoomInMenu = new JMenuItem("Zoom in", icon);
        zoomInMenu.setEnabled(false);
        zoomInMenu.setActionCommand("zoom");
        zoomInMenu.addActionListener(this);
        zoomInMenu.setAccelerator(KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_EQUALS, 
                java.awt.Event.CTRL_MASK));
        menu.add(zoomInMenu);
        
        icon = createImageIcon("images/placeholder");
        zoomOutMenu = new JMenuItem("Zoom out", icon);
        zoomOutMenu.setEnabled(false);
        zoomOutMenu.setActionCommand("zoom");
        zoomOutMenu.addActionListener(this);
        zoomOutMenu.setAccelerator(KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_MINUS, 
                java.awt.Event.CTRL_MASK));
        menu.add(zoomOutMenu);
        
       
        
        
        
        return menuBar;
    }
   
    public JPanel createS1() {
    	JPanel temp = new JPanel(new GridLayout(1,1));
    	temp.setOpaque(true);
        
        fileTitle = new JTextField("Press a button to begin");
        fileTitle.setHorizontalAlignment(SwingConstants.RIGHT);
        fileTitle.setOpaque(false);
        fileTitle.setBorder(null);
        temp.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray));
        temp.add(fileTitle);
		return temp;
    }  
    

	
	
	private void generatePDF(final int zoomLevel) {
		
		try {
   			PdfOutputCreator pdfout = new PdfOutputCreator();
   			pdfout.makePDF(userTab, userStyle);
   		} catch (IOException | DocumentException e1) {
   			System.out.println("test");
   		}
	   
		if (opened == true && preview != null) { //Expand the view, or if already expanded, refresh the preview
			preview.refresh(zoomLevel);
		}
	    else 
   			expandView();
		frame.setTitle("*" + userTab.getTitle() + ".pdf - Tab2PDF");
	}


	public void grabUserValues() {

	}

   

	
	private void openFile(File userFile) throws FileNotFoundException {
	
	}

	
	
	private void reset() {
		/*-Reset Variables-*/
		userStyle.setFontSize(8);
		userStyle.setMyTitleSize(24);
		userStyle.setMySubTitleSize(16);
		userStyle.setLineDistance(7f);
		userStyle.setMeasureDistance(30f);
		userStyle.setLeftMargin(36f);
		userStyle.setRightMargin(36f);
		
		/*-Reset GUI------*/
		fontSizeTitle.setSelectedIndex(10);
		fontSizeAuthor.setSelectedIndex(6);
		title.setText(defaultTitle);
		author.setText(defaultSubtitle);
		fontType.setSelectedIndex(indexOfHelvetica);
		fontSize.setSelectedIndex(1);
		numberSpacing.setValue((int) (defaultSpacing * 10));
		measureSpacing.setValue(300);
		lineSpacing.setValue(70);
		leftMargin.setValue(36);
		rightMargin.setValue(36);
		zoomSlide.setValue(defaultZoom);
		zoom.setText("100%");

		generatePDF(defaultZoom);
		fileTitle.setText("Values reset to default.");
	}
	

	private void saveFile() {
		
	}
	
	private void saveOnExit(){
		if (!fileSaved && opened) {
			int temp = JOptionPane.showOptionDialog(frame, "File has not been saved. Would you like to save " 
			+ userTab.getTitle() + ".pdf ?",  "Save?", JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE, null, new String[]{"Save", "Don't Save", "Cancel"}, "Cancel");
			if (temp == JOptionPane.YES_OPTION) {
				saveFile();
				frame.dispose();
				System.exit(0);
			}
			else if (temp == JOptionPane.NO_OPTION) {
				frame.dispose();
				System.exit(0);
			}
		} else if (!changesSaved && opened) {
			int temp = JOptionPane.showOptionDialog(frame, "Changes have not been saved. Would you like to save " 
			+ userTab.getTitle() + ".pdf ?",  "Save?", JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE, null, new String[]{"Save", "Don't Save", "Cancel"}, "Cancel");
			if (temp == JOptionPane.YES_OPTION) {
				saveFile();
				frame.dispose();
				System.exit(0);
			}
			else if (temp == JOptionPane.NO_OPTION) {
				frame.dispose();
				System.exit(0);
			}
		} else {
			frame.dispose();
			System.exit(0);
		}
	}
	
	
	private void selectFile() {
		fileTitle.setText("Opening...");
		File txtFile;
		JFileChooser openFile = new JFileChooser();
		if (prevDir != null)
			openFile.setCurrentDirectory(new File(prevDir));
		openFile.setFileFilter(new FileNameExtensionFilter("Text documents", "txt"));
		int returnVal = openFile.showOpenDialog(this);
		txtFile = openFile.getSelectedFile();
		if (txtFile != null)
			prevDir = txtFile.getAbsolutePath();
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				openFile(txtFile);
			} catch (IOException e1) { 
				JOptionPane.showMessageDialog(frame, "Cannot open file!");
			}
		}
		else
			fileTitle.setText("Open cancelled.");
	}
    
    @Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "open") {
			if (opened && !fileSaved) {
				int temp = JOptionPane.showOptionDialog(frame, "File has not been saved. Would you like to save " 
					+ userTab.getTitle() + ".pdf ?",  "Save?", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, new String[]{"Save", "Don't Save", "Cancel"}, "Don't Save");
				if (temp == JOptionPane.YES_OPTION) {
					saveFile();
					selectFile();
				} else if (temp == JOptionPane.NO_OPTION)
					selectFile();
			} else if (opened && !changesSaved) {
				int temp = JOptionPane.showOptionDialog(frame, "Changes not been saved. Would you like to save " 
						+ userTab.getTitle() + ".pdf ?",  "Save?", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, new String[]{"Save", "Don't Save", "Cancel"}, "Don't Save");
					if (temp == JOptionPane.YES_OPTION) {
						saveFile();
						selectFile();
					} else if (temp == JOptionPane.NO_OPTION)
						selectFile();
			}
			else
				selectFile();
		}

		else if (e.getActionCommand() == "save")
			saveFile();
		
	
		
		else if (e.getActionCommand() == "exit")
			saveOnExit();
		
		
		
		
		
		
		
			else {
				reset();
				fileTitle.setText("Opened " + userTab.getTitle() + ".txt");
			}
		}
	
    
	
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = UserInterface.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            return null;
        }
    }
    
    @Override
	public void keyPressed(KeyEvent e) { }

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			grabUserValues();
			textTimer.stop();
		} else if (textTimer.isRunning()) {
			if(textTimer.getInitialDelay() > 0)
				textTimer.restart();
		} else 
			textTimer.start();
	}
	
	@Override
	public void keyTyped(KeyEvent e) { }
	
	@Override
	public void mouseClicked(MouseEvent arg0) { }

	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) { }

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource().equals(zoomSlide)) {
			zoom.setText(zoomSlide.getValue() + "%");
	        preview.refresh(zoomSlide.getValue());
			changesSaved = false;
		}
		
		else if (e.getSource().equals(numberSpacing)) {
			userTab.setSpacing(numberSpacing.getValue() / 10);
			generatePDF(zoomSlide.getValue());
			fileTitle.setText("Updated spacing");
			changesSaved = false;
		}
		
		else if (e.getSource().equals(measureSpacing)) {
			userStyle.setMeasureDistance(measureSpacing.getValue() / 10f);
			generatePDF(zoomSlide.getValue());
			fileTitle.setText("Updated measure spacing");
			changesSaved = false;
		}
		
		else if (e.getSource().equals(lineSpacing)) {
			userStyle.setLineDistance(lineSpacing.getValue() / 10);
			generatePDF(zoomSlide.getValue());
			fileTitle.setText("Updated line spacing");
			changesSaved = false;
		}
		
		else if (e.getSource().equals(leftMargin)) {
			userStyle.setLeftMargin(leftMargin.getValue());
			generatePDF(zoomSlide.getValue());
			fileTitle.setText("Left Margin adjusted");
			changesSaved = false;
        }
		
		else if (e.getSource().equals(rightMargin)) {
			userStyle.setRightMargin(rightMargin.getValue());
			generatePDF(zoomSlide.getValue());
			fileTitle.setText("Right Margin adjusted");
			changesSaved = false;
		}
	}
    
    @Override
	public void windowActivated(WindowEvent arg0) { }
	
	@Override
	public void windowClosed(WindowEvent arg0) { }
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		saveOnExit();
	}
	
	@Override
	public void windowDeactivated(WindowEvent arg0) { }
	
	@Override
	public void windowDeiconified(WindowEvent arg0) { }
	
	@Override
	public void windowIconified(WindowEvent arg0) { }
	
	@Override
	public void windowOpened(WindowEvent arg0) { }
}