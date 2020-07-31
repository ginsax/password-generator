package app;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import resource.Directory_Style;

/**
 * Main start point of the application.
 * @author jacobwatson
 * @since 08/06/2019
 */
public class PROJECT_TEMPLATE_app extends Application {
	
	/**
	 * Start.
	 * @param stage the stage
	 */
	@Override
	public void start(final Stage stage) {
		final PROJECT_TEMPLATE_controller root = new PROJECT_TEMPLATE_controller();
		final Scene scene = new Scene(root);
		scene.getStylesheets().add(Directory_Style.Style_Default);
		
		stage.setTitle("PROJECT_TEMPLATE_title");
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
		
		final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		stage.setWidth(screenBounds.getWidth() / 2);
		stage.setHeight(screenBounds.getHeight() / 2);
		
	}
	
	/**
	 * The main method. Launches the application with the given arguments.
	 * @param args String arguments that modify how the application runs..
	 */
	public static void main(final String[] args) {
		Application.launch(args);
	}
}