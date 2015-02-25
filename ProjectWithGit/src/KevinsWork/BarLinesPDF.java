package KevinsWork;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;


public class BarLinesPDF 
{
	//public static String DEST;	//Destination, this should be changed according to the GUI
	
	public static String TITLE_STRING = DataToArray.getTitle();
	public static String COMPOSER_STRING = DataToArray.getsubTitle();
	
	private static final float marginLeft = 50.0f; //Note original margins are 36.0f for letter size
	private static final float marginRight = 50.0f;
	private static final float marginTop = 0.0f;
	private static final float marginBottom = 0.0f;
	private static final int ROWS = 6;
	
	private static Font titleFont = new Font(FontFamily.HELVETICA, 30);
	private static Font composerFont = new Font(FontFamily.HELVETICA, 14);
	private static Font numberFont = new Font(FontFamily.HELVETICA, 9);
	private static Paragraph title = new Paragraph(TITLE_STRING, titleFont);
	private static Paragraph composer = new Paragraph (COMPOSER_STRING, composerFont);
	
	private static Phrase currentChar;
	private static int noteFontSize = 6; //Size of the characters to be written to the page
	private static int givenSpacing = (int)DataToArray.getSpacing(); //The spacing given at the start of the program, change to variable once we read it in
	private static int barSpacing = 7; //Space between individual lines to be drawn
	private static int whiteSpace = 1; //Space around a written number that does not have a bar line
	private static int groupBarSpacing = 75; //Spaces between the groups of 6 lines
	private static int topVoidSpace = 160; //Space at the top of the page for info. 
	private static int pageHeight = 800;
	private static int pageWidth = 620;
	//Note, standard page is 620 units wide and 800 units tall.
	
	//Data from DataToArray:
	
	//chars.get(i)[j].length ---> Gets the exact column number from bar i and row j.
	private static ArrayList<char[][]> chars;	// All chars from the text file: you obtain by .get(bar)[row][col]
	private static int maxCol;					// The maximum # of the columns in the array.
	private static int totalRows;
	private static int bars;
	
	private static LineSeparator line = new LineSeparator();
	private static DataToArray data;

	public static void convertPDF(String textFile, String Destination) throws DocumentException, IOException//Convert() throws DocumentException, IOException
	{	
		
		chars = DataToArray.textToArray(textFile); // Gets the array of information
		maxCol = DataToArray.getMaxColumnAmount();
		totalRows = DataToArray.getTotalRowAmount();
		TITLE_STRING = DataToArray.getTitle();
		COMPOSER_STRING = DataToArray.getsubTitle();
		title = new Paragraph(TITLE_STRING, titleFont);
		composer = new Paragraph (COMPOSER_STRING, composerFont);
		givenSpacing = (int)GUI.getgivenspacing();
		
		Document document = new Document(PageSize.LETTER, marginLeft, marginRight, marginTop, marginBottom);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(Destination));
		//Header/Meta Data:
		document.addCreator("Kevin");
		document.addAuthor("Kevin Arindaeng");
		
		//Changing some object features: (alignment for text)
		title.setAlignment(Element.ALIGN_CENTER);
		composer.setAlignment(Element.ALIGN_CENTER);
		line.setAlignment(Element.ALIGN_MIDDLE);

		//Creating the document:
		document.open();
		PdfContentByte cb = writer.getDirectContent(); //writer to draw lines
		document.add(title);
		document.add(composer);
		
