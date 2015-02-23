package KevinsWork;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.PanelUI;

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
			"Convert Guitar Notes to pdf Format");
	public static JTextField destination = new JTextField();
	public static JTextField name = new JTextField();

	// private static JTextField source = new JTextField();

	private GUI() {

		frame.setSize(325, 215);
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
		JButton OpenB = new JButton("Modify and convert");

		JButton QuickB = new JButton("Fast Convert");

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
		//OpenerPanel.setSize(frame.getWidth(), frame.getHeight());
		//OpenerPanel.setBounds(0, 0, 320, 10);
		frame.add(OpenerPanel);
		//frame.pack();
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

	private void editorpanel() throws DocumentException, IOException {
		//left panel
		//frame.setLayout(new BorderLayout());
		DataToArray.textToArray(input.getText());
		final JPanel EditorPanel = new JPanel(null);
		frame.setSize(1000, 1000);
		//EditorPanel.setSize(500, 10000);
		String inputname = name.getText();
		StringBuffer buffer = new StringBuffer(name.getText().substring(0, name.getText().indexOf('.')));
		buffer.append(".pdf");
		//System.out.println(destination.getText());
		name.setText(buffer.toString());
		JLabel NameL = new JLabel("Input Name:"+ inputname);
		NameL.setBounds(0,0, 500, 30);
		EditorPanel.add(NameL);
		
		JLabel OUTNAME = new JLabel("Output Name:");
		OUTNAME.setBounds(0, 30, 200, 30);
		EditorPanel.add(OUTNAME);
		name.setBounds(100, 30, 220, 30);
		EditorPanel.add(name);
		
		JLabel OUTDES = new JLabel("Output directory:");
		OUTDES.setBounds(0, 70, 150, 30);
		EditorPanel.add(OUTDES);
		destination.setBounds(123, 70, 200, 30);
		EditorPanel.add(destination);
		
		JLabel TitleL = new JLabel("Title:");
		TitleL.setBounds(0, 110, 50, 30);
		EditorPanel.add(TitleL);
		
		JTextField TitleF = new JTextField();
		TitleF.setBounds(40, 110, 200, 30);
		TitleF.setText(DataToArray.getTitle());
		EditorPanel.add(TitleF);
		
		JLabel STitleL = new JLabel("Subtitle:");
		STitleL.setBounds(0, 150, 200, 30);
		EditorPanel.add(STitleL);
		
		JTextField STitleF = new JTextField(DataToArray.getsubTitle());
		STitleF.setBounds(65, 150, 260, 30);
		EditorPanel.add(STitleF);
		
		JLabel SGBSL = new JLabel("set group bar spacing:(range between 4-20)");
		SGBSL.setBounds(0, 190, 350, 30);
		EditorPanel.add(SGBSL);
		
		final JSlider setGroupBarSpacing = new JSlider(4, 20);
		setGroupBarSpacing.setBounds(5, 220, 270, 30);
		EditorPanel.add(setGroupBarSpacing);
		
		final JTextField SGBSF = new JTextField();
		int i = setGroupBarSpacing.getValue();
		SGBSF.setText(Integer.toString(i));
		SGBSF.setBounds(280, 220, 30, 30);
		EditorPanel.add(SGBSF);
		
		
		
		
		
		
		//JLabel 
		
		EditorPanel.add(NameL);
		
		setGroupBarSpacing.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				BarLinesPDF.SetGroupBarSpacing(setGroupBarSpacing.getValue());
				int i = setGroupBarSpacing.getValue();
				SGBSF.setText(Integer.toString(i));
				EditorPanel.repaint();
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
		final JSlider setGivenSpacing = new JSlider(2,20);
		setGivenSpacing.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				BarLinesPDF.SetGivenSpacing(setGivenSpacing.getValue());				
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
				EditorPanel.repaint();
			}
		});
		
		frame.add(EditorPanel); 

	}

}