package eecs2311project;

import eecs2311project.ViewerController;
import eecs2311project.ViewerModel;
import eecs2311project.ViewerView;


public class ViewerApp {
	private ViewerApp() {

	}
	/**
	 * The entry point to the image viewer application. Creates the model,
	 * view, and controller, and makes the view visible.
	 * 
	 * @param args no used
	 */
	public static void main(String[] args) {
		ViewerModel model = new ViewerModel();
		ViewerController controller = new ViewerController();
		ViewerView view = new ViewerView(controller);
		controller.setModel(model);
		controller.setView(view);
		view.setVisible(true);
	}
}

