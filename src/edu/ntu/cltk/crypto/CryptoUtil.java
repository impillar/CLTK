package edu.ntu.cltk.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.InflaterInputStream;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;

import edu.ntu.cltk.data.PrimUtil;

public class CryptoUtil {

	/**
	 * Return the encoded text for the plainText with base64 encoding
	 * @param plainText		The content which is to be encoded
	 * @param urlSafe		If the encoding should consider the url-safty issue.
	 * @return				The encoded message
	 */
	public static String base64Encoding(String plainText, boolean urlSafe) {
		Base64 base64 = new Base64(urlSafe);
		byte[] encoded = base64.encode(plainText.getBytes());
		return new String(encoded);
	}
	
	/**
	 * Return the decoded text for the encoded text with base64 decoding
	 * @param encrypted		The content which is to be decoded
	 * @return				The decoded message
	 */
	public static String base64Decoding(String encoded) {
		Base64 base64 = new Base64();
		byte[] plainText = base64.decode(encoded.getBytes());
		return new String(plainText);
	}
	
	/**
	 * Return the encoded url
	 * @param plainText		The content which is to be encoded
	 * @return				The encoded message
	 * @throws UnsupportedEncodingException
	 */
	public static String urlEncoding(String plainText) throws UnsupportedEncodingException {
		Charset charset = Charsets.UTF_8;
		return URLEncoder.encode(plainText, charset.name());
	}
	
	/**
	 * Return the decoded url
	 * @param encoded		The content which is to be decoded
	 * @return				The decoded message
	 * @throws UnsupportedEncodingException
	 */
	public static String urlDecoding(String encoded) throws UnsupportedEncodingException {
		Charset charset = Charsets.UTF_8;
		return URLDecoder.decode(encoded, charset.name());
	}
	
	/**
	 * Luhn Algorithm
	 * http://en.wikipedia.org/wiki/Luhn_algorithm <br/>
	 * The Luhn algorithm or Luhn formula, also known as <br/>
	 * the "modulus 10" or "mod 10" algorithm, is a simple <br/>
	 * checksum formula used to validate a variety of <br/>
	 * identification numbers, such as credit card numbers, <br/>
	 * IMEI numbers, National Provider Identifier numbers in <br/>
	 * US and Canadian Social Insurance Numbers. It was created <br/>
	 * by IBM scientist Hans Peter Luhn and described in U.S. <br/>
	 * Patent No. 2,950,048, filed on January 6, 1954, <br/>
	 * and granted on August 23, 1960.
	 * @param number
	 * @return
	 */
	public static boolean luhnChecker(String number){
		if (PrimUtil.isNonNegativeInteger(number) && number.length() > 2){
			int num = number.charAt(number.length()-1) - '0';
			return (luhnChecksum(number.substring(0, number.length()-1)) == num);
		}
		return false;
	}
	
	public static int luhnChecksum(String number){
		if (PrimUtil.isNonNegativeInteger(number)){
			int sum = 0;
			for (int i = 0 ; i < number.length(); i++){
				int num = number.charAt(i) - '0';
				if (i % 2 == 0){
					sum += num;
				}else{
					num *= 2;
					while (num > 0){
						sum += num % 10;
						num /= 10;
					}
				}
			}
			return ((sum / 10 + 1) * 10 - sum) % 10;
		}
		return -1;
	}
	
	/**
	 * Replace all hexadecimal \xAA to a char
	 * TODO: use regex to replace all hexadecimal?
	 * @param str
	 * @return
	 */
	public static String hexToChar(String str){
		Pattern pat = Pattern.compile("\\\\[x]([0-9a-fA-F]{2})");
		Matcher mat = pat.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (mat.find()){
			String text = mat.group(1);
			int i = Integer.valueOf(text, 16);
			char c = (char) i;
			mat.appendReplacement(sb, Matcher.quoteReplacement(String.valueOf(c)));
		}
		mat.appendTail(sb);
		return sb.toString();
	}
	
	public static String deflate(String str){
		
		InflaterInputStream is = new InflaterInputStream(new ByteArrayInputStream(str.getBytes()));
		ByteArrayOutputStream baos = new ByteArrayOutputStream(8192);
		byte[] buf = new byte[4096];
		int len = 0;
		try {
			while ((len = is.read(buf)) != -1){
				baos.write(buf, 0, len);
			}
			
			return new String(baos.toByteArray(), "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
