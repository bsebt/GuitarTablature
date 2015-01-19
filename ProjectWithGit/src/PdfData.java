import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;



public class PdfData
{
	private static Scanner file;
	private static File input;
	private static int lines;
	private static String[] lineInput;
	private static String fileName = "Test2.pdf";
	
	public static void main(String[] args) throws IOException, FileNotFoundException
	{
		input = new File("Test.txt");
		file = new Scanner(input);
		BufferedReader reader = new BufferedReader(new FileReader(input));
		while (reader.readLine() != null)
			lines++;
		reader.close();
		lineInput = new String[lines];
		for (int i = 0; i < lines; i++)
		{
			lineInput[i] = file.nextLine();
			System.out.println(lineInput[i]);
		}
		
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

    
   
