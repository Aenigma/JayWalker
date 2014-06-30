package org.compbox.jaywalker;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.*;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
public class ShresthaVistor implements FileVisitor<Path>{
	private final LinkedList<Path> imageList;
	public ShresthaVistor() {
		this.imageList = new LinkedList<Path>();
	}
	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException arg1)
			throws IOException {
		return CONTINUE;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes arg1)
			throws IOException {
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes arg1)
			throws IOException {
		imageList.add(file);
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path dir, IOException arg1)
			throws IOException {
		return TERMINATE;
	}
	
	public List<Path> getImageList() {
		return imageList;
	}
}
