package app;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import fxml.FXMLFileException;
import fxml.FXMLFileLoader;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

class Controller extends AnchorPane {
	
	@FXML
	private CheckBox	mCheckbox_Uppercase;
	@FXML
	private CheckBox	mCheckbox_Digits;
	@FXML
	private CheckBox	mCheckbox_SpecialCharacters;
	@FXML
	private CheckBox	mCheckbox_CustomCharacters;
	
	@FXML
	private TextField	mInput_CustomCharacters;
	@FXML
	private TextField	mOutput_GeneratedPassword;
	
	@FXML
	private Slider mPasswordLengthSlider;
	
	
	public Controller() {
		super();
		
		try {
			FXMLFileLoader.initFXMLfor(this);
		}
		catch (FXMLFileException e) {
			e.printStackTrace();
			System.err.println(e.getStackTrace());
		}
	}
	
	@FXML
	private void generatePassword() {
		final String chars_Lowercase = "abcdefghijklmnopqrstuvwxyz";
		
		final StringBuilder availableCharactersBuilder = new StringBuilder();
		availableCharactersBuilder.append(chars_Lowercase);
		
		// Add uppercase, if selected
		if (mCheckbox_Uppercase.isSelected()) {
			final String chars_Uppercase = chars_Lowercase.toUpperCase();
			
			availableCharactersBuilder.append(chars_Uppercase);
		}
		
		// Add digits, if selected
		if (mCheckbox_Digits.isSelected()) {
			final String chars_Digits = "0123456789";
			
			availableCharactersBuilder.append(chars_Digits);
		}
		
		// Add special characters, if selected
		if (mCheckbox_SpecialCharacters.isSelected()) {
			final String chars_Special = "_.-!@*$?&%";
			
			availableCharactersBuilder.append(chars_Special);
		}
		
		// Add custom characters, if selected
		if (mCheckbox_CustomCharacters.isSelected()) {
			final String chars_Custom = mInput_CustomCharacters.getText();
			
			availableCharactersBuilder.append(chars_Custom);
		}
		
		// Use a set to maintain uniqueness
		final Set<Character> passwordPool = new LinkedHashSet<Character>();
		
		final String availableCharacters = availableCharactersBuilder.toString();
		
		for (char c : availableCharacters.toCharArray()) {
			passwordPool.add(c);
		}
		
		final StringBuilder passwordBuilder = new StringBuilder();
		
		final int passwordLength = mPasswordLengthSlider.valueProperty().intValue();
		
		Character[] pool = new Character[] { };
		pool = passwordPool.toArray(pool);
		
		for (int i = 0; i < passwordLength; i++) {
			final int index = new Random().nextInt(passwordPool.size());
			
			final char nextCharacter = pool[index];
			
			passwordBuilder.append(nextCharacter);
		}
		
		mOutput_GeneratedPassword.setText(passwordBuilder.toString());
	}
	
	@FXML
	private void copyToClipboard() {
		final String passwordValue = mOutput_GeneratedPassword.getText();
		
		final StringSelection clipboard = new StringSelection(passwordValue);
		
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipboard,
		                                                             null);
	}
}