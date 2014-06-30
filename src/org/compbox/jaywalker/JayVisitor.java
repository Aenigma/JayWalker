package org.compbox.jaywalker;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.*;

import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class JayVisitor implements FileVisitor<Path> {
	public static final float PROBABILITY_ASHWINIFY = 0.5F;
	private static final Pattern AUTISM;
	private static final Random rand;
	static {
		rand = new Random();
		AUTISM = Pattern.compile("^.*[/\\\\]autism$");
	}
	private final Path replacementFile;

	private final LinkedList<String> results;
	private final ArrayList<Path> imageList;

	private final boolean testing;

	public JayVisitor(List<Path> imageList, Path replPath) {
		this(imageList, replPath, true);
	}

	public JayVisitor(List<Path> imageList, Path replPath, boolean testing) {
		this.results = new LinkedList<String>();
		this.imageList = new ArrayList<Path>(imageList);
		this.replacementFile = replPath;
		Collections.shuffle(this.imageList);
		this.testing = testing;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
			throws IOException {
		if (AUTISM.matcher(dir.toString()).matches()) {
			AutismVisitor autism = new AutismVisitor(true);
			Files.walkFileTree(dir, autism);
			this.results.addAll(autism.getSummary());
			return FileVisitResult.SKIP_SUBTREE;
		}

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc)
			throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			throws IOException {
		if(rand.nextFloat() > PROBABILITY_ASHWINIFY)
			return FileVisitResult.CONTINUE;
		Path source = imageList.get(rand.nextInt(imageList.size()));
		results.add(JayWalker.logFormat(file, source));
		if (!testing) {
			try {
				Files.copy(source, file,StandardCopyOption.REPLACE_EXISTING);
				if(Files.isDirectory(replacementFile) ||  Files.isDirectory(source))
					System.err.println(JayWalker.logFormat(file, source));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc)
			throws IOException {
		return FileVisitResult.TERMINATE;
	}

	public String getSummary() {
		StringBuilder result = new StringBuilder();
		for (Object f : this.results) {
			result.append(f.toString());
			result.append('\n');
		}
		return result.toString();
	}

	private class AutismVisitor implements FileVisitor<Path> {
		private final LinkedList<String> results;

		public AutismVisitor(boolean testing) {
			this.results = new LinkedList<String>();
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			return CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			return CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			results.add(JayWalker.logFormat(file, replacementFile));
			if (!testing) {
				try {
					Files.copy(replacementFile, file, StandardCopyOption.REPLACE_EXISTING);
					if(Files.isDirectory(replacementFile) || Files.isDirectory(file))
						System.err.println(JayWalker.logFormat(file, replacementFile));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {
			return TERMINATE;
		}

		public List<String> getSummary() {
			return this.results;
		}
	}
}
