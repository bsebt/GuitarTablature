package Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import com.itextpdf.text.DocumentException;

public class DataToArray {

	public static char[][] c;
	public static ArrayList<String> lines = new ArrayList<String>();
	public static ArrayList<char[][]> chars = new ArrayList<char[][]>();
	public static ArrayList<Integer> partitionLength = new ArrayList<Integer>();
	public static String textFile = "MoonlightSonata.txt";
	public static String Title = "NO TITLE";
	public static String SubTitle = "NO SUBTITLE";
	public static float Spacing = 8.0f;
	public static String correctLine = "^( |[0-9]|\\|)([0-9]|<|>|s|h|x|\\||\\*|\\-|p| |\\^|g|\\[|\\]|\\(|\\)|\\=|\\\\|\\/|S)+([0-9]|\\|)";
	public static String starter = "([0-9]| |\\|)";

	private static ArrayList<char[][]> newchars = new ArrayList<char[][]>();

	private static int col;

//	public static ArrayList<char[][]> TrimElement(ArrayList<char[][]> uncutArray) 
//	{
//		int actualLength = 0;
//		ArrayList<char[][]> newCharList = new ArrayList<>();
//		char[][] newElement = 
//		for (int p = 0; p < chars.size(); p++) {
//			for (int j=0;j<chars.get(p).length; j++){
//				for(int i =0; i< chars.get(p)[j].length;i++)
//					if()
//					System.out.println((chars.get(p))[j][i]);
//			}
//		}
//	}

