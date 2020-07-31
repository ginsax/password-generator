package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PasswordGeneratorApp extends Application {
	
	
	@Override
	public void start(final Stage stage) throws Exception {
		final Controller controller = new Controller();
		final Scene scene = new Scene(controller);
		
		stage.setScene(scene);
		stage.setTitle("Password Generator");
		
		stage.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth() / 3);
		stage.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight() / 3);
		
		stage.show();
		stage.centerOnScreen();
	}
	
	public static void main(final String[] args) {
		Application.launch(args);
	}
}