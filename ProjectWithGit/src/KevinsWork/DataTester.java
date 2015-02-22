package KevinsWork;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.DocumentException;

public class DataTester 
{
	private Boolean nullResult;
	private DataToArray test, EmptyFile, IncompleteBar, MoonlightSonata, RememberingRain, UnevenLines, NonExistingFile, NullFile, Prose;
	private ArrayList<char[][]> data;
	
		@Before
		public void setUp() throws DocumentException, IOException
		{
			test = new DataToArray();
			NullFile = new DataToArray(null);
			NonExistingFile = new DataToArray("");
			EmptyFile = new DataToArray("EmptyFile.txt");
			MoonlightSonata = new DataToArray("MoonlightSonata.txt");
			IncompleteBar = new DataToArray("IncompleteBar.txt");
			RememberingRain = new DataToArray("RememberingRain.txt");
			UnevenLines = new DataToArray("UnevenLines.txt");
			Prose = new DataToArray("Prose.txt");
		}
		
//Testing DataToArray objects ------------------------------------------------------------------
		
		//Tests to see if the conversion works with a non-existing file.
		@Test(expected=IOException.class)
		public void noData() throws DocumentException, IOException
		{
			data = NonExistingFile.textToArray();
			nullResult = (data.equals(null)) ? false : true;
			assertTrue(nullResult);
		}
		
		//Tests to see if the conversion works for a null file.
		@Test(expected=NullPointerException.class)
		public void nullData() throws DocumentException, IOException
		{
			data = NullFile.textToArray();
			nullResult = (data.equals(null)) ? false : true;
			assertTrue(nullResult);
		}
		@Test //Tests to see if the conversion works for the test file.
		public void testData() throws DocumentException, IOException
		{
			data = test.textToArray();
			nullResult = (data.equals(null)) ? false : true;
			assertTrue(nullResult);
		}
		@Test //Tests to see if the conversion works for an empty file.
		public void EmptyFileData() throws DocumentException, IOException
		{
			data = EmptyFile.textToArray();
			nullResult = (data.equals(null)) ? false : true;
			assertTrue(nullResult);
		}
		@Test //Tests to see if the conversion works for the MoonlightSonata file.
		public void MoonlightSonataData() throws DocumentException, IOException
		{
			data = MoonlightSonata.textToArray();
			nullResult = (data.equals(null)) ? false : true;
			assertTrue(nullResult);
		}
		@Test //Tests to see if the conversion works for the IncompleteBar file.
		public void IncompleteBarData() throws DocumentException, IOException
		{
			data = IncompleteBar.textToArray();
			nullResult = (data.equals(null)) ? false : true;
			assertTrue(nullResult);
		}
		@Test //Tests to see if the conversion works for the RememberingRain file.
		public void RememberingRainData() throws DocumentException, IOException
		{
			data = RememberingRain.textToArray();
			nullResult = (data.equals(null)) ? false : true;
			assertTrue(nullResult);
		}
		@Test //Tests to see if the conversion works for the UnevenLines file.
		public void UnevenLinesData() throws DocumentException, IOException
		{
			data = UnevenLines.textToArray();
			nullResult = (data.equals(null)) ? false : true;
			assertTrue(nullResult);
		}
		@Test //Tests to see if the conversion works for the prose file.
		public void ProseData() throws DocumentException, IOException
		{
			data = Prose.textToArray();
			nullResult = (data.equals(null)) ? false : true;
			assertTrue(nullResult);
		}
}
