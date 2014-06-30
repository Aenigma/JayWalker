package org.compbox.jaywalker.gui;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.compbox.jaywalker.JayVisitor;
import org.compbox.jaywalker.JayWalker;

public class JayCLI {
	private Path dataPath, replacementPath, autisticPath;

	public JayCLI(String dataPath, String replacementPath, String autisticPath,
			boolean force, boolean verbose, boolean testing) {
		this.dataPath = Paths.get(dataPath);
		this.replacementPath = Paths.get(replacementPath);
		this.autisticPath = Paths.get(autisticPath);

		String existCheckResults = JayWalker.existCheck(this.dataPath,
				this.replacementPath, this.autisticPath);
		if (existCheckResults.equals("")) {
			if (!force)
				confirmOperation();
			launch(verbose,testing);
		} else {
			System.err.println(existCheckResults);
			System.exit(-2);
		}
	}

	private void launch(boolean verbose,boolean testing) {
		JayWalker walker = new JayWalker(dataPath, replacementPath, autisticPath,testing);
		String output = walker.walk();
		if(verbose) {
			System.out.println(output);	
		}
	}

	private void confirmOperation() {
		Scanner sc = new Scanner(System.in);
		System.out.println("===WARNING===");
		if(!Files.isDirectory(this.replacementPath))
			System.err.println(this.autisticPath.toString() + " is not a directory!");
		if(!Files.isDirectory(this.dataPath))
			System.err.println(this.autisticPath.toString() + " is not a directory!");
		if(Files.isDirectory(this.autisticPath))
			System.err.println(this.autisticPath.toString() + " is a directory!");
		System.out.println("Program will now replace a random " + JayVisitor.PROBABILITY_ASHWINIFY
				+ " in " + this.dataPath.getFileName() + " with "
				+ this.replacementPath.getFileName() + " and every file in "
				+ " **/autism/ with " + this.autisticPath.getFileName());
		System.out.println("Please type in YES (all caps) to continue: ");
		if (!sc.nextLine().equals("YES")) {
			System.out.println("Exiting..");
			System.exit(2);
		}
	}
}
