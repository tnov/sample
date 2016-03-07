package jp.dip.fission;

import java.io.File;
import java.net.URISyntaxException;

public class FolderTreeMain {

	public static void main(String[] args) throws URISyntaxException {
		FolderTree.getTree(new File("c:\\temp").toURI(), true, true);
	}

}
