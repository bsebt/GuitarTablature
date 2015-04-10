package Project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class BarLinesPDF {
	 // public static String DEST; //Destination, this should be changed
	// according to the GUI
	public static DataToArray a = new DataToArray();
	public static String TITLE_STRING = GUI.getTitle1();
	public static String COMPOSER_STRING = GUI.getsubTitle1();

	private static final float marginLeft = 50.0f; // Note original margins are
	// 36.0f for letter size
	private static final float marginRight = 50.0f;
	private static final float marginTop = 0.0f;
	private static final float marginBottom = 0.0f;
	// private static final int ROWS = 6;

	public static Font titleFont = new Font(FontFamily.HELVETICA, 30);
	public static Font composerFont = new Font(FontFamily.HELVETICA, 14);
	public static Font numberFont = new Font(FontFamily.HELVETICA, 9);
	public static Paragraph title;
	public static Paragraph composer;
		//	composerFont);

	private static Phrase currentChar;
	private static float noteFontSize = 9; // Size of the characters to be
	// written to the page
	private static float givenSpacing = (float) a.getSpacing(); // The
	// spacing given at the start of the program, change to variable once we read it in
	private static int barSpacing = 7; // Space between individual lines to be
	// drawn
	private static float whiteSpace = 1.0f; // Space around a written number
	// that does not have a bar line
	private static int groupBarSpacing = 75; // Spaces between the groups of 6
	// lines
	private static float topVoidSpace = 160; // Space at the top of the page for
	// info.
	private static float pageHeight = 800;
	private static float pageWidth = 620;
	// Note, standard page is 620 units wide and 800 units tall.

	// Data from DataToArray:

	// chars.get(i)[j].length ---> Gets the exact column number from bar i and
	// row j.
	private static ArrayList<char[][]> chars; // All chars from the text file:
	// you obtain by
	// .get(bar)[row][col]
	private static int maxCol; // The maximum # of the columns in the array.
	private static int totalRows;
	private static int bars;
	
	private static float singleBarLineWidth = 1;

	private static LineSeparator line = new LineSeparator();
	private static DataToArray data;
	public static String destination1;
	
	public static float doubleBarLineWidth = 2;
	public static float doubleBarLinePreview = 2;

	public static void convertPDF(File[] textFile, String Destination)
			throws DocumentException, IOException// Convert() throws
	// DocumentException,
	// IOException
	{

		a = new DataToArray(textFile);
		chars = a.getChars(); // Gets the array of
		// information
		maxCol = a.getMaxColumnAmount();
		totalRows = a.getTotalRowAmount();
		TITLE_STRING = a.getTitle();
		COMPOSER_STRING = a.getsubTitle();
		titleFont = FontFactory.getFont(GUI.getFont1(), 30);
		composerFont = FontFactory.getFont(GUI.getFont1(), 14);
		numberFont = FontFactory.getFont(GUI.getFont1(), GUI.getnotefont());
		title = new Paragraph(TITLE_STRING, titleFont);
		composer = new Paragraph(COMPOSER_STRING, composerFont);
		givenSpacing = GUI.getgivenspacing();
		destination1 = Destination;
		whiteSpace = GUI.getWhiteSpacing();
		barSpacing = GUI.getbarspacing();
		SetNoteFontSize(GUI.getnotefont()); // TODO replace all of these with
		// set methods rather than direct
		// assignment
		groupBarSpacing = GUI.getgroupbarspacing();
		doubleBarLinePreview = GUI.getDBLP();
		
		
		

		Document document = new Document(PageSize.LETTER, marginLeft,
				marginRight, marginTop, marginBottom);
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(Destination));
		// Header/Meta Data:
		document.addCreator("Kevin");
		document.addAuthor("Kevin Arindaeng");

		// Changing some object features: (alignment for text)
		title.setAlignment(Element.ALIGN_CENTER);
		composer.setAlignment(Element.ALIGN_CENTER);
		line.setAlignment(Element.ALIGN_MIDDLE);

		// Creating the document:
		document.open();
		PdfContentByte cb = writer.getDirectContent(); // writer to draw lines
		cb.setLineWidth(singleBarLineWidth);
		document.add(title);
		document.add(composer);

		// document.newPage(); TO DO add pages as needed, add a new loop
		if (chars.isEmpty() != true) {
			ColumnText column = new ColumnText(cb); // text bound left and right
			// on a series of lines
			int lineStart = 0;
			int rowPos = 0;
			int colPos = 0;
			int barPos = 0;

			int lastWriteX = 0;
			int lastWriteY = 0;
			boolean EOB = false;
			boolean doneWriting = false;
			boolean lastBarred = false;
			int firstBarOfLine = 0;
			
			int rowSave[][] = { { 0, 0, DataToArray.getLargestNumber(chars.get(barPos)), 0, 0 },
					{ 0, 0, chars.get(barPos)[1].length, 0, 0 },
					{ 0, 0, chars.get(barPos)[2].length, 0, 0 },
					{ 0, 0, chars.get(barPos)[3].length, 0, 0 },
					{ 0, 0, chars.get(barPos)[4].length, 0, 0 },
					{ 0, 0, chars.get(barPos)[5].length, 0, 0 },
					{ 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } }; // Used to store our
			// place when we
			// change between
			// lines, first
			// number is the bar
			// we are on, second
			// number is the
			// column.

			char arrayChar = chars.get(barPos)[rowPos][colPos]; // Gets first
			// character of
			// the bar = |
			int barLength = chars.get(barPos)[rowPos].length; // Gets size of
			// bar, so I can
			// check to see
			// if we are at
			// the end of
			// the array and
			// get the next
			// bar. TO DO
			// write a
			// method to
			// check to see
			// if there is
			// enough space
			// automatically

			for (float j = pageHeight - (float) topVoidSpace; j > 0 + marginBottom
					&& !doneWriting; j -= groupBarSpacing) // Groups of bars,
			// 100 is void space
			// at the top for
			// title
			{
				rowPos = 0; // Reset the row we are on (start at the top, reset
				// every 6)
				for (float i = barSpacing * 6; i > 0; i -= barSpacing) // Individual
				// horizontal
				// bars
				{
					barPos = rowSave[rowPos][0]; // Pull up what bar we have
					// written to on this line
					colPos = rowSave[rowPos][1]; // Pull up how many columns we
					// have written on this line
					barLength = rowSave[rowPos][2]; // pull up the bar length,
					// in case we changed bars
					// at the very end of the
					// last line
					lastWriteX = rowSave[rowPos][3]; // Pull up the last write
					// co-ords for this line
					// of music
					lastWriteY = rowSave[rowPos][4];
					System.out.println("barpos = " + barPos);
					if (barPos < chars.size())
					{
						arrayChar = chars.get(barPos)[rowPos][colPos];
					}

					boolean noSpaceAvailable = false; // Used to check if there
					// is enough space to
					// write the next
					// character
					lineStart = 0;
					// int characterSpace = HowManyCharacters(barPos) *
					// givenSpacing;
					for (float q = (float) (marginLeft + givenSpacing); (q < pageWidth
							- marginRight - givenSpacing); q += givenSpacing) // Individual
					// characters
					{
						boolean cancelBarDraw = false;
						EOB = false; // used to check if there is a next bar to
						// pull from, avoids index errors
						if (q + givenSpacing < pageWidth - (int) marginLeft
								- givenSpacing) // Don't print a character on
						// the far right side to make
						// room for the bar line
						{
							// System.out.println("Column: " + colPos + " Row: "
							// + rowPos + " Bar: " + barPos);
							while (arrayChar == ' ' || arrayChar == '&') // TO DO do this check when
							// we read in data,
							// empty space is
							// normaly a mistake or
							// junk DataToArray.
							// This is a quick fix
								
							{
								colPos++;
								if (colPos == barLength) {
									colPos = 0;
									barPos++;
								}
								if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos == 5) {
										if (!lastBarred) {
											cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing * 5);// draw
											// the
											// very
											// last
											// bar
											// line
											lastBarred = true;
										}
										doneWriting = true; // If there are no
										// more bars to
										// write, stop
									}
								}
								if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos]; // Load
									// the
									// next
									// char
									// to
									// write
									barLength = chars.get(barPos)[rowPos].length;
								}
							}

							if (noSpaceAvailable) {
								// Do nothing, we are waiting until the end of
								// the this line
							} 
							else if (arrayChar == '|') 
							{
								if (i - barSpacing > 0 && barPos < chars.size())
								{
									if (InLeftSide(chars.get(barPos), colPos)) //We are on the left side of a bar looking to write
									{
										if (chars.get(barPos)[2][2] == '*')
										{
											//This is a double bar line, draw a thick line
											line.drawLine(cb, 0f, 0f, 0f);
											cb.setLineWidth(doubleBarLineWidth);
											cb.moveTo(q, i + j - (doubleBarLinePreview - 1));
											cb.lineTo(q, i + j - barSpacing + (doubleBarLinePreview - singleBarLineWidth));
											cb.stroke();
											cb.setLineWidth(singleBarLineWidth);
											
											cb.moveTo(q+givenSpacing, i + j);
											cb.lineTo(q+givenSpacing, i + j - barSpacing);
											cb.stroke();
											
											q+=givenSpacing;
											//We also must get rid of the line that will be drawn after, because it is already taken care of
											if (chars.get(barPos)[rowPos][1] == '|')
											{
												char[][] tempCharArrayDoubleBar = chars.get(barPos);
												tempCharArrayDoubleBar[rowPos][1] = '&'; // Set the bar to an empty space so it is ignored when we get to it, so everything lines up properly
												chars.set(barPos, tempCharArrayDoubleBar);
											}
										}
										else
										{
											//This is a normal line, draw a normal one as long as we arn't at the beginning of a line.
											if (colPos != 0 || firstBarOfLine == 0)
											{
												line.drawLine(cb, 0f, 0f, 0f); // This is used to draw the lines, it allows cb.lineTo to function. Draws nothing on its own.
												cb.moveTo(q, i + j);
												cb.lineTo(q, i + j - barSpacing);
												
												if (chars.get(barPos)[rowPos][colPos+1] == '|' && colPos == 0)
												{
													char[][] tempCharArrayDoubleBar = chars.get(barPos);
													tempCharArrayDoubleBar[rowPos][colPos+1] = '&'; // Set the bar to an empty space so it is ignored when we get to it, so everything lines up properly
													chars.set(barPos, tempCharArrayDoubleBar);
												}
											}
											else
											{
												 q-= givenSpacing;
											}
										}
									}
									else //We are on the right side of the bar looking to write a double bar
									{
										if (chars.get(barPos)[2][colPos-1] == '*')
										{
											//There should be a double line, draw a thick line
											q+=givenSpacing;
											
											line.drawLine(cb, 0f, 0f, 0f);
											cb.setLineWidth(singleBarLineWidth);
											cb.moveTo(q-givenSpacing, i + j);
											cb.lineTo(q-givenSpacing, i + j - barSpacing);
											cb.stroke();											
											
											cb.setLineWidth(doubleBarLineWidth);
											cb.moveTo(q, i + j - (doubleBarLinePreview - 1));
											cb.lineTo(q, i + j - barSpacing + (doubleBarLinePreview - singleBarLineWidth));
											cb.stroke();
											cb.setLineWidth(singleBarLineWidth);
											
											if (colPos < barLength-1)
											{
												//We must also get rid of the line after that will be drawn, because it is already taken care of
												if (chars.get(barPos)[rowPos][colPos+1] == '|')
												{
													char[][] tempCharArrayDoubleBar = chars.get(barPos);
													tempCharArrayDoubleBar[rowPos][colPos+1] = '&'; // Set the bar to an empty space so it is ignored when we get to it, so everything lines up properly
													chars.set(barPos, tempCharArrayDoubleBar);
												}
												
												if (barPos < chars.size()-1) //There is another bar, we should check it to see if there is a double bar
												{
													if ((DataToArray.getLargestNumber(chars.get(barPos+1))) * givenSpacing <= (pageWidth - marginRight - q)) 
													{
														//There is space for the next line, so we don't need it to draw it's own thick line
														if (chars.get(barPos+1)[rowPos][0] == '|')
														{
															char[][] tempCharArrayDoubleBar = chars.get(barPos+1);
															tempCharArrayDoubleBar[rowPos][0] = '&'; // Set the bar to an empty space so it is ignored when we get to it, so everything lines up properly
															chars.set(barPos+1, tempCharArrayDoubleBar);
														}
														
														if (chars.get(barPos+1)[rowPos][1] == '|')
														{
															char[][] tempCharArrayDoubleBar = chars.get(barPos+1);
															tempCharArrayDoubleBar[rowPos][1] = '&'; // Set the bar to an empty space so it is ignored when we get to it, so everything lines up properly
															chars.set(barPos+1, tempCharArrayDoubleBar);
														}
														
														if (chars.get(barPos+1)[2][2] == '*')
														{
															line.drawLine(cb, 0f, 0f, 0f);
															cb.setLineWidth(singleBarLineWidth);
															cb.moveTo(q+givenSpacing, i + j);
															cb.lineTo(q+givenSpacing, i + j - barSpacing);
															cb.stroke();
															q+=givenSpacing;
														}
													}
													else
													{
														//The repeat is split across two bars, if there is at the bottom it will be handled by the left branch
													}
												}
											}
											else
											{
												//This was already the end, do nothing.
												if (barPos < chars.size()-1) //There is another bar, we should check it to see if there is a double bar
												{
													if ((DataToArray.getLargestNumber(chars.get(barPos+1))) * givenSpacing <= (pageWidth - marginRight - q)) 
													{
														//There is space for the next bar, so we don't need it to draw it's own thick line
														if (chars.get(barPos+1)[rowPos][0] == '|')
														{
															char[][] tempCharArrayDoubleBar = chars.get(barPos+1);
															tempCharArrayDoubleBar[rowPos][0] = '&'; // Set the bar to an empty space so it is ignored when we get to it, so everything lines up properly
															chars.set(barPos+1, tempCharArrayDoubleBar);
														}
														
														if (chars.get(barPos+1)[rowPos][1] == '|')
														{
															char[][] tempCharArrayDoubleBar = chars.get(barPos+1);
															tempCharArrayDoubleBar[rowPos][1] = '&'; // Set the bar to an empty space so it is ignored when we get to it, so everything lines up properly
															chars.set(barPos+1, tempCharArrayDoubleBar);
														}
														
														if (chars.get(barPos+1)[2][2] == '*')
														{
															line.drawLine(cb, 0f, 0f, 0f);
															cb.setLineWidth(singleBarLineWidth);
															cb.moveTo(q+givenSpacing, i + j);
															cb.lineTo(q+givenSpacing, i + j - barSpacing);
															cb.stroke();
															q+=givenSpacing;
														}
													}
													else
													{
														//The repeat is split across two bars, if there is at the bottom it will be handled by the left branch
													}
												}
											}
										}
										else
										{
											//This is a normal line, draw a normal one.
											if (colPos != 0 || firstBarOfLine == 0)
											{
												if (barPos < chars.size()-1)
												{
													if (chars.get(barPos+1)[2][2] != '*')
													{
														line.drawLine(cb, 0f, 0f, 0f); // This is used to draw the lines, it allows cb.lineTo to function. Draws nothing on its own.
														cb.moveTo(q, i + j);
														cb.lineTo(q, i + j - barSpacing);
													}
													else if ((DataToArray.getLargestNumber(chars.get(barPos+1)) * givenSpacing) > (pageWidth - marginRight - q)) //Draw a normal line
													{
														line.drawLine(cb, 0f, 0f, 0f); // This is used to draw the lines, it allows cb.lineTo to function. Draws nothing on its own.
														cb.moveTo(q, i + j);
														cb.lineTo(q, i + j - barSpacing);
													}
													else
													{
														//The next line is a repeat, so we'll collide with it unless we don't draw this. Do nothing
													}
												}
												else
												{
													//We are at the end, so draw a normal line
													line.drawLine(cb, 0f, 0f, 0f); // This is used to draw the lines, it allows cb.lineTo to function. Draws nothing on its own.
													cb.moveTo(q, i + j);
													cb.lineTo(q, i + j - barSpacing);
												}
											}
										}
									}
								}
								{
									colPos++;
									if (colPos == barLength) {
									colPos = 0;
									barPos++;
									if (barPos < chars.size()) {
									if ((DataToArray.getLargestNumber(chars.get(barPos)) * givenSpacing) >= (pageWidth
									- marginRight - q)) {
									noSpaceAvailable = true;
									if (rowPos != 5) {
									//cb.moveTo(q + givenSpacing,
									//i + j);
									//cb.lineTo(q + givenSpacing,
									//i + j - barSpacing);
									}
									}
									}
									}
									if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos == 5) {
									if (!lastBarred) {
									cb.moveTo(q, i + j);
									//cb.lineTo(q, i + j + barSpacing * 5);// draw the very
									// last bar line
									lastBarred = true;
									}
									doneWriting = true; // If there are
									// no more bars
									// to write,
									// stop
									}
									}
									if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos]; // Load
									// the
									// next
									// char
									// to
									// write
									barLength = chars.get(barPos)[rowPos].length;
									}
									}
							}
							else if (arrayChar == '-' || arrayChar == '<') 
							{
								colPos++;
								if (colPos == barLength) {
									colPos = 0;
									barPos++;
									if (barPos < chars.size()) {
										if ((DataToArray.getLargestNumber(chars.get(barPos)) * givenSpacing) > (pageWidth
												- marginRight - q)) {
											noSpaceAvailable = true;
											if (rowPos != 5) {
												cb.moveTo(q + givenSpacing, i
														+ j);
												cb.lineTo(q + givenSpacing, i
														+ j - barSpacing);
											}
										}
									}
								}
								if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos == 5) {
										if (!lastBarred) {
											cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing * 5);// draw
											// the
											// very
											// last
											// bar
											// line
											lastBarred = true;
										}
										doneWriting = true; // If there are no
										// more bars to
										// write, stop
									}
								}
								// System.out.println("Column: " + colPos +
								// " Row: " + rowPos + " Bar: " + barPos +
								// " EOB: " + EOB);
								if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos]; // Load
									// the
									// next
									// char
									// to
									// write
									barLength = chars.get(barPos)[rowPos].length;
								}
							} 
							else if (arrayChar == '*') {
								createCircle(q-(givenSpacing/2), i + j, cb);
								colPos++;
								if (colPos == barLength) {
									colPos = 0;
									barPos++;
									if (barPos < chars.size()) {
										if ((DataToArray.getLargestNumber(chars.get(barPos)) * givenSpacing) > (pageWidth
												- marginRight - q)) {
											noSpaceAvailable = true;
											if (rowPos != 5) {
												cb.moveTo(q + givenSpacing, i
														+ j);
												cb.lineTo(q + givenSpacing, i
														+ j - barSpacing);
											}
										}
									}
								}
								if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos == 5) {
										if (!lastBarred) {
											cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing * 5);// draw
											// the
											// very
											// last
											// bar
											// line
											lastBarred = true;
										}
										doneWriting = true; // If there are no
										// more bars to
										// write, stop
									}
								}
								// System.out.println("Column: " + colPos +
								// " Row: " + rowPos + " Bar: " + barPos +
								// " EOB: " + EOB);
								if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos]; // Load
									// the
									// next
									// char
									// to
									// write
									barLength = chars.get(barPos)[rowPos].length;
								}
							} else if (arrayChar == 's') // Draw a slash before
							// an s
							{
								line.drawLine(cb, 0f, 0f, 0f); // This is used
								// to draw the
								// lines, it
								// allows
								// cb.lineTo to
								// function.
								// Draws nothing
								// on its own.
								cb.moveTo(q - noteFontSize, i + j
										- noteFontSize / 3); // Give the option
								// for the user
								// to tweek the
								// settings for
								// this?
								cb.lineTo(q, i + j + noteFontSize / 3); // How
								// should
								// font
								// affect
								// it?

								colPos++;
								if (colPos == barLength) {
									colPos = 0;
									barPos++;
									if (barPos < chars.size()) {
										if ((DataToArray.getLargestNumber(chars.get(barPos)) * givenSpacing) > (pageWidth
												- marginRight - q)) {
											noSpaceAvailable = true;
											if (rowPos != 5) {
												cb.moveTo(q + givenSpacing, i
														+ j);
												cb.lineTo(q + givenSpacing, i
														+ j - barSpacing);
											}
										}
									}
								}
								if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos == 5) {
										if (!lastBarred) {
											cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing * 5);// draw
											// the
											// very
											// last
											// bar
											// line
											lastBarred = true;
										}
										doneWriting = true; // If there are no
										// more bars to
										// write, stop
									}
								}
								if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos]; // Load
									// the
									// next
									// char
									// to
									// write
									barLength = chars.get(barPos)[rowPos].length;
								}
							} else if (arrayChar == 'p') // Draw a phrasing line
							// between this
							// character and the
							// next one
							{
								if (i + j == lastWriteY) // The two characters
								// are on the same
								// line, all is
								// well!
								{
									cb.moveTo(q, i + j + noteFontSize); // We
									// are
									// drawing
									// the
									// curve
									// backwards,
									// so
									// set
									// start
									// point
									// to
									// our
									// current
									// location
									cb.curveTo((q + lastWriteX) / 2, i + j
											+ (noteFontSize * 2), lastWriteX,
											lastWriteY + noteFontSize);
								} else // The characters are on seperate lines,
										// or perhaps different pages. Arc to
										// the end of the page and beginning of
										// the page for both.
								{
									cb.moveTo(q, i + j + noteFontSize); // We
									// are
									// drawing
									// the
									// curve
									// backwards,
									// so
									// set
									// start
									// point
									// to
									// our
									// current
									// location
									cb.curveTo((q + marginLeft) / 2, i + j
											+ (noteFontSize * 2), marginLeft, i
											+ j + noteFontSize);

									cb.moveTo(pageWidth - marginRight,
											lastWriteY + noteFontSize);
									cb.curveTo(
											(lastWriteX + (pageWidth - marginRight)) / 2,
											lastWriteY + (noteFontSize * 2),
											lastWriteX, lastWriteY
													+ noteFontSize);
								}

								colPos++;
								if (colPos == barLength) {
									colPos = 0;
									barPos++;
									if (barPos < chars.size()) {
										if ((DataToArray.getLargestNumber(chars.get(barPos)) * givenSpacing) > (pageWidth
												- marginRight - q)) {
											noSpaceAvailable = true;
											if (rowPos != 5) {
												cb.moveTo(q + givenSpacing, i
														+ j);
												cb.lineTo(q + givenSpacing, i
														+ j - barSpacing);
											}
										}
									}
								}
								if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos == 5) {
										if (!lastBarred) {
											cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing * 5);// draw
											// the
											// very
											// last
											// bar
											// line
											lastBarred = true;
										}
										doneWriting = true; // If there are no
										// more bars to
										// write, stop
									}
								}
								if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos]; // Load
									// the
									// next
									// char
									// to
									// write
									barLength = chars.get(barPos)[rowPos].length;
								}
							} else if (arrayChar == '>') // Draw a diamond after
							// a > symbol, the
							// starting < can be
							// ignored since it
							// doesn't actualy
							// carry any info
							{
								createDiamond(q - givenSpacing, i + j, cb);
//								line.drawLine(cb, 0f, 0f, 0f); // This is used
//								// to draw the
//								// lines, it
//								// allows
//								// cb.lineTo to
//								// function.
//								// Draws nothing
//								// on its own.
//								cb.moveTo(q - noteFontSize / 2, i + j);
//								cb.lineTo(q - noteFontSize / 4, i + j
//										+ noteFontSize / 4);
//								cb.lineTo(q, i + j);
//								cb.lineTo(q - noteFontSize / 4, i + j
//										- noteFontSize / 4);
//								cb.lineTo(q - noteFontSize / 2, i + j);
//								cb.lineTo(q - noteFontSize / 4, i + j
//										+ noteFontSize / 4); // Draws a diamond,
//								// with an extra
//								// leg to
//								// eliminate the
//								// starting area
//								// having a mark

								if ((q - whiteSpace * 2 - noteFontSize)
										- lineStart > 0) // Do not draw lines
								// backwards, if
								// there isn't
								// enough space just
								// draw no line at
								// all. This should
								// be used
								// everywhere, but
								// is most prominent
								// here TO DO: Apply
								// this change
								// everywhere needed
								{
									cb.moveTo(lineStart, i + j);
									cb.lineTo(
											q - whiteSpace * 2 - noteFontSize,
											i + j);
								}
								lineStart = (int) (q + whiteSpace * 2);

								colPos++;
								if (colPos == barLength) {
									colPos = 0;
									barPos++;
									if (barPos < chars.size()) {
										if ((DataToArray.getLargestNumber(chars.get(barPos)) * givenSpacing) > (pageWidth
												- marginRight - q)) {
											noSpaceAvailable = true;
											if (rowPos != 5) {
												cb.moveTo(q + givenSpacing, i
														+ j);
												cb.lineTo(q + givenSpacing, i
														+ j - barSpacing);
											}
										}
									}
								}
								if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos == 5) {
										if (!lastBarred) {
											cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing * 5);// draw
											// the
											// very
											// last
											// bar
											// line
											lastBarred = true;
										}
										doneWriting = true; // If there are no
										// more bars to
										// write, stop
									}
								}
								if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos]; // Load
									// the
									// next
									// char
									// to
									// write
									barLength = chars.get(barPos)[rowPos].length;
								}
							} 
							else if (arrayChar == '%')
							{
								line.drawLine(cb, 0f, 0f, 0f);
								cb.setLineWidth(doubleBarLineWidth);
								cb.moveTo(q - givenSpacing, i + j - (doubleBarLinePreview - 1));
								cb.lineTo(q - givenSpacing, i + j - barSpacing + (doubleBarLinePreview - singleBarLineWidth));
								cb.stroke();
								cb.setLineWidth(singleBarLineWidth);
								
								q -= givenSpacing/2;
								
								colPos++;
								if (colPos == barLength) {
									colPos = 0;
									barPos++;
									if (barPos < chars.size()) {
										if ((DataToArray.getLargestNumber(chars.get(barPos)) * givenSpacing) > (pageWidth
												- marginRight - q)) {
											noSpaceAvailable = true;
										}
									}

								}
								if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos == 5) {
										if (!lastBarred) {
											cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing
													* 5);// draw the very
											// last bar line
											lastBarred = true;
										}
										doneWriting = true; // If there are
										// no more bars
										// to write,
										// stop
									}
								}
								if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos]; // Load
									// the
									// next
									// char
									// to
									// write
									barLength = chars.get(barPos)[rowPos].length;
								}
							}
							else // Otherwise it's a normal character, print
									// it out. Make sure to catch all special
									// characters before this section
							{
								char nextChar;
								if (colPos + 1 != barLength) // Get the next
								// character to
								// see if it is
								// a two digit
								// number, set
								// to blank if
								// the next char
								// does not
								// exist (no
								// space)
								{
									nextChar = chars.get(barPos)[rowPos][colPos + 1];
								} else {
									nextChar = '-';
								}
								if (IsDigit(nextChar)) // it is a two digit
								// number, print them
								// out together
								{
									currentChar = new Phrase(
											("" + arrayChar + nextChar),
											numberFont); // Replace this with
									// the character
									// from the array we
									// are currently
									// proccessing.
									column.setSimpleColumn(currentChar, q
											- noteFontSize / 2f - noteFontSize
											* (3f / 4f), i + j - noteFontSize
											* (1f / 4f), q + noteFontSize / 2f
											+ noteFontSize * (1f / 4f), i + j
											+ noteFontSize * (3f / 4f),
											noteFontSize, Element.ALIGN_LEFT); // Writes
									// the
									// character
									// curentChar
									column.go();

									line.drawLine(cb, 0f, 0f, 0f); // This is
									// used to
									// draw the
									// lines, it
									// allows
									// cb.lineTo
									// to
									// function.
									// Draws
									// nothing
									// on its
									// own.
									if ((q - whiteSpace * 2 - noteFontSize)
											- lineStart > 0) // Do not draw
									// lines
									// backwards, if
									// there isn't
									// enough space
									// just draw no
									// line at all.
									// This should
									// be used
									// everywhere,
									// but is most
									// prominent
									// here TO DO:
									// Apply this
									// change
									// everywhere
									// needed
									{
										cb.moveTo(lineStart, i + j);
										cb.lineTo(q - whiteSpace * 2
												- noteFontSize, i + j);
									}

									char[][] tempArray = chars.get(barPos); // The
									// next
									// character
									// has
									// already
									// been
									// written,
									// change
									// it
									// to
									// a
									// blank
									// so
									// we
									// don't
									// write
									// it
									// twice
									tempArray[rowPos][colPos + 1] = '-';
									chars.set(barPos, tempArray);
									lineStart = (int) (q + whiteSpace * 2);

									lastWriteX = (int) q;
									lastWriteY = (int) (i + j + noteFontSize); // Extra
									// noteFontSize
									// to
									// account
									// for
									// the
									// fact
									// that
									// it
									// is
									// a
									// double
									// digit,
									// might
									// be
									// too
									// big.
								} else // it is single digit, same method as
										// before
								{
									if (rowPos == 0) // Check if it's the top
									// row, this section
									// writes the
									// "Repeat for # times"
									{ 
										if (chars.get(barPos)[rowPos + 1][colPos] == '|' || chars.get(barPos)[rowPos + 1][colPos] == '&' || chars.get(barPos)[rowPos + 1][colPos] == '#') // There
										// was
										// a
										// number,
										// but
										// below
										// is
										// a
										// bar
										// line
										// so
										// this
										// is
										// a
										// repeat
										// symbol.
										// Print
										// a
										// repeat
										// line
										// above
										// the
										// lines,
										// and
										// draw
										// a
										// normal
										// bar
										// line
										// here
										{
											if (firstBarOfLine == 0
													|| firstBarOfLine == 1) // If
											// we
											// read
											// a
											// character
											// at
											// the
											// start
											// of
											// a
											// line,
											// it
											// should
											// be
											// drawn
											// at
											// the
											// top
											// right
											// of
											// the
											// last
											// bar
											// instead,
											// because
											// of
											// how
											// bars
											// are
											// partitioned
											{
												currentChar = new Phrase(
														("Repeat " + arrayChar + " times"),
														numberFont); // Replace
												// this
												// with
												// the
												// character
												// from
												// the
												// array
												// we
												// are
												// currently
												// proccessing.
												column.setSimpleColumn(currentChar, pageWidth - marginLeft- 12 * noteFontSize, i+ j+ noteFontSize+ groupBarSpacing, pageWidth - marginLeft + 0 *noteFontSize, i+ j+ noteFontSize* 2+ groupBarSpacing,noteFontSize, Element.ALIGN_LEFT); // Writes
												// the
												// character
												// curentChar
												column.go();
											} else {
												currentChar = new Phrase(
														("Repeat " + arrayChar + " times"),
														numberFont); // Replace
												// this
												// with
												// the
												// character
												// from
												// the
												// array
												// we
												// are
												// currently
												// proccessing.
												column.setSimpleColumn(
														currentChar, q - 8
																* noteFontSize,
														i + j + noteFontSize,
														q + 4*noteFontSize, i + j + noteFontSize
																* 2,
														noteFontSize,
														Element.ALIGN_LEFT); // Writes
												// the
												// character
												// curentChar
												column.go();
											}
											/*
											if (chars.get(barPos)[rowPos + 1][colPos] != '&')
											{
												line.drawLine(cb, 0f, 0f, 0f); // This is used to draw the lines, it allows cb.lineTo to function. Draws nothing on its own.
												cb.moveTo(q, i + j);
												cb.lineTo(q, i + j - barSpacing);
											}
											*/
											//else
												q -= givenSpacing;
											//line.drawLine(cb, 0f, 0f, 0f); // This is used to draw the lines, it allows cb.lineTo to function. Draws nothing on its own.
											//cb.moveTo(q, i + j);
											//cb.lineTo(q, i + j - barSpacing);
											cancelBarDraw = true; // Don't draw
											// a bar
											// line if
											// this is
											// at the
											// end of a
											// bar, we
											// have
											// already
											// done that
											// (It would
											// be extra)
										} else // repeat of below, the checks
												// must be done seperatly to
												// avoid index out of bounds
										{
											line.drawLine(cb, 0f, 0f, 0f); // This
											// is
											// used
											// to
											// draw
											// the
											// lines,
											// it
											// allows
											// cb.lineTo
											// to
											// function.
											// Draws
											// nothing
											// on
											// its
											// own.
											if ((q - whiteSpace - noteFontSize)
													- lineStart > 0) // Do not
											// draw
											// lines
											// backwards,
											// if
											// there
											// isn't
											// enough
											// space
											// just
											// draw
											// no
											// line
											// at
											// all.
											// This
											// should
											// be
											// used
											// everywhere,
											// but
											// is
											// most
											// prominent
											// here
											// TO
											// DO:
											// Apply
											// this
											// change
											// everywhere
											// needed
											{
												cb.moveTo(lineStart, i + j);
												cb.lineTo(q - whiteSpace
														- noteFontSize, i + j);
											}

											currentChar = new Phrase(
													("" + arrayChar),
													numberFont); // Replace this
											// with the
											// character
											// from the
											// array we
											// are
											// currently
											// proccessing.
											column.setSimpleColumn(currentChar,
													q - noteFontSize
															* (3f / 4f), i + j
															- noteFontSize
															* (1f / 4f), q
															+ noteFontSize
															* (1f / 4f), i + j
															+ noteFontSize
															* (3f / 4f),
													noteFontSize,
													Element.ALIGN_LEFT); // Writes
											// the
											// character
											// curentChar
											column.go();
											lineStart = (int) (q + whiteSpace);

											lastWriteX = (int) q;
											lastWriteY = (int) (i + j);
										}
									} else // not the top row, print numbers
											// normally
									{
										line.drawLine(cb, 0f, 0f, 0f); // This
										// is
										// used
										// to
										// draw
										// the
										// lines,
										// it
										// allows
										// cb.lineTo
										// to
										// function.
										// Draws
										// nothing
										// on
										// its
										// own.
										if ((q - whiteSpace - noteFontSize)
												- lineStart > 0) // Do not draw
										// lines
										// backwards,
										// if there
										// isn't
										// enough
										// space
										// just draw
										// no line
										// at all.
										// This
										// should be
										// used
										// everywhere,
										// but is
										// most
										// prominent
										// here TO
										// DO: Apply
										// this
										// change
										// everywhere
										// needed
										{
											cb.moveTo(lineStart, i + j);
											cb.lineTo(q - whiteSpace
													- noteFontSize, i + j);
										}

										currentChar = new Phrase(
												("" + arrayChar), numberFont); // Replace
										// this
										// with
										// the
										// character
										// from
										// the
										// array
										// we
										// are
										// currently
										// proccessing.
										column.setSimpleColumn(currentChar, q
												- noteFontSize * (3f / 4f), i
												+ j - noteFontSize * (1f / 4f),
												q + noteFontSize * (1f / 4f), i
														+ j + noteFontSize
														* (3f / 4f),
												noteFontSize,
												Element.ALIGN_LEFT); // Writes
										// the
										// character
										// curentChar
										column.go();
										lineStart = (int) (q + whiteSpace);

										lastWriteX = (int) q;
										lastWriteY = (int) (i + j);
									}
								}

								colPos++; // TODO change this increment to a
								// method
								if (colPos == barLength) {
									colPos = 0;
									barPos++;
									if (barPos < chars.size()) {
										if ((DataToArray.getLargestNumber(chars.get(barPos)) * givenSpacing) > (pageWidth
												- marginRight - q)) {
											noSpaceAvailable = true;
											if (rowPos != 5 && !cancelBarDraw) {
												cb.moveTo(q + givenSpacing, i
														+ j);
												cb.lineTo(q + givenSpacing, i
														+ j - barSpacing);
											}
										}
									}
								}
								if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos >= 5) {
										if (!lastBarred) {
											cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing * 5);// draw
											// the
											// very
											// last
											// bar
											// line
											lastBarred = true;
										}
										doneWriting = true; // If there are no
										// more bars to
										// write, stop
									}
								}
								if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos]; // Load
									// the
									// next
									// char
									// to
									// write
									barLength = chars.get(barPos)[rowPos].length;
								}
							}
						}
						if ((q + givenSpacing * 2 >= pageWidth
								- (int) marginLeft - givenSpacing)) // If the
						// next
						// character
						// doesn't
						// fit, and
						// we are at
						// the end
						// and need
						// to draw a
						// line to
						// the end
						{
							line.drawLine(cb, 0f, 0f, 0f); // This is used to
							// draw the lines,
							// it allows
							// cb.lineTo to
							// function. Draws
							// nothing on its
							// own.
							cb.moveTo(lineStart, i + j);
							cb.lineTo(pageWidth, i + j);
						}
						firstBarOfLine += 1;
					}
					firstBarOfLine = 0;
					rowSave[rowPos][0] = barPos; // Keeps track of what bar we
					// are at on this line
					rowSave[rowPos][1] = colPos; // Start of new bar, so we are
					// at column 0
					rowSave[rowPos][2] = barLength; // Keep track of how long
					// this section is, to avoid
					// index errors
					rowSave[rowPos][3] = lastWriteX; // Keeps track of the last
					// element's X position
					// written on this line,
					// so we can draw a
					// phrase arc to it if
					// needed later
					rowSave[rowPos][4] = lastWriteY; // Keeps track of the last
					// element's Y position
					rowPos++;

				}
				if (j - groupBarSpacing < 0 + marginBottom && !doneWriting) {
					j = pageHeight - (int) marginTop;
					document.newPage();
				}
			}
		}
		document.close();

	}

	private static boolean IsDigit(char nextChar) {
		return (nextChar == '0' || nextChar == '1' || nextChar == '2'
				|| nextChar == '3' || nextChar == '4' || nextChar == '5'
				|| nextChar == '6' || nextChar == '7' || nextChar == '8' || nextChar == '9');
	}

	public static boolean SetGroupBarSpacing(int newSpacing) {
		if (newSpacing < 0) {
			return false; // Could not change spacing
		} else {
			groupBarSpacing = newSpacing;
			return true;
		}
	}

	public static boolean SetWhiteSpace(float newSpacing) {
		if (newSpacing < 0) {
			return false; // Could not change spacing
		} else {
			whiteSpace = newSpacing;
			return true;
		}
	}

	public static boolean SetBarSpacing(int newSpacing) {
		if (newSpacing < 0) {
			return false; // Could not change spacing
		} else {
			barSpacing = newSpacing;
			return true;
		}
	}

	public static boolean SetGivenSpacing(float newSpacing) {
		if (newSpacing < 0) {
			return false; // Could not change spacing
		} else {
			givenSpacing = newSpacing;
			return true;
		}
	}

	public static boolean SetNoteFontSize(float newSpacing) {
		if (newSpacing < 0) {
			return false; // Could not change spacing
		} else {
			noteFontSize = newSpacing;
			numberFont.setSize(newSpacing);
			return true;
		}
	}

	public static void setTitle(String s) {
		TITLE_STRING = s;
	}

	public static void setSubTitle(String s) {
		COMPOSER_STRING = s;
	}

	public static String getDestination() {
		return destination1;
	}

