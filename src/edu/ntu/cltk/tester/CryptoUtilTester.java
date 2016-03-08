package edu.ntu.cltk.tester;

import java.util.List;

import edu.ntu.cltk.crypto.CryptoUtil;
import edu.ntu.cltk.file.FileUtil;

public class CryptoUtilTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//hexToCharTester();
		deflateTester();
	}
	
	public static void hexToCharTester(){
		//String str = "\\x41jfdabc";
		//System.out.println(CryptoUtil.hexToChar(str));
		//System.out.println(CryptoUtil.hexToChar(str));
		List<String> lines = FileUtil.readFileLineByLine("javascript.php");
		for (String line : lines){
			FileUtil.writeFile("converted.php", CryptoUtil.hexToChar(line), "a");
		}
	}

	public static void deflateTester(){
		String[] cases = {"eJzLTSzKTi2x0tcvTk0sSs6wL7QtyEvMTbUCAG8VCMs="};
		for (String s : cases){
			System.out.println(s + " : " + CryptoUtil.deflate(s));//CryptoUtil.base64Decoding(s)));
		}
	}
}
