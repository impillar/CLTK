package edu.ntu.cltk.crypto;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;

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
}
