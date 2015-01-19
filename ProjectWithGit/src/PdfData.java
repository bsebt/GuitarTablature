import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;



public class PdfData
{
	private static Scanner file;
	private static File input;
	private static int lineNumber;
	private static String[] line;
	private static char[][] chars;
	
	private static final String fileName = "Test2.pdf";
	
	public static void main(String[] args) throws IOException, FileNotFoundException
	{
		//Reads into the file and gets the number of lines, creates an array to store all the String lines.
		input = new File("Test.txt");
		file = new Scanner(input);
		BufferedReader reader = new BufferedReader(new FileReader(input));
		while (reader.readLine() != null)
			lineNumber++;
		reader.close();
		line = new String[lineNumber];
		for (int i = 0; i < lineNumber; i++)
		{
			line[i] = file.nextLine(); //line 4 is the start of the music, ends at lineNumber-1
		}
		// charAt goes from 0 to line[x].length() - 1
		for(int i = 0; i < lineNumber; i++)
		{
			for(int j = 0; j < line[i].length(); j++)
			{
				chars[i][j] = line[i].charAt(j);
			}
		}
		
		//Testing:
		System.out.println(line[4]);
		System.out.println(line[lineNumber-2]);
		System.out.println(line[4].length());
		System.out.println(line[4].charAt(52));
		
		//Convert each line into a 2D array of characters seperated in columns and rows
		
		/* Create the pdf here
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
		document.open();
		for(int i = 0; i < lines; i++)
		{
			if(i % 4 == 0)
			{
				document.add(new Paragraph("\n"));
			}
			document.add(new Paragraph(lineInput[i]));
		}
		document.close();
		*/

	}
}

    
   
