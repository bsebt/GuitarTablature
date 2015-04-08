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
	public static char[][] c1;
	public static ArrayList<String> lines = new ArrayList<String>();
	public static ArrayList<char[][]> chars = new ArrayList<char[][]>();
	public static ArrayList<Integer> partitionLength = new ArrayList<Integer>();
	public static String textFile = "MoonlightSonata.txt";
	public static String Title = "NO TITLE";
	public static String SubTitle = "NO SUBTITLE";
	public static float Spacing = 8.0f;
	public static String correctLine = "^( |[0-9]|\\||[A-Z])([0-9a-zA-Z]|<|>|s|h|x|\\||\\*|\\-|p| |\\^|g|\\[|\\]|\\(|\\)|\\=|\\\\|\\/|S)+( |[0-9a-zA-Z]|\\|)";
	//public static String starter = "([0-9]| |\\|)";

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
				else if(line.trim().length() < 5){ //new 
					continue;
				}
				//				else if (line.charAt(0) == ' '){ //new
				//					continue;
				//				}
				//	line.trim().substring(0, line.lastIndexOf('|') + 1).matches(correctLine) && !line.trim().substring(0, line.lastIndexOf('|') + 1).contains("  |")
				else if (line.matches(correctLine)) {

					if((line.charAt(0)+"").matches("[0-9a-zA-Z]")){ 
						//line = line.replace(line.charAt(0), '|');
						line = "|" + line.substring(1);
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
//		for(int i = 0; i < lines.size(); i++)
//		{	if(i%6 == 0){
//			//System.out.println();
//		}
//		System.out.println(lines.get(i));
//		}
		lines = ProperLines(lines);
		for(int i = 0; i < lines.size(); i++)
		{	if(i%6 == 0){
			System.out.println();
		}
		System.out.println(lines.get(i));
		}
		lines = whiteSpaceRemover(lines);

		try{
			lines = addDummyLines(lines, lines.get(lines.size()-1));
		}catch(IndexOutOfBoundsException e){
			System.out.println("empty file");
		}
		lines = changingNumToPipe(lines);
		lines = sizeCutter(lines);
		lines = Partitioning(lines);
		

//		for(int i = 0; i < lines.size(); i++)
//		{	if(i%6 == 0){
//			System.out.println();
//		}
//		System.out.println(lines.get(i));
//		}

		int temp = 0;
		for (int z = 0; z < lines.size(); z = z + 6) {
			if(lines.size()-temp >= 6)
			{
				c = new char[6][];
			}
			//			else
			//			{
			//				c = new char[lines2.size()-temp][];
			//			}
			//temp++;
			for (int i = 0; i < 6 && temp < lines.size(); i++, temp++) 
			{
				c[i] = new char[lines.get(temp).length()];

				for (int j = 0; j < lines.get(temp).length(); j++)
				{
					c[i][j] = lines.get(temp).charAt(j);
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
		//		for (int i = 0; i < chars.size(); i++) {
		//			System.out.println(Arrays.deepToString(chars.get(i)));
		//		}
		//	
		// Test to see if characters properly placed in 2-d array.
		for (int i = 0; i < newchars.size(); i++) {
			//] System.out.println(Arrays.deepToString(newchars.get(i)));
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
		File file[] = {new File("ExtraWhiteSpaces.txt")};
		File file2[] = {new File("GarbageInLine.txt")};
		File file3[] = {new File("elnegrito.txt")};
		File file4[] = {new File("UnevenLines.txt")};
		File file5[] = {new File("bohemianrhapsody.txt")};
		
		DataToArray.textToArray(file3);
	}
	private static ArrayList<String> addDummyLines(ArrayList<String> list, String lastLine)
	{
		String dummy = "";
		if(list.size() % 6 != 0)
		{
			dummy = lastLine;
			//System.out.println("Dummy is " + dummy);
			for(int i = 1; i < dummy.length() - 1; i++)
			{
				if(dummy.charAt(i) != '-' && dummy.charAt(i) != '|')
				{
					dummy = dummy.replace(dummy.charAt(i), '-');
				}
			}
			dummy = "|" + dummy.substring(1, dummy.length()-1) + "|";
			//System.out.println("Dummy is " + dummy);
		}
		while(list.size() % 6 != 0)
		{
			list.add(dummy);
		}
		return list;
	}
	private static ArrayList<String> ProperLines(ArrayList<String> lines){
		ArrayList<String> a = new ArrayList<String>();
		boolean pack =true;
		try{
			for(int i=0;i<lines.size();i++){
				pack =true;
				if(lines.get(i).length() == lines.get(i+1).length()){
					for(int j=i;j < i+6;j++){
						if(!(lines.get(i).trim().length() == lines.get(j).trim().length())){
							pack = false;
							//System.out.println("fail= "+lines.get(j));
							//System.out.println("pack= "+pack);
							break;
						}else{
							//System.out.println("pass= "+lines.get(j));
						}
					}
					if(pack){
						for(int z=i;z<i+6;z++){
							a.add(lines.get(z));
						}i=i+5;
					}else{
						continue;
					}
				}else{
					//System.out.println("first fail");
					continue;
				}
			}
		}catch(IndexOutOfBoundsException e){
			return a;
		}
		return a;
	}
	public static ArrayList<String> whiteSpaceRemover(ArrayList<String> list){
		ArrayList<String> lines1 = new ArrayList<String>();
		for(int i=0; i<list.size() ; i++){
			StringBuffer adder = new StringBuffer();
			for(int j=0;j<list.get(i).length();j++){
				if(list.get(i).charAt(j) != ' '){
					adder.append((list.get(i).charAt(j))+"");
				}
			}
			lines1.add(adder.toString().trim());
			//	System.out.println(adder.toString().trim());
		}
		return lines1;
	}
	public static ArrayList<String> changingNumToPipe(ArrayList<String> lines){
		for(int i=0; i<lines.size() ; i=i+6){
			//	System.out.println(i);
			for(int j=0;j<lines.get(i).length();j++){
				if((lines.get(i).charAt(j)+"").matches("[0-9]")){
					if(lines.get(i).charAt(j-1) == '|' && lines.get(i+1).charAt(j) == '|'){
						//System.out.println("ok");
					}else if(lines.get(i+1).charAt(j) == '|'){
						lines.set(i, lines.get(i).replaceFirst(lines.get(i).charAt(j)+"", "|"));
					}		
				}
			}
		}
		return lines;
	}
	public static ArrayList<String> sizeCutter(ArrayList<String> lines1){
		for(int i=0; i<lines1.size() ; i=i+6){
			int min=lines1.get(i).length();
			if(!((lines1.get(i).length() == lines1.get(i+1).length()) && (lines1.get(i+2).length() == lines1.get(i+3).length()) && (lines1.get(i+4).length()==lines1.get(i+5).length()))){
				for(int j=i;j < i+6;j++){
					if(lines1.get(j).length()<min)
						min = lines1.get(j).length();
				}
				for(int z=i;z <i+6 ;z++){
					lines1.set(z, lines1.get(z).substring(0, min));
					if(lines1.get(z).charAt(lines1.get(z).length()-1) != '|'){
						lines1.set(z, lines1.get(z).substring(0, min-1)+"|");
					}
					System.out.println(lines1.get(z).substring(0, min));
				}
			}	
		}
		return lines1;
	}
	public static ArrayList<String> Partitioning(ArrayList<String> lines1){
		ArrayList<String> lines2 = new ArrayList<String>();
		for(int i=0;i<lines1.size();i++){
			//			System.out.println(lines1.get(i).indexOf('|', 2));
			//			System.out.println(lines1.get(i).lastIndexOf('|'));
			//			System.out.println(lines1.get(i).indexOf('|', 3));
			//			System.out.println(lines1.get(i).lastIndexOf('|')-1);
			//			System.out.println(lines1.get(i).length());
			while((lines1.get(i).indexOf('|', 2)) != (lines1.get(i).length()-1) && (lines1.get(i).indexOf('|', 2))!= (lines1.get(i).length()-2)){
				for(int j=i;j<i+6;j++){
					//System.out.println(lines1.get(j).substring(0,lines1.get(j).indexOf('|', 2)) + "|");
					lines2.add(lines1.get(j).substring(0,lines1.get(j).indexOf('|', 2)) + "|");
					lines1.set(j, lines1.get(j).substring(lines1.get(j).indexOf('|', lines1.get(j).indexOf('|', 2))));
				}
			}
			lines2.add(lines1.get(i));
		}
		return lines2;
	}


	public static int getLargestNumber(char[][] list)
	{
		int max=0;
		for(int i=0; i<list.length;i++)
			if(list[i].length>max)
				max = list[i].length;
		return max;
	}
}
