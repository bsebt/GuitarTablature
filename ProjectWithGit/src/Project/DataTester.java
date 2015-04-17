package Project;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
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
			emptyResult = (EmptyFile.getChars().isEmpty()) ? true : false;
			System.out.println(EmptyFile.getChars().size());
			barResult = (EmptyFile.getChars().size() == 0) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the MoonlightSonata file.
		public void MoonlightSonataData() throws DocumentException, IOException
		{
			File file[] = {new File("MoonlightSonata.txt")};
			DataToArray MoonlightSonata = new DataToArray(file);
			emptyResult = (MoonlightSonata.getChars().isEmpty()) ? false : true;
			System.out.println(MoonlightSonata.getChars().size());
			barResult = (MoonlightSonata.getChars().size() == 69) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the IncompleteBar file.
		public void IncompleteBarData() throws DocumentException, IOException
		{
			File file[] = {new File("IncompleteBar.txt")};
			DataToArray IncompleteBar = new DataToArray(file);
			emptyResult = (IncompleteBar.getChars().isEmpty()) ? false : true;
			barResult = (IncompleteBar.getChars().size() == 2) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the RememberingRain file.
		public void RememberingRainData() throws DocumentException, IOException
		{
			File file[] = {new File("RememberingRain.txt")};
			DataToArray RememberingRain = new DataToArray(file);
			emptyResult = (RememberingRain.getChars().isEmpty()) ? false : true;
			System.out.println(RememberingRain.getChars().size());
			barResult = (RememberingRain.getChars().size() == 32) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test
		public void UnevenLinesData() throws DocumentException, IOException
		{
			File file[] = {new File("UnevenLines.txt")};
			DataToArray UnevenLines = new DataToArray(file);
			emptyResult = (UnevenLines.getChars().isEmpty()) ? false : true;
			System.out.println(UnevenLines.getChars().size());
			barResult = (UnevenLines.getChars().size() == 2) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the Prose file.
		public void ProseData() throws DocumentException, IOException
		{
			File file[] = {new File("Prose.txt")};
			DataToArray ProseFile = new DataToArray(file);
			emptyResult = (ProseFile.getChars().isEmpty()) ? true : false;
			System.out.println(ProseFile.getChars().size());
			barResult = (ProseFile.getChars().size() == 0) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the ExtendedASCII file.
		public void ExtendedASCIIData() throws DocumentException, IOException
		{
			File file[] = {new File("ExtendedASCII.txt")};
			DataToArray ExtendedASCII = new DataToArray(file);
			emptyResult = (ExtendedASCII.getChars().isEmpty()) ? true : false;
			System.out.println(ExtendedASCII.getChars().size());
			barResult = (ExtendedASCII.getChars().size() == 0) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the EmptyFileWithInfoData file.
		public void EmptyFileWithInfoData() throws DocumentException, IOException
		{
			File file[] = {new File("EmptyFileWithInfo.txt")};
			DataToArray EmptyFileWithInfo = new DataToArray(file);
			emptyResult = (EmptyFileWithInfo.getChars().isEmpty()) ? true : false;
			System.out.println(EmptyFileWithInfo.getChars().size());
			barResult = (EmptyFileWithInfo.getChars().size() == 0) ? true : false;
			assertTrue(emptyResult && barResult);
		}
}
