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
			barResult = (EmptyFile.getChars().size() == 0) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the MoonlightSonata file.
		public void MoonlightSonataData() throws DocumentException, IOException
		{
			File file[] = {new File("MoonlightSonata.txt")};
			DataToArray MoonlightSonata = new DataToArray(file);
			emptyResult = (MoonlightSonata.getChars().isEmpty()) ? false : true;
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
			barResult = (RememberingRain.getChars().size() == 32) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the UnevenLines file.
		public void UnevenLinesData() throws DocumentException, IOException
		{
			File file[] = {new File("UnevenLines.txt")};
			DataToArray UnevenLines = new DataToArray(file);
			emptyResult = (UnevenLines.getChars().isEmpty()) ? false : true;
			barResult = (UnevenLines.getChars().size() == 2) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the Prose file.
		public void ProseData() throws DocumentException, IOException
		{
			File file[] = {new File("Prose.txt")};
			DataToArray ProseFile = new DataToArray(file);
			emptyResult = (ProseFile.getChars().isEmpty()) ? true : false;
			barResult = (ProseFile.getChars().size() == 0) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the ExtendedASCII file.
		public void ExtendedASCIIData() throws DocumentException, IOException
		{
			File file[] = {new File("ExtendedASCII.txt")};
			DataToArray ExtendedASCII = new DataToArray(file);
			emptyResult = (ExtendedASCII.getChars().isEmpty()) ? true : false;
			barResult = (ExtendedASCII.getChars().size() == 0) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the elnegrito file.
		public void elnegritoData() throws DocumentException, IOException
		{
			File file[] = {new File("elnegrito.txt")};
			DataToArray ElNegrito = new DataToArray(file);
			emptyResult = (ElNegrito.getChars().isEmpty()) ? false : true;
			barResult = (ElNegrito.getChars().size() == 51) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the bohemianrhapsody file.
		public void bohemianrhapsodyData() throws DocumentException, IOException
		{
			File file[] = {new File("bohemianrhapsody.txt")};
			DataToArray BohemianRhapsody = new DataToArray(file);
			emptyResult = (BohemianRhapsody.getChars().isEmpty()) ? false : true;
			barResult = (BohemianRhapsody.getChars().size() == 115) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the extrawhitespaces file.
		public void ExtraWhiteSpacesData() throws DocumentException, IOException
		{
			File file[] = {new File("ExtraWhiteSpaces.txt")};
			DataToArray ExtraWhiteSpaces = new DataToArray(file);
			emptyResult = (ExtraWhiteSpaces.getChars().isEmpty()) ? false : true;
			barResult = (ExtraWhiteSpaces.getChars().size() == 2) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the GarbageInLine file.
		public void GarbageInLineData() throws DocumentException, IOException
		{
			File file[] = {new File("GarbageInLine.txt")};
			DataToArray GarbageInLine = new DataToArray(file);
			emptyResult = (GarbageInLine.getChars().isEmpty()) ? true : false;
			barResult = (GarbageInLine.getChars().size() == 0) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the StairwayToHeave file.
		public void StairwayToHeavenData() throws DocumentException, IOException
		{
			File file[] = {new File("StairwayToHeaven.txt")};
			DataToArray StairwayToHeaven = new DataToArray(file);
			emptyResult = (StairwayToHeaven.getChars().isEmpty()) ? false : true;
			barResult = (StairwayToHeaven.getChars().size() == 154) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the HowDeepIsYourLove file.
		public void HowDeepIsYourLoveData() throws DocumentException, IOException
		{
			File file[] = {new File("HowDeepIsYourLove.txt")};
			DataToArray HowDeepIsYourLove = new DataToArray(file);
			emptyResult = (HowDeepIsYourLove.getChars().isEmpty()) ? false : true;
			barResult = (HowDeepIsYourLove.getChars().size() == 88) ? true : false;
			assertTrue(emptyResult && barResult);
		}
		@Test //Tests to see if the conversion works for the HeyJude file.
		public void HeyJudeData() throws DocumentException, IOException
		{
			File file[] = {new File("HeyJude.txt")};
			DataToArray HeyJude = new DataToArray(file);
			emptyResult = (HeyJude.getChars().isEmpty()) ? false : true;
			barResult = (HeyJude.getChars().size() == 82) ? true : false;
			assertTrue(emptyResult && barResult);
		}
}
