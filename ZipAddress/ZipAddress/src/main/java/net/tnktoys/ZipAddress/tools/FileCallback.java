package net.tnktoys.ZipAddress.tools;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent.Kind;

public interface FileCallback {
	
	default boolean execute(Kind<?> kind, Path target) {
		boolean result = false;
		if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
			if (target.toFile().isFile()) {
				result = entryCreateFile(target);
			} else if (target.toFile().isDirectory()) {
				result = entryCreateDirectory(target);
			}
		} else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
			if (target.toFile().isFile()) {
				result = entryModifyFile(target);
			} else if (target.toFile().isDirectory()) {
				result = entryModifyDirectory(target);
			}
		} else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
			if (target.toFile().isFile()) {
				result = entryDeleteFile(target);
			} else if (target.toFile().isDirectory()) {
				result = entryDeleteDirectory(target);
			}
		} else if (kind.equals(StandardWatchEventKinds.OVERFLOW)) {
			result = overflow(target);
		}
		return result;
	}
	
	public boolean entryCreateFile(Path target);
	public boolean entryModifyFile(Path target);
	public boolean entryDeleteFile(Path target);
	public boolean entryCreateDirectory(Path target);
	public boolean entryModifyDirectory(Path target);
	public boolean entryDeleteDirectory(Path target);
	public boolean overflow(Path target);
}
