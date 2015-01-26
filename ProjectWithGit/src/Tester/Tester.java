package Tester;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.DocumentException;

public class Tester 
{
	private TxtToPdf t;
	private BufferedReader reader;
	private Boolean result;
	private Boolean textFileOpen;
	
	@Before
	public void setUp() throws DocumentException, IOException
	{
		t = new TxtToPdf();
		result = t.createPdf();
	}
	@Test
	public void canMakePDF()
	{
		assertTrue(result);
	}
	@Test
	public void notNullTextDestination()
	{
		assertFalse(t.getTextFile().equals(null));
	}
	@Test
	public void notNullPDFDestination()
	{
		assertFalse(t.getPDFFile().equals(null));
	}
	@Test
	public void notEmptyTextFile() throws IOException
	{
		reader = new BufferedReader(new FileReader(t.getTextFile()));
		if(reader.readLine() == null)
		{
			textFileOpen = true;
		}
		else
			textFileOpen = false;
		assertFalse(textFileOpen);
	}
}