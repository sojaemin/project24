package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class encrypUtil {
	
	/*public static String processEncryp(String srcStr, String encType) {
		String encrypData = "";
		
		try {
			
			srcStr = common.nulltovoid(srcStr);
			//SHA-256 MessageDigest object created
			
			MessageDigest md = MessageDigest.getInstance(encType);
			md.update(srcStr.getBytes());
			
			byte[] resultAry = md.digest();
			
			for (int i=0;i<resultAry.length;i++) {
				encrypData += Integer.toHexString(resultAry[i]&0xFF);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encrypData;
		}
	*/
	
	public String getHash(String pwd) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String SHA = ""; 
		String str = "";
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.update(str.getBytes()); 

		 byte byteData[] = digest.digest(pwd.getBytes("UTF-8"));

		 StringBuffer sb = new StringBuffer(); 

		 for(int i = 0 ; i < byteData.length ; i++){

			 sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));

		 }

		 SHA = sb.toString();
		
		return SHA;
	}

}
