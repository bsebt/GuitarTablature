import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class TxtToPdf {
	
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
				System.out.println(line);
				Paragraph p = new Paragraph(line);
				output.add(p);
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

    
   
