package RamisWork;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Tablature {
	private ArrayList<Measure> myMeasure;
	private float mySpacing = 5;
	private String myTitle, mySubtitle;

	public Tablature() {
		this.mySubtitle = "Default";
		this.myTitle = "Default";
	}

	public void addLineToLastMeasure(String g) {
		if (!this.getMeasures().isEmpty() && this.getLastMeasure().size() != 6) {
			this.getLastMeasure().addLine(g);
		} else {
			this.addMeasure();
			this.addLineToLastMeasure(g);
		}
	}

	private void addLineToMeasure(int index, String inputLine) {
		if(this.getMeasures().size() >= index)
			this.getMeasures().get(getMeasures().size() - index).addLine(inputLine);
	}

	private void addMeasure() {
		this.getMeasures().add(new Measure(this.getSpacing()));
	}

	public void addMultiMeasureLine(StringTokenizer StrTkn) {
		if (this.getMeasures().size() < StrTkn.countTokens() || this.getLastMeasure().size() == 6) {
			while (StrTkn.hasMoreTokens()) {
				this.addMeasure();
				this.addLineToLastMeasure(StrTkn.nextToken());
			}
		} else {
			while (StrTkn.hasMoreTokens()) {
					this.addLineToMeasure(StrTkn.countTokens(), StrTkn.nextToken());
			}
		}
	}

	private Measure getLastMeasure() {
		if (!getMeasures().isEmpty())
			return getMeasures().get(getMeasures().size() - 1);
		else
			return null;
	}
	
	public ArrayList<Measure> getMeasures() {
		if (this.myMeasure == null) // Lazy initialization
			this.myMeasure = new ArrayList<Measure>();
		return myMeasure;
	}

	public float getSpacing() {
		return mySpacing;
	}

	public String getSubtitle() {
		return mySubtitle;
	}

	public String getTitle() {
		return myTitle;
	}

	public void printMeasures() {
		for (Measure m : this.getMeasures())
			m.printLines();
	}

	public void setMeasures(ArrayList<Measure> myMeasure) {
		this.myMeasure = myMeasure;
	}

	public void setSpacing(float mySpacing) {
		for(Measure m : this.getMeasures())
			m.setSpacing(mySpacing);
		this.mySpacing = mySpacing;
	}

	public void setSubtitle(String mySubtitle) {
		this.mySubtitle = mySubtitle;
	}

	public void setTitle(String myTitle) {
		this.myTitle = myTitle;
	}

	public int size() {
		return this.getMeasures().size();
	}
}
