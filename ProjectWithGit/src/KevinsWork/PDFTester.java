package KevinsWork;

import static org.junit.Assert.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import com.itextpdf.text.DocumentException;

public class PDFTester
{
	private Desktop desktop;
	private BarLinesPDF pdf;
	private DataToArray data;
	
		@Before
		public void setUp() throws DocumentException, IOException
		{
			data = new DataToArray();
			pdf = new BarLinesPDF();
			desktop = null;
		}

//Testing BarLinesPDF ------------------------------------------------------------------
		
		@Test //Tests to see if the PDF has a null destination. If it works, open it.
		public void notNullPDFDestination()
		{
			assertFalse(pdf.DEST.equals(null));
		}
		@Test //Tests to see if the PDF has an empty destination. If it works, open it.
		public void emptyPDFDestination()
		{
			assertFalse(pdf.DEST.equals(""));
		}
		@Test //Tests to see if the PDF creation was successful for Test.txt. If it works, open it.
		public void testPDF() throws DocumentException, IOException
		{
			pdf.DEST = ("test.pdf");
			pdf.convertPDF("Test.txt");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("test.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for EmptyFile.txt. If it works, open it.
		public void testEmptyFilePDF() throws DocumentException, IOException
		{
			pdf.DEST = ("EmptyFile.pdf");
			pdf.convertPDF("EmptyFile.txt");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("EmptyFile.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for MoonlightSonata.txt. If it works, open it.
		public void testMoonlightSonataPDF() throws DocumentException, IOException
		{
			pdf.DEST = ("MoonlightSonata.pdf");
			pdf.convertPDF("MoonlightSonata.txt");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("MoonlightSonata.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void testRememberingRainPDF() throws DocumentException, IOException
		{
			pdf.DEST = ("RememberingRain.pdf");
			pdf.convertPDF("RememberingRain.txt");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("RememberingRain.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void unevenLinesPDF() throws DocumentException, IOException
		{
			pdf.DEST = ("UnevenLines.pdf");
			pdf.convertPDF("UnevenLines.txt");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("UnevenLines.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void incompleteBarPDF() throws DocumentException, IOException
		{
			pdf.DEST = ("IncompleteBar.pdf");
			pdf.convertPDF("IncompleteBar.txt");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("IncompleteBar.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void ProsePDF() throws DocumentException, IOException
		{
			pdf.DEST = ("Prose.pdf");
			pdf.convertPDF("Prose.txt");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("Prose.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void extendedASCIIPDF() throws DocumentException, IOException
		{
			pdf.DEST = ("ExtendedASCII.pdf");
			pdf.convertPDF("ExtendedASCII.txt");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("ExtendedASCII.pdf"));
		}
}
