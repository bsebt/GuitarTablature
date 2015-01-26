//Tests the functionality of methods included in the PDF Writer
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class WriterTester 
{
	int GroupBarSpacing;
	int WhiteSpace;
	int BarSpacing;
	int GivenSpacing;
	int FontSize;
	@Before 
	public void setup()
	{
	GroupBarSpacing = 100;
	WhiteSpace = 1;
	BarSpacing = 10;
	GivenSpacing = 20;
	FontSize = 8;
	}
	
	@Test
	public void testChangeGroupBarSpacing()
	{
		assertTrue(BarLinesPDF.SetGroupBarSpacing(10));
		//Check to see if there are any numbers in this column
	}
	
	@Test
	public void testChangeWhiteSpace()
	{
		assertTrue(BarLinesPDF.SetWhiteSpace(4));
		//Check to see if we should draw a bar line
	}
	
	@Test
	public void testChangeBarSpacing()
	{
		assertTrue(BarLinesPDF.SetBarSpacing(12));
		//Check if there is enough space to write the next bar
	}
	
	@Test
	public void testChangeGivenSpacing()
	{
		assertTrue(BarLinesPDF.SetGivenSpacing(40));
		//Add another page
	}
	
	@Test
	public void testChangeNoteFontSize()
	{
		assertTrue(BarLinesPDF.SetNoteFontSize(4));
		//Change the font size
	}
}
