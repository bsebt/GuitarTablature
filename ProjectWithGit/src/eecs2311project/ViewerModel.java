package eecs2311project;

import java.awt.MediaTracker;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class ViewerModel {
	private static final ImageIcon EMPTY_IMAGE = new ImageIcon();

	private int currentImage;          // the index in this.images for the current image
	private List<ImageIcon> images;    // the images

	/**
	 * Creates a model having zero images.
	 */
	public ViewerModel() {
	
		this.currentImage = -1;
		this.images = new ArrayList<ImageIcon>();
	
		
	}

	/**
	 * Get the current image in the model. Returns an empty image if the model has
	 * no images.
	 * 
	 * @return the current image if the model has one or more images, or an empty
	 *         image otherwise
	 */
	public ImageIcon getCurrentImage() {
	
		if (this.currentImage <0){
			return ViewerModel.EMPTY_IMAGE;
		}else{
			return this.images.get(this.currentImage);
		}
	}

	/**
	 * Get the next image in the model and makes the next image the current image.
	 * Returns the current image if the current image is the last image in the
	 * model. Returns an empty image if the model has no images.
	 * 
	 * @return the next image in the model
	 */
	public ImageIcon getNextImage() {
	
		if (this.currentImage <0){
			return ViewerModel.EMPTY_IMAGE;
		}else if (this.currentImage == this.images.size() - 1){
			return this.images.get(this.currentImage);
		}else {
			this.currentImage = this.currentImage + 1;
			return this.images.get(this.currentImage);		
		}

	}

	/**
	 * Get the previous image in the model and makes the previous image the
	 * current image. Returns the current image if the current image is the first
	 * image in the model. Returns an empty image if the model has no images.
	 * 
	 * @return the previous image in the model
	 */
	public ImageIcon getPreviousImage() {

		if (this.currentImage < 0){
			return EMPTY_IMAGE;
		}else if (this.currentImage == 0){
			return this.images.get(this.currentImage);
		}else {
			this.currentImage = this.currentImage -1;
			return this.images.get(this.currentImage);		
		}


	}

	/**
	 * Clear the images held by the model.
	 */
	public void clearImages() {

		this.currentImage = -1;
		this.images = new ArrayList<ImageIcon>();

	}
	
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

	/**
	 * Read an array of image files into the model. The images are added to the
	 * end of the sequence of images in the model. Image file formats supported
	 * include JPEG, PNG, and GIF.
	 * 
	 * @param files
	 *          an array of <code>File</code> references to open
	 */
	public void openImages(File[] files) {
		List<ImageIcon> icons = new ArrayList<ImageIcon>();
		for (File f : files) {
			ImageIcon icon = new ImageIcon(f.getAbsolutePath());
			if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
				icons.add(icon);
			}
		}
		if (this.currentImage < 0 && icons.size() > 0) {
			this.currentImage = 0;
		}
		this.images.addAll(icons);
	}
}

