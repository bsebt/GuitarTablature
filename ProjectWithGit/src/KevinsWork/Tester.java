package KevinsWork;


import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import com.itextpdf.text.DocumentException;

public class Tester
{
	private BufferedReader reader;
	private Boolean result;
	private Boolean textFileOpen;
	
		@Before
		public void setUp() throws DocumentException, IOException
		{
			result = (DataToArray.textToArray() != null);
		}
		@Test
		public void canMakePDF()
		{
			assertTrue(result);
		}
		@Test
		public void notNullTextDestination()
		{
			assertFalse(DataToArray.textFile.equals(null));
		}
		@Test
		public void notNullPDFDestination()
		{
			assertFalse(BarLinesPDF.DEST.equals(null));
		}
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
}