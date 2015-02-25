package Project;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.DocumentException;

public class PDFTester
{
	private Desktop desktop;
	
		@Before
		public void setUp() throws DocumentException, IOException
		{
			desktop = null;
		}

//Testing BarLinesPDF ------------------------------------------------------------------
		
		@Test (expected = FileNotFoundException.class)//Tests to see if the PDF has an empty destination. If it works, open it.
		public void emptyPDFDestination() throws DocumentException, IOException
		{
			BarLinesPDF.convertPDF(".txt", ".pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File(".pdf"));
		}
		@Test //Tests to see if the PDF creation was successful for Test.txt. If it works, open it.
		public void testPDF() throws DocumentException, IOException
		{
			BarLinesPDF.convertPDF("Test.txt", "test.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("test.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for EmptyFile.txt. If it works, open it.
		public void testEmptyFilePDF() throws DocumentException, IOException
		{
			BarLinesPDF.convertPDF("EmptyFile.txt", "EmptyFile.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("EmptyFile.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for MoonlightSonata.txt. If it works, open it.
		public void testMoonlightSonataPDF() throws DocumentException, IOException
		{
			BarLinesPDF.convertPDF("MoonlightSonata.txt", "MoonlightSonata.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("MoonlightSonata.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void testRememberingRainPDF() throws DocumentException, IOException
		{
			BarLinesPDF.convertPDF("RememberingRain.txt", "RememberingRain.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("RememberingRain.pdf"));
		}
		/*
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void unevenLinesPDF() throws DocumentException, IOException
		{
			BarLinesPDF.convertPDF("UnevenLines.txt", "UnevenLines.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("UnevenLines.pdf"));
		}
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void incompleteBarPDF() throws DocumentException, IOException
		{
			BarLinesPDF.convertPDF("IncompleteBar.txt", "IncompleteBar.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("IncompleteBar.pdf"));
		}
		*/
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void ProsePDF() throws DocumentException, IOException
		{
			BarLinesPDF.convertPDF("Prose.txt", "Prose.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("Prose.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void extendedASCIIPDF() throws DocumentException, IOException
		{
			BarLinesPDF.convertPDF("ExtendedASCII.txt", "ExtendedASCII.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("ExtendedASCII.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void emptyFileWithInfo() throws DocumentException, IOException
		{
			BarLinesPDF.convertPDF("EmptyFileWithInfo.txt", "EmptyFileWithInfo.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("EmptyFileWithInfo.pdf"));
		}
}
