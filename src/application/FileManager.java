package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class FileManager {

	public static String openFile(Stage stage)
	{
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT","*.txt"));//Widzi tylko .mp3
        File file  = fileChooser.showOpenDialog(stage);
        if(file!=null)
        return readFile(file);
        else
        return null;
    }
	
	public static void saveFile(Stage stage, String data)
	{
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");
        
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT","*.txt"));//Widzi tylko .mp3
        File file  = fileChooser.showSaveDialog(stage);
        
        if(file != null){
            try {
                FileWriter fileWriter = null;
                fileWriter = new FileWriter(file);
                fileWriter.write(data);
                fileWriter.close();
            } catch (IOException ex) {
                
            }
        }
	}
	
	private static String readFile(File file){
		if(file==null)
		{
			return null;
		}
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
         
        try {
 
            bufferedReader = new BufferedReader(new FileReader(file));
             
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }
 
        } catch (FileNotFoundException ex) {
            
        } catch (IOException ex) {
            
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                
            }
        } 
         
        return stringBuffer.toString();
    }
	
	
	
	
}