		//document.newPage(); TODO add pages as needed, add a new loop
		if(chars.isEmpty() != true)
		{
			ColumnText column = new ColumnText(cb); //text bound left and right on a series of lines
			int lineStart = 0;
			int rowPos = 0;
			int colPos = 0;
			int barPos = 0;
			
			int lastWriteX = 0;
			int lastWriteY = 0;
			boolean EOB = false;
			boolean doneWriting = false;
			boolean lastBarred = false;
			if(chars.size() >= 1) //As long as there is something in the file, trim the end to avoid extra repeating | symbols for the full line.
			{
				char[][] tempCharArray = chars.get(chars.size() - 1); //Get the last bar
				for (int q = 0; q <= 5; q++)
				{
					tempCharArray[q][tempCharArray[0].length-1] = '-'; //Set the character to '-' for the last column of each line
				}
				chars.set(chars.size()-1, tempCharArray); //Reset the last bar to the new one we have changed.
			}
			int rowSave[][] = {{0,0,chars.get(barPos)[0].length,0,0},{0,0,chars.get(barPos)[1].length,0,0},{0,0,chars.get(barPos)[2].length,0,0},{0,0,chars.get(barPos)[3].length,0,0},{0,0,chars.get(barPos)[4].length,0,0},{0,0,chars.get(barPos)[5].length,0,0},{0,0,0,0,0},{0,0,0,0,0}}; //Used to store our place when we change between lines, first number is the bar we are on, second number is the column.
				
			char arrayChar = chars.get(barPos)[rowPos][colPos]; //Gets first character of the bar = |
			int barLength = chars.get(barPos)[rowPos].length; //Gets size of bar, so I can check to see if we are at the end of the array and get the next bar. TODO write a method to check to see if there is enough space automatically
			
			for (int j = pageHeight - (int)topVoidSpace; j > 0 + marginBottom && !doneWriting; j -= groupBarSpacing) //Groups of bars, 100 is void space at the top for title
			{			
				rowPos = 0; //Reset the row we are on (start at the top, reset every 6)
				for(int i = barSpacing*6; i > 0; i -= barSpacing) //Individual horizontal bars
				{
					barPos = rowSave[rowPos][0]; //Pull up what bar we have written to on this line
					colPos = rowSave[rowPos][1]; //Pull up how many columns we have written on this line
					barLength = rowSave[rowPos][2]; //pull up the bar length, in case we changed bars at the very end of the last line
					lastWriteX = rowSave[rowPos][3]; //Pull up the last write co-ords for this line of music
					lastWriteY = rowSave[rowPos][4];
					
					
					arrayChar = chars.get(barPos)[rowPos][colPos]; 
					
					boolean noSpaceAvailable = false; //Used to check if there is enough space to write the next character
					lineStart = 0;
					//int characterSpace = HowManyCharacters(barPos) * givenSpacing;
					for(int q = (int)marginLeft + givenSpacing; q < pageWidth - marginRight - givenSpacing; q += givenSpacing) //Individual characters
					{
						boolean cancelBarDraw = false;
						EOB = false; //used to check if there is a next bar to pull from, avoids index errors
						if (q + givenSpacing < pageWidth - (int)marginLeft - givenSpacing) //Don't print a character on the far right side to make room for the bar line
						{
							//System.out.println("Column: " + colPos + " Row: " + rowPos + " Bar: " + barPos);
							while (arrayChar == ' ') //TODO do this check when we read in data, empty space is normaly a mistake or junk DataToArray. This is a quick fix
							{
								colPos++;
								if (colPos == barLength)
						        {
						        	colPos = 0;
						        	barPos++;
						        }
								if (barPos >= chars.size())
					        	{
									EOB = true;
					        		if (rowPos == 5)
					        		{
					        			if (!lastBarred)
					        			{
						        			cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing*5);// draw the very last bar line
											lastBarred = true;
					        			}
					        			doneWriting = true; //If there are no more bars to write, stop
					        		}
					        	}
								if (!EOB)
								{
									arrayChar = chars.get(barPos)[rowPos][colPos]; //Load the next char to write
									barLength = chars.get(barPos)[rowPos].length;
								}
							}
							
							if (noSpaceAvailable)
							{
								//Do nothing, we are waiting until the end of the this line
							}
							else if (arrayChar == '|')
							{
								if(i - barSpacing > 0)
								{
									line.drawLine(cb, 0f, 0f, 0f); //This is used to draw the lines, it allows cb.lineTo to function. Draws nothing on its own.
									cb.moveTo(q, i + j);
									cb.lineTo(q, i + j - barSpacing);
									
									//cancelBarDraw = true; //Don't draw a bar line if this is at the end, we have already done that
									
									colPos++;
							        if (colPos == barLength)
							        {
							        	colPos = 0;
							        	barPos++;
							        	if (barPos < chars.size())
							        	{
							        		if ((chars.get(barPos)[0].length * givenSpacing) > (pageWidth - marginRight - q))
							        		{
							        			noSpaceAvailable = true;
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
							        			cb.moveTo(q, i + j);
												cb.lineTo(q, i + j + barSpacing*5);// draw the very last bar line
												lastBarred = true;
						        			}
						        			doneWriting = true; //If there are no more bars to write, stop
						        		}
						        	}
							        if (!EOB)
									{
										arrayChar = chars.get(barPos)[rowPos][colPos]; //Load the next char to write
										barLength = chars.get(barPos)[rowPos].length;
									}
								}
								else
								{
									colPos++;
							        if (colPos == barLength)
							        {
							        	colPos = 0;
							        	barPos++;
							        	if (barPos < chars.size())
							        	{
							        		if ((chars.get(barPos)[0].length * givenSpacing) > (pageWidth - marginRight - q))
							        		{
							        			noSpaceAvailable = true;
							        			if (rowPos != 5)
							        			{
								        			cb.moveTo(q + givenSpacing, i + j);
													cb.lineTo(q + givenSpacing, i + j - barSpacing);
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
							        			cb.moveTo(q, i + j);
												cb.lineTo(q, i + j + barSpacing*5);// draw the very last bar line
												lastBarred = true;
						        			}
						        			doneWriting = true; //If there are no more bars to write, stop
						        		}
						        	}
							        if (!EOB)
									{
										arrayChar = chars.get(barPos)[rowPos][colPos]; //Load the next char to write
										barLength = chars.get(barPos)[rowPos].length;
									}
								}
							}
							else if (arrayChar == '-' || arrayChar == '<')
							{
								colPos++;
						        if (colPos == barLength)
						        {
						        	colPos = 0;
						        	barPos++;
						        	if (barPos < chars.size())
						        	{
						        		if ((chars.get(barPos)[0].length * givenSpacing) > (pageWidth - marginRight - q))
						        		{
						        			noSpaceAvailable = true;
						        			if (rowPos != 5)
						        			{
							        			cb.moveTo(q + givenSpacing, i + j);
												cb.lineTo(q + givenSpacing, i + j - barSpacing);
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
						        			cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing*5);// draw the very last bar line
											lastBarred = true;
					        			}
					        			doneWriting = true; //If there are no more bars to write, stop
					        		}
					        	}
						        //System.out.println("Column: " + colPos + " Row: " + rowPos + " Bar: " + barPos + " EOB: " + EOB);
						        if (!EOB)
								{
									arrayChar = chars.get(barPos)[rowPos][colPos]; //Load the next char to write
									barLength = chars.get(barPos)[rowPos].length;
								}
							}
							else if (arrayChar == '*')
							{
								createCircle(cb, q, i + j, noteFontSize/6);
								colPos++;
						        if (colPos == barLength)
						        {
						        	colPos = 0;
						        	barPos++;
						        	if (barPos < chars.size())
						        	{
						        		if ((chars.get(barPos)[0].length * givenSpacing) > (pageWidth - marginRight - q))
						        		{
						        			noSpaceAvailable = true;
						        			if (rowPos != 5)
						        			{
							        			cb.moveTo(q + givenSpacing, i + j);
												cb.lineTo(q + givenSpacing, i + j - barSpacing);
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
						        			cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing*5);// draw the very last bar line
											lastBarred = true;
					        			}
					        			doneWriting = true; //If there are no more bars to write, stop
					        		}
					        	}
						        //System.out.println("Column: " + colPos + " Row: " + rowPos + " Bar: " + barPos + " EOB: " + EOB);
						        if (!EOB)
								{
									arrayChar = chars.get(barPos)[rowPos][colPos]; //Load the next char to write
									barLength = chars.get(barPos)[rowPos].length;
								}
							}
							else if (arrayChar == 's') //Draw a slash before an s
							{
								line.drawLine(cb, 0f, 0f, 0f); //This is used to draw the lines, it allows cb.lineTo to function. Draws nothing on its own.
								cb.moveTo(q - noteFontSize, i + j - noteFontSize/3); //Give the option for the user to tweek the settings for this?
								cb.lineTo(q, i + j + noteFontSize/3); //How should font affect it?
								
								colPos++;
						        if (colPos == barLength)
						        {
						        	colPos = 0;
						        	barPos++;
						        	if (barPos < chars.size())
						        	{
						        		if ((chars.get(barPos)[0].length * givenSpacing) > (pageWidth - marginRight - q))
						        		{
						        			noSpaceAvailable = true;
						        			if (rowPos != 5)
						        			{
							        			cb.moveTo(q + givenSpacing, i + j);
												cb.lineTo(q + givenSpacing, i + j - barSpacing);
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
						        			cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing*5);// draw the very last bar line
											lastBarred = true;
					        			}
					        			doneWriting = true; //If there are no more bars to write, stop
					        		}
					        	}
						        if (!EOB)
								{
									arrayChar = chars.get(barPos)[rowPos][colPos]; //Load the next char to write
									barLength = chars.get(barPos)[rowPos].length;
								}
							}
							else if (arrayChar == 'p') //Draw a phrasing line between this character and the next one
							{
								if (i + j == lastWriteY) //The two characters are on the same line, all is well!
								{
									cb.moveTo(q, i + j + noteFontSize); //We are drawing the curve backwards, so set start point to our current location
									cb.curveTo((q + lastWriteX)/2 , i + j + (noteFontSize*2),lastWriteX, lastWriteY + noteFontSize);
								}
								else //The characters are on seperate lines, or perhaps different pages. Arc to the end of the page and beginning of the page for both.
								{
									cb.moveTo(q, i + j + noteFontSize); //We are drawing the curve backwards, so set start point to our current location
									cb.curveTo((q + marginLeft)/2 , i + j + (noteFontSize*2), marginLeft, i + j + noteFontSize); 
									
									cb.moveTo(marginRight, lastWriteY);
									cb.curveTo((lastWriteX + marginRight)/2 , lastWriteY + (noteFontSize*2), lastWriteX , lastWriteY + noteFontSize);
								}
								
								colPos++;
						        if (colPos == barLength)
						        {
						        	colPos = 0;
						        	barPos++;
						        	if (barPos < chars.size())
						        	{
						        		if ((chars.get(barPos)[0].length * givenSpacing) > (pageWidth - marginRight - q))
						        		{
						        			noSpaceAvailable = true;
						        			if (rowPos != 5)
						        			{
							        			cb.moveTo(q + givenSpacing, i + j);
												cb.lineTo(q + givenSpacing, i + j - barSpacing);
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
						        			cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing*5);// draw the very last bar line
											lastBarred = true;
					        			}
					        			doneWriting = true; //If there are no more bars to write, stop
					        		}
					        	}
						        if (!EOB)
								{
									arrayChar = chars.get(barPos)[rowPos][colPos]; //Load the next char to write
									barLength = chars.get(barPos)[rowPos].length;
								}
							}
							else if (arrayChar == '>') //Draw a diamond after a > symbol, the starting < can be ignored since it doesn't actualy carry any info
							{
								line.drawLine(cb, 0f, 0f, 0f); //This is used to draw the lines, it allows cb.lineTo to function. Draws nothing on its own.
								cb.moveTo(q - noteFontSize, i + j);
								cb.lineTo(q - noteFontSize/2, i + j + noteFontSize/2);
								cb.lineTo(q, i + j); 
								cb.lineTo(q - noteFontSize/2, i + j - noteFontSize/2);
								cb.lineTo(q - noteFontSize, i + j);
								cb.lineTo(q - noteFontSize/2, i + j + noteFontSize/2); //Draws a diamond, with an extra leg to eliminate the starting area having a mark
								
								if ((q - whiteSpace*2 - noteFontSize) - lineStart > 0) //Do not draw lines backwards, if there isn't enough space just draw no line at all. This should be used everywhere, but is most prominent here TODO: Apply this change everywhere needed
								{
								cb.moveTo(lineStart, i + j);
								cb.lineTo(q - whiteSpace*2 - noteFontSize , i + j );
								}
								lineStart = q + whiteSpace*2;
								
								colPos++;
						        if (colPos == barLength)
						        {
						        	colPos = 0;
						        	barPos++;
						        	if (barPos < chars.size())
						        	{
						        		if ((chars.get(barPos)[0].length * givenSpacing) > (pageWidth - marginRight - q))
						        		{
						        			noSpaceAvailable = true;
						        			if (rowPos != 5)
						        			{
							        			cb.moveTo(q + givenSpacing, i + j);
												cb.lineTo(q + givenSpacing, i + j - barSpacing);
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
						        			cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing*5);// draw the very last bar line
											lastBarred = true;
					        			}
					        			doneWriting = true; //If there are no more bars to write, stop
					        		}
					        	}
						        if (!EOB)
								{
									arrayChar = chars.get(barPos)[rowPos][colPos]; //Load the next char to write
									barLength = chars.get(barPos)[rowPos].length;
								}
							}
							else //Otherwise it's a normal character, print it out. Make sure to catch all special characters before this section
							{
								char nextChar;
								if (colPos+1 != barLength) //Get the next character to see if it is a two digit number, set to blank if the next char does not exist (no space)
								{
									nextChar = chars.get(barPos)[rowPos][colPos+1];
								}
								else
								{
									nextChar = '-'; 
								}
								if (IsDigit(nextChar)) //it is a two digit number, print them out together
								{
									currentChar = new Phrase(("" + arrayChar + nextChar), numberFont); //Replace this with the character from the array we are currently proccessing.
									column.setSimpleColumn(currentChar, q - noteFontSize - noteFontSize/2, i + j - noteFontSize/2, q + noteFontSize/2, i + j + noteFontSize/2, noteFontSize, Element.ALIGN_LEFT); //Writes the character curentChar
							        column.go();
							        
							        line.drawLine(cb, 0f, 0f, 0f); //This is used to draw the lines, it allows cb.lineTo to function. Draws nothing on its own.
							        if ((q - whiteSpace*2 - noteFontSize) - lineStart > 0) //Do not draw lines backwards, if there isn't enough space just draw no line at all. This should be used everywhere, but is most prominent here TODO: Apply this change everywhere needed
									{
									cb.moveTo(lineStart, i + j);
									cb.lineTo(q - whiteSpace*2 - noteFontSize , i + j );
									}
							        
							        char[][] tempArray = chars.get(barPos); //The next character has already been written, change it to a blank so we don't write it twice
							        tempArray[rowPos][colPos+1] = '-';
							        chars.set(barPos, tempArray);
							        lineStart = q + whiteSpace*2;
							        
							        lastWriteX = q;
							        lastWriteY = i + j + noteFontSize; //Extra noteFontSize to account for the fact that it is a double digit, might be too big.
								}
								else //it is single digit, same method as before
								{
									if (rowPos == 0) //Check if it's the top row, this section writes the "Repeat for # times"
									{
										if (chars.get(barPos)[rowPos+1][colPos] == '|') //There was a number, but below is a bar line so this is a repeat symbol. Print a repeat line above the lines, and draw a normal bar line here
										{
											currentChar = new Phrase(("Repeat " + arrayChar + " times" ), numberFont); //Replace this with the character from the array we are currently proccessing.
											column.setSimpleColumn(currentChar, q - 12*noteFontSize , i + j +noteFontSize , q, i + j + noteFontSize*2, noteFontSize, Element.ALIGN_LEFT); //Writes the character curentChar
									        column.go();
									        
									        line.drawLine(cb, 0f, 0f, 0f); //This is used to draw the lines, it allows cb.lineTo to function. Draws nothing on its own.
											cb.moveTo(q, i + j); //This draws the vertical bar line where the number was. there might be some problems with using this naked, check first for errors
											cb.lineTo(q, i + j - barSpacing);
											
											cancelBarDraw = true; //Don't draw a bar line if this is at the end of a bar, we have already done that (It would be extra)
										}
										else // repeat of below, the checks must be done seperatly to avoid index out of bounds
										{
											line.drawLine(cb, 0f, 0f, 0f); //This is used to draw the lines, it allows cb.lineTo to function. Draws nothing on its own.
											if ((q - whiteSpace - noteFontSize) - lineStart > 0) //Do not draw lines backwards, if there isn't enough space just draw no line at all. This should be used everywhere, but is most prominent here TODO: Apply this change everywhere needed
											{
											cb.moveTo(lineStart, i + j);
											cb.lineTo(q - whiteSpace - noteFontSize , i + j );
											}
											
											currentChar = new Phrase(("" + arrayChar), numberFont); //Replace this with the character from the array we are currently proccessing.
											column.setSimpleColumn(currentChar, q - noteFontSize, i + j - noteFontSize/2, q, i + j + noteFontSize/2, noteFontSize, Element.ALIGN_LEFT); //Writes the character curentChar
									        column.go();
									        lineStart = q + whiteSpace;
									        
									        lastWriteX = q;
									        lastWriteY = i + j;
										}
									}
									else //not the top row, print numbers normally
									{
										line.drawLine(cb, 0f, 0f, 0f); //This is used to draw the lines, it allows cb.lineTo to function. Draws nothing on its own.
										if ((q - whiteSpace - noteFontSize) - lineStart > 0) //Do not draw lines backwards, if there isn't enough space just draw no line at all. This should be used everywhere, but is most prominent here TODO: Apply this change everywhere needed
										{
										cb.moveTo(lineStart, i + j);
										cb.lineTo(q - whiteSpace - noteFontSize , i + j );
										}
										
										currentChar = new Phrase(("" + arrayChar), numberFont); //Replace this with the character from the array we are currently proccessing.
										column.setSimpleColumn(currentChar, q - noteFontSize, i + j - noteFontSize/2, q, i + j + noteFontSize/2, noteFontSize, Element.ALIGN_LEFT); //Writes the character curentChar
								        column.go();
								        lineStart = q + whiteSpace;
								        
								        lastWriteX = q;
								        lastWriteY = i + j;
									}
								}
						        
						        colPos++; //TODO change this increment to a method
						        if (colPos == barLength)
						        {
						        	colPos = 0;
						        	barPos++;
						        	if (barPos < chars.size())
						        	{
						        		if ((chars.get(barPos)[0].length * givenSpacing) > (pageWidth - marginRight - q))
						        		{
						        			noSpaceAvailable = true;
						        			if (rowPos != 5 && !cancelBarDraw)
						        			{
							        			cb.moveTo(q + givenSpacing, i + j);
												cb.lineTo(q + givenSpacing, i + j - barSpacing);
						        			}
						        		}
						        	}
						        }
						        if (barPos >= chars.size())
					        	{
						        	EOB = true;
					        		if (rowPos >= 5)
					        		{
					        			if (!lastBarred)
					        			{
						        			cb.moveTo(q, i + j);
											cb.lineTo(q, i + j + barSpacing*5);// draw the very last bar line
											lastBarred = true;
					        			}
					        			doneWriting = true; //If there are no more bars to write, stop
					        		}
					        	}
						        if (!EOB)
								{
									arrayChar = chars.get(barPos)[rowPos][colPos]; //Load the next char to write
									barLength = chars.get(barPos)[rowPos].length;
								}
							}
						}
						if ((q + givenSpacing*2 >= pageWidth - (int)marginLeft - givenSpacing)) //If the next character doesn't fit, and we are at the end and need to draw a line to the end
				        {
							line.drawLine(cb, 0f, 0f, 0f); //This is used to draw the lines, it allows cb.lineTo to function. Draws nothing on its own.
							cb.moveTo(lineStart, i + j);
							cb.lineTo(pageWidth , i + j );
				        }
					}
					rowSave[rowPos][0] = barPos; //Keeps track of what bar we are at on this line
	        		rowSave[rowPos][1] = colPos; //Start of new bar, so we are at column 0
	        		rowSave[rowPos][2] = barLength; //Keep track of how long this section is, to avoid index errors
	        		rowSave[rowPos][3] = lastWriteX; //Keeps track of the last element's X position written on this line, so we can draw a phrase arc to it if needed later
	        		rowSave[rowPos][4] = lastWriteY; //Keeps track of the last element's Y position
					rowPos++;
					
				}
				if (j - groupBarSpacing < 0 + marginBottom && !doneWriting)
				{
					 j = pageHeight - (int)marginTop;
					 document.newPage();
				}
			}
		}
		document.close();
		
	}
	
	private static boolean IsDigit(char nextChar)
	{
		return (nextChar == '0' || nextChar == '1' || nextChar == '2' || nextChar == '3' || nextChar == '4' || nextChar == '5' || nextChar == '6' || nextChar == '7' || nextChar == '8' || nextChar == '9');
	}
	
	public static boolean SetGroupBarSpacing(int newSpacing)
	{
		if (newSpacing <0)
		{
			return false; //Could not change spacing
		}
		else
		{
			groupBarSpacing = newSpacing;
			return true;
		}
	}
	
	public static boolean SetWhiteSpace(int newSpacing)
	{
		if (newSpacing <0)
		{
			return false; //Could not change spacing
		}
		else
		{
			whiteSpace = newSpacing;
			return true;
		}
	}
	
	public static boolean SetBarSpacing(int newSpacing)
	{
		if (newSpacing <0)
		{
			return false; //Could not change spacing
		}
		else
		{
			barSpacing = newSpacing;
			return true;
		}
	}
	
	public static boolean SetGivenSpacing(int newSpacing)
	{
		if (newSpacing <0)
		{
			return false; //Could not change spacing
		}
		else
		{
			givenSpacing = newSpacing;
			return true;
		}
	}
	
	public static boolean SetNoteFontSize(int newSpacing)
	{
		if (newSpacing < 0)
		{
			return false; //Could not change spacing
		}
		else
		{
			noteFontSize = newSpacing;
			return true;
		}
	}
	
	public static void createCircle(PdfContentByte canvas, float x, float y, float r) 
	{
		float b = 0.5523f;
	    canvas.moveTo(x + r, y);
	    canvas.curveTo(x + r, y - r * b, x + r * b, y - r, x, y - r);
	    canvas.curveTo(x - r * b, y - r, x - r, y - r * b, x - r, y);
	    canvas.curveTo(x - r, y + r * b, x - r * b, y + r, x, y + r);
	    canvas.curveTo(x + r * b, y + r, x + r, y + r * b, x + r, y);
	}
}
