package app;

import fxml.FXMLFileException;
import fxml.FXMLFileLoader;
import javafx.scene.layout.AnchorPane;

/**
 * Controls all aspects of the application.
 * @author jacobwatson
 * @since 08/06/2019
 */
public class PROJECT_TEMPLATE_controller extends AnchorPane {
	
	
	/** Instantiates a new controller. */
	public PROJECT_TEMPLATE_controller() {
		super();
		
		try {
			FXMLFileLoader.initFXMLfor(this);
		}
		catch (final FXMLFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}