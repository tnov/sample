package jp.dip.fission.SampleRestApplication.tools;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class Preference {

	private static String USER_HOME = "user.home";
	private Path root = null;
	private ConcurrentHashMap<Path,Properties> fileMap = null;
	
	public Preference() {
		init(System.getProperty(USER_HOME));
	};
	
	public Preference(String path) {
		init(path);
	};
	
	public void init(String path) {
		FileSystem fileSystem = FileSystems.getDefault();
		fileMap = new ConcurrentHashMap<>();
		root = fileSystem.getPath(path);
	}
	
	public void tree() throws IOException {
		FileVisitor<Path> visitor = new PreferenceVisitor();
		try {
			Files.walkFileTree(root, visitor);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
