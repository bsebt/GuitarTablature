package RamisWork;

import java.util.ArrayList;

public class Staff {
	private ArrayList<StringBuffer> myLines;
	private float printSpace, width;
	private ArrayList<Integer>  repeatNum;

	public Staff(float printSpace) {
		setPrintSpace(printSpace);
	}

	public void addStringBuffer() {
		getLines().add(new StringBuffer());
	}

	public void addToStaff(Measure m) {
		appendToLines(m);
		if (m.isRepeat())
			getrepeatNum().add((m.getRepeatNum()));
		setWidth(m.getWidth());
	}

	private void appendToLines(Measure m) {
		int i = 0;
		for (String line : m.getLines()) {
			if (!(getLines().size() <= i))
				getLines().get(i).append(barsAdded(m.getBarType(),line));
			else {
				addStringBuffer();
				getLines().get(i).append(barsAdded(m.getBarType(),line));
			}
			i++;
		}
		fixBars();
	}
	
	private String barsAdded(String bar, String line) {
		String single = "|";
		String left = "D-|";
		String right = "|-D";
	
		if("Both".equals(bar))
			line = left + line + right;
		if("Right".equals(bar))
			line = single + line + right;
		if("Left".equals(bar))
			line = left + line + single;
		if("Single".equals(bar))
			line = single + line + single;
		
		return line;
	}
	
	public boolean canFitAnother(Measure m) {
		return getWidth() + m.getWidth() < getPrintSpace();
	}

	private void fixBars() {
		for(int i = 0; i < getLines().size(); i++) {
			String line = getLines().get(i).toString();
			getLines().remove(i);
			myLines.add(i, fixedLine(line));
		}
	}

	private StringBuffer fixedLine(String line) {
		line = line.replace("T", "|-|-|");
		line = line.replace("DD", "D");
        line = line.replace("|D", "D");
        line = line.replace("D|", "D");
		line = line.replace("||", "|");
		return new StringBuffer(line);
	}

	public ArrayList<StringBuffer> getLines() {
		if (myLines == null)// Lazy initialization
			myLines = new ArrayList<StringBuffer>();
		return myLines;
	}

	public float getPrintSpace() {
		return printSpace;
	}

	public ArrayList<Integer> getrepeatNum() {
		if (repeatNum == null) // Lazy initialization
				repeatNum = new ArrayList<Integer>();
		return repeatNum;
	}	

	public int getTopInt() {
		int i =-1;
		if(getrepeatNum()!= null && !getrepeatNum().isEmpty() ){
			i = getrepeatNum().get(0);
			getrepeatNum().remove(0);
		}
		return i;
	}

	public float getWidth() {
		return width;
	}

	public void printLines() {
		for (StringBuffer m : myLines) {
			System.out.println(m.toString());
		}
		System.out.println();
	}

	private void setPrintSpace(float userPrintSpace) {
		printSpace = userPrintSpace;
	}

	public void setrepeatNum(ArrayList<Integer> userNum) {
		repeatNum = userNum;
	}

	private void setWidth(float userWidth) {
		width += userWidth;
	}
}