	//		
	//		for (int a = 0; a < element[0].length; a++) // Go through the full
	//		{
	//			if (element[a][a] != '\u0000') {
	//				actualLength++;
	//			}
	//			// System.out.println(a);
	//		}
	//
	//		
	//		for (int b = 0; b < 6; b++) {
	//			int actualColumn = 0;
	//			for (int a = 0; a < element[0].length; a++) {
	//				if (element[b][a] != '\u0000') {
	//					// System.out.println("a: " + a + " b: " + b + " Ac: " +
	//					// actualColumn);
	//					newestElement[b][actualColumn] = element[b][a];
	//					actualColumn++;
	//				}
	//			}
	//		}
//	return newestElement;
//}


public static ArrayList<char[][]> textToArray(File[] source)
		throws DocumentException, IOException {
	lines = new ArrayList<String>();
	chars = new ArrayList<char[][]>();
	newchars = new ArrayList<char[][]>();
	partitionLength = new ArrayList<Integer>();
	String name = null;
	chars.clear();
	lines.clear();
	Title = " ";
	SubTitle = " "; 
	BufferedReader input = null;
	String line = "";
	for (int i = 0; i < source.length; i++) {
		name = source[i].getPath();
		input = new BufferedReader(new FileReader(name));
		while (null != (line = input.readLine())) {
			line = line.trim();
			if (line.contains("SUBTITLE")) {
				SubTitle = line.substring(line.indexOf('=') + 1,
						line.length());
			} else if (line.contains("TITLE")) {
				Title = line.substring(line.indexOf('=') + 1, line.length());
			}
			else if (line.contains("SPACING")) {
				Spacing = Float.parseFloat(line.substring(
						line.indexOf('=') + 1, line.length()));
			}
			else if (line.trim().length() == 0) {
				continue;
			}
			else if (line.substring(0, line.lastIndexOf('|') + 1).matches(correctLine) && !line.substring(0, line.lastIndexOf('|') + 1).contains("  |")) {
				if((line.charAt(0)+"").matches("[0-9]")){
					line = line.replace(line.charAt(0), '|');
				}
				try{
					lines.add(line.substring(0, line.lastIndexOf('|') + 2));
					//System.out.println(line.substring(0, line.lastIndexOf('|') + 2));
				}catch(StringIndexOutOfBoundsException e){
					lines.add(line.substring(0, line.lastIndexOf('|') + 1));
					//System.out.println(line.substring(0, line.lastIndexOf('|') + 1));
				}
			}else{
				//System.out.println("ignore: "+line);
			}
		}
		input.close();
	}
	
	ArrayList<String>lines1 = new ArrayList<String>();
	for(int i=0; i<lines.size() ; i++){
		StringBuffer adder = new StringBuffer();
		for(int j=0;j<lines.get(i).length();j++){
			if(lines.get(i).charAt(j) != ' '){
				adder.append((lines.get(i).charAt(j))+"");
			}
		}
		lines1.add(adder.toString().trim());
		System.out.println(lines1.get(i));
	}
	
	int temp = 0;
	for (int z = 0; z < lines1.size(); z = z + 6) {
		if(lines1.size()-temp >= 6)
		{c = new char[6][];}else{
			c = new char[lines1.size()-temp][];}
		//temp++;
		for (int i = 0; i < 6 && temp < lines1.size(); i++, temp++) {
			c[i] = new char[lines1.get(temp).length()];
			for (int j = 0; j < lines1.get(temp).length(); j++) {
				c[i][j] = lines1.get(temp).charAt(j);
				//System.out.println(c[i][j]);
			}
		}
		chars.add(c);
	}
	
//	for (int t = 0; t < chars.size(); t++) // Check every element in the
//		// cars and split them up as
//		// needed
//	{
//		boolean alreadyBottomed = true;
//		char[][] d = new char[6][100]; // Make it as long
//		// as the old
//		// element, and
//		// we'll trim it
//		// later
//		int w=0;
//		for (int v = 0; v < chars.get(t)[w].length; v++) // Read every
//			// column
//		{
//
//			for (w=w ;w < chars.get(t).length; w++) // Then read every row
//			{	
//				
//				char currentChar = chars.get(t)[w][v];
//				//System.out.println(chars.get(t)[w].length);
//
//				 System.out.println("w: " + w + " v: " + v);
//				d[w][v] = currentChar;
//				if (w == 5 && currentChar == '|' && !alreadyBottomed) 
//				{
//					v--; // The last column should be printed in twice, so
//					// back up one and do this column again
//					newchars.add(d); // Add the new element to the list
//					d = new char[6][chars.get(t)[w].length]; // Reset the
//					// array we
//					// are
//					// writing
//					// so it is
//					// blank
//					alreadyBottomed = true;
//				}
//				if (w == 5 && currentChar != '|' && alreadyBottomed) 
//				{
//					alreadyBottomed = false;
//				}
//			}
//		}
//		/*
//			if (alreadyBottomed)
//			{
//				newchars.add(d);
//				d = new char[6][chars.get(t)[0].length];
//			}
//		 */
//	}
//
	//		ArrayList<char[][]> finalChars = new ArrayList<char[][]>();
	//
	//		for (int p = 0; p < newchars.size(); p++) {
	//			finalChars.add(TrimElement(newchars.get(p))); // Trim every element
	//			// to remove extra
	//			// white space, all
	//			// elements should
	//			// now only contain
	//			// characters and be
	//			// the proper length
	//		}

	// Test to see printed lines
	for (int i = 0; i < chars.size(); i++) {
		//System.out.println(chars.get(i));
	}

	// Test to see if characters properly placed in 2-d array.
	for (int i = 0; i < newchars.size(); i++) {
		// System.out.println(Arrays.deepToString(newchars.get(i)));
	}

	return chars;
}


public static void LengthOfPartition() {
	int length;
	String temp;
	for (int i = 0; i < lines.size(); i++) {
		String line = lines.get(i);
		StringTokenizer StrTkn = new StringTokenizer(line, "|");
		// System.out.println("NUMBER OF TOKENS = " + StrTkn.countTokens());
		while (StrTkn.hasMoreTokens()) {
			temp = StrTkn.nextToken();
			length = temp.length();
			partitionLength.add(length);
		}
	}
}

// Gets the left bars and everything else not including the right bars
public static String DanielsPartition(String line) {
	for (int i = 0; i < line.length(); i++) {
		if (line.charAt(i) == '|') {
			if (i == 0 || i == 1) {
			} else {
				line = line.substring(0, i);
			}
		}
	}
	// System.out.println(line);
	return line;
}

public static ArrayList<String> DanielsPartition2(ArrayList<String> data) {
	ArrayList<String> partition = data;
	for (int i = 0; i < partition.size(); i++) {
		String line = DanielsPartition(partition.get(i));
		partition.set(i, line);
	}
	for (int j = 0; j < partition.size(); j++) {
		// System.out.println(partition.get(j));
	}
	return partition;
}

public static int getMaxColumnAmount() {
	return DataToArray.col;
}

public static int getTotalRowAmount() {
	return DataToArray.lines.size();
}

public static int getBarAmount() {
	return DataToArray.lines.size() / 6;
}

public static String getTitle() {
	return Title;
}

public static String getsubTitle() {
	return SubTitle;
}

public static float getSpacing() {
	return Spacing;
}

public static void main(String[] args) throws DocumentException,
IOException {
	File[] source1 = new File[1];
	File source = new File("/home/behshad/Desktop/El Negrito.txt");
	File source2 = new File("/home/behshad/Desktop/MoonlightSonata.txt");
	File source3 = new File("/home/behshad/Desktop/Bohemian Rhapsody.txt");
	File source4 = new File("WhitespaceInLine.txt");
	source1[0]= source4;
	DataToArray.textToArray(source1);
	// textToArray();
	// DataToArray.textToArray(DataToArray.textFile);
	// LengthOfPartition();
	// DanielsPartition2(lines);
}
}
