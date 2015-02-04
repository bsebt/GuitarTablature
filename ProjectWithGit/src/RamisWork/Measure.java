package RamisWork;

import java.util.ArrayList;

public  class Measure {
	private ArrayList<String> myLines;
	private String barType;
	private boolean repeat;
	private int repeatNum;
	private float spacing, width = 0;

	public Measure(float f) {
		spacing = f;
	}
	
	public Measure(String firstLine) {
		addLine(firstLine);
	}
	
	public boolean addLine(String line) {
		if(getLines().size() < 6){
			getLines().add(line);
				if(size() == 6)
					corrections();
			return true;
		}
		return false;
	}
	
	private void checkBarType() {
		char left = getLines().get(2).charAt(0);
		char right = getLines().get(2).charAt(getLines().get(2).length() - 1);
	
		if (left == right && right == '*') {
			barType = "Both";
			setWidth(6.6f);
		} else if (left != right && right == '*') {
			barType = "Right";
			setWidth(4.3f);
		} else if (left != right && left == '*') {
			barType = "Left";
			setWidth(4.3f);
		} else {
			barType = "Single";
			setWidth(.5f);
		}
	}
	
	private void checkLength() {
		int longestLength = -1 ;
		for (String s : getLines()) {
			if (longestLength == -1)
				longestLength = s.length();
			else if (longestLength > s.length())	
				longestLength = s.length();
		}
		
		ArrayList<String> temp = new  ArrayList<String>() ;
		for (String s : getLines()) {
			if(s.charAt(s.length() - 1) == '+')
				temp.add(s.substring(0, longestLength) + "+");
			else
				temp.add(s.substring(0, longestLength));
		}
		setMyLines(temp);
	}

	private boolean checkRepeat() {
		StringBuffer temp = new StringBuffer(getLines().get(0));
		if(temp.length() < 2){
			setRepeat(false);
			return false;
		}
		if (((temp.length()!= 0) && (temp.toString().charAt(temp.length() - 2) == '+'))) {
			setRepeat(true);
			if (((temp.toString().charAt(temp.length() - 1) > 47) && (temp.toString().charAt(temp.length() - 1) < 58))) {
				setRepeatNum(Integer.parseInt(temp.substring(temp.length() - 1)));
				getLines().remove(0);
				getLines().add(0, temp.substring(0, temp.length() - 1).toString());
			}
		} 
		else 
			setRepeat(false);
		return true;
	}

	private void corrections() {
		checkRepeat();
		checkBarType();
		checkLength();//doesn't work perfectly
		setWidth(getLines().get(0).length() * spacing);
	}
	
	public String getBarType() {
		return barType;
	}

	public ArrayList<String> getLines() {
		if (myLines == null) //Lazy initialization
			myLines = new ArrayList<String>();
		return myLines;
	}

	public int getRepeatNum() {
		return repeatNum;
	}
	
	public float getSpacing() {
		return spacing;
	}

	public float getWidth() {
		return width;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void printLines() {
		System.out.println(barType);
		for (String myStr : getLines())
			   System.out.println(myStr);
		System.out.println();
	}
	
	private void recalculateWidth() {
		if ("Both".equals(getBarType()))
			width = 6.6f;	
		else if ("Right".equals(getBarType())) 
			width = 4.3f;
		else if ("Left".equals(getBarType()))
			width = 4.3f;
		else
			width = .5f;
		setWidth(getLines().get(0).length() * spacing);
	}

	public void setBarType(String userBarType) {
		barType = userBarType;
	}
	
	public void setMyLines(ArrayList<String> userLines) {
		myLines = userLines;
	}

	public void setRepeat(boolean userRepeat) {
		repeat = userRepeat;
	}

	public void setRepeatNum(int userRepeatNum) {
		repeatNum = userRepeatNum;
	}

	public void setSpacing(float userSpacing) {
		spacing = userSpacing;
		recalculateWidth();
	}

	private void setWidth(float userWidth) {
		width += userWidth;
	}

	public int size() {
		return getLines().size();
	}
}
