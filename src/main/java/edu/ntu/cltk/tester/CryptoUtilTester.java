package edu.ntu.cltk.tester;

import edu.ntu.cltk.crypto.CryptoUtil;
import edu.ntu.cltk.file.FileUtil;

import java.util.List;

public class CryptoUtilTester {

    /**
     * @param args
     */
    public static void main(String[] args) {

        hexToCharTester();
    }

    public static void hexToCharTester() {
        //String str = "\\x41jfdabc";
        //System.out.println(CryptoUtil.hexToChar(str));
        //System.out.println(CryptoUtil.hexToChar(str));
        List<String> lines = FileUtil.readFileLineByLine("javascript.php");
        for (String line : lines) {
            FileUtil.writeFile("converted.php", CryptoUtil.hexToChar(line), "a");
        }
    }

}
