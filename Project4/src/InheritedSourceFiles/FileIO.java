import java.io.*;

/**
 * FileIO.java, ECE4960-P3
 * Created by Yuan He(yh772) on 2018/04/05
 * Platform: Java 8, Eclipse, MacOS
 * Copyright © 2018 Yuan He. All rights reserved.
 * 
 * FileIO:
 * Implements file operations.
 */

public class FileIO {

	/* Class invariant: data-output's path*/
	static String filePath = null;

	/* Class methods:*/
	/**Function: Create report, for the purpose of Sequentially streaming 
	 * 			contents into files
	 * @param: String filePath, String content
	 * @return: None*/
	public static void createReport(String Path, String content) {
		filePath = Path;
		try {
			FileWriter fw = new FileWriter(filePath,true);
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			System.out.println("writing failure" + e);
		}
	}

	/**Function: Writing "content" into "filePath"
	 * @param: String content
	 * @return: None*/
	public static void output(String content) {
		try {
			FileWriter fw = new FileWriter(filePath,true);
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			System.out.println("writing failure" + e);
		}
	}

}
