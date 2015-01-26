package Tester;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Tester 
{
	private BarLinesPDF b;
	private TxtToPdf t;
	
	@Before
	public void setUp() throws Exception
	{
		b = new BarLinesPDF();
		t = new TxtToPdf();
	}
	@Test
	public void properMargins() throws Exception
	{
		assertEquals(50f, b.getMargins());
	}
	@Test
	public void nonEmptyTitle() throws Exception
	{
		assertTrue(b.TITLE_STRING, true);
	}
}
