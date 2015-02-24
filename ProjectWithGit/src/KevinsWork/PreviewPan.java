package KevinsWork;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFPrintPage;

/**
 * This class allows for the creation of a JScrollPane that displays a PDF
 * @author
 */
public class PreviewPan extends JScrollPane {
	private static final long serialVersionUID = 1L;
	private static final int defaultZoom = 100;
	private JPanel livePreview;
	private PDFFile pdfFile;
	private File fileInView;
	
	/**
     * Creates the PDF preview panel, which acts as a JComponent
     * @param file - PDF file to be previewed
     */	 
	public PreviewPan(File file) {
		fileInView = file;

    	livePreview = new JPanel();
    	livePreview.setLayout(new BoxLayout(livePreview, BoxLayout.Y_AXIS));
    	
    	JPanel livePreviewContainer = new JPanel(new GridBagLayout());
    	livePreviewContainer.add(livePreview);
    	this.setViewportView(livePreviewContainer);
    	refresh(defaultZoom);
    	
    	this.revalidate();
    	this.repaint();
	}
 
	
	
	
	public void refresh(int zoomLevel) {
		RandomAccessFile raf;
		ByteBuffer buf;
		PDFPage page;
		double width, height;
		ArrayList<Image> image = new ArrayList<Image>();
		ArrayList<JLabel> pageLabel = new ArrayList<JLabel>();

		try {
			raf = new RandomAccessFile (fileInView, "r");
			byte[] b = new byte[(int) raf.length()];
			raf.read(b);
			buf = ByteBuffer.wrap(b);
			pdfFile = new PDFFile(buf);
			
			for (int i = 1; i <= pdfFile.getNumPages(); i++) {
				page = pdfFile.getPage(i);
			    Rectangle2D rectangle = page.getBBox();
			    width = rectangle.getWidth();
			    height = rectangle.getHeight();
			    width /= 72.0;
			    height /= 72.0;
			    int res = Toolkit.getDefaultToolkit().getScreenResolution ();
			    width *= res;
			    height *= res;
			    
			    double realZoomLevel = 100 / (double) zoomLevel;
			    image.add(page.getImage ((int)(width/realZoomLevel), (int)(height/realZoomLevel), rectangle, null, true, true));
			}
		    buf.clear();
		    raf.close();
		} catch (IOException e1) { }
		
		if (image.size() > 0) {
			for (Image tempImage: image) 
				pageLabel.add(new JLabel(new ImageIcon(tempImage)));
		}

    	livePreview.setLayout(new BoxLayout(livePreview, BoxLayout.Y_AXIS));
    	
    	if (pageLabel != null) {
    		livePreview.removeAll();
        	for (JLabel temp: pageLabel) {
        		livePreview.add(temp);
    		}
		}
    	
    	this.revalidate();
    	this.repaint();
	}
}
