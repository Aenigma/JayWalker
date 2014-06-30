package org.compbox.jaywalker;

import java.awt.GraphicsEnvironment;
import org.apache.commons.cli.*;
import org.compbox.jaywalker.gui.JayCLI;
import org.compbox.jaywalker.gui.JayGooey;

@SuppressWarnings("static-access")
public class JayDriver {
	private static Options options;
	static {
		options = new Options();
		options.addOption("h", "help", false, "Displays this help page");
		options.addOption("f", "force", false, "Do not prompt to confirm");
		options.addOption("c", "nox", false, "Run in headless mode");
		options.addOption("v", "verbose", false, "Run in headless mode");
		options.addOption("t", "test", false, "Testing mode. Does not make changes, just shows you");
		synchronized (OptionBuilder.class) {
			options.addOption(OptionBuilder.withLongOpt("operation")
			             .hasArg(true)
			             .withArgName("DIRECTORY")
			             .withDescription("Directory to perform operations on")
			             .create("d"));
			options.addOption(OptionBuilder.withLongOpt("repl-dir")
		             .hasArg(true)
		             .withArgName("DIRECTORY")
		             .withDescription("Directory to find replacement files on")
		             .create("r"));
			options.addOption(OptionBuilder.withLongOpt("repl-file")
		             .hasArg(true)
		             .withArgName("DIRECTORY")
		             .withDescription("File to replace with")
		             .create("a"));
		}
	}
	private boolean force;
	private boolean verbose;
	private boolean testing;
	private String operationDirectory;
	private String replacementDirectory;
	private String replacementFile;
	private void cliHandler(String[] args) {
		CommandLineParser parser = new GnuParser();
		CommandLine results = null;
		try {
			results = parser.parse(options, args);
			
			// required arguments
			this.operationDirectory = results.getOptionValue("d");
			this.replacementDirectory = results.getOptionValue("r");
			this.replacementFile = results.getOptionValue("a");
			
			// optional booleans
			this.force = results.hasOption("f");
			
			if(results.hasOption("h")) {
				showHelp();
				System.exit(0);
			}
			if(results.hasOption("c"))
				System.setProperty("java.awt.headless", "true");
			if(results.hasOption("t"))
				testing = true;
			if(results.hasOption("v"))
				verbose = true;
			if(results.hasOption("f")) {
				force = true;			
			}
			if(GraphicsEnvironment.isHeadless() && (operationDirectory == null || replacementDirectory == null || replacementFile == null)) {
				showHelp();
				System.err.println("Must enter values for repl-dir, repl-file, and operation parameters if running\n");
				System.err.println("in headless mode. See above help.\n");
				System.exit(1);
			}
		} catch (ParseException e) {
			showHelp();
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}
	private static void showHelp() {
		HelpFormatter help = new HelpFormatter();
		help.printHelp("java -jar jaywalker.jar", "Enterprise grade photo collection ruiner\n", options, "\nBy Kevin Raoofi", true);
	}
	public JayDriver(String[] args) {
		// default values for CLI parameters
		
		cliHandler(args);
		
		if (GraphicsEnvironment.isHeadless()) {
			new JayCLI(this.operationDirectory, this.replacementDirectory, this.replacementFile, this.force, this.verbose,this.testing);
		} else {
			System.out.println("Staring GUI in testing mode: " + this.testing);
			new JayGooey(this.operationDirectory, this.replacementDirectory, this.replacementFile,this.testing);
		}
	}

	public static void main(String[] args) {
		new JayDriver(args);
	}
}
