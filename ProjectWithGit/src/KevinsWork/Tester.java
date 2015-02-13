package KevinsWork;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.DocumentException;

public class Tester
{
	private BufferedReader reader;
	private Boolean result;
	private Boolean textFileOpen;
	private DataToArray test, EmptyFile, IncompleteBar, MoonlightSonata, RememberingRain, UnevenLines;
	private ArrayList<char[][]> data;
	
		@Before
		public void setUp() throws DocumentException, IOException
		{
			test = new DataToArray();
			EmptyFile = new DataToArray("EmptyFile.txt");
			MoonlightSonata = new DataToArray("MoonlightSonata.txt");
			IncompleteBar = new DataToArray("IncompleteBar.txt");
			RememberingRain = new DataToArray("RememberingRain.txt");
			UnevenLines = new DataToArray("UnevenLines.txt");
		}
		@Test
		public void testData() throws DocumentException, IOException
		{
			data = test.textToArray();
			result = (data.equals(null)) ? false : true;
			assertTrue(result);
		}
		@Test
		public void EmptyFileData() throws DocumentException, IOException
		{
			data = EmptyFile.textToArray();
			result = (data.equals(null)) ? false : true;
			assertTrue(result);
		}
		@Test
		public void MoonlightSonataData() throws DocumentException, IOException
		{
			data = MoonlightSonata.textToArray();
			result = (data.equals(null)) ? false : true;
			assertTrue(result);
		}
		@Test
		public void IncompleteBarData() throws DocumentException, IOException
		{
			data = IncompleteBar.textToArray();
			result = (data.equals(null)) ? false : true;
			assertTrue(result);
		}
		@Test
		public void RememberingRainData() throws DocumentException, IOException
		{
			data = RememberingRain.textToArray();
			result = (data.equals(null)) ? false : true;
			assertTrue(result);
		}
		@Test
		public void UnevenLinesData() throws DocumentException, IOException
		{
			data = UnevenLines.textToArray();
			result = (data.equals(null)) ? false : true;
			assertTrue(result);
		}
		@Test
		public void notNullPDFDestination()
		{
			assertFalse(BarLinesPDF.DEST.equals(null));
		}
		/*
		@Test
		public void notEmptyTextFile() throws IOException
		{
			reader = new BufferedReader(new FileReader(DataToArray.textFile));
			if(reader.readLine() == null)
			{
				textFileOpen = true;
			}
			else
				textFileOpen = false;
			assertFalse(textFileOpen);
		}
		*/
}
