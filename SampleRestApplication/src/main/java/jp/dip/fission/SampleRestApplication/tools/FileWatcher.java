package jp.dip.fission.SampleRestApplication.tools;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;

public class FileWatcher {
	
	private Path root = null;
	
//	public WatchEvent.Modifier[] modifier = new WatchEvent.Modifier[] {StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.ENTRY_DELETE};
	public FileWatcher(Path path) {
		root = path;
	}
	
	public void test() {
		
		try (WatchService service = FileSystems.getDefault().newWatchService()) {
			root.register(service , StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.OVERFLOW);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
