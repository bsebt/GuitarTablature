package Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.itextpdf.text.DocumentException;

public class DataToArray {
//FIELDS:
	private char[][] c;
	private ArrayList<String> lines;
	private ArrayList<char[][]> chars;
	private String title = "";
	private String subtitle = "";
	private float Spacing = 8.0f;
	private String correctLine = "^( |[0-9]|\\||[A-Z])([0-9a-zA-Z]|<|>|s|h|x|\\||\\*|\\-|p| |\\^|g|\\[|\\]|\\(|\\)|\\=|\\\\|\\/|S)+( |[0-9a-zA-Z]|\\|)";
	private int col;

//CONSTRUCTORS:
	public DataToArray(){
		this.title = "NO TITLE";
		this.subtitle = "NO SUBTITLE";
		this.Spacing = 8.0f;
	}

	public DataToArray(File[] source) throws DocumentException, IOException {
		lines = new ArrayList<String>();
		chars = new ArrayList<char[][]>();
		String name = null;
		BufferedReader input = null;
		String line = "";
		for (int i = 0; i < source.length; i++) {
			name = source[i].getPath();
			input = new BufferedReader(new FileReader(name));
			while (null != (line = input.readLine())) {
				line = line.trim();
				if (line.contains("SUBTITLE")) {
					this.setSubTitle(line.substring(line.indexOf('=') + 1, line.length()));
				} 
				else if (line.contains("TITLE")) {
					this.setTitle(line.substring(line.indexOf('=') + 1, line.length()));
				}
				else if (line.contains("SPACING")) {
					this.setSpacing(Float.parseFloat(line.substring(line.indexOf('=') + 1, line.length())));
				}
				else if (line.trim().length() == 0) {
					continue;
				}
				else if(line.trim().length() < 5){
					continue;
				}
				else if (line.matches(correctLine)) {

					if((line.charAt(0)+"").matches("[0-9]")){ 
						line = "|" + line.substring(1);
					}
					try{
						if(line.substring(0, line.lastIndexOf('|') + 2).length() > 2){
						lines.add(line.substring(0, line.lastIndexOf('|') + 2)); //making sure to add repeat bars
						}
					}
					catch(StringIndexOutOfBoundsException e){ //to catch the out of boundaries for lines that dont have repeat
						if(line.substring(0, line.lastIndexOf('|') + 1).length() > 2){
							lines.add(line.substring(0, line.lastIndexOf('|') + 1)); //making sure to add repeat bars
						}
					}
				}
			}
			input.close();
		}
		this.setLines(whiteSpaceRemover(lines)); // removes all the in line extra white spaces.
		try {
			this.setLines(addDummyLines(lines, lines.get(lines.size()-1))); // adds empty lines to the input only if input has any lines, makes number of
			this.setLines(ProperLines(lines));	
			this.setLines(sizeCutter(lines));		
			this.setLines(changingNumToPipe(lines));			
			this.setLines(Partitioning(lines));
			
		}
		catch(Exception e) {
			
		}
		
		this.setChars(addToChars(this.lines));
	}

	private ArrayList<char[][]> addToChars(ArrayList<String> lines) {
		int temp = 0;
		for (int z = 0; z < lines.size(); z = z + 6) {
			if(lines.size()-temp >= 6) {
				c = new char[6][];
			}
			for (int i = 0; i < 6 && temp < lines.size(); i++, temp++) {
				c[i] = new char[lines.get(temp).length()];
				for (int j = 0; j < lines.get(temp).length(); j++) {
					c[i][j] = lines.get(temp).charAt(j);
				}
			}
			chars.add(c);
		}
		return chars;
	}

//METHODS: ACCESSOR METHODS
	public ArrayList<String> getLines()
	{
		return this.lines;
	}
	public ArrayList<char[][]> getChars()
	{
		return this.chars;
	}
	public int getMaxColumnAmount() {
		return this.col;
	}

	public int getTotalRowAmount() {
		return this.lines.size();
	}

	public int getBarAmount() {
		return this.lines.size() / 6;
	}

	public String getTitle() {
		return this.title;
	}

	public String getsubTitle() {
		return this.subtitle;
	}

