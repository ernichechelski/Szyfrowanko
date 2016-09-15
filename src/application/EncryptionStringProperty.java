package application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;


import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


import org.apache.commons.codec.binary.Base64;



public class EncryptionStringProperty extends SimpleStringProperty
{
	SimpleStringProperty key;
	SimpleStringProperty initVector;
	SimpleBooleanProperty decrypted;
	
	
	public EncryptionStringProperty(){
		super();
		key = new SimpleStringProperty();
		initVector = new SimpleStringProperty();
		this.decrypted = new SimpleBooleanProperty(true);
	}
	public EncryptionStringProperty(String value){
		super(value);
		key = new SimpleStringProperty();
		initVector = new SimpleStringProperty();
		this.decrypted = new SimpleBooleanProperty(true);
	}
	
	public EncryptionStringProperty(String value, Boolean encrypted){
		super(value);
		key = new SimpleStringProperty();
		initVector = new SimpleStringProperty();
		this.decrypted = new SimpleBooleanProperty(encrypted);
	}
	
	
	
	public void Encrypt() throws IllegalStateException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException 
	{
		
		if(this.decrypted.get() && key!=null && initVector!=null)
		{
			System.out.println("This: "+ this.getValue());
		 	System.out.println("Key: "+ key.getValue());
		 	System.out.println("Vector: "+ initVector.getValue());
		
            IvParameterSpec iv = new IvParameterSpec(initVector.getValue().toString().getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getValue().toString().getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(this.getValue().getBytes());
            
            this.set(Base64.encodeBase64String(encrypted));
            System.out.println("encrypted string: "+ this.getValue());
        
		
		}
	
		else
		{
			throw new IllegalStateException("No key and initVector defined!");
		}
		decrypted.set(false);
	}
	
	public void Decrypt() throws IllegalStateException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException 
	{
		if(!this.decrypted.get()  && key!=null && initVector!=null)
		{
			System.out.println("This: "+ this.getValue());
		 	 System.out.println("Key: "+ key.getValue());
		 	 System.out.println("Vector: "+ initVector.getValue());
			
			
				IvParameterSpec iv = new IvParameterSpec(initVector.getValue().toString().getBytes("UTF-8"));
		        SecretKeySpec skeySpec = new SecretKeySpec(key.getValue().toString().getBytes("UTF-8"), "AES");
		
		        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		
		        byte[] original = cipher.doFinal(Base64.decodeBase64(this.getValue()));
		        this.set(new String(original));
		        System.out.println("decrypted string: "+ this.getValue());
		   
		}
			 
		else
		{
			throw new IllegalStateException();
		}
		decrypted.set(true);
	}
	
	public void clearKey(){
		this.key = null;
	}
	
	public void clearInitVector(){
		this.key = null;
	}
	
	/**
	 * @return the key
	 */
	public SimpleStringProperty getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(SimpleStringProperty key) 
	{
		if(key.toString().length()==16)
			this.key = key;
		else
			throw new IllegalArgumentException("16 chars expected.");
	}
	
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) 
	{
		if(key.toString().length()==16)
			this.key = new SimpleStringProperty(key);
		else
			throw new IllegalArgumentException("16 chars expected.");
	}
	/**
	 * @return the initVector
	 */
	public SimpleStringProperty getInitVector() {
		return initVector;
	}
	/**
	 * @param initVector the initVector to set
	 */
	public void setInitVector(SimpleStringProperty initVector) 
	{
		if(key.toString().length()==16)
			this.initVector = initVector;
		
	}
	/**
	 * @param initVector the initVector to set
	 */
	public void setInitVector(String initVector) 
	{
		if(key.toString().length()==16)
			this.initVector = new SimpleStringProperty(initVector);
		
	}
	/**
	 * @return the encrypted
	 */
	public SimpleBooleanProperty getDecrypted() {
		return decrypted;
	}

	/**
	 * @param decrypted the decrypted to set
	 */
	public void setDecrypted(SimpleBooleanProperty decrypted) {
		this.decrypted = decrypted;
	}
	
	
//  public static void main(String[] args) {
//	  
//	 EncryptionStringProperty text = new EncryptionStringProperty("Ernest Chechelski");
//	 text.setKey("1234567890123456");
//	 text.setInitVector("1234567890123456");
//	 text.Encrypt();
//	 
//	 try 
//	 {
//		 text.Decrypt();
//	 } 
//	 catch (Exception e) 
//	 {
//		// TODO: handle exception
//	 }
	

  
	 
}
