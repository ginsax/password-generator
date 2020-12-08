package app;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import common.Constants;
import common.alphabet.Alphabet;
import exception.FXMLFileException;
import fxml.FXMLFileLoader;
import javafx.animation.PauseTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

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
	private TextFlow mTextFlow_Phonetic;
	
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
		
		mTextFlow_Phonetic.getChildren().addAll(new Text(System.lineSeparator()),
		                                        new Text(System.lineSeparator()),
		                                        new Text(System.lineSeparator()));
	}
	
	@FXML
	private void generatePassword() {
		// These are default characters
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
			final String chars_Special = "!$%&*-.?@_";
			
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
		
		final String password = passwordBuilder.toString();
		
		mOutput_GeneratedPassword.setText(password);
		
		setPhoneticPassword(password);
	}
	
	private void setPhoneticPassword(final String password) {
		mTextFlow_Phonetic.getChildren().clear();
		
		final String delimiter = "-";
		final String phoneticPassword = Alphabet.toPhoneticString(password,
		                                                          delimiter);
		
		final String[] phoneticPasswordComponents = phoneticPassword.split(Pattern.quote(delimiter));
		
		for (int i = 0; i < phoneticPasswordComponents.length; ++i) {
			final Text text = new Text(phoneticPasswordComponents[i].toLowerCase());
			text.setId("font-italic");
			
			// If character is uppercase
			final String characterAtIndex = new String(password.charAt(i) + Constants.NullString);
			if (!Objects.equals(characterAtIndex,
			                    characterAtIndex.toLowerCase())) {
				text.setText(text.getText().toUpperCase());
			}
			
			mTextFlow_Phonetic.getChildren().add(text);
			
			if (i < phoneticPasswordComponents.length - 1) {
				mTextFlow_Phonetic.getChildren().add(new Text(delimiter));
			}
		}
		
		adjustTextFlowHeight();
	}
	
	private void adjustTextFlowHeight() {
		final DoubleProperty phoneticHeight = new SimpleDoubleProperty();
		
		phoneticHeight.set(mTextFlow_Phonetic.getParent().getLayoutBounds().getHeight());
		
		final PauseTransition pauseTransition = new PauseTransition(Duration.millis(1));
		
		pauseTransition.setOnFinished(e -> {
			mTextFlow_Phonetic.getChildren().add(new Text(System.lineSeparator()));
			
			phoneticHeight.set(mTextFlow_Phonetic.getParent().getLayoutBounds().getHeight());
			
			if (phoneticHeight.get() <= 45.0) {
				pauseTransition.play();
			}
			
			if (phoneticHeight.get() > 45.0 ) {
				mTextFlow_Phonetic.getChildren().remove(mTextFlow_Phonetic.getChildren().size() - 1);
			}
		});
		
		pauseTransition.play();
		
	}
	
	@FXML
	private void copyToClipboard() {
		final String passwordValue = mOutput_GeneratedPassword.getText();
		
		final StringSelection clipboard = new StringSelection(passwordValue);
		
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipboard,
		                                                             null);
	}
}