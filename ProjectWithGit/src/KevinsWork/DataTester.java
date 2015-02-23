package KevinsWork;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.DocumentException;

public class DataTester 
{
	private Boolean result;
	private DataToArray test, EmptyFile, IncompleteBar, MoonlightSonata, 
	RememberingRain, UnevenLines, NoFile, NullFile, ProseFile, ExtendedASCII;
	private ArrayList<char[][]> data;
	
		@Before
		public void setUp() throws DocumentException, IOException
		{
			test = new DataToArray();
			NullFile = new DataToArray(null);
			NoFile = new DataToArray("");
			EmptyFile = new DataToArray("EmptyFile.txt");
			MoonlightSonata = new DataToArray("MoonlightSonata.txt");
			IncompleteBar = new DataToArray("IncompleteBar.txt");
			RememberingRain = new DataToArray("RememberingRain.txt");
			UnevenLines = new DataToArray("UnevenLines.txt");
			ProseFile = new DataToArray("Prose.txt");
			ExtendedASCII = new DataToArray("ExtendedASCII.txt");
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
		@Test //Tests to see if the conversion works for the UnevenLines file.
		public void ProseData() throws DocumentException, IOException
		{
			data = UnevenLines.textToArray();
			result = (data.equals(null)) ? false : true;
			assertTrue(result);
		}
		@Test //Tests to see if the conversion works for the UnevenLines file.
		public void ExtnededASCIIData() throws DocumentException, IOException
		{
			data = UnevenLines.textToArray();
			result = (data.equals(null)) ? false : true;
			assertTrue(result);
		}
}
