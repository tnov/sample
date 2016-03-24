package jp.dip.fission.SampleRestApplication.tools;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class PreferenceVisitor implements FileVisitor<Path> {
	
	private static String POSTFIX_EXTENTIONS = ".properties";
	private WatchService service = null;
	private ConcurrentHashMap<WatchKey,Path> serviceMap = null;
	private ArrayList<FileCallback> callbacks = null;
	private boolean isCancel = false;
	private boolean isRunning = false;
	
	public PreferenceVisitor() throws IOException {
		serviceMap = new ConcurrentHashMap<>();
		callbacks = new ArrayList<>();
		try {
			service = FileSystems.getDefault().newWatchService();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		WatchKey key = dir.register(service, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.OVERFLOW);
		serviceMap.put(key, dir);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		// エラー時はログ出力
		return FileVisitResult.TERMINATE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}
	
	public void addFileCallback(FileCallback callback) {
		callbacks.add(callback);
	}

	public void startWatch() {
		WatchKey key = null;
		isCancel = false;
		do {
			try {
				key = service.take();
				isRunning = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
			Path path = serviceMap.get(key);
			key.pollEvents().forEach(event->{
				callbacks.forEach(callback->{
					callback.execute(event.kind(), path);
				});
			});
		} while (isCancel);
		isRunning = false;
	}
	
	public void cancelWatch() {
		isCancel = true;
	}
	
	public void endWatch() throws IOException {
		while (isRunning) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 待機メッセージ
		}
		try {
			service.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			callbacks.forEach(callback -> {
				callback = null;
			});
			callbacks.clear();
			serviceMap.forEach((path,service) -> {
				service = null;
				serviceMap.remove(path);
			});
			serviceMap.clear();
		}
	}
	
//	private void test(Path file) throws IOException {
//		if (file .endsWith(POSTFIX_EXTENTIONS)) {
//			if (fileMap.contains(file)) {
//				try (Reader reader = Files.newReader(file.toFile(), StandardCharsets.UTF_8)){
//					fileMap.get(file).load(reader);
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//					fileMap.remove(file);
//				}
//			} else {
//				try (Reader reader = Files.newReader(file.toFile(), StandardCharsets.UTF_8)){
//					Properties properties = new Properties();
//					properties.load(reader);
//					fileMap.put(file, properties);
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//					fileMap.remove(file);
//				}
//			}
//		}
//	}
}
