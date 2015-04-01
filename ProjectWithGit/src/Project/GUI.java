package Project;

import javax.annotation.processing.FilerException;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.tools.JavaFileManager;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument.Destination;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.TileObserver;
import java.io.*;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.*;
import java.awt.*;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

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
	public static JTextField TitleF = new JTextField(DataToArray.getTitle());
	public static String inputname;
	public static String deskeeper;
	public static JTextField SWSF = new JTextField("1.0");
	public static JTextField SBSF = new JTextField("7");
	public static JTextField SNFF = new JTextField("9");
	public static File[] list;
	public static String[] list1 = { "COURIER", "HELVETICA", "SYMBOL", "TIMES"};
	public static int index;

	// private static JTextField source = new JTextField();

	private GUI() {

		input = new JTextField();
		frame = new JFrame("ASCII Guitar Tablature to PDF");
		destination = new JTextField();
		name = new JTextField();
		PreviewPan preview;
		// private JPanel body;
		SGBSF = new JTextField("75");
		SGSPF = new JTextField(Double.toString(DataToArray.getSpacing()));
		EditorPanel = new JPanel(null);
		STitleF = new JTextField(DataToArray.getsubTitle());
		TitleF = new JTextField(DataToArray.getTitle());
		SWSF = new JTextField("1.0");
		SBSF = new JTextField("7");
		SNFF = new JTextField("9");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		list = new File[100];

		final JButton OpenB = new JButton("Preview and Convert");

		final JButton QuickB = new JButton("Convert Only");
		final JButton ExitB = new JButton("Exit");
		final JButton AboutB = new JButton("About");
		final JPanel OpenerPanel = new JPanel(null);
		final JButton opening = new JButton("Open");

		frame.setSize(315, 140);
		opening.setBounds(5, 5, 150, 50);
		OpenerPanel.add(opening);
		AboutB.setBounds(155, 5, 150, 50);
		OpenerPanel.add(AboutB);
		ExitB.setBounds(5, 55, 300, 50);
		OpenerPanel.add(ExitB);
		frame.add(OpenerPanel);

		frame.setVisible(true);
		frame.setResizable(false);

		opening.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Text Files", "txt");
				fc.setFileFilter(filter);
				File mnmn = new File("C:\\Users\\Behshad\\Desktop");
				fc.setCurrentDirectory(mnmn);
				fc.setMultiSelectionEnabled(true);
				int response = fc.showOpenDialog(GUI.this);
				if (response == JFileChooser.APPROVE_OPTION) {
					list = fc.getSelectedFiles();
					input.setText(fc.getSelectedFile().getPath());
					name.setText(fc.getSelectedFile().getName());
					destination.setText(list[0].getParent());
					frame.setResizable(true);
					OpenerPanel.removeAll();
					frame.setSize(320, 200);
					OpenB.setBounds(10, 5, 300, 50);
					OpenerPanel.add(OpenB);
					QuickB.setBounds(10, 55, 150, 50);
					OpenerPanel.add(QuickB);
					AboutB.setBounds(160, 55, 150, 50);
					OpenerPanel.add(AboutB);
					ExitB.setBounds(10, 105, 300, 50);
					OpenerPanel.add(ExitB);
					frame.add(OpenerPanel);
					frame.setResizable(false);
				}
			}
		});

		QuickB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					DataToArray.textToArray(list);
				} catch (DocumentException | IOException | NullPointerException e2) {

					JOptionPane.showMessageDialog(frame,
							"There are no files selected.",
							"ASCII Tablature to PDF Message",
							JOptionPane.INFORMATION_MESSAGE, null);
					EditorPanel.removeAll();
					frame.removeAll();
					frame.setVisible(false);
					new GUI();
				}
				inputname = list[0].getName();
				deskeeper = destination.getText();
				StringBuffer buffer = new StringBuffer(list[0].getName()
						.substring(0, list[0].getName().indexOf('.')));
				System.out.println(buffer);
				name.setText(buffer.toString());
				SGSPF.setText(Double.toString(DataToArray.getSpacing()));
				TitleF.setText(DataToArray.getTitle());
				STitleF.setText(DataToArray.getsubTitle());
				try {
					BarLinesPDF.convertPDF(list, (destination.getText() + "/"
							+ name.getText() + ".pdf"));
					JOptionPane.showMessageDialog(frame, "Conversion Complete",
							"ASCII Tablature to PDF Message",
							JOptionPane.INFORMATION_MESSAGE, null);
					EditorPanel.removeAll();
					frame.removeAll();
					frame.setVisible(false);
					new GUI();
				} catch (DocumentException | IOException e1) {

					e1.printStackTrace();
				}
			}
		});
		ExitB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});

		AboutB.setIconTextGap(JOptionPane.INFORMATION_MESSAGE);
		AboutB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String detail = "Preview and Convert: Previews the expected PDF, and then converts it after modifications. \n"
						+ "Convert Only: Converts the ASCII to PDF according to default settings. Output name is the same as the input name. \n"
						+ "About: How to use the program. \n"
						+ "Exit: The program is terminated. \n\n"
						+ "Created by: \n"
						+ "Kevin Arindaeng, Rami Abou-Nassar, Abasifreke James, Daniel McVicar, Behshad Sebthosseini";
				JOptionPane.showMessageDialog(frame, detail,
						"ASCII Tablature to PDF Manual",
						JOptionPane.INFORMATION_MESSAGE, null);
			}
		});

		OpenB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					OpenerPanel.removeAll();
					frame.remove(OpenerPanel);
					editorpanel();
				} catch (DocumentException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			// }
		});
	}

	public void preview() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		preview = new PreviewPan(new File(destination.getText() + "/"
				+ name.getText() + ".pdf"));

		frame.setResizable(true);
		frame.setSize(screenSize);
		preview.setBounds(380, 0, frame.getWidth() - 380,
				frame.getHeight() - 40);
		EditorPanel.add(preview);
		frame.add(EditorPanel);
		frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

	}

	public void modify() {
		EditorPanel.remove(preview);
		frame.remove(EditorPanel);
		try {
			BarLinesPDF.convertPDF(list,
					(destination.getText() + "/" + name.getText() + ".pdf"));
			preview();
		} catch (DocumentException | IOException e1) {
			e1.printStackTrace();
		}

	}

	private void editorpanel() throws DocumentException, IOException {

		JLabel fontLabel = new JLabel("Select your font:");
		final JComboBox<String> drop = new JComboBox<String>();
		for(int i=0;i<=list1.length-1;i++)
			drop.addItem(list1[i]);
		drop.setSelectedItem("HELVETICA");
		index = drop.getSelectedIndex();
		
		drop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				index = drop.getSelectedIndex();
				//System.out.println(index);
			}
		});

		inputname = list[0].getName();
		deskeeper = destination.getText();
		StringBuffer buffer = new StringBuffer(list[0].getName().substring(0,
				list[0].getName().indexOf('.')));
		name.setText(buffer.toString());
		DataToArray.textToArray(list);
		SGSPF.setText(Float.toString(DataToArray.getSpacing()));
		TitleF.setText(DataToArray.getTitle());
		STitleF.setText(DataToArray.getsubTitle());
		BarLinesPDF.convertPDF(list,
				(destination.getText() + "/" + name.getText() + ".pdf"));
		preview();
		
		drop.setBounds(160, 480, 150, 30);
		EditorPanel.add(drop);
		fontLabel.setBounds(10, 480, 150, 30);
		EditorPanel.add(fontLabel);

		JLabel NameL = new JLabel("Input Name: " + inputname);
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
		destination.setBounds(123, 70, 200, 30);
		EditorPanel.add(destination);
		JButton browse = new JButton("...");
		browse.setBounds(323, 70, 30, 30);
		browse.setToolTipText("Browse for directory and choose a file name");
		EditorPanel.add(browse);
		browse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				File file = new File(BarLinesPDF.destination1);
				fc.setSelectedFile(file);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"PDF Files", "pdf");
				fc.setFileFilter(filter);
				int n = fc.showSaveDialog(GUI.this);
				if (n == JFileChooser.APPROVE_OPTION) {
					destination.setText(list[0].getParent());
					name.setText(fc.getSelectedFile().getName());
					EditorPanel.repaint();
				}

			}
		});

		JLabel TitleL = new JLabel("Title:");
		TitleL.setBounds(0, 110, 50, 30);
		EditorPanel.add(TitleL);

		TitleF.setBounds(40, 110, 200, 30);
		TitleF.setText(DataToArray.getTitle());
		EditorPanel.add(TitleF);

		JLabel STitleL = new JLabel("Subtitle:");
		STitleL.setBounds(0, 150, 200, 30);
		EditorPanel.add(STitleL);

		STitleF.setText(DataToArray.getsubTitle());
		STitleF.setBounds(65, 150, 255, 30);
		EditorPanel.add(STitleF);

		JLabel SGBSL = new JLabel("Set Group Bar Spacing: Range Between 50-90");
		SGBSL.setBounds(0, 190, 375, 30);
		EditorPanel.add(SGBSL);

		final JSlider setGroupBarSpacing = new JSlider(50, 90,
				Integer.parseInt(SGBSF.getText()));
		setGroupBarSpacing.setBounds(5, 220, 280, 30);
		EditorPanel.add(setGroupBarSpacing);

		SGBSF.setText(Integer.toString(setGroupBarSpacing.getValue()));
		SGBSF.setBounds(285, 220, 40, 30);
		EditorPanel.add(SGBSF);

		JLabel SGSPL = new JLabel("Set The Given Spacing: Range Between 2-10");
		SGSPL.setBounds(0, 250, 320, 30);
		EditorPanel.add(SGSPL);

		final FloatJSlider setGivenSpacing = new FloatJSlider(2.0f, 10.0f,
				Float.parseFloat(SGSPF.getText()));
		setGivenSpacing.setBounds(5, 280, 280, 30);
		setGivenSpacing.setFloatValue(Float.parseFloat(SGSPF.getText()));
		EditorPanel.add(setGivenSpacing);
		SGSPF.setBounds(285, 280, 40, 30);
		EditorPanel.add(SGSPF);

		// JLabel

		EditorPanel.add(NameL);

		setGroupBarSpacing.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// BarLinesPDF.SetGroupBarSpacing(setGroupBarSpacing.getValue());
				SGBSF.setText(Integer.toString(setGroupBarSpacing.getValue()));
			}
		});
		SGBSF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setGroupBarSpacing.setValue(Integer.parseInt(SGBSF.getText()));
			}
		});

		final FloatJSlider setWhiteSpace = new FloatJSlider(1.0f, 5.0f,
				Float.parseFloat(SWSF.getText() + "f"));
		// setWhiteSpace.setValue(Integer.parseInt(SWSF.getText()));
		SWSF.setBounds(285, 330, 40, 30);
		EditorPanel.add(SWSF);
		setWhiteSpace.setBounds(5, 330, 280, 30);
		EditorPanel.add(setWhiteSpace);
		JLabel SWSL = new JLabel("Set White Space: Range between 1-10");
		SWSL.setBounds(0, 300, 320, 30);
		EditorPanel.add(SWSL);

		setWhiteSpace.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				SWSF.setText(Float.toString(setWhiteSpace.getFloatValue()));
				// EditorPanel.repaint();
			}
		});
		SWSF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setWhiteSpace.setFloatValue(Float.parseFloat(SWSF.getText()));

				// EditorPanel.repaint();
			}
		});

		final JSlider setBarSpacing = new JSlider(4, 10, 7);
		JLabel SBSL = new JLabel("Set Bar Spacing: Range between 1-10");
		SBSL.setBounds(0, 360, 380, 30);
		EditorPanel.add(SBSL);

		setBarSpacing.setBounds(0, 390, 280, 30);
		SBSF.setText(Integer.toString(setBarSpacing.getValue()));
		SBSF.setBounds(285, 385, 40, 30);
		EditorPanel.add(SBSF);
		EditorPanel.add(setBarSpacing);
		final JSlider setNoteFontSize = new JSlider(9, 15,
				Integer.parseInt(SNFF.getText()));

		JButton Default = new JButton("Default");
		Default.setBounds(10, frame.getHeight() - 170, 210, 30);
		EditorPanel.add(Default);

		Default.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				SGBSF.setText("75");
				SGSPF.setText(Float.toString(DataToArray.getSpacing()));
				STitleF.setText(DataToArray.getsubTitle());
				TitleF.setText(DataToArray.getTitle());
				SWSF.setText("1.0");
				SBSF.setText("7");
				SNFF.setText("9");
				setWhiteSpace.setFloatValue(Float.parseFloat(SWSF.getText()));
				setGroupBarSpacing.setValue(Integer.parseInt(SGBSF.getText()));
				setBarSpacing.setValue(Integer.parseInt(SBSF.getText()));
				setGivenSpacing.setFloatValue(Float.parseFloat(SGSPF.getText()));
				setNoteFontSize.setValue(Integer.parseInt(SNFF.getText()));
				// name.setText((String) input.getName());
				// destination.setText(list[0].getParent());
				modify();
			}
		});

		setBarSpacing.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// BarLinesPDF.SetBarSpacing(setBarSpacing.getFloatValue());
				SBSF.setText(Integer.toString(setBarSpacing.getValue()));
			}
		});
		SBSF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setBarSpacing.setValue(Integer.parseInt(SBSF.getText()));
			}
		});

		setGivenSpacing.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				SGSPF.setText(Float.toString(setGivenSpacing.getFloatValue()));
				// EditorPanel.repaint();
			}
		});

		SGSPF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setGivenSpacing.setFloatValue(Float.parseFloat(SGSPF.getText()));
				// EditorPanel.repaint();
			}
		});

		JLabel SNFL = new JLabel("Set Note Font Size: Range between 2-10");
		SNFL.setBounds(0, 415, 380, 30);
		setNoteFontSize.setBounds(0, 445, 280, 30);

		SNFF.setBounds(285, 445, 40, 30);
		EditorPanel.add(setNoteFontSize);
		EditorPanel.add(SNFL);
		EditorPanel.add(SNFF);

		setNoteFontSize.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				SNFF.setText(Integer.toString(setNoteFontSize.getValue()));
			}
		});
		SNFF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setNoteFontSize.setValue(Integer.parseInt(SNFF.getText()));
			}
		});
				
		JButton apply = new JButton("Apply");
		apply.setBounds(118, frame.getHeight() - 135, 102, 30);
		EditorPanel.add(apply);
		apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (destination.getText() + "/" + name != BarLinesPDF
						.getDestination()) {
					File file = new File(BarLinesPDF.getDestination());
					file.delete();
					modify();
				} else {
					modify();
				}
			}
		});

		JButton save = new JButton("Save");
		save.setBounds(10, frame.getHeight() - 135, 103, 30);
		EditorPanel.add(save);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame,
						"The PDF has been saved with latest changes.", null,
						JOptionPane.INFORMATION_MESSAGE);
				frame.setVisible(false);
				new GUI();
			}
		});

		JButton Exit = new JButton("Exit");
		Exit.setBounds(10, frame.getHeight() - 100, 210, 30); // 623+30
		EditorPanel.add(Exit);
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object[] options = { "Yes, save and exit",
						"No, delete the file and exit" };
				int n = JOptionPane.showOptionDialog(frame,
						"Would you like to save the PDF before you exit?",
						"ASCII Guitar Tablature to PDF",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, // do not use a
						// custom Icon
						options, // the titles of buttons
						options[0]); // default button title
				if (n == 0) { // yes
					modify();
					frame.setVisible(false);
					new GUI();
				} else if (n == 1) { // no

					File file = new File(BarLinesPDF.destination1);
					boolean b = file.delete();
					if (b == true) {
						JOptionPane.showMessageDialog(frame,
								"Preview file has been removed.", null,
								JOptionPane.INFORMATION_MESSAGE);
						frame.setVisible(false);
						new GUI();
					}
				} else {

				}
			}
		});
		EditorPanel.repaint();
	}

	public static float getgivenspacing() {
		return Float.parseFloat(SGSPF.getText());
	}

	public static int getgroupbarspacing() {
		return Integer.parseInt(SGBSF.getText());
	}

	public static String getTitle1() {
		return TitleF.getText();
	}

	public static String getsubTitle1() {
		return STitleF.getText();
	}

	public static float getWhiteSpacing() {

		return Float.parseFloat(SWSF.getText());
	}

	public static int getbarspacing() {
		return Integer.parseInt(SBSF.getText());
	}

	public static Float getnotefont() {
		return Float.parseFloat(SNFF.getText()+"f");
	}

	public static File[] getList() {
		return list;
	}
	public static String getFont1(){
		return list1[index];
	}
}