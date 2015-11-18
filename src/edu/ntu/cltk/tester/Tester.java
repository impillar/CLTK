package edu.ntu.cltk.tester;

import edu.ntu.cltk.file.FileRetrieval;
import edu.ntu.cltk.file.FileRetrieval.INode;
import edu.ntu.cltk.file.FileUtil;

import java.util.List;

public class Tester {

    public static void main(String[] args) {
        FileRetrieval fr = new FileRetrieval("C:\\Users\\gzmeng\\Downloads\\ProductLine\\jmetal\\", "java");
        while (fr.hasNext()) {
            INode inode = (INode) fr.next();
            //System.out.println(inode.path);
            List<String> lines = FileUtil.readFileLineByLine(inode.path);
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                if (line.matches("import(\\s)*jmetal.qualityIndicator.*;")) {
                    sb.append(line.replace("qualityIndicator", "qualityindicator")).append("\n");
                    System.out.println("File " + inode.path);
                } else {
                    sb.append(line + "\n");
                }
            }
            FileUtil.writeFile(inode.path, sb.toString());
        }
    }
}
