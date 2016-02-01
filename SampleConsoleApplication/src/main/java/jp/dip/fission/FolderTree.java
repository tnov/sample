package jp.dip.fission;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class FolderTree {
	public static List<URI> getTree(URI url,boolean isFile,boolean isDirectory) {
		Path path = Paths.get(url);
		System.out.println(path.toString() + " start");
		if (path.toFile().exists()) {
			try {
				Files.walkFileTree(path, new FileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
						System.out.println(dir.toString() + " directory pre");
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						System.out.println(file.toString() + " file visit");
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
						System.out.println(file.toString() + " file feiled");
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
						System.out.println(dir.toString() + " directory post");
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		} else {
		}
		System.out.println(path.toString() + " end");
		return null;
	}
}
