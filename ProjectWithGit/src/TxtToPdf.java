import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class TxtToPdf {
	
	private static char[][] c;
	private static ArrayList<String> lines = new ArrayList<String>();
	private static ArrayList<char[][]> chars = new ArrayList<char[][]>();
	
	
	public void createPdf()
			throws DocumentException, IOException {
		BufferedReader input = null;
		Document output = null;
    
		try{
			input = new BufferedReader (new FileReader("Test.txt"));
			output = new Document();
			PdfWriter.getInstance(output, new FileOutputStream("Test.pdf"));
    	
			output.open();
			
			
			String line = "";	
			while(null != (line = input.readLine())){
				
				
				if (line.trim().length() == 0)
				{
					continue;
				}
				
				lines.add(line);			
				Paragraph p = new Paragraph(line);
				output.add(p);
			}
			
			
			
			System.out.println(lines.size());
			
			System.out.println(lines.get(0).length());
			
			
			//Read the lines from the array list put them into 2d array list
			for(int z = 0; z<lines.size(); z = z + 6)
			{
				int col = lines.get(z).length();
				//System.out.println(col);
				//c = new char[6][col];
				c = new char[6][53];  /* NOT SURE WHY VARIABLE DECLERATION (ie using col) WONT WORK NEED TO FIX*/
				int temp = z;
				for (int i = 0; i<6; i++, temp++)
				{
					
					//System.out.println(temp);
					for(int j = 0; j< lines.get(temp).length(); j++)
					{
						c[i][j] = lines.get(temp).charAt(j);
						//System.out.println("i = " + i + " temp = " + temp + " j = " + j);
						//System.out.println(j);
					}					
				}
				chars.add(c);
			}	
			
			//Test to see printed lines
			for (int i = 0; i<lines.size(); i++)
			{
				System.out.println(lines.get(i));
			}
			
			//Test to see if characters properly placed in 2-d array.
			for (int i = 0; i<chars.size(); i++)
			{
				System.out.println(Arrays.deepToString(chars.get(i)));
			}
			
			System.out.println("Done.");
			output.close();
			input.close();
			System.exit(0);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String[] args)
	    	throws DocumentException, IOException {
	    	new TxtToPdf().createPdf();
	    }
}

    
   
