package net.tnk_toys.JerseyClient.gui.propertyeditor;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUncompressor {
	
	private static Logger logger = Logger.getLogger(ZipUncompressor.class.getName());
	
	public static void main(String[] args) {
		logger.log(Level.FINE, "ZipUncompressor start");
		ZipUncompressor uncompressor = new ZipUncompressor(Paths.get("C:\\tmp\\test.zip"));
		uncompressor.getInfo();
		logger.log(Level.FINE, "ZipUncompressor end");
	}
	
	Path path;
	
	public ZipUncompressor(Path path) {
		this.path = path;
	}
	
	public void getInfo() {
		try (ZipInputStream stream = new ZipInputStream(new FileInputStream(this.path.toFile()),Charset.forName("Windows-31J"))) {
			ZipEntry entry;
			while ((entry = stream.getNextEntry()) != null ) {
				if(entry.isDirectory()){
					
				} else {
					System.out.println(entry.getName()+"/"+entry.getSize()+"/"+entry.getCompressedSize()+"/"+entry.getLastModifiedTime());
					
				}
			}
			stream.closeEntry();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void uncompress() {
		try (ZipInputStream stream = new ZipInputStream(new FileInputStream(this.path.toFile()))) {
			stream.getNextEntry();
			
			stream.closeEntry();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
