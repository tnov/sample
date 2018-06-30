package net.tnk_toys.JerseyClient.gui.propertyeditor;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class ImageUtil {

	private static int width = 800;
	private static int height = 600;
	
	public static void main(String[] args) {
		try {
			BufferedImage bi = ImageIO.read(Paths.get("").toFile());
			ImageFilter filter = new AreaAveragingScaleFilter(bi.getWidth(), bi.getHeight());
			ImageProducer producer = new FilteredImageSource(bi.getSource(), filter);
			Image dist = Toolkit.getDefaultToolkit().createImage(producer);
			BufferedImage image = new BufferedImage(dist.getWidth(null), dist.getHeight(null),BufferedImage.TYPE_INT_RGB);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
