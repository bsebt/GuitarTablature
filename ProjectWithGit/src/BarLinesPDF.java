import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;


public class BarLinesPDF 
{
	public static final String DEST = "C:/Users/me/Desktop/tester.pdf";
	
	public static final String TITLE_STRING = "Moonlight Sonata";
	public static final String COMPOSER_STRING = "Ludwig Van Beethoven";
	
	private static final float marginLeft = 0.0f; //Note original margins are 36.0f for letter size
	private static final float marginRight = 0.0f;
	private static final float marginTop = 0.0f;
	private static final float marginBottom = 0.0f;
	
	private static Font titleFont = new Font(FontFamily.HELVETICA, 30);
	private static Font composerFont = new Font(FontFamily.HELVETICA, 14);
	private static Paragraph title = new Paragraph(TITLE_STRING, titleFont);
	private static Paragraph composer = new Paragraph (COMPOSER_STRING, composerFont);
	
	private static LineSeparator line = new LineSeparator();

	public static void main (String args[]) throws DocumentException, IOException
	{
		Document document = new Document(PageSize.LETTER, marginLeft, marginRight, marginTop, marginBottom);
		PdfWriter.getInstance(document, new FileOutputStream(DEST));
		//Header/Meta Data:
		document.addCreator("Kevin");
		document.addAuthor("Kevin Arindaeng");
		
		//Changing some object features:
		title.setAlignment(Element.ALIGN_CENTER);
		composer.setAlignment(Element.ALIGN_CENTER);
		line.setAlignment(Element.ALIGN_MIDDLE);

		//Creating the document:
		document.open();
		document.add(title);
		document.add(composer);
		for(int i = 0; i < 5; i ++)
		{
			for(int j = 0; j < 2; j++)
			{
				document.add(new Paragraph(" "));
			}
			for(int j = 0; j < 5; j++)
			{
				document.add(line);
				document.add(new Paragraph(" "));
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
