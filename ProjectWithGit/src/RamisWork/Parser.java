package RamisWork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.DocumentException;

public class Parser {
	
	String logPath;
	FileHandler fh;
	Logger logger;
	Boolean logEmpty = true;
    
	public Parser() { }

	
	
	public Tablature readFile(File file) throws FileNotFoundException {  
    	long currentTime = System.currentTimeMillis();
        logger = Logger.getLogger("MyLog");
        try { // This block configure the logger with handler and formatter  
            logger.addHandler(fh = new FileHandler("logs/MyLogFile " + currentTime + ".log"));
            fh.setFormatter(new SimpleFormatter());
        } catch (SecurityException | IOException e) { }
        
		Scanner s = new Scanner(file);
		Tablature returnTab = new Tablature();
        int i = 0;
        
		return returnTab;
                            
         
		}
		
	


	
	
	
	public static void main(String[] args)
	    	throws DocumentException, IOException {
			Tablature test = new Parser().readFile(new File("Test.txt"));
			
	    	new PdfOutputCreator().makePDF(test, new Style());
	    	
	    }
}