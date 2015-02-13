package KevinsWork;

import static org.junit.Assert.*;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.DocumentException;

public class Tester 
{
	private BufferedReader reader;
	private Boolean result;
	private Boolean textFileOpen;
	private DataToArray test, EmptyFile, IncompleteBar, MoonlightSonata, RememberingRain, UnevenLines, NoFile, NullFile;
	private ArrayList<char[][]> data;
	private Desktop desktop;
	private BarLinesPDF pdf;
	
		@Before
		public void setUp() throws DocumentException, IOException
		{
			pdf = new BarLinesPDF();
			test = new DataToArray();
			NullFile = new DataToArray(null);
			NoFile = new DataToArray("");
			EmptyFile = new DataToArray("EmptyFile.txt");
			MoonlightSonata = new DataToArray("MoonlightSonata.txt");
			IncompleteBar = new DataToArray("IncompleteBar.txt");
			RememberingRain = new DataToArray("RememberingRain.txt");
			UnevenLines = new DataToArray("UnevenLines.txt");
			desktop = null;
		}
		
//Testing DataToArray objects ------------------------------------------------------------------
		
		@Test //Tests to see if the conversion works with a non-existing file.
		public void noData() throws DocumentException, IOException
		{
			data = NoFile.textToArray();
			result = (data.equals(null)) ? false : true;
			assertTrue(result);
		}
		@Test //Tests to see if the conversion works for a null file.
		public void nullData() throws DocumentException, IOException
		{
			data = NullFile.textToArray();
			result = (data.equals(null)) ? false : true;
			assertTrue(result);
		}
		@Test //Tests to see if the conversion works for the test file.
		public void testData() throws DocumentException, IOException
		{
			data = test.textToArray();
			result = (data.equals(null)) ? false : true;
			assertTrue(result);
		}
		@Test //Tests to see if the conversion works for an empty file.
		public void EmptyFileData() throws DocumentException, IOException
		{
			data = EmptyFile.textToArray();
			result = (data.equals(null)) ? false : true;
			assertTrue(result);
		}
		@Test //Tests to see if the conversion works for the MoonlightSonata file.
		public void MoonlightSonataData() throws DocumentException, IOException
		{
			data = MoonlightSonata.textToArray();
			result = (data.equals(null)) ? false : true;
			assertTrue(result);
		}
		@Test //Tests to see if the conversion works for the IncompleteBar file.
		public void IncompleteBarData() throws DocumentException, IOException
		{
			data = IncompleteBar.textToArray();
			result = (data.equals(null)) ? false : true;
			assertTrue(result);
		}
		@Test //Tests to see if the conversion works for the RememberingRain file.
		public void RememberingRainData() throws DocumentException, IOException
		{
			data = RememberingRain.textToArray();
			result = (data.equals(null)) ? false : true;
			assertTrue(result);
		}
		@Test //Tests to see if the conversion works for the UnevenLines file.
		public void UnevenLinesData() throws DocumentException, IOException
		{
			data = UnevenLines.textToArray();
			result = (data.equals(null)) ? false : true;
			assertTrue(result);
		}

//Testing BarLinesPDF ------------------------------------------------------------------
		
		@Test //Tests to see if the PDF has a null destination
		public void notNullPDFDestination()
		{
			assertFalse(pdf.DEST.equals(null));
		}
		@Test //Tests to see if the PDF has an empty destination
		public void emptyPDFDestination()
		{
			assertFalse(pdf.DEST.equals(""));
		}
		
		@Test //Tests to see if the PDF creation was successful for test.txt
		public void testPDF() throws DocumentException, IOException
		{
			pdf.SetDesintation("test.pdf");
			pdf.convertPDF(test);
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("test.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for test.txt
		public void testEmptyFilePDF() throws DocumentException, IOException
		{
			pdf.SetDesintation("EmptyFile.pdf");
			pdf.convertPDF(EmptyFile);
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("EmptyFile.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for test.txt
		public void testMoonlightSonataPDF() throws DocumentException, IOException
		{
			pdf.SetDesintation("MoonlightSonata.pdf");
			pdf.convertPDF(MoonlightSonata);
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("MoonlightSonata.pdf"));
		}
		
		@Test //Tests to see if the PDF creation was successful for test.txt
		public void testRememberingRainPDF() throws DocumentException, IOException
		{
			pdf.SetDesintation("RememberingRain.pdf");
			pdf.convertPDF(RememberingRain);
			desktop = (Desktop.isDesktopSupported()) ? Desktop.getDesktop() : null;
			desktop.open(new File("RememberingRain.pdf"));
		}
		
}
