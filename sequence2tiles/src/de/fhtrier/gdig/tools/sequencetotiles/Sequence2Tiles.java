package de.fhtrier.gdig.tools.sequencetotiles;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Sequence2Tiles {
	
	private static File sourceDirectory = new File("./");

//	public static void main(String[] args) {
//
//		System.out.println("Enter Path to Imagesequence: ["
//				+ sourceDirectory.getAbsolutePath() + "]");
//
//		InputStreamReader isr = new InputStreamReader(System.in);
//
//		BufferedReader br = new BufferedReader(isr);
//		String sourcePath;
//		try {
//			sourcePath = br.readLine();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public static String getSourceDirectory() {
		return sourceDirectory.getAbsolutePath() + File.separator;
	}

	public static void run(String sourcePath, String destinationPath) {
		sourceDirectory = new File(sourcePath);
		
		if (!sourceDirectory.isDirectory())
		{
			System.out.println("Selected location: \"" + sourceDirectory.getAbsolutePath() + "\" is not a Directory!");			
		} else {
			System.out.println("Selected location: \"" + sourceDirectory.getAbsolutePath() + "\"");
			
			String[] l = sourceDirectory.list();
			ArrayList<BufferedImage> il = new ArrayList<BufferedImage>();
			int width = -1,
			height = -1;
			
			for (String s : l) {
				System.out.print(s + ",");
			}
			
			System.out.println();
			
			Arrays.sort(l);
			
			for (String s : l) {
				System.out.print(s + ",");
			}
			
			System.out.println();
			
			for (String s : l) {
				File item = new File(sourceDirectory.getAbsolutePath() + '/' + s);
				if (item.isFile() && s.toLowerCase().endsWith(".png"))
				{
					try {
						BufferedImage image = ImageIO.read(item);
						if (width == -1)
						{
							width = image.getWidth();
							height = image.getHeight();
						} else if (width != image.getWidth() || height != image.getHeight()) {
							throw new IllegalArgumentException("Image with wrong size in Sequence! \n All images in sequence have to have the same size.");
						}
						il.add(image);
					
					} catch (IOException e) {
						e.printStackTrace();
					}
//					System.out.println("Selected: " + item.getAbsolutePath());
				} 
//				else {
//					System.out.println("Ignored:  " + item.getAbsolutePath());
//				}
			}
			
			int numImages = il.size();
			
			System.out.println(numImages + " Images found!");
			
			// tiles square
//			int tilesSquare = 1;
//			if (!il.isEmpty())
//			{
//				double d = Math.sqrt(il.size());
//				tilesSquare = (int)d;
//				if (tilesSquare < d)
//				{
//					tilesSquare++;
//				}
//			}
			
			// tiles square
//			BufferedImage img = new BufferedImage(width*tilesSquare, height*tilesSquare, BufferedImage.TYPE_INT_ARGB);
			// tiles strip
			BufferedImage img = new BufferedImage(width*numImages, height, BufferedImage.TYPE_INT_ARGB);
			
			int j = 0;
			int k = 0;
			for (BufferedImage bI : il) {
				// tiles square
//				if (j == tilesSquare)
//				{
//					j=0;
//					k++;
//				}
			// tiles strip
			
			
//				System.out.println("Image size: (" + bI.getWidth() + ", " + bI.getHeight() +")");
				
				for (int m = 0; m < width; m++)
				{
					for (int n = 0; n < height; n++)
					{
//						System.out.println("Source: (" + m + ", " + n +")");
						int rgb = bI.getRGB(m, n);
						int o = width * j + m,
							p = height * k + n;
//						System.out.println("Dest:   (" + o + ", " + p +")");
						img.setRGB(o, p, rgb);
					}
				}
				j++;
			}
			
			File out = new File(destinationPath + File.separator + Main.tf.getText() + "_" + width + "_"+height+"_"+numImages + ".png");
			try {
				ImageIO.write(img, "png", out);
				
				System.out.println("Image Created");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
