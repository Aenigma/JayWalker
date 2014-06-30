package org.compbox.jaywalker.tests;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.util.List;

import org.compbox.jaywalker.ShresthaVistor;

public class PathCopyTest {
	public static void main(String[] args) {
		Path ashwin = Paths.get("C:\\Users\\kevin\\Dropbox\\peopledump\\shrestha");
		Path jay = Paths.get("C:\\Users\\kevin\\jaytest");
		ShresthaVistor ashvisitor = new ShresthaVistor();
		ShresthaVistor jayvisitor = new ShresthaVistor();
		try {
			
			Files.walkFileTree(ashwin, ashvisitor);
			Files.walkFileTree(jay, jayvisitor);
			
			//List<Path> foo = ashvisitor.getImageList();
			
			
			
		} catch (IOException e) {
		}
		
		
		
	}
}
