package KevinsWork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import com.itextpdf.text.DocumentException;

//Feb/2/15: I removed the making a PDF on this since it is not necessary.
//Feb/2/15: Got c = new char[6][col]; to work! Made other methods to send to BarLinesPDF.

public class DataToArray 
{
	
	private static char[][] c;
	private static ArrayList<String> lines = new ArrayList<String>();
	private static ArrayList<char[][]> chars = new ArrayList<char[][]>();
	public static String textFile = "Test.txt";
	
	private static int col;
	
	public static int getMaxColumnAmount()
	{
		return DataToArray.col;
	}
	
	public static int getTotalRowAmount()
	{
		return DataToArray.lines.size();
	}
	
	public static int getBarAmount()
	{
		return DataToArray.lines.size()/6;
	}
	
	public static ArrayList<char[][]> textToArray() throws DocumentException, IOException 
	{
		BufferedReader input = null;
		input = new BufferedReader (new FileReader(textFile));
		String line = "";	
		while(null != (line = input.readLine()))
		{	
			if (line.trim().length() == 0)
			{
				continue;
			}
				
			lines.add(line);			
		}	
		
		/*	System.out.println(lines.size());	
			System.out.println(lines.get(0).length());
			Read the lines from the array list put them into 2d array list

			the c = new char[6][col] works.
		 */
		for(int z = 0; z < lines.size(); z = z + 6)
		{
			col = lines.get(z).length();
			c = new char[6][col];
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
			
		System.out.println("Done. TxtToPdf successfully got the txt to an array. \n");
		input.close();
		return chars;
	}
}

    
   