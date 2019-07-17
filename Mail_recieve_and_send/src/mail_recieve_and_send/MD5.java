/*
 * MD5.java
 *
 * Created on 29 лютого 2008, 19:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package mail_recieve_and_send;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Technik
 */
public class MD5 
{ 
    /** 
    * Encodes a string 
    * 
    * @param str String to encode 
    * @return Encoded String 
    * @throws NoSuchAlgorithmException 
    */ 
    public static String crypt(String str) throws Exception{ 
        if (str == null || str.length() == 0) { 
            throw new IllegalArgumentException("String to encript cannot be null or zero length"); 
        } 

        StringBuffer hexString = new StringBuffer(); 

        try { 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
            md.update(str.getBytes()); 
            byte[] hash = md.digest(); 

            for (int i = 0; i < hash.length; i++) { 
                if ((0xff & hash[i]) < 0x10) { 
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i]))); 
                } 
                else { 
                    hexString.append(Integer.toHexString(0xFF & hash[i])); 
                } 
            } 
        } catch (NoSuchAlgorithmException e) { 
            throw new Exception(e); 
        } 
    return hexString.toString(); 
    } 
} 
/*
       String s="This is a test";
       MessageDigest m=MessageDigest.getInstance("MD5");
       m.update(s.getBytes(),0,s.length());
       System.out.println("MD5: "+new BigInteger(1,m.digest()).toString(16));
 */