import java.io.PrintStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64.Decoder;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Keith
{
  private Cipher aes_cbc_pkcs5;
  private SecretKey key;
  private byte[] iv = { 10, -73, -33, -65, 87, 87, -121, -41, -16, 89, 12, 31, 7, 82, -43, -100 };
  
  public Keith() throws javax.crypto.NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException
  {
    aes_cbc_pkcs5 = Cipher.getInstance("AES/CBC/PKCS5Padding");
    
    byte[] bkey = java.util.Base64.getDecoder().decode("/Vl4PKzS9d+Vm/0eePmaYw==");
    key = new SecretKeySpec(bkey, 0, bkey.length, "AES");
  }
  
  public String encrypt(String plain)
  {
    try {
      aes_cbc_pkcs5.init(1, key, new IvParameterSpec(iv));
      return bytes_to_string(aes_cbc_pkcs5.doFinal(plain.getBytes()));
    }
    catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (InvalidAlgorithmParameterException e) {
      e.printStackTrace();
    } catch (BadPaddingException e) {
      e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    }
    
    return "";
  }
  

  public String bytes_to_string(byte[] bytes)
  {
    String text = "";
    for (byte b : bytes)
    {
      text = text + b + "^";
    }
    return text;
  }
  

  public boolean check(String string)
  {
    return string.equals("-93^35^23^82^-4^57^-128^83^-95^-60^-100^73^40^-86^7^73^-101^3^118^-66^-104^69^121^76^1^-124^-124^-1^-64^29^28^43^2^-25^54^52^-79^-62^11^-43^52^-72^-117^-25^-103^-55^75^-97^");
  }
  

  public static void main(String[] args)
  {
    try
    {
      System.out.print("I cannot tell you the flag but I'm more than happy to check if you have the right flag: ");
      
      Keith keith = new Keith();
      

      if (keith.check(keith.encrypt(new Scanner(System.in).nextLine())))
      {
        System.out.println("Congrats! You have the flag!");
      }
      else
      {
        System.out.println("Er...You might want to check that over.");
      }
      
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
