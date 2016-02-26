package data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ImageContainer {
	
	private final static int width  = 352;
	private final static int height = 288;
	byte[] imagebytes;
	public BufferedImage image;
	
	ImageContainer(String imagefilename){
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		try{	
			File imageFile = new File(imagefilename);
			InputStream imageFileInputStream = new FileInputStream(imageFile);
			
			long len = imageFile.length();
			imagebytes = new byte[(int)len];
			
			int offset = 0, numRead = 0;
			while(offset < imagebytes.length && (numRead = imageFileInputStream.read(imagebytes, offset, imagebytes.length-offset))>=0){
				offset+=numRead;
			}		
			
			imageFileInputStream.close();
			
			} catch (FileNotFoundException e){
				 e.printStackTrace();
			} catch (IOException e){
				 e.printStackTrace();
			}
		
		DrawImage();
			
	}
	
	public void DrawImage(){
		
		 int ind = 0;
		 for(int y = 0; y < height; y++){
			 for(int x = 0; x < width; x++){
				 
				 byte r = imagebytes[ind];				
				 byte g = imagebytes[ind+height*width];	
				 byte b = imagebytes[ind+height*width*2];
				 
				 int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
				 image.setRGB(x, y, pix);
				 ind++; 
			 }
		 }
		
	}

}
