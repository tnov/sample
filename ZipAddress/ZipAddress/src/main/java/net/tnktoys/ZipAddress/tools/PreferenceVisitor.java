package net.tnktoys.ZipAddress.tools;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
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
import java.util.concurrent.TimeUnit;

public class PreferenceVisitor implements FileVisitor<Path> {
	
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
		System.out.println("startWatch");
		isCancel = false;
		do {
			isRunning = true;
			try {
				WatchKey key = service.poll(5, TimeUnit.SECONDS);
				if (key == null) {
					continue;
				} else {
					key.pollEvents().forEach(event->{
						Path path = serviceMap.get(key).resolve((Path)event.context());
						callbacks.forEach(callback->{
							boolean result = callback.execute(event.kind(), path);
							// エラー時は監視停止
							if (!result) {
								//TODO ログ
								isCancel = true;
							}
						});
					});
				}
			} catch (InterruptedException | ClosedWatchServiceException e) {
				e.printStackTrace();
				isCancel = true;
			}
		} while (!isCancel);
		isRunning = false;
		System.out.println("finish");
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public void cancelWatch() {
		isCancel = true;
		System.out.println("cancelWatch");
	}
	
	public void endWatch() throws IOException {
		System.out.println("start endWatch");
		int i = 0;
		while (isRunning) {
			System.out.println("endWatch" + isRunning());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 待機メッセージ
			System.out.println("sleeping..." + ++i);
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
			serviceMap.forEach((service,path) -> {
				path = null;
				service.reset();
				serviceMap.remove(service);
			});
			serviceMap.clear();
		}
		System.out.println("end   endWatch");
	}
}
