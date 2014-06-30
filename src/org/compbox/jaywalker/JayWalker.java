package org.compbox.jaywalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JayWalker {
	private final JayVisitor autismVisitor;
	private final boolean testing;

	private final Path dataPath;

	public JayWalker(Path dataPath, Path replacementPath, Path autisticPath,
			boolean testing) {
		final ShresthaVistor replacementVistor = new ShresthaVistor();
		try {
			Files.walkFileTree(replacementPath, replacementVistor);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.testing = testing;
		this.autismVisitor = new JayVisitor(replacementVistor.getImageList(),
				autisticPath, this.testing);
		this.dataPath = dataPath;
	}

	public JayWalker(Path dataPath, Path replacementPath, Path autisticPath) {
		this(dataPath, replacementPath, autisticPath, false);
	}

	public static String existCheck(Path... paths) {
		StringBuilder result = new StringBuilder();
		for (Path path : paths) {
			if (!Files.exists(path)) {
				result.append("Does not exist: ");
				result.append(path.toString());
				result.append('\n');
			}
		}
		return result.toString();
	}

	public String walk() {
		try {
			Files.walkFileTree(dataPath, autismVisitor);
			return autismVisitor.getSummary();
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	public static String logFormat(Path destination, Path source) {
		return destination.toString() + " -> " + source.toString();
	}
}
