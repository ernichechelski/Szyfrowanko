package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import application.EncryptionStringProperty;
public class ViewController {
	
	EncryptionStringProperty text;
	
	
	@FXML
	public void initialize(){
		System.out.println("initialize");
		text = new EncryptionStringProperty(mainTextArea.getText());
		text.bindBidirectional(mainTextArea.textProperty());
		text.getKey().bind(keyTextField.textProperty());
		text.getInitVector().bind(initVectorTextField.textProperty());
		
		keyTextField.setMaxWidth(150);
		initVectorTextField.setMaxWidth(150);
		
		
    	keyTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
    		if (!newValue) 
    		{ //when focus lost
    			if(keyTextField.getText().toString().length()>=16){
    				keyTextField.setText(keyTextField.getText().toString().substring(0, 16));
            }
        }

    	});
    	
    	initVectorTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
	        if (!newValue) { //when focus lost
	            if(keyTextField.getText().toString().length()>=16){
	               	keyTextField.setText(keyTextField.getText().toString().substring(0, 16));
	            }
	        }

	    });
	
		
		
	}

	@FXML
    private SplitMenuButton encryptionTypeButton;

    @FXML
    private TextField keyTextField;
    
    @FXML
    private TextField initVectorTextField;

    @FXML
    private TextArea mainTextArea;

    @FXML
    void textChanged(ActionEvent event) {
    	//text.set(mainTextArea.getText());
    }
    
    @FXML
    void decryptButtonClicked(ActionEvent event) {
    	try 
		{
			text.Decrypt();
		} 
		catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
				| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException e) {
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Błąd rozszyfrowania");
    		alert.setContentText("Dane błędu: \n"+ e.getMessage());
    		alert.show();
		}
		catch(IllegalStateException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Błąd rozszyfrowania");
    		alert.setContentText("Sprawdź wartości klucza i wektora\n"
    				+ "Muszą zawierać po 16 znaków"
    				+ "\nSprawdź też poprawność szyfru"
    				+ "\n(np. Brakujące znaki)");
    		alert.show();
		}
    	System.out.println("decryptButtonClicked");
    }

    @FXML
    void encryptButtonClicked(ActionEvent event) {

    	try 
		{
			text.Encrypt();
		} 
		catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
				| NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException e) {
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Błąd szyfrowania");
    		alert.setContentText("Dane błędu: \n"+ e.getMessage());
    		alert.show();
		}
		catch(IllegalStateException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Błąd szyfrowania");
    		alert.setContentText("Sprawdź wartości klucza i wektora\n"
    				+ "Muszą zawierać po 16 znaków\n");
    		alert.show();
		}
	
    	
    	
    	System.out.println("encryptButtonClicked");
    }

    @FXML
    void encryptionTypeAESButtonClicked(ActionEvent event) {
    	System.out.println("encryptionTypeAESButtonClicked");
    }

    @FXML
    void keyTypedTextField(ActionEvent event) {
    	System.out.println("keyTypedTextField");
    }

    @FXML
    void newFileButtonClicked(ActionEvent event) {
    	System.out.println("newFileButtonClicked");
    }

    @FXML
    void openFileButtonClicked(ActionEvent event) {
    	
    	String string = FileManager.openFile(Main.stage);
    		if(string!=null)text.set(string);
    	System.out.println("openFileButtonClicked");
    	
    }

    @FXML
    void optionsButtonClicked(ActionEvent event) {
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Szyfrowanko");
		alert.setHeaderText("Szyfrowanko");
		alert.setContentText("Prosty program do szyfrowania tekstu \nalgorytmem AES\n"
				+ "by Ernest Chechelski");
		alert.show();
    	System.out.println(text);
    	System.out.println("optionsButtonClicked");
    }

    @FXML
    void saveFileButtonClicked(ActionEvent event) {
    	FileManager.saveFile(Main.stage, text.get());
    	System.out.println("saveFileButtonClicked");
    }

    @FXML
    void vectorTypedTextField(ActionEvent event) {
    
    	System.out.println("vectorTypedTextField");
    }



	
	
}
