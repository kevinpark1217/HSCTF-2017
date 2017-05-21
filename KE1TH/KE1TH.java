import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class KE1TH {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher aes_cbc_pkcs5;
		SecretKey key;
		byte[] iv = { 10, -73, -33, -65, 87, 87, -121, -41, -16, 89, 12, 31, 7, 82, -43, -100 };
		
		aes_cbc_pkcs5 = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    byte[] bkey = java.util.Base64.getDecoder().decode("/Vl4PKzS9d+Vm/0eePmaYw==");
	    key = new SecretKeySpec(bkey, 0, bkey.length, "AES");
	    aes_cbc_pkcs5.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
	    
		
		String crypted = "-93^35^23^82^-4^57^-128^83^-95^-60^-100^73^40^-86^7^73^-101^3^118^-66^-104^69^121^76^1^-124^-124^-1^-64^29^28^43^2^-25^54^52^-79^-62^11^-43^52^-72^-117^-25^-103^-55^75^-97^";

		String[] chunks = crypted.split("\\^");
		byte[] secret = new byte[chunks.length];
		for(int i=0; i<chunks.length; i++) {
			//System.out.println(chunks[i]);
			secret[i] = Byte.parseByte(chunks[i]);
		}
		
	    byte[] msg = aes_cbc_pkcs5.doFinal(secret);
	    System.out.println(new String(msg));
	}

}
