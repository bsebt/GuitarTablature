package Tester;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class TextToArray 
{
	private static ArrayList<String> lines = new ArrayList<String>();
	private static BufferedReader input;
	private static Document output;
	private static int columnSize;
	private static int rowSize;
	private static char[][] data;
	
	public static void main (String args[]) throws DocumentException, IOException
	{
		input = new BufferedReader (new FileReader("Test.txt"));	//The txt file
		output = new Document();
		PdfWriter.getInstance(output, new FileOutputStream("Test.pdf")); //The pdf title
    	
		output.open(); //Open the document
			
		String line = "";	
		while(null != (line = input.readLine()))	//While the line has an input
		{
			if (line.trim().length() == 0)
			{
				continue; //If the line is empty, just keep going to the next one
			}
				
			//Add the line to the array list
			lines.add(line);			
			Paragraph p = new Paragraph(line);
			output.add(p);
		}
		rowSize = lines.size();
		columnSize = lines.get(0).length();
		
		data = new char[rowSize][columnSize];
		for(int i = 0; i < rowSize; i ++)
		{
			for(int j = 0; j < columnSize; j++)
			{
				data[i][j] = lines.get(i).charAt(j);
				System.out.print(data[i][j]);
			}
			System.out.println();
		}
		
		
			
	}
}
