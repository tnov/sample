package jp.dip.fission.SampleRestApplication.tools;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class Preference {

	private static String USER_HOME = "user.home";
	private Path root = null;
	private PreferenceVisitor visitor = null;
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
	
	public void startWatch() throws IOException {
		visitor = new PreferenceVisitor();
		visitor.addFileCallback(new FileCallback() {
			
			@Override
			public boolean overflow(Path target) {
				return false;
			}
			
			@Override
			public boolean entryModifyFile(Path target) {
				return false;
			}
			
			@Override
			public boolean entryModifyDirectory(Path target) {
				return false;
			}
			
			@Override
			public boolean entryDeleteFile(Path target) {
				return false;
			}
			
			@Override
			public boolean entryDeleteDirectory(Path target) {
				return false;
			}
			
			@Override
			public boolean entryCreateFile(Path target) {
				return false;
			}
			
			@Override
			public boolean entryCreateDirectory(Path target) {
				return false;
			}
		});
		try {
			Files.walkFileTree(root, visitor);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		visitor.startWatch();
	}
	
	public void endWatch() {
		try {
			visitor.endWatch();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cancelWatch() {
		visitor.cancelWatch();
	}
}
