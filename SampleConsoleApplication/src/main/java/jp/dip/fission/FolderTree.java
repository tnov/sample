package jp.dip.fission;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FolderTree {
	public static List<URI> getTree(URI url,boolean isFile,boolean isDirectory) {
		List<URI> list = null;
		Path path = Paths.get(url);
		if (path.toFile().exists()) {
			if (path.toFile().isFile()) {
				if (isFile) {
					list = new ArrayList<>();
					list.add(path.toUri());
				}
			} else if(path.toFile().isDirectory()) {
				if (isDirectory) {
					list = new ArrayList<>();
					list.add(path.toUri());
				}
				Iterator<Path> ite = path.iterator();
				while (ite.hasNext()) {
					List<URI> treeList = getTree(ite.next().toUri(),isFile,isDirectory);
					if (treeList != null) {
						list = new ArrayList<>();
						list.addAll(treeList);
					}
				}
				list = new ArrayList<>();

				list.addAll(getTree(url, isFile, isDirectory));
			} else {
			}
		}
		return list;
	}
	public static List<String> getTree(String first,String... more) {
		Path path = Paths.get(first,more);
		return null;
	}
}
