package jp.dip.fission.SampleRestApplication.tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class Preference {

	private static String USER_HOME = "user.home";
	private static String POSTFIX_EXTENTIONS = ".properties";
	private Path root = null;
	private PreferenceVisitor visitor = null;
	private ConcurrentHashMap<Path,Properties> fileMap = null;
	
	public Preference() {
		init(System.getProperty(USER_HOME));
	};
	
	public Preference(String path) {
		init(path);
	};
	
	private void init(String path) {
		FileSystem fileSystem = FileSystems.getDefault();
		fileMap = new ConcurrentHashMap<>();
		root = fileSystem.getPath(path);
	}
	
	public void startWatch() throws IOException {
		visitor = new PreferenceVisitor();
		visitor.addFileCallback(new FileCallback() {
			
			@Override
			public boolean overflow(Path target) {
				return true;
			}
			
			@Override
			public boolean entryModifyFile(Path target) {
				boolean result = true;
				try {
					load(target);
				} catch (IOException e) {
					e.printStackTrace();
					result = false;
				}
				return result;
			}
			
			@Override
			public boolean entryModifyDirectory(Path target) {
				return true;
			}
			
			@Override
			public boolean entryDeleteFile(Path target) {
				unload(target);
				return true;
			}
			
			@Override
			public boolean entryDeleteDirectory(Path target) {
				return true;
			}
			
			@Override
			public boolean entryCreateFile(Path target) {
				boolean result = true;
				try {
					load(target);
				} catch (IOException e) {
					e.printStackTrace();
					result = false;
				}
				return result;
			}
			
			@Override
			public boolean entryCreateDirectory(Path target) {
				return true;
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
	
	private void load(Path file) throws IOException {
		if (file.getFileName().toString().endsWith(POSTFIX_EXTENTIONS)) {
			if (fileMap.contains(file)) {
				try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)){
					fileMap.get(file).load(reader);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					fileMap.remove(file);
				}
			} else {
				try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)){
					Properties properties = new Properties();
					properties.load(reader);
					fileMap.put(file, properties);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private void unload(Path file) {
		if (file.getFileName().toString().endsWith(POSTFIX_EXTENTIONS)) {
			if (fileMap.contains(file)) {
				fileMap.remove(file);
			}
		}
	}
}
