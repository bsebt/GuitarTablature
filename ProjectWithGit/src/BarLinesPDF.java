import java.io.FileOutputStream;
import java.io.IOException;

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
	public static final String DEST = "/cs/home/flexo735/Desktop/eclipse/tester.pdf";
	
	public static final String TITLE_STRING = "Moonlight Sonata";
	public static final String COMPOSER_STRING = "Ludwig Van Beethoven";
	
	private static final float marginLeft = 50.0f; //Note original margins are 36.0f for letter size
	private static final float marginRight = 50.0f;
	private static final float marginTop = 0.0f;
	private static final float marginBottom = 50.0f;
	
	private static Font titleFont = new Font(FontFamily.HELVETICA, 30);
	private static Font composerFont = new Font(FontFamily.HELVETICA, 14);
	private static Paragraph title = new Paragraph(TITLE_STRING, titleFont);
	private static Paragraph composer = new Paragraph (COMPOSER_STRING, composerFont);
	
	private static int noteFontSize = 8; //Size of the characters to be written to the page
	private static int givenSpacing = 30; //The spacing given at the start of the program, change to variable once we read it in
	private static int barSpacing = 10; //Space between individual lines to be drawn
	private static int whiteSpace = 1; //Space around a written number that does not have a bar line
	private static int groupBarSpacing = 100; //Spaces between the groups of 6 lines
	private static int topVoidSpace = 100; //Space at the top of the page for info.
	private static int pageHeight = 800;
	private static int pageWidth = 620;
	//Note, standard page is 620 units wide and 800 units tall.
	
	private static LineSeparator line = new LineSeparator();
	

	public static void main (String args[]) throws DocumentException, IOException
	{
		Document document = new Document(PageSize.LETTER, marginLeft, marginRight, marginTop, marginBottom);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
		//Header/Meta Data:
		document.addCreator("Kevin");
		document.addAuthor("Kevin Arindaeng");
		
		//Changing some object features:
		title.setAlignment(Element.ALIGN_CENTER);
		composer.setAlignment(Element.ALIGN_CENTER);
		line.setAlignment(Element.ALIGN_MIDDLE);

		//Creating the document:
		document.open();
		PdfContentByte cb = writer.getDirectContent(); //writer to draw lines
		document.add(title);
		document.add(composer);
		ColumnText column = new ColumnText(cb);
		int lineStart;
		for (int j = (int)marginBottom; j <= pageHeight - topVoidSpace; j += groupBarSpacing) //Groups of bars, 100 is void space at the top for title
		{
			cb.moveTo(marginLeft, j);
			cb.lineTo(marginLeft, j+ 5*barSpacing); //Far left vertical bar
			for(int i = 0; i < barSpacing*6; i += barSpacing) //Individual horizontal bars
			{
				lineStart = 0;
				line.drawLine(cb, 0f, 0f, 0f); //This is used to draw the lines, it allows cb.lineTo to function. Draws nothing on its own.
				for(int q = (int)marginLeft + givenSpacing; q < pageWidth - (int)marginLeft - givenSpacing; q += givenSpacing) //Individual characters
				{
					if ((q + givenSpacing >= pageWidth - (int)marginLeft - givenSpacing)) //If the next character doesn't fit, and we are one the last bar, draw a bar line
			        {
						cb.moveTo(lineStart, i + j);
						cb.lineTo(pageWidth , i + j );
			        	cb.moveTo(q, j);
						cb.lineTo(q, j+ 5*barSpacing);
			        }
					if (q + givenSpacing < pageWidth - (int)marginLeft - givenSpacing) //Don't print a character on the far right side to make room for the bar line
					{
						//TODO - Check the char to be written, ignore - and move on, otherwise write it down and place the line correctly.
						cb.moveTo(lineStart, i + j);
						cb.lineTo(q - whiteSpace - noteFontSize , i + j );
						lineStart = q + whiteSpace;
						Phrase currentChar = new Phrase("3"); //Replace this with the character from the array we are currently proccessing.
						column.setSimpleColumn(currentChar, q - noteFontSize, i + j - noteFontSize/2, q, i + j + noteFontSize/2, noteFontSize, Element.ALIGN_LEFT); //Writes the character curentChar
				        column.go();
					}
				}
			}
		}
		document.close();
		
		//Testing:
		System.out.println("left margin: " + document.leftMargin());
		System.out.println("right margin: " + document.rightMargin());
		System.out.println("top margin: " + document.topMargin());
		System.out.println("bottom margin: " + document.bottomMargin());
		System.out.println("title alignment: " + title.getAlignment());
		System.out.println("composer alignment: " + composer.getAlignment());
		System.out.println("line width: " + line.getLineWidth());
	}
}
