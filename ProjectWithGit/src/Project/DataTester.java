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
	
//Testing DataToArray objects ------------------------------------------------------------------
		
		@Test //Tests to see if the conversion works for an empty file.
		public void EmptyFileData() throws DocumentException, IOException
		{
			File file[] = {new File("EmptyFile.txt")};
			DataToArray EmptyFile = new DataToArray(file);
			emptyResult = (EmptyFile.getLines().isEmpty()) ? true : false;
			barResult = (EmptyFile.getLines().size() == 0) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the MoonlightSonata file.
		public void MoonlightSonataData() throws DocumentException, IOException
		{
			File file[] = {new File("MoonlightSonata.txt")};
			DataToArray MoonlightSonata = new DataToArray(file);
			emptyResult = (MoonlightSonata.getLines().isEmpty()) ? true : false;
			barResult = (MoonlightSonata.getLines().size() == 35) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		//Tests to see if the conversion works for the IncompleteBar file.
		public void IncompleteBarData() throws DocumentException, IOException
		{
			File file[] = {new File("IncompleteBar.txt")};
			DataToArray IncompleteBar = new DataToArray(file);
			emptyResult = (IncompleteBar.getLines().isEmpty()) ? false : true;
			barResult = (IncompleteBar.getLines().size() == 0) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the RememberingRain file.
		public void RememberingRainData() throws DocumentException, IOException
		{
			File file[] = {new File("RememberingRain.txt")};
			DataToArray RememberingRain = new DataToArray(file);
			emptyResult = (RememberingRain.getLines().isEmpty()) ? false : true;
			barResult = (RememberingRain.getLines().size() == 15) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		public void UnevenLinesData() throws DocumentException, IOException
		{
			File file[] = {new File("UnevenLines.txt")};
			DataToArray UnevenLines = new DataToArray(file);
			emptyResult = (UnevenLines.getLines().isEmpty()) ? false : true;
			barResult = (UnevenLines.getLines().size() == 1) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the Prose file.
		public void ProseData() throws DocumentException, IOException
		{
			File file[] = {new File("Prose.txt")};
			DataToArray ProseFile = new DataToArray(file);
			emptyResult = (ProseFile.getLines().isEmpty()) ? false : true;
			barResult = (ProseFile.getLines().size() == 0) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the ExtendedASCII file.
		public void ExtendedASCIIData() throws DocumentException, IOException
		{
			File file[] = {new File("ExtendedASCII.txt")};
			DataToArray ExtendedASCII = new DataToArray(file);
			emptyResult = (ExtendedASCII.getLines().isEmpty()) ? false : true;
			barResult = (ExtendedASCII.getLines().size() == 0) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the EmptyFileWithInfoData file.
		public void EmptyFileWithInfoData() throws DocumentException, IOException
		{
			File file[] = {new File("EmptyFileWithInfo.txt")};
			DataToArray EmptyFileWithInfo = new DataToArray(file);
			emptyResult = (EmptyFileWithInfo.getLines().isEmpty()) ? true : false;
			barResult = (EmptyFileWithInfo.getLines().size() == 0) ? true : false;
			assertTrue(emptyResult && barResult);
		}
//		@Test //Tests to see if the getter methods work for MoonlightSonata after conversion for the MoonlightSonata file.
//		public void MSDataDecisionTable() throws DocumentException, IOException
//		{
//			File file[] = {new File("MoonlightSonata.txt")};
//			MoonlightSonata = DataToArray.textToArray(file);
//			emptyResult = (MoonlightSonata.isEmpty()) ? false : true;
//			barResult = (MoonlightSonata.size() == 15) ? true : false;
//			boolean spacing = (DataToArray.getSpacing() == 8.0) ;
//			boolean row = (DataToArray.getTotalRowAmount() == 90) ;
//			boolean barAmount = (DataToArray.getBarAmount() == 15) ;
//			boolean column = (DataToArray.getMaxColumnAmount() == 53) ;
//			boolean subtitle = (DataToArray.getsubTitle().equalsIgnoreCase("Default")) ;
//			assertTrue(emptyResult && barResult && spacing && column && row  && barAmount && subtitle);
//		}
//		
//		@Test //Testing boundary values at empty file construction.
//		public void DefaultCreationBoundaryTests() throws DocumentException, IOException
//		{
//			File file[] = {new File("EmptyFile.txt")};
//			EmptyFile = DataToArray.textToArray(file);
//			emptyResult = (EmptyFile.isEmpty()) ? false : true;
//			barResult = (EmptyFile.size() == 0) ? true : false;
//			boolean spacing = (DataToArray.getSpacing() == 8.0) ;
//			boolean row = (DataToArray.getTotalRowAmount() == 0) ;
//			boolean barAmount = (DataToArray.getBarAmount() == 0) ;
//			boolean subtitle = (DataToArray.getsubTitle().equalsIgnoreCase("Default")) ;
//			assertTrue(barResult  && !emptyResult && spacing && barAmount && row  && subtitle);			
//		}
}
