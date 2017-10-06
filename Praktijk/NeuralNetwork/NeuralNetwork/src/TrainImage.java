import java.awt.Color;
import java.awt.image.BufferedImage;

public class TrainImage {

	public static final int IMAGE_SIZE = 28;
	short[][] data = new short[28][28];
	short label = -1;

	public TrainImage(short[][] data, short label) {
		this.data = data;
		this.label = label;
	}

	public BufferedImage getImage() {
		BufferedImage bufferedImage = new BufferedImage(IMAGE_SIZE,IMAGE_SIZE, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < IMAGE_SIZE; x++) {
			for (int y = 0; y < IMAGE_SIZE; y++) {
				bufferedImage.setRGB(x, y, new Color(255-data[x][y],255-data[x][y],255-data[x][y]).getRGB());		
			}
		}
		return bufferedImage;
	}
	
	public static TrainImage getImageFromString(String str) {
		String[] d = str.split(",");

		short label = Short.parseShort(d[0]);
		short[][] data = new short[IMAGE_SIZE][IMAGE_SIZE];

		for (int i = 1; i < d.length; i++) {
			data[(i - 1) % IMAGE_SIZE][(i - 1) / IMAGE_SIZE] = Short.parseShort(d[(i - 1)]);
		}
		return new TrainImage(data, label);
	}

}