/*	public static void createCircle(PdfContentByte canvas, float x, float y,
			float r) {
		float b = 0.5523f;
		canvas.moveTo(x + r, y);
		canvas.curveTo(x + r, y - r * b, x + r * b, y - r, x, y - r);
		canvas.curveTo(x - r * b, y - r, x - r, y - r * b, x - r, y);
		canvas.curveTo(x - r, y + r * b, x - r * b, y + r, x, y + r);
		canvas.curveTo(x + r * b, y + r, x + r, y + r * b, x + r, y);
		canvas.closePathFillStroke();
		
	}*/
	
	private static void createCircle(float currX, float currY, PdfContentByte draw) {
		currX += 1.7f;
		draw.circle(currX + (givenSpacing - 3f) / 2, currY, 1.5f);
		draw.setColorFill(BaseColor.BLACK);
		draw.fillStroke();
	}
	
	private static void createDiamond(float currX, float currY, PdfContentByte draw) {
		currY += 1.7f;
		currX = currX + 2.4f;
		draw.moveTo(currX + 0.175f, currY + 0.175f);
		draw.lineTo(currX - 1.93f, currY - 1.93f);
		draw.stroke();
		
		draw.moveTo(currX, currY);
		draw.lineTo(currX + 1.93f, currY - 1.93f);
		draw.stroke();
		
		currY = currY - (3.5f);
		draw.moveTo(currX - 0.175f, currY - 0.175f);
		draw.lineTo(currX + 1.93f, currY + 1.93f);
		draw.stroke();
		
		draw.moveTo(currX, currY);
		draw.lineTo(currX - 1.93f, currY + 1.93f);
		draw.stroke();
		currY -= 1.7f;
	}
	
	private static Boolean InLeftSide(char[][] array, int col)
	{
		int i = array[0].length/2;
		if(col<=i)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}