package RamisWork;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfOutputCreator {
	int fontSize = 8;
	float spacing;
	
    String fileTitle;
	File outputLocation, temporary;
	PdfWriter write;
	Style s;
	
	public PdfOutputCreator() {	}

	private void createBezierCurves(PdfContentByte draw, float x0, float y0, float x1, char c) {
            draw.moveTo(x0 + s.getWidth(c), y0 +s.getHeight() / 2); //Start Point
            float third = ((x1+spacing)-(x0 + s.getWidth(c))) / 3.0f;
            draw.curveTo( // Control Point 1, Control Point 2, End Point
                    x0 + s.getWidth(c) + third, y0 + s.getHeight() / 1.5f, // Control Point 1
                    x0 + s.getWidth(c) + (2 * third), y0 + s.getHeight() / 1.5f, // Control Point 2
                    x1 + spacing, y0 + s.getHeight() / 2); //End Point
	}
	
	private void drawCircle(float currX, float currY, PdfContentByte draw) {
		currX += 1.7f;
		draw.circle(currX + (this.spacing - 3f) / 2, currY, 1.5f);
		draw.setColorFill(BaseColor.BLACK);
		draw.fillStroke();
	}

	private void drawDiagonal(float currX, float currY, PdfContentByte draw) {
		draw.moveTo(currX + 2.5f, currY - 2f);
		draw.lineTo(currX+spacing - 0.5f, currY + 2f);
		draw.stroke();

	}

	private void drawDiamond(float currX, float currY, PdfContentByte draw) {
		currY += 1.7f;
		currX = currX + 2.4f;
		draw.moveTo(currX + 0.175f, currY + 0.175f);
		draw.lineTo(currX - 1.93f, currY - 1.93f);
		draw.stroke();
		
		draw.moveTo(currX, currY);
		draw.lineTo(currX + 1.93f, currY - 1.93f);
		draw.stroke();
		
		currY = currY - (3.5f);
		draw.moveTo(currX - 0.175f, currY - 0.175f);
		draw.lineTo(currX + 1.93f, currY + 1.93f);
		draw.stroke();
		
		draw.moveTo(currX, currY);
		draw.lineTo(currX - 1.93f, currY + 1.93f);
		draw.stroke();
		currY -= 1.7f;
	}

	private void drawHorLine(float currX, float currY, float toX, PdfContentByte draw) {
		draw.setLineWidth(.2f);
		draw.moveTo(currX, currY);
		draw.lineTo(currX + toX, currY);
		draw.stroke();
		draw.setLineWidth(.5f);
	}

	private void drawThick(float currX, float currY, float toY, PdfContentByte draw) {
		draw.setLineWidth(2.2f);
		draw.moveTo(currX, currY);
		draw.lineTo(currX, currY + toY);
		draw.stroke();
	}

	private void drawVerLine(float currX, float currY, float toY, PdfContentByte draw) {
		draw.setLineWidth(.5f);
		draw.moveTo(currX + 1.5f, currY);
		draw.lineTo(currX + 1.5f, currY + toY);
		draw.stroke();
	}

	public void makePDF(Tablature userTab, Style userStyle) throws IOException, DocumentException {
		s = userStyle;
		
		fontSize = userStyle.getFontSize();
		this.spacing = userTab.getSpacing();
		Document document = new Document(PageSize.A4);
		FileOutputStream documentStream;
		temporary = new File("temp.pdf");
		
		write = PdfWriter.getInstance(document, documentStream = new FileOutputStream(temporary));
		document.open();
		write.open();
		PdfContentByte draw = write.getDirectContent();

		float locationX = 0.0f;
		float locationY = document.top();
		float lastWordX = locationX; // location of last printed number/letter for arc
		float lastWordY = locationY;
        locationY -= printTitle(userTab.getTitle(), userTab.getSubtitle(), document);
               
		
				
		//temporary.deleteOnExit();
		document.close();
		write.close();
		documentStream.close();
	}

	
	
}