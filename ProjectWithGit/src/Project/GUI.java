package Project;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.itextpdf.text.DocumentException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.TileObserver;
import java.io.*;
import java.util.*;
import java.awt.*;

public class GUI extends JFrame {

	public static void main(String[] args) {
		new GUI();
	}

	public static JTextField input = new JTextField();
	public static JFrame frame = new JFrame(
			"Convert Guitar Notes to PDF Format");
	public static JTextField destination = new JTextField();
	public static JTextField name = new JTextField();
	private PreviewPan preview;
	// JPanel body;
	public static JTextField SGBSF = new JTextField("75");
	public static JTextField SGSPF = new JTextField(Double.toString(DataToArray
			.getSpacing()));
	static JPanel EditorPanel = new JPanel(null);
	public static JTextField STitleF = new JTextField(DataToArray.getsubTitle());
	public static JTextField TitleF = new JTextField(DataToArray.getsubTitle());

	// private static JTextField source = new JTextField();

	private GUI() {
		
		
		input = new JTextField();
		frame = new JFrame(
				"Convert Guitar Notes to PDF Format");
		destination = new JTextField();
		name = new JTextField();
		PreviewPan preview;
		//private JPanel body;
		SGBSF = new JTextField("75");
		SGSPF = new JTextField(Double.toString(DataToArray
				.getSpacing()));
		EditorPanel = new JPanel(null);
		STitleF = new JTextField(DataToArray.getsubTitle());
		TitleF = new JTextField(DataToArray.getsubTitle());
		
		
		

		frame.setSize(320, 210);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		JMenuItem open = new JMenuItem("Open");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem about = new JMenuItem("About");

		// creating a menu
		JMenuBar mb = new JMenuBar();
		frame.setJMenuBar(mb);

		// making menues on the bar
		JMenu file = new JMenu("File");
		mb.add(file);
		JMenu help = new JMenu("Help");
		mb.add(help);

		file.add(open);
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				int response = fc.showOpenDialog(GUI.this);
				if (response == JFileChooser.APPROVE_OPTION) {
					input.setText("Input Address: "
							+ fc.getSelectedFile().getPath());
				}
			}
		});

		file.add(exit);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

		help.add(about);
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, "how our program works.",
						"guitar note converter manual",
						JOptionPane.INFORMATION_MESSAGE, null);
			}
		});
		// ImageIcon icon =
		// createImageIcon("/home/behshad/Desktop/open-file.png");
		JButton OpenB = new JButton("Open");

		JButton QuickB = new JButton("Fast Convert");
		QuickB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Yes, save and exit",
                "No, delete the file and exit"};
				int n = JOptionPane.showOptionDialog(frame,
					    "would you like to save the pdf before you exit?",
					    "A Silly Question",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,     //do not use a custom Icon
					    options,  //the titles of buttons
					    options[0]); //default button title
				System.out.println(n);
				
			}
		});

		JButton ExitB = new JButton("Exit");
		ExitB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

		JButton AboutB = new JButton("About");
		AboutB.setIconTextGap(JOptionPane.INFORMATION_MESSAGE);
		AboutB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, "how our program works.",
						"guitar note converter manual",
						JOptionPane.INFORMATION_MESSAGE, null);
			}
		});
		final JPanel OpenerPanel = new JPanel(null);
		OpenB.setBounds(10, 5, 300, 50);
		OpenerPanel.add(OpenB);
		QuickB.setBounds(10, 55, 130, 50);
		OpenerPanel.add(QuickB);
		AboutB.setBounds(180, 55, 130, 50);
		OpenerPanel.add(AboutB);
		ExitB.setBounds(10, 105, 300, 50);
		OpenerPanel.add(ExitB);
		frame.add(OpenerPanel);

		frame.setResizable(false);

		OpenB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				final JFileChooser fc = new JFileChooser();
				int response = fc.showOpenDialog(GUI.this);
				if (response == JFileChooser.APPROVE_OPTION) {
					input.setText(fc.getSelectedFile().getPath());
					name.setText(fc.getSelectedFile().getName());
					destination.setText(fc.getSelectedFile().getParent());
					frame.remove(OpenerPanel);
					frame.repaint();
					try {
						editorpanel();
					} catch (DocumentException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = GUI.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			return null;
		}
	}

	public void preview() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		preview = new PreviewPan(new File(destination.getText() + "/"
				+ name.getText()));

		frame.setResizable(true);

		int height = (int) (screenSize.getHeight() - 20);
		int width = (int) (screenSize.getWidth() - 20);

		frame.setPreferredSize(new Dimension(width, height));
		preview.setBounds(380, 0, 800, (int) (screenSize.getHeight() - 60));
		EditorPanel.add(preview, BorderLayout.CENTER);
		frame.add(EditorPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void modify() {
		EditorPanel.remove(preview);
		frame.remove(EditorPanel);
		try {
			BarLinesPDF.convertPDF(input.getText(), (destination.getText()
					+ "/" + name.getText()));
		} catch (DocumentException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		preview();
	}

	private void editorpanel() throws DocumentException, IOException {
		// left panel
		frame.setSize(1000, 1000);
		String inputname = name.getText();
		StringBuffer buffer = new StringBuffer(name.getText().substring(0,
				name.getText().indexOf('.')));
		buffer.append(".pdf");
		name.setText(buffer.toString());
		DataToArray.textToArray(input.getText());
		SGSPF.setText(Double.toString(DataToArray.getSpacing()));
		STitleF.setText(DataToArray.getTitle());
		TitleF.setText(DataToArray.getsubTitle());
		BarLinesPDF.convertPDF(input.getText(),
				(destination.getText() + "/" + name.getText()));

		JLabel NameL = new JLabel("Input Name:" + inputname);
		NameL.setBounds(0, 0, 500, 30);
		EditorPanel.add(NameL);

		JLabel OUTNAME = new JLabel("Output Name:");
		OUTNAME.setBounds(0, 30, 200, 30);
		EditorPanel.add(OUTNAME);
		name.setBounds(100, 30, 220, 30);
		EditorPanel.add(name);

		JLabel OUTDES = new JLabel("Output directory:");
		OUTDES.setBounds(0, 70, 150, 30);
		EditorPanel.add(OUTDES);
		destination.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		destination.setBounds(123, 70, 200, 30);
		EditorPanel.add(destination);

		JLabel TitleL = new JLabel("Title:");
		TitleL.setBounds(0, 110, 50, 30);
		EditorPanel.add(TitleL);
		
		TitleF.setBounds(40, 110, 200, 30);
		TitleF.setText(DataToArray.getTitle());
		EditorPanel.add(TitleF);

		JLabel STitleL = new JLabel("Subtitle:");
		STitleL.setBounds(0, 150, 200, 30);
		EditorPanel.add(STitleL);

		STitleF.setText(DataToArray.getTitle());
		STitleF.setBounds(65, 150, 255, 30);
		EditorPanel.add(STitleF);

		JLabel SGBSL = new JLabel("Set Group Bar Spacing: Range Between 50-90");
		SGBSL.setBounds(0, 190, 320, 30);
		EditorPanel.add(SGBSL);

		final JSlider setGroupBarSpacing = new JSlider(50, 90);
		setGroupBarSpacing.setBounds(5, 220, 280, 30);
		EditorPanel.add(setGroupBarSpacing);

		int i = setGroupBarSpacing.getValue();
		SGBSF.setText(Integer.toString(i));
		SGBSF.setBounds(285, 220, 30, 30);
		EditorPanel.add(SGBSF);

		JLabel SGSPL = new JLabel("Set The Given Spacing: Range Between 4-20");
		SGSPL.setBounds(0, 250, 320, 30);
		EditorPanel.add(SGSPL);

		final JSlider setGivenSpacing = new JSlider(4, 20);
		setGivenSpacing.setBounds(5, 280, 280, 30);
		EditorPanel.add(setGivenSpacing);
		SGSPF.setBounds(285, 280, 30, 30);
		EditorPanel.add(SGSPF);

		// buttons
		JButton save = new JButton("Save");
		save.setBounds(10, 500, 100, 30);
		EditorPanel.add(save);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, "The pdf has been saved with latest changes.",
						null, JOptionPane.INFORMATION_MESSAGE);
				frame.setVisible(false);
				new GUI();
			}
		});
		JButton apply = new JButton("Apply");
		apply.setBounds(120, 500, 100, 30);
		EditorPanel.add(apply);
		apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modify();
			}
		});
		
		JButton Exit = new JButton("Exit without saving");
		Exit.setBounds(10, 532, 200, 30);
		EditorPanel.add(Exit);
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object[] options = {"Yes, save and exit",
                "No, delete the file and exit"};
				int n = JOptionPane.showOptionDialog(frame,
					    "would you like to save the pdf before you exit?",
					    "A Silly Question",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,     //do not use a custom Icon
					    options,  //the titles of buttons
					    options[0]); //default button title
				System.out.println(n);
				if (n==0){ //yes
					modify();
					frame.setVisible(false);
					new GUI();
				}else if(n==1){ //no
					File file = new File(destination.getText() + "/" + name.getText());
					boolean b = file.delete();
					if(b==true){
						JOptionPane.showMessageDialog(frame, "Preview file has been removed.",
								null, JOptionPane.INFORMATION_MESSAGE);
						//System.exit(0);
						frame.setVisible(false);
						new GUI();
					}
				}else{
					
				}
			}
		});

		// JLabel

		EditorPanel.add(NameL);

		setGroupBarSpacing.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				BarLinesPDF.SetGroupBarSpacing(setGroupBarSpacing.getValue());
				SGBSF.setText(Integer.toString(setGroupBarSpacing.getValue()));
			}
		});
		final JSlider setWhiteSpace = new JSlider(2, 20);
		setWhiteSpace.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				BarLinesPDF.SetWhiteSpace(setWhiteSpace.getValue());
			}
		});

		final JSlider setBarSpacing = new JSlider(2, 20);
		setBarSpacing.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				BarLinesPDF.SetBarSpacing(setBarSpacing.getValue());
			}
		});

		setGivenSpacing.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				BarLinesPDF.SetGivenSpacing(setGivenSpacing.getValue());
				SGSPF.setText(Integer.toString(setGivenSpacing.getValue()));
				EditorPanel.repaint();
			}
		});
		final JSlider setNoteFontSize = new JSlider(2, 20);
		setNoteFontSize.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				BarLinesPDF.SetNoteFontSize(setNoteFontSize.getValue());
			}
		});
		SGBSF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setGroupBarSpacing.setValue(Integer.parseInt(SGBSF.getText()));
				modify();
			}
		});

		preview();

	}

	public static int getgivenspacing() {
		return (int) Double.parseDouble(SGSPF.getText());
	}

	public static int getgroupbarspacing() {
		return Integer.parseInt(SGBSF.getText());
	}

	public static String getTitle1() {
		String s = TitleF.getText();
		return s;
	}
	public static String getsubTitle1(){
		return STitleF.getText();
	}

}