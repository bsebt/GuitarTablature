package RamisWork;

import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;

public class Style {
    public BaseFont myFontface;
	private int myFontSize = 8, myTitleSize = 24, mySubTitleSize = 16;
	private float distMeasures = 30f, distLines = 7f;
	float leftMargin = 36f, rightMargin = 36f;

	public Style() {
		try {
			myFontface = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, false);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getFontSize() {
		return myFontSize;
	}
	
	public float getHeight() {
		return (myFontface.getFontDescriptor(BaseFont.ASCENT, myFontSize) - myFontface
				.getFontDescriptor(BaseFont.DESCENT, myFontSize));
	}
	
	public float getLeftMargin() {
		return leftMargin;
    }
        
    public float getLineDistance() {
		return distLines;
	}
	
	public int getMySubTitleSize() {
		return mySubTitleSize;
	}
        
    public int getMyTitleSize() {
		return myTitleSize;
	}
	
	public float getPrintSpace() {
		return 595f - leftMargin - rightMargin;
	}
	
    public float getRightMargin() {
		return rightMargin;
    }
	
	public float getSectionDistance() {
		return distMeasures;
	}
    
	public float getWidth(char char1) {
		return myFontface.getWidthPoint(char1, myFontSize);
	}
	
	public void setFontSize(int userSize) {
		myFontSize = userSize;
	}
	
	public void setLeftMargin(float userSize) {
		 leftMargin = userSize;
	}
	
	public void setLineDistance(float userDistance) {
		distLines = userDistance;
	}
	
	public void setMeasureDistance(float userDistance) {
		distMeasures = userDistance;
	}
	
	public void setMySubTitleSize(int userSize) {
		mySubTitleSize = userSize;
	}

	public void setMyTitleSize(int userSize) {
    	myTitleSize = userSize;
	}

	public void setRightMargin(float userSize) {
		 rightMargin = userSize;
	}
}
