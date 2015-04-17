package Project;

import java.awt.Desktop;
import java.io.File;
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
	
		
		@Test //Tests to see if the PDF creation was successful for EmptyFile.txt. If it works, open it.
		public void testEmptyFilePDF() throws DocumentException, IOException
		{
			File file[] = {new File("EmptyFile.txt")};
			BarLinesPDF.convertPDF(file, "EmptyFile.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("EmptyFile.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for MoonlightSonata.txt. If it works, open it.
		public void testMoonlightSonataPDF() throws DocumentException, IOException
		{
			File file[] = {new File("MoonlightSonata.txt")};
			BarLinesPDF.convertPDF(file, "MoonlightSonata.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("MoonlightSonata.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void testRememberingRainPDF() throws DocumentException, IOException
		{
			File file[] = {new File("RememberingRain.txt")};
			BarLinesPDF.convertPDF(file, "RememberingRain.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("RememberingRain.pdf"));
		}
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void unevenLinesPDF() throws DocumentException, IOException
		{
			File file[] = {new File("UnevenLines.txt")};
			BarLinesPDF.convertPDF(file, "UnevenLines.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("UnevenLines.pdf"));
		}
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void incompleteBarPDF() throws DocumentException, IOException
		{
			File file[] = {new File("IncompleteBar.txt")};
			BarLinesPDF.convertPDF(file, "IncompleteBar.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("IncompleteBar.pdf"));
		}
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void ProsePDF() throws DocumentException, IOException
		{
			File file[] = {new File("Prose.txt")};
			BarLinesPDF.convertPDF(file, "Prose.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("Prose.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void extendedASCIIPDF() throws DocumentException, IOException
		{
			File file[] = {new File("ExtendedASCII.txt")};
			BarLinesPDF.convertPDF(file, "ExtendedASCII.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("ExtendedASCII.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void emptyFileWithInfo() throws DocumentException, IOException
		{
			File file[] = {new File("EmptyFileWithInfo.txt")};
			BarLinesPDF.convertPDF(file, "EmptyFileWithInfo.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("EmptyFileWithInfo.pdf"));
		}
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void testElNegritoPDF() throws DocumentException, IOException
		{
			File file[] = {new File("elnegrito.txt")};
			BarLinesPDF.convertPDF(file, "ElNegrito.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("ElNegrito.pdf"));
		}
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void testBohemianRhapsodyPDF() throws DocumentException, IOException
		{
			File file[] = {new File("bohemianrhapsody.txt")};
			BarLinesPDF.convertPDF(file, "BohemianRhapsody.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("BohemianRhapsody.pdf"));
		}
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void testExtraWhiteSpacesPDF() throws DocumentException, IOException
		{
			File file[] = {new File("ExtraWhiteSpaces.txt")};
			BarLinesPDF.convertPDF(file, "ExtraWhiteSpaces.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("ExtraWhiteSpaces.pdf"));
		}
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void testGarbageInLine() throws DocumentException, IOException
		{
			File file[] = {new File("GarbageInLine.txt")};
			BarLinesPDF.convertPDF(file, "GarbageInLine.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("GarbageInLine.pdf"));
		}
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void testStairwayToHeavenPDF() throws DocumentException, IOException
		{
			File file[] = {new File("StairwayToHeaven.txt")};
			BarLinesPDF.convertPDF(file, "StairwayToHeaven.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("StairwayToHeaven.pdf"));
		}
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void testHowDeepIsYourLovePDF() throws DocumentException, IOException
		{
			File file[] = {new File("HowDeepIsYourLove.txt")};
			BarLinesPDF.convertPDF(file, "HowDeepIsYourLove.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("HowDeepIsYourLove.pdf"));
		}
		@Test //Tests to see if the PDF creation was successful for RememberingRain.txt. If it works, open it.
		public void testHeyJudePDF() throws DocumentException, IOException
		{
			File file[] = {new File("HeyJude.txt")};
			BarLinesPDF.convertPDF(file, "HeyJude.pdf");
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("HeyJude.pdf"));
		}
}
