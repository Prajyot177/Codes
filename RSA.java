import javax.swing.*;
import java.security.*;
import javax.crypto.*;
import java.util.Base64;

public class RSA
{
	private static final int KEYSIZE = 512;
	public static void main(String args[])
	{
		KeyPair mykeyPair;
		try
		{
			KeyPairGenerator mypair = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = new SecureRandom();
			mypair.initialize(KEYSIZE,random);
			mykeyPair = mypair.generateKeyPair();

			final Cipher cipher = Cipher.getInstance("RSA");
			final String plaintext =JOptionPane.showInputDialog(null,"Enter message to encrypt");
			System.out.println("Original Message - "+plaintext);

			cipher.init(Cipher.ENCRYPT_MODE,mykeyPair.getPublic());
			byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
			String chipertext = new String(Base64.getEncoder().encode(encryptedBytes));	
			JOptionPane.showMessageDialog(null,"Encrypted Data "+"\n"+chipertext);
			System.out.println("Encrypted ciphertext -"+chipertext);

			cipher.init(Cipher.DECRYPT_MODE,mykeyPair.getPrivate());
			byte[] ciphertextBytes = Base64.getDecoder().decode(chipertext.getBytes());
			byte[] decryptedBytes = cipher.doFinal(ciphertextBytes);
			String decryptedString = new String(decryptedBytes);
			JOptionPane.showMessageDialog(null,"Decrypted Data "+"\n"+decryptedString);	
			System.out.println("Decrypted plaintext -"+decryptedString);
		}
		catch(NoSuchAlgorithmException e)
		{
			System.err.println("Algorithm not supported!" + e.getMessage() + "!");
		}
		catch(NoSuchPaddingException | InvalidKeyException e)
		{
			System.err.println("Cipher cannot be created");
			e.printStackTrace();
		}
		catch(BadPaddingException | IllegalBlockSizeException e)
		{
			System.err.println("An error occured during encryption");
			e.printStackTrace();		
		}
	}
}