	public float getSpacing() {
		return this.Spacing;
	}

	public static int getLargestNumber(char[][] list)
	{
		int max=0;
		for(int i=0; i<list.length;i++)
			if(list[i].length>max)
				max = list[i].length;
		return max;
	}
//METHODS: MUTATOR METHODS
	public void setTitle(String s){
		this.title = s;
	}
	
	public void setSubTitle(String s){
		this.subtitle = s;
	}
	public void setSpacing(float spacing){
		this.Spacing = spacing;
	}
	public void setLines(ArrayList<String> newLines)
	{
		this.lines = newLines;
	}
	public void setChars(ArrayList<char[][]> newChars)
	{
		this.chars = newChars;
	}

//METHODS: PARTITIONING
	private static ArrayList<String> addDummyLines(ArrayList<String> list, String lastLine)
	{
		String dummy = "";
		if(list.size() % 6 != 0)
		{
			dummy = lastLine;
			for(int i = 1; i < dummy.length() - 1; i++)
			{
				if(dummy.charAt(i) != '-' && dummy.charAt(i) != '|')
				{
					dummy = dummy.replace(dummy.charAt(i), '-');
				}
			}
			dummy = "|" + dummy.substring(1, dummy.length()-1) + "|";
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
		for(int i=0;lines.size()-i>=6;i++){
				pack =true;
				String a1 = lines.get(i).trim().substring(0, lines.get(i).indexOf('|',2)+1);
				String a2 = lines.get(i+1).trim().substring(0, lines.get(i+1).indexOf('|',2)+1);
				if(a1.length() == a2.length()){
					for(int j=i;j < i+6;j++){
						a1 = lines.get(i).trim().substring(0, lines.get(i).indexOf('|',3)+1);
						a2 = lines.get(j).trim().substring(0, lines.get(j).indexOf('|',3)+1);
						if(!(a1.length() == a2.length())){
							pack = false;
							break;
						}
					}
					if(pack){
						for(int z=i;z<i+6;z++){
							a.add(lines.get(z));
						}
						i=i+5;
					}
					else{
						continue;
					}
				}
				else{
					continue;
				}
		}
		return a;
	}
	
	private static ArrayList<String> whiteSpaceRemover(ArrayList<String> list){
		ArrayList<String> lines1 = new ArrayList<String>();
		for(int i=0; i<list.size() ; i++){
			StringBuffer adder = new StringBuffer();
			for(int j=0;j<list.get(i).length();j++){
				if(list.get(i).charAt(j) != ' '){
					adder.append((list.get(i).charAt(j))+"");
				}
			}
			lines1.add(adder.toString().trim());
		}
		return lines1;
	}
	
	private static ArrayList<String> changingNumToPipe(ArrayList<String> lines){
		for(int i=0; i<lines.size() ; i=i+6){
			for(int j=0;j<lines.get(i).length();j++){
				if((lines.get(i).charAt(j)+"").matches("[0-9]")){
					if(lines.get(i).charAt(j-1) == '|' && lines.get(i+1).charAt(j) == '|'){
					}
					else if(lines.get(i+1).charAt(j) == '|'){
						lines.set(i, lines.get(i).replaceFirst(lines.get(i).charAt(j)+"", "|"));
					}		
				}
			}
		}
		return lines;
	}
	
	private static ArrayList<String> sizeCutter(ArrayList<String> lines1){
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
				}
			}	
		}
		return lines1;
	}
	
	private static ArrayList<String> Partitioning(ArrayList<String> lines1){
		ArrayList<String> lines2 = new ArrayList<String>();
		for(int i=0;i<lines1.size();i++){
			while((lines1.get(i).indexOf('|', 2)) != (lines1.get(i).length()-1) && (lines1.get(i).indexOf('|', 2))!= (lines1.get(i).length()-2)){
				for(int j=i;j<i+6;j++){
					lines2.add(lines1.get(j).substring(0,lines1.get(j).indexOf('|', 2)) + "|");
					lines1.set(j, lines1.get(j).substring(lines1.get(j).indexOf('|', lines1.get(j).indexOf('|', 2))));
				}
			}
			lines2.add(lines1.get(i));
		}
		return lines2;
	}
}
