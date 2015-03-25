package Project;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.DocumentException;

public class DataTester 
{
	private Boolean emptyResult, barResult;
	private static ArrayList<char[][]> test, EmptyFile, IncompleteBar, MoonlightSonata, 
	RememberingRain, UnevenLines, NoFile, NullFile, ProseFile, ExtendedASCII, EmptyFileWithInfo;
	
//Testing DataToArray objects ------------------------------------------------------------------
		
		@Test //Tests to see if the conversion works for the test file.
		public void testData() throws DocumentException, IOException
		{
			File file[] = {new File("test.txt")};
			test = DataToArray.textToArray(file);
			emptyResult = (test.isEmpty()) ? false : true;
			barResult = (test.size() == 15) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test(expected = FileNotFoundException.class)
		//Tests to see if the conversion works with a non-existing file.
		public void noData() throws DocumentException, IOException
		{
			File file[] = {new File("")};
			NoFile = DataToArray.textToArray(file);
			emptyResult = (NoFile.isEmpty()) ? false : true;
			assertTrue(emptyResult);
		}
		@Test //Tests to see if the conversion works for an empty file.
		public void EmptyFileData() throws DocumentException, IOException
		{
			File file[] = {new File("EmptyFile.txt")};
			EmptyFile = DataToArray.textToArray(file);
			emptyResult = (EmptyFile.isEmpty()) ? true : false;
			barResult = (EmptyFile.size() == 0) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the MoonlightSonata file.
		public void MoonlightSonataData() throws DocumentException, IOException
		{
			File file[] = {new File("MoonlightSonata.txt")};
			MoonlightSonata = DataToArray.textToArray(file);
			emptyResult = (MoonlightSonata.isEmpty()) ? false : true;
			barResult = (MoonlightSonata.size() == 35) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test(expected = IndexOutOfBoundsException.class) //I'm throwing an exception
		//Tests to see if the conversion works for the IncompleteBar file.
		public void IncompleteBarData() throws DocumentException, IOException
		{
			File file[] = {new File("IncompleteBar.txt")};
			IncompleteBar = DataToArray.textToArray(file);
			emptyResult = (IncompleteBar.isEmpty()) ? false : true;
			barResult = (IncompleteBar.size() == 0) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the RememberingRain file.
		public void RememberingRainData() throws DocumentException, IOException
		{
			File file[] = {new File("RememberingRain.txt")};
			RememberingRain = DataToArray.textToArray(file);
			emptyResult = (RememberingRain.isEmpty()) ? false : true;
			barResult = (RememberingRain.size() == 15) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test(expected = ArrayIndexOutOfBoundsException.class) //Tests to see if the conversion works for the UnevenLines file.
		public void UnevenLinesData() throws DocumentException, IOException
		{
			File file[] = {new File("UnevenLines.txt")};
			UnevenLines = DataToArray.textToArray(file);
			emptyResult = (UnevenLines.isEmpty()) ? false : true;
			barResult = (UnevenLines.size() == 1) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the Prose file.
		public void ProseData() throws DocumentException, IOException
		{
			File file[] = {new File("Prose.txt")};
			ProseFile = DataToArray.textToArray(file);
			emptyResult = (ProseFile.isEmpty()) ? false : true;
			barResult = (ProseFile.size() == 1) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the ExtendedASCII file.
		public void ExtendedASCIIData() throws DocumentException, IOException
		{
			File file[] = {new File("ExtendedASCII.txt")};
			ExtendedASCII = DataToArray.textToArray(file);
			emptyResult = (ExtendedASCII.isEmpty()) ? false : true;
			barResult = (ExtendedASCII.size() == 3) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the EmptyFileWithInfoData file.
		public void EmptyFileWithInfoData() throws DocumentException, IOException
		{
			File file[] = {new File("EmptyFileWithInfo.txt")};
			EmptyFileWithInfo = DataToArray.textToArray(file);
			emptyResult = (EmptyFileWithInfo.isEmpty()) ? true : false;
			barResult = (EmptyFileWithInfo.size() == 0) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the getter methods work for MoonlightSonata after conversion for the MoonlightSonata file.
		public void MSDataDecisionTable() throws DocumentException, IOException
		{
			File file[] = {new File("MoonlightSonata.txt")};
			MoonlightSonata = DataToArray.textToArray(file);
			emptyResult = (MoonlightSonata.isEmpty()) ? false : true;
			barResult = (MoonlightSonata.size() == 15) ? true : false;
			boolean spacing = (DataToArray.getSpacing() == 8.0) ;
			boolean row = (DataToArray.getTotalRowAmount() == 90) ;
			boolean barAmount = (DataToArray.getBarAmount() == 15) ;
			boolean column = (DataToArray.getMaxColumnAmount() == 53) ;
			boolean subtitle = (DataToArray.getsubTitle().equalsIgnoreCase("Default")) ;
			assertTrue(emptyResult && barResult && spacing && column && row  && barAmount && subtitle);
		}
		
		@Test //Testing boundary values at empty file construction.
		public void DefaultCreationBoundaryTests() throws DocumentException, IOException
		{
			File file[] = {new File("EmptyFile.txt")};
			EmptyFile = DataToArray.textToArray(file);
			emptyResult = (EmptyFile.isEmpty()) ? false : true;
			barResult = (EmptyFile.size() == 0) ? true : false;
			boolean spacing = (DataToArray.getSpacing() == 8.0) ;
			boolean row = (DataToArray.getTotalRowAmount() == 0) ;
			boolean barAmount = (DataToArray.getBarAmount() == 0) ;
			boolean subtitle = (DataToArray.getsubTitle().equalsIgnoreCase("Default")) ;
			assertTrue(barResult  && !emptyResult && spacing && barAmount && row  && subtitle);
			
		}
}
