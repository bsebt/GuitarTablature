package eecs2311project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import com.itextpdf.text.DocumentException;


public class ViewerController implements ActionListener {
	/**
	 * String for the action command for opening files.
	 */
	public static final String OPEN = "OPEN";

	/**
	 * String for the action command for clearing all of the images.
	 */
	public static final String CLEAR = "CLEAR";

	/**
	 * String for the action command for showing the previous image.
	 */
	public static final String PREVIOUS = "PREVIOUS";

	/**
	 * String for the action command for showing the next image.
	 */
	public static final String NEXT = "NEXT";
	
	public static final String CONVERT = "CONVERT";

	private ViewerModel model;
	private ViewerView view;


	/**
	 * Creates a controller having no model and no view.
	 */
	public ViewerController() {
	}

	/**
	 * Sets the model for the controller.
	 * 
	 * @param model the viewer model the controller should use
	 */
	public void setModel(ViewerModel model) {
		this.model = model;
	}

	/**
	 * Sets the view for the controller.
	 * 
	 * @param view the view the controller should use
	 */
	public void setView(ViewerView view) {
		this.view = view;
	}

	/**
	 * The method that responds to events emitted by the view components.
	 * 
	 * @param event an event emitted by the view
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event)
	{
	
		if (event.getActionCommand() == OPEN){
			File [] f = view.getFilesToOpen();
			model.openImages(f);
			view.setImage(model.getCurrentImage());
		}else if (event.getActionCommand() == CLEAR){
			model.clearImages();
			view.setImage(model.getCurrentImage());
		}else if (event.getActionCommand() == CONVERT){
			try {
				model.createPdf();
			}catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		
		/*else if (event.getActionCommand() == PREVIOUS){
			view.setImage(model.getPreviousImage());
		}else if (event.getActionCommand() == NEXT){
			view.setImage(model.getNextImage());
		}*/

	}


}

