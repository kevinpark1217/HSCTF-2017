import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;

public class KE2TH {

	public static String[] getWords(int length) throws FileNotFoundException, IOException {
		ArrayList<String> words = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader("wordsEn.txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       if(line.length() == length)
		    	   words.add(line);
		    }
		}
		
		String[] arr = new String[words.size()];
		return words.toArray(arr);
	}

	public static String b(String string)
	{
		return string.substring(string.length() / 2) + string.substring(0, string.length() / 2);
	}

	public static String a(String string)
	{
		String c = "";
		for (int i = 0; i < 1024; i++)
		{
			string = b(string.substring(string.length() / 2)) + b(string.substring(0, string.length() / 2));
			c = c + string + "^";
		}
		return c;
	}

    //Squeezing everything into a single function to save processing time
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		//char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		
		String crypted = "-22^29^-11^-55^-12^22^82^53^-89^2^29^-65^37^116^63^27^-109^93^-3^-115^28^-40^1^109^-7^94^105^1^79^116^19^41^";
		String[] chunks = crypted.split("\\^");
		byte[] secret = new byte[chunks.length];
		for(int i=0; i<chunks.length; i++)
			secret[i] = Byte.parseByte(chunks[i]);
		
		String[] words3 = getWords(3);
		String[] words7 = getWords(7);
		String[] words2 = getWords(2);
		String[] words5 = getWords(5);
		String[] words4 = getWords(4);
		String[] words8 = getWords(8);
		
		String[] key = new String[9];
		for(String a: words3) {
			key[0] = a;
			for(String aa: words7) {
				key[1] = aa;
				for(String b: words3) {
					key[2] = b;
					for(String bb: words2) {
						key[3] = bb;
						for(String c: words5) {
							key[4] = c;
							for(String cc: words5) {
								key[5] = cc;
								for(String d: words3) {
									key[6] = d;
									for(String dd: words4) {
										key[7] = dd;
										for(String e: words8) {
											key[8] = e;
											
											//Random Hex characters generator
											char[] randHex = new char[8];
											for(int i=0; i<hexChars.length; i++) {
												randHex[0] = hexChars[i];
												for(int j=0; j<hexChars.length; j++) {
													randHex[1] = hexChars[j];
													for(int k=0; k<hexChars.length; k++) {
														randHex[2] = hexChars[k];
														for(int l=0; l<hexChars.length; l++) {
															randHex[3] = hexChars[l];
															for(int m=0; m<hexChars.length; m++) {
																randHex[4] = hexChars[m];
																for(int n=0; n<hexChars.length; n++) {
																	randHex[5] = hexChars[n];
																	for(int o=0; o<hexChars.length; o++) {
																		randHex[6] = hexChars[o];
																		for(int p=0; p<hexChars.length; p++) {
																			randHex[7] = hexChars[p];
																			
																			String attempt = "";
																			for(int z=0; z<key.length; z++)
																				attempt += key[z] + '_';
																			for(int z=0; z<randHex.length; z++)
																				attempt += randHex[z];
																			

																		    String text = a(attempt);
																		    //System.out.println(attempt);
																			if(text.hashCode() == 1670400264) {
																				try
																			      {
																			        MessageDigest md = MessageDigest.getInstance("SHA-256");
																			        md.update(text.getBytes("UTF-8"));
																			        
																			        byte[] hash = md.digest();
																			        boolean isMatch = true;
																					for(int z=0; z<secret.length; z++) {
																						if(secret[z] != hash[z]) {
																							isMatch = false;
																							break;
																						}
																					}
																					if(isMatch) {
																						System.out.println(attempt);
																						return;
																					}

																			      }
																			      catch (Exception localException) {}
																			}
																			//Randomized
																		}
																	}
																}
															}
														}
													}
												}
											}
											
											//Randomized String
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		
	}

}
