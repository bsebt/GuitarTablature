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

	public static DataToArray a = new DataToArray();
	public static String TITLE_STRING = GUI.getTitle1();
	public static String COMPOSER_STRING = GUI.getsubTitle1();
	private static final float marginLeft = 50.0f; // Note original margins are 36.0f for letter size
	private static final float marginRight = 50.0f;
	private static final float marginTop = 0.0f;
	private static final float marginBottom = 0.0f;
	public static Font titleFont = new Font(FontFamily.HELVETICA, 30);
	public static Font composerFont = new Font(FontFamily.HELVETICA, 14);
	public static Font numberFont = new Font(FontFamily.HELVETICA, 9);
	public static Paragraph title;
	public static Paragraph composer;		
	private static Phrase currentChar;
	private static float noteFontSize = 9; // Size of the characters to be written to the page
	private static float givenSpacing = (float) a.getSpacing(); // The spacing given at the start of the program, change to variable once we read it in
	private static int barSpacing = 7; // Space between individual lines to be drawn
	private static float whiteSpace = 1.0f; // Space around a written number that does not have a bar line
	private static int groupBarSpacing = 75; // Spaces between the groups of 6 lines
	private static float topVoidSpace = 160; // Space at the top of the page for info.
	private static float pageHeight = 800;
	private static float pageWidth = 620;
	//Note, standard page is 620 units wide and 800 units tall.

	private static ArrayList<char[][]> chars; // All chars from the text file
	
	private static float singleBarLineWidth = 1;
	private static LineSeparator line = new LineSeparator();
	public static String destination1;
	public static float doubleBarLineWidth = 2;
	public static float doubleBarLinePreview = 2;

	public static void convertPDF(File[] textFile, String Destination) throws DocumentException, IOException// Convert() throws
	{
		a = new DataToArray(textFile);
		chars = a.getChars(); // Gets the array of information
		if (!(GUI.getTitle1().equals("NO TITLE"))  && !(GUI.getsubTitle1().equals("NO SUBTITLE"))) {
			TITLE_STRING = GUI.getTitle1();
			COMPOSER_STRING = GUI.getsubTitle1();
		}
		else {
			TITLE_STRING = a.getTitle();
			COMPOSER_STRING = a.getsubTitle();
		}
		titleFont = FontFactory.getFont(GUI.getFont1(), 30);
		composerFont = FontFactory.getFont(GUI.getFont1(), 14);
		numberFont = FontFactory.getFont(GUI.getFont1(), GUI.getnotefont());
		title = new Paragraph(TITLE_STRING, titleFont);
		composer = new Paragraph(COMPOSER_STRING, composerFont);
		givenSpacing = GUI.getgivenspacing();
		destination1 = Destination;
		whiteSpace = GUI.getWhiteSpacing();
		barSpacing = GUI.getbarspacing();
		SetNoteFontSize(GUI.getnotefont());
		groupBarSpacing = GUI.getgroupbarspacing();
		doubleBarLinePreview = GUI.getDBLP();
		Document document = new Document(PageSize.LETTER, marginLeft, marginRight, marginTop, marginBottom);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(Destination));
		
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

		if (chars.isEmpty() != true) {
			ColumnText column = new ColumnText(cb); // text bound left and right on a series of lines
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
					{ 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } }; // Used to store our place when we change between lines, first  number is the bar  we are on, second number is the column.
			char arrayChar = chars.get(barPos)[rowPos][colPos]; // Gets first character of the bar = |
			int barLength = chars.get(barPos)[rowPos].length; // Gets size of bar, so I can check to see if we are at the end of the array and get the next bar.

			for (float j = pageHeight - (float) topVoidSpace; j > 0 + marginBottom && !doneWriting; j -= groupBarSpacing) // Groups of bars, 100 is void space at the top for title
			{
				rowPos = 0; // Reset the row we are on (start at the top, reset every 6)
				for (float i = barSpacing * 6; i > 0; i -= barSpacing) // Individual horizontal bars
				{
					barPos = rowSave[rowPos][0]; // Pull up what bar we have written to on this line
					colPos = rowSave[rowPos][1]; // Pull up how many columns we have written on this line
					barLength = rowSave[rowPos][2]; // pull up the bar length, in case we changed bars at the very end of the last line
					lastWriteX = rowSave[rowPos][3]; // Pull up the last write co-ords for this line of music
					lastWriteY = rowSave[rowPos][4];
					if (barPos < chars.size())
					{
						arrayChar = chars.get(barPos)[rowPos][colPos];
					}

					boolean noSpaceAvailable = false; // Used to check if there	is enough space to write the next character
					lineStart = 0;
					for (float q = (float) (marginLeft + givenSpacing); (q < pageWidth - marginRight - givenSpacing); q += givenSpacing) // Individual characters
					{
						boolean cancelBarDraw = false;
						EOB = false; // used to check if there is a next bar to  pull from, avoids index errors
						if (q + givenSpacing < pageWidth - (int) marginLeft - givenSpacing) // Don't print a character on the far right side to make room for the bar line
						{
							while (arrayChar == ' ' || arrayChar == '&')
							{
								colPos++;
								if (colPos == barLength)
								{
									colPos = 0;
									barPos++;
									if (barPos < chars.size()) 
									{
										if (((DataToArray.getLargestNumber(chars.get(barPos))+1) * givenSpacing) > (pageWidth - marginRight - q)) 
										{
											noSpaceAvailable = true;
											if (rowPos != 5) 
											{
											}
										}
									}
								}
								if (barPos >= chars.size()) 
								{
									EOB = true;
									if (rowPos == 5) 
									{
										if (!lastBarred) 
										{
											lastBarred = true;
										}
										doneWriting = true;
										}
								}
								if (!EOB) 
								{
									arrayChar = chars.get(barPos)[rowPos][colPos];
									barLength = chars.get(barPos)[rowPos].length;
								}
							}

							if (noSpaceAvailable) 
							{
								// Do nothing, we are waiting until the end of the this line
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
								colPos++;
								if (colPos == barLength)
								{
									colPos = 0;
									barPos++;
									if (barPos < chars.size()) 
									{
										if (((DataToArray.getLargestNumber(chars.get(barPos))+1) * givenSpacing) > (pageWidth - marginRight - q)) 
										{
											noSpaceAvailable = true;
											if (rowPos != 5) 
											{
												//cb.moveTo(q + givenSpacing, i + j);
												//cb.lineTo(q + givenSpacing, i + j - barSpacing);
											}
										}
									}
								}
								if (barPos >= chars.size()) 
								{
									EOB = true;
									if (rowPos == 5) 
									{
										if (!lastBarred) 
										{
											//cb.moveTo(q, i + j);
											//cb.lineTo(q, i + j + barSpacing * 5);// draw the very
											lastBarred = true; //last bar line
										}
										doneWriting = true;
										}
								}
								if (!EOB) 
								{
									arrayChar = chars.get(barPos)[rowPos][colPos];
									barLength = chars.get(barPos)[rowPos].length;
								}
							}
							else if (arrayChar == '-' || arrayChar == '<') 
							{
								colPos++;
								if (colPos == barLength) {
									colPos = 0;
									barPos++;
									if (barPos < chars.size()) {
										if ((DataToArray.getLargestNumber(chars.get(barPos)) * givenSpacing) > (pageWidth - marginRight - q)) {
											noSpaceAvailable = true;
											if (rowPos != 5) {
												cb.moveTo(q + givenSpacing, i + j);
												cb.lineTo(q + givenSpacing, i + j - barSpacing);
											}
										}
									}
								}
								if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos == 5) {
										if (!lastBarred) {
											cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing * 5);// draw the very last bar line
											lastBarred = true;
										}
										doneWriting = true; // If there are no more bars to write, stop
									}
								}
								if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos]; // Load next char to write
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
										if ((DataToArray.getLargestNumber(chars.get(barPos)) * givenSpacing) > (pageWidth - marginRight - q)) {
											noSpaceAvailable = true;
											if (rowPos != 5) {
												cb.moveTo(q + givenSpacing, i + j);
												cb.lineTo(q + givenSpacing, i + j - barSpacing);
											}
										}
									}
								}
								if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos == 5) {
										if (!lastBarred) {
											cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing * 5); //draw the very last bar line
											lastBarred = true;
										}
										doneWriting = true; // If there are no more bars to write, stop
									}
								}
								if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos]; // Load the next char to write
									barLength = chars.get(barPos)[rowPos].length;
								}
							} else if (arrayChar == 's') // Draw a slash before an s
							{
								line.drawLine(cb, 0f, 0f, 0f);
								cb.moveTo(q - noteFontSize, i + j - noteFontSize / 3);
								cb.lineTo(q, i + j + noteFontSize / 3);
								colPos++;
								if (colPos == barLength) {
									colPos = 0;
									barPos++;
									if (barPos < chars.size()) {
										if ((DataToArray.getLargestNumber(chars.get(barPos)) * givenSpacing) > (pageWidth - marginRight - q)) {
											noSpaceAvailable = true;
											if (rowPos != 5) {
												cb.moveTo(q + givenSpacing, i + j);
												cb.lineTo(q + givenSpacing, i + j - barSpacing);
											}
										}
									}
								}
								if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos == 5) {
										if (!lastBarred) {
											cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing * 5); //draw the very last bar line
											lastBarred = true;
										}
										doneWriting = true; // If there are no more bars to write, stop
									}
								}
								if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos]; // Load next char to write
									barLength = chars.get(barPos)[rowPos].length;
								}
							} 
							else if (arrayChar == 'p') // Draw a phrasing line between this and the next one
							{
								if (i + j == lastWriteY) // if the 2 characters are on the same line
								{
									cb.moveTo(q, i + j + noteFontSize/2); // We draw the curve backwards
									cb.curveTo((q + lastWriteX) / 2, i + j + (noteFontSize), lastWriteX, lastWriteY + noteFontSize/2);
								} 
								else // The characters are on seperate lines
								{
									cb.moveTo(q, i + j + noteFontSize/2); // We are drawing the curve backwards
									cb.curveTo((q + marginLeft) / 2, i + j + (noteFontSize * 2), marginLeft, i + j + noteFontSize/2);
									cb.moveTo(pageWidth - marginRight, lastWriteY + noteFontSize/2);
									cb.curveTo((lastWriteX + (pageWidth - marginRight)) / 2, lastWriteY + (noteFontSize * 2), lastWriteX, lastWriteY + noteFontSize/2);
								}
								colPos++;
								if (colPos == barLength) {
									colPos = 0;
									barPos++;
									if (barPos < chars.size()) {
										if ((DataToArray.getLargestNumber(chars.get(barPos)) * givenSpacing) > (pageWidth - marginRight - q)) {
											noSpaceAvailable = true;
											if (rowPos != 5) {
												cb.moveTo(q + givenSpacing, i + j);
												cb.lineTo(q + givenSpacing, i + j - barSpacing);
											}
										}
									}
								}
								if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos == 5) {
										if (!lastBarred) {
											cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing * 5); // draw very last bar line
											lastBarred = true;
										}
										doneWriting = true; 
									}
								}
								if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos]; // Load next char to write
									barLength = chars.get(barPos)[rowPos].length;
								}
							} 
							else if (arrayChar == 'h') // Draw a phrasing line between this and the next one
							{
								if (i + j == lastWriteY) // if the 2 characters are on the same line
								{
									cb.moveTo(q, i + j + noteFontSize/2); // We draw the curve backwards
									cb.curveTo((q + lastWriteX) / 2, i + j + (noteFontSize), lastWriteX, lastWriteY + noteFontSize/2);
									currentChar = new Phrase(("h"), numberFont);
									column.setSimpleColumn(currentChar, (q)-((noteFontSize*3)/4),  i + j + (noteFontSize*1), (q)+(noteFontSize/4),  i + j + (noteFontSize*2),noteFontSize, Element.ALIGN_LEFT);
									column.go();
								} 
								else // The characters are on seperate lines
								{
									cb.moveTo(q, i + j + noteFontSize/2); // We are drawing the curve backwards
									cb.curveTo((q + marginLeft) / 2, i + j + (noteFontSize * 2), marginLeft, i + j + noteFontSize/2);
									cb.moveTo(pageWidth - marginRight, lastWriteY + noteFontSize/2);
									cb.curveTo((lastWriteX + (pageWidth - marginRight)) / 2, lastWriteY + (noteFontSize * 2), lastWriteX, lastWriteY + noteFontSize/2);
									
									currentChar = new Phrase(("h"), numberFont);
									column.setSimpleColumn(currentChar, (q)-((noteFontSize*3)/4),  i + j + (noteFontSize*2), (q)+(noteFontSize/4),  i + j + (noteFontSize/2 * 3),noteFontSize/2, Element.ALIGN_LEFT);
									column.go();
								}
								colPos++;
								if (colPos == barLength) {
									colPos = 0;
									barPos++;
									if (barPos < chars.size()) {
										if ((DataToArray.getLargestNumber(chars.get(barPos)) * givenSpacing) > (pageWidth - marginRight - q)) {
											noSpaceAvailable = true;
											if (rowPos != 5) {
//												cb.moveTo(q + givenSpacing, i + j);
												cb.lineTo(q + givenSpacing, i + j - barSpacing);
											}
										}
									}
								}
								if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos == 5) {
										if (!lastBarred) {
											cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing * 5); // draw very last bar line
											lastBarred = true;
										}
										doneWriting = true; 
									}
								}
								if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos]; // Load next char to write
									barLength = chars.get(barPos)[rowPos].length;
								}
							} 
							else if (arrayChar == '>') // Draw a diamond after a > symbol
							{
								createDiamond(q - givenSpacing, i + j, cb);
								if ((q - whiteSpace * 2 - noteFontSize)- lineStart > 0) //Do not draw backwards if there is no line at all
								{
									cb.moveTo(lineStart, i + j);
									cb.lineTo(q - whiteSpace * 2 - noteFontSize, i + j);
								}
								lineStart = (int) (q + whiteSpace * 2);
								colPos++;
								if (colPos == barLength) {
									colPos = 0;
									barPos++;
									if (barPos < chars.size()) {
										if ((DataToArray.getLargestNumber(chars.get(barPos)) * givenSpacing) > (pageWidth - marginRight - q)) {
											noSpaceAvailable = true;
											if (rowPos != 5) {
												cb.moveTo(q + givenSpacing, i + j);
												cb.lineTo(q + givenSpacing, i + j - barSpacing);
											}
										}
									}
								}
								if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos == 5) {
										if (!lastBarred) {
											cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing * 5);
											lastBarred = true;
										}
										doneWriting = true;
									}
								}
								if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos];
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
										if ((DataToArray.getLargestNumber(chars.get(barPos)) * givenSpacing) > (pageWidth - marginRight - q)) {
											noSpaceAvailable = true;
										}
									}

								}
								if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos == 5) {
										if (!lastBarred) {
											cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing	* 5);
											lastBarred = true;
										}
										doneWriting = true;
									}
								}
								if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos];
									barLength = chars.get(barPos)[rowPos].length;
								}
							}
							else // Otherwise it's a normal character
							{
								char nextChar;
								if (colPos + 1 != barLength) // Get the next character to see if it is a 2 digit #
								{
									nextChar = chars.get(barPos)[rowPos][colPos + 1];
								} 
								else {
									nextChar = '-';
								}
								if (IsDigit(nextChar)) // it is a two digit character, print out together
								{
									currentChar = new Phrase(("" + arrayChar + nextChar), numberFont); // Replace with character we are currently processing
									column.setSimpleColumn(currentChar, q - noteFontSize / 2f - noteFontSize * (3f / 4f), i + j - noteFontSize * (1f / 4f), q + noteFontSize / 2f + noteFontSize * (1f / 4f), i + j + noteFontSize * (3f / 4f), noteFontSize, Element.ALIGN_LEFT);
									column.go();

									line.drawLine(cb, 0f, 0f, 0f); 
									if ((q - whiteSpace * 2 - noteFontSize)- lineStart > 0) //Do not draw lines backwards
									{
										cb.moveTo(lineStart, i + j);
										cb.lineTo(q - whiteSpace * 2 - noteFontSize, i + j);
									}

									char[][] tempArray = chars.get(barPos); // The next character has already been written
									tempArray[rowPos][colPos + 1] = '-';
									chars.set(barPos, tempArray);
									lineStart = (int) (q + whiteSpace * 2);
									lastWriteX = (int) q;
									lastWriteY = (int) (i + j + noteFontSize); // Extra noteFontSize to account for the double digit
								} 
								else // it is single digit
								{
									if (rowPos == 0) // Check if it's the top row, this section writes repeat for # times
									{ 
										// There was a #, but below is a bar line so this is a repeat symbo.
										if (chars.get(barPos)[rowPos + 1][colPos] == '|' || chars.get(barPos)[rowPos + 1][colPos] == '&' || chars.get(barPos)[rowPos + 1][colPos] == '#')
										{
											//If we read a character at the start of a line, it should be drawn at the top right of the bar instead
											if (firstBarOfLine == 0 || firstBarOfLine == 1) // If
											{
												//currentChar is the character from the array we are currently processing
												currentChar = new Phrase(("Repeat " + arrayChar + " times"), numberFont);
												column.setSimpleColumn(currentChar, pageWidth - marginLeft- 12 * noteFontSize, i+ j+ noteFontSize+ groupBarSpacing, pageWidth - marginLeft + 0 *noteFontSize, i+ j+ noteFontSize* 2+ groupBarSpacing,noteFontSize, Element.ALIGN_LEFT);
												column.go();
											} 
											else {
												currentChar = new Phrase(("Repeat " + arrayChar + " times"), numberFont); // Replace with character we are currently processing
												column.setSimpleColumn(currentChar, q - 8 * noteFontSize, i + j + noteFontSize, q + 4*noteFontSize, i + j + noteFontSize * 2, noteFontSize, Element.ALIGN_LEFT); // Writes the character, currentChar
												column.go();
											}
											q -= givenSpacing;
											cancelBarDraw = true; // Don't draw a bar if we already did
										} 
										else // repeat of below
										{
											line.drawLine(cb, 0f, 0f, 0f); 
											if ((q - whiteSpace - noteFontSize) - lineStart > 0)
											{
												cb.moveTo(lineStart, i + j);
												cb.lineTo(q - whiteSpace - noteFontSize, i + j);
											}
											currentChar = new Phrase(("" + arrayChar), numberFont);
											column.setSimpleColumn(currentChar, q - noteFontSize * (3f / 4f), i + j - noteFontSize * (1f / 4f), q + noteFontSize * (1f / 4f), i + j + noteFontSize * (3f / 4f), noteFontSize, Element.ALIGN_LEFT); 
											column.go();
											lineStart = (int) (q + whiteSpace);
											lastWriteX = (int) q;
											lastWriteY = (int) (i + j);
										}
									} 
									else // not the top row, print numbers
									{
										line.drawLine(cb, 0f, 0f, 0f);
										if ((q - whiteSpace - noteFontSize) - lineStart > 0)
										{
											cb.moveTo(lineStart, i + j);
											cb.lineTo(q - whiteSpace - noteFontSize, i + j);
										}
										currentChar = new Phrase(("" + arrayChar), numberFont); 
											column.setSimpleColumn(currentChar, q - noteFontSize * (3f / 4f), i + j - noteFontSize * (1f / 4f), q + noteFontSize * (1f / 4f), i + j + noteFontSize * (3f / 4f), noteFontSize, Element.ALIGN_LEFT);
											column.go();
											lineStart = (int) (q + whiteSpace);
											lastWriteX = (int) q;
											lastWriteY = (int) (i + j);
									}
								}
								colPos++;
								if (colPos == barLength) {
									colPos = 0;
									barPos++;
									if (barPos < chars.size()) {
										if ((DataToArray.getLargestNumber(chars.get(barPos)) * givenSpacing) > (pageWidth - marginRight - q)) {
											noSpaceAvailable = true;
											if (rowPos != 5 && !cancelBarDraw) {
												cb.moveTo(q + givenSpacing, i + j);
												cb.lineTo(q + givenSpacing, i + j - barSpacing);
											}
										}
									}
								}
								if (barPos >= chars.size()) {
									EOB = true;
									if (rowPos >= 5) {
										if (!lastBarred) {
											cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing * 5);
											lastBarred = true;
										}
										doneWriting = true; 
									}
								}
								if (!EOB) {
									arrayChar = chars.get(barPos)[rowPos][colPos]; //load next char to write
									barLength = chars.get(barPos)[rowPos].length;
								}
							}
						}
						if ((q + givenSpacing * 2 >= pageWidth - (int) marginLeft - givenSpacing)) // If the next character does not fit and we are at the end
						{
							line.drawLine(cb, 0f, 0f, 0f); 
							cb.moveTo(lineStart, i + j);
							cb.lineTo(pageWidth, i + j);
						}
						firstBarOfLine += 1;
					}
					firstBarOfLine = 0;
					rowSave[rowPos][0] = barPos; // Keeps track of what bar we are at on this line
					rowSave[rowPos][1] = colPos; // Start of new bar, so we are at column 0
					rowSave[rowPos][2] = barLength; // Keep track of how long this section is, to avoid index errors
					rowSave[rowPos][3] = lastWriteX; // Keeps track of the last element's X position written on this line
					rowSave[rowPos][4] = lastWriteY; // Keeps track of the last element's Y position
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
		} 
		else {
			groupBarSpacing = newSpacing;
			return true;
		}
	}

	public static boolean SetWhiteSpace(float newSpacing) {
		if (newSpacing < 0) {
			return false; // Could not change spacing
		} 
		else {
			whiteSpace = newSpacing;
			return true;
		}
	}

	public static boolean SetBarSpacing(int newSpacing) {
		if (newSpacing < 0) {
			return false; // Could not change spacing
		} 
		else {
			barSpacing = newSpacing;
			return true;
		}
	}

	public static boolean SetGivenSpacing(float newSpacing) {
		if (newSpacing < 0) {
			return false; // Could not change spacing
		} 
		else {
			givenSpacing = newSpacing;
			return true;
		}
	}

	public static boolean SetNoteFontSize(float newSpacing) {
		if (newSpacing < 0) {
			return false; // Could not change spacing
		} 
		else {
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