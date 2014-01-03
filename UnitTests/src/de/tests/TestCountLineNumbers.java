package de.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TestCountLineNumbers {
	
	static int fileCounter = 0;
	static int lineCounter = 0;
	
	public static void main(String[] args) throws IOException {
		File highestDir = new File("C:/Users/D059373/git/Fallstudie");
		processFileContents(highestDir);
		
		
		//String parentDirPath = upperDir.getPath().lastIndexOf("\\");
		//System.out.println(highestDir.getPath());
		//File parent = upperDir.getPath().
		//ArrayList<File> files = new ArrayList<File>(Arrays.asList(upperDir.listFiles()));
		//for (File f : files) {
		//	System.out.println(f.getPath());
		//}
		System.out.println("File counter: " + fileCounter);
		System.out.println(("Line counter: " + lineCounter));
	}
	
	
	
	public static void processFileContents(File file) throws IOException {
		String path = file.getPath();
		//System.out.println(path);
		//java file
		if (path.indexOf(".java") != -1) {
			fileCounter++;
			
			FileReader fr = new FileReader(file);
			BufferedReader reader = new BufferedReader(fr);
			String line = reader.readLine();
			while ( line != null ) {
			    line = reader.readLine();
			    lineCounter++;
			}
			reader.close();
		}
		//dir
		else if (file.isDirectory()) {
			ArrayList<File> files = new ArrayList<File>(Arrays.asList(file.listFiles()));
			for (File f : files) {
				processFileContents(f);
			}
		}
		else {
			//System.out.println(path);
		}
		
	}
	
}
