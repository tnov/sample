package jp.dip.fission.SampleRestApplication.tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.io.Files;

public class PreferenceVisitor implements FileVisitor<Path> {
	
	private static String POSTFIX_EXTENTIONS = ".properties";
	private ConcurrentHashMap<Path,Properties> fileMap = null;
	
	public PreferenceVisitor() {
		fileMap = new ConcurrentHashMap<>();
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		if (file .endsWith(POSTFIX_EXTENTIONS)) {
			if (fileMap.contains(file)) {
				try (Reader reader = Files.newReader(file.toFile(), StandardCharsets.UTF_8)){
					fileMap.get(file).load(reader);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					fileMap.remove(file);
				}
			} else {
				try (Reader reader = Files.newReader(file.toFile(), StandardCharsets.UTF_8)){
					Properties properties = new Properties();
					properties.load(reader);
					fileMap.put(file, properties);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					fileMap.remove(file);
				}
			}
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		// エラー時はログ出力
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

}
